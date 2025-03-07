package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una Unidad de compilacion y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class UnidadDeCompilacion {
	
	/**
	 * Guarda la lista de funciones de la unidad de compilacion
	 */
	private ArrayList<Funcion> listaFunciones;
	
	/**
	 * Constructor
	 * @param listaFunciones
	 */
	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		super();
		this.listaFunciones = listaFunciones;
	}

	/**
	 * @return the listaFunciones
	 */
	public ArrayList<Funcion> getListaFunciones() {
		return listaFunciones;
	}

	/**
	 * @param listaFunciones the listaFunciones to set
	 */
	public void setListaFunciones(ArrayList<Funcion> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}
	
	/**
	 * 
	 * @return
	 */
	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<String>("Unidad de compilación");
		
		for (Funcion funcion : listaFunciones) {
			raiz.getChildren().add( funcion.getArbolVisual() );
		}
		
		return raiz;
	}

	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {

		for (Funcion funcion : listaFunciones) {
			funcion.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos);
		}
		
	}

	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos) {

		for (Funcion funcion : listaFunciones) {
			funcion.analizarSemantica(tablaSimbolos, erroresSemanticos);
		}
		
	}
	
	public String getJavaCode() 
	{
		String codigo = "";
		codigo += "import java.util.HashMap;\r\n" + 
				"import javax.swing.JOptionPane;\r\n" +
				"import java.util.Map;\r\n";
		codigo += "public class Principal {\r\n";
		for(Funcion funcion : listaFunciones) 
		{
			codigo += funcion.getJavaCode() + "\n";
		}
		return codigo + "}";
	}


}
