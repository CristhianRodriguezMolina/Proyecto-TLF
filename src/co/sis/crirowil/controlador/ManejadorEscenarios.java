package co.sis.crirowil.controlador;

import java.io.IOException;
import java.util.ArrayList;

import co.sis.crirowil.Main;
import co.sis.crirowil.modelo.ErrorLexicoObservable;
import co.sis.crirowil.modelo.ErrorSintacticoObservable;
import co.sis.crirowil.modelo.TokenObservable;
import co.sis.crirowil.persistencia.analizadorLexico.AnalizadorLexico;
import co.sis.crirowil.persistencia.analizadorLexico.ErrorLexico;
import co.sis.crirowil.persistencia.analizadorLexico.Token;
import co.sis.crirowil.persistencia.analizadorLexico.Utilidades;
import co.sis.crirowil.persistencia.analizadorSintactico.AnalizadorSintactico;
import co.sis.crirowil.persistencia.analizadorSintactico.ErrorSintactico;
import co.sis.crirowil.persistencia.analizadorSintactico.UnidadDeCompilacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlador que me permite manejar todos lo escenarios
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class ManejadorEscenarios {
	
	
	/**
	 * Escenario principal
	 */
	private Stage escenario;
	
	/**
	 * Tipo de panel inicial
	 */
	private BorderPane anchorPanel;
	
	/**
	 * Lista de tokens observables
	 */
	private ObservableList<TokenObservable> tokensObservables;
	
	
	/**
	 * Lista de errores lexicos observables
	 */
	private ObservableList<ErrorLexicoObservable> erroresObservables;
	
	/**
	 * Lista de errores sintacticos observables
	 */
	private ObservableList<ErrorSintacticoObservable> erroresSintacticosObservables;
	
	/**
	 * La unidad de compilacion del programa
	 */
	private UnidadDeCompilacion unidadDeCompilacion;
	
	/**
	 * Recibe el escenario principal de la aplicacion
	 * 
	 * @param escenario inicial
	 */
	public ManejadorEscenarios(Stage escenario) 
	{
		this.escenario = escenario;
		try {
			// se inicializa el escenario
			escenario.setTitle("Analizador Lexico de la Universidad del Quindio");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/ventanaCompilador.fxml"));

			anchorPanel = (BorderPane) loader.load();
			
			VentanaPrincipalControlador controlador = loader.getController();
			controlador.setManejador(this);

			// se carga la escena
			Scene scene = new Scene(anchorPanel);
			escenario.setScene(scene);
			escenario.show();

//			cargarEscenario();	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo envia el codigo fuente al analizador lexico 
	 * @param cFuente
	 */
	public void analizar(String cFuente)
	{
		
		//ANALISIS LEXICO
		AnalizadorLexico al = new AnalizadorLexico(cFuente);		
		try {
			al.analizar();
		} catch (ErrorLexico e) {
			Utilidades.mostrarMensaje("Error Lexico", e.getMessage());
		}
		ArrayList<Token> tokens = al.getListaTokens();
		ArrayList<ErrorLexico> errores = al.getListaErrores();
		ObservableList<TokenObservable> tokensObservablesTemp = FXCollections.observableArrayList();
		ObservableList<ErrorLexicoObservable> erroresObservablesTemp = FXCollections.observableArrayList();
		for(Token token: tokens) 
		{
			tokensObservablesTemp.add(new TokenObservable(token));			
		}
		for(ErrorLexico error: errores) 
		{
			erroresObservablesTemp.add(new ErrorLexicoObservable(error));			
		}
		setTokensObservables(tokensObservablesTemp);
		setErroresObservables(erroresObservablesTemp);
		
		//ANALISIS SINTACTICO
		AnalizadorSintactico as = new AnalizadorSintactico(tokens);
		as.analizar();
		ArrayList<ErrorSintactico> erroresSintacticos = as.getListaErrores();
		ObservableList<ErrorSintacticoObservable> erroresSintacticosObservablesTemp = FXCollections.observableArrayList();
		for(ErrorSintactico error: erroresSintacticos) 
		{
			erroresSintacticosObservablesTemp.add(new ErrorSintacticoObservable(error));			
		}
		setErroresSintacticosObservables(erroresSintacticosObservablesTemp);
		setUnidadDeCompilacion(as.getUnidadDeCompilacion());
	}

	/**
	 * @return the tokensObservables
	 */
	public ObservableList<TokenObservable> getTokensObservables() {
		return tokensObservables;
	}

	/**
	 * @param tokensObservables the tokensObservables to set
	 */
	public void setTokensObservables(ObservableList<TokenObservable> tokensObservables) {
		this.tokensObservables = tokensObservables;
	}
	
	/**
	 * 
	 * @return
	 */
	public ObservableList<ErrorLexicoObservable> getErroresObservables() {
		return erroresObservables;
	}

	/**
	 * 
	 * @param erroresObservables
	 */
	public void setErroresObservables(ObservableList<ErrorLexicoObservable> erroresObservables) {
		this.erroresObservables = erroresObservables;
	}
	
	/**
	 * 
	 * @return
	 */
	public ObservableList<ErrorSintacticoObservable> getErroresSintacticosObservables() {
		return erroresSintacticosObservables;
	}
	
	/**
	 * 
	 * @param erroresSintacticosObservables
	 */
	public void setErroresSintacticosObservables(ObservableList<ErrorSintacticoObservable> erroresSintacticosObservables) {
		this.erroresSintacticosObservables = erroresSintacticosObservables;
	}
	
	/**
	 * 
	 * @return
	 */
	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	/**
	 * 
	 * @param unidadDeCompilacion
	 */
	public void setUnidadDeCompilacion(UnidadDeCompilacion unidadDeCompilacion) {
		this.unidadDeCompilacion = unidadDeCompilacion;
	}

	/**
	 * @return the escenario
	 */
	public Stage getEscenario() {
		return escenario;
	}

	/**
	 * @param escenario the escenario to set
	 */
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	/**
	 * @return the anchorPanel
	 */
	public BorderPane getAnchorPanel() {
		return anchorPanel;
	}

	/**
	 * @param anchorPanel the anchorPanel to set
	 */
	public void setAnchorPanel(BorderPane anchorPanel) {
		this.anchorPanel = anchorPanel;
	}	
}
