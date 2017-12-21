package FrontEnd.user.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = -5011962009065225959L;

	@Override
	public void destroy() {
		//release resources here
	}

	@Override
	public void init() {
		// create resources here
	}
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		System.out.println("inside auth interceptor");
		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();
		int ncc = (int) sessionAttributes.get("current_ncc");
		System.out.println(ncc);
		
		if(actionInvocation.getInvocationContext().getName().equals("home")){
			return "userloggedin";
		}
		actionInvocation.getInvocationContext().setSession((Map<String, Object>) sessionAttributes);
		System.out.println(sessionAttributes);
		return actionInvocation.invoke();
	}
}