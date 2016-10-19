package Client;

import java.io.FileWriter;
import java.util.Scanner;

//注册
public class register {
	private static String username;
	private static String password;
	private static String password1;
	private static String email;

	public register()
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Please input username:");
		username = in.nextLine();
		System.out.println("Please input E_mail:");
		email = in.nextLine();
		while(true)
		{
			System.out.println("Please input password:");
			password = in.nextLine();
			System.out.println("Please input password again:");
			password1 = in.nextLine();
			if(!password.equals(password1))
			{
				System.out.println("两次密码不同，请重新输入");
			}
			else
			{
				break;
			}
			
		}
//		in.close();
	}
	
    //返回用户ID
    public  String  main()
    {
    	String str;
    	String result[] = null;
    	Personal_Info info= new Personal_Info();
    	String temp = new String("reg "+ "username "+username+" password "+ password+" email "+ email+info.setData());
    	Message message = new Message();
    	str = message.sendMessage(temp);
    	result = str.split(" ");
		//result[0]为用户的ID
		if(!result[0].equals("ERROR"))
		{
			
			System.out.println("register success !");
			System.out.println(str);
			//注册成功，保存用户的证书并提取出公私钥
			try {
				//将数字证书保存在本地，用ID作为文件名
				FileWriter writer=new FileWriter("E:\\数字内容安全\\小学期\\文件\\" + result[0]+"-CA.txt");
				writer.write(result[1]);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			System.out.println("您的ID为 ： "+result[0]);
			return result[0];
		}
		else
		{
			System.out.println("register failed !");
			return  null;
		}
    }
}
