/*��ǰ1���� ���÷��̸� ǥ���� Ŭ����*/

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
	URL url; //�ڿ��� ��ġ�� ���� ��ü
	int width=120;//ĵ���� ũ��
	int height=150;
	
	
	public Product(URL url, ClientMain main) {
		this.main=main;
		this.url=url;
		
		//�̹��� ����!!
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
		bt_buy = new JButton("����");

		setLayout(new BorderLayout());

		bt_buy.addActionListener(this);
		
		add(can);
		add(bt_buy, BorderLayout.SOUTH);

		this.setPreferredSize(new Dimension(width, width+45));
	}

	//xml�� Text����� ���������ް�ü
	//json�� Text����� ���������ް�ü, json�� �ξ� �����ϴ�.
	//json�� �������ݷ� �Ẹ��
	//json�� parser�� �̿��ϸ� xml���� �ξ� �����ϴ�.
	public void buy(){
		//�� �췡��!!
		//String msg="���� ��ü+������+��������...";
		/*String msg="requestType=buy&product_id=batman";*/
		/*Sting msg={
			"requestType":"chat",
			"msg":"�ȳ��ϼ���?",
			"id":"batman"			
		}*/
		String msg="";
		
		
		main.ct.send(msg);
		
	}

	public void actionPerformed(ActionEvent e) {
		buy();
	}

}
