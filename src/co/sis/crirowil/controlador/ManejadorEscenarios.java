package co.sis.crirowil.controlador;

import java.io.IOException;
import java.util.ArrayList;

import co.sis.crirowil.Main;
import co.sis.crirowil.modelo.TokenObservable;
import co.sis.crirowil.persistencia.AnalizadorLexico;
import co.sis.crirowil.persistencia.Token;
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
 * @author Juan Manuel Roa Mejia
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
	private AnchorPane anchorPanel;
	
	/**
	 * Lista de tokens observables
	 */
	private ObservableList<TokenObservable> tokensObservables;
	
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
			escenario.setTitle("Analizador Lexico de la Universidad del Quindío");

			// se carga la vista
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./vista/ventanaPrincipal.fxml"));

			anchorPanel = (AnchorPane) loader.load();
			
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
		AnalizadorLexico al = new AnalizadorLexico(cFuente);
		al.analizar();			
		System.out.println(al.getListaTokens());
		ArrayList<Token> tokens = al.getListaTokens();
		ObservableList<TokenObservable> tokensObservablesTemp = FXCollections.observableArrayList();
		for(Token token: tokens) 
		{
			tokensObservablesTemp.add(new TokenObservable(token));
			
		}
		setTokensObservables(tokensObservablesTemp);
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
	public AnchorPane getAnchorPanel() {
		return anchorPanel;
	}

	/**
	 * @param anchorPanel the anchorPanel to set
	 */
	public void setAnchorPanel(AnchorPane anchorPanel) {
		this.anchorPanel = anchorPanel;
	}	
}
