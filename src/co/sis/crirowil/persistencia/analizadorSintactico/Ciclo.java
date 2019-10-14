package co.sis.crirowil.persistencia.analizadorSintactico;

/**
 * Clase que describe que es un ciclo y sus componentes
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class Ciclo extends Sentencia
{
	private Asignacion asignacion;
	private Condicion condicion;
	private SentenciaAsignacion sentenciaAsignacion;
	private BloqueSentencia bloqueSentencia;
	
	
	
	
	
	/**
	 * @param asignacion
	 * @param condicion
	 * @param sentenciaAsignacion
	 * @param bloqueSentencia
	 */
	public Ciclo(Asignacion asignacion, Condicion condicion, SentenciaAsignacion sentenciaAsignacion,
			BloqueSentencia bloqueSentencia) {
		super();
		this.asignacion = asignacion;
		this.condicion = condicion;
		this.sentenciaAsignacion = sentenciaAsignacion;
		this.bloqueSentencia = bloqueSentencia;
	}
	
	
	/**
	 * @param condicion
	 * @param bloqueSentencia
	 */
	public Ciclo(Condicion condicion, BloqueSentencia bloqueSentencia) {
		super();
		this.condicion = condicion;
		this.bloqueSentencia = bloqueSentencia;
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
	 * @return the sentenciaAsignacion
	 */
	public SentenciaAsignacion getSentenciaAsignacion() {
		return sentenciaAsignacion;
	}
	/**
	 * @param sentenciaAsignacion the sentenciaAsignacion to set
	 */
	public void setSentenciaAsignacion(SentenciaAsignacion sentenciaAsignacion) {
		this.sentenciaAsignacion = sentenciaAsignacion;
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
