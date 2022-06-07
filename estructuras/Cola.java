package estructuras;

public class Cola {

	private Nodo frente;
	private Nodo fin;

	// Constructor
	public Cola() {

		this.fin = null;
		this.frente = null;

	}

	// Modificadores
	public boolean poner(Object nuevoElem) {
		Nodo nuevoNodo = new Nodo(nuevoElem, null);

		if (this.esVacia()) {
			// Si está vacía el nodo es el nuevo frente y fin
			this.fin = nuevoNodo;
			this.frente = nuevoNodo;
		} else {
			this.fin.setEnlace(nuevoNodo);
			this.fin = this.fin.getEnlace();
		}

		return true;
	}

	public boolean sacar() {
		boolean exito = true;

		if (this.esVacia()) {
			exito = false;
		} else {
			this.frente = this.frente.getEnlace();
			if (this.frente == null) {
				// Si el frente pasa a ser null tampoco hay fin
				this.fin = null;
			}
		}

		return exito;
	}

	// Obesrvadores
	public Object obtenerFrente() {
		Object elementoFrente = null;

		if (!this.esVacia()) {
			elementoFrente = this.frente.getElemento();
		}

		return elementoFrente;
	}

	public boolean esVacia() {

		return this.frente == null;

	}

	// Propias del tipo
	public void vaciar() {

		// Deja que se lleve los nodos el garbage collector
		this.fin = null;
		this.frente = null;

	}

	@Override
	public Cola clone() {
		Cola clon = new Cola();

		if (!this.esVacia()) {
			clon.frente = new Nodo(this.frente.getElemento(), null);
			clon.fin = clon.frente;
			Nodo nuevoNodo = this.frente.getEnlace();

			while (nuevoNodo != null) {
				// Vamos asignando el fin al próximo nodo
				clon.fin.setEnlace(new Nodo(nuevoNodo.getElemento(), null));
				nuevoNodo = nuevoNodo.getEnlace();
				clon.fin = clon.fin.getEnlace();
			}
		}

		return clon;
	}

	// Testing
	@Override
	public String toString() {
		String texto;

		if (this.esVacia()) {
			texto = "Cola vacia";
		} else {
			Nodo nodoAuxiliar = this.frente;
			texto = "";

			while (nodoAuxiliar != null) {
				texto += nodoAuxiliar.getElemento();
				nodoAuxiliar = nodoAuxiliar.getEnlace();
				if (nodoAuxiliar != null) {
					texto += ",";
				}
			}
		}

		return texto;
	}

}
