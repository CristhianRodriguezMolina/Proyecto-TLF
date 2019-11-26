package co.sis.crirowil.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {
	
	public static String traducirTipo(String tipo) 
	{
		String tipoTraducido = "";
		if(tipo.equals("cadena")) 
		{
			tipoTraducido = "String";
		} else if(tipo.equals("real")) 
		{
			tipoTraducido = "Double";
		}else if(tipo.equals("entero")) 
		{
			tipoTraducido = "Integer";
		}else if(tipo.equals("bool")) 
		{
			tipoTraducido = "Boolean";
		}
		else if(tipo.equals("char"))
		{
			tipoTraducido = "Character";
		}
		else 
		{
			tipoTraducido = "HashMap";
		}
		return tipoTraducido;
	}
	
	public static String traducirOperadorLogico(String operador) 
	{
		String operadorTraducido = "";
		if(operador.equals("yy")) 
		{
			operadorTraducido = "&&";
		}
		else 
		{
			operadorTraducido = "||";
		}
		return operadorTraducido;
	}
	
	/**
	 * permite mostrar un texto informativo en pantalla
	 * 
	 * @param titulo  subtitulo de la alerta
	 * @param mensaje mensaje principal
	 */
	public static void mostrarMensaje(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Herbario");
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
}
