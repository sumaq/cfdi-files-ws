package com.aje.download.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface

@WebService
@SOAPBinding(style = Style.RPC)
public interface FileText {

  // create a File in local server

  @WebMethod
  public int createFileText(String[] query);

}
