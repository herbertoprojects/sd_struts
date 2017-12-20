package bd_java_code;

import java.io.Serializable;

public class Pessoa implements Serializable {
	
	private String nome;
	private int ncc;
	private String cargo;
	private String senha;
	private int telefone;
	private String morada;
	private Faculdade fac;
	private Departamento dep;
	private int status;
	private String facebook_id;
	private int estatuto;
	
	public Pessoa(){
		
	}
	
	public Pessoa(int ncc,String nome,String cargo,String senha,int telefone,String morada,Departamento dep,Faculdade fac, int status, String facebook_id, int estatuto){
		
		this.nome = nome;
		this.ncc = ncc;
		this.cargo = cargo;
		this.senha = senha;
		this.telefone = telefone;
		this.morada = morada;
		this.fac = fac;
		this.dep = dep;
		this.status = status;
		this.facebook_id = facebook_id;
		this.estatuto = estatuto;
	}
	public String getCargo() {
		return cargo;
	}public String getMorada() {
		return morada;
	}public int getNcc() {
		return ncc;
	}public String getNome() {
		return nome;
	}public String getSenha() {
		return senha;
	}public int getTelefone() {
		return telefone;
	}public Faculdade getFac() {
		return fac;
	}public Departamento getDep() {
		return dep;
	}public int getStatus() {
		return status;
	}public String getFacebook_id() {
		return facebook_id;
	}public int getEstatuto() {
		return estatuto;	
	}public void setCargo(String cargo) {
		this.cargo = cargo;
	}public void setMorada(String morada) {
		this.morada = morada;
	}public void setNcc(int ncc) {
		this.ncc = ncc;
	}public void setNome(String nome) {
		this.nome = nome;
	}public void setSenha(String senha) {
		this.senha = senha;
	}public void setTelefone(int telefone) {
		this.telefone = telefone;
	}public void setFac(Faculdade fac) {
		this.fac = fac;
	}public void setDep(Departamento dep) {
		this.dep = dep;
	}public void setStatus(int status) {
		this.status = status;
	}public void setFacebook_id(String facebook_id) {
		this.facebook_id = facebook_id;
	}public void setEstatuto(int estatuto) {
		this.estatuto = estatuto;
	}
}