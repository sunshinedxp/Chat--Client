package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


//��Կ����õ��Ự��Կ
public class Key_agreement {
	private static String Module; 			 //RSA��ģ
	private static String RSA_PublicExponent;	//RSA�Ĺ�Կ��ָ��
	private static String RSA_PrivateExponent;	//RSA��˽Կ��ָ��
	
	
	//�õ�ģ
	public String get_Module()
	{
		return Module;
	}
	
	//�õ��Լ��Ĺ�Կ
	public RSAPublicKey get_PublicKey()
	{
		RSAUtils rsa = new RSAUtils();
		RSAPublicKey key = rsa.getPublicKey(Module, RSA_PublicExponent);
		return key;
	}
	
	//�õ��Լ���˽Կ
	public RSAPrivateKey get_PrivateKey()
	{
		RSAUtils rsa = new RSAUtils();
		RSAPrivateKey key = rsa.getPrivateKey(Module, RSA_PrivateExponent);
		return key;
	}
	
	//��½�ɹ��󣬶������ص�����֤�飬��ù�˽Կ�����ں���ļӽ���,��������֤������ݣ���һ��½�ɹ���͵���
	public String get_CA(String userID)
	{
		String tempString = null ;
		System.out.println("get_CA :"+userID);
		String filename = new String("E:\\�������ݰ�ȫ\\Сѧ��\\�ļ�\\"+userID+"-CA.txt");
		File  file = new File(filename);
		BufferedReader reader = null;
        try {
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
            reader = new BufferedReader(new FileReader(file));
            tempString = reader.readLine();
           
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
        get_NED(tempString);
        return tempString;
	}
	
	//����֤��õ���Կ��˽Կ��ģ
	public void get_NED(String temp)
	{
		String result[] = temp.split(" ");
		//��ȡģ
		String str[] = result[0].split("<module>");
		System.out.println(str[1]);
		String str1[] = str[1].split("</module>");
		Module = str1[0];
		
		//��ȡ��Կ��ģ
		str = result[1].split("<RSAPublicKey>");
		System.out.println(str[1]);
		str1 = str[1].split("</RSAPublicKey>");
		RSA_PublicExponent = str1[0];
		
		//��ȡ˽Կ��ģ
		str = result[2].split("<RSAPublicKey>");
		System.out.println(str[1]);
		str1 = str[1].split("</RSAPublicKey>");
		RSA_PrivateExponent = str1[0];
		
	}
	
	//��ȡ�Է��Ĺ�Կ
	public RSAPublicKey askPK(String friendID)
	{
		String modulus;
		String exponent;
		String temp = new String("askPK "+"userID "+friendID);
		Message message = new Message();
		String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		
		//��ȡģ
		String str1[] = result[0].split("<module>");
		System.out.println(str1[1]);
		String str2[] = str1[1].split("</module>");
		modulus = str2[0];
		
		//��ȡָ��
		str1 = result[1].split("<RSAPublicKey>");
		System.out.println(str1[1]);
		str2 = str1[1].split("</RSAPublicKey>");
		exponent = str2[0];
		
		RSAUtils rsa = new RSAUtils();
		return rsa.getPublicKey(modulus, exponent);
				
	}

}



