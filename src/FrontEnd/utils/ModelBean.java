package FrontEnd.utils;

import java.rmi.registry.LocateRegistry;

import bd_java_code.RMI_1;

public abstract class ModelBean {
	private RMI_1 rmiServer;
	
	public ModelBean(){
		this.connect();
	}
	
	private void connect(){
		try{
			this.rmiServer = (RMI_1) LocateRegistry.getRegistry("localhost", 9000).lookup("web");
		}catch (Exception e){
			e.printStackTrace();;
		}
	}
	
	public RMI_1 getServidorRMI(){
		return rmiServer;
	}
}