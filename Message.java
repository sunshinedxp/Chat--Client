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

//����Ϣ���д������ܽ���,������Ϣ
public class Message {

	//������Ϣ��������
	public String sendMessage(String temp)
	{
	    String str = null;
		try {
			 Socket client = new Socket("139.129.30.243", 8888);
			 //��ȡSocket��������������������ݵ������
		     PrintStream out = new PrintStream(client.getOutputStream());
		     out.print(temp);
		     out.flush();
		     //��ȡSocket�����������������մӷ���˷��͹���������
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
	
	//����������Ϣ��ÿһ��String[]�������һ�����ߺ��ѵ�������Ϣ����һ��������Ǻ��ѵ�ID
	public ArrayList<String[]> getOfflineText(String userID,int port)
	{
		int num = 0;
		ArrayList<String[]> list = new ArrayList<String[]>();
		String temp = new String("getOfflineText "+"userID "+userID);
		Message message = new Message();
		String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		//�õ�����������Ϣ�ĺ��ѵĸ�����ÿ�����ѵ����� ��Ϣ����Ϊһ���ַ������飬��һ��Ϊ���ѵ�ID������Ϊ��Ϣ
		num = Integer.parseInt(result[0]);
		for(int i = 0;i < num;i++)
		{
			for(int j = 1;j < result.length;j++)
			{
				//��Ϣ�Ŀ�ͷ
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
