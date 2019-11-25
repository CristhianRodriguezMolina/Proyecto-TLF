package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;

public class Arreglo {

	private ArrayList<Argumento> listaArgumentos;
	
	public Arreglo(ArrayList<Argumento> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	public ArrayList<Argumento> getListaArgumentos() {
		return listaArgumentos;
	}

	public void setListaArgumentos(ArrayList<Argumento> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}
	
	public TreeItem<String> getArbolVisual(){
		
		
		TreeItem<String> raiz = new TreeItem<String>("Arreglo");
		
		if(this.listaArgumentos.size() > 0) {
			TreeItem<String> listaArgumentos = new TreeItem<>("Argumentos");
			raiz.getChildren().add(listaArgumentos);
			for (Argumento argumento : this.listaArgumentos) {
				listaArgumentos.getChildren().add(argumento.getArbolVisual());
			}
		}
		
		return raiz;
		
	}
	
	public String getTipo() {
		return "";
	}

	public void analizarSemantica(String tipo, TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {

		for (Argumento argumento : listaArgumentos) {
			String tipoAux = argumento.getTipo(tablaSimbolos, erroresSemanticos, ambito); 
			if(!tipoAux.equals("nulo")) {
				if(!tipoAux.equals(tipo)) {
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir de "+argumento.getTipo(tablaSimbolos, erroresSemanticos, ambito)+" a "+tipo+", en declaración de arreglo.");
				}
			}				
		}
		
	}

	public String getJavaCode(String tipo, String nombre) {
		String codigo = "new " + tipo + "[" + listaArgumentos.size() + "];\n";
		int i = 0;
		for(Argumento argumento: listaArgumentos) 
		{
			codigo += nombre + "[" + i + "] = " + argumento.getJavaCode() + ";\n";
			i++;
		}
		return codigo.substring(0, codigo.length() - 2);
	}
		
	
}
