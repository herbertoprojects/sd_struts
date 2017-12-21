package FrontEnd.facebook.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import FrontEnd.utils.ModelBean;

public class FacebookBean extends ModelBean{
	private String username;

	public FacebookBean() {
		super();
	}
	
	public boolean loginFacebook(String user_id) throws RemoteException {
		String username = this.getServidorRMI().login_facebook(user_id);
		if(username == null) {
			return false;
		}
		this.username = username;
		return true;
	}
	
	
	public boolean removeFacebook(int ncc) throws RemoteException {
		return this.getServidorRMI().removeFacebook(ncc);
	}
	
	public boolean associateFacebook(int ncc, String user_id) throws RemoteException {
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
 
        data.put("type", "associate");
        data.put("username", username);
        data.put("user_id", user_id);
        return this.getServidorRMI().associateFacebook(data);
    }
	
	public String getUsername(){
		return this.username;
	}
		

}