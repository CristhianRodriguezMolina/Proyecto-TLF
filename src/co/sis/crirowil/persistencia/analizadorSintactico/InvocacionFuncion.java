package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una invocacion de una funcion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class InvocacionFuncion{
	
	private Token nombre;
	
	private ArrayList<Argumento> listaArgumentos;
	
	
	

	/**
	 * @param nombre
	 * @param listaArgumentos
	 */
	public InvocacionFuncion(Token nombre, ArrayList<Argumento> listaArgumentos) {
		super();
		this.nombre = nombre;
		this.listaArgumentos = listaArgumentos;
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
	 * @return the listaArgumentos
	 */
	public ArrayList<Argumento> getListaArgumentos() {
		return listaArgumentos;
	}




	/**
	 * @param listaArgumentos the listaArgumentos to set
	 */
	public void setListaArgumentos(ArrayList<Argumento> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}


	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<>("Invocacion función");
		
		raiz.getChildren().add(new TreeItem<>("Nombre: "+nombre.getPalabra()));
		
		if(this.listaArgumentos.size() > 0) {
			
			TreeItem<String> listaArgumentos = new TreeItem<>("Argumentos");
			raiz.getChildren().add(listaArgumentos);
			for (Argumento argumento : this.listaArgumentos) {
				listaArgumentos.getChildren().add(argumento.getArbolVisual());
			}
		}
		
		return raiz;
	}

	public ArrayList<String> getTiposParametros(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito){
		
		ArrayList<String> tipoParametros = new ArrayList<>();
		for (Argumento argumento : listaArgumentos) {
			tipoParametros.add(argumento.getTipo(tablaSimbolos, erroresSemanticos, ambito));
		}
		return tipoParametros;
		
	}


	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito, String identificador) {

		ArrayList<String> tipoParametros = getTiposParametros(tablaSimbolos, erroresSemanticos, ambito);
		Simbolo s = tablaSimbolos.buscarSimboloFuncion(nombre.getPalabra(), tipoParametros);
		if(s != null) {
			if(identificador != null)
			{
				Simbolo var = tablaSimbolos.buscarSimboloVariable(identificador, ambito);				
				if(!s.getTipo().equals(var.getTipo())) 
				{
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir de " + s.getTipo() + " a "
							+ var.getTipo() + " en el ambito de " + ambito.getNombre());
				}
			}
			
		}else {
			String msj = "(";
			for(String parametro: tipoParametros) 
			{
				msj += parametro + ", ";
			}
			msj = msj.substring(0, msj.length() - 2) + ")";
			erroresSemanticos.add("La funcion que esta tratando de invocar \""+ nombre.getPalabra() + msj +"\" no existe.");
		}
		
	}

	public String getJavaCode() {

		String javaCode = nombre+"(";
		
		for (Argumento argumento : listaArgumentos) {
			javaCode += argumento.getJavaCode()+",";
		}
		
		javaCode = javaCode.substring(0, javaCode.length()-2);
		
		javaCode += ")";
		
		return javaCode;
	}

	
}
