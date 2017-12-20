package FrontEnd.user.model;

import java.rmi.RemoteException;

import FrontEnd.utils.ModelBean;
import bd_java_code.Pessoa;

public class UserBean extends ModelBean {
	public UserBean() {
		super();
	}
	
	public boolean registo(Pessoa pessoa) {
		boolean registado = false;
		try {
			registado = this.getServidorRMI().registaPessoa(pessoa);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return registado;
	}
	
	public boolean login(int ncc, String password) {
		boolean logado = false;
		try {
			logado = this.getServidorRMI().loginPessoa(ncc, password);
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return logado;
	}
}