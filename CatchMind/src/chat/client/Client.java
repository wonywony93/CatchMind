package chat.client;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Client {

	ArrayList<String> nameList=new ArrayList<String>();
	private int number=0;
	private ObjectInputStream ois;
	private String an,msg;
	private  Vector v=new Vector();
	private Socket s1;
	private String server="127.0.0.1"; //IP�ּ�
	private Panel p1,p2,optionP,loginP,gameP,chatP,listP;
	private JLabel answer,la,lm,user;
	private JButton bt,black,orange,green,red,blue;
	private ImageIcon im;
	private TextField tf;
	private Frame loginF,gameF;
	private List ul,rank;//userList
	TextArea chatA;
	private TextField chatF;
	JButton plus;
	JButton minus;
	private Button erase,eraseAll;
	CanvasDemo cd;
	private ObjectOutputStream oos;
	private String name;
	private int score=0;
	
	public Client() {

		
		//�α���â
		loginF=new Frame();
		loginP=new Panel();
	    im = new ImageIcon("./img/mind.png");
		lm = new JLabel(im);
		la=new JLabel("�г���");
		tf=new TextField(13);
		bt = new JButton(new ImageIcon("./img/login.png"));
		bt.setBorderPainted(false);
		bt.setContentAreaFilled(false);
		bt.setFocusPainted(false);
		
		
		bt.setSize(200, 50);
		
		//����â
		gameF=new Frame();
		gameP=new Panel();
		optionP=new Panel();
		chatP=new Panel();
		listP=new Panel();
		user=new JLabel("<������>");//������
		ul=new List(6);
		rank=new List(15);
		chatF=new TextField(30);
		chatA=new TextArea();
		black = new JButton("black");
		orange = new JButton("orange");
		green = new JButton("green");
		red = new JButton("red");
		blue = new JButton("blue");
		plus = new JButton(new ImageIcon("./img/plus.png"));
		minus = new JButton(new ImageIcon("./img/minus.png"));
		erase = new Button("�����");
		eraseAll = new Button("��� �����");
		
		
		answer=new JLabel("");
		
		answer.setVisible(false);
		cd = new CanvasDemo();
		cd.setBackground(Color.WHITE);
		
		plus.setBorderPainted(false);
		plus.setContentAreaFilled(false);
		plus.setFocusPainted(true);
		
		minus.setBorderPainted(false);
		minus.setContentAreaFilled(false);
		minus.setFocusPainted(true);
		
		erase.enable(false);
		eraseAll.enable(false);
		cd.enable(false);

	}
	
	//EventHandler
	class Handler implements ActionListener{



		//�α���â
		public void launchFrame(){ 
			
			loginP.add(lm);
			loginP.add(la);
			loginP.add(tf);
			loginP.add(bt);
			
			
			loginF.add(loginP);
			loginF.setBackground(new Color(142,171,241));
			bt.setBackground(new Color(137,170,215));
			loginP.setBackground(new Color(142,171,241));

			
			loginF.setTitle("�α���");
			
			loginF.pack(); //frame ũ���ּ�ȭ
			loginF.setSize(400,100);
			loginF.setResizable(false);  //frame ũ�����
			Dimension d=loginF.getToolkit().getScreenSize();
			loginF.setLocation(d.width/2-loginF.getWidth()/2,d.height/2-loginF.getHeight()/2);    //frame center��ġ
			loginF.setVisible(true);
			tf.addActionListener(this);
			bt.addActionListener(this);//button action
			loginF.addWindowListener(new WindowAdapter(){

				public void windowClosing(WindowEvent e){
					System.exit(0);
				}
			});
		}// launchFrame end...
		
		//����â
		public void gameFrame(){

			im = new ImageIcon("./img/mind2.png");
			JLabel la=new JLabel(im);
			la.setBackground(Color.black);
			
			
			optionP.setForeground(null);
			p1=new Panel(){ //color����
				public Insets getInsets() {
					return new Insets(10, 10, 10, 10); // ����
				}
			};
			//Color��ư���� 
			optionP.add(p1);
			black.setBackground(Color.black);
			orange.setBackground(Color.orange);			
			green.setBackground(Color.green);
			red.setBackground(Color.red);
			blue.setBackground(Color.blue);
			
			p1.add(black);
			p1.add(orange);
			p1.add(green);
			p1.add(red);
			p1.add(blue);
			
			
			p2=new Panel();
			
			p2.add(plus);
			p2.add(minus);
			p2.add(erase);
			p2.add(eraseAll);
			p2.add(answer);//����
			
			
			optionP.add(p2);
			
			gameP.setLayout(new BorderLayout());
			Panel cdP=new Panel();
			
			
			cd.setSize(850,500);
			cdP.add(cd);
			

			
			gameP.add(cdP,BorderLayout.CENTER);
			gameP.add(optionP,BorderLayout.SOUTH);
			
			chatP.setLayout(new BorderLayout());
			chatP.add(chatA,BorderLayout.CENTER);
			chatP.add(chatF,BorderLayout.SOUTH);
			
			
			listP.setLayout(new BorderLayout());
			listP.add(user,BorderLayout.NORTH);
			listP.add(ul,BorderLayout.CENTER);
			Label rank1=new Label("<���>");

			Panel tP=new Panel(new BorderLayout());//tempPanel
			tP.add(rank1,BorderLayout.NORTH);
			tP.add(rank,BorderLayout.CENTER);
			listP.add(tP,BorderLayout.SOUTH);
			

			
			//�̺�Ʈ �ڵ鷯 -------------------------
			black.addActionListener(new EventHandler());
			orange.addActionListener(new EventHandler());
			green.addActionListener(new EventHandler());
			blue.addActionListener(new EventHandler());
			red.addActionListener(new EventHandler());
			plus.addActionListener(new EventHandler());
			minus.addActionListener(new EventHandler());
			erase.addActionListener(new EventHandler());
			eraseAll.addActionListener(new EventHandler());
			cd.addMouseMotionListener(new EventHandler());
			
			
			//����â
			
			chatF.addActionListener(this);
			gameF.setTitle("Catch Mind");
			gameF.add(la,BorderLayout.NORTH);
			gameF.add(gameP,BorderLayout.CENTER);
			gameF.add(chatP,BorderLayout.SOUTH);
			gameF.add(listP,BorderLayout.EAST);
			gameF.setSize(1000,800);
			gameF.setVisible(true);
			gameF.addWindowListener(new WindowAdapter(){
			    public void windowClosing(WindowEvent e) {
			    	System.exit(0);
			    }

			});
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {

			 if(bt==e.getSource() || tf==e.getSource()){ 
	             loginF.setVisible(false); 
	             loginF.dispose();   
	             name=tf.getText();
	             gameFrame();
	             go(server,name); //������ ����!
	           }
	          
			 if(chatF==e.getSource()){
				 String msg=chatF.getText();
				 try {
					oos.writeObject(msg);
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				 chatF.setText("");
				 
			 }
	         
	     } //actionPerformed
			
	
		}//handler end..
	
	public void go(String server,String name){ 
		try{   
			System.out.println("Client  �Դϴ�...");
			
			s1=new Socket(server,4500);

			
			ClientThread ct=new ClientThread(s1,this);//1���ͽ���
			addThread(ct);
			
			ct.start(); //thread �ѱ��

			oos=new ObjectOutputStream(s1.getOutputStream());
			oos.writeObject(name);
			oos.flush();
			

		} //try end..

		catch(IOException e){   
			System.out.println(e.getMessage());
		} //catch
	} //go end...

	//-------- ����Ʈ�� �̸� �߰��ϱ� ---------
	public void addName(String name) {
		
			ul.add(name);
			nameList.add(name);
			
	}
	//--------------------------------------
	
	
	//---------����Ʈ�� �̸� �����ϱ�-----------
	public void deleteName(String name){
		//deleteName�� ������ ���� <����>name�̱⶧���� name�� ã�� ���ϰ� ����

		String[] li=ul.getItems();
		for (int i = 0; i < li.length; i++) {
			if(li[i].indexOf("<����>")==0)
			{
				li[i]=li[i].substring(4);
			}
		}
		for (int i = 0; i < li.length; i++) {
			if(name.equals(li[i])){
				System.out.println(li[i]+"����");
				ul.remove("<����>"+name);
				break;
			}
			else
			{
				System.out.println("�ٸ�");
				ul.remove(name);
				break;
			}
		}
//		ul.remove(name);
		chatA.append("deleteName:"+name);
	}
	
	public void updateName(String name) {
		if(name.indexOf("<����>")==0)
		{
			ul.remove(name.substring(4));
		}
		else{
		ul.remove(name);
		}
		ul.add(name,0);
		
		
	}
	public void answer(String str) {
		
		an=str;
		answer.setText("����:"+ an);
	}

	public void setCanvas(CanvasDemo cd2) {
		cd=cd2;
		cd.repaint();
		
	}

	//����� �̸� �ֱ�
	public void addScoreNumber(String name){
		rank.add(name+"("+score+"��)");
		
	}
	
	//���� �߰�
	public void addScore(String name){
	}
	
	
	
	//�׸��� �̺�Ʈ
	//�ǽð����� ���������
	class EventHandler extends MouseMotionAdapter implements ActionListener {
		
		
		public void mouseDragged(MouseEvent me) {
			cd.x = me.getX();
			cd.y = me.getY();
			
			cd.repaint();
			
			msg="��ǥ"+cd.x+"!"+cd.y+"!"+cd.w+"!"+cd.h;
			//Ŭ���̾�Ʈ���� ������ �����ش�. ->ServerThread���� ����.
			try {
				oos.writeObject(msg);
				oos.flush();
				//ok
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		public void actionPerformed(ActionEvent ae) {
			Object obj = ae.getSource();

			// System.out.println(btnCol);
			cd.sw = 0;

			String arg = (String)ae.getActionCommand();
			if(arg.equals("black")) {
				cd.color = Color.black;
				
			}else if(arg.equals("green")) {
				cd.color = Color.green;
				
			}else if(arg.equals("orange")) {
				cd.color = Color.orange;
			}else if(arg.equals("red")) {
				cd.color = Color.red;
			}else if(arg.equals("blue")) {
				cd.color = Color.blue;
			}
			
			else if (obj == plus) { // ũ�� ����
				cd.w++;
				cd.h++;
			} else if (obj == minus) {
				if (cd.w > 3) { // �ּҰ� ����
					cd.w--;
					cd.h--;
				}
			} else if (obj == erase) {
				cd.color = Color.WHITE;
			} else if (obj == eraseAll) {
				cd.sw = 1;
				cd.repaint();
			}

			String msg1="/����"+cd.sw+"!"+cd.color;
			try {
				oos.writeObject(msg1);
				oos.flush();
				//ok
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}//action event

		
	}
	
	public void settingRight(){
		answer.setVisible(true);
		erase.setEnabled(true);
		eraseAll.setEnabled(true);
		cd.setEnabled(true);
	}
	
	
	public static void main(String[] args) {
		
		Client client=new Client();
		Client.Handler hd=client.new Handler();
		hd.launchFrame();
	}
	
	public void canvasSetting(CanvasDemo cd1, int x, int y,int w, int h,int sw,String color)
	{
		this.cd=cd1;
    	cd.x=x;
    	cd.y=y;
    	cd.w=w;
    	cd.h=h;
    	cd.sw=sw;
    	
    	if(color.equals("java.awt.Color[r=255,g=200,b=0]")){
    		cd.color=Color.ORANGE;
    	}
    	if(color.equals("java.awt.Color[r=0,g=255,b=0]")){
    		cd.color=Color.GREEN;
    	}
    	if(color.equals("java.awt.Color[r=255,g=0,b=0]")){
    		cd.color=Color.RED;
    	}
    	if(color.equals("java.awt.Color[r=0,g=0,b=255]")){
    		cd.color=Color.BLUE;
    	}
    	if(color.equals("java.awt.Color[r=0,g=0,b=0]")){
    		cd.color=Color.WHITE;
    	}
    	
//    	switch(color)
//    	{
//    	case "java.awt.Color[r=255,g=200,b=0]":
//    		cd.color=Color.ORANGE;
//    		break;
//    	case "java.awt.Color[r=0,g=255,b=0]":
//    		cd.color=Color.GREEN;
//    		break;
//    	case "java.awt.Color[r=255,g=0,b=0]":
//    		cd.color=Color.RED;
//    		break;
//    	case "java.awt.Color[r=0,g=0,b=255]":
//    		cd.color=Color.BLUE;
//    		break;
//    	case "java.awt.Color[r=0,g=0,b=0]":
//    		cd.color=Color.BLACK;
//    		break;
//    	case "java.awt.Color[r=255,g=255,b=255]":
//    		cd.color=Color.WHITE;
//    		break;
//    	}
    	
    	
    	cd.getGraphics().setColor(cd.color);
    	cd.getGraphics().fillOval(x-gameF.getWidth(), y,w,h); // �� ���;
    	
    	cd.repaint();
    	
    	for (int i = 0; i < v.size(); i++) {
			ClientThread ctd=(ClientThread)v.elementAt(i);
			ctd.settingCanvas(cd1, x, y,w,h,sw,color);
		}
	}

	
	public void setNumber(int n) {
		number=n;
		
		try {
			oos.writeObject("@num"+number); ///@num�� �ȸ԰� ����
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	 public CanvasDemo getCd() {
		return cd;
	}

	public void addThread(ClientThread ctd){       
		 v.addElement(ctd);  //vector�� clientThread �߰�~~
	 } //addThread end
	
	public void deleteThread(ClientThread ctd) {
		v.remove(ctd);
	}

	 
}
