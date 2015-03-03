package com.aje.download.ws;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

//Service Implementation Bean
@MTOM
@WebService(endpointInterface = "com.aje.download.ws.FileText")
public class FileTextImpl implements FileText {

  @Override
  public int createFileText(String[] query) {
	  String _query = query[0];
	  int result = 0;
	  ArchivoPlano ap = new ArchivoPlano(_query);
	  result = ap.generateFile();
	  //System.out.println("Ejecutando: " + _query);
     return result;
  }

}
