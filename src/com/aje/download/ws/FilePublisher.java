package com.aje.download.ws;

import javax.xml.ws.Endpoint;

//Endpoint publisher

public class FilePublisher {
  public static void main(String[] args) {
    Endpoint.publish("http://localhost:9899/ws/file", new FileServerImpl());
    System.out.println("Server is published!");
    /*
     * ./wsimport.sh -keep http://localhost:9899/ws/file?wsdl –p com.aje.download.client
     */

  }

}
