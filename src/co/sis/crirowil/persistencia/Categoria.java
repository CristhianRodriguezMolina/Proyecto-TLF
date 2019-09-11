package co.sis.crirowil.persistencia;

/**
 * Enumeracion me define el tipo de token identificado o desconocido si no lo fue
 * @author Wilmar Stiven Valencia Cardona
 * @author Juan Manuel Roa Mejia
 * @author Cristhian Camilo Rodriguez Molina
 * @version 1.0
 */
public enum Categoria {

	ENTERO, REAL, CADENA_CARACTERES, IDENTIFICADOR, OPERADOR_ARITMETICO, OPERADOR_ASIGNACION, DESCONOCIDO,
	OPERADOR_LOGICO, SEPARADOR, TERMINAL, PARENTESIS_ABRE, PARENTESIS_CIERRA, LLAVES_ABRE, LLAVES_CIERRA, HEXADECIMAL,
	PALABRA_RESERVADA, COMENTARIO_LINEA, COMENTARIO_BLOQUE, OPERADOR_RELACIONAL, ERROR_LEXICO

}
