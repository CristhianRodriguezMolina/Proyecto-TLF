package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

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


	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	
}
