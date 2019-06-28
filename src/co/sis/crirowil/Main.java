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

		String codigoFuente = "hx245hxAB453yhx245.ada"+
		"sadgasd.hxA324.hxACFE342AFhx.";
//		String codigoFuente = "hx2hx34hxlñasdj.hxaoer.hx4phx";
		AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
		al.analizar();
		System.out.println(al.getListaTokens());

		// launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}