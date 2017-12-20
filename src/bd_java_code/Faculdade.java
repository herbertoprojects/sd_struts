package bd_java_code;

import java.io.Serializable;

public class Faculdade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String nome;
	
	public Faculdade() {
		// TODO Auto-generated constructor stub
	}
	public String getNome() {
		return nome;
	}public String getSigla() {
		return sigla;
	}public void setNome(String nome) {
		this.nome = nome;
	}public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
