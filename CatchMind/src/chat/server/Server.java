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
	Vector v=new Vector(); //vector���� Ŭ���̾�Ʈ���� �����Ѵ�.
	Vector nickV = new Vector();
	StringBuffer sb = new StringBuffer();
	ObjectOutputStream oos;
	String answer;
	 public void go(){ 
		   
		      try{  
		    	  s=new ServerSocket(4500);
		          System.out.println("���� ���� �����...");
		       
		        while(true){ //client ���� �����
		            s1=s.accept();  
		            System.out.println(s1.getInetAddress()+" ����...");
		            ServerThread std=new ServerThread(this,s1); //1�� std�� ����
		            addThread(std);
		            
		            std.start();
		            
		           } //while end

		      } //try end..
		      catch(IOException e){  
		         System.out.println("������ ����Ǿ����ϴ�.");
		      } //catch end
		   } //go end...
	  
	 
	 //ù��° ����Thread
	 public ServerThread headThread(){
		 return (ServerThread) v.get(0);
	 }
	 
	
	 public void addThread(ServerThread std){       
		 v.addElement(std);  //vector�� serverThread �߰�~~
	 } //addThread end

	 public void removeThread(ServerThread std){  
		 
		 v.removeElement(std);  //vector�� serverThread����
	 } //removeThread end..

	 
	//Ŭ���̾�Ʈ�鿡�� �޼��� �Ѹ���
	 
	 public void broadCasting(String str){          
		 for(int i=0;i<v.size();i++){//vecter�� ������ ��ŭ
			 ServerThread std=(ServerThread)v.elementAt(i); //���������� ��ü �����ϰ�
			 if(str.indexOf("@~")!=0)
				 std.sendMessage(str); //�� client �� vector �Ѱ��ֱ�
			
			 else //��ǥ�϶�
			 {
				 if(!(std.equals(headThread())))
				 {
					 std.sendMessage(str);
				 }
			 }
		 } 
		 
	 } //broadCasting
	  

	//------- �̹� ������ �ִ� Ŭ���̾�Ʈ���� �̸� ������ -------
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
	  
	  
	  //������� �ѱ�� (ù��° Ŭ���̾�Ʈ�� ������ ���� Ŭ���̾�Ʈ���� ���� �ο�)
	  public void sendRight2(int num) 
	  {
		  ServerThread std;
		  ServerThread std2;

		  if(num==i) { 
			   i++;
			   System.out.println(nickV.get(0));
			   nickV.remove(0);
			   if(v.isEmpty()==false){
			   std=(ServerThread)v.elementAt(0);//�ش� Ŭ���̾�Ʈ �̾Ƽ�(ù��° Ŭ���̾�Ʈ)

			   if(v.size()>1){
			   for(int j=1; j<v.size(); j++)
			   {
				   std2=(ServerThread)v.elementAt(j);
				   std2.sendMessage("@@up<����>"+nickV.get(0));
			   }
			   }
			   std.sendMessage("@@����<����>");//Ŭ���̾�Ʈ Thread
			   
			   String name=(String)nickV.get(0);
			   nickV.remove(0);
			   nickV.add(0, "<����>"+name);

			   }
		  }
	  }
	  
	  //20180419
	  //���� �ο��ϱ�(����ȣ ������+����������)-----
	  public void sendRight(ServerThread st) {
		  //����ȣ!����
		  st.sendMessage("@aty"+sum);
		  
	  }
	
	  public static void main(String[] args){ //main start..
	      Server ss=new Server();
	      ss.go();
	  } //main end
	

}
