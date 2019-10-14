package co.sis.crirowil.modelo;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Permite Transforma un Token a formato Observale
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class TokenObservable {

	/**
	 * categoria observable de un Token
	 */
	private StringProperty categoria;
	/**
	 * palabra observable de una persona
	 */
	private StringProperty palabra;
	/**
	 * Token asociado
	 */
	private Token token;

	/**
	 * constructor que genera con Token observable con base a un Token
	 * 
	 * @param Token que se quiere volver observable
	 */
	public TokenObservable(Token token) {

		this.token = token;
		this.categoria = new SimpleStringProperty(token.getCategoria().toString());
		this.palabra = new SimpleStringProperty(token.getPalabra());
	}

	/**
	 * @return the categoria
	 */
	public StringProperty getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(StringProperty categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the palabra
	 */
	public StringProperty getPalabra() {
		return palabra;
	}

	/**
	 * @param palabra the palabra to set
	 */
	public void setPalabra(StringProperty palabra) {
		this.palabra = palabra;
	}

	/**
	 * @return the token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(Token token) {
		this.token = token;
	}

}
