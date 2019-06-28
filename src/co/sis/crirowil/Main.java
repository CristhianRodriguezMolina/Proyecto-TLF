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
 		String codigoFuente = ".GAWLVK;;cicloAWKentero"+
 		"RG´{{}787/58}*======***sdar"+
 		"ealsd3***45^=24531//212@5.fgdg=f." +
 		"345\n.fdt@rer++=`{{{[[[^tert,sdg."+
		"hx245hxAB453yhx245.7126574adasenteroadgasd."+
 		"enterohxA324.hxACFE342AFhx."+
		"hx2hx34hxl?sdj.hxaoer.hx4phx";
		AnalizadorLexico al = new AnalizadorLexico(codigoFuente);
		al.analizar();
		System.out.println(al.getListaTokens());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
