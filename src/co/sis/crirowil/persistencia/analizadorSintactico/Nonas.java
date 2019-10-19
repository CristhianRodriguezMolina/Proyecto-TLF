package co.sis.crirowil.persistencia.analizadorSintactico;

/**
 * Clase que describe que es una sentencias nonas y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Nonas {
	
	private BloqueSentencia bloqueSentencia;
	
	

	/**
	 * @param bloqueSentencia
	 */
	public Nonas(BloqueSentencia bloqueSentencia) {
		super();
		this.bloqueSentencia = bloqueSentencia;
	}

	/**
	 * @return the bloqueSentencia
	 */
	public BloqueSentencia getBloqueSentencia() {
		return bloqueSentencia;
	}

	/**
	 * @param bloqueSentencia the bloqueSentencia to set
	 */
	public void setBloqueSentencia(BloqueSentencia bloqueSentencia) {
		this.bloqueSentencia = bloqueSentencia;
	}
	
	
	
}
