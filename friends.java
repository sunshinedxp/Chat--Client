package Client;

public class friends {
	private String username;
	private String userID;
	private String IP;
	private String port;
	private boolean Online;    //标志是否在线
	
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
	//用于查看好友的昵称
	public String get_friendName()
	{
		return username;
	}
	
	//查看好友ID
	public String get_friendID()
	{
		return userID;
	}
	//查看好友IP
	public String get_friendIP()
	{
		return IP;
	}
	
	//查看好友端口号
	public String get_friendPort()
	{
		return port;
	}
	
	//返回状态是否在线
	public boolean check_Online()
	{
		return  Online;
	}
	
}
