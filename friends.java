package Client;

public class friends {
	private String username;
	private String userID;
	private String IP;
	private String port;
	private boolean Online;    //��־�Ƿ�����
	
	public friends(String username,String userID,String IP,String Port)
	{
		this.username = username;
		this.userID = userID;
		this.IP = IP;
		if(IP.equals("-1"))
		{
			Online = false;
		}
		else
		{
			Online = true;
		}
		port = Port;
	}
	
	public void ToString()
	{
		System.out.println("username:"+username+"userID: "+userID+"IP: "+IP+"PORT: "+port+"Online "+Online);
	}
	//���ڲ鿴���ѵ��ǳ�
	public String get_friendName()
	{
		return username;
	}
	
	//�鿴����ID
	public String get_friendID()
	{
		return userID;
	}
	//�鿴����IP
	public String get_friendIP()
	{
		return IP;
	}
	
	//�鿴���Ѷ˿ں�
	public String get_friendPort()
	{
		return port;
	}
	
	//����״̬�Ƿ�����
	public boolean check_Online()
	{
		return  Online;
	}
	
}
