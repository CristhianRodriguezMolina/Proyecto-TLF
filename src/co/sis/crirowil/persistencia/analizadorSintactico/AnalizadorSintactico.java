package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;
import java.util.spi.TimeZoneNameProvider;

import javax.xml.bind.Validator;

import co.sis.crirowil.persistencia.analizadorLexico.Categoria;
import co.sis.crirowil.persistencia.analizadorLexico.Token;

/**
 * Clase que analiza si los tokens previamente identificados por el analizador
 * lexico estan en un orden correcto segun la sintactica del lenguaje
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public class AnalizadorSintactico {
	/**
	 * Lista de tokens
	 */
	ArrayList<Token> tokens;

	/**
	 * Guarda el token actual
	 */
	Token tokenActual;

	/**
	 * Posicional del token actual
	 */
	int posActual = 0;

	/**
	 * Guarda errores encontrados en el codigo fuente
	 */
	ArrayList<ErrorSintactico> listaErrores;

	/**
	 * Metodo constructor
	 * 
	 * @param tokens
	 */
	public AnalizadorSintactico(ArrayList<Token> tokens) {
		this.tokens = tokens;
		listaErrores = new ArrayList<>();
	}

	/**
	 * Metodo que describe si los token corresponden al BNF de la unidad de
	 * compilacion <UnidadDeCompilacion> ::= <listaFunciones>
	 * 
	 * @return
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {
		ArrayList<Funcion> listaFunciones = esListaFunciones();

		if (listaFunciones != null) {
			return new UnidadDeCompilacion(listaFunciones);
		} else {
			reportarError("la unidad de compilacion debe de tener una lista de funciones que ejecutar");
			return null;
		}
	}

	/**
	 * Metodo que me verifica que dado el BNF del listaFunciones es o no valido
	 * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
	 */
	public ArrayList<Funcion> esListaFunciones() {
		ArrayList<Funcion> listaFunciones = new ArrayList<>();
		Funcion funcion = esFuncion();

		while (funcion != null) {
			listaFunciones.add(funcion);
		}

		return listaFunciones;
	}

	/**
	 * Metodo que describe si los token corresponden al BNF de la lista de funciones
	 * <Funcion> ::= metodo identificador "("[<ListaParametros>]")"
	 * [":"<TipoRetorno>] <BloqueSentencias>
	 */
	public Funcion esFuncion() {
		
		if (!(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("metodo"))) {
			reportarError("Error, se esperaba la palabra metodo");
			return null;
		}

		if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
			reportarError("identificador de la funcion no es valido");
			return null;
		}

		Token nombre = tokenActual;
		obtenerTokenSiguiente();

		if (tokenActual.getCategoria() != Categoria.PARENTESIS_ABRE) {
			reportarError("falta el parentesis de apertura");
			return null;
		}

		obtenerTokenSiguiente();

		ArrayList<Parametro> listaParametros = esListaParametros();

		if (tokenActual.getCategoria() != Categoria.PARENTESIS_CIERRA) {
			reportarError("Falta el parentesis de cierre");
			return null;
		}

		if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
			reportarError("Falta el parentesis de cierre");
			return null;
		}

		Token retorno = esTipoRetorno();
		BloqueSentencia bloqueSentencias = esBloqueSentencia();

		if (bloqueSentencias == null) {
			reportarError("Falta el bloque de sentencias");
			return null;
		}

		return new Funcion(nombre, listaParametros, retorno, bloqueSentencias);
	}

	/**
	 * Metodo que me verifica que dado el BNF de listaParametros es o no valido
	 * <ListaParametros> ::= <Parametro>[","<ListaParametros>]
	 * 
	 * @return
	 */
	public ArrayList<Parametro> esListaParametros() {
		
		ArrayList<Parametro> listaParametros = new ArrayList<>();
		Parametro parametro = esParametro();
		while(parametro != null) 
		{
			listaParametros.add(parametro);
			parametro = esParametro();
		}
		return listaParametros;
	}

	/**
	 * Metodo que me verifica que dado el BNF del parametros es o no valido
	 * <Parametro> ::= <TipoRetorno> identificador
	 * 
	 * @return
	 */
	public Parametro esParametro() {
		if (!(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && esTipoRetorno() != null)) {
			reportarError("El tipo de dato de un parametro no es valido");
			return null;
		}

		Token retorno = tokenActual;
		obtenerTokenSiguiente();

		if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
			reportarError("El nombre del parametro no es valido");
			return null;
		}

		Token nombre = tokenActual;
		return new Parametro(retorno, nombre);
	}

	

	/**
	 * Metodo que dado un caracter me dice si es un de un tipo de retorno valido
	 * 
	 * @return
	 */
	public Token esTipoRetorno() {
		if (tokenActual.getPalabra().equals("real") || tokenActual.getPalabra().equals("entero")
				|| tokenActual.getPalabra().equals("cadena") || tokenActual.getPalabra().equals("bool")
				|| tokenActual.getPalabra().equals("char")) {
			return tokenActual;
		}
		return null;
	}

	/**
	 * Analiza los tokens para verificar la sintactica correcta del lenguaje
	 */
	public void analizar() {
		
	}

	/**
	 * Metodo que me verifica que dado el BNF del Sisas es o no valido
	 * <Sisas> ::= sisas <condicion> ":" <BloqueSentencias> [<ListaNonais>][<Nonas>]
	 * 
	 * @return
	 */
	public Sisas esSentenciaSisas() {
		if(!(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("sisas"))) 
		{
			return null;
		}
		
		obtenerTokenSiguiente();
		
		Condicion condicion = esCondicion();
		
		if(condicion == null) 
		{
			reportarError("la sentencia sisas debe de tener una condicon valida");
			return null;
		}
		
		if(tokenActual.getCategoria() != Categoria.DOS_PUNTOS) 
		{
			reportarError("Token inesperado, se esperaba: \":\"");
			return null;
		}
		
		obtenerTokenSiguiente();
		
		BloqueSentencia bloqueSentenciasSisas = esBloqueSentencia();
		
		if(bloqueSentenciasSisas == null) {
			reportarError("Falta el bloque de sentencias");
			return null;
		}
		
		ListaNonais listaNonais = esListaNonais();
		
		Nonas nonas = esSentenciaNonas();
		
		return new Sisas(condicion, bloqueSentenciasSisas, listaNonais, nonas);
	}

	public ListaNonais esListaNonais() {
		// TODO Auto-generated method stub
		return null;
	}

	public Nonas esSentenciaNonas() {
		// TODO Auto-generated method stub
		return null;
	}

	public Condicion esCondicion() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del bloqueSentencias es o no valido
	 * <BloqueSentencia> ::= "{"<listaSentecias>"}"
	 * 
	 * @return
	 */
	public BloqueSentencia esBloqueSentencia() {
		ArrayList<Sentencia> listaSentencia = esListaSentencias();
		if (tokenActual.getPalabra().equals("{")) {
			if (obtenerTokenSiguiente()) {
				if (listaSentencia != null) {
					if (tokenActual.getPalabra().equals("}")) {
						return new BloqueSentencia(listaSentencia);
					}
				}
			}
		}
		return null;
	}

	/**
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 * 
	 * @return
	 */
	public ArrayList<Sentencia> esListaSentencias() {
		ArrayList<Sentencia> listaSentencia = new ArrayList<>();
		Sentencia sentencia = esSentencia();

		while (sentencia != null) {
			listaSentencia.add(sentencia);
			sentencia = esSentencia();
		}

		return listaSentencia;
	}

	/**
	 * Obtiene el siguiente token de la lista de tokens
	 * 
	 * @return
	 */
	public boolean obtenerTokenSiguiente() {
		posActual++;
		if (posActual < tokens.size()) {
			tokenActual = tokens.get(posActual);
			return true;
		} else {
			tokenActual = null;
			return false;
		}
	}

	/**
	 * Me permite reportar un error sintactico
	 * 
	 * @param mensaje
	 */
	public void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * 
	 * @return
	 */
	public Sentencia esSentencia() {
		
		Sentencia s = esSentenciaSisas();
		if(s != null)
			return s;
		
		s = esCiclo();
		if(s != null)
			return s;
		
		
		return null;
		
	}
	
	/**
	 * <Ciclo> ::= <condicion> ":" <BloqueSentencia> | ciclo <Asignacion> "," <Condicion> "," <SentenciaAsignacion> ":" <BloqueSentencias>
	 * @return
	 */
	public Ciclo esCiclo() {
		
		boolean tieneAsignacion = false;
		
		Asignacion asignacion = esAsignacion();
		
		if(asignacion != null) 
		{
			if(tokenActual.getCategoria() == Categoria.SEPARADOR) 
			{
				tieneAsignacion = true;
			}
			else 
			{
				reportarError("Falta un separador");
			}
		}
		
		Condicion condicion = esCondicion();
		if(condicion != null) 
		{
			SentenciaAsignacion sentenciaAsignacion = esSentenciaAsignacion();
			
			if(asignacion != null && tieneAsignacion) 
			{
				if(sentenciaAsignacion != null) 
				{
					if(tokenActual.getCategoria() == Categoria.DOS_PUNTOS) 
					{
						obtenerTokenSiguiente();
						BloqueSentencia bloqueSentencia = esBloqueSentencia();
						if(bloqueSentencia != null) 
						{
							return new Ciclo(asignacion, condicion, sentenciaAsignacion, bloqueSentencia);
						}
						else 
						{
							reportarError("falta las sentecias en el ciclo");
							return null;
						}
						
					}
				}
				else 
				{
					reportarError("Sentencia de ciclo incompleta");
					return null;
				}
			}
			else 
			{
				if(tokenActual.getCategoria() == Categoria.DOS_PUNTOS) 
				{
					obtenerTokenSiguiente();
					BloqueSentencia bloqueSentencia = esBloqueSentencia();
					if(bloqueSentencia != null) 
					{
						return new Ciclo(condicion, bloqueSentencia);
					}
					else 
					{
						reportarError("falta las sentecias en el ciclo");
						return null;
					}
				}
			}
			return null;
			
		}
		
		return null;
	}

	public SentenciaAsignacion esSentenciaAsignacion() {
		// TODO Auto-generated method stub
		return null;
	}

	public Asignacion esAsignacion() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public Expresion esExpresion() 
	{
		Expresion expresion = null;
		
		expresion = esExpresionAritmetica();
		if(expresion != null)
			return expresion;
					
		expresion = esExpresionRelacional();
		if(expresion != null)
			return expresion;
		
		expresion = esExpresionLogica();
		if(expresion != null)
			return expresion;
		return  null;
	}
	
	public ExpresionCadena esExpresionCadena() {
		
		if(tokenActual.getCategoria() != Categoria.CADENA_CARACTERES) {
			return null;
		}
		
		Token cadenaCaracteres = tokenActual;
		
		obtenerTokenSiguiente();
		
		if(tokenActual.getCategoria() != Categoria.OPERADOR_ARITMETICO || !tokenActual.getPalabra().equals("+")) {
			return new ExpresionCadena(cadenaCaracteres);
		}
		
		obtenerTokenSiguiente();
		
		Expresion expresion = esExpresion();
		
		if(expresion == null) {
			reportarError("Falta la expresion desues del mas (+)");
			return null;
		}
		
		return new ExpresionCadena(cadenaCaracteres, expresion);
	}
	
	public ExpresionLogica esExpresionLogica() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExpresionRelacional esExpresionRelacional() {
		
		return null;
	}

	/**
	 * <ExpresionAritmetica> ::= "(" <ExpresionAritmetica> ")" [<ExpresionAuxiliar>] | <ValorNumerico>[<ExpresionAuxiliar>]
	 * <ExpresionAuxiliar> ::= operadorAritmetico <ExpresionAritmetica>[<ExpresionAuxiliar>]
	 * 
	 * @return
	 */
	public ExpresionAritmetica esExpresionAritmetica() 
	{
		if(tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) 
		{
			obtenerTokenSiguiente();
			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();
			
			if(expresionAritmetica != null) 
			{
				if(tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) 
				{
					obtenerTokenSiguiente();
					
					ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();
					
					return new ExpresionAritmetica(expresionAritmetica, expresionAuxiliar);
				}
			}
		}
		else 
		{
			ValorNumerico valorNumerico = esValorNumerico();
			
			if(valorNumerico != null) 
			{
				ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();
				
				return new ExpresionAritmetica(valorNumerico, expresionAritmetica);
			}
		}
		return null;
	}

	/**
	 * <ValorNumerico> ::=[<Signo>] real | [<Signo>] entero | [<Signo>] identificador
	 * @return
	 */
	private ValorNumerico esValorNumerico() {
		Token signo = null;
		if(tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO && (tokenActual.getPalabra().equals("-") || tokenActual.getPalabra().equals("+"))) 
		{
			signo = tokenActual;
			obtenerTokenSiguiente();
		}
		
		if(tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.REAL || tokenActual.getCategoria() == Categoria.IDENTIFICADOR)
		{
			Token termino = tokenActual;
			return new ValorNumerico(signo, termino);
		}
		
		return null;
	}

	/**
	 * <ExpresionAuxiliar> ::= operadorAritmetico <ExpresionAritmetica>[<ExpresionAuxiliar>]
	 */
	private ExpresionAuxiliar esExpresionAuxiliar() {
		if(tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) 
		{
			Token operadorAritmetico = tokenActual;
			obtenerTokenSiguiente();
			
			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();
			
			if(expresionAritmetica != null) 
			{
				ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();
				
				return new ExpresionAuxiliar(operadorAritmetico, expresionAritmetica, expresionAuxiliar);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}
}
