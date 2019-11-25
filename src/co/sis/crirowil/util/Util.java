package co.sis.crirowil.util;

public class Util {
	
	public static String traducirTipo(String tipo) 
	{
		String tipoTraducido = "";
		if(tipo.equals("cadena")) 
		{
			tipoTraducido = "String";
		} else if(tipo.equals("real")) 
		{
			tipoTraducido = "double";
		}else if(tipo.equals("entero")) 
		{
			tipoTraducido = "int";
		}else if(tipo.equals("bool")) 
		{
			tipoTraducido = "boolean";
		}
		else
		{
			tipoTraducido = "char";
		}
		return tipoTraducido;
	}
	
	public static String traducirOperadorLogico(String operador) 
	{
		String operadorTraducido = "";
		if(operador.equals("yy")) 
		{
			operadorTraducido = "&&";
		}
		else 
		{
			operadorTraducido = "||";
		}
		return operadorTraducido;
	}
	
}
