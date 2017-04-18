/*상품1건의 디스플레이를 표현한 클래스*/

package com.ss.client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Product extends JPanel implements ActionListener{
	ClientMain main;
	Canvas can;
	JButton bt_buy;
	BufferedImage image;
	URL url; //자원의 위치에 대한 객체
	int width=120;//캔버스 크기
	int height=150;
	
	
	public Product(URL url, ClientMain main) {
		this.main=main;
		this.url=url;
		
		//이미지 생성!!
		try {
			image=ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can = new Canvas() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, width, height, this);
			}
		};
		bt_buy = new JButton("구매");

		setLayout(new BorderLayout());

		bt_buy.addActionListener(this);
		
		add(can);
		add(bt_buy, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(width, width+45));
	}

	//xml은 Text기반의 데이터전달객체
	//json도 Text기반의 데이터전달객체, json이 훨씬 간단하다.
	//json을 프로토콜로 써보자
	//json은 parser를 이용하면 xml보다 훨씬 간단하다.
	public void buy(){
		//나 살래요!!
		//String msg="나의 정체+구분자+뭘먹을지...";
		/*String msg="requestType=buy&product_id=batman";*/
		/*Sting msg={
			"requestType":"chat",
			"msg":"안녕하세요?",
			"id":"batman"			
		}*/
		String msg="";
		
		
		main.ct.send(msg);
		
	}

	public void actionPerformed(ActionEvent e) {
		buy();
	}

}
