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
			

			String codigoFuente = ".GAWLVK;;AWKRG´{{}787/58}*======***sdasd3***45^=24531//212@5.fgdg=f.345\n.fdt@rer++=`{{{[[[^tert,sdg.";
			AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
			al.analizar();			
			System.out.println(al.getListaTokens());
			
			//launch(args);
		}
		
		/**
		 * 
		 */
		public void start(Stage primaryStage) {
			
		}
	
}
