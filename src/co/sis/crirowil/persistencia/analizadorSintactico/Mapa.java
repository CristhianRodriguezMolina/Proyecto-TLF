package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.persistencia.analizadorSemantico.TablaSimbolos;
import javafx.scene.control.TreeItem;
import co.sis.crirowil.util.Util;

public class Mapa {

	private ArrayList<ArgumentoMapa> listaArgumentos;
	private Token tipoLlave;
	private Token tipoDato;

	public Mapa(Token tipoLlave, Token tipoDato, ArrayList<ArgumentoMapa> listaArgumentos) {
		this.tipoLlave = tipoLlave;
		this.tipoDato = tipoDato;
		this.listaArgumentos = listaArgumentos;
	}

	public ArrayList<ArgumentoMapa> getListaArgumentos() {
		return listaArgumentos;
	}

	public void setListaArgumentos(ArrayList<ArgumentoMapa> listaArgumentos) {
		this.listaArgumentos = listaArgumentos;
	}

	/**
	 * @return the llave
	 */
	public Token getTipoLlave() {
		return tipoLlave;
	}

	/**
	 * @param llave the llave to set
	 */
	public void setTipoLlave(Token tipoLlave) {
		this.tipoLlave = tipoLlave;
	}

	/**
	 * @return the tipoDato
	 */
	public Token getTipoDato() {
		return tipoDato;
	}

	/**
	 * @param tipoDato the tipoDato to set
	 */
	public void setTipoDato(Token tipoDato) {
		this.tipoDato = tipoDato;
	}

	public TreeItem<String> getArbolVisual(){
		
		TreeItem<String> raiz = new TreeItem<String>("Mapa");
		
		if(this.listaArgumentos.size() > 0) {
			TreeItem<String> listaArgumentos = new TreeItem<>("Argumentos");
			raiz.getChildren().add(listaArgumentos);
			for (ArgumentoMapa argumento : this.listaArgumentos) {
				listaArgumentos.getChildren().add(argumento.getArbolVisual());
			}
		}
		
		return raiz;
		
	}
	
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> erroresSemanticos, Simbolo ambito) {
		
		for (ArgumentoMapa argumentoMapa : listaArgumentos) {
			String tipoLlaveAux = argumentoMapa.getTipoLlave(tablaSimbolos, erroresSemanticos, ambito);
			if(!tipoLlaveAux.equals("nulo")) {
				if(!tipoLlaveAux.equals(tipoLlave.getPalabra())) {
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir de "+argumentoMapa.getTipoLlave(tablaSimbolos, erroresSemanticos, ambito)+" a "+tipoLlave.getPalabra());
				}
			}
			String tipoDatoAux = argumentoMapa.getTipoDato(tablaSimbolos, erroresSemanticos, ambito); 
			if(!tipoDatoAux.equals("nulo")) {
				if(!tipoDatoAux.equals(tipoDato.getPalabra())) {
					erroresSemanticos.add("Tipo incorrecto: No se puede convertir de "+argumentoMapa.getTipoDato(tablaSimbolos, erroresSemanticos, ambito)+" a "+tipoDato.getPalabra());
				}
			}
		}
		
	}
	
	public String getJavaCode(String nombre) {
		
		String javaCode = "new HashMap<"+Util.traducirTipo(tipoLlave.getPalabra())+","+Util.traducirTipo(tipoDato.getPalabra())+">();\n";
		
		for (ArgumentoMapa argumentoMapa : listaArgumentos) {
			javaCode += nombre + ".put("+argumentoMapa.getLlave().getPalabra()+","+argumentoMapa.getDato().getPalabra()+");\n";
		}
		
		return javaCode.substring(0, javaCode.length() - 2);
		
	}
}
