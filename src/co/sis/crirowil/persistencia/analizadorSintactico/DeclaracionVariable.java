package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import co.sis.crirowil.util.Util;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencia de declaracion de variable y sus
 * componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class DeclaracionVariable extends Sentencia {

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

		if (asignacion != null) {
			raiz.getChildren().add(asignacion.getArbolVisual());
		}

		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		if (asignacion != null) {
			if (asignacion.getInvocacionFuncion() != null) {
				tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(),
						identificador.getFila(), identificador.getColumna(), ambito, asignacion.getInvocacionFuncion());
			} else if (asignacion.getArgumento() != null) {
				tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(),
						identificador.getFila(), identificador.getColumna(), ambito, asignacion.getArgumento());
			} else if (asignacion.getArreglo() != null) {
				tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(),
						identificador.getFila(), identificador.getColumna(), ambito, asignacion.getArreglo());
			} else {
				tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(),
						identificador.getFila(), identificador.getColumna(), ambito, asignacion.getMapa());
			}
		} else {
			tablaSimbolos.guardarSimboloVariable(identificador.getPalabra(), tipoDato.getPalabra(),
					identificador.getFila(), identificador.getColumna(), ambito);
		}
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		if (asignacion != null) {

			if (asignacion.getOperadorAsignacion().getPalabra().equals("=")) {

				Simbolo s = tablaSimbolos.buscarSimboloVariable(identificador.getPalabra(), ambito);

				if (asignacion.getArgumento() != null) {

					asignacion.getArgumento().analizarSemantica(tablaSimbolos, erroresSemanticos, ambito,
							identificador.getPalabra(), false);

				} else if (asignacion.getInvocacionFuncion() != null) {

					asignacion.getInvocacionFuncion().analizarSemantica(tablaSimbolos, erroresSemanticos, ambito,
							identificador.getPalabra());

				} else if (asignacion.getArreglo() != null) { // AQUIIIIII

					asignacion.getArreglo().analizarSemantica(s.getTipo(), tablaSimbolos, erroresSemanticos, ambito);

				} else if (asignacion.getLecturaDatos() != null) { // AQUIIIIII

					if (!s.getTipo().equals("cadena")) {
						erroresSemanticos.add("Tipo incorrecto: No se puede convertir de cadena a " + s.getTipo());
					}

				} else if (asignacion.getMapa() != null) { // AQUIIIIII

					asignacion.getMapa().analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
					;

				}

			} else {

				erroresSemanticos.add("Token erroneo en declaracion \""
						+ asignacion.getOperadorAsignacion().getPalabra() + "\", se esperaba =");

			}

		}

	}

	@Override
	public String getJavaCode() {
		String codigo = "";
		codigo = Util.traducirTipo(tipoDato.getPalabra()) + " " + identificador.getPalabra();
		
		if(asignacion != null) 
		{
			codigo += asignacion.getJavaCode(Util.traducirTipo(tipoDato.getPalabra()), identificador.getPalabra());
		}
		
		codigo += ";";
		return codigo;
	}
}
