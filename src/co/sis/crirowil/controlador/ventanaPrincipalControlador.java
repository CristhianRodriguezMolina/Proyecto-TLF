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
