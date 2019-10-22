package co.sis.crirowil.modelo;

import co.sis.crirowil.persistencia.analizadorSintactico.ErrorSintactico;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Usuario
 *
 */
/**
 * @author Usuario
 *
 */
public class ErrorSintacticoObservable {
	
	private StringProperty mensaje;
	
	private StringProperty fila;
	
	private StringProperty columna;
	
	private ErrorSintactico errorSintactico;
	
	public ErrorSintacticoObservable(ErrorSintactico errorSintactico) {
		this.mensaje = new SimpleStringProperty(errorSintactico.getMensaje());
		this.fila = new SimpleStringProperty(errorSintactico.getFila()+"");
		this.columna = new SimpleStringProperty(errorSintactico.getColumna()+"");
		this.errorSintactico = errorSintactico;
	}

	/**
	 * @return
	 */
	public StringProperty getMensaje() {
		return mensaje;
	}

	public void setMensaje(StringProperty mensaje) {
		this.mensaje = mensaje;
	}

	public StringProperty getFila() {
		return fila;
	}
	
	public void setFila(StringProperty fila) {
		this.fila = fila;
	}

	/**
	 * @return
	 */
	public StringProperty getColumna() {
		return columna;
	}

	public void setColumna(StringProperty columna) {
		this.columna = columna;
	}

	public ErrorSintactico getErrorSintactico() {
		return errorSintactico;
	}

	public void setErrorSintactico(ErrorSintactico errorSintactico) {
		this.errorSintactico = errorSintactico;
	}

	
}
