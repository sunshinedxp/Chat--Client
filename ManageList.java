package Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//好友列表管理，添加好友，删除好友
class ManageList 
{
	
	private String[] Requestlist;
	private ArrayList<friends> list;
	Personal_Info info = new Personal_Info();
    Message message = new Message();
	public ManageList()
	{
		list = new ArrayList<friends>();
	}
	
	
	//查找陌生人按昵称
	public ArrayList<friends> searchUserByName()
	{
			ArrayList<friends> searchList= new ArrayList<friends>();
			String name = null;
			int searchNum = 0;
			System.out.println("请输入要搜索的用户的昵称");
			Scanner in = new Scanner(System.in);
			in.close();
			String temp = new String("searchUser "+ "searchID "+"-1"+"username "+name);
		    String str = message.sendMessage(temp);
			String result[] = str.split(" ");
			
			searchNum = Integer.parseInt(result[0]);
			System.out.println("查询到的好友如下：");
			if(searchNum != 0)
			{
				for(int i = 0;i<searchNum;i++ )
				{
					friends friend = new friends(result[2],result[1],"-1","-1");
					searchList.add(friend);
					System.out.println(result);
				}
			}
			return searchList;
	}
		
		
	//查找陌生人按ID查询,返回查找到的好友，可以调用search_friendByID_Local(ID)来看查到的用户是否已经是你的好友
	public ArrayList<friends> searchUserByID()
	{
		ArrayList<friends> searchList= new ArrayList<friends>();
		String ID = null;
		int searchNum = 0;
		System.out.println("请输入要搜索的用户的ID");
		Scanner in = new Scanner(System.in);
		ID = in.nextLine();
		in.close();
		String temp = new String("searchUser "+ "searchID "+ID+"username -1");
	    String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		
		searchNum = Integer.parseInt(result[0]);
		System.out.println("查询到的好友如下：");
		if(searchNum != 0)
		{
			for(int i = 0;i<searchNum;i++ )
			{
				//result[2]表示username,result[1]表示userID
				friends friend = new friends(result[2],result[1],"-1","-1");
				searchList.add(friend);
				System.out.println(result);
			}
		}
		return searchList;
	}
	
	
	
	//请求添加好友
	public void AddFriendRequest(String askID)
	{
	    String temp = new String("addFriendQuest "+ "userID "+info.get_userID()+" askID "+ askID);
	    String str = message.sendMessage(temp);
		System.out.println(str);
       
	}
	
	//处理好友请求，是否同意加为好友，同意返回1，否则返回0
	public void  AddFriend(String askID,String userID)
	{
		String ifAgree;
		System.out.println(askID+" 想添加你为好友 ");
		System.out.println("同意请输入1，不同意输入0");
		Scanner in = new Scanner(System.in);
		ifAgree = in.next();
		
		String temp = new String("addFriend "+ "questID "+askID+" uerID "+info.get_userID()+" ifAgree "+ ifAgree);
	    String str = message.sendMessage(temp);
	    String result[] = null;
	    result = str.split(" ");
       if(result[0].equals("Add"))
       {
    	   System.out.println("Add OK");
    	   //更新好友列表
    	   Update_list(result[2],askID,result[3],result[4],"Add");
       }
       else
       {
    	   System.out.println("Reject OK");
       }
      in.close();
	}
	
	//删除好友
	public void DeleteFriend(String deleteID)
	{
		String temp = new String("deleteFriend "+ "uerID "+info.get_userID()+" deleteID "+deleteID);
	    String str = message.sendMessage(temp);
	    String result[] = null;
		
       result = str.split(" ");
       if(result[0].equals("Delete"))
       {
    	   System.out.println("Delete OK");
    	   //更新好友列表
    	   Update_list(result[2],deleteID,result[3],result[4],"delete");
       }
       else
       {
    	   System.out.println("Server Error");
       }
		
	}
	
	//更新好友状态，从服务器得到最新的好友列表
	public void Update_List()
	{
		String temp = new String("getFriends "+"userID "+info.get_userID());
		String str = message.sendMessage(temp);
		String[] result = str.split(" ");
		int num = Integer.parseInt(result[0]);
		//重新更新好友列表 
		for(int i=0;i<num;i++)
		{
			friends friend = new friends(result[i*4+1],result[i*4+5+2],result[i*4+5+3],result[i*4+5+4]);
			list.add(friend);
			Collections.sort(list, new Sort());

		}
	}
	
	
	//在本地更新好友列表，添加好友或者删除好友
	public void Update_list(String username,String userID,String IP,String Port,String operate)
	{
		//添加好友
		if(operate.equals("Add"))
		{
			friends friend = new friends(username,userID,IP,Port);
			list.add(friend);
		}
		//删除好友
		else if(operate.equals("delete"))
		{
			friends friend = new friends(username,userID,IP,Port);
			list.remove(list.indexOf(friend));
		}
		
	}
	
	//显示好友列表
	public void showList()
	{
		for(friends friend:list)
		{
			friend.ToString();
		}
	}
	
	//按ID在本地搜索好友
	public friends search_friendByID_Local(String ID)
	{
		
		for(friends friend:list)
		{
			if(friend.get_friendID().equals(ID))
				return friend;
		}
		return null;
	}
	
	//按昵称在搜索好友，可以有多个昵称相同的好友
	public ArrayList<friends> search_friendByUsername_local(String username)
	{
		ArrayList<friends> temp = new ArrayList<friends>();
		
		for(friends  friend :list)
		{
			if(friend.get_friendName().equals(username))
			{
				temp.add(friend);
			}
		}
		return temp;
	}
	
	//保存好友列表，在登陆成功时服务器返回好友列表
	public void save_list(String[] result)
	{
		int number = 0 ;
		//先保存好友请求列表
		if(!result[3].equals("0"))
		{
			number  = Integer.parseInt(result[3]);
			for(int i = 1;i <= number;i++)
			{
				Requestlist[i] = result[i+3];
			}
		}
		int friendNumber = Integer.parseInt(result[4+Integer.parseInt(result[3])]);
		//保存好友列表，显示在线与离线好友
		for(int i = 0;i < friendNumber;i++)
		{
			friends friend = new friends(result[i*4+5+number],result[i*4+5+number+1],result[i*4+5+number+2],result[i*4+5+number+3]);
			list.add(friend);
			Collections.sort(list, new Sort());
		}
	
	}
}


//按IP升序排序
class Sort implements Comparator<Object>{   
	public int compare(Object obj1,Object obj2)
	{   
		String IP1=(String)obj1;   
		String IP2=(String)obj2;   
		
		if(Integer.parseInt(IP1) > Integer.parseInt(IP2) )
		{
			return 1;  
		}
		else
		{
			return 0;
		}
      
}   
}  