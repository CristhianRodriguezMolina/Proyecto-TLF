package co.sis.crirowil;


import javax.xml.stream.events.StartDocument;

import co.sis.crirowil.controlador.ManejadorEscenarios;
import co.sis.crirowil.persistencia.AnalizadorLexico;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

		/**
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
			
			
//			String codigoFuente = ".asdasd345^==24535.oo.o.fgdg=f.345\n.fdtrer++=tert,sdg.";
//			AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
//			al.analizar();			
//			System.out.println(al.getListaTokens());
			
			launch(args);
		}
		
		/**
		 * Inicia la app
		 */
		@Override
		public void start(Stage primaryStage) {
			
			System.out.println("LLego");
			new ManejadorEscenarios(primaryStage);
		}
	
}
