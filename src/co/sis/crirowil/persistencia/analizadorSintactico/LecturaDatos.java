package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
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
		
		TreeItem<String> raiz = new TreeItem<String>("Lectura de datos");
		
		if(expresionCadena != null) {
			raiz.getChildren().add(expresionCadena.getArbolVisual());
		}
		
		return raiz;
	}

	@Override
	public void llenarTablaSimbolos(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		return;
		
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

}
