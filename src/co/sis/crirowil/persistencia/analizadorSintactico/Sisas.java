package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

/**
 * Clase que describe que es una sentencias sisas y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Sisas extends Sentencia{
	
	/**
	 * Condicion de ejecucion de la sentecia
	 */
	private Condicion condicion;
	
	/**
	 * Sentencias de sisas
	 */
	private BloqueSentencia bloqueSentenciaSisas;
	
	/**
	 * Guarda los nonais del la sentencia sisas.
	 */
	private ArrayList<Nonais> listaNonais;
	
	/**
	 * 	Bloques de sentencia de la sentencia nonais
	 */
	private Nonas nonas;
	
	/**
	 * Constructor
	 * @param condicion
	 * @param bloqueSentenciasSisas
	 * @param bloqueSenteciasNonas
	 */
	public Sisas(Condicion condicion, BloqueSentencia bloqueSentenciasSisas, ArrayList<Nonais> listaNonais, Nonas nonas) 
	{
		this.condicion = condicion;
		this.bloqueSentenciaSisas = bloqueSentenciasSisas;
		this.listaNonais = listaNonais;
		this.nonas = nonas;
		
	}

	/**
	 * @return the condicion
	 */
	public Condicion getCondicion() {
		return condicion;
	}

	/**
	 * @param condicion the condicion to set
	 */
	public void setCondicion(Condicion condicion) {
		this.condicion = condicion;
	}

	/**
	 * @return the bloqueSentenciaSisas
	 */
	public BloqueSentencia getBloqueSentenciaSisas() {
		return bloqueSentenciaSisas;
	}

	/**
	 * @param bloqueSentenciaSisas the bloqueSentenciaSisas to set
	 */
	public void setBloqueSentenciaSisas(BloqueSentencia bloqueSentenciaSisas) {
		this.bloqueSentenciaSisas = bloqueSentenciaSisas;
	}
	
	

	/**
	 * @return the listaNonais
	 */
	public ArrayList<Nonais> getListaNonais() {
		return listaNonais;
	}

	/**
	 * @param listaNonais the listaNonais to set
	 */
	public void setListaNonais(ArrayList<Nonais> listaNonais) {
		this.listaNonais = listaNonais;
	}

	/**
	 * @return the nonas
	 */
	public Nonas getNonas() {
		return nonas;
	}

	/**
	 * @param nonas the nonas to set
	 */
	public void setNonas(Nonas nonas) {
		this.nonas = nonas;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		TreeItem<String> raiz = new TreeItem<String>("Sisas");
		
		raiz.getChildren().add(condicion.getArbolVisual());

		TreeItem<String> sentencias = new TreeItem<String>("Sentencias");
		raiz.getChildren().add(sentencias);

		for (Sentencia sentencia : bloqueSentenciaSisas.getListaSentencias()) {
			sentencias.getChildren().add(sentencia.getArbolVisual());
		}
		
		if(this.listaNonais.size() > 0) {
			TreeItem<String> listaNonais = new TreeItem<String>("Lista nonais");
			raiz.getChildren().add(listaNonais);
			
			for (Nonais nonais : this.listaNonais) {
				listaNonais.getChildren().add(nonais.getArbolVisual());
			}
		}		
		
		if(this.nonas != null) {
			raiz.getChildren().add(this.nonas.getArbolVisual());
		}

		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		tablaSimbolos.guardarSimboloSentencia(this, ambito);
		
		for(Sentencia sentencia: bloqueSentenciaSisas.getListaSentencias()) 
		{
			sentencia.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		for (Nonais nonais : listaNonais) {
			nonais.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito);
		}
		if(nonas != null) {
			nonas.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito);
		}
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		condicion.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		
		for(Sentencia sentencia: bloqueSentenciaSisas.getListaSentencias()) 
		{
			sentencia.analizarSemantica(tablaSimbolos, erroresSemanticos, tablaSimbolos.buscarSimboloSentencia(this, ambito));
		}
		for (Nonais nonais : listaNonais) {
			nonais.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		if(nonas != null) {
			nonas.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito);
		}
		
	}

	@Override
	public String getJavaCode() {

		String javaCode = "if("+condicion.getExpresion().getJavaCode()+"){\n";
		
		for (Sentencia sentencia : bloqueSentenciaSisas.getListaSentencias()) {
			javaCode += sentencia.getJavaCode()+"\n";
		}
		
		javaCode += "}";
		
		if(listaNonais.size() > 0) {
			
			for (Nonais nonais : listaNonais) {
				
				javaCode += nonais.getJavaCode();
				
			}
			
		}
		
		if(nonas != null) {
			
			javaCode += nonas.getJavaCode();	
			
		}
		
		return javaCode+"\n";
	}
	
	

	
}
