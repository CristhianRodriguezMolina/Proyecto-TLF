package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

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
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
}
