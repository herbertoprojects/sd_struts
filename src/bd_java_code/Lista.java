package bd_java_code;

import java.io.Serializable;
import java.util.ArrayList;

public class Lista extends Candidatos implements Serializable {
	private ArrayList <PessoaLista> lista_pessoas;
	private String nome;
	public Lista() {
		// TODO Auto-generated constructor stub
		super();
		super.setTipo("Lista");
	}
	public ArrayList<PessoaLista> getLista_pessoas() {
		return lista_pessoas;
	}public void setLista_pessoas(ArrayList<PessoaLista> lista_pessoas) {
		this.lista_pessoas = lista_pessoas;
	}public String getNome() {
		return nome;
	}public void setNome(String nome) {
		this.nome = nome;
	}
}
