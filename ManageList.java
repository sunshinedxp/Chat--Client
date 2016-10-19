package Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//�����б������Ӻ��ѣ�ɾ������
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
	
	
	//����İ���˰��ǳ�
	public ArrayList<friends> searchUserByName()
	{
			ArrayList<friends> searchList= new ArrayList<friends>();
			String name = null;
			int searchNum = 0;
			System.out.println("������Ҫ�������û����ǳ�");
			Scanner in = new Scanner(System.in);
			in.close();
			String temp = new String("searchUser "+ "searchID "+"-1"+"username "+name);
		    String str = message.sendMessage(temp);
			String result[] = str.split(" ");
			
			searchNum = Integer.parseInt(result[0]);
			System.out.println("��ѯ���ĺ������£�");
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
		
		
	//����İ���˰�ID��ѯ,���ز��ҵ��ĺ��ѣ����Ե���search_friendByID_Local(ID)�����鵽���û��Ƿ��Ѿ�����ĺ���
	public ArrayList<friends> searchUserByID()
	{
		ArrayList<friends> searchList= new ArrayList<friends>();
		String ID = null;
		int searchNum = 0;
		System.out.println("������Ҫ�������û���ID");
		Scanner in = new Scanner(System.in);
		ID = in.nextLine();
		in.close();
		String temp = new String("searchUser "+ "searchID "+ID+"username -1");
	    String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		
		searchNum = Integer.parseInt(result[0]);
		System.out.println("��ѯ���ĺ������£�");
		if(searchNum != 0)
		{
			for(int i = 0;i<searchNum;i++ )
			{
				//result[2]��ʾusername,result[1]��ʾuserID
				friends friend = new friends(result[2],result[1],"-1","-1");
				searchList.add(friend);
				System.out.println(result);
			}
		}
		return searchList;
	}
	
	
	
	//������Ӻ���
	public void AddFriendRequest(String askID)
	{
	    String temp = new String("addFriendQuest "+ "userID "+info.get_userID()+" askID "+ askID);
	    String str = message.sendMessage(temp);
		System.out.println(str);
       
	}
	
	//������������Ƿ�ͬ���Ϊ���ѣ�ͬ�ⷵ��1�����򷵻�0
	public void  AddFriend(String askID,String userID)
	{
		String ifAgree;
		System.out.println(askID+" �������Ϊ���� ");
		System.out.println("ͬ��������1����ͬ������0");
		Scanner in = new Scanner(System.in);
		ifAgree = in.next();
		
		String temp = new String("addFriend "+ "questID "+askID+" uerID "+info.get_userID()+" ifAgree "+ ifAgree);
	    String str = message.sendMessage(temp);
	    String result[] = null;
	    result = str.split(" ");
       if(result[0].equals("Add"))
       {
    	   System.out.println("Add OK");
    	   //���º����б�
    	   Update_list(result[2],askID,result[3],result[4],"Add");
       }
       else
       {
    	   System.out.println("Reject OK");
       }
      in.close();
	}
	
	//ɾ������
	public void DeleteFriend(String deleteID)
	{
		String temp = new String("deleteFriend "+ "uerID "+info.get_userID()+" deleteID "+deleteID);
	    String str = message.sendMessage(temp);
	    String result[] = null;
		
       result = str.split(" ");
       if(result[0].equals("Delete"))
       {
    	   System.out.println("Delete OK");
    	   //���º����б�
    	   Update_list(result[2],deleteID,result[3],result[4],"delete");
       }
       else
       {
    	   System.out.println("Server Error");
       }
		
	}
	
	//���º���״̬���ӷ������õ����µĺ����б�
	public void Update_List()
	{
		String temp = new String("getFriends "+"userID "+info.get_userID());
		String str = message.sendMessage(temp);
		String[] result = str.split(" ");
		int num = Integer.parseInt(result[0]);
		//���¸��º����б� 
		for(int i=0;i<num;i++)
		{
			friends friend = new friends(result[i*4+1],result[i*4+5+2],result[i*4+5+3],result[i*4+5+4]);
			list.add(friend);
			Collections.sort(list, new Sort());

		}
	}
	
	
	//�ڱ��ظ��º����б���Ӻ��ѻ���ɾ������
	public void Update_list(String username,String userID,String IP,String Port,String operate)
	{
		//��Ӻ���
		if(operate.equals("Add"))
		{
			friends friend = new friends(username,userID,IP,Port);
			list.add(friend);
		}
		//ɾ������
		else if(operate.equals("delete"))
		{
			friends friend = new friends(username,userID,IP,Port);
			list.remove(list.indexOf(friend));
		}
		
	}
	
	//��ʾ�����б�
	public void showList()
	{
		for(friends friend:list)
		{
			friend.ToString();
		}
	}
	
	//��ID�ڱ�����������
	public friends search_friendByID_Local(String ID)
	{
		
		for(friends friend:list)
		{
			if(friend.get_friendID().equals(ID))
				return friend;
		}
		return null;
	}
	
	//���ǳ����������ѣ������ж���ǳ���ͬ�ĺ���
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
	
	//��������б��ڵ�½�ɹ�ʱ���������غ����б�
	public void save_list(String[] result)
	{
		int number = 0 ;
		//�ȱ�����������б�
		if(!result[3].equals("0"))
		{
			number  = Integer.parseInt(result[3]);
			for(int i = 1;i <= number;i++)
			{
				Requestlist[i] = result[i+3];
			}
		}
		int friendNumber = Integer.parseInt(result[4+Integer.parseInt(result[3])]);
		//��������б���ʾ���������ߺ���
		for(int i = 0;i < friendNumber;i++)
		{
			friends friend = new friends(result[i*4+5+number],result[i*4+5+number+1],result[i*4+5+number+2],result[i*4+5+number+3]);
			list.add(friend);
			Collections.sort(list, new Sort());
		}
	
	}
}


//��IP��������
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