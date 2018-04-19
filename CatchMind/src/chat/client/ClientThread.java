package chat.client;


import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import chat.server.Server;


public class ClientThread extends Thread {

		ArrayList<String> nl=new ArrayList<String>();
		int myScore=0;
		int myNumber=0;
		String head;
		Server server;
		CanvasDemo cd1;
		Socket s1;
		static String color;
		static int x,w,h,sw;
		static int y;
		ObjectInputStream ois;
		String str2;
		String name;
		Client client;
		Object obj;
		int c;

	   public ClientThread(Socket s1,Client client){   
		   this.s1=s1;
	       this.client=client;

	   } //clientThread end..
	   
	   public void run(){                                
	      try{                
	    	 
	          //srever���� �Ѿ�� �� �ޱ�
	          
	    	 ois=new ObjectInputStream(s1.getInputStream()); 
	    	
	         while(true){
	        	
	        	
	        	String str2="";
	        	obj=ois.readObject();
	        	
	        	str2=(String)obj;
	        	
	        	//������ ����� �̸� �߰�
	            if(str2.indexOf("!@#") == 0){
	            	name=str2.substring(3);
	            	client.addName(name);
	            	client.setScore(myScore);
	            	client.addScoreNumber(name);
	            	nl.add(name);
	            }
	            //---score ����
	            else if(str2.indexOf("@����")==0){
	            	String temp=str2.substring(3);
	            	myScore=Integer.parseInt(temp);
	            	client.setScore(myScore);
	            }
	            //ù��° �濡 ���� ��� �������
	            else if(str2.indexOf("<����>")==0)
	            {
	            	
	            	name=str2.substring(4);
	            	client.addName("<����>"+name);
	            	client.settingRight();
	            	nl.add(str2.substring(4));
	            }
	            //������ ���� �� ���� ���� ����
	            else if(str2.indexOf("@@����")==0)
	            {
	            	
//	            	client.updateName(nl.get(0));
	            	client.updateName(str2.substring(4)+nl.get(0));
	            	client.settingRight();
	            	
	            }
	            //������� �ٲ� �Ŀ� ����Ʈ �̸� ����
	            else if(str2.indexOf("@@up")==0)
	            {
	            	String e=str2.substring(4);
	            	client.updateName(e);
	            }

	            //������ ��ȣ �ޱ� 
	            else if(str2.indexOf("@aty")==0)
	            {
	            	String tmp=str2.substring(4);
	            	
	            	myNumber=Integer.parseInt(tmp);
	            	client.setNumber(myNumber);
	            }
	            
	            //����ҷ�����
	            else if(str2.indexOf("/����")==0)
	            {
	            	String tmp=str2.substring(3);//����
	            	System.out.println(tmp);
	            	client.answer(tmp);//answer
	            }
	            //���������� �� �����߰��ϱ�...
	            else if(str2.indexOf("@���")==0)
	            {
	            	String tmp=str2.substring(3);
	            	String[] temp=tmp.split(",");
	            	String n1=temp[0];
	            	int score=Integer.parseInt(temp[1]);
	            	client.addScore(n1, score);
	            	
	            	
	            }
	            
	            else if(str2.indexOf("@~1")==0){//��ǥ
	            	
	            	String tmp=str2.substring(3);
	            	String[] lo=tmp.split("!");
	            	x=Integer.parseInt(lo[0]);
	            	y=Integer.parseInt(lo[1]);
	            	w=Integer.parseInt(lo[2]);
	            	h=Integer.parseInt(lo[3]);
	            	
	            	cd1=client.getCd();
	            	client.canvasSetting(cd1, x, y, w, h,sw,color);
	            }
	            else if(str2.indexOf("@~2")==0) { //����,sw����
	            	
	            	String tmp=str2.substring(3);
	            	String[] lo=tmp.split("!");
	            	sw=Integer.parseInt(lo[0]);
	            	color=lo[1];
	            	cd1=client.getCd();
	            	client.canvasSetting(cd1, x, y, w, h,sw,color);

	            }

	            
	            else if(str2.indexOf("����")==0){ 
	            	String str3=str2.substring(2);
	            	client.chatA.append("["+str3+"]"+"���� �����Ͽ����ϴ�..\n");
	            	//<����>
	            	for (int j = 0; j < client.nameList.size(); j++) {
						
	            		if((client.nameList.get(j).indexOf("<����>")==0))
	            				{
	            			String tmp=client.nameList.get(j).substring(4);
	            			if(tmp.equals(str3))
	            				client.deleteName(client.nameList.get(j));
	            				}
	            		else{
	            			if((client.nameList.get(j).equals(str3)))
	            					client.deleteName(client.nameList.get(j));
	            		}
					}
	
	            	client.deleteThread(this);
	            	
	            }

	            else {
	               client.chatA.append(str2+"\n");//mainâ���� ���..
	               
	            }
//	            }//if end
	 
	        	}//while
	        	
	         } //try
	      catch(IOException e){                           //catch ����
	         System.out.println("Error...");
	      } //catch
	      catch (ClassNotFoundException e) {
	      }
	    
	   } //run

	public void settingCanvas(CanvasDemo cd,int x2, int y2,int w1,int h1,int sw,String color) {
		
		cd1=cd;
		cd1.x=x2;
		cd1.y=y2;
		cd1.w=w1;
		cd1.h=h1;
		cd1.sw=sw;
		
		
		switch(color)
		{
    	case "java.awt.Color[r=255,g=200,b=0]":
    		cd.color=Color.ORANGE;
    		break;
    	case "java.awt.Color[r=0,g=255,b=0]":
    		cd.color=Color.GREEN;
    		break;
    	case "java.awt.Color[r=255,g=0,b=0]":
    		cd.color=Color.RED;
    		break;
    	case "java.awt.Color[r=0,g=0,b=255]":
    		cd.color=Color.BLUE;
    		break;
    	case "java.awt.Color[r=0,g=0,b=0]":
    		cd.color=Color.BLACK;
    		break;
    	case "java.awt.Color[r=255,g=255,b=255]":
    		cd.color=Color.WHITE;
    		break;
		}
		
		cd1.repaint();
		
		
	}




}
