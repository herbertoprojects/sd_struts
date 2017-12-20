package bd_java_code;

import java.util.Calendar;

import sun.util.calendar.CalendarDate;

public class dataEleicao {
	public int ano;
	public int mes;
	public int dia;
	public int hora;
	public int minuto;
	
	
	public dataEleicao() {
		// TODO Auto-generated constructor stub
		
	}

	
	public boolean maior_data (dataEleicao data){
		if(this.ano < data.ano) {return false;}
		if(this.ano > data.ano) {return true;}
		if(this.mes < data.mes) {return false;}
		if(this.mes > data.mes) {return true;}
		if(this.dia < data.dia) {return false;}
		if(this.dia > data.dia) {return true;}
		if(this.hora < data.hora) {return false;}
		if(this.hora > data.hora) {return true;}
		if(this.minuto < data.minuto) {return false;}
		if(this.minuto > data.minuto) {return true;}
		
		return false;
	
	}
}
