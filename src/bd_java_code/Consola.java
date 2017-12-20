package bd_java_code;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Consola extends UnicastRemoteObject {
	
	private static final long serialVersionUID = -2292307713953902247L;
	private RMI_1 comunicacao;
	public int portoConsola = 9000;
	
	public Consola () throws RemoteException {
		super();
		
	}

	public static void main(String[] args) {
		try {
		
			Consola consola = new Consola();
			consola.comunicacao = (RMI_1) Naming.lookup("rmi://localhost:"+consola.portoConsola+"/rmi");
			consola.menuInicial();
			
		}
		catch (RemoteException | MalformedURLException | NotBoundException e){
			System.out.println("Erro ligacao RMI");
			
		}
	}
	
	public void menuInicial() throws RemoteException {
		/**
		 * menu inicial
		 * @return void saída do menu inicial
		 */
		while(true) {
			System.out.println();
			System.out.println("------------MAIN MENU-----------");
			System.out.println("1- Gerir utilizador");						//adicionar, remover, consultar
			System.out.println("2- Gerir faculdades");						//adicionar, remover, consultar
			System.out.println("3- Gerir departamentos");					//adicionar, remover, consultar
			System.out.println("4- Gerir eleições");						//criar eleição, adicionar listas, remover listas, consultar listas, consultar eleições, remover eleições			System.out.println("6- Voto antecipado");						//autenticar a pessoa
			System.out.println("0- Sair");
			
			switch (textEditor.pedeNumero("Opção: ", 0, 6)) {
				case 1:
					menuUtil();	
					break;
				case 2:
					menuFac();
					break;
				case 3:
					menuDep();
					break;
				case 4:
					menuEleicoes();
					break;
				case 0:
					return;
					
			}
			textEditor.leLinha("Continuar...");
		}
	}
	
	public void menuUtil() throws RemoteException{
		while(true) {
			System.out.println();
			System.out.println("------------Sub Menu do Utilizador------------");
			System.out.println("1- Adicionar utilizador");
			System.out.println("2- Remover utilizador");
			System.out.println("3- Consultar utilizador");
			System.out.println("4- Login utilizador");
			System.out.println("0- sair");
			
			switch(textEditor.pedeNumero("Opção: ", 0, 4)) {
				case 1:
					Pessoa pessoa = pedePessoa();
					if(pessoa!=null){
						if(comunicacao.registaPessoa(pessoa)){
							System.out.println("Utilizador Adicionado");
						}
						else{
							System.out.println("Utilizador Não Adicionado");
						}
					}
					
					break;
				case 2:
					int ncc = textEditor.pedeNumero("Ncc: ", 9999999, 100000000);
					if(comunicacao.removePessoa(comunicacao.procuraPessoa(ncc))){
						System.out.println("Utilizador Removido");
					}else{
						System.out.println("Utilizador não Removido");
					}
					break;
				case 3:
					int ncc1 = textEditor.pedeNumero("Ncc: ", 9999999, 100000000);
					Pessoa pessoa1 = comunicacao.procuraPessoa(ncc1);
					System.out.println("Nome: "+pessoa1.getNome());
					System.out.println("Departamento: " +pessoa1.getDep()!=null?pessoa1.getDep().getNome():"Sem Departamento");
					System.out.println("Faculdade: " +pessoa1.getFac()!=null?pessoa1.getFac().getNome():"Sem Faculdade");

					break;
					
				case 4:
					int ncc2 = textEditor.pedeNumero("Ncc: ", 9999999, 100000000);
					String pass = textEditor.leLinha("Password: ");
					comunicacao.loginPessoa(ncc2, pass);
					System.out.println("Login feito");
					break;
				case 0:
					return;
				
			}
			textEditor.leLinha("Continuar...");
		}
		
	}
	
	
	public void menuFac()throws RemoteException{
		while(true) {
		 	System.out.println("------------Sub Menu das Faculdades------------");
			System.out.println("1- Adicionar faculdade");
			System.out.println("2- Remover faculdade");
			System.out.println("3- Consultar faculdade");
			System.out.println("0- Sair");
			switch(textEditor.pedeNumero("Opção: ", 0, 3)) {
				
			case 1:
				Faculdade fac1 = pedeFaculdade();
				if(fac1!=null){
					if(comunicacao.addFaculdade(fac1)){
						System.out.println("Faculdade adicionada com sucesso");
					}else{
						System.out.println("Faculdade não adicionada");
					}
				}
				break;
			case 2:
				Faculdade fac = escolheFaculdade();
				if(fac!=null){
					if(comunicacao.removeFaculdade(fac)){
						System.out.println("Faculdade Removida");
					}else{
						System.out.println("Faculdade não removida");
					}
				}
				
				break;
			case 3:
				for(Faculdade facTemp:comunicacao.ListFaculdades()){
					System.out.printf("%s %s\n", facTemp.getSigla(),facTemp.getNome());
				}
				break;
				
			case 0:
				return;
			
			}
			textEditor.leLinha("Continuar...");
		}
	}

	public void menuDep() throws RemoteException{
		while(true) {
			System.out.println("------------Sub Menu dos Departamentos------------");
			System.out.println("1- Adicionar departamento");
			System.out.println("2- Remover departamento");
			System.out.println("3- Consultar Departamento");
			System.out.println("0- Sair");
			
			switch(textEditor.pedeNumero("Opção: ", 0, 3)) {
			
				case 1:
					Departamento dep = pedeDepartamento();
					if(dep!=null){
						if(comunicacao.addDepartamento(dep)){
							System.out.println("Departamento adicionado");
						}else{
							System.out.println("Departamento não adicionado");
						}
					}
					break;
				case 2:
					Departamento dep1 = escolheDepartamento();
					if(dep1!=null){
						if(comunicacao.removeDepartamento(dep1)){
							System.out.println("Departamento removido com sucesso");
						}
						else{
							System.out.println("Departemnto não removido");
						}
					}
					break;
					
				case 3:
					for(Faculdade facTemp:comunicacao.ListFaculdades()){
						System.out.printf("%s %s\n", facTemp.getSigla(),facTemp.getNome());
						for(Departamento depTemp:comunicacao.ListDepartamentos(facTemp)){
							System.out.printf("    %s %s\n", depTemp.getSigla(),depTemp.getNome());
						}
						System.out.println("");
					}
					break;
				
				case 0:
					return;
				
					
			}
			textEditor.leLinha("Continuar...");
		}
		
		
	}
	
	public void menuEleicoes() throws RemoteException {
		while(true) {
			System.out.println("------------Sub Menu das Eleições------------");
			
			System.out.println(" 1- Criar eleição");
			System.out.println(" 2- Remover eleições");
			System.out.println(" 3- Consultar eleições");
			System.out.println(" 4- Adicionar listas");
			System.out.println(" 5- Adicionar Candidato Individual");
			System.out.println(" 6- Remover Candidatos");
			System.out.println(" 7- Consultar Candidatos");
			System.out.println(" 8- Adicionar mesa de voto");
			System.out.println(" 9- remover mesa de voto");
			System.out.println("10- Consultar mesas de voto");
			System.out.println(" 0- Sair");
			
			switch(textEditor.pedeNumero("Opção: ", 0, 10)) {
				case 1:
					Eleicao eleicao = pedeEleicao();
					if(eleicao!=null){
						if(comunicacao.novaEleicao(eleicao)){
							System.out.println("Eleição criada com sucesso");
						}else{
							System.out.println("Eleição não criada");
						}
					}
					break;
				case 2:
					Eleicao eleicao1 = escolheEleicao();
					if(eleicao1!=null){
						if(comunicacao.removerEleicao(eleicao1)){
							System.out.println("Eleicao removida com sucesso");
						}else{
							System.out.println("Eleicao não removida");
						}
					}
					break;
				case 3:
					ArrayList<Eleicao> listaEleicao = comunicacao.listaEleicao();
					for (Eleicao eleiTemp:listaEleicao){
						System.out.println(comunicacao.detalheEleicao(eleiTemp));
					}
					break;
				case 4:
					Eleicao eleicao2 = escolheEleicao();
					Lista lista = pedeLista(eleicao2);
					if(eleicao2.getTipo().equalsIgnoreCase("faculdade") || 
							eleicao2.getTipo().equalsIgnoreCase("departamento")||
									eleicao2.getTipo().equalsIgnoreCase("conselhoGeral")){
						System.out.println("Esta eleicao não permite listas");
					}else{
					if(lista!=null && eleicao2!=null){
						if(comunicacao.addCandidato(lista, eleicao2)){
							System.out.println("Lista adiciona com sucesso");
						}else{
							System.out.println("Lista não adicionada");
						}
					}}
					break;
				case 5:
					Eleicao eleicao5 = escolheEleicao();
					CandidatoIndividual candInd = pedeCandIndividual(eleicao5);
					
					if(eleicao5.getTipo().equalsIgnoreCase("faculdade") || 
							eleicao5.getTipo().equalsIgnoreCase("departamento")||
									eleicao5.getTipo().equalsIgnoreCase("conselhoGeral")){
						if(candInd!=null){
							if(comunicacao.addCandidato(candInd, eleicao5)){
								System.out.println("Candidato adicionado com sucesso");
							}else{
								System.out.println("Candidato não adicionado");
							}
						}
					
					}else{
						System.out.println("Esta eleicao não permite candidatos individuais");
					}
					break;
				case 6:
					Eleicao eleicao3 = escolheEleicao();
					Candidatos cand = escolheCandidato(eleicao3);
					if(cand!=null){
						if(comunicacao.removeCandidato(cand, eleicao3)){
							System.out.println("Lista Removida");
						}
						else{
							System.out.println("Lista não removida");
						}
					}
					
					break;
				case 7:
					Eleicao eleicao4 = escolheEleicao();
					for(Candidatos candTemp:comunicacao.listaCandidatos(eleicao4)){
						if(candTemp.getTipo().equalsIgnoreCase("lista")){
							Lista listaTemp = (Lista) candTemp;
							System.out.println("Lista: "+listaTemp.getNome());
							System.out.println("Votos: "+listaTemp.getnVotos());
							for(PessoaLista pessoaTemp:listaTemp.getLista_pessoas()){
								System.out.println("Cargo: "+pessoaTemp.getCargo()+" Pessoa: "+pessoaTemp.getPessoa().getNcc()+
										" "+pessoaTemp.getPessoa().getNcc());
							}
						}
						else{
							CandidatoIndividual candIndTemp = (CandidatoIndividual) candTemp;
							System.out.println("Candidato: "+candIndTemp.getPessoa().getNome()+" "+candIndTemp.getPessoa().getNcc());
							System.out.println("Votos: "+candIndTemp.getnVotos());
							System.out.println("");
						}
					}
					break;
				case 8:
					MesaVoto mesa1 = pedeMesaVoto();
					if(mesa1!=null){
						if(comunicacao.addMesaVoto(mesa1)){
							System.out.println("Mesa adiciona com sucesso");
						}
						else{
							System.out.println("Mesa não adicionada");
						}
					}
					break;
				case 9:
					Eleicao eleicao6 = escolheEleicao();
					MesaVoto mesa2 = escolheMesavoto(eleicao6);
					if(mesa2!=null){
						if(comunicacao.removeMesaVoto(mesa2)){
							System.out.println("Mesa removida");
						}else{
							System.out.println("Mesa não removida");
						}
					}
					break;
				case 10:
					Eleicao eleicao7 = escolheEleicao();
					for(MesaVoto mesaTemp:comunicacao.listMesaVoto(eleicao7, null)){
						System.out.println("Mesa de voto: "+mesaTemp.getUsername() + " - "+mesaTemp.getDep().getSigla()+
								" - "+mesaTemp.getDep().getFac().getSigla());
						
					}
					break;
				case 0:
					return;
			}
			textEditor.leLinha("Continuar...");
		}
		
	}
	
	public Pessoa pedePessoa() throws RemoteException{
		Pessoa pessoa = new Pessoa();
		pessoa.setNcc(textEditor.pedeNumero("Ncc: ", 9999999, 100000000));
		pessoa.setNome(textEditor.leLinha("Nome: "));
		pessoa.setMorada(textEditor.leLinha("Morada: "));
		pessoa.setSenha(textEditor.leLinha("Senha: "));
		pessoa.setTelefone(textEditor.pedeNumero("Telefone: ", 99999999, 1000000000));
		pessoa.setDep(escolheDepartamento());
		pessoa.setFac(pessoa.getDep().getFac());
		System.out.println("Cargo: ");
		System.out.println("1- Professor");
		System.out.println("2- Aluno");
		System.out.println("3- Funcionario");
		switch (textEditor.pedeNumero("opção:" , 1, 3)) {
			case 1:
				pessoa.setCargo("professor");
				break;
			case 2:
				pessoa.setCargo("aluno");
				break;
			case 3:
				pessoa.setCargo("funcionario");
				break;
		}
		pessoa.setStatus(0);
		pessoa.setFacebook_id(null);
		
		System.out.println("Estatuto: ");
		System.out.println("0- Utilizador");
		System.out.println("1- Administrador");
		switch (textEditor.pedeNumero("opção:" , 0, 1)) {
			case 0:
				pessoa.setEstatuto(0);
				break;
			case 1:
				pessoa.setEstatuto(1);
				break;
		}
		
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(textEditor.pedeNumero("opção: ", 0, 1)==1){
			return pessoa;
		}
		return null;
	}
	public Faculdade pedeFaculdade(){
		Faculdade fac = new Faculdade();
		fac.setSigla(textEditor.leLinha("Sigla: "));
		fac.setNome(textEditor.leLinha("Nome: "));
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(textEditor.pedeNumero("opção: ", 0, 1)==1){
			return fac;
		}
		return null;
	}
	public Departamento pedeDepartamento() throws RemoteException{
		Departamento dep = new Departamento();
		dep.setSigla(textEditor.leLinha("Sigla: "));
		dep.setNome(textEditor.leLinha("Nome: "));
		dep.setFac(escolheFaculdade());
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(textEditor.pedeNumero("opção: ", 0, 1)==1){
			return dep;
		}
		return null;
	}
	public Faculdade escolheFaculdade() throws RemoteException{
		int num = 0;
		ArrayList<Faculdade> faculdades = comunicacao.ListFaculdades();
		for(Faculdade facTemp:faculdades){
			System.out.println(num+"- "+facTemp.getSigla()+" "+facTemp.getNome());
			num ++;
		}
		return faculdades.get(textEditor.pedeNumero("Numero: ",0, num-1));
	}
	public Departamento escolheDepartamento() throws RemoteException{
		int num = 0;
		Faculdade fac = escolheFaculdade();
		ArrayList<Departamento> departamentos = comunicacao.ListDepartamentos(fac);
		for(Departamento depTemp:departamentos){
			System.out.printf("%d- %s %s\n",num, depTemp.getSigla(),depTemp.getNome());
			num++;
		}
		return departamentos.get(textEditor.pedeNumero("Numero: ", 0, num-1));
	}
	public Eleicao pedeEleicao()throws RemoteException{
		Eleicao eleicao = new Eleicao();
		eleicao.setCandidatos(null);
		eleicao.setDataInicio(textEditor.dataEleicao());
		eleicao.setDataFim(textEditor.dataEleicao());
		eleicao.setTitulo(textEditor.leLinha("Titulo: "));
		eleicao.setDescricao(textEditor.leLinha("Descricão: "));
		eleicao.setnVotoBNA(0);
		System.out.println("1- faculdade");
		System.out.println("2- departamento");
		System.out.println("3- conselhoGeral");
		System.out.println("4- nucleo");
		switch (textEditor.pedeNumero("Opção: ", 1, 4)) {
			case 1:
				eleicao.setTipo("faculdade");
				eleicao.setDep(null);
				eleicao.setFac(escolheFaculdade());
				break;
			case 2:
				eleicao.setTipo("departamento");
				eleicao.setDep(escolheDepartamento());
				eleicao.setFac(eleicao.getDep().getFac());
				break;
			case 3:
				eleicao.setTipo("conselhoGeral");
				eleicao.setDep(null);
				eleicao.setFac(null);
				break;
			case 4:
				eleicao.setTipo("nucleo");
				eleicao.setDep(escolheDepartamento());
				eleicao.setFac(eleicao.getDep().getFac());
				break;
		}
		System.out.println("1- Confirmar");
		System.out.println("0- Cancelar");
		if(textEditor.pedeNumero("opção: ", 0, 1)==1){
			return eleicao;
		}
		return null;
	}
	public Eleicao escolheEleicao() throws RemoteException{
		int num = 0;
		ArrayList<Eleicao>listaEleicao = comunicacao.listaEleicao();
		if(listaEleicao==null){return null;}
		for(Eleicao eleicaoTemp:listaEleicao){
			System.out.printf("%d %s %s %s\n",num,eleicaoTemp.getDataInicio(),eleicaoTemp.getDataFim(),eleicaoTemp.getTitulo());
			num++;
		}
		return listaEleicao.get(textEditor.pedeNumero("Opção: ", 0, num-1));
	}
	public Lista pedeLista(Eleicao eleicao) throws RemoteException{
		Lista lista = new Lista();
		lista.setEleicao(eleicao);
		lista.setNome(textEditor.leLinha("Nome: "));
		ArrayList<PessoaLista> pessoaLista = new ArrayList<PessoaLista>();
		while(true){
			System.out.println("1 - Adicionar pessoa");
			System.out.println("0 - Concluido");
			if(textEditor.pedeNumero("Opção: ", 0, 1)==0){
				break;
			}
			PessoaLista pessoaL = new PessoaLista();
			pessoaL.setPessoa(comunicacao.procuraPessoa(textEditor.pedeNumero("Ncc: ", 9999999, 100000000)));
			pessoaL.setCargo(textEditor.leLinha("Cargo: "));
			pessoaLista.add(pessoaL);
		}
		lista.setLista_pessoas(pessoaLista);
		return lista;
	}
	public CandidatoIndividual pedeCandIndividual(Eleicao eleicao) throws RemoteException{
		CandidatoIndividual candInd = new CandidatoIndividual();
		candInd.setEleicao(eleicao);
		candInd.setPessoa(comunicacao.procuraPessoa(textEditor.pedeNumero("Ncc: ", 9999999, 100000000)));
		return candInd;
	}
	public Candidatos escolheCandidato(Eleicao eleicao){
		int num = 0;
		for(Candidatos cand:eleicao.getCandidatos()){
			if(cand.getTipo().equalsIgnoreCase("lista")){
				Lista lista = (Lista) cand;
				System.out.printf("%n %s %s\n",num,lista.getId(),lista.getNome());
			}else{
				CandidatoIndividual candIn = (CandidatoIndividual) cand;
				System.out.printf("%n %s %s\n",num,candIn.getId(),candIn.getPessoa().getNome());
				
			}
			num++;
		}
		
		return eleicao.getCandidatos().get(textEditor.pedeNumero("Opção: ", 0, num-1));
	}
	public MesaVoto pedeMesaVoto()throws RemoteException{
		MesaVoto mesa = new MesaVoto();
		mesa.setDep(escolheDepartamento());
		mesa.setEleicao(escolheEleicao());
		mesa.setUsername(textEditor.leLinha("Username: "));
		mesa.setPassword(textEditor.leLinha("Password: "));
		return mesa;
	}
	public MesaVoto escolheMesavoto(Eleicao eleicao) throws RemoteException{
		int num = 0;
		ArrayList <MesaVoto> mesasDeVoto = comunicacao.listMesaVoto(eleicao,null);
		for(MesaVoto mesaTemp:mesasDeVoto){
			System.out.printf("%d %s %s\n",num,mesaTemp.getDep().getSigla(),mesaTemp.getUsername());
			num++;
		}
		return mesasDeVoto.get(textEditor.pedeNumero("Opção: ", 0, num-1));
	}

}
