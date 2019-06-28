package co.sis.crirowil;

import co.sis.crirowil.persistencia.AnalizadorLexico;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String msj = "4636346sfhaoihaof3432423asd35af34";
		AnalizadorLexico analizador = new AnalizadorLexico(msj);
		analizador.analizar();
		System.out.println(analizador.getListaTokens());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
