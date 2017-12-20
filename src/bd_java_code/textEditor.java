package bd_java_code;

import java.util.Date;
import java.util.Scanner;



public abstract class textEditor {

	public static String limitaString(String texto,int maxCaracteres){
		String vazio = " ";
		for (int i = 0;i<maxCaracteres;i++){
			vazio += " ";
		}
		return (texto+vazio).substring(0, maxCaracteres);
	}
	
	public static int pedeNumero(String texto,int minNum,int maxNum) {
		Scanner sc = new Scanner(System.in);
		String s;
		int num = 0;
		boolean teste = true;
		
		do {
			System.out.print(texto);
			s = sc.nextLine();
			try {
				num = Integer.parseInt(s);
				teste = false;
				if(num<minNum) {teste = true;System.out.println("O numero tera de ser maior que "+minNum);}
				if(num>maxNum) {teste = true;System.out.println("O numero tera de ser menor que "+maxNum);}
			}catch(NumberFormatException e) {
				teste = true;
				System.out.println("Formato invalido. Por favor coloque um numero.");
			}
			
		}while(teste);
		return num;
	}
	
	public static String leLinha(String texto) {
		Scanner sc = new Scanner(System.in);
		System.out.print(texto);
		String textoR = sc.nextLine();
		return textoR;
	}
	
	public static String formatNum (int num,int casas) {
		String texto = ""+num;
		while(texto.length()<casas) {
			texto = "0"+texto;
		}
		return texto;
	}
	
	public static String dataEleicao(){
		int ano = pedeNumero("ano: ", 1900, 2100);
		int mes = pedeNumero("mes: ", 1, 12);
		int dia = pedeNumero("dia: ", 1, 31);
		int hora = pedeNumero("hora: ", 1, 12);
		int minutos = pedeNumero("minutos: ", 0, 60);
		
		return String.format("%s:%s:%s %s:%s",
				formatNum(ano, 4),
				formatNum(mes, 2),
				formatNum(dia, 2),
				formatNum(hora, 2),
				formatNum(minutos, 2));
	}
	public static dataEleicao dataStringToData(String data){
		
		String [] dataLista = (data.split(" "));
		String [] data_2 = dataLista[0].split(":");
		if(data_2.length<2){data_2 = dataLista[0].split("-");}
		String [] horas = dataLista[1].split(":");
		dataEleicao eleicao = new dataEleicao();
		eleicao.ano = Integer.parseInt(data_2[0]);
		eleicao.mes = Integer.parseInt(data_2[1]);
		eleicao.dia = Integer.parseInt(data_2[2]);
		eleicao.hora = Integer.parseInt(horas[0]);
		eleicao.minuto = Integer.parseInt(horas[1]);
		
		return eleicao;
		
		
		
	}	
	
}
