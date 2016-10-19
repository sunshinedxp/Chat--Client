 package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

//�������죬P2P

public class OnlineChatting {
	private static Key_agreement keyAgreement ;
	private static String Ks;                         				  //�Ự��Կ
	private static DES des;
	private boolean  fileTrans = false;              					 //�ļ�����ı�ǣ���fileTrans
	private static String path;                   						 //�ļ�·��,ȫ�ֱ����������ı�����������ֱ�Ӽ����ļ����õ��ļ���
	private static String friendInfo[] = new String[4];
	private static Personal_Info personal_Info = new Personal_Info();	//�����û��ĸ�����Ϣ
	private static boolean offlineFileTrans;                            //ȫ�ֱ�����ʶ�Ƿ�������ߴ��䣬����һ��button���ı�����ֵ        
	Message message = new Message();
	
	//IDΪ�û��Լ���ID
	public OnlineChatting(String ID)
	{
		//�õ� �û��Լ�����Ϣ
		personal_Info.LookupData(ID);
		//�õ�����֤�飬���ں���Ự��ԿЭ��
		keyAgreement.get_CA(ID);
	}
	
	
	//portΪ�Լ��Ķ˿ںţ��̶�p2p����
	public  void Point_C(int port)
	{
		file_trans filetrans = new file_trans(friendInfo[0]);
		Client client = new Client();
		
		//�õ����ѵĵ�ַ��ͼ�λ����Ե���ManageList����showList()���������Լ��ĺ����б�ѡ���Լ�Ҫ��������ĺ���
		System.out.println("��������ѵ�ID");
		Scanner in = new Scanner(System.in);
		String friendID = in.nextLine();
		
//		Client ask_Address = new Client();
		friendInfo = personal_Info.get_Address(friendID);
//		���Ҫ���з��������ļ�
		if(offlineFileTrans == true)
		{
			filetrans.sendOfflineFile(path,personal_Info.get_userID());
		}
		//����û�����������������Ϣ
		if((friendInfo[2].equals("-1")) || friendInfo[3].equals("-1"))
		{
			System.out.println("���Ѳ����ߣ��Ƿ�Ҫ����������Ϣ��1.����������Ϣ��2.����");
			
			int flag = in.nextInt();
			if(flag == 1)
			{
				//��ȡ��������
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
		//��������
		else{
			try {
				
				 Socket point_c = new Socket();
				 point_c.bind(new InetSocketAddress("localhost", port));
				 point_c.connect(new InetSocketAddress(friendInfo[2],Integer.parseInt(friendInfo[3])));
				//��ȡ��������
		        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		        //��ȡSocket��������������������ݵ������
		        PrintStream out = new PrintStream(point_c.getOutputStream());
		        
		        //��ͨ��֮ǰ����Э�̻Ự��Կ
		        String temp = new String("Key_Agreement "+personal_Info.get_userID()+" "+friendInfo[0]);
		        out.print(temp);
		        out.flush();
		        
		        //����һ���߳�ר�������������Ѷ˷�������Ϣ��������ʼΪ��ԿЭ�̣�֮��Ϊ��������ļ�����
		        new Thread(new recv_MessageThread(point_c)).start();
		        
		        //���Բ��ϵĸ����ѷ�����Ϣ
		        boolean flag = true;
		        System.out.print("��������Ϣ���س������ͣ���Bye�������� ");
		        while(flag || fileTrans == true)
		        {	  
		        	//�����ļ�
			        if(fileTrans == true)
			        {
			        	temp = new String("fileTrans");
			        	out.print(temp);
			        	out.flush();
			        	filetrans.sendFile(path, point_c,Ks);
			        }
			        else
			        {
			        	//��Ϣ���͸��Է�
			        	String str = input.readLine();
			        	String text = new String(personal_Info.get_userName()+" :"+str);
			        	out.print(des.encrypt(text.getBytes(), Ks).toString()) ;           //�ûỰ��Կ������Ϣ 
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
	
	

	//ר�������������Ѷ˷�������Ϣ������
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
				//��ȡSocket�����������������մӷ���˷��͹���������
		        buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
				while(true)
				{
					String temp = buf.readLine();
					String result[] = temp.split(" ");
					//�յ��Է����صĻỰ��Կ��������Ϣ���õ��Ự��Կ
					if(result[0].equals("Key_Agreement"))
					{
						RSAUtils rsa = new RSAUtils();
						//���Լ���˽Կ���� ���õ��Ự��Կ
						Ks = rsa.decryptByPrivateKey(result[1], keyAgreement.get_PrivateKey());
					}
					if(temp.equals("Bye"))
					{
						break;
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
