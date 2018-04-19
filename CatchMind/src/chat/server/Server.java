package chat.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import chat.client.CanvasDemo;

public class Server {
	
	int i=1;
	int sum=0;
	int count=1;
	boolean set=false;
	ServerSocket s;
	Socket s1;
	Vector v=new Vector(); //vector에는 클라이언트들을 저장한다.
	Vector nickV = new Vector();
	StringBuffer sb = new StringBuffer();
	ObjectOutputStream oos;
	String answer;
	 public void go(){ 
		   
		      try{  
		    	  s=new ServerSocket(4500);
		          System.out.println("서버 접속 대기중...");
		       
		        while(true){ //client 접속 대기중
		            s1=s.accept();  
		            System.out.println(s1.getInetAddress()+" 접속...");
		            ServerThread std=new ServerThread(this,s1); //1번 std가 방장
		            addThread(std);
		            
		            std.start();
		            
		           } //while end

		      } //try end..
		      catch(IOException e){  
		         System.out.println("연결이 종료되었습니다.");
		      } //catch end
		   } //go end...
	  
	 
	 //첫번째 방장Thread
	 public ServerThread headThread(){
		 return (ServerThread) v.get(0);
	 }
	 
	
	 public void addThread(ServerThread std){       
		 v.addElement(std);  //vector에 serverThread 추가~~
	 } //addThread end

	 public void removeThread(ServerThread std){  
		 
		 v.removeElement(std);  //vector에 serverThread삭제
	 } //removeThread end..

	 
	//클라이언트들에게 메세지 뿌리기
	 
	 public void broadCasting(String str){          
		 for(int i=0;i<v.size();i++){//vecter의 사이즈 만큼
			 ServerThread std=(ServerThread)v.elementAt(i); //서버스레드 객체 생성하고
			 if(str.indexOf("@~")!=0)
				 std.sendMessage(str); //각 client 에 vector 넘겨주기
			
			 else //좌표일때
			 {
				 if(!(std.equals(headThread())))
				 {
					 std.sendMessage(str);
				 }
			 }
		 } 
		 
	 } //broadCasting
	  

	//------- 이미 접속해 있는 클라이언트에게 이름 보내기 -------
	  public void broadCastingName(ServerThread st) {
		  
	   for(int j=0; j <nickV.size(); j++) {
		   
				sb.append("!@#");	   
				sb.append(nickV.get(j));
				st.sendMessage(sb.toString());
				sb.setLength(0);
	   }
	  }
	  
	  public void addNameVector(String str) {
	      nickV.add(str);
	  }
	  
	  public void deleteNameVector(String name){
		  nickV.remove(name);
	  }
	  
	  
	  //방장권한 넘기기 (첫번째 클라이언트가 나가면 다음 클라이언트에게 방장 부여)
	  public void sendRight2(int num) 
	  {
		  ServerThread std;
		  ServerThread std2;

		  if(num==i) { 
			   i++;
			   System.out.println(nickV.get(0));
			   nickV.remove(0);
			   if(v.isEmpty()==false){
			   std=(ServerThread)v.elementAt(0);//해당 클라이언트 뽑아서(첫번째 클라이언트)

			   if(v.size()>1){
			   for(int j=1; j<v.size(); j++)
			   {
				   std2=(ServerThread)v.elementAt(j);
				   std2.sendMessage("@@up<방장>"+nickV.get(0));
			   }
			   }
			   std.sendMessage("@@방장<방장>");//클라이언트 Thread
			   
			   String name=(String)nickV.get(0);
			   nickV.remove(0);
			   nickV.add(0, "<방장>"+name);

			   }
		  }
	  }
	  
	  //20180419
	  //권한 부여하기(내번호 보내기+점수보내기)-----
	  public void sendRight(ServerThread st) {
		  //내번호!점수
		  st.sendMessage("@aty"+sum);
		  
	  }
	
	  public static void main(String[] args){ //main start..
	      Server ss=new Server();
	      ss.go();
	  } //main end
	

}
