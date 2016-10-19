package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

//文件传输，会话密钥加解密
public class file_trans{

	private  DataOutputStream dos; //将文件的内容写入到发送给对方的输入
	private DataInputStream dis;   //得到输入流
	private  DES des;
	String str[] = new String[4];
	public file_trans()
	{
		;
	}
	public file_trans(String friendID)
	{
		
		Personal_Info client = new Personal_Info();
		//得到好友的地址
		str = client.get_Address(friendID);
	}
	
	
	//作为文件发送方,给好友发送文件，在线发送
	public void sendFile(String path,Socket sender,String Ks)
	{
		
		try {

			File file = new File(path);
			dos = new DataOutputStream(sender.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			//发送文件名和长度
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			
			String line = "";// 用来保存每次读取一行的内容  
			try {
				while ((line = bufferedReader.readLine()) != null)
				{  
					//加密传输
					dos.writeChars(des.encrypt(line.getBytes(), Ks).toString());
					dos.flush();
				}
				 bufferedReader.close();// 关闭输入流  
			
			} catch (IOException e) {
				e.printStackTrace();
			}  
			if(dos!=null)
			{
				dos.close();
			}
//			sender.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//作为文件接收方，在线接收
	public void receFile(Socket event,String Ks)
	{
		try {

				dis = new DataInputStream(event.getInputStream());
				//得到文件名和长度
				String filename = dis.readUTF();
				dis.readLong();
				//创建文件
				File file = new File(filename);
				FileWriter writer=new FileWriter(file);
				String str = dis.readUTF();
				while(str != null)
				{
					//解密
					writer.write(des.decrypt(str.getBytes(), Ks).toString());
					str = dis.readUTF();
				}
				writer.close();
				System.out.println(filename+" 传输完毕 ！");
				if(dis != null)
					dis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
	}

	

	//发送离线文件
	public void sendOfflineFile(String fileName,String userID)
	{
		DataOutputStream dos; //将文件的内容写入到发送给对方的输入

		try {
			Socket client = new Socket("10.8.168.14", 8888);
			File file = new File(fileName);
			dos = new DataOutputStream(client.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String temp = new String("OfflineFile "+"userID "+userID+" recvID "+str[0]);
			dos.writeChars(temp);
			dos.flush();
			//发送文件名和长度
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			
			String line = "";// 用来保存每次读取一行的内容  
			try {
				while ((line = bufferedReader.readLine()) != null)
				{  
					//加密传输
					dos.writeChars(line);
					dos.flush();
				}
				 bufferedReader.close();// 关闭输入流  
			
			} catch (IOException e) {
				e.printStackTrace();
			}  
			if(dos!=null)
			{
				dos.close();
			}
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
