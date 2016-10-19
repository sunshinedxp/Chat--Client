package Client;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

//对消息进行处理，加密解密,发送消息
public class Message {

	//发送消息到服务器
	public String sendMessage(String temp)
	{
	    String str = null;
		try {
			 Socket client = new Socket("139.129.30.243", 8888);
			 //获取Socket的输出流，用来发送数据到服务端
		     PrintStream out = new PrintStream(client.getOutputStream());
		     out.print(temp);
		     out.flush();
		     //获取Socket的输入流，用来接收从服务端发送过来的数据
		     BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
		     str = buf.readLine();
		     client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	
	}
	
	//接收离线消息，每一个String[]保存的是一个离线好友的离线消息，第一个保存的是好友的ID
	public ArrayList<String[]> getOfflineText(String userID,int port)
	{
		int num = 0;
		ArrayList<String[]> list = new ArrayList<String[]>();
		String temp = new String("getOfflineText "+"userID "+userID);
		Message message = new Message();
		String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		//得到发送离线消息的好友的个数，每个好友的离线 消息保存为一个字符串数组，第一个为好友的ID，后面为消息
		num = Integer.parseInt(result[0]);
		for(int i = 0;i < num;i++)
		{
			for(int j = 1;j < result.length;j++)
			{
				//消息的开头
				if(result[j].equals("*****"))
				{
					List<String> tempList = new ArrayList<String>();
					j++;
					while(!result[j].equals("#####"))
					{
						tempList.add(result[j]);
						j++;
					}
					String str1[] = tempList.toArray(new String[tempList.size()]);
					list.add(str1);
				}
			}
		}
		return list;	
	}
}
