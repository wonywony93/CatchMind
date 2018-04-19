package chat.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import chat.client.CanvasDemo;

public class ServerThread extends Thread{
	
		int score;
		int clientNum;
		Socket s1;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Object obj;
		Server server;
		String str;
		String msg;
		String name;
		CanvasDemo cd;
		int cnt=0;
		String[] an = {"�ڳ���","����","����","�Ѱ���","������","������","���","���",
				"������ġ","��","��Ʈķ��","�ڳ�","���ϸ���","������","������","����",};
		
		int su = (int)(Math.random()*16);
		
		public ServerThread(Server ss,Socket s1){    
			this.server=ss;
			this.s1=s1;
		} //serterThread end..

		public void run(){  //Ŭ���̾�Ʈ���� ������ server�� ���ִ°�!
			   
			try{ 
				//client���� �Ѿ�� ��
				
				ois=new ObjectInputStream(s1.getInputStream());
				oos=new ObjectOutputStream(s1.getOutputStream());
				
				obj=ois.readObject();
				
				if(obj instanceof String){ //�ҷ��� ��ü�� ���ڸ�..

					if(((String) obj).indexOf("��ǥ")==0){
						msg=(String)obj;
						server.broadCasting(msg);
					}
					else if(((String)obj).indexOf("/����")==0){ //���󺯰�
						msg=(String)obj;
						server.broadCasting(msg);
					}


					else{ //�α���!
					cnt++;
					name=(String)obj;
					server.sum +=cnt;
					server.sendRight(this); //1�� ������ 2��������
					
					
					if(server.sum==1)//����
					{
						server.broadCasting("<����>"+name);
						server.broadCastingName(this);
						server.addNameVector("<����>"+name); //<����>
					}

					else {
						
					server.broadCasting("!@#"+name);
					server.broadCastingName(this);
					server.addNameVector(name);
					}
					
					server.broadCasting("["+name+"]"+"���� �����ϼ̽��ϴ�...");
					server.broadCasting("/����"+an[su]);
					}
					
					
					
					while(true){
						str=(String)ois.readObject();  //�޽����� �޴´�.
						if(str.indexOf("��ǥ")==0)
						{
							msg=str.substring(2);
							server.broadCasting("@~1"+msg);
						}
						else if(str.indexOf("/����")==0)
						{
							msg=str.substring(3);
							server.broadCasting("@~2"+msg);
						}

						else if(str.indexOf("@num")==0) //Ŭ���̾�Ʈ ������ ��ȣ ����
						{
							msg=str.substring(4);
							clientNum=Integer.parseInt(msg);
						
						}
						
						else{
							if(str.equals(an[su])) {
								server.broadCasting("*************************************************"); //Server�� BroadCasting
								server.broadCasting("*********"+name+"���� >> "+str+" << �����Դϴ�***********"); //Server�� BroadCasting
								server.broadCasting("*************************************************"); //Server�� BroadCasting
								su=(int)(Math.random()*16);
								server.broadCasting("/����"+an[su]);
							}

							else {	
								server.broadCasting(""+name+">"+str); //Server�� BroadCasting
							}
						}
					} //while end..
					
				}//if end...

			} //try
			catch(IOException e){//catch.. 
				server.removeThread(this);
				server.deleteNameVector(name);
				server.broadCasting("����"+name);  
				System.out.println("������ȣ"+clientNum);
				server.sendRight2(clientNum);
				System.out.println(s1.getInetAddress()+"���� ���� ����...");  //������忡 ������ message
			} //catch
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		      
		   } //run
		   public void sendMessage(String str){ 
			   try {
				oos.writeObject(str);
				oos.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		   } //sendMessage


		
		

}
