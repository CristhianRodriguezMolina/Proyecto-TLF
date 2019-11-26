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
			tipoTraducido = "Double";
		}else if(tipo.equals("entero")) 
		{
			tipoTraducido = "Integer";
		}else if(tipo.equals("bool")) 
		{
			tipoTraducido = "Boolean";
		}
		else if(tipo.equals("char"))
		{
			tipoTraducido = "Character";
		}
		else 
		{
			tipoTraducido = "HashMap";
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
