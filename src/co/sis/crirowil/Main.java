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
			
			launch(args);
		}
		
		/**
		 * Inicia la app
		 */
		@Override
		public void start(Stage primaryStage) {
			
			new ManejadorEscenarios(primaryStage);
		}
	

}
