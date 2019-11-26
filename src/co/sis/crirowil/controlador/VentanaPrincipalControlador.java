package co.sis.crirowil.controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import co.sis.crirowil.modelo.ErrorLexicoObservable;
import co.sis.crirowil.modelo.ErrorSintacticoObservable;
import co.sis.crirowil.modelo.TokenObservable;
import co.sis.crirowil.persistencia.analizadorSemantico.Simbolo;
import co.sis.crirowil.util.Util;
import javafx.beans.property.SimpleStringProperty;
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
     * Text area donde se muestra la traduccion del codigo en nuestro lenguaje a lengiaje Java
     */
    @FXML
    private TextArea txtATraduccion;
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
    private TableView<String> tblErroresSemanticos;

    /**
     * 
     */
    @FXML
    private TableColumn<String, String> errorColumnaSemantico;

    /**
     * 
     */
    @FXML
    private TableView<Simbolo> tblSimbolos;

    /**
     * 
     */
    @FXML
    private TableColumn<Simbolo, String> nombreColumnaSimbolos;

    /**
     * 
     */
    @FXML
    private TableColumn<Simbolo, String> tipoColumnaSimbolos;

    /**
     * 
     */
    @FXML
    private TableColumn<Simbolo, String> ambitoColumnaSimbolos;

    /**
     * 
     */
    @FXML
    private TableColumn<Simbolo, String> filaColumnaSimbolos;

    /**
     * 
     */
    @FXML
    private TableColumn<Simbolo, String> columnaColumnaSimbolos;
    
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
		
		errorColumnaSemantico.setCellValueFactory(error -> new SimpleStringProperty(error.getValue()));
		
		nombreColumnaSimbolos.setCellValueFactory(nombreColumna -> new SimpleStringProperty(nombreColumna.getValue().getNombre()));
		tipoColumnaSimbolos.setCellValueFactory(tipoColumna -> new SimpleStringProperty(tipoColumna.getValue().getTipo()));
		ambitoColumnaSimbolos.setCellValueFactory(ambitoColumna -> new SimpleStringProperty(ambitoColumna.getValue().getAmbito().getNombre()));
		filaColumnaSimbolos.setCellValueFactory(filaColumna -> new SimpleStringProperty(filaColumna.getValue().getFila()+""));
		columnaColumnaSimbolos.setCellValueFactory(columnaColumna -> new SimpleStringProperty(columnaColumna.getValue().getColumna()+""));
		
		
		textArea.setText("$$\r\n" + 
				"	$Esto es un comentario de bloque\r\n" + 
				"$$\r\n" + 
				"\r\n" + 
				"metodo main()\r\n" + 
				"{\r\n" + 
				"\r\n" + 
				"	entero numero1 = 345;\r\n" + 
				"	entero numero = 0;\r\n" + 
				"	real numero2 = 5.63;\r\n" + 
				"	real numero3 = .45;\r\n" + 
				"	real numero4 = 456.;\r\n" + 
				"	entero numero5 = 3;\r\n" + 
				"	numero5 *= numero1;\r\n" + 
				"	real numero6 = 4.3;\r\n" + 
				"	numero6 /= numero2;\r\n" + 
				"	real numero7 = 312.2;\r\n" + 
				"	numero7 -= numero3;\r\n" + 
				"	real numero8 = numero2;\r\n" + 
				"	numero8 ^= numero4;\r\n" + 
				"	entero o = 1;\r\n" + 
				"	entero y = 0 + o;\r\n" + 
				"	real numero9 = numero2 - numero3 % 2.3;\r\n" + 
				"	cadena d = \"hola mundo\";\r\n" + 
				"	cadena temp = \"\";\r\n" + 
				"	cadena res2 = \"hola\" + \"mundo\" + \"\";\r\n" + 
				"	cadena a = \"hola\";\r\n" + 
				"	cadena v = \"hola\";\r\n" + 
				"	cadena b = \"hola\";\r\n" + 
				"	cadena cad = {a, v, b};\r\n" + 
				"	bool flag;\r\n" + 
				"	switch 6+5:{\r\n" + 
				"		caso 6:{\r\n" + 
				"		}\r\n" + 
				"		caso 89 + 9:{\r\n" + 
				"			sisas 5>6:{\r\n" + 
				"				flag = 3 < 4 yy 4>3;\r\n" + 
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
				"metodo sumar(bool numero, entero n2): real\r\n" + 
				"{\r\n" + 
				"	entero n4 = 3;\r\n" + 
				"	restar(n2, n4);\r\n" + 
				"	real z = 0.3;\r\n" + 
				"	sisas 5>6:{\r\n" + 
				"		z = restar(n2, n2);\r\n" + 
				"		numero = 3 < 4 yy 4>3;\r\n" + 
				"	} nonais 5 < 6 oo 4 <2: {\r\n" + 
				"		n4++;\r\n" + 
				"	}nonais 4 > 3:{\r\n" + 
				"		z = restar(n2, n2);\r\n" + 
				"	}nonas{\r\n" + 
				"		entero numero2 = 2 + n2;\r\n" + 
				"\r\n" + 
				"		cadena a = \"hola\";\r\n" + 
				"		cadena v = \"hola\";\r\n" + 
				"		cadena b = \"hola\";\r\n" + 
				"		cadena arregloCadenas = {a, v, b};\r\n" + 
				"		porcada cadena s : arregloCadenas {\r\n" + 
				"\r\n" + 
				"			ciclo entero i=0; n2 + n4 == 2; i++ : \r\n" + 
				"			{\r\n" + 
				"				real mundo;\r\n" + 
				"				restar(n2, n2);\r\n" + 
				"			}\r\n" + 
				"\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"	retorno 4.5;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo restar(entero n, entero n2): real\r\n" + 
				"{\r\n" + 
				"	real numero;\r\n" + 
				"	bool numero1 = 5 > 6;\r\n" + 
				"	real numero3 = sumar(true, 3);\r\n" + 
				"	sumar(true, 3);\r\n" + 
				"	retorno 3.3;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo mult(entero a, entero hola): real\r\n" + 
				"{\r\n" + 
				" 	real holaa = 3.3;\r\n" + 
				"	ciclo a + 2 == 2: \r\n" + 
				"	{\r\n" + 
				"		hola = 3;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	ciclo entero i=0; a + 2 == 2; i++ : \r\n" + 
				"	{\r\n" + 
				"		real mundo;\r\n" + 
				"	}\r\n" + 
				"	retorno holaa;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"metodo div(cadena args, cadena resp)\r\n" + 
				"{\r\n" + 
				"	entero a = 3;\r\n" + 
				"	entero b = 3;\r\n" + 
				"	entero c = 3;\r\n" + 
				"	entero d = 3;\r\n" + 
				"	entero e = 3;\r\n" + 
				"	cadena v = \"viva del paro!\";\r\n" + 
				"	cadena vv = \"viva del paro!\";\r\n" + 
				"	cadena vvv = \"viva del paro!\";\r\n" + 
				"	real f = 3.3;\r\n" + 
				"	entero g = 3;\r\n" + 
				"	entero h = a + b - ((c * d) + e);\r\n" + 
				"	bool i = true;\r\n" + 
				"	real j = 3.4;\r\n" + 
				"	cadena k = \"hola\";\r\n" + 
				"	bool l = a + b - ((c * d) + e) > f;\r\n" + 
				"	mapa m = mapDe(entero , cadena){ {a, v}, {b, vv}, {g, vvv},{d , k}};\r\n" + 
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
		tblErroresSemanticos.getItems().clear();
		tblSimbolos.getItems().clear();
		ObservableList<ErrorLexicoObservable> ErroresObservables = manejador.getErroresObservables();
		ObservableList<TokenObservable> TokensObservables = manejador.getTokensObservables();
		ObservableList<ErrorSintacticoObservable> ErroresSintacticosObservables = manejador.getErroresSintacticosObservables();
		ObservableList<String> ErroresSemanticosObservables = manejador.getErroresSemanticosObservables();
		ObservableList<Simbolo> SimbolosObservables = manejador.getSimbolosObservables();
		
		for (TokenObservable TokenObs : TokensObservables) {
			tablaTokens.getItems().add(TokenObs);
		}
		for (ErrorLexicoObservable ErrorOb : ErroresObservables) {
			tablaErrores.getItems().add(ErrorOb);
		}
		for (ErrorSintacticoObservable ErrorOb : ErroresSintacticosObservables) {
			tblErroresSintacticos.getItems().add(ErrorOb);
		}
		for (String string : ErroresSemanticosObservables) {
			tblErroresSemanticos.getItems().add(string);
		}
		for (Simbolo simbolo : SimbolosObservables) {
			tblSimbolos.getItems().add(simbolo);
		}
		
		tablaTokens.getColumns().get(0).setVisible(false);
		tablaTokens.getColumns().get(0).setVisible(true);
		tablaTokens.getColumns().get(1).setVisible(false);
		tablaTokens.getColumns().get(1).setVisible(true);
		tablaErrores.getColumns().get(0).setVisible(false);
		tablaErrores.getColumns().get(0).setVisible(true);
	}

	@FXML
	public void traducir(){	

		if(manejador.getErroresObservables().size() == 0 && manejador.getErroresSintacticosObservables().size() == 0 
				&& manejador.getErroresSemanticosObservables().size() == 0) {
			//Validar que las listas de errores del análisis estén vacías

		    String codigo = manejador.getUnidadDeCompilacion().getJavaCode();

		    txtATraduccion.setText(codigo);
		    
		    try{

		        crearArchivo(codigo);			
		    
		        //Invocar el compilador de Java
		        ProcessBuilder builder = new ProcessBuilder("javac", "src/Principal.java");
	            builder.redirectErrorStream(true);
	            builder.inheritIO();
	            Process process = builder.start();
	            int errCode = process.waitFor();
	            System.out.println("Error al ejecutar el comando? " + (errCode == 0 ? "No" : "Sí"));
						
				//Se ejecuta el .class 
				ProcessBuilder builder2 = new ProcessBuilder("java", "src/Principal");
	            builder2.redirectErrorStream(true);
	            builder2.inheritIO();
	            Process process2 = builder2.start();
	            int errCode2 = process2.waitFor();
	            System.out.println("Error al ejecutar el comando? " + (errCode2 == 0 ? "No" : "Sí"));


			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}else {
			Util.mostrarMensaje("Codigo erroneo", "Aun hay errores, debe corregirlos antes de continuar ");
		}

	}

	public void crearArchivo(String codigo)throws Exception{		

	    FileWriter fw = new FileWriter("src/Principal.java");
		BufferedWriter bw = new BufferedWriter(fw);

		bw.write(codigo);
		bw.flush();
		bw.close();

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

