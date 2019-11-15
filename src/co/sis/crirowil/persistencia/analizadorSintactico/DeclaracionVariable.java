package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencia de declaracion de variable y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class DeclaracionVariable extends Sentencia
{
	
	private Token tipoDato;
	
	private Token identificador;
	
	private Asignacion asignacion;

	/**
	 * @param tipoDato
	 * @param identificador
	 * @param asignacion
	 */
	public DeclaracionVariable(Token tipoDato, Token identificador, Asignacion asignacion) {
		super();
		this.tipoDato = tipoDato;
		this.identificador = identificador;
		this.asignacion = asignacion;
	}
	
	/**
	 * @return the tipoRetorno
	 */
	public Token getTipoRetorno() {
		return tipoDato;
	}

	/**
	 * @param tipoRetorno the tipoRetorno to set
	 */
	public void setTipoRetorno(Token tipoRetorno) {
		this.tipoDato = tipoRetorno;
	}

	/**
	 * @return the identificador
	 */
	public Token getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(Token identificador) {
		this.identificador = identificador;
	}

	/**
	 * @return the asignacion
	 */
	public Asignacion getAsignacion() {
		return asignacion;
	}

	/**
	 * @param asignacion the asignacion to set
	 */
	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}
	
	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<>("Declaracion de variable");
		
		raiz.getChildren().add(new TreeItem<>(identificador.getPalabra() + " : " + tipoDato.getPalabra()));
		
		if(asignacion != null) {
			raiz.getChildren().add(asignacion.getArbolVisual());
		}
		
		
		
		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		if(asignacion.getInvocacionFuncion() != null) 
		{
			tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(), identificador.getFila(), identificador.getColumna(), ambito, asignacion.getInvocacionFuncion());
		}
		else if(asignacion.getArgumento() != null) 
		{
			tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(), identificador.getFila(), identificador.getColumna(), ambito, asignacion.getArgumento());
		}
		else if(asignacion.getArreglo() != null) 
		{
			tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(), identificador.getFila(), identificador.getColumna(), ambito, asignacion.getArreglo());
		}
		else 
		{
			tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(), identificador.getFila(), identificador.getColumna(), ambito, asignacion.getMapa());
		}
		
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}
}
