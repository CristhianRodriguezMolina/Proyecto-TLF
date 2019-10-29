package co.sis.crirowil.persistencia.analizadorSintactico;

import javafx.scene.control.TreeItem;

public class Impresion extends Sentencia {

	private Argumento argumento;
	
	public Impresion(Argumento argumento) {
		super();
		this.argumento = argumento;
	}
	
	/**
	 * @return the argumento
	 */
	public Argumento getArgumento() {
		return argumento;
	}

	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(Argumento argumento) {
		this.argumento = argumento;
	}

	@Override
	public TreeItem<String> getArbolVisual() {

		TreeItem<String> raiz = new TreeItem<String>("Impresion");
		raiz.getChildren().add(argumento.getArbolVisual());
		return raiz;
	}

}
