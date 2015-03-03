package com.aje.download.ws;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface

@WebService
@SOAPBinding(style = Style.RPC)
public interface FileServer {

  // download a File from server

  @WebMethod
  public DataHandler downloadFile(String[] fileName);

}
