package com.ss.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientMain extends JFrame implements ActionListener{
	JPanel p_center, p_south;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	JButton bt;
	String[] path={"first.jpg", "business.jpg", "deluxe.jpg", "first.jpg", "grand.jpg", "vip.jpg", "wine.jpg", "beer.jpg", "breakfast.jpg", "dinner.jpg"};
	//이경로를 나중에 stream으로 서버에서 client로 줘야 한다.
	
	Socket socket;
	String ip="localhost";
	int port=7878;
	ClientThread ct;
	
	public ClientMain() {
		p_center=new JPanel();
		p_south=new JPanel();
		area=new JTextArea();
		scroll=new JScrollPane(area);
		t_input=new JTextField(20);
		bt=new JButton("접속");
		
		scroll.setPreferredSize(new Dimension(580, 200)); //area에 주면 스크롤생성이 안된다.
		
		//상품 나열하기!
		createProduct();
		
		p_center.add(scroll);
		
		p_south.add(t_input);
		p_south.add(bt);
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		//버튼과 리스너 연결
		bt.addActionListener(this);
		
		//입력박스와 리스너 연결
		t_input.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					String msg=t_input.getText();			
					
					//ct.send(msg); //앞으로는 채팅이라는 것과 누구인지도 함께 보내야 한다.
					ct.send("requestType=chat&msg="+msg+"&id=batman");
										
					t_input.setText("");
					//서버에 메시지 보냄
				}
			}
		});
		
				
		setSize(650, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//상품 이미지를 웹서버에서 가져오자
	public void createProduct(){
		try {
			
			for(int i=0; i<path.length; i++){
				URL url=new URL("http://localhost:9090/data/"+path[i]);		
				Product product=new Product(url, this);
				p_center.add(product);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	//접속메소드 정의
	public void connect(){
		try {
			socket=new Socket(ip, port);
			ct=new ClientThread(socket, area);
			ct.start();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		connect();
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}

}
