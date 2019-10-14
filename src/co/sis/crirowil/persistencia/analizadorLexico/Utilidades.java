package co.sis.crirowil.persistencia.analizadorLexico;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilidades {

	/**
	 * permite mostrar un texto informativo en pantalla
	 * 
	 * @param titulo  subtitulo de la alerta
	 * @param mensaje mensaje principal
	 */
	public static void mostrarMensaje(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Analizador lexico");
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
	
}
