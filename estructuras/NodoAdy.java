package estructuras;

public class NodoAdy {

	private NodoVert vertice;
	private NodoAdy sigAdyacente;
	private int etiqueta;

	public NodoAdy(NodoVert nuevoVertice, int etiqueta) {
		this.vertice = nuevoVertice;
		this.etiqueta = etiqueta;
	}

	// Observadores
	public NodoVert getVertice() {
		return vertice;
	}

	public NodoAdy getSigAdyacente() {
		return sigAdyacente;
	}

	public int getEtiqueta() {
		return etiqueta;
	}

	// Modificadores
	public void setVertice(NodoVert nuevoVertice) {
		this.vertice = nuevoVertice;
	}

	public void setSigAdyacente(NodoAdy nuevoSigAdyacente) {
		if (this.sigAdyacente == null) {
			this.sigAdyacente = nuevoSigAdyacente;
		} else {
			this.sigAdyacente.setSigAdyacente(nuevoSigAdyacente);
		}
	}

	public void setEtiqueta(int nuevaEtiqueta) {
		this.etiqueta = nuevaEtiqueta;
	}

}
