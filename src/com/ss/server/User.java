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
		//thread.start(); 여기서 스타트해버리면 아래에서 스트림을 써버린다.
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		//스트림 생성 후에 대화가 가능하기 때문에 여기서 생성하고 시작한다.
		thread=new Thread(this);
		thread.start(); 
		
		la=new JLabel("123번 pc");
		
		add(la);
		
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
		setVisible(true);
	}
	
	//듣기
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine();
			
			//대화, 구매, 가입, 탈퇴....    우리가 다 프로토콜을 만들어야 한다. "requestType=buy&id=batman&product_id=87"
			//커맨드 패턴을 이용하면 쉽게 만들수 있지만 먼저 if문을 써보자.
			
			//요청분석 시작
			//requestType=chat or buy//chat이면 돌려보내고 buy면 안돌려보낸다.
			//msg=클라이언트말
			//id=클라이언트id
			
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");
			if(requestType[1].equals("chat")){
				String[] str=data[1].split("=");
				send(str[1]);//클라이언트에 다시 보내기								
			}else if (requestType[1].equals("buy")) {
				System.out.println("구매를 원하는 군요");
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//말하기
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
