package Client;

import java.util.Scanner;

//������Ϣ����
public class Personal_Info {

	private String username ;
	private String userID;
	private String email;
	private String gender;   //�Ա�
	private String age;
	private String birthday;
	private String constellation;//����
	private String habits; //��Ȥ����
	private String introduction;  //����ǩ��
	
	
	//��ѯ�û�����ѵ���Ϣ��
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
	
	//��ʾ�û�����ѵ�����
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
	
	//��ע����û�Ҫ���ø�����Ϣ
	public String setData()
	{
		Scanner in  = new Scanner(System.in);
		System.out.println("���������Ա�");
		gender = in.nextLine();
		System.out.println("���������䣺");
		age = in.nextLine();
		System.out.println("���������գ�");
		birthday = in.nextLine();
		System.out.println("����������");
		constellation = in.nextLine();
		System.out.println("������������Ȥ���ã�");
		habits = in.nextLine();
		System.out.println("���������ĸ���ǩ����");
		introduction = in.nextLine();
		
		
		String temp = new String(" gender "+gender+" age "+age+" birthday "+birthday+" constellation "+constellation
				+" habits "+habits+" introduction "+introduction);
		System.out.println("setDATA"+temp);
//		in.close();
		return temp;
	}
	
	//�༭��������,ͼ�λ��󣬽��޸ĵ���Ϣ������ַ�����������������
	public void editData()
	{
		
		//ͼ�λ���
		String temp = new String("changePersonalInfo "+"userID "+userID+ " username "+username+" email "+ email+
				" gender "+gender+" age "+age+" birthday "+birthday+" constellation "+constellation
				+" habits "+habits+" introduction "+introduction);
		//���޸ĺ�����Ϸ��͸�������
		Message message = new Message();
		String str = message.sendMessage(temp);
		System.out.println(str);
		
	}
	
	//�޸�����
	public void changePSW()
	{
		String newPSW;
		String oldPSW;
		String userID;
		Scanner in  = new Scanner(System.in);
		
		System.out.println("�������û�����");
		userID = in.nextLine();
		System.out.println("����������룺");
		oldPSW = in.nextLine();
		System.out.println("�����������룺");
		newPSW = in.nextLine();
		in.close();
		String temp = new String("changePSW "+"userID "+userID+" oldPSW "+oldPSW+" newPSW "+newPSW);
		Message message = new Message();
		String str = message.sendMessage(temp);
		if(str.equals("PSWCHANGED"))
		{
			System.out.println("�����޸ĳɹ���");
		}
		else
		{
			System.out.println("�����޸�ʧ��!");
		}
	}
	//��ѯ�û���Ϣ,IDΪҪ��ѯ���û���ID
	public String[] get_Address(String friendID)
	{
		String temp[] = new String[4];
		//�����������������Ϣ   
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
