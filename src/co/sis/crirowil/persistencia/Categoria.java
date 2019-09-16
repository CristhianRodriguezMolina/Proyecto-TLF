package co.sis.crirowil.persistencia;

/**
 * Enumeracion me define el tipo de token identificado o desconocido si no lo
 * fue
 * 
 * @author Wilmar Stiven Valencia Cardona
 * @author Juan Manuel Roa Mejia
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public enum Categoria {

	ENTERO, REAL, CARACTER, CADENA_CARACTERES, IDENTIFICADOR, OPERADOR_ARITMETICO, OPERADOR_ASIGNACION, DESCONOCIDO,
	OPERADOR_LOGICO, SEPARADOR, TERMINAL, PARENTESIS_ABRE, PARENTESIS_CIERRA, CORCHETES_ABRE, CORCHETES_CIERRA,
	LLAVES_ABRE, LLAVES_CIERRA, HEXADECIMAL, INCREMENTO_DECREMENTO, PALABRA_RESERVADA, COMENTARIO_LINEA, PUNTO,
	DOS_PUNTOS, COMENTARIO_BLOQUE, OPERADOR_RELACIONAL, ERROR_LEXICO

}
