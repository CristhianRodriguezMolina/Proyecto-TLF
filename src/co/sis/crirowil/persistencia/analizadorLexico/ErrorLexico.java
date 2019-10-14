package co.sis.crirowil.persistencia.analizadorLexico;

/**
 * Me permite guardar errores lexicos presentes en el codigo fuente
 * @author Wilmar
 *
 */
public class ErrorLexico extends Exception {

	/**
	 * Me permite identificar la linea del error Lexico
	 */
	private int line;
	public ErrorLexico(String message, int line) {
		super(message);
		this.line = line;
	}
	
	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}
	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}
	
	
}
