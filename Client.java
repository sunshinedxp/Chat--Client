package Client;


import java.util.Random;
import java.util.Scanner;

public class Client {
	
    private static boolean register;
    private static  String userID;		//�û��Լ���ID
    //���� �̶�p2p����Ķ˿ں�
    private static int port;
    private static int choice = 0;
    //�����û��Լ��ĸ�����Ϣ
    private static Personal_Info info = new Personal_Info();
 

  //�����Լ���ID
    public String getID()
    {
    	return userID;
    }
    
	
	
	public static void main(String[] args)
	{
		 System.out.println("1:��½     2:ע��");
		 int Option;
		 Scanner input = new Scanner(System.in);
		 Option = input.nextInt();
		 
		 //û���˺ţ�����ע��
		 if(Option == 2)
		 {
			 register registerApply = new register();
			String result  = registerApply.main();

			if(result != null)
			{
				register = true;
			}
				
		 }
		 
		 if(Option == 1 || register == true)											
		 {
			 Login login = new Login();
			//�õ��˿ںţ������Ĳ�����ͨ������˿ں������д���
			 userID = login.main() ;
			 port = login.getPort();
			 //��½�ɹ�
			 if(userID != null)
			 {
				
				 System.out.println("******MENU******");
				 System.out.println("1.Online-chatting. 2.file-trans. 3.Managelist. 4.offline");
				 while(true)
				 {
					 choice = input.nextInt();
					 
					 //������������
					 if(choice == 1)
					 {
						 //Ϊ�������̣߳����ڼ����Ƿ��к��ѷ�����Ϣ
						 Point_S chat_S = new Point_S(port);
						 
						 //�������������
						 OnlineChatting chat_F = new OnlineChatting(userID);
						 //portΪ�̶���p2p�Ķ˿ں�
						 chat_F.Point_C(port);
					 }
					 // ���к��ѹ���
					 if(choice == 3)
					 {
						 ManageList manageList = new ManageList();
						 //ͼ�λ�ʵ�֣�ʵ�ֺ��ѵ���ӣ�ɾ�������ҵȵ�
					 }
					 //����
					 if(choice == 4)
					 {
						String temp = new String("offline "+ "userID "+userID);
						Message message = new Message();
						String str = message.sendMessage(temp);
						System.out.println(str);
						 break;
					 }
					 
				 }
				 
			 }
		 }
		 input.close();
	 }
}
