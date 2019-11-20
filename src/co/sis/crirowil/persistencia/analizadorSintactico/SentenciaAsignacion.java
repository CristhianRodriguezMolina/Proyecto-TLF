package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;


/**
 * Clase que describe que es una sentencia de asignacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class SentenciaAsignacion extends Sentencia{
	
	private Token nombre;
	private Asignacion asignacion;
	
	
	
	/**
	 * @param nombre
	 * @param asignacion
	 */
	public SentenciaAsignacion(Token nombre, Asignacion asignacion) {
		super();
		this.nombre = nombre;
		this.asignacion = asignacion;
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
		TreeItem<String> raiz = new TreeItem<String>("Sentencia de Asignacion");
		raiz.getChildren().add(new TreeItem<String>("Variable: " + nombre.getPalabra()));
		raiz.getChildren().add(asignacion.getArbolVisual());
		return raiz;
	}
	
	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		Simbolo simbolo = tablaSimbolos.buscarSimboloVariable(nombre.getPalabra(), ambito);
		if(simbolo != null) 
		{
			if(asignacion.getInvocacionFuncion() != null) 
			{
				simbolo.setInvocacionFuncion(asignacion.getInvocacionFuncion());
			}
			else if(asignacion.getArgumento() != null) 
			{
				simbolo.setArgumento(asignacion.getArgumento());
			}
			else if(asignacion.getArreglo() != null) 
			{
				simbolo.setArreglo(asignacion.getArreglo());
			}
			else 
			{
				simbolo.setMapa(asignacion.getMapa());
			}			
		}
		else 
		{
			erroresSemanticos.add("La variable " + nombre.getPalabra() + " no ha sido declarada previamente");
		}
	}
	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		Simbolo s = tablaSimbolos.buscarSimboloVariable(nombre.getPalabra(), ambito);
		Expresion expresion = asignacion.getArgumento().getExpresion();
		Arreglo arreglo = asignacion.getArreglo();
		
		if(s==null) {
			erroresSemanticos.add("La variable no existe");
		}else {
			
			
			if(expresion!=null) {
				
				if( !s.getTipo().equals( expresion.obtenerTipo() )) {
					erroresSemanticos.add("El tipo de la expresión no es correcto");
				}
			}
			
			
			if(arreglo != null) 
			{
				for(Argumento argumento: arreglo.getListaArgumentos()) 
				{
//					if(!s.getTipo().equals(argumento.obtenerTipo())) 
//					{
//						erroresSemanticos.add("El tipo del argumento no es correcto");
//					}
				}
			}
		}
		
		
		if(expresion!=null) {
			// expresion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
		if(arreglo != null) 
		{
			arreglo.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
	}
	
	

}
