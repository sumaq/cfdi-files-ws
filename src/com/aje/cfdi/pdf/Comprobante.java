package com.aje.cfdi.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.aje.common.ConnectStringDB;
import com.aje.common.OperatingSystem;
import com.aje.common.ResourcePath;

public class Comprobante {
	static Connection conn = null;
	Map parameters = null;
	ConnectStringDB aConn;

	public Comprobante(String[] params) {
		parameters = new HashMap();
		parameters.put("P_COMPANIA", new String(params[0]));
		parameters.put("P_SUCURSAL", new String(params[1]));
		parameters.put("P_EMISOR", new String(params[2]));
		parameters.put("P_DOCUCOMVTA", new String(params[3]));
		parameters.put("P_NROCOMVTA", new String(params[4]));
		parameters.put("P_SERIE", new String(params[5]));
		parameters.put("P_FOLIO", new String(params[6]));

		aConn = new ConnectStringDB(new String(params[0]));
	}

	public ByteArrayOutputStream getPDFStream() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Cargamos el driver JDBC
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("SQLServer JDBC Driver not found.");
			// System.exit(1);
		}
		try {
			conn = DriverManager.getConnection("jdbc:sqlserver://"
					+ aConn.getDataBaseHost().trim() + ":1433;"
					+ "databaseName=" + aConn.getDataBaseName().trim()
					+ ";user=" + aConn.getDataBaseUser().trim() + ";password="
					+ aConn.getDataBasePassword().trim() + ";");
			// .getConnection("jdbc:sqlserver://172.20.0.11:1433;databaseName=BDAJEMEX3;user=sqlerp;password=m9nP2010t;");

			conn.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Error de conexion: " + e.getMessage());
			// System.exit(4);
		}
		try {
			System.out.println(new File("").getAbsolutePath());
			OperatingSystem OS = new OperatingSystem();
			ResourcePath resource = new ResourcePath(this);
			String templateFile = resource.getWEBINFpath()
					+ OS.getOSPathDelimiter() + "conf"
					+ OS.getOSPathDelimiter() + "template"
					+ OS.getOSPathDelimiter() + "Factura.jrxml";

			JasperReport report = JasperCompileManager
					.compileReport(templateFile);
			// .compileReport("conf\\template\\Factura.jrxml");
			// .compileReport("D:\\app\\workspace\\CFDI\\src\\com\\aje\\cfdi\\template\\Factura.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report,
					parameters, conn);
			// Exporta el informe a PDF
			// JasperExportManager.exportReportToPdfFile(print,
			// "/home/wil/workspace/CFDI/src/com/aje/cfdi/template/Factura.pdf");

			JasperExportManager.exportReportToPdfStream(print, baos);

			// Para visualizar el pdf directamente desde java
			// JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.rollback();
					System.out.println("ROLLBACK EJECUTADO");
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return baos;
	}

}