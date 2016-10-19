package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


//密钥分配得到会话密钥
public class Key_agreement {
	private static String Module; 			 //RSA的模
	private static String RSA_PublicExponent;	//RSA的公钥的指数
	private static String RSA_PrivateExponent;	//RSA的私钥的指数
	
	
	//得到模
	public String get_Module()
	{
		return Module;
	}
	
	//得到自己的公钥
	public RSAPublicKey get_PublicKey()
	{
		RSAUtils rsa = new RSAUtils();
		RSAPublicKey key = rsa.getPublicKey(Module, RSA_PublicExponent);
		return key;
	}
	
	//得到自己的私钥
	public RSAPrivateKey get_PrivateKey()
	{
		RSAUtils rsa = new RSAUtils();
		RSAPrivateKey key = rsa.getPrivateKey(Module, RSA_PrivateExponent);
		return key;
	}
	
	//登陆成功后，读出本地的数字证书，获得公私钥，用于后面的加解密,返回数字证书的内容，在一登陆成功后就调用
	public String get_CA(String userID)
	{
		String tempString = null ;
		System.out.println("get_CA :"+userID);
		String filename = new String("E:\\数字内容安全\\小学期\\文件\\"+userID+"-CA.txt");
		File  file = new File(filename);
		BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            tempString = reader.readLine();
           
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
        get_NED(tempString);
        return tempString;
	}
	
	//处理证书得到公钥和私钥和模
	public void get_NED(String temp)
	{
		String result[] = temp.split(" ");
		//获取模
		String str[] = result[0].split("<module>");
		System.out.println(str[1]);
		String str1[] = str[1].split("</module>");
		Module = str1[0];
		
		//获取公钥的模
		str = result[1].split("<RSAPublicKey>");
		System.out.println(str[1]);
		str1 = str[1].split("</RSAPublicKey>");
		RSA_PublicExponent = str1[0];
		
		//获取私钥的模
		str = result[2].split("<RSAPublicKey>");
		System.out.println(str[1]);
		str1 = str[1].split("</RSAPublicKey>");
		RSA_PrivateExponent = str1[0];
		
	}
	
	//获取对方的公钥
	public RSAPublicKey askPK(String friendID)
	{
		String modulus;
		String exponent;
		String temp = new String("askPK "+"userID "+friendID);
		Message message = new Message();
		String str = message.sendMessage(temp);
		String result[] = str.split(" ");
		
		//获取模
		String str1[] = result[0].split("<module>");
		System.out.println(str1[1]);
		String str2[] = str1[1].split("</module>");
		modulus = str2[0];
		
		//获取指数
		str1 = result[1].split("<RSAPublicKey>");
		System.out.println(str1[1]);
		str2 = str1[1].split("</RSAPublicKey>");
		exponent = str2[0];
		
		RSAUtils rsa = new RSAUtils();
		return rsa.getPublicKey(modulus, exponent);
				
	}

}



