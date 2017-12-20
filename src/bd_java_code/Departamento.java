package bd_java_code;

import java.io.Serializable;

public class Departamento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String nome;
	private Faculdade fac;
	
	public Departamento() {
		// TODO Auto-generated constructor stub
	}
	public Faculdade getFac() {
		return fac;
	}public String getNome() {
		return nome;
	}public String getSigla() {
		return sigla;
	}public void setFac(Faculdade fac) {
		this.fac = fac;
	}public void setNome(String nome) {
		this.nome = nome;
	}public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
