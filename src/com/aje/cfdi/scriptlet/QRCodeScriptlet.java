package com.aje.cfdi.scriptlet;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeScriptlet extends JRDefaultScriptlet {
  public void afterDetailEval() throws JRScriptletException {
    QRCodeWriter writer = new QRCodeWriter();
    BitMatrix matrix = null;
    try {
      matrix = writer.encode(getFieldValue("CodeBar_text").toString(), BarcodeFormat.QR_CODE, 256,
          256);
      this.setVariableValue("BarCodeImage", MatrixToImageWriter.toBufferedImage(matrix));
    } catch (WriterException e) {
      e.printStackTrace();
    }
  }
}