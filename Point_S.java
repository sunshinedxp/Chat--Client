package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

  	class ServerThread implements Runnable
	{
		private static String Ks;                           //会话密钥
		private static DES des;

		Personal_Info personal_Info = new Personal_Info();
		private Socket point_s = null;  
	    public ServerThread(Socket client)
	    {  
	        this.point_s = client;  
	    }  
	 
	    public void run() {  
	        try{  
	        	//获取键盘输入
		        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	            //获取Socket的输出流，用来向好友发送数据  
	            PrintStream out = new PrintStream(point_s.getOutputStream());  
	            
	            //开启一个线程专门用来监听好友端发来的消息并打印输出
		        new Thread(new recv_S_MessageThread(point_s)).start();
		        
	            boolean flag =true;  
	            System.out.print("server:请输入消息，回车即发送，以Bye结束聊天");
	            while(flag)
	            {  
	                  //消息发送给对方
	            	String str = input.readLine();
	            	String message = new String(personal_Info.get_userName()+" :"+str);
		        	out.print(des.encrypt(message.getBytes(), Ks).toString()) ;           //用会话密钥加密消息 
		        	out.flush();
		        	if(str.equals("Bye"))
	  	        		break;
	            }  
	            input.close();
	            out.close();  
	            point_s.close();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	    }  
	    

		//专门用来监听好友端发来的消息并处理
		class recv_S_MessageThread implements Runnable
		{
			private  Key_agreement keyAgreement = new Key_agreement() ;
			private RSAUtils rsa = new RSAUtils();
			private Socket client = null; 
			private BufferedReader buf;
			
			public recv_S_MessageThread(Socket client)
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
							//得到 会话密钥
							Ks = des.get_Key();
							//用对方的公钥加密会话密钥
							String str = new String("Key_Agreement "+rsa.encryptByPublicKey(Ks, keyAgreement.askPK(result[1])));
							//获取Socket的输出流，用来向好友发送数据  
				            PrintStream out = new PrintStream(point_s.getOutputStream());  
				            out.println(str);
				            out.flush();
				            out.close();
						}
						else if(temp.equals("Bye"))
						{
							break;
						}
						else if(temp.equals("fileTrans"))
						{
							file_trans filetrans = new file_trans();
							filetrans.receFile(point_s,Ks);	
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


