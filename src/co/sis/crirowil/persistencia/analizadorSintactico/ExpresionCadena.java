package co.sis.crirowil.persistencia.analizadorSintactico;
import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
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

	@Override
	public String obtenerTipo(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		return "cadena";
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito, String identificador, boolean declaracion) {
		Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador, ambito);
		if(s != null) 
		{
			if(!s.getTipo().equals("cadena")) 
			{
				erroresSemanticos.add("Tipo incorrecto: No se puede convertir de cadena a " + s.getTipo());
			}
		}
		else 
		{
			erroresSemanticos.add("La variable " + identificador
					+ " no existe en el ambito actual");
		}
		
	}
	
	
}
