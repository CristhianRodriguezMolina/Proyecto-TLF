package co.sis.crirowil.controlador;

import co.sis.crirowil.modelo.ErrorLexicoObservable;
import co.sis.crirowil.modelo.ErrorSintacticoObservable;
import co.sis.crirowil.modelo.TokenObservable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

/**
 * Controlador que me permite maneja la ventana principal
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class VentanaPrincipalControlador {

		
		
	/**
	 * Relacion al textArea a disposicion del usuario
	 */
    @FXML
    private TextArea textArea;
    
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
	 * Tabla donde se almacenan los errores lexicos que se encuentren
	 */
	@FXML
    private TableView<ErrorLexicoObservable> tablaErrores;
	/**
	 * Hace referencia al nombre de los errores lexicox 
	 */
	@FXML
	private TableColumn<ErrorLexicoObservable, String> errorColumna;
	
	/**
	 * Hace referencia a la linea del error lexico
	 */
	@FXML
    private TableColumn<ErrorLexicoObservable, String> lineaColumna;
	
	/**
	 * 
	 */
	@FXML
    private TableView<ErrorSintacticoObservable> tblErroresSintacticos;

	/**
	 * 
	 */
    @FXML
    private TableColumn<ErrorSintacticoObservable, String> filaColumna;

    /**
     * 
     */
    @FXML
    private TableColumn<ErrorSintacticoObservable, String> columnaColumna;

    /**
     * 
     */
    @FXML
    private TableColumn<ErrorSintacticoObservable, String> mensajeColumna;

    /**
     * 
     */
    @FXML
    private StackPane root;
    
    /**
     * 
     */
    @FXML
    private TreeView<String> treeArbolVisual;
		
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
		errorColumna.setCellValueFactory(ErrorCelda -> ErrorCelda.getValue().getMensaje());
		lineaColumna.setCellValueFactory(LineaColumna -> LineaColumna.getValue().getLine());
		filaColumna.setCellValueFactory(FilaCelda -> FilaCelda.getValue().getFila());
		columnaColumna.setCellValueFactory(ColumnaCelda -> ColumnaCelda.getValue().getColumna());
		mensajeColumna.setCellValueFactory(MensajeCelda -> MensajeCelda.getValue().getMensaje());
		
		textArea.setText(
//				"$$Comentario de prueba$$\r\n" + 
				"metodo main(cadena args, cadena resp)\r\n" + 
				"{\r\n" + 
				"	entero numero1 = 345;\r\n" + 
				"	real numero2 = 5.63;\r\n" + 
				"	real numero3 = .45;\r\n" + 
				"	real numero4 = 456.;\r\n" + 
				"	real numero5 *= numero1;\r\n" + 
				"	real numero6 /= numero2;\r\n" + 
				"	real numero7 -= numero3;\r\n" + 
				"	real numero8 ^= numero4;\r\n" + 
				"	entero o = 1;\r\n" + 
				"	entero y = 0 + o;\r\n" + 
				"	entero numero9 = numero2 - numero3 % 2;\r\n" + 
				"	cadena d = \"hola mundo\";\r\n" + 
				"	cadena temp = \"\";\r\n" + 
				"	cadena res2 = \"hola\" + \"mundo\" + \"\";\r\n" + 
				"	retorno nada;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	sisas 5>6:{\r\n" + 
				"		numero = 3 < 4 yy 4>3;\r\n" + 
				"	} nonais 5 < 6 oo 4 <2: {\r\n" + 
				"		numero++;\r\n" + 
				"	}nonais 4 > 3:{\r\n" + 
				"		numero = holis();\r\n" + 
				"	}nonas{\r\n" + 
				"		numero = 2 + numero;\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	real numero;\r\n" + 
				"	real numero1 = 5 > 6;\r\n" + 
				"	real numero3 = hola(5 + 6, numero);\r\n" + 
				"	hola(1, 3,hola,d);\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	ciclo a + b == 2: \r\n" + 
				"	{\r\n" + 
				"		 retorno hola;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	ciclo entero i=0; a + b == 2; i++ : \r\n" + 
				"	{\r\n" + 
				"		real mundo;\r\n" + 
				"	}\r\n" + 
				"}");
		
		textArea.setText("metodo main(cadena args, cadena resp)\r\n" + 
				"{\r\n" + 
				"	mapa b = mapDe(entero , cadena){ {a, v}, {b, d}, {g, we},{f , df}};\r\n" + 
				"	entero numero1 = 345;\r\n" + 
				"	real numero2 = 5.63;\r\n" + 
				"	real numero3 = .45;\r\n" + 
				"	real numero4 = 456.;\r\n" + 
				"	real numero5 *= numero1;\r\n" + 
				"	real numero6 /= numero2;\r\n" + 
				"	real numero7 -= numero3;\r\n" + 
				"	real numero8 ^= numero4;\r\n" + 
				"	entero o = 1;\r\n" + 
				"	entero y = 0 + o;\r\n" + 
				"	entero numero9 = numero2 - numero3 % 2;\r\n" + 
				"	cadena d = \"hola mundo\";\r\n" + 
				"	cadena temp = \"\";\r\n" + 
				"	cadena res2 = \"hola\" + \"mundo\" + \"\";\r\n" + 
				"	cadena a = {a, v, b};\r\n" + 
				"	switch 6+5:{\r\n" + 
				"		caso 6:{\r\n" + 
				"		}\r\n" + 
				"		caso 89 + 9:{\r\n" + 
				"			sisas 5>6:{\r\n" + 
				"				numero = 3 < 4 yy 4>3;\r\n" + 
				"			} nonais 5 < 6 oo 4 <2: {\r\n" + 
				"				numero++;\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		defecto{}	\r\n" + 
				"	}\r\n" + 
				"	retorno nada;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	sisas 5>6:{\r\n" + 
				"		numero = 3 < 4 yy 4>3;\r\n" + 
				"	} nonais 5 < 6 oo 4 <2: {\r\n" + 
				"		numero++;\r\n" + 
				"	}nonais 4 > 3:{\r\n" + 
				"		numero = holis();\r\n" + 
				"	}nonas{\r\n" + 
				"		numero = 2 + numero;\r\n" + 
				"	}\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	real numero;\r\n" + 
				"	real numero1 = 5 > 6;\r\n" + 
				"	real numero3 = hola(5 + 6, numero);\r\n" + 
				"	hola(1, 3,hola,d);\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo sumar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	ciclo a + b == 2: \r\n" + 
				"	{\r\n" + 
				"		 retorno hola;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	ciclo entero i=0; a + b == 2; i++ : \r\n" + 
				"	{\r\n" + 
				"		real mundo;\r\n" + 
				"	}\r\n" + 
				"}");
	}
	
	/**
	 * Permite refrescar la tabla de objetos observables (Tokens)
	 */
	public void refrescarTabla()
	{
		tablaTokens.getItems().clear();
		tablaErrores.getItems().clear();
		tblErroresSintacticos.getItems().clear();
		ObservableList<ErrorLexicoObservable> ErroresObservables = manejador.getErroresObservables();
		ObservableList<TokenObservable> TokensObservables = manejador.getTokensObservables();
		ObservableList<ErrorSintacticoObservable> ErroresSintacticosObservables = manejador.getErroresSintacticosObservables();
		
		for (TokenObservable TokenObs : TokensObservables) {
			tablaTokens.getItems().add(TokenObs);
		}
		for (ErrorLexicoObservable ErrorOb : ErroresObservables) {
			tablaErrores.getItems().add(ErrorOb);
		}
		for (ErrorSintacticoObservable ErrorOb : ErroresSintacticosObservables) {
			tblErroresSintacticos.getItems().add(ErrorOb);
		}
		
		tablaTokens.getColumns().get(0).setVisible(false);
		tablaTokens.getColumns().get(0).setVisible(true);
		tablaTokens.getColumns().get(1).setVisible(false);
		tablaTokens.getColumns().get(1).setVisible(true);
		tablaErrores.getColumns().get(0).setVisible(false);
		tablaErrores.getColumns().get(0).setVisible(true);
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
		crearArbolVisual();
		
	}
	
	private void crearArbolVisual() {

		treeArbolVisual.setRoot(manejador.getUnidadDeCompilacion().getArbolVisual());
		
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
