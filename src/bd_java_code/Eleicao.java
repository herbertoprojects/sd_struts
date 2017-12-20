package bd_java_code;

import java.io.Serializable;
import java.util.ArrayList;

public class Eleicao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String tipo;
	private String dataInicio;
	private String dataFim;
	private String titulo;
	private String descricao;
	private int nVotoBNA;
	private Departamento dep;
	private Faculdade fac;
	private ArrayList<Candidatos> candidatos;
	
	
	
	public Eleicao() {
		// TODO Auto-generated constructor stub
	}
	public String getDataFim() {
		return dataFim;
	}public String getDataInicio() {
		return dataInicio;
	}public String getDescricao() {
		return descricao;
	}public int getId() {
		return id;
	}public int getnVotoBNA() {
		return nVotoBNA;
	}public String getTipo() {
		return tipo;
	}public String getTitulo() {
		return titulo;
	}public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}public void setDescricao(String descricao) {
		this.descricao = descricao;
	}public void setId(int id) {
		this.id = id;
	}public void setnVotoBNA(int voto) {
		this.nVotoBNA = voto;
	}public void setTipo(String tipo) {
		this.tipo = tipo;
	}public void setTitulo(String titulo) {
		this.titulo = titulo;
	}public Departamento getDep() {
		return dep;
	}public Faculdade getFac() {
		return fac;
	}public void setDep(Departamento dep) {
		this.dep = dep;
	}public void setFac(Faculdade fac) {
		this.fac = fac;
	}public ArrayList<Candidatos> getCandidatos() {
		return candidatos;
	}public void setCandidatos(ArrayList<Candidatos> candidatos) {
		this.candidatos = candidatos;
	}
}
