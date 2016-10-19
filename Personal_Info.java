package Client;

import java.util.Scanner;

//个人信息设置
public class Personal_Info {

	private String username ;
	private String userID;
	private String email;
	private String gender;   //性别
	private String age;
	private String birthday;
	private String constellation;//星座
	private String habits; //兴趣爱好
	private String introduction;  //个性签名
	
	
	//查询用户或好友的信息，
	public void LookupData(String ID)
	{
		String temp = new String("getPersonalInfo "+"userID "+ID);
		Message message = new Message();
		String str = message.sendMessage(temp);
		System.out.println(str);
		String result[] = str.split(" ");
		username = result[1];
		userID = ID;
		email = result[3];
		gender = result[5];
		age = result[7];
		birthday = result[9];
		constellation = result[11];
		temp = "";
		int i = 13;
		for(;i<result.length;i++)
		{
			if(!result[i].equals("introduction"))
			
			{
				temp += result[i];
			}
			else
			{
				break;
			}
		}
		habits = temp;
		temp = "";
		while(i<result.length)
		{
			temp += result[i];
		}
		
		introduction = temp;
		display();
	}
	
	//显示用户或好友的资料
	public void display()
	{
		System.out.println("username      : "+ username);
		System.out.println("userID        : "+ userID);
		System.out.println("email         :  "+ email);
		System.out.println("gender        : "+ gender);
		System.out.println("age           : "+ age);
		System.out.println("birthday      : "+ birthday);
		System.out.println("constellation : "+ constellation);
		System.out.println("habits        : "+ habits);
		System.out.println("introduction     : "+ introduction);
		
	}
	
	//新注册的用户要设置个人信息
	public String setData()
	{
		Scanner in  = new Scanner(System.in);
		System.out.println("请设置是性别：");
		gender = in.nextLine();
		System.out.println("请设置年龄：");
		age = in.nextLine();
		System.out.println("请设置生日：");
		birthday = in.nextLine();
		System.out.println("请设置星座");
		constellation = in.nextLine();
		System.out.println("请设置您的兴趣爱好：");
		habits = in.nextLine();
		System.out.println("请设置您的个性签名：");
		introduction = in.nextLine();
		
		
		String temp = new String(" gender "+gender+" age "+age+" birthday "+birthday+" constellation "+constellation
				+" habits "+habits+" introduction "+introduction);
		System.out.println("setDATA"+temp);
//		in.close();
		return temp;
	}
	
	//编辑个人资料,图形化后，将修改的消息后组成字符串，传给服务器，
	public void editData()
	{
		
		//图形化，
		String temp = new String("changePersonalInfo "+"userID "+userID+ " username "+username+" email "+ email+
				" gender "+gender+" age "+age+" birthday "+birthday+" constellation "+constellation
				+" habits "+habits+" introduction "+introduction);
		//将修改后的资料发送给服务器
		Message message = new Message();
		String str = message.sendMessage(temp);
		System.out.println(str);
		
	}
	
	//修改密码
	public void changePSW()
	{
		String newPSW;
		String oldPSW;
		String userID;
		Scanner in  = new Scanner(System.in);
		
		System.out.println("请输入用户名：");
		userID = in.nextLine();
		System.out.println("请输入旧密码：");
		oldPSW = in.nextLine();
		System.out.println("请输入新密码：");
		newPSW = in.nextLine();
		in.close();
		String temp = new String("changePSW "+"userID "+userID+" oldPSW "+oldPSW+" newPSW "+newPSW);
		Message message = new Message();
		String str = message.sendMessage(temp);
		if(str.equals("PSWCHANGED"))
		{
			System.out.println("密码修改成功！");
		}
		else
		{
			System.out.println("密码修改失败!");
		}
	}
	//查询用户信息,ID为要查询的用户的ID
	public String[] get_Address(String friendID)
	{
		String temp[] = new String[4];
		//向服务器发送请求消息   
		String  askInfo = new String("forAddress userID "+friendID);
		Message message = new Message();
		String str = message.sendMessage(askInfo);
		String result[] = str.split(" ");
			
		if(result[2] != "-1" ||result[3] != "-1")
		{
		    temp[0] = result[0];  //userID
		    temp[1] = result[1];  //username
		    temp[2] = result[2];  //IP
		    temp[3] = result[3];  //port
		 }
		 else
		 {
		    temp = null;
		 }
		      
	        return temp;
	}
		
		
	public String get_userName()
	{
		return username;
	}
	
	public String get_userID()
	{
		return userID;
	
	}
	
	public String get_email()
	{
		return  email;
	}
	
	
}
