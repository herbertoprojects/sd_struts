package bd_java_code;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServidorRMI extends UnicastRemoteObject implements RMI_1 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int portoConsola = 9000;
	
	LigacaoBD ligacao;

	protected ServidorRMI() throws RemoteException {
		super();
		ligacao = new LigacaoBD();
		if(ligacao.conectaBD()){
			consolaRMI();
		}
	}

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		ServidorRMI server = new ServidorRMI();
	}
	
	public boolean consolaRMI(){
		try{
			LocateRegistry.createRegistry(portoConsola);
			Naming.rebind("rmi://localhost:"+portoConsola+"/rmi", this);
			Naming.rebind("rmi://localhost:"+portoConsola+"/tcpServer", this);
			Naming.rebind("rmi://localhost:"+portoConsola+"/web", this);
			return true;
		}catch(RemoteException | MalformedURLException e){
			return false;
		}
	}
	
	@Override
	public boolean registaPessoa(Pessoa pessoa) throws RemoteException {
		
		String comando = "INSERT INTO Pessoa values ('";
		comando += pessoa.getNcc();//ncc
		comando += "',";
		if(pessoa.getFac()!=null){
			comando += "'";
			comando += pessoa.getFac().getSigla();//faculdade
			comando += "',";
		}else{
			comando += "null,";
		}
		if(pessoa.getDep()!=null){
			comando += "'";
			comando += pessoa.getDep().getSigla();//departamento
			comando += "',";
			if(pessoa.getDep().getFac()!=null){
				comando += "'";
				comando += pessoa.getDep().getFac().getSigla();//departamento->faculdade
				comando += "','";
			}else{
				comando += "null,'";
			}
		}else{
			comando += "null,null,'";
		}
		comando += pessoa.getCargo();//cargo
		comando += "','";
		comando += pessoa.getNome();//Nome
		comando += "','";
		comando += pessoa.getSenha();//Senha
		comando += "','";
		comando += pessoa.getTelefone();//telefone
		comando += "','";
		comando += pessoa.getMorada();//morada
		comando += "','";
		comando += pessoa.getStatus();//status
		comando += "','";
		comando += pessoa.getFacebook_id();
		comando += "','";
		comando += pessoa.getEstatuto();
		comando += "')";
		return ligacao.executaUpdateSQL(comando);

	}
	
	@Override
	public boolean loginPessoa(int ncc, String password) throws RemoteException {
		String comando = "select * from pessoa where ncc = "+ncc+" and senha = "+password;
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return false;}
		else {
			String comando1 = "update pessoa set status = 1 where ncc ="+ncc;
			ligacao.executaUpdateSQL(comando1);
			return true;
		}
	}

	@Override
	public boolean modificaPessoa(Pessoa pessoa) throws RemoteException {
		
		String comando = "UPDATE Pessoa SET ";
		if(pessoa.getFac()!=null){
			comando += "Faculdade = '";
			comando += pessoa.getFac().getSigla();
			comando += "',";
		}else{
			comando += "Faculdade = null,";
		}
		
		if(pessoa.getDep()!=null){
			comando += "DepartamentoNome= '";
			comando += pessoa.getDep().getSigla();
			comando += "',";
			if(pessoa.getDep().getFac()!=null){
				comando += "DepartamentoFaculdadesigla = '";
				comando += pessoa.getDep().getFac().getSigla();
				comando += "',";
			}else{
				comando += "DepartamentoFaculdadesigla = null,";
			}
			
		}else{
			comando += "DepartamentoNome= null , DepartamentoFaculdadesigla = null, ";
		}
		comando += "tipo = '";
		comando += pessoa.getCargo();
		comando += "', Nome = '";
		comando += pessoa.getNome();
		comando += "', senha= '";
		comando += pessoa.getSenha();
		comando += "', telefone = ";
		comando += pessoa.getTelefone();
		comando += ", morada = '";
		comando += pessoa.getMorada();
		comando += "' where NCC = ";
		comando += pessoa.getNcc();
		
		return(ligacao.executaUpdateSQL(comando));
	}

	@Override
	public boolean removePessoa(Pessoa pessoa) throws RemoteException {
		String comando = "DELETE FROM Pessoa WHERE NCC = "+pessoa.getNcc();
		return (ligacao.executaUpdateSQL(comando));
	}
	
	@Override
	public Pessoa procuraPessoa(int ncc)throws RemoteException{
		String comando = "select * from pessoa where ncc = "+ncc;
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			if (res.next()) {
	            Pessoa pessoa = new Pessoa();
	            pessoa.setNcc(ncc);
	            pessoa.setFac(procuraFaculdade(res.getString(2)));
	            pessoa.setDep(procuraDepartamento(res.getString(3),res.getString(4)));
	            pessoa.setCargo(res.getString(5));
	            pessoa.setNome(res.getString(6));
	            pessoa.setSenha(res.getString(7));
	            pessoa.setTelefone(res.getInt(8));
	            pessoa.setMorada(res.getString(9));
	            
	            res.close();
	            return pessoa;
	        }else{
	        	res.close();
	        	return null;
	        }
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	
	/*FACEBOOK*/
	public boolean associateFacebook(String user_idP, int nccP) throws RemoteException {
		String comando;
		String user_id = user_idP;
		int ncc = nccP;
		
		comando = "Select facebook_id from pessoa where facebook_id = "+user_id;
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return false;}
		try {
			if(res.next()) {
				System.out.println("Conta Facebook já existe!");
				return false;
			} else {
				String comando1;
				comando1 = "Update pessoa set facebook_id ="+user_id+"where ncc ="+ncc;
				ligacao.executaUpdateSQL(comando1);
				return true;
			}
		} catch (SQLException e){
			try{res.close();}catch(Exception e1){}
			return false;
		}
	}
	
	/*public boolean removeFacebook(int ncc) {
		String comando;
		comando = "Update pessoa set facebook_id == null where ncc ="
	}*/
			
	@Override
	public ArrayList<Departamento> ListDepartamentos(Faculdade faculdade) throws RemoteException {
		String comando;
		if(faculdade==null){
			comando = "Select * from departamento";
		}else{
			comando = "Select * from departamento where FAC = '"+faculdade.getSigla()+"'";
		}
		
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			ArrayList<Departamento> listaDepartamentos = new ArrayList<Departamento>();
			Departamento depTemp;
			while(res.next()){
				System.out.println(res.getString(1));
				depTemp = new Departamento();
				depTemp.setSigla(res.getString(1));
				depTemp.setFac(procuraFaculdade(res.getString(2)));
				depTemp.setNome(res.getString(3));
				listaDepartamentos.add(depTemp);
			}
			res.close();
			return listaDepartamentos;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}

	@Override
	public boolean addDepartamento(Departamento dep) throws RemoteException {
		String comando = "INSERT INTO departamento values ('";
		comando += dep.getSigla();
		comando += "','";
		comando += dep.getFac().getSigla();
		comando += "','";
		comando += dep.getNome();
		comando += "')";
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public boolean removeDepartamento(Departamento dep) throws RemoteException {
		String comando = "DELETE FROM Departamento WHERE sigla = '"+dep.getSigla()+"'";
		return ligacao.executaUpdateSQL(comando);
	}
	@Override
	public Departamento procuraDepartamento(String siglaDep, String siglaFac) throws RemoteException {
		String comando = "Select * from departamento where sigla = '"+siglaDep+"' and fac = '"+siglaFac+"'";
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			if(res.next()){
				Departamento dep = new Departamento();
				dep.setSigla(res.getString(1));
				dep.setFac(procuraFaculdade(res.getString(2)));
				dep.setNome(res.getString(3));
				res.close();
				return dep;
			}else{
				res.close();
				return null;
			}
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}

	@Override
	public ArrayList<Faculdade> ListFaculdades() throws RemoteException {
		String comando = "Select * from faculdade";
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			ArrayList<Faculdade> listaFaculdades = new ArrayList<Faculdade>();
			Faculdade facTemp;
			while(res.next()){
				facTemp = new Faculdade();
				facTemp.setSigla(res.getString(1));
				facTemp.setNome(res.getString(2));
				listaFaculdades.add(facTemp);
			}
			res.close();
			return listaFaculdades;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}

	@Override
	public boolean addFaculdade(Faculdade fac) throws RemoteException {
		String comando = "INSERT INTO faculdade values ('";
		comando += fac.getSigla();
		comando += "','";
		comando += fac.getNome();
		comando += "')";
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public boolean removeFaculdade(Faculdade fac) throws RemoteException {
		String comando = "DELETE FROM Faculdade WHERE sigla = '"+fac.getSigla()+"'";
		return ligacao.executaUpdateSQL(comando);
	}
	@Override
	public Faculdade procuraFaculdade(String sigla) throws RemoteException {
		String comando = "Select * from faculdade where sigla = '"+sigla+"'";
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			if(res.next()){
				Faculdade fac = new Faculdade();
				fac.setSigla(res.getString(1));
				fac.setNome(res.getString(2));
				res.close();
				return fac;
			}else{
				res.close();
				return null;
			}
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}

	@Override
	public boolean novaEleicao(Eleicao eleicao) throws RemoteException {
		String comando = "INSERT INTO Eleicao VALUES (SEQ_ELEICAO.NEXTVAL,'";
		comando += eleicao.getTipo();
		comando += "', TO_TIMESTAMP ('";
		comando += eleicao.getDataInicio();
		comando += "','yyyy:mm:dd hh:mi'),TO_TIMESTAMP ('";
		comando += eleicao.getDataFim();
		comando += "','yyyy:mm:dd hh:mi'),'";
		comando += eleicao.getTitulo();
		comando += "','";
		comando += eleicao.getDescricao();
		comando += "',";
		comando += eleicao.getnVotoBNA();
		comando += ")";
		
		return ligacao.executaUpdateSQL(comando);

	}

	@Override
	public boolean removerEleicao(Eleicao eleicao) throws RemoteException {
		String comando = "Delete from eleicao where id = " + eleicao.getId();	
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public ArrayList<Eleicao> listaEleicao() throws RemoteException {
		String comando = "Select * from eleicao";
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			ArrayList<Eleicao> listaEleicoes = new ArrayList<Eleicao>();
			Eleicao elecTemp;
			while(res.next()){
				elecTemp = new Eleicao();
				elecTemp.setId(res.getInt(1));
				elecTemp.setTipo(res.getString(2));
				elecTemp.setDataInicio(res.getString(3));
				elecTemp.setDataFim(res.getString(4));
				elecTemp.setTitulo(res.getString(5));
				elecTemp.setDescricao(res.getString(6));
				elecTemp.setnVotoBNA(res.getInt(7));;
				elecTemp.setCandidatos(listaCandidatos(elecTemp));
				listaEleicoes.add(elecTemp);
			}
			res.close();
			return listaEleicoes;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	@Override
	public ArrayList<Eleicao> listaEleicaoDecorrer() throws RemoteException {
		String comando = "Select * from eleicao where DATAINICIO <= CURRENT_TIMESTAMP AND DATAFIM > CURRENT_TIMESTAMP";
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			ArrayList<Eleicao> listaEleicoes = new ArrayList<Eleicao>();
			Eleicao elecTemp;
			while(res.next()){
				elecTemp = new Eleicao();
				elecTemp.setId(res.getInt(1));
				elecTemp.setTipo(res.getString(2));
				elecTemp.setDataInicio(res.getString(3));
				elecTemp.setDataFim(res.getString(4));
				elecTemp.setTitulo(res.getString(5));
				elecTemp.setDescricao(res.getString(6));
				elecTemp.setnVotoBNA(res.getInt(7));;
				elecTemp.setCandidatos(listaCandidatos(elecTemp));
				listaEleicoes.add(elecTemp);
			}
			res.close();
			return listaEleicoes;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	
	@Override
	public Eleicao procuraEleicao(int id) throws RemoteException {
		String comando = "SELECT ID, tipo, TO_CHAR(DataInicio,'yyyy:mm:dd hh:mi'), TO_CHAR(DataFim,'yyyy:mm:dd hh:mi'), Titulo, Descricao, nVotoBNA FROM Eleicao where id = "+id;
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			if(res.next()){
				Eleicao eleicao = new Eleicao();
				eleicao.setId(id);
				eleicao.setTipo(res.getString(2));
				eleicao.setDataInicio(res.getString(3));
				eleicao.setDataFim(res.getString(4));
				eleicao.setTitulo(res.getString(5));
				eleicao.setDescricao(res.getString(6));
				eleicao.setnVotoBNA(res.getInt(7));
				eleicao.setCandidatos(listaCandidatos(eleicao));
				res.close();
				return eleicao;
			}else{
				res.close();
				return null;
			}
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}

	}

	@Override
	public boolean addCandidato(Candidatos candidato, Eleicao eleicao) throws RemoteException {
		if(ligacao.inicioTransacao()){
			String comando = "Insert INTO Candidatos values (seq_Candidato.nextval,";
			comando += eleicao.getId();
			comando += ",'";
			comando += candidato.getTipo();
			comando += "',";
			comando += candidato.getnVotos();
			comando += ",'sim')";
			if(ligacao.executaUpdateSQL(comando)){
				candidato.setId(ultimoCandidato());
				if(candidato.getTipo().equalsIgnoreCase("Lista")){
					Lista list = (Lista) candidato;
					comando = "INSERT INTO Lista VALUES ('";
					comando += list.getNome();
					comando += "',SEQ_CANDIDATO.CURRVAL,";
					comando += candidato.getEleicao().getId();
					comando += ")";
					if(ligacao.executaUpdateSQL(comando)){
						for (PessoaLista tempPessoaLista:list.getLista_pessoas()){
							if(!addPessoaLista(tempPessoaLista, list)){
								ligacao.voltarTransacao();
								return false;
							}
						}
						if(ligacao.fimTransacao()){
							return true;
						}
					}
				}
				if(candidato.getTipo().equalsIgnoreCase("Individual")){
					CandidatoIndividual candIndividual = (CandidatoIndividual) candidato;
					comando = "INSERT INTO Individual VALUES (";
					comando += candIndividual.getPessoa().getNcc();
					comando += ",seq_Candidato.CURRVAL,";
					comando += candIndividual.getEleicao().getId();
					comando += ")";
					if(ligacao.executaUpdateSQL(comando)){
						if(ligacao.fimTransacao()){
							return true;
						}
					}
				}
			}
			ligacao.voltarTransacao();
			return false;
			
		}else{
			return false;
		}
	}
	
	public int ultimoCandidato(){
		try {
			return ligacao.executaSQL("Select SEQ_CANDIDATO.CURRVAL  from dual").getInt(1);
		} catch (SQLException e) {
			return 0;
		}
	}

	@Override
	public boolean removeCandidato(Candidatos candidato, Eleicao eleicao) throws RemoteException {
		String comando = "UPDATE Candidatos SET valido = 'nao' where id = "+candidato.getId()+" and EleicaoID = " +eleicao.getId();
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public ArrayList<Candidatos>listaCandidatos(Eleicao eleicao) throws RemoteException {
		String comando = "Select * from CANDIDATOS where ELEICAOID = "+eleicao.getId();
		ResultSet res = ligacao.executaSQL(comando);
		if(res==null){return null;}
		try{
			if(res.next()){
				ArrayList<Candidatos> candidatoslista = new ArrayList<Candidatos>();
				Candidatos cand;
				while(res.next()){
					if(!res.getString(5).equalsIgnoreCase("nao")){
						cand = new Candidatos();
						cand.setEleicao(eleicao);
						cand.setId(res.getInt(1));
						cand.setnVotos(res.getInt(4));
						cand.setTipo(res.getString(3));
						if(res.getString(3).equalsIgnoreCase("Individual")){
							candidatoslista.add(procuraIndividual(cand));
						}
						else{
							candidatoslista.add(procuraLista(cand));
						}
					}
				}
				res.close();
				return candidatoslista;
			}else{
				res.close();
				return null;
			}
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	@Override
	public CandidatoIndividual procuraIndividual(Candidatos candidato) throws RemoteException {
		String comando = "select * from Individual where CANDIDATOSID = "+candidato.getId()+" and CANDIDATOSELEICAOID = "+candidato.getEleicao().getId();
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			if(res.next()){
				CandidatoIndividual cand = new CandidatoIndividual();
				cand.setId(candidato.getId());
				cand.setPessoa(procuraPessoa(res.getInt(1)));
				cand.setEleicao(candidato.getEleicao());
				cand.setnVotos(candidato.getnVotos());
				cand.setTipo(candidato.getTipo());
				res.close();
				return cand;
			}
			res.close();
			return null;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	@Override
	public Lista procuraLista(Candidatos candidato) throws RemoteException {
		String comando = "select * from lista where CAND = "+candidato.getId()+" and CANDELEICAO = "+candidato.getEleicao().getId();
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			if(res.next()){
				Lista lista = new Lista();
				lista.setEleicao(candidato.getEleicao());
				lista.setId(candidato.getId());
				lista.setNome(res.getString(1));
				lista.setnVotos(candidato.getnVotos());
				lista.setTipo(candidato.getTipo());
				lista.setLista_pessoas(listaPessoaLista(lista));
				res.close();
				return lista;
			}
			res.close();
			return null;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	@Override
	public boolean addPessoaLista(PessoaLista pessoaLista,Lista lista) throws RemoteException {
		String comando = "INSERT INTO LtemP values('";
		comando += pessoaLista.getCargo();
		comando += "',";
		comando += pessoaLista.getPessoa().getNcc();
		comando += ",'";
		comando += lista.getNome();
		comando += "',";
		comando += lista.getId();
		comando += ",";
		comando += lista.getEleicao().getId();
		comando += ")";
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public boolean removePessoaLista(PessoaLista pessoaLista,Lista lista) throws RemoteException {
		String comando = "DELETE FROM LtemP WHERE PessoaNCC =";
		comando += pessoaLista.getPessoa().getNcc();
		comando += "AND Listanome = '";
		comando += lista.getNome();
		comando += "' AND ListaCand = ";
		comando += lista.getId();
		comando += " AND ListaCandEleicao =";
		comando += lista.getEleicao().getId();
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public ArrayList<PessoaLista> listaPessoaLista(Lista lista) throws RemoteException {
		String comando = "Select * FROM LtemP WHERE  Listanome = '";
		comando += lista.getNome();
		comando += "' AND ListaCand = ";
		comando += lista.getId();
		comando += " AND ListaCandEleicao =";
		comando += lista.getEleicao().getId();
		
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			ArrayList<PessoaLista> pessoaLista = new ArrayList<PessoaLista>();
			PessoaLista pessoaListaTemp;
			while(res.next()){
				pessoaListaTemp = new PessoaLista();
				pessoaListaTemp.setCargo(res.getString(1));
				pessoaListaTemp.setPessoa(procuraPessoa(res.getInt(2)));
				pessoaLista.add(pessoaListaTemp);
			}
			res.close();
			return pessoaLista;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}

	@Override
	public boolean addMesaVoto(MesaVoto mesaVoto) throws RemoteException {
		String comando = "INSERT INTO MesaVoto values(";
		comando += mesaVoto.getEleicao().getId();
		comando += ",'";
		comando += mesaVoto.getDep().getSigla();
		comando += "','";
		comando += mesaVoto.getDep().getFac().getSigla();
		comando += "','";
		comando += mesaVoto.getUsername();
		comando += "','";
		comando += mesaVoto.getPassword();
		comando += "')";
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public boolean removeMesaVoto(MesaVoto mesaVoto) throws RemoteException {
		String comando = "DELETE FROM MesaVoto WHERE EleicaoID = ";
		comando += mesaVoto.getEleicao().getId();
		comando += "AND Dep = '";
		comando += mesaVoto.getDep().getSigla();
		comando += "AND DepFac = ";
		comando += mesaVoto.getDep().getFac().getSigla();
		return ligacao.executaUpdateSQL(comando);
	}

	@Override
	public ArrayList <MesaVoto> listMesaVoto(Eleicao eleicao,Departamento dep) throws RemoteException {
		String comando = "Select * FROM MesaVoto WHERE  EleicaoID = ";
		comando += eleicao.getId();
		if(dep!=null){
		comando += " AND Dep = '";
		comando += dep.getSigla();
		comando += "' AND DepFac ='";
		comando += dep.getFac().getSigla();
		comando += "'";
		}
		
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return null;}
		try{
			ArrayList<MesaVoto> listaMesa = new ArrayList<MesaVoto>();
			MesaVoto mesaTemp;
			while(res.next()){
				mesaTemp = new MesaVoto();
				mesaTemp.setDep(procuraDepartamento(res.getString(2), res.getString(3)));
				mesaTemp.setEleicao(procuraEleicao(res.getInt(1)));
				mesaTemp.setUsername(res.getString(4));
				mesaTemp.setPassword(res.getString(5));
				listaMesa.add(mesaTemp);
			}
			res.close();
			return listaMesa;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return null;
		}
	}
	@Override
	public MesaVoto ligarServidor(Eleicao eleicao, String nomeMesaVoto, String passwordMesaVoto) throws RemoteException {
		ArrayList<MesaVoto> mesasVoto = listMesaVoto(eleicao, null);
		for(MesaVoto tempMesa:mesasVoto){
			if(tempMesa.getUsername().equals(nomeMesaVoto) && tempMesa.getPassword().equals(passwordMesaVoto)){
				return tempMesa;
			}
		}
		return null;
	}@Override
	public Boolean testaVotar(MesaVoto mesa, int ncc) throws RemoteException {
		String comando = "select count(*) from PVOTAE where PESSOANCC = "+ncc+" and MESAELEICAO = "+mesa.getEleicao().getId();
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return false;}
		try{
			if(res.next()){
				if(res.getInt(1)==0){
					res.close();
					return true;
				}
			}
			res.close();
			return false;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return false;
		}
	}@Override
	public Boolean validaUser(MesaVoto mesa, int ncc, String senha) throws RemoteException {
		String comando = "select count(*) from pessoa where ncc = "+ncc+" and senha = '"+senha+"'";
		ResultSet res = ligacao.executaSQL(comando);
		if(res == null){return false;}
		try{
			if(res.next()){
				if(res.getInt(1)==1){
					res.close();
					return true;
				}
			}
			res.close();
			return false;
			
		}catch(SQLException e){
			try{res.close();}catch(Exception e1){}
			return false;
		}
	}
	@Override
	public Boolean votar(MesaVoto mesa, Candidatos cand, int ncc, String senha) throws RemoteException {
		ligacao.inicioTransacao();
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm");
		dataEleicao data_atual = textEditor.dataStringToData(dateFormat.format(new Date()));
		
		if(!data_atual.maior_data(textEditor.dataStringToData(mesa.getEleicao().getDataFim()))||
				data_atual.maior_data(textEditor.dataStringToData(mesa.getEleicao().getDataInicio()))){
			ligacao.voltarTransacao();
			return false;
		}
		if(validaUser(mesa, ncc, senha)){
			if(testaVotar(mesa, ncc)){
				if(cand == null){
					String comando = "  UPDATE Eleicao SET nVotoBNA = (select nVotoBNA+1 from ELEICAO where id = "+
										mesa.getEleicao().getId()+
										") WHERE ID = "+
										mesa.getEleicao().getId();
					
					
					
					if(!ligacao.executaUpdateSQL(comando)){
						ligacao.voltarTransacao();
						return false;
					}
				}else{
					String comando = "UPDATE Candidatos SET nVotos = (select NVOTOS+1 from CANDIDATOS where ID = "+
										cand.getId()+
										" AND EleicaoID = "+
										cand.getEleicao().getId()+
										") WHERE ID = "+
										cand.getId()
										+" AND EleicaoID = "+
										cand.getEleicao().getId();
					if(!ligacao.executaUpdateSQL(comando)){
						ligacao.voltarTransacao();
						return false;
					}
				}
				
				String comando = "INSERT INTO PvotaE values (";
				comando += ncc;
				comando += ",";
				comando += mesa.getEleicao().getId();
				comando += ",'";
				comando += mesa.getDep().getSigla();
				comando += "','";
				comando += mesa.getDep().getFac().getSigla();
				comando += "')";
				
				if(ligacao.executaUpdateSQL(comando)){
					ligacao.fimTransacao();
					return true;
				}else{
					ligacao.voltarTransacao();
					return false;
				}

			}
			ligacao.voltarTransacao();
			return false;
		}
		ligacao.voltarTransacao();
		return false;
	}
	
	@Override
	public String detalheEleicao(Eleicao eleicao) throws RemoteException {
		// TODO Auto-generated method stub
		String resultado = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd hh:mm");
		dataEleicao data_atual = textEditor.dataStringToData(dateFormat.format(new Date()));
		if (!data_atual.maior_data(textEditor.dataStringToData(eleicao.getDataInicio()))) {
			resultado += "\nTítulo eleição: "+eleicao.getTitulo()+" - Data início: "+eleicao.getDataInicio()+" - Data fim: "+ eleicao.getDataFim();
			resultado += "\nEleição ainda não iniciada.";
			for(Candidatos candTemp: eleicao.getCandidatos()) {
				if(candTemp.getTipo().equalsIgnoreCase("lista")) {
					Lista lista = (Lista) candTemp;
					resultado += "\nNúmero candidato: "+lista.getId()+" - Nome lista: "+lista.getNome()+" Membros: "+lista.getLista_pessoas();
					for(PessoaLista pessoalista : lista.getLista_pessoas()) {
						resultado += "\n\tCC: "+pessoalista.getPessoa().getNcc()+" - Cargo: "+pessoalista.getCargo()+ " - Nome: "+pessoalista.getPessoa().getNome();
					}
					resultado += "\n";
				}else {
					CandidatoIndividual cand = (CandidatoIndividual) candTemp;
					resultado += "\nNúmero candidato: "+cand.getId()+" - Nome: "+cand.getPessoa().getNome()+" - CC: "+cand.getPessoa().getNcc();
				}
			}
		}
		else {
			if (data_atual.maior_data(textEditor.dataStringToData(eleicao.getDataFim()))) {
				resultado += "\nTítulo eleição: "+eleicao.getTitulo()+" - Data início: "+eleicao.getDataInicio()+" - Data fim: "+ eleicao.getDataFim();
				resultado += "\nEleição terminada.";
				resultado += "\nVotos em branco/nulos: "+eleicao.getnVotoBNA();
				for(Candidatos candTemp: eleicao.getCandidatos()) {
					if(candTemp.getTipo().equalsIgnoreCase("lista")) {
						Lista lista = (Lista) candTemp;
						resultado += "\nNúmero candidato: "+lista.getId()+" - Nome lista: "+lista.getNome()+" Membros: "+lista.getLista_pessoas()+"Votos: "+lista.getnVotos();
						resultado += "\n";
					}else {
						CandidatoIndividual cand = (CandidatoIndividual) candTemp;
						resultado += "\nNúmero candidato: "+cand.getId()+" - Nome: "+cand.getPessoa().getNome()+" - CC: "+cand.getPessoa().getNcc()+" - Votos: "+cand.getnVotos();
					}
				}
				
			}
			else {
				System.out.println(eleicao.getDataInicio()+"-"+eleicao.getDataFim()+": A decorrer");
			}
		}
		return resultado;
	
	}
}
