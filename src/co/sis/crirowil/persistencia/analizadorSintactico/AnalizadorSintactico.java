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
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && esTipoRetorno() != null) {
			Token retorno = tokenActual;
			obtenerTokenSiguiente();

			if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
				reportarError("El nombre del parametro no es valido");
				return null;
			}

			Token nombre = tokenActual;
			obtenerTokenSiguiente();
			return new Parametro(retorno, nombre);
		} else {
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
			return tokenActual;
		}
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
			obtenerTokenSiguiente();
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

		Sentencia s = esSisas();
		if (s != null)
			return s;

//		s = esCiclo();
//		if (s != null)
//			return s;
//
		s = esDeclaracionVariable();
		if (s != null)
			return s;
//
//		s = esSentenciaInvocacion();
//		if (s != null)
//			return s;
//
		s = esSentenciaAsignacion();
		if (s != null)
			return s;

		s = esRetorno();
		if (s != null)
			return s;

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
			
			if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getPalabra().equals("nada")) {
				obtenerTokenSiguiente();
				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerTokenSiguiente();
					return new Retorno();

				} else {
					reportarError("falta el terminal de la sentecia");
				}
			}
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerTokenSiguiente();
				if (tokenActual.getCategoria() == Categoria.TERMINAL) {
					obtenerTokenSiguiente();
					return new Retorno(identificador);

				} else {
					tokenActual = identificador;
					
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
				reportarError("falta el terminal de la sentecia");
				
			} else {
				Expresion expresion = esExpresion();
				if (expresion != null) {
					if (tokenActual.getCategoria() == Categoria.TERMINAL) {
						obtenerTokenSiguiente();
						return new Retorno(expresion);

					} else {
						reportarError("falta el terminal de la sentecia");
					}
				}else {
					reportarError("Sentencia de retorno erronea");
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

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;
			obtenerTokenSiguiente();

			if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {
				obtenerTokenSiguiente();
				ArrayList<Argumento> listaArgumentos = esListaArgumentos();
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
					obtenerTokenSiguiente();
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

		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la lista de argumentos es o no
	 * valido <ListaArgumentos> ::= <Argumento>[, <ListaArgumentos>]
	 */
	public ArrayList<Argumento> esListaArgumentos() {
		ArrayList<Argumento> listaArgumentos = new ArrayList<>();
		Argumento argumento = esArgumento();

		while (argumento != null) {
			listaArgumentos.add(argumento);
			if(tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
				argumento = null;
			} else if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerTokenSiguiente();
				argumento = esArgumento();
				if (argumento == null) {
					reportarError("Separador (,) demas en la lista de argumentos");
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

		return listaArgumentos;
	}

	/**
	 * Metodo que me verifica que dado el BNF de un argumento es o no valido
	 * <Argumento> ::= identificador | <Expresion>
	 */
	private Argumento esArgumento() {

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token nombre = tokenActual;
			obtenerTokenSiguiente();

			return new Argumento(nombre);
		} else {
			Expresion expresion = esExpresion();
			if (expresion != null) {
				return new Argumento(expresion);
			}
		}
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF del DeclaracionVariable es o no valido
	 * <DeclaracionVariable> ::= <TipoRetorno> identificador [<Asignacion>]"@"
	 * 
	 * @return
	 */
	public DeclaracionVariable esDeclaracionVariable() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && esTipoRetorno() != null) {
			Token tipoDato = tokenActual;
			obtenerTokenSiguiente();

			if (tokenActual.getCategoria() != Categoria.IDENTIFICADOR) {
				reportarError("El nombre de la varible no es valido");
				return null;
			}

			Token identificador = tokenActual;
			obtenerTokenSiguiente();

			Asignacion asignacion = esAsignacion();

			if (tokenActual.getCategoria() == Categoria.TERMINAL) {
				obtenerTokenSiguiente();
				return new DeclaracionVariable(tipoDato, identificador, asignacion);
			} else {
				reportarError("Falta el terminal \";\"");
				return null;
			}
		}
		return null;
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
			obtenerTokenSiguiente();

			Asignacion asignacion = esAsignacion();
			if (asignacion != null) {
				return new SentenciaAsignacion(nombre, asignacion);
			} else {
				reportarError("Asignacion invalida");
			}
		}
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la asignacion es o no valido
	 * <Asignacion> ::= operadorAsignacion <Expresion> | operadorAsignacion
	 * <InvocacionFuncion>
	 */
	public Asignacion esAsignacion() {
		if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
			Token operadorAsignacion = tokenActual;
			obtenerTokenSiguiente();

			Expresion expresion = esExpresion();
			if (expresion != null) {
				return new Asignacion(operadorAsignacion, expresion);
			} else {
				InvocacionFuncion invocacionFuncion = esInvocacionFuncion();
				if (invocacionFuncion != null) {
					return new Asignacion(operadorAsignacion, invocacionFuncion);
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
		Expresion expresion = null;
		Token tokenActualAux = tokenActual;
		int posActualAux = posActual;

		expresion = esExpresionAritmetica();
		if(tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {
			tokenActual = tokenActualAux;
			posActual = posActualAux;
			expresion = null;
		}
		if (expresion != null)
			return expresion;
		
		expresion = esExpresionRelacional();
		if(tokenActual.getCategoria() == Categoria.OPERADOR_LOGICO) {
			tokenActual = tokenActualAux;
			posActual = posActualAux;
			expresion = null;
		}
		if (expresion != null)
			return expresion;
		
		expresion = esExpresionLogica();
		if (expresion != null)
			return expresion;

		expresion = esExpresionCadena();
		if (expresion != null)
			return expresion;
		
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
	 * [<ExpresionAuxiliarLogica>] <ExpresionLogicaAuxiliar> ::= operadorLogio
	 * <ExpresionLogica> [<ExpresionAuxiliarLogica>]
	 * 
	 * @return
	 */
	public ExpresionLogica esExpresionLogica() {

		if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {

			obtenerTokenSiguiente();

			ExpresionLogica expresionLogica = esExpresionLogica();

			if (expresionLogica != null) {

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
					obtenerTokenSiguiente();
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

		 if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {

			obtenerTokenSiguiente();

			ExpresionRelacional expresionRelacional = esExpresionRelacional();

			if (expresionRelacional != null) {

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {

					return expresionRelacional;

				} else if(tokenActual.getCategoria() != Categoria.OPERADOR_LOGICO) {
					reportarError("Falta un parentesis que cierra ( \")\" )");
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

		} else {
			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();

			if (expresionAritmetica != null) {

				if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {
					Token operadorRelacional = tokenActual;
					obtenerTokenSiguiente();

					ExpresionAritmetica expresionAritmetica2 = esExpresionAritmetica();

					if (expresionAritmetica2 != null) {

						return new ExpresionRelacional(expresionAritmetica, expresionAritmetica2, operadorRelacional, null);

					} else {
						reportarError("Falta una expresion relacional");
						return null;
					}

				} else if(tokenActual.getCategoria() != Categoria.OPERADOR_LOGICO) {
					reportarError("Falta un operador relacional");
					return null;
				}

			}else {
				reportarError("Expresion relacional incompleta");
			}
		}

		return null;
	}

	/**
	 * <ExpresionAritmetica> ::= "(" <ExpresionAritmetica> ")" [<ExpresionAuxiliar>]
	 * | <ValorNumerico>[<ExpresionAuxiliar>] <ExpresionAuxiliar> ::=
	 * operadorAritmetico <ExpresionAritmetica>[<ExpresionAuxiliar>]
	 * 
	 * @return
	 */
	public ExpresionAritmetica esExpresionAritmetica() {
		if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRE) {
			obtenerTokenSiguiente();
			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();

			if (expresionAritmetica != null) {
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_CIERRA) {
					obtenerTokenSiguiente();

					ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();

					return new ExpresionAritmetica(expresionAritmetica, expresionAuxiliar);
				}
			}
		} else {
			ValorNumerico valorNumerico = esValorNumerico();

			if (valorNumerico != null) {
				ExpresionAuxiliar expresionAritmetica = esExpresionAuxiliar();

				return new ExpresionAritmetica(valorNumerico, expresionAritmetica);
			}
		}
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
			return new ValorNumerico(signo, termino);
		}

		reportarError("No hay valor numerico");
		return null;
	}

	/**
	 * Metodo que me verifica que dado el BNF de la expresionAuxiliar es o no valido
	 * <ExpresionAuxiliar> ::= operadorAritmetico
	 * <ExpresionAritmetica>[<ExpresionAuxiliar>]
	 */
	private ExpresionAuxiliar esExpresionAuxiliar() {
		if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
			Token operadorAritmetico = tokenActual;
			obtenerTokenSiguiente();

			ExpresionAritmetica expresionAritmetica = esExpresionAritmetica();

			if (expresionAritmetica != null) {
				ExpresionAuxiliar expresionAuxiliar = esExpresionAuxiliar();

				return new ExpresionAuxiliar(operadorAritmetico, expresionAritmetica, expresionAuxiliar);
			}
		}
		// TODO Auto-generated method stub
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
