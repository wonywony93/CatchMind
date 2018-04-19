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
		String[] an = {"코끼리","사자","퇴학","한가인","아이폰","장조림","돈까스","배그",
				"오버워치","롤","비트캠프","코난","포켓몬스터","서강대","정혜원","나무",};
		
		int su = (int)(Math.random()*16);
		
		public ServerThread(Server ss,Socket s1){    
			this.server=ss;
			this.s1=s1;
		} //serterThread end..

		public void run(){  //클라이언트에서 받은걸 server에 쏴주는거!
			   
			try{ 
				//client에서 넘어온 값
				
				ois=new ObjectInputStream(s1.getInputStream());
				oos=new ObjectOutputStream(s1.getOutputStream());
				
				obj=ois.readObject();
				
				if(obj instanceof String){ //불러온 객체가 문자면..

					if(((String) obj).indexOf("좌표")==0){
						msg=(String)obj;
						server.broadCasting(msg);
					}
					else if(((String)obj).indexOf("/변경")==0){ //색상변경
						msg=(String)obj;
						server.broadCasting(msg);
					}


					else{ //로그인!
					cnt++;
					name=(String)obj;
					server.sum +=cnt;
					server.sendRight(this); //1번 보내고 2번보내고
					
					
					if(server.sum==1)//방장
					{
						server.broadCasting("<방장>"+name);
						server.broadCastingName(this);
						server.addNameVector("<방장>"+name); //<방장>
					}

					else {
						
					server.broadCasting("!@#"+name);
					server.broadCastingName(this);
					server.addNameVector(name);
					}
					
					server.broadCasting("["+name+"]"+"님이 입장하셨습니다...");
					server.broadCasting("/정답"+an[su]);
					}
					
					
					
					while(true){
						str=(String)ois.readObject();  //메시지를 받는다.
						if(str.indexOf("좌표")==0)
						{
							msg=str.substring(2);
							server.broadCasting("@~1"+msg);
						}
						else if(str.indexOf("/변경")==0)
						{
							msg=str.substring(3);
							server.broadCasting("@~2"+msg);
						}

						else if(str.indexOf("@num")==0) //클라이언트 각각의 번호 받음
						{
							msg=str.substring(4);
							clientNum=Integer.parseInt(msg);
						
						}
						
						else{
							if(str.equals(an[su])) {
								server.broadCasting("*************************************************"); //Server에 BroadCasting
								server.broadCasting("*********"+name+"님이 >> "+str+" << 정답입니다***********"); //Server에 BroadCasting
								server.broadCasting("*************************************************"); //Server에 BroadCasting
								su=(int)(Math.random()*16);
								server.broadCasting("/정답"+an[su]);
							}

							else {	
								server.broadCasting(""+name+">"+str); //Server에 BroadCasting
							}
						}
					} //while end..
					
				}//if end...

			} //try
			catch(IOException e){//catch.. 
				server.removeThread(this);
				server.deleteNameVector(name);
				server.broadCasting("나감"+name);  
				System.out.println("나간번호"+clientNum);
				server.sendRight2(clientNum);
				System.out.println(s1.getInetAddress()+"서버 접속 중지...");  //서버모드에 찍히는 message
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
