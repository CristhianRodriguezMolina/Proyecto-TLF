package co.sis.crirowil.persistencia.analizadorSintactico;

import javafx.scene.control.TreeItem;

public class LecturaDatos extends Sentencia {
	
	private ExpresionCadena expresionCadena;

	public LecturaDatos(ExpresionCadena expresionCadena) {
		super();
		this.expresionCadena = expresionCadena;
	}

	public ExpresionCadena getExpresionCadena() {
		return expresionCadena;
	}

	public void setExpresionCadena(ExpresionCadena expresionCadena) {
		this.expresionCadena = expresionCadena;
	}

	@Override
	public TreeItem<String> getArbolVisual() {
		
		TreeItem<String> raiz = new TreeItem<String>("Raiz");
		raiz.getChildren().add(expresionCadena.getArbolVisual());
		return raiz;
	}

}
