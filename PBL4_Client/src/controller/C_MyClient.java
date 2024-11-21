package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import model.M_FileHandler;

public class C_MyClient {
	private String serverAddress;
    private int serverPort;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    
    private M_FileHandler m_FileHandler;
    
    public C_MyClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.m_FileHandler = new M_FileHandler("Sum");
        try {
            this.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Kết nối đến server " + serverAddress + " qua cổng " + serverPort);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
    }
    
    public void disconnect() throws IOException {
        if (dos != null) dos.close();
        if (socket != null) socket.close();
        System.out.println("Đã ngắt kết nối với server.");
    }

    public void upLoad(String filePath) {
    	try {
			this.dos.writeUTF("UP_LOAD"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
    	m_FileHandler.upLoad(filePath, dos);
    }
    
    public String[] load() {
    	try {
			dos.writeUTF("LOAD");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return m_FileHandler.load(dis, dos);
    }
    
    public void downLoadFile(List<String> fileList) {
    	try {
			dos.writeUTF("DOWN_LOAD");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//m_FileHandler.sendFileNameListToServer(fileList, dos);
    	m_FileHandler.downLoad(fileList, dis, dos);
    }
    
    public void deleteFile(List<String> fileList) {
    	try {
			dos.writeUTF("DELETE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	m_FileHandler.delete(fileList, dis, dos);
    }
}
