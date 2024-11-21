package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class C_MyServer {
	private int port;
    private ServerSocket serverSocket;
    private static String homeDirectoryPath;

	public static String getHomeDirectoryPath() {
		return homeDirectoryPath;
	}

	public C_MyServer(int port) {
        this.port = port;
    }

    public void startServer(String Path) {
    	homeDirectoryPath = Path;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server đang lắng nghe tại cổng " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Kết nối từ " + socket);

                new Thread(new C_ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
