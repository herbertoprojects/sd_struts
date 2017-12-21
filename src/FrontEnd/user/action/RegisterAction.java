package FrontEnd.user.action;

import com.opensymphony.xwork2.ActionSupport;

import FrontEnd.user.model.UserBean;
import bd_java_code.Pessoa;

import java.util.Map;

public class RegisterAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private Pessoa pessoa;
	
	@Override
	public String execute() {
		if(this.ncc != 0) {
			this.setNcc(this.ncc);
			this.setPassword(this.password);
			UserBean u = new UserBean();
			
			if(u.registo())
		}
	}
	
	public void setNcc(int ncc) {
		this.ncc = ncc;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}*/
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}

