package Client;


import java.util.Random;
import java.util.Scanner;

public class Client {
	
    private static boolean register;
    private static  String userID;		//用户自己的ID
    //用于 固定p2p聊天的端口号
    private static int port;
    private static int choice = 0;
    //保存用户自己的个人信息
    private static Personal_Info info = new Personal_Info();
 

  //返回自己的ID
    public String getID()
    {
    	return userID;
    }
    
	
	
	public static void main(String[] args)
	{
		 System.out.println("1:登陆     2:注册");
		 int Option;
		 Scanner input = new Scanner(System.in);
		 Option = input.nextInt();
		 
		 //没有账号，进行注册
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
			//得到端口号，后续的操作都通过这个端口号来进行传输
			 userID = login.main() ;
			 port = login.getPort();
			 //登陆成功
			 if(userID != null)
			 {
				
				 System.out.println("******MENU******");
				 System.out.println("1.Online-chatting. 2.file-trans. 3.Managelist. 4.offline");
				 while(true)
				 {
					 choice = input.nextInt();
					 
					 //进行在线聊天
					 if(choice == 1)
					 {
						 //为单独的线程，用于监听是否有好友发来消息
						 Point_S chat_S = new Point_S(port);
						 
						 //主动与好友聊天
						 OnlineChatting chat_F = new OnlineChatting(userID);
						 //port为固定的p2p的端口号
						 chat_F.Point_C(port);
					 }
					 // 进行好友管理
					 if(choice == 3)
					 {
						 ManageList manageList = new ManageList();
						 //图形化实现，实现好友的添加，删除，查找等等
					 }
					 //离线
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
