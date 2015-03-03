/* Clase    : Connection
 * Autor    : Wilmer Reyes Alfaro
 * Revision : 22/06/2013 12:45
 * Funcion  : Permite obtener la cadena de conexi�n hacia la base de datos APM. 
 * 			  Se obtiene a partir del archivo config.xml de la carpeta conf del proyecto.
 * */
package com.aje.common;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConnectStringDB extends DefaultHandler {
	String xmlFileName;
	String tmpValue;
	String dataBaseHost;
	String dataBaseName;
	String dataBaseUser;
	String dataBasePassword;
	OperatingSystem OS = new OperatingSystem();
	Boolean fHost;
	Boolean fDBName;
	Boolean fDBUser;
	Boolean fDBPassword;

	public ConnectStringDB(String compania) {
		// this.xmlFileName = "/home/wil/workspace/APM/conf/config.xml" ;
		// this.xmlFileName = "C:\\Users\\wil\\workspace\\APM\\conf\\config.xml"
		// ;
		// this.xmlFileName = new File("").getAbsolutePath() +
		// "\\conf\\config.xml" ;

		ResourcePath resource = new ResourcePath(this);

		String DSFileName = "DS" + compania.trim() + ".xml";
		// Debe ejecutarse la clase dentro del home del proyecto
		// if (OS.isWindows()) {
		// this.xmlFileName = "conf\\datasource\\" + DSFileName; // Windows
		// }

		this.xmlFileName = resource.getWEBINFpath() + OS.getOSPathDelimiter()
				+ "conf" + OS.getOSPathDelimiter() + "datasource"
				+ OS.getOSPathDelimiter() + DSFileName;

		// if (OS.isUnix()) {
		// this.xmlFileName = "conf/datasource/" + DSFileName; // Linux
		// }
		parseDocument();
	}

	private void parseDocument() {
		// parse
		SAXParserFactory factory = SAXParserFactory.newInstance();
		fHost = false;
		fDBName = false;
		fDBUser = false;
		fDBPassword = false;

		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(this.xmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	@Override
	public void startElement(String s, String s1, String elementName,
			Attributes attributes) throws SAXException {

		if (elementName.equalsIgnoreCase("host")) {
			fHost = true;
		}
		if (elementName.equalsIgnoreCase("dbName")) {
			fDBName = true;
		}
		if (elementName.equalsIgnoreCase("user")) {
			fDBUser = true;
		}
		if (elementName.equalsIgnoreCase("password")) {
			fDBPassword = true;
		}

	}

	@Override
	public void endElement(String s, String s1, String element)
			throws SAXException {
		// if end of book element add to list

	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		if (fHost) {
			this.setDataBaseHost(new String(ac, i, j));
			fHost = false;
		}
		if (fDBName) {
			this.setDataBaseName(new String(ac, i, j));
			fDBName = false;
		}
		if (fDBUser) {
			this.setDataBaseUser(new String(ac, i, j));
			fDBUser = false;
		}
		if (fDBPassword) {
			this.setDataBasePassword(new String(ac, i, j));
			fDBPassword = false;
		}
	}

	public String getDataBaseHost() {
		return dataBaseHost;
	}

	public void setDataBaseHost(String dataBaseHost) {
		this.dataBaseHost = dataBaseHost;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getDataBaseUser() {
		return dataBaseUser;
	}

	public void setDataBaseUser(String dataBaseUser) {
		this.dataBaseUser = dataBaseUser;
	}

	public String getDataBasePassword() {
		return dataBasePassword;
	}

	public void setDataBasePassword(String dataBasePassword) {
		this.dataBasePassword = dataBasePassword;
	}

}
