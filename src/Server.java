package teamProject;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server{
	
//	서버 클래스는 서버를 띄우고 요청에 따라서 해당 기능을 하는 스레드에 소켓을 보내 실행한다.	
	public static void main(String[] args) {
	
		ServerSocket serverSocket = null;
		Scanner sc = null;
		Socket socket = null;
		
		System.out.println("서버 가동");
		
		
		try {
			//서버 소켓(10004번 포트)
			serverSocket = new ServerSocket(10004);
			while(true) {
				//무한 반복을 통해서 접근하는 모든 클라이언트의 접근을 받는다.
				socket = serverSocket.accept();
				//클라이언트의 요청을 구분하여 해당하는 스레드에 소켓을 넘겨 실행한다.
				sc = new Scanner(socket.getInputStream());
				String request = sc.nextLine().trim();
				int check = Integer.parseInt(request);
				switch (check) {
				case 1:
					BoardThread bh = new BoardThread(socket);
					Thread thread = new Thread(bh);
					thread.start();
					break;
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(serverSocket != null) {try {serverSocket.close();} catch (Exception e2) {}}
		}
		
	}
	
	
	

}
