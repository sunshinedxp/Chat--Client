package Client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;


public class Login {
	private static String userID;
    private static String password;
    private static int port;
    
    public Login()
    {
    	int max=5000;
        int min=2000;
        Random random = new Random();

        port = random.nextInt(max)%(max-min+1) + min;
    }
  
    //返回自己的port
    public int getPort()
    {
    	
    	return port;
    }
    

	    //返回ID
	    public  String main() {
	    	 String str = null;
	    	 String result[] = null;
	    	 Scanner in = new Scanner(System.in);
		     System.out.println("Please input userID:");
		     userID = in.next();
		     System.out.println("Please input password:");
		     password = in.next();
		     
//		     in.close();   
		     String temp = new String("login "+ "userID "+userID+" password "+ password+" port "+port);
		     Message message = new Message();
		     str = message.sendMessage(temp);
		     result = str.split(" ");
		     System.out.println(str);
			//如果登陆失败
	        if(str.equals("ID or PSW ERROR"))
	        {
	        	System.out.println("ID OR PSW ERROR ! ");
	            return null;
	            
	        } 
	        else//登陆成功
	        {
	        	System.out.println("Login OK !");
	            System.out.println("Welcome "+ result[1]);
	            
	            //保存好友信息
	            ManageList managelist = new ManageList();
	            managelist.save_list(result);
	            return userID;   
	            
	        }
	      
	    }
	    
}





