package com.ss.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center; //전체 화면용
	Thread thread; //접속자 감지용 쓰레드
	ServerSocket server;
	int port=7878;
	
	public ServerMain() {
		
		try {
			server=new ServerSocket(port);
			System.out.println("서버 생성");
			thread=new Thread(this);
			thread.start();//이시점엔 메인쓰레드와 개발자 정의 쓰레드는 각자 자신의 코드를 수행하게 되므로
			//메인 쓰레드에 의해 프레임이 화면에 등장함과 동시에 서버에 accept()도 동시에 동작하게된다.
			//즉 별도의 실행을 하며 독립적으로 가동된다. 누가 먼저동작할지는 알수 없다. 분기점
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p_center=new JPanel();
		p_center.setBackground(Color.BLUE);
		
		add(p_center);
		
		setSize(800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void run() {
		//접속자를 무한으로 받는다.!
		while (true) {
			try {
				Socket socket=server.accept();
				//접속자가 발견되면, pc 메인 카운터에 접속자를 화면에 등장시킨
				User user=new User(socket);
				
				p_center.add(user);
				p_center.updateUI();
				
			} catch (IOException e) {
				e.printStackTrace();
			}		
			
		}
	}
	
	
	public static void main(String[] args) {
		new ServerMain();
	}
}
