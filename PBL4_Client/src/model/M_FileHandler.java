package model;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class M_FileHandler {
	private String currentDirectoryPath;
	private String downLoadDirectory;
	
	public M_FileHandler(String userName) {
		currentDirectoryPath = "\\" + userName;
		downLoadDirectory = "C:\\Users\\Admin\\Downloads\\My DownLoad";
	}

	public String getCurrentDirectoryPath() {
		return currentDirectoryPath;
	}

	public void setCurrentDirectoryPath(String currentDirectoryPath) {
		this.currentDirectoryPath = currentDirectoryPath;
	}
	
	public void upLoad(String filePath, DataOutputStream dos) {
        File file = new File(filePath);
        FileInputStream fis;
		try {
			fis = new FileInputStream(file);

	        // Gửi tên file và kích thước file đến server
	        //
	    	//	Sửa chỗ truyền curruntDirectory
	    	//
	    	dos.writeUTF(this.currentDirectoryPath);
	        dos.writeUTF(file.getName());
	        dos.writeLong(file.length());  // Gửi kích thước file trước khi gửi dữ liệu

	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        while ((bytesRead = fis.read(buffer)) != -1) {
	            dos.write(buffer, 0, bytesRead);
	        }
	        dos.flush();

	        fis.close();
	        System.out.println("Đã gửi file thành công!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void sendFileNameListToServer(List<String> fileList, DataOutputStream dos) {
		try {
			dos.writeInt(fileList.size());
	        for(String fileName : fileList) {
	        	dos.writeUTF(fileName);
	        }
	        dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downLoad(List<String> fileList, DataInputStream dis, DataOutputStream dos) {
	    try {
	        // Gửi thư mục hiện tại
	        dos.writeUTF(this.currentDirectoryPath);
	        // Gửi danh sách tên file cần tải xuống
	        this.sendFileNameListToServer(fileList, dos);

	        // Tải từng file từ server
	        for (String fileName : fileList) {
	            // Nhận kích thước file
	            long fileSize = dis.readLong();
	            File file = new File(this.downLoadDirectory, fileName);

	            // Đảm bảo thư mục tồn tại
	            file.getParentFile().mkdirs();

	            // Lưu dữ liệu nhận được vào file
	            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
	                byte[] buffer = new byte[4096];
	                int bytesRead;
	                long totalRead = 0;

	                while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
	                    bos.write(buffer, 0, bytesRead);
	                    totalRead += bytesRead;
	                }
	                bos.flush();
	            }

	            System.out.println("File " + fileName + " đã tải xuống thành công!");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public String[] load(DataInputStream dis, DataOutputStream dos) {
    	try {
			dos.writeUTF(this.currentDirectoryPath); // Gửi đường dẫn thư mục cần lấy danh sách
	        dos.flush();
	        
	        int fileCount = dis.readInt(); // Nhận số lượng file
	        
	        if (fileCount > 0) {
	            String[] fileList = new String[fileCount];
	            
	            for (int i = 0; i < fileCount; i++) {
	                fileList[i] = dis.readUTF(); // Nhận từng tên file
	            }
	            
//	            System.out.println("Danh sách file:");
//	            for (String fileName : fileList) {
//	                System.out.println("- " + fileName);
//	            }
	            return fileList;
	        } else {
	            System.out.println("Thư mục trống hoặc không thể đọc được.");
	            return null;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
   
    }
	
	public void delete(List<String> fileList, DataInputStream dis, DataOutputStream dos) {
	    try {
	        // Gửi thư mục hiện tại
	        dos.writeUTF(this.currentDirectoryPath);

	        // Gửi danh sách tên file cần xóa
	        this.sendFileNameListToServer(fileList, dos);

	        // Nhận phản hồi từ server
	        for (int i = 0; i < fileList.size(); i++) {
	            boolean isDeleted = dis.readBoolean();
	            if (isDeleted) {
	                System.out.println("File " + fileList.get(i) + " đã được xóa.");
	            } else {
	                System.out.println("Không thể xóa file: " + fileList.get(i));
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
