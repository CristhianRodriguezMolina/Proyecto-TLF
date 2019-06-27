package co.sis.crirowil.persistencia;

public class Token implements Comparable<Token> {

	private Categoria categoria;
	private String palabra;
	private int fila, columna;
	
	public Token(Categoria categoria, String palabra, int fila, int columna) {
		super();
		this.categoria = categoria;
		this.palabra = palabra;
		this.fila = fila;
		this.columna = columna;
	}

	@Override
	public String toString() {
		return "Token [categoria=" + categoria + ", palabra=" + palabra + ", fila=" + fila + ", columna=" + columna
				+ "]\n";
	}

	@Override
	public int compareTo(Token o) {
		return categoria.getClass().getSimpleName().compareTo(o.getCategoria().getClass().getSimpleName());
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	
	
}
