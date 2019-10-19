package co.sis.crirowil.persistencia.analizadorSintactico;
import co.sis.crirowil.persistencia.analizadorLexico.Token;

public class ExpresionCadena {

	private Token cadenaCaracteres;
	private Expresion expresion;
	
	public ExpresionCadena(Token cadenaCaracteres, Expresion expresion) {
		super();
		this.cadenaCaracteres = cadenaCaracteres;
		this.expresion = expresion;
	}
	
	public ExpresionCadena(Token cadenaCaracteres) {
		super();
		this.cadenaCaracteres = cadenaCaracteres;
	}

	public Token getCadenaCaracteres() {
		return cadenaCaracteres;
	}

	public void setCadenaCaracteres(Token cadenaCaracteres) {
		this.cadenaCaracteres = cadenaCaracteres;
	}

	public Expresion getExpresion() {
		return expresion;
	}

	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
}
