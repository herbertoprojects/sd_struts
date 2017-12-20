package bd_java_code;

import java.sql.*;

public class LigacaoBD {
	
	private Connection conn;
	private String baseDadosURL;
	private String baseDadosUser;
	private String baseDadosPassword;
	boolean debug_opc = true;
	
	public LigacaoBD() {
		// TODO Auto-generated constructor stub
		baseDadosURL = "jdbc:oracle:thin:@localhost:1521:xe";
		baseDadosPassword = "proj";
		baseDadosUser = "proj";
		
		if(conectaBD()){//Serve para conectar a base de dados
			System.out.println("Ligado ao servidor: "+baseDadosURL);
		}
	}
	
	public void debug(String texto){
		if(debug_opc){
			System.out.println(texto);
		}
	}
	
	public boolean conectaBD (){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			return (conn = DriverManager.getConnection(baseDadosURL, baseDadosUser, baseDadosPassword)) != null;
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			  System.out.println(e.getMessage());
			  return false;
		}
	}
	
	public ResultSet executaSQL(String comando){return executaSQL(comando,0);}
	public ResultSet executaSQL(String comando, int num){
		debug(comando);
		try{
			Statement stm;
			if((stm = conn.createStatement())==null){
				if(num<5){
					this.conectaBD();
					return (executaSQL(comando,num+1));
				}
				System.out.println("Comando Invalido, ou conflito!");
				return null;
			}
			ResultSet resultado = stm.executeQuery(comando);
			return resultado;			
		}
		catch(SQLException e){
			if(num<5){
				this.conectaBD();
				return (executaSQL(comando,num+1));
			}
			System.out.println("Sem liagacao com a base de dados, ou conflito de comando!");
			return null;
		}
	}
	public boolean executaUpdateSQL(String comando){return executaUpdateSQL(comando,0);}
	public boolean executaUpdateSQL(String comando, int num){
		debug(comando);
		try{
			Statement stm;
			if((stm = conn.createStatement())==null){
				if(num<5){
					this.conectaBD();
					return (executaUpdateSQL(comando,num+1));
				}
				System.out.println("Comando Invalido, ou conflito!");
				return false;
			}
			boolean resultado = stm.executeUpdate(comando)!=0;
			stm.close();
			return resultado;
		}
		catch(SQLException|NullPointerException e){
			if(num<5){
				this.conectaBD();
				return (executaUpdateSQL(comando,num+1));
			}
			System.out.println("Sem liagacao com a base de dados, ou conflito de comando!");
			return false;
		}
	}
	
	public void imprimeResultSet (ResultSet res){
		if(res==null){System.out.println("Sem resultados");return;}
		try{
			ResultSetMetaData rsmd = res.getMetaData();
	        int columnsNumber = rsmd.getColumnCount();
	
	        for(int i = 1 ; i <= columnsNumber ; i++){
	            System.out.print(textEditor.limitaString(rsmd.getColumnName(i),30));
	        }
	        System.out.println("");
	        while (res.next()) {
	            for (int i = 1; i <= columnsNumber; i++) {
	                String columnValue = res.getString(i);
	                System.out.print(textEditor.limitaString(columnValue,30));
	            }
	            System.out.println("");
	        }
	        res.close();
		}catch (SQLException e){
			System.out.println("Sem resultados");
			return;
		}
	}
	
	public boolean inicioTransacao(){
		try {
			this.conn.setAutoCommit(false);
			return true;
		} catch (SQLException e) {
			if(this.conectaBD()){
				try {
					this.conn.setAutoCommit(false);
					return true;
				} catch (SQLException e1) {
					return false;
				}
			}else{
				return false;
			}
		}
	}
	public boolean fimTransacao(){
		try {
			this.conn.commit();
			this.conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			if(this.conectaBD()){
				try {
					this.conn.commit();
					this.conn.setAutoCommit(true);
					return true;
				} catch (SQLException e1) {
					return false;
				}
			}else{
				return false;
			}
		}
	}
	public boolean voltarTransacao(){
		try {
			this.conn.rollback();
			this.conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			if(this.conectaBD()){
				try {
					this.conn.rollback();
					this.conn.setAutoCommit(true);
					return true;
				} catch (SQLException e1) {
					return false;
				}
			}else{
				return false;
			}
		}
	}
}
