package co.sis.crirowil.modelo;

import co.sis.crirowil.persistencia.ErrorLexico;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ErrorLexicoObservable {

	private StringProperty mensaje;
	
	private StringProperty line;
	
	private ErrorLexico errorLexico;
	
	
	public ErrorLexicoObservable(ErrorLexico errorLexico) {
		this.mensaje = new SimpleStringProperty(errorLexico.getMessage());
		this.line = new SimpleStringProperty(errorLexico.getLine() + "");
		this.errorLexico = errorLexico;
	}
	
	

	/**
	 * @return the mensaje
	 */
	public StringProperty getMensaje() {
		return mensaje;
	}



	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(StringProperty mensaje) {
		this.mensaje = mensaje;
	}



	/**
	 * @return the errorLexico
	 */
	public ErrorLexico getErrorLexico() {
		return errorLexico;
	}



	/**
	 * @param errorLexico the errorLexico to set
	 */
	public void setErrorLexico(ErrorLexico errorLexico) {
		this.errorLexico = errorLexico;
	}



	/**
	 * @return the line
	 */
	public StringProperty getLine() {
		return line;
	}



	/**
	 * @param line the line to set
	 */
	public void setLine(StringProperty line) {
		this.line = line;
	}



	
	
	
}
