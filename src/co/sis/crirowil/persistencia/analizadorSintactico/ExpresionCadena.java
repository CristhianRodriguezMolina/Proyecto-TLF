package co.sis.crirowil.persistencia.analizadorSintactico;
import co.sis.crirowil.persistencia.analizadorLexico.Token;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es ExpresionCadena y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ExpresionCadena extends Expresion{

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

	@Override
	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<String>("Expresion cadena");
		
		raiz.getChildren().add(new TreeItem<String>(cadenaCaracteres.getPalabra()));
		
		if(expresion != null)
			raiz.getChildren().add(expresion.getArbolVisual());
		
		return raiz;
	}
	
	
}
