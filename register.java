package Client;

import java.io.FileWriter;
import java.util.Scanner;

//ע��
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
				System.out.println("�������벻ͬ������������");
			}
			else
			{
				break;
			}
			
		}
//		in.close();
	}
	
    //�����û�ID
    public  String  main()
    {
    	String str;
    	String result[] = null;
    	Personal_Info info= new Personal_Info();
    	String temp = new String("reg "+ "username "+username+" password "+ password+" email "+ email+info.setData());
    	Message message = new Message();
    	str = message.sendMessage(temp);
    	result = str.split(" ");
		//result[0]Ϊ�û���ID
		if(!result[0].equals("ERROR"))
		{
			
			System.out.println("register success !");
			System.out.println(str);
			//ע��ɹ��������û���֤�鲢��ȡ����˽Կ
			try {
				//������֤�鱣���ڱ��أ���ID��Ϊ�ļ���
				FileWriter writer=new FileWriter("E:\\�������ݰ�ȫ\\Сѧ��\\�ļ�\\" + result[0]+"-CA.txt");
				writer.write(result[1]);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//
			System.out.println("����IDΪ �� "+result[0]);
			return result[0];
		}
		else
		{
			System.out.println("register failed !");
			return  null;
		}
    }
}
