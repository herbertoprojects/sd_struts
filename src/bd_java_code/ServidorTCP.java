package bd_java_code;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServidorTCP {
	public RMI_1 comunicacao;
	public int portoConsola = 9000;
	public int portoTcpServer = 8000;
	public Eleicao eleicao;
	public String user;
	public String pass;
	public MesaVoto mesa;
	public boolean ativo = true;
	
	public ArrayList<Pessoa> pessoasParaVotar;
	
	public ServidorTCP() {
		// TODO Auto-generated constructor stub
		pessoasParaVotar = new ArrayList<Pessoa>();
		try {
			comunicacao = (RMI_1) Naming.lookup("rmi://localhost:"+portoConsola+"/tcpServer");
			
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void main(String[] args){
		ServidorTCP server = new ServidorTCP();
		server.ligar_base();
	}
	
	public void ligar_base(){		
		try {
			System.out.println("Escolher Eleição:");
			int num = 0;
			ArrayList<Eleicao> eleicoesValidas = comunicacao.listaEleicaoDecorrer();
			if(eleicoesValidas==null){return;}
			for(Eleicao eleiTemp:eleicoesValidas){
				num++;
				System.out.println(num+"-\t"+eleiTemp.getTitulo()+"\t"+eleiTemp.getTipo());
			}
			System.out.println("0-\t cancelar");
			int opc = textEditor.pedeNumero("Opcao: ", 0, num);
			if(opc==0){
				return;
			}
			eleicao = eleicoesValidas.get(opc-1);
			if(autenticar()){
				(new Thread(){ public void run(){aceitaLigacoes();}}).start();
				pedeUser();
			}else{
				ligar_base();
				return;
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public boolean autenticar() throws RemoteException{
		this.user = textEditor.leLinha("Username: ");
		this.pass = textEditor.leLinha("Password: ");
		this.mesa = comunicacao.ligarServidor(eleicao,this.user, this.pass);
		return this.mesa!=null;
	}
	public void pedeUser()throws RemoteException{
		int ncc;
		while(true){
			ncc = textEditor.pedeNumero("Ncc: ", 9999999, 1000000000);
			
			if(comunicacao.testaVotar(mesa, ncc)){
					Pessoa pessoa = comunicacao.procuraPessoa(ncc);
					if(pessoa!=null){
						this.pessoasParaVotar.add(pessoa);
				}
			}
			else{
				System.out.println("Utilizador não autorizador");
			}
		}
	}
	
	public void aceitaLigacoes(){
		try {
			ServerSocket serverSocket = new ServerSocket(portoTcpServer);
			while(ativo){
				Socket cliente = serverSocket.accept();
				new terminalVoto(this, cliente).start();
			}
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 