package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import co.sis.crirowil.util.Util;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una funcion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Funcion {

	/**
	 * Nombre de la funcion
	 */
	private Token nombre;

	/**
	 * Lista de los parametros de una funcion
	 */
	private ArrayList<Parametro> listaParametros;

	/**
	 * Guarda el tipo de retorno de la funcion null es void
	 */
	private Token retorno;

	/**
	 * Lista de setencias ejecuta la funcion
	 */
	private BloqueSentencia bloqueSentencias;

	/**
	 * Contructor de la clase funcion
	 * 
	 * @param nombre
	 * @param listaParametros
	 * @param retorno
	 * @param bloqueSentencias
	 */
	public Funcion(Token nombre, ArrayList<Parametro> listaParametros, Token retorno,
			BloqueSentencia bloqueSentencias) {
		super();
		this.nombre = nombre;
		this.listaParametros = listaParametros;
		this.retorno = retorno;
		this.bloqueSentencias = bloqueSentencias;
	}

	/**
	 * @return the nombre
	 */
	public Token getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(Token nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the listaParametros
	 */
	public ArrayList<Parametro> getListaParametros() {
		return listaParametros;
	}

	/**
	 * @param listaParametros the listaParametros to set
	 */
	public void setListaParametros(ArrayList<Parametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

	/**
	 * @return the retorno
	 */
	public Token getRetorno() {
		return retorno;
	}

	/**
	 * @param retorno the retorno to set
	 */
	public void setRetorno(Token retorno) {
		this.retorno = retorno;
	}

	/**
	 * @return the bloqueSentencias
	 */
	public BloqueSentencia getBloqueSentencias() {
		return bloqueSentencias;
	}

	/**
	 * @param bloqueSentencias the bloqueSentencias to set
	 */
	public void setBloqueSentencias(BloqueSentencia bloqueSentencias) {
		this.bloqueSentencias = bloqueSentencias;
	}

	public TreeItem<String> getArbolVisual() {
			
		TreeItem<String> raiz = new TreeItem<String>("Funci�n");

		raiz.getChildren().add(new TreeItem<String>("Nombre: " + nombre.getPalabra()));

		if (retorno != null) {
			raiz.getChildren().add(new TreeItem<String>("Tipo de retorno: " + retorno.getPalabra()));
		} else {
			raiz.getChildren().add(new TreeItem<String>("Tipo de retorno: void"));
		}

		TreeItem<String> params = new TreeItem<String>("Par�metros");
		raiz.getChildren().add(params);

		for (Parametro parametro : listaParametros) {
			params.getChildren().add(parametro.getArbolVisual());
		}

		TreeItem<String> sentencias = new TreeItem<String>("Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentencias.getListaSentencias()) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}

		return raiz;
			
	}
	
	public ArrayList<String> getTiposParametros(){
		
		ArrayList<String> tipoParametros = new ArrayList<>();
		for(Parametro parametro: listaParametros) 
		{
			tipoParametros.add(parametro.obtenerTipo());
		}
		return tipoParametros;
		
	}

	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {
		ArrayList<String> tipoParametros = getTiposParametros();		

		if(retorno != null) {
			tablaSimbolos.guardarSimboloFuncion(nombre.getPalabra(), retorno.getPalabra(), tipoParametros);			
		}else {
			tablaSimbolos.guardarSimboloFuncion(nombre.getPalabra(), "void", tipoParametros);	
		}
		
		for(Parametro parametro: listaParametros) 
		{
			tablaSimbolos.guardarSimboloVariable(parametro.getNombre().getPalabra(), parametro.obtenerTipo(), parametro.getRetorno().getFila(), parametro.getRetorno().getColumna(), tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), tipoParametros));
		}

		
		for(Sentencia sentencia: bloqueSentencias.getListaSentencias()) 
		{
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), tipoParametros));
		}
	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {

		for(Sentencia sentencia: bloqueSentencias.getListaSentencias()) 
		{
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), getTiposParametros()));
		}
		
		if(retorno != null) {
			if(!(bloqueSentencias.getListaSentencias().get(bloqueSentencias.getListaSentencias().size()-1) instanceof Retorno)) {
				erroresSemanticos.add("El metodo "+nombre.getPalabra()+getTiposParametros().toString()+" no tiene retorno.");
			}else {
				String tipoRetorno = ((Retorno)bloqueSentencias.getListaSentencias().get(bloqueSentencias.getListaSentencias().size()-1)).getTipoRetorno(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), getTiposParametros()));
				
				if(!tipoRetorno.equals(retorno.getPalabra())) {
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir el tipo de retorno " +retorno.getPalabra()+ " a " + tipoRetorno);
				}
			}
		}else {
			
			for(Sentencia sentencia: bloqueSentencias.getListaSentencias()) 
			{
				if(sentencia instanceof Retorno) {
					erroresSemanticos.add("El metodo void "+nombre.getPalabra()+getTiposParametros()+" no puede retornar un valor.");
				}
			}
			
		}
		
	}

	public String getJavaCode() {
		String codigo = "";
		if(retorno != null) 
		{
			codigo = "public static " + Util.traducirTipo(retorno.getPalabra()) + " " + nombre.getPalabra() + "(";
		}
		else
		{
			codigo = "public static void " + nombre.getPalabra() + "(";
		}
		for(Parametro parametro: listaParametros) 
		{
			codigo += parametro.getJavaCode() + ", ";
		}
		
		if(listaParametros.size() > 0) 
		{
			codigo = codigo.substring(0, codigo.length() - 2) + ") {\r\n";			
		}
		else 
		{
			codigo += ") {\r\n";
		}
		for(Sentencia sentencia : bloqueSentencias.getListaSentencias()) 
		{
			codigo += sentencia.getJavaCode() + "\r\n";
		}
		codigo += "\n}";
		return codigo;
	}
}
