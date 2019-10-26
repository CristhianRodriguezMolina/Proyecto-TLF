package co.sis.crirowil.persistencia.analizadorSintactico;

import java.util.ArrayList;

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
	 * Unidad de compilacion del codigo fuente
	 */
	UnidadDeCompilacion unidadDeCompilacion;

	/**
	 * Metodo constructor
	 * 
	 * @param tokens
	 */
	public AnalizadorSintactico(ArrayList<Token> tokens) {
		this.tokens = tokens;
		listaErrores = new ArrayList<>();
		tokenActual = tokens.get(posActual);
	}

	/**
	 * Analiza los tokens para verificar la sintactica correcta del lenguaje
	 */
	public void analizar() {
		unidadDeCompilacion = esUnidadDeCompilacion();
	}

	/**
	 * Metodo que describe si los token corresponden al BNF de la unidad de
	 * compilacion <UnidadDeCompilacion> ::= <listaFunciones>
	 * 
	 * @return
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {
		System.out.println("revisemos la unidada de compilacion");
		ArrayList<Funcion> listaFunciones = esListaFunciones();

		if (listaFunciones != null) {
			System.out.println("si es una unidad de compilacion valida");
			return new UnidadDeCompilacion(listaFunciones);
		} else {
			reportarError("la unidad de compilacion debe de tener una lista de funciones que va a ejecutar");
			return null;
		}
	}

	/**
	 * Metodo que me verifica que dado el BNF del listaFunciones es o no valido
	 * <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
	 */
	public ArrayList<Funcion> esListaFunciones() {
		
		System.out.println("revisemos la lista de funciones");
		ArrayList<Funcion> listaFunciones = new ArrayList<>();
		Funcion funcion = esFuncion();

		while (funcion != null) {
			listaFunciones.add(funcion);
			funcion = esFuncion();
		}

		return listaFunciones;
	}

	/**
	 * Metodo que describe si los token corresponden al BNF de la lista de funciones
	 * <Funcion> ::= metodo identificador "("[<ListaParametros>]")"
	 * [":"<TipoRetorno>] <BloqueSentencias>
	 */
	public Funcion esFuncion() {
		
		System.out.println("revisemos la funcion");

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("metodo")) {
			obtenerTokenSiguiente();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token nombre = tokenActual;
				obtenerTokenSiguiente();

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {
					obtenerTokenSiguiente();

					ArrayList<Parametro> listaParametros = esListaParametros();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
						
						obtenerTokenSiguiente();
						
						if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
							obtenerTokenSiguiente();
							Token retorno = esTipoRetorno();
							obtenerTokenSiguiente();
							BloqueSentencia bloqueSentencias = esBloqueSentencia();

							if (bloqueSentencias == null) {
								reportarError("Falta el bloque de sentencias");
								return null;
							}

							return new Funcion(nombre, listaParametros, retorno, bloqueSentencias);
						} else if(tokenActual.getCategoria() == Categoria.LLAVES_ABRE) {
							BloqueSentencia bloqueSentencias = esBloqueSentencia();

							if (bloqueSentencias == null) {
								reportarError("Falta el bloque de sentencias");
								return null;
							}

							System.out.println("si es una funcion valida");
							return new Funcion(nombre, listaParametros, null, bloqueSentencias);
						}
						
						reportarError("Falta el parentesis de cierre2");
						return null;
						
					} else {
						reportarError("Falta el parentesis de cierre");
						return null;
					}
				} else {
					reportarError("falta el parentesis de apertura");
					return null;
				}
			} else {
				reportarError("identificador de la funcion no es valido");
				return null;
			}
		} else {
			reportarError("Error, se esperaba la palabra metodo");
			return null;
		}
	}

	/**
	 * Metodo que me verifica que dado el BNF de listaParametros es o no valido
	 * <ListaParametros> ::= <Parametro>[","<ListaParametros>]
	 * 
	 * @return
	 */
	public ArrayList<Parametro> esListaParametros() {
		
		System.out.println("revisemos la lista de parametros");
		ArrayList<Parametro> listaParametros = new ArrayList<>();
		Parametro parametro = esParametro();
		while (parametro != null) {
			listaParametros.add(parametro);
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerTokenSiguiente();
				parametro = esParametro();
				if (parametro == null) {
					return listaParametros;
				}
			} else {
				
				parametro = esParametro();
				if (parametro != null) {
					reportarError("falta una coma entre parametros");
					return null;
				}
			}
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
		
		System.out.println("revisemos el parametro");
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && esTipoRetorno() != null) {
			Token retorno = tokenActual;
			obtenerTokenSiguiente();

			if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
				reportarError("El nombre del parametro no es valido");
				return null;
			}

			Token nombre = tokenActual;
			obtenerTokenSiguiente();
			System.out.println("es un parametro valido");
			return new Parametro(retorno, nombre);
		} else {
			System.out.println("no es un parametro valido");
			return null;
		}

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
			System.out.println("es un tipo de retorno valido");
			return tokenActual;
		}
		System.out.println("no es un tipo de retorno valido");
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del Sisas es o no valido <Sisas> ::=
	 * sisas <condicion> ":" <BloqueSentencias> [<ListaNonais>][<Nonas>]
	 * 
	 * @return
	 */
	public Sisas esSisas() {
		if (!(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("sisas"))) {
			return null;
		}

		obtenerTokenSiguiente();

		Condicion condicion = esCondicion();
		System.out.println("paso");

		if (condicion == null) {
			reportarError("la sentencia sisas debe de tener una condicon valida");
			return null;
		}

		if (tokenActual.getCategoria() != Categoria.DOS_PUNTOS) {
			reportarError("Token inesperado, se esperaba: \":\"");
			return null;
		}

		obtenerTokenSiguiente();

		BloqueSentencia bloqueSentenciasSisas = esBloqueSentencia();

		if (bloqueSentenciasSisas == null) {
			reportarError("Falta el bloque de sentencias sisas");
			return null;
		}

		ArrayList<Nonais> listaNonais = esListaNonais();

		Nonas nonas = esNonas();

		return new Sisas(condicion, bloqueSentenciasSisas, listaNonais, nonas);
	}

	/**
	 * Metodo que me verifica que dado el BNF de la listaNonais es o no valido
	 * <ListaNonais> ::= <Nonais>[<ListaNonais>]
	 * 
	 * @return
	 */
	public ArrayList<Nonais> esListaNonais() {
		ArrayList<Nonais> listaNonais = new ArrayList<>();
		Nonais nonais = esNonais();

		while (nonais != null) {
			listaNonais.add(nonais);
			nonais = esNonais();
		}

		return listaNonais;
	}

	/**
	 * Metodo que me verifica que dado el BNF del nonais es o no valido <Nonais> ::=
	 * nonais <BloqueSentencia>
	 * 
	 * @return
	 */
	public Nonais esNonais() {
		
		if (!(tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("nonais"))) {
			return null;
		}

		obtenerTokenSiguiente();

		Condicion condicion = esCondicion();

		if (condicion == null) {
			reportarError("la sentencia nonais debe de tener una condicon valida");
			return null;
		}

		if (tokenActual.getCategoria() != Categoria.DOS_PUNTOS) {
			reportarError("Token inesperado, se esperaba: \":\"");
			return null;
		}

		obtenerTokenSiguiente();

		BloqueSentencia bloqueSentencias = esBloqueSentencia();

		if (bloqueSentencias == null) {
			reportarError("Falta el bloque de sentencias");
			return null;
		}
		
		return new Nonais(condicion, bloqueSentencias);
	}

	/**
	 * Metodo que me verifica que dado el BNF del nonas es o no valido <Nonas> ::=
	 * nonas <BloqueSentencia>
	 * 
	 * @return
	 */
	public Nonas esNonas() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("nonas")) {
			BloqueSentencia bloqueSentencia = esBloqueSentencia();
			if (bloqueSentencia != null) {
				return new Nonas(bloqueSentencia);
			} else {
				reportarError("Falta el bloque de sentencias");
			}
		}
		return null;
	}

	public Condicion esCondicion() {
		Expresion expresion = esExpresion();
		
		if(expresion != null) {
			
			if(expresion instanceof ExpresionRelacional || expresion instanceof ExpresionLogica) {
				return new Condicion(expresion);
			}else {
				reportarError("La condicion debe ser una expresion relacional o logica no una "+expresion.getClass().getSimpleName());
				return null;
			}
			
		}else {
			reportarError("Hace falta una condicion");
			return null;
		}
	}

	/**
	 * Metodo que me verifica que dado el BNF del bloqueSentencias es o no valido
	 * <BloqueSentencia> ::= "{"<listaSentecias>"}"
	 * 
	 * @return
	 */
	public BloqueSentencia esBloqueSentencia() {
		System.out.println("revisemos el bloque se sentencias");
		if (tokenActual.getPalabra().equals("{")) {
			if (obtenerTokenSiguiente()) {
				ArrayList<Sentencia> listaSentencia = esListaSentencias();
				if (tokenActual.getPalabra().equals("}")) {
					obtenerTokenSiguiente();
					return new BloqueSentencia(listaSentencia);
				}
			}
		}
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la listaSentecia es o no valido
	 * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
	 * 
	 * @return
	 */
	public ArrayList<Sentencia> esListaSentencias() {
		System.out.println("revisemos la lista de sentencias");
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
			tokenActual = new Token(Categoria.DESCONOCIDO, "?", 0, 0);
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
		System.out.println("revisemos las sentencias");

		Sentencia s;
//		s = esSisas();
//		if (s != null)
//			return s;
//
//		s = esCiclo();
//		if (s != null)
//			return s;
//
//		s = esDeclaracionVariable();
//		if (s != null)
//			return s;
//
		s = esSentenciaInvocacion();
		if (s != null)
			return s;
//
//		s = esSentenciaAsignacion();
//		if (s != null)
//			return s;
//
//		s = esRetorno();
//		if (s != null)
//			return s;

		return null;

	}

	/**
	 * Metodo que me verifica que dado el BNF de la sentencia de invocacion es o no
	 * valido <SentenciaInvocacion> ::= <InvocacionFuncion> "@"
	 * 
	 * @return
	 */
	public SentenciaInvocacion esSentenciaInvocacion() {
		InvocacionFuncion invocacionFuncion = esInvocacionFuncion();

		if (invocacionFuncion != null) {
			if (tokenActual.getCategoria() == Categoria.TERMINAL) {
				obtenerTokenSiguiente();
				return new SentenciaInvocacion(invocacionFuncion);
			}
		}
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del Retorno es o no valido <Retorno>
	 * ::= "retorno" identificador "@"| "retorno" <Expresion> "@"| "retorno"
	 * <InvocacionFuncion> "@"
	 * 
	 * @return
	 */
	public Retorno esRetorno() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("retorno")) {
			obtenerTokenSiguiente();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerTokenSiguiente();
				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerTokenSiguiente();
					return new Retorno(identificador);

				} else {
					reportarError("falta el terminal de la sentecia");
				}
			} else {
				Expresion expresion = esExpresion();
				if (expresion != null) {
					if (tokenActual.getCategoria() == Categoria.TERMINAL) {
						obtenerTokenSiguiente();
						return new Retorno(expresion);

					} else {
						reportarError("falta el terminal de la sentecia");
					}
				} else {
					InvocacionFuncion invocacionFuncion = esInvocacionFuncion();
					if (invocacionFuncion != null) {
						if (tokenActual.getCategoria() == Categoria.TERMINAL) {
							obtenerTokenSiguiente();
							return new Retorno(invocacionFuncion);

						} else {
							reportarError("falta el terminal de la sentecia");
						}
					}
				}
			}
		}
		return null;

	}

	/**
	 * Metodo que me verifica que dado el BNF del InvocacionFuncion es o no valido
	 * <InvocacionFuncion> ::= identificador "("[<listaArgumentos>]")"
	 * 
	 * @return
	 */
	public InvocacionFuncion esInvocacionFuncion() {

		System.out.println("revisemos si es una invoacion de una funcion");
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;
			obtenerTokenSiguiente();

			if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {
				obtenerTokenSiguiente();
				ArrayList<Argumento> listaArgumentos = esListaArgumentos();
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
					obtenerTokenSiguiente();
					System.out.println("es una invocacion de una funcion valida");
					return new InvocacionFuncion(nombre, listaArgumentos);
				} else {
					reportarError("Falta el parentesis de cierre");
					return null;
				}
			} else {
				reportarError("Falta el parentesis de apertura");
				return null;
			}
		}
		System.out.println("no es una invocacion de una funcion valida");
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la lista de argumentos es o no
	 * valido <ListaArgumentos> ::= <Argumento>[, <ListaArgumentos>]
	 */
	public ArrayList<Argumento> esListaArgumentos() {
		System.out.println("revisemos lo argumentos");
		ArrayList<Argumento> listaArgumentos = new ArrayList<>();
		Argumento argumento = esArgumento();
		System.out.println("Token actual en lista de argumentos: " +  tokenActual);
		System.out.println("asdisandsadas");
		System.out.println("argumento actual: " + (argumento == null));

		while (argumento != null) {
			listaArgumentos.add(argumento);
			System.out.println("estoy en el while");
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				System.out.println("es un separador");
				System.out.println("revisemos el siquiente argumento");
				obtenerTokenSiguiente();
				argumento = esArgumento();
				if (argumento == null) {
					reportarError("lista de argumentos invalida");
					return null;
				}
			} else {
				argumento = esArgumento();
				if (argumento != null) {
					reportarError("falta el separador de argumentos");
					return null;
				}
			}
		}

		if (listaArgumentos.size() == 0) {
			reportarError("La lista de argumentos debe tener minimo un argumento valido");
			return null;
		}
		return listaArgumentos;
	}

	/**
	 * Metodo que me verifica que dado el BNF de un argumento es o no valido
	 * <Argumento> ::= identificador | <Expresion>
	 */
	private Argumento esArgumento() {
		
		System.out.println("revisemos si es un argumento");

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;
			obtenerTokenSiguiente();

			System.out.println("si es un argumento valido");
			return new Argumento(nombre);

		} else {
			Expresion expresion = esExpresion();
			if (expresion != null) {
				System.out.println("si es un argumento valido");
				return new Argumento(expresion);
			}
		}
		System.out.println("no es un argumento valido");
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del DeclaracionVariable es o no valido
	 * <DeclaracionVariable> ::= <TipoRetorno> identificador [<Asignacion>]"@"
	 * 
	 * @return
	 */
	public DeclaracionVariable esDeclaracionVariable() {
		System.out.println("revisemos si es una declaracion de una varivale");
		System.out.println("Token Actual:" + tokenActual);
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && esTipoRetorno() != null) {
			System.out.println("es un tipo de retorno");
			Token tipoDato = tokenActual;
			obtenerTokenSiguiente();
			
			System.out.println("Token Actual:" + tokenActual);

			if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
				reportarError("El nombre de la varible no es valido");
				return null;
			}
			
			System.out.println("es un identificador");

			Token identificador = tokenActual;
			obtenerTokenSiguiente();
			
			System.out.println("Token Actual:" + tokenActual);

			Asignacion asignacion = esAsignacion();

			if (tokenActual.getCategoria() == Categoria.TERMINAL) {
				obtenerTokenSiguiente();
				System.out.println("es una declaracion de variable valida");
				return new DeclaracionVariable(tipoDato, identificador, asignacion);
			} else {
				reportarError("Falta el terminal \"@\"");
				return null;
			}
		} else {
			System.out.println("no es un declaracion de variable valida");
			return null;

		}
	}

	/**
	 * Metodo que me verifica que dado el BNF del Ciclo es o no valido <Ciclo> ::=
	 * <condicion> ":" <BloqueSentencia> | ciclo <SentenciaAsignacion> ","
	 * <Condicion> "," <SentenciaAsignacion> ":" <BloqueSentencias>
	 * 
	 * @return
	 */
	public Ciclo esCiclo() {

		boolean tieneAsignacion = false;

		Asignacion asignacion = esAsignacion();

		if (asignacion != null) {
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				tieneAsignacion = true;
			} else {
				reportarError("Falta un separador");
			}
		}

		Condicion condicion = esCondicion();
		if (condicion != null) {
			SentenciaAsignacion sentenciaAsignacion = esSentenciaAsignacion();

			if (asignacion != null && tieneAsignacion) {
				if (sentenciaAsignacion != null) {
					if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
						obtenerTokenSiguiente();
						BloqueSentencia bloqueSentencia = esBloqueSentencia();
						if (bloqueSentencia != null) {
							return new Ciclo(asignacion, condicion, sentenciaAsignacion, bloqueSentencia);
						} else {
							reportarError("falta las sentecias en el ciclo");
							return null;
						}

					}
				} else {
					reportarError("Sentencia de ciclo incompleta");
					return null;
				}
			} else {
				if (tokenActual.getCategoria() == Categoria.DOS_PUNTOS) {
					obtenerTokenSiguiente();
					BloqueSentencia bloqueSentencia = esBloqueSentencia();
					if (bloqueSentencia != null) {
						return new Ciclo(condicion, bloqueSentencia);
					} else {
						reportarError("falta las sentecias en el ciclo");
						return null;
					}
				}
			}
			return null;

		}

		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del Ciclo es o no valido
	 * <SentenciaAsigncacion> ::= identificador <Asignacion> "@"
	 * 
	 * @return
	 */
	public SentenciaAsignacion esSentenciaAsignacion() {
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;

			Asignacion asignacion = esAsignacion();
			if (asignacion != null) {
				return new SentenciaAsignacion(nombre, asignacion);
			} else {
				reportarError("asignacion no valida");
			}
		}
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la asignacion es o no valido
	 * <Asignacion> ::= operadorAsignacion <InvocacionFuncion> | operadorAsignacion <Expresion>
	 */
	public Asignacion esAsignacion() {
		System.out.println("revisemos la asignacion");
		System.out.println("Token Actual: " + tokenActual);
		if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
			System.out.println("");
			Token operadorAsignacion = tokenActual;
			obtenerTokenSiguiente();
			System.out.println("Token Actual: " + tokenActual);
			InvocacionFuncion invocacionFuncion = esInvocacionFuncion();
			if (invocacionFuncion != null) {
				return new Asignacion(operadorAsignacion, invocacionFuncion);
			} else {
				Expresion expresion = esExpresion();
				if (expresion != null) {
					return new Asignacion(operadorAsignacion, expresion);
				}
			}
			reportarError("La asignacion que esta haciendo es invalida");
			return null;
		}
		return null;
	}

	/**
	 * @return
	 */
	public Expresion esExpresion() {
		System.out.println("es Expresion");
		Expresion expresion = null;
		
		expresion = esExpresionLogica();
		if (expresion != null) { 
			System.out.println("LA EXPRESION ES LOGICA");
			return expresion;}

		expresion = esExpresionRelacional();
		if (expresion != null) {
			System.out.println("LA EXPRESION ES RELACIONAL");
			return expresion;}
		
		expresion = esExpresionAritmetica();
		if (expresion != null) {
			System.out.println("LA EXPRESION ES ARITMETICA");
			return expresion;}

		expresion = esExpresionCadena();
		if (expresion != null) {
			System.out.println("LA EXPRESION ES DE CADENA");
			return expresion;}
		
		System.out.println("LA EXPRESION ES INVALIDA");
		return null;
	}

	public ExpresionCadena esExpresionCadena() {

		if (tokenActual.getCategoria() != Categoria.CADENA_CARACTERES) {
			return null;
		}

		Token cadenaCaracteres = tokenActual;

		obtenerTokenSiguiente();

		if (tokenActual.getCategoria() != Categoria.OPERADOR_ARITMETICO || !tokenActual.getPalabra().equals("+")) {
			return new ExpresionCadena(cadenaCaracteres);
		}

		obtenerTokenSiguiente();

		Expresion expresion = esExpresion();

		if (expresion == null) {
			reportarError("Falta la expresion del mas (+)");
			return null;
		}

		return new ExpresionCadena(cadenaCaracteres, expresion);
	}

	/**
	 * <ExpresionLogica> ::= "(" <ExpresionLogica> ")" [<ExpresionAuxiliarLogica>] |
	 * "!" <ExpresionLogica> [<ExpresionAuxiliarLogica>] | <ExpresionRelacional>
	 * [<ExpresionAuxiliarLogica>]
	 * 
	 * @return
	 */
	public ExpresionLogica esExpresionLogica() {
		
		System.out.println("revisemos si Es expresion Logica");
		System.out.println("token actual:" + tokenActual);

		if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {

			obtenerTokenSiguiente();

			ExpresionLogica expresionLogica = esExpresionLogica();

			if (expresionLogica != null) {

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {

					ExpresionAuxiliarLogica expresionAuxiliarLogica = esExpresionAuxiliarLogica();

					return new ExpresionLogica(expresionLogica, null, expresionAuxiliarLogica, false);

				} else {
					reportarError("Faltan parentesis que cierran");
					return null;
				}

			} else {
				reportarError("Falta expresion logica");
				return null;
			}

		} else if (tokenActual.getCategoria() == Categoria.OPERADOR_LOGICO && tokenActual.getPalabra().equals("!")) {

			obtenerTokenSiguiente();

			ExpresionLogica expresionLogica = esExpresionLogica();

			if (expresionLogica != null) {

				ExpresionAuxiliarLogica expresionAuxiliarLogica = esExpresionAuxiliarLogica();

				return new ExpresionLogica(expresionLogica, null, expresionAuxiliarLogica, true);

			} else {
				reportarError("Falta una expresion logica");
				return null;
			}

		} else {

			ExpresionRelacional expresionRelacional = esExpresionRelacional();

			if (expresionRelacional != null) {

				ExpresionAuxiliarLogica expresionAuxiliarLogica = esExpresionAuxiliarLogica();

				return new ExpresionLogica(null, expresionRelacional, expresionAuxiliarLogica, false);

			} else {
				reportarError(
						"Falta una expresion relacional, un parentesis que abre ( \"(\") o un operador logico ( \"!\" )");
			}

		}

		return null;
	}

	/**
	 * <ExpresionLogicaAuxiliar> ::= operadorLogio <ExpresionLogica>
	 * [<ExpresionAuxiliarLogica>]
	 * 
	 * @return
	 */
	private ExpresionAuxiliarLogica esExpresionAuxiliarLogica() {
		if (tokenActual.getCategoria() == Categoria.OPERADOR_LOGICO) {
			Token operadorLogico = tokenActual;
			obtenerTokenSiguiente();

			ExpresionLogica expresionLogica = esExpresionLogica();

			if (expresionLogica != null) {
				ExpresionAuxiliarLogica expresionAuxiliarLogica = esExpresionAuxiliarLogica();

				return new ExpresionAuxiliarLogica(operadorLogico, expresionLogica, expresionAuxiliarLogica);
			}
		}
		return null;
	}

	/**
	 * <ExpresionRelacional> ::= <ExpresionAritmetica> operadorRelacional
	 * <ExpresionAritmetica> | "(" <ExpresionRelacional> ")" | true | false
	 * 
	 * @return
	 */
	public ExpresionRelacional esExpresionRelacional() {
		System.out.println("revisemos la expresion relacional");
		System.out.println("token actual:" + tokenActual);
		ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();
		System.out.println("en expresion relacional encontramos una expresion aritmetica valida");
		System.out.println("expresion aritmetica " + (expresionAritmetica == null));

		if (expresionAritmetica != null) {

			if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {
				System.out.println("su es un operador relacional");
				Token operadorRelacional = tokenActual;
				obtenerTokenSiguiente();
				System.out.println("token actual:" + tokenActual);
				ExpresionAritmetica expresionAritmetica2 = esExpresionAritmetica();

				if (expresionAritmetica2 != null) {

					return new ExpresionRelacional(expresionAritmetica, expresionAritmetica2, operadorRelacional, null);

				} else {
					reportarError("Falta una expresion relacional");
					return null;
				}

			} else {
				reportarError("Falta un operador relacional");
				return null;
			}

		} else if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {

			obtenerTokenSiguiente();

			ExpresionRelacional expresionRelacional = esExpresionRelacional();

			if (expresionRelacional != null) {

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {

					return expresionRelacional;

				} else {
					reportarError("Falta un parentesis que cierra ( \"(\" )");
					return null;
				}

			} else {
				reportarError("Falta una expresion relacional despues del parentesis");
				return null;
			}

		} else if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
				&& (tokenActual.getPalabra().equals("true") || tokenActual.getPalabra().equals("false"))) {

			Token valorVerdad = tokenActual;
			obtenerTokenSiguiente();

			return new ExpresionRelacional(null, null, null, valorVerdad);

		}

		return null;
	}

	/**
	 * <ExpresionAritmetica> ::= "(" <ExpresionAritmetica> ")" [<ExpresionAuxiliar>]
	 * | <ValorNumerico>[<ExpresionAuxiliar>]
	 * 
	 * @return
	 */
	public ExpresionAritmetica esExpresionAritmetica() {
		System.out.println("revisemos si es una expresionAritmetica");
		System.out.println("token actual:" + tokenActual);
		if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {
			obtenerTokenSiguiente();
			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();

			if (expresionAritmetica != null) {
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
					obtenerTokenSiguiente();

					ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();

					System.out.println("si es una expresion aritmetica");
					return new ExpresionAritmetica(expresionAritmetica, expresionAuxiliar);
				}
			}
		} else {
			ValorNumerico valorNumerico = esValorNumerico();

			if (valorNumerico != null) {
				ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();
				System.out.println("si es una expresion aritmetica");
				return new ExpresionAritmetica(valorNumerico, expresionAuxiliar);
			}
		}
		System.out.println("no es una expresion aritmetica");
		return null;
	}
	
	/**
	 * Metodo que me verifica que dado el BNF de la expresionAuxiliar es o no valido
	 * <ExpresionAuxiliar> ::= operadorAritmetico <ExpresionAritmetica>[<ExpresionAuxiliar>]
	 */
	private ExpresionAuxiliar esExpresionAuxiliar() {
		System.out.println("revisemos si es una exoresion auxiliar");
		System.out.println("Token Actual: " + tokenActual);
		if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
			Token operadorAritmetico = tokenActual;
			obtenerTokenSiguiente();

			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();

			if (expresionAritmetica != null) {
				ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();
				System.out.println("si es una expresion auxiar");
				return new ExpresionAuxiliar(operadorAritmetico, expresionAritmetica, expresionAuxiliar);
			}
		}
		System.out.println("no es una expresion auxiar");
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del ValorNumerico es o no valido
	 * <ValorNumerico> ::=[<Signo>] real | [<Signo>] entero | [<Signo>]
	 * identificador
	 * 
	 * @return
	 */
	private ValorNumerico esValorNumerico() {
		
		System.out.println("revisemos si es valor numerico");
		Token signo = null;
		if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO
				&& (tokenActual.getPalabra().equals("-") || tokenActual.getPalabra().equals("+"))) {
			signo = tokenActual;
			obtenerTokenSiguiente();
		}

		if (tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.REAL
				|| tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token termino = tokenActual;
			obtenerTokenSiguiente();
			System.out.println("si es un valor numerico");
			return new ValorNumerico(signo, termino);
		}
		System.out.println("no es un valor numerico");
		return null;
	}

	

	/**
	 * 
	 * @return retorna la Unidad de compilacion de programa
	 */
	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	/**
	 * 
	 * @return los errores sintacticos del programa
	 */
	public ArrayList<ErrorSintactico> getListaErrores() {
		return listaErrores;
	}
	
}
