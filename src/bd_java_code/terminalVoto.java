package bd_java_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

public class terminalVoto extends Thread {
	public ServidorTCP server;
	private Pessoa pessoa;
	private Socket socketTerminal;
	
	private BufferedReader readMesa;
	private PrintWriter writeMesa;
	
	public terminalVoto(ServidorTCP server,Socket socket) {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socketTerminal = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
				if(server.ativo){
					if(!server.pessoasParaVotar.isEmpty()){
						pessoa = server.pessoasParaVotar.remove(0);
						comunicacao();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					return;
				}
		}
	}
	
	private void comunicacao(){
		try {
			readMesa = new BufferedReader(new InputStreamReader(this.socketTerminal.getInputStream()));
			writeMesa = new PrintWriter(this.socketTerminal.getOutputStream(), true);
			int num = 0;
			do{
				if(num==5){
					writeMesa.println("Tentativas maximas exedidas");				
				}
				writeMesa.println("Utilizador: "+pessoa.getNcc()+"\n Senha: \nfechar");
				
			}while(!server.comunicacao.validaUser(server.mesa, pessoa.getNcc(), readMesa.readLine()));
			
				try{
					int num1 = 1;
					String comando = "Votar: \n";
					ArrayList<Candidatos> cand = server.comunicacao.listaCandidatos(server.eleicao);
					for(Candidatos candTemp:cand){
						if(candTemp.getTipo().equalsIgnoreCase("lista")){
							Lista lista = (Lista) candTemp;
							comando+= "\n"+num1+"\t"+lista.getNome();
						}else{
							CandidatoIndividual candIndTemp = (CandidatoIndividual) candTemp;
							comando+= "\n"+num1+"\t"+candIndTemp.getPessoa().getNome();
						}
						num1++;
					}
					writeMesa.println(comando+"fechar");
					int valor = Integer.parseInt(readMesa.readLine());
					if(valor<=num1&&valor>0){
						server.comunicacao.votar(server.mesa, cand.get(num1-1), pessoa.getNcc(), pessoa.getSenha());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
