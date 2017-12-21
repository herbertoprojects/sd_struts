package FrontEnd.user.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.Map;
import FrontEnd.user.model.*;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private int ncc = 0;
	private String password = null;

	@Override
	public String execute() {
		if(this.ncc != 0) {
			this.setNcc(this.ncc);
			this.setPassword(this.password);
			UserBean r = new UserBean();
			if(r.login(this.ncc, this.password)){
				this.session.put("current_username", this.ncc);
				this.session.put("msg", "Logged with success!");
				return "user_success";
			}
		}
		this.session.put("msg", "Check your credentials!");
		return ERROR;
	}

	public void setNcc(int ncc) {
		this.ncc = ncc;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
