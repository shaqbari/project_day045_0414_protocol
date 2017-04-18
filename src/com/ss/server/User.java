package com.ss.server;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel implements Runnable{
	JLabel la;
	
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Boolean flag=true;
	Thread thread;
	
	
	public User(Socket socket) {
		this.socket=socket;	
		//thread=new Thread(this);
		//thread.start(); ���⼭ ��ŸƮ�ع����� �Ʒ����� ��Ʈ���� �������.
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		//��Ʈ�� ���� �Ŀ� ��ȭ�� �����ϱ� ������ ���⼭ �����ϰ� �����Ѵ�.
		thread=new Thread(this);
		thread.start(); 
		
		la=new JLabel("123�� pc");
		
		add(la);
		
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
		setVisible(true);
	}
	
	//���
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			
			//��ȭ, ����, ����, Ż��....    �츮�� �� ���������� ������ �Ѵ�. "requestType=buy&id=batman&product_id=87"
			//Ŀ�ǵ� ������ �̿��ϸ� ���� ����� ������ ���� if���� �Ẹ��.
			
			//��û�м� ����
			//requestType=chat or buy//chat�̸� ���������� buy�� �ȵ���������.
			//msg=Ŭ���̾�Ʈ��
			//id=Ŭ���̾�Ʈid
			
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");
			if(requestType[1].equals("chat")){
				String[] str=data[1].split("=");
				send(str[1]);//Ŭ���̾�Ʈ�� �ٽ� ������								
			}else if (requestType[1].equals("buy")) {
				System.out.println("���Ÿ� ���ϴ� ����");
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while (flag) {
			listen();
		}
	}
	
}
