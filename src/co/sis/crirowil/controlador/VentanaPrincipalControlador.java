package co.sis.crirowil.controlador;

import co.sis.crirowil.modelo.TokenObservable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class VentanaPrincipalControlador {

		
		
    @FXML
    private TextArea textArea;

    @FXML
    private Button btnAnalizar;
    
    /**
	 * Tabla donde se almacena la informacion de los tokens
	 */
	@FXML
	private TableView<TokenObservable> tablaTokens;
	/**
	 * Hace referencia a la columna con las cedulas
	 */
	@FXML
	private TableColumn<TokenObservable, String> categoriaColumna;
	/**
	 * Hace referencia a la columna de los nombres de los Tokens
	 */
	@FXML
	private TableColumn<TokenObservable, String> palabraColumna;
    
    /**
	 * instancia del manejador de los escenario
	 */
	private ManejadorEscenarios manejador;


	/**
	 * Permite carga la informacion en las tables y escuchar las operaciones que se
	 * realizan con la tabla
	 */
	@FXML
	private void initialize() {
		categoriaColumna.setCellValueFactory(TokenCelda -> TokenCelda.getValue().getCategoria());
		palabraColumna.setCellValueFactory(TokenCelda -> TokenCelda.getValue().getPalabra());			
		
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
	 * Permite refrescar la tabla de objetos observables (Tokens)
	 */
	public void refrescarTabla()
	{
		tablaTokens.getItems().clear();
		ObservableList<TokenObservable> TokensObservables = manejador.getTokensObservables();
		
		for (TokenObservable TokenObs : TokensObservables) {
			tablaTokens.getItems().add(TokenObs);
		}
		
		tablaTokens.getColumns().get(0).setVisible(false);
		tablaTokens.getColumns().get(0).setVisible(true);
		tablaTokens.getColumns().get(1).setVisible(false);
		tablaTokens.getColumns().get(1).setVisible(true);
	}

	
	/**
	 * Este metodo captura el codigo ingresado y lo pasa al manejador de escenarios.
	 */
	@FXML
	private void analizar()
	{
		String codigoFuente = textArea.getText();
		manejador.analizar(codigoFuente);
		refrescarTabla();
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
