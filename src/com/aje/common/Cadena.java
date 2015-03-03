package com.aje.common;

import com.aje.common.Cadena;

public class Cadena {

	public String extraerCadenaDelimitada (String pCadena, String pCharDelimitador, int pNroOcurrencia, String pDireccion){
	// Esta funcion devuelve una subcadena encontrada en la ocurrencia N
	// dentro de otra cadena.
	
	String word = pCharDelimitador;
	String text = pCadena;
	String result= "";
	int firstChar = 0;
	int lastChar = 0;
	int j = 0;
	int m = 0;
	int firstPosOcurrencia = 0;
	
	// Obtener posicion del par de valores a extraer segun el numero de ocurrencia dado
	for (int k = 1; (1!=0) ; k++ ){
	if (k%2!=0){ // si es impar, considerar primer par de valores
	m = m + 1;
	}
	if (m==pNroOcurrencia){
	firstPosOcurrencia = k;
	break;
	}
	}
	// Obtener ocurrencia empezando por la izquierda
	if (pDireccion == "LEFT"){
	// find all occurrences forward
	for (int i = -1; (i = text.indexOf(pCharDelimitador, i + 1)) != -1; ) {
	j = j + 1;
	if (j == firstPosOcurrencia){
	firstChar = i;
	}
	if (j == firstPosOcurrencia + 1){
	lastChar = i;
	}			
	}
	result = text.substring(firstChar + 1, lastChar);
	}
	// Obtener ocurrencia empezando por la derecha		
	if (pDireccion == "RIGHT"){
	// find all occurrences backward
	for (int i = text.length(); (i = text.lastIndexOf(word, i - 1)) != -1; ) {
	j = j + 1;
	if (j == firstPosOcurrencia){
	firstChar = i;
	}
	if (j == firstPosOcurrencia + 1){
	lastChar = i;
	}
	}
	result = text.substring(lastChar + 1, firstChar);
	}
	return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] params = {"EXEC USP_FEX_CFDI_GEN_FILE_ESTANDAR '0030','0001','1002','FXC',36715"};
		Cadena  cadena = new Cadena(); 
		String text = cadena.extraerCadenaDelimitada(params[0],"'",1,"LEFT"); 
		System.out.println(text);
	
	}

}
