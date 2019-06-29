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
 		String codigoFuente = "importar mundo@\r\n" + 
 				"\r\n" + 
 				"$$Comentario de prueba$$\r\n" + 
 				"metodo main(args, resp)\r\n" + 
 				"{\r\n" + 
 				"	entero numero1 = 345@\r\n" + 
 				"	real numero2 = 5.63@\r\n" + 
 				"	real numero3 = .45@\r\n" + 
 				"	real numero4 = 456.@\r\n" + 
 				"	cadena = \"hola mundo\"@\r\n" + 
 				"	ciclo(entero i = 0@ i < 50@ i+=1)\r\n" + 
 				"	{\r\n" + 
 				"		cadena temp = \"\"@\r\n" + 
 				"		entero res = numero2*numero3@\r\n" + 
 				"		entero res1 *= numero1^numero4@\r\n" + 
 				"		string res2 = \"hola\" + \"mundo\" + \"\"@\r\n" + 
 				"		sisas(numero1 >= numero 3)\r\n" + 
 				"		{\r\n" + 
 				"			cadena hexa = hx123456789ABCDEF + \"\"@\r\n" + 
 				"		}\r\n" + 
 				"		nonasis(numero3 == numero4 yy 5<4 oo n4<3) \r\n" + 
 				"		{\r\n" + 
 				"\r\n" + 
 				"		}\r\n" + 
 				"		nonas\r\n" + 
 				"		{\r\n" + 
 				"\r\n" + 
 				"		}\r\n" + 
 				"	}\r\n" + 
 				"	devolver nada@\r\n" + 
 				"}";
		AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
		al.analizar();
		System.out.println(al.getListaTokens());
	}

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
