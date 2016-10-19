 package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

//在线聊天，P2P

public class OnlineChatting {
	private static Key_agreement keyAgreement ;
	private static String Ks;                         				  //会话密钥
	private static DES des;
	private boolean  fileTrans = false;              					 //文件传输的标记，方fileTrans
	private static String path;                   						 //文件路径,全局变量，监听文本框的输入或者直接检索文件来得到文件。
	private static String friendInfo[] = new String[4];
	private static Personal_Info personal_Info = new Personal_Info();	//保存用户的个人信息
	private static boolean offlineFileTrans;                            //全局变量标识是否进行离线传输，监听一个button来改变他的值        
	Message message = new Message();
	
	//ID为用户自己的ID
	public OnlineChatting(String ID)
	{
		//得到 用户自己的信息
		personal_Info.LookupData(ID);
		//得到数字证书，用于后面会话密钥协商
		keyAgreement.get_CA(ID);
	}
	
	
	//port为自己的端口号，固定p2p聊天
	public  void Point_C(int port)
	{
		file_trans filetrans = new file_trans(friendInfo[0]);
		Client client = new Client();
		
		//得到好友的地址，图形化可以调用ManageList类中showList()方法看到自己的好友列表，选择自己要进行聊天的好友
		System.out.println("请输入好友的ID");
		Scanner in = new Scanner(System.in);
		String friendID = in.nextLine();
		
//		Client ask_Address = new Client();
		friendInfo = personal_Info.get_Address(friendID);
//		如果要进行发送离线文件
		if(offlineFileTrans == true)
		{
			filetrans.sendOfflineFile(path,personal_Info.get_userID());
		}
		//如果用户不在线则发送离线信息
		if((friendInfo[2].equals("-1")) || friendInfo[3].equals("-1"))
		{
			System.out.println("好友不在线，是否要继续发送消息：1.发送离线消息。2.返回");
			
			int flag = in.nextInt();
			if(flag == 1)
			{
				//获取键盘输入
		        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		        try {
					String text = input.readLine();
					String temp = new String("offineMessage "+"userID "+client.getID()+" revID "+friendID+" text "+text);
					
					String str = message.sendMessage( temp);
					System.out.println(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}//if
		//好友在线
		else{
			try {
				
				 Socket point_c = new Socket();
				 point_c.bind(new InetSocketAddress("localhost", port));
				 point_c.connect(new InetSocketAddress(friendInfo[2],Integer.parseInt(friendInfo[3])));
				//获取键盘输入
		        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		        //获取Socket的输出流，用来发送数据到服务端
		        PrintStream out = new PrintStream(point_c.getOutputStream());
		        
		        //在通话之前首先协商会话密钥
		        String temp = new String("Key_Agreement "+personal_Info.get_userID()+" "+friendInfo[0]);
		        out.print(temp);
		        out.flush();
		        
		        //开启一个线程专门用来监听好友端发来的消息并处理，开始为密钥协商，之后为聊天或者文件传输
		        new Thread(new recv_MessageThread(point_c)).start();
		        
		        //可以不断的给好友发送消息
		        boolean flag = true;
		        System.out.print("请输入消息，回车即发送，以Bye结束聊天 ");
		        while(flag || fileTrans == true)
		        {	  
		        	//传输文件
			        if(fileTrans == true)
			        {
			        	temp = new String("fileTrans");
			        	out.print(temp);
			        	out.flush();
			        	filetrans.sendFile(path, point_c,Ks);
			        }
			        else
			        {
			        	//消息发送给对方
			        	String str = input.readLine();
			        	String text = new String(personal_Info.get_userName()+" :"+str);
			        	out.print(des.encrypt(text.getBytes(), Ks).toString()) ;           //用会话密钥加密消息 
			        	out.flush();
			        	if(str.equals("Bye"))
			        		break;
			        }
		        	
		        }//while
		        
		        out.close();
				point_c.close();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
//			in.close();
		}//else
	}//OnlineChatting
	
	

	//专门用来监听好友端发来的消息并处理
	class recv_MessageThread implements Runnable
	{
		private Socket client = null; 
		private BufferedReader buf;
		
		public recv_MessageThread(Socket client)
		{
			this.client = client;
		}
		
		public void run() 
		{  
			try{
				//获取Socket的输入流，用来接收从服务端发送过来的数据
		        buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
				while(true)
				{
					String temp = buf.readLine();
					String result[] = temp.split(" ");
					//收到对方返回的会话密钥，解密消息，得到会话密钥
					if(result[0].equals("Key_Agreement"))
					{
						RSAUtils rsa = new RSAUtils();
						//用自己的私钥解密 ，得到会话密钥
						Ks = rsa.decryptByPrivateKey(result[1], keyAgreement.get_PrivateKey());
					}
					if(temp.equals("Bye"))
					{
						break;
					}
					else
					{
						//解密输出 
						System.out.println(des.decrypt(temp.getBytes(), Ks).toString());
					}
				}	
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
