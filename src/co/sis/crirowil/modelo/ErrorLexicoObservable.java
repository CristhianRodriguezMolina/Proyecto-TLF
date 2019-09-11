package co.sis.crirowil.modelo;

import co.sis.crirowil.persistencia.ErrorLexico;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ErrorLexicoObservable {

	private StringProperty mensaje;
	
	private ErrorLexico errorLexico;
	
	public ErrorLexicoObservable(ErrorLexico errorLexico) {
		this.mensaje = new SimpleStringProperty(errorLexico.getMessage().toString());
		this.errorLexico = errorLexico;
	}

	public StringProperty getMensaje() {
		return mensaje;
	}

	public void setMensaje(StringProperty mensaje) {
		this.mensaje = mensaje;
	}

	public ErrorLexico getErrorLexico() {
		return errorLexico;
	}

	public void setErrorLexico(ErrorLexico errorLexico) {
		this.errorLexico = errorLexico;
	}
	
	
}
