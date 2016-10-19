package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

  	class ServerThread implements Runnable
	{
		private static String Ks;                           //�Ự��Կ
		private static DES des;

		Personal_Info personal_Info = new Personal_Info();
		private Socket point_s = null;  
	    public ServerThread(Socket client)
	    {  
	        this.point_s = client;  
	    }  
	 
	    public void run() {  
	        try{  
	        	//��ȡ��������
		        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	            //��ȡSocket�����������������ѷ�������  
	            PrintStream out = new PrintStream(point_s.getOutputStream());  
	            
	            //����һ���߳�ר�������������Ѷ˷�������Ϣ����ӡ���
		        new Thread(new recv_S_MessageThread(point_s)).start();
		        
	            boolean flag =true;  
	            System.out.print("server:��������Ϣ���س������ͣ���Bye��������");
	            while(flag)
	            {  
	                  //��Ϣ���͸��Է�
	            	String str = input.readLine();
	            	String message = new String(personal_Info.get_userName()+" :"+str);
		        	out.print(des.encrypt(message.getBytes(), Ks).toString()) ;           //�ûỰ��Կ������Ϣ 
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
	    

		//ר�������������Ѷ˷�������Ϣ������
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
					//��ȡSocket�����������������մӷ���˷��͹���������
			        buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
					while(true)
					{
						String temp = buf.readLine();
						String result[] = temp.split(" ");
						//�յ��Է����صĻỰ��Կ��������Ϣ���õ��Ự��Կ
						if(result[0].equals("Key_Agreement"))
						{
							//�õ� �Ự��Կ
							Ks = des.get_Key();
							//�öԷ��Ĺ�Կ���ܻỰ��Կ
							String str = new String("Key_Agreement "+rsa.encryptByPublicKey(Ks, keyAgreement.askPK(result[1])));
							//��ȡSocket�����������������ѷ�������  
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
							//������� 
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


