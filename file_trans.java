package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

//�ļ����䣬�Ự��Կ�ӽ���
public class file_trans{

	private  DataOutputStream dos; //���ļ�������д�뵽���͸��Է�������
	private DataInputStream dis;   //�õ�������
	private  DES des;
	String str[] = new String[4];
	public file_trans()
	{
		;
	}
	public file_trans(String friendID)
	{
		
		Personal_Info client = new Personal_Info();
		//�õ����ѵĵ�ַ
		str = client.get_Address(friendID);
	}
	
	
	//��Ϊ�ļ����ͷ�,�����ѷ����ļ������߷���
	public void sendFile(String path,Socket sender,String Ks)
	{
		
		try {

			File file = new File(path);
			dos = new DataOutputStream(sender.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			//�����ļ����ͳ���
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			
			String line = "";// ��������ÿ�ζ�ȡһ�е�����  
			try {
				while ((line = bufferedReader.readLine()) != null)
				{  
					//���ܴ���
					dos.writeChars(des.encrypt(line.getBytes(), Ks).toString());
					dos.flush();
				}
				 bufferedReader.close();// �ر�������  
			
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

	//��Ϊ�ļ����շ������߽���
	public void receFile(Socket event,String Ks)
	{
		try {

				dis = new DataInputStream(event.getInputStream());
				//�õ��ļ����ͳ���
				String filename = dis.readUTF();
				dis.readLong();
				//�����ļ�
				File file = new File(filename);
				FileWriter writer=new FileWriter(file);
				String str = dis.readUTF();
				while(str != null)
				{
					//����
					writer.write(des.decrypt(str.getBytes(), Ks).toString());
					str = dis.readUTF();
				}
				writer.close();
				System.out.println(filename+" ������� ��");
				if(dis != null)
					dis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
	}

	

	//���������ļ�
	public void sendOfflineFile(String fileName,String userID)
	{
		DataOutputStream dos; //���ļ�������д�뵽���͸��Է�������

		try {
			Socket client = new Socket("10.8.168.14", 8888);
			File file = new File(fileName);
			dos = new DataOutputStream(client.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String temp = new String("OfflineFile "+"userID "+userID+" recvID "+str[0]);
			dos.writeChars(temp);
			dos.flush();
			//�����ļ����ͳ���
			dos.writeUTF(file.getName());
			dos.flush();
			dos.writeLong(file.length());
			dos.flush();
			
			String line = "";// ��������ÿ�ζ�ȡһ�е�����  
			try {
				while ((line = bufferedReader.readLine()) != null)
				{  
					//���ܴ���
					dos.writeChars(line);
					dos.flush();
				}
				 bufferedReader.close();// �ر�������  
			
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
