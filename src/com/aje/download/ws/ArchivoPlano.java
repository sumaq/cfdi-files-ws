package com.aje.download.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.aje.common.Cadena;
import com.aje.common.ConnectStringDB;

public class ArchivoPlano {
	String query;
	ConnectStringDB aConn;
	
	public ArchivoPlano (String pQuery){
		//System.out.println(pQuery);
		query = pQuery;
		Cadena  cadena = new Cadena(); 
		// Obtener compania como primera cadena del query separada por apostrofes
		String compania = cadena.extraerCadenaDelimitada(pQuery,"'",1,"LEFT"); 
		//System.out.println(compania);
		aConn = new ConnectStringDB(compania);
	}
	
	public int generateFile() {
	    int result = 0;
	    try {
	      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	      Connection con = DriverManager.getConnection("jdbc:sqlserver://" + aConn.getDataBaseHost().trim() + ":1433;"
	              + "databaseName=" + aConn.getDataBaseName().trim() +";user=" + aConn.getDataBaseUser().trim() + 
	              ";password="+ aConn.getDataBasePassword().trim() +";");
	      
	      //query = "EXEC USP_FEX_CFDI_GEN_FILE_ESTANDAR '0030','0001','1002','FXC',36715";
	      ResultSet rs = con.createStatement().executeQuery(query);

	      ResultSetMetaData rsmd = rs.getMetaData();
	      int colCount = rsmd.getColumnCount();
	      PrintStream out = null;
	      String fileName = "";
	      String content = "";
	      while (rs.next()) {
	        for (int i = 1; i <= colCount; i++) {
	          String columnName = rsmd.getColumnName(i);
	          Object value = rs.getObject(i);
	          if(columnName.equals("FILENAME")){
	        	  fileName = value.toString().trim();
        	  }
        	  if(columnName.equals("CONTENIDO")){
        		  content = value.toString();
        	  }
	          //System.out.println(value);
	        }
		    try {	  
		    	  out = new PrintStream(new FileOutputStream(fileName));
		    	  out.print(content);
	        }
			finally {
				    if (out != null) out.close();
			}
	      }
	      con.close();
	      rs.close();
	      result = 1;

	    } catch (Exception e) {
	      System.out.println(e);
	    }
	    return result;
	  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArchivoPlano ap = new ArchivoPlano("USP_FEX_CFDI_GEN_FILE_ESTANDAR",
		//							   		 "0030","0001","1002","FXC","36714");
		ArchivoPlano ap = new ArchivoPlano("EXEC USP_FEX_CFDI_GEN_FILE_ESTANDAR '0030','0001','1002','FXC',36713");
		ap.generateFile();
	}
}
