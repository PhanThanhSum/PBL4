package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import model.M_ServerFileHandler;

public class C_ClientHandler implements Runnable{
	private Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	M_ServerFileHandler m_ServerFileHandler;

    public C_ClientHandler(Socket socket) {
        this.socket = socket;
        m_ServerFileHandler = new M_ServerFileHandler();
        try {
			dis = new DataInputStream(this.socket.getInputStream());
			dos = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void run() {
    	while(true) {
	    	try {
	            String request = dis.readUTF();
	            switch (request) {
	            case "UP_LOAD":
	                this.upLoadHandler();
	                break;

	            case "LOAD":
	                this.loadHandler();
	                break;

	            case "DOWN_LOAD":
	                //this.receiveFileList();
	            	this.downLoadHandler();
	                break;
	                
	            case "DELETE":
	                //this.receiveFileList();
	            	this.deleteHandler();
	                break;
	                
	            default:
	                System.out.println("Unknown request: " + request);
	                break;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            try {
	            	dis.close();
					socket.close();
					break;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        } finally {
	        	
	        }
    	}
    }
    
    private void upLoadHandler() {
    	m_ServerFileHandler.upLoadHandler(dis);
    }
    
    private void loadHandler() {
    	m_ServerFileHandler.loadHandler(dis, dos);
    }
    
    private void downLoadHandler() {
    	m_ServerFileHandler.downLoadHandler(dis, dos);
    }
    
    private void deleteHandler() {
    	m_ServerFileHandler.deleteHandler(dis, dos);
    }
}
