package com.aje.download.ws;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import com.aje.cfdi.pdf.Comprobante;
import com.aje.common.OperatingSystem;
import com.aje.common.ResourcePath;

//Service Implementation Bean
@MTOM
@WebService(endpointInterface = "com.aje.download.ws.FileServer")
public class FileServerImpl implements FileServer {

	@Override
	public DataHandler downloadFile(String[] fileName) {

		// Location of File in Web service
		OperatingSystem OS = new OperatingSystem();
		ResourcePath resource = new ResourcePath(this);
		String pdfFile = resource.getWEBINFpath() + OS.getOSPathDelimiter()
				+ "conf" + OS.getOSPathDelimiter() + "temp"
				+ OS.getOSPathDelimiter() + "aComprobante.pdf";

		// String[] params = { "0030", "0096", "1002", "FXC", "16661","","" };
		Comprobante comprobante = new Comprobante(fileName);
		ByteArrayOutputStream baos = comprobante.getPDFStream();
		try {

			// FileOutputStream fos = new FileOutputStream(new
			// File("D:\\aWil.pdf"));
			// FileOutputStream fos = new FileOutputStream(new
			// File("conf\\temp\\aComprobante.pdf"));
			FileOutputStream fos = new FileOutputStream(new File(pdfFile));
			baos.writeTo(fos);
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}

		// FileDataSource dataSource = new FileDataSource("D:\\aWil.pdf"); // +
		// fileName);
		// FileDataSource dataSource = new
		// FileDataSource("conf\\temp\\aComprobante.pdf"); // + fileName);
		FileDataSource dataSource = new FileDataSource(pdfFile); // + fileName);

		DataHandler fileDataHandler = new DataHandler(dataSource);
		return fileDataHandler;
	}

}
