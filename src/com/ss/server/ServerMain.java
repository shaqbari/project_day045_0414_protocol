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
	JPanel p_center; //��ü ȭ���
	Thread thread; //������ ������ ������
	ServerSocket server;
	int port=7878;
	
	public ServerMain() {
		
		try {
			server=new ServerSocket(port);
			System.out.println("���� ����");
			thread=new Thread(this);
			thread.start();//�̽����� ���ξ������ ������ ���� ������� ���� �ڽ��� �ڵ带 �����ϰ� �ǹǷ�
			//���� �����忡 ���� �������� ȭ�鿡 �����԰� ���ÿ� ������ accept()�� ���ÿ� �����ϰԵȴ�.
			//�� ������ ������ �ϸ� ���������� �����ȴ�. ���� �������������� �˼� ����. �б���
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
		//�����ڸ� �������� �޴´�.!
		while (true) {
			try {
				Socket socket=server.accept();
				//�����ڰ� �߰ߵǸ�, pc ���� ī���Ϳ� �����ڸ� ȭ�鿡 �����Ų
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
