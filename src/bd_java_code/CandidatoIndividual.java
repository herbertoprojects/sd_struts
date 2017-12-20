package bd_java_code;

import java.io.Serializable;

public class CandidatoIndividual extends Candidatos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pessoa pessoa;
	
	public CandidatoIndividual() {
		// TODO Auto-generated constructor stub
		super();
		super.setTipo("Individual");
	}
	public Pessoa getPessoa() {
		return pessoa;
	}public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
