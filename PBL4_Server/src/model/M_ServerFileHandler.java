package model;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.C_MyServer;

public class M_ServerFileHandler {
	private String homeDirectoryPath;
	
	public M_ServerFileHandler() {
		homeDirectoryPath = C_MyServer.getHomeDirectoryPath();
	}
	
	public boolean createFolderIfNotExists(String folderPath) {
        File folder = new File(this.homeDirectoryPath + folderPath);
        if (folder.exists()) {
            return false;
        } else {
            return folder.mkdirs();
        }
    }
	
	public void upLoadHandler(DataInputStream dis) {
        BufferedOutputStream bos = null;
        try {
            // Nhận tên và kích thước file từ client
        	String currentDirectoryPath = dis.readUTF();
            String fileName = dis.readUTF();
            long fileSize = dis.readLong();
            System.out.println("Receiving file: " + fileName + " with size: " + fileSize + " bytes");

            bos = new BufferedOutputStream(new FileOutputStream(this.homeDirectoryPath + currentDirectoryPath + "\\" + fileName));

            // Nhận dữ liệu file
            byte[] buffer = new byte[4096];
            int bytesRead;
            long totalRead = 0;
            while (totalRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            bos.flush();

            System.out.println("Nhận file thành công!");
        } catch (IOException e) {
			
        	
		} finally {
            if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
    }
	
	public void loadHandler(DataInputStream dis, DataOutputStream dos) {
    	String directoryPath;
		try {
			directoryPath = dis.readUTF();
			directoryPath = this.homeDirectoryPath + directoryPath;
	    	
	        File directory = new File(directoryPath);
	        
	        if (directory.canRead() && directory.isDirectory()) {
	            File[] files = directory.listFiles();
	            
	            if (files != null) {
	                dos.writeInt(files.length); // Gửi độ dài mảng
	                
	                for (File file : files) {
	                    dos.writeUTF(file.getName()); // Gửi từng tên file
	                }
	            } else {
	                dos.writeInt(0); // Nếu không có file nào
	            }
	        } else {
	            dos.writeInt(0); // Nếu không thể đọc thư mục
	        }
	        dos.flush(); // Đảm bảo dữ liệu được gửi đi
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public List<String> receiveFileNameListFromClient(DataInputStream dis) {
	    List<String> fileList = new ArrayList<>();
	    try {
	        // Đọc số lượng file
	        int fileCount = dis.readInt();
	        
	        // Đọc từng tên file và thêm vào danh sách
	        for (int i = 0; i < fileCount; i++) {
	            String fileName = dis.readUTF();
	            fileList.add(fileName);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    for(String fileName : fileList) {
	    	System.out.println(fileName);
	    }
	    return fileList;
	}
	
	public void downLoadHandler(DataInputStream dis, DataOutputStream dos) {
	    try {
	        // Nhận thư mục hiện tại từ client
	        String currentDirectory = dis.readUTF();
	        String fullDirectoryPath = this.homeDirectoryPath + currentDirectory;

	        // Nhận danh sách tên file từ client
	        List<String> fileList = this.receiveFileNameListFromClient(dis);

	        for (String fileName : fileList) {
	            File file = new File(fullDirectoryPath, fileName);

	            if (file.exists() && file.isFile()) {
	                // Gửi kích thước file cho client
	                dos.writeLong(file.length());

	                // Đọc file và gửi dữ liệu
	                try (FileInputStream fis = new FileInputStream(file)) {
	                    byte[] buffer = new byte[4096];
	                    int bytesRead;

	                    while ((bytesRead = fis.read(buffer)) != -1) {
	                        dos.write(buffer, 0, bytesRead);
	                    }
	                    dos.flush();
	                }

	                System.out.println("File " + fileName + " đã gửi thành công!");
	            } else {
	                // Gửi kích thước file bằng 0 để thông báo lỗi
	                dos.writeLong(0);
	                System.out.println("Không tìm thấy file: " + fileName);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteHandler(DataInputStream dis, DataOutputStream dos) {
	    try {
	        // Nhận thư mục hiện tại từ client
	        String currentDirectory = dis.readUTF();
	        String fullDirectoryPath = this.homeDirectoryPath + currentDirectory;

	        // Nhận danh sách tên file từ client
	        List<String> fileList = this.receiveFileNameListFromClient(dis);

	        for (String fileName : fileList) {
	            File file = new File(fullDirectoryPath, fileName);

	            if (file.exists() && file.isFile()) {
	                if (file.delete()) {
	                    dos.writeBoolean(true); // Báo thành công
//	                    System.out.println("File " + fileName + " đã xóa thành công.");
	                } else {
	                    dos.writeBoolean(false); // Báo lỗi
//	                    System.out.println("Không thể xóa file: " + fileName);
	                }
	            } else {
	                dos.writeBoolean(false); // Báo lỗi nếu file không tồn tại
//	                System.out.println("Không tìm thấy file: " + fileName);
	            }
	        }

	        dos.flush(); // Đảm bảo phản hồi được gửi đến client
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	

}
