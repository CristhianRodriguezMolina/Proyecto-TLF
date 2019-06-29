package co.sis.crirowil.controlador;

import java.io.IOException;



import co.sis.crirowil.Main;
import co.sis.crirowil.persistencia.AnalizadorLexico;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
			
			ventanaPrincipalControlador controlador = loader.getController();
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
	
	}
	
	
	

	
	
	
}
