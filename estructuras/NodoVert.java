package estructuras;

public class NodoVert {

	private Object elem;
	private NodoVert sigVertice;
	private NodoAdy primerAdy;

	// Constructor
	public NodoVert(Object nuevoElem, NodoVert nuevoSigVertice) {
		this.elem = nuevoElem;
		this.sigVertice = nuevoSigVertice;
	}

	// Observadores
	public Object getElem() {
		return elem;
	}

	public NodoVert getSigVertice() {
		return sigVertice;
	}

	public NodoAdy getPrimerAdy() {
		return primerAdy;
	}

	// Modificadores
	public void setElem(Object nuevoElem) {
		this.elem = nuevoElem;
	}

	public void setSigVertice(NodoVert nuevoNodoVert) {
		this.sigVertice = nuevoNodoVert;
	}

	public void setPrimerAdy(NodoAdy nuevoAdy) {
		this.primerAdy = nuevoAdy;
	}

}
