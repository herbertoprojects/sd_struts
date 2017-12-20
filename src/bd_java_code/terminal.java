package bd_java_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class terminal {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			try {
				Socket servidor = new Socket("localhost", 8000);
				PrintWriter out =  new PrintWriter(servidor.getOutputStream(),true);
				BufferedReader in = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
				
				String fromServer;
				String fromUser;
				Scanner sc = new Scanner(System.in);
				while(true){
					while ((fromServer = in.readLine()) != null) {
						if (fromServer.equals("fechar"))
					        break;
						else{
					    System.out.println("Server: " + fromServer);
						}
				    
					}

				    System.out.print("Cliente: ");
				    fromUser = sc.nextLine();
				    if (fromUser != null) {
				        out.println(fromUser);
				}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
