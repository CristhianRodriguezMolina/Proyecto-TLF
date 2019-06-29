package co.sis.crirowil.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ventanaPrincipalControlador {

		
		
    @FXML
    private TextArea textArea;

    @FXML
    private Button btnAnalizar;
    
    /**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;

		/**
		 * 
		 */
		@FXML
		private void initialize() {
			
			textArea.setText("importar mundo@\r\n" + 
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
	 				"}");
		}

	

	
		/**
		 * Este metodo captura el codigo ingresado y lo pasa al manejador de escenarios.
		 */
		@FXML
		private void analizar()
		{
			String codigoFuente = textArea.getText();
			manejador.analizar(codigoFuente);
		}
		
		/**
		 * Permite cerrar la ventana de editar y crear
		 */
		@FXML
		private void cancelar() {
			//this.close();
		}
		
		
		/**
		 * Permite cargar el manejador de escenarios
		 * 
		 * @param manejador
		 */
		public void setManejador(ManejadorEscenarios manejador) {
			this.manejador = manejador;
		}




		/**
		 * @return the manejador
		 */
		public ManejadorEscenarios getManejador() {
			return manejador;
		}

		
		
	
}
