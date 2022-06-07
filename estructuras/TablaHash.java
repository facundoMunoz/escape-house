package estructuras;

public class TablaHash {
	// Implementación abierta de tabla Hash

	private static final int TAMANIO = 100;
	private NodoDicc[] hash;
	private int cant;

	public TablaHash() {
		this.hash = new NodoDicc[TAMANIO];
		this.cant = 0;
	}

	public boolean pertenece(Object codigo) {
		int pos = codigo.hashCode() % TablaHash.TAMANIO;
		pos = valorAbsoluto(pos);
		NodoDicc aux = this.hash[pos];
		boolean encontrado = false;

		while (!encontrado && aux != null) {
			encontrado = aux.getCodigo().equals(codigo);
			aux = aux.getEnlace();
		}

		return encontrado;
	}

	public boolean insertar(Object nuevoCodigo, Object nuevoObjeto) {
		int pos = nuevoCodigo.hashCode() % TablaHash.TAMANIO;
		pos = valorAbsoluto(pos);
		NodoDicc aux = this.hash[pos];
		boolean encontrado = false;

		while (!encontrado && aux != null) {
			encontrado = aux.getCodigo().equals(nuevoCodigo);
			aux = aux.getEnlace();
		}

		// Si no está en la lista lo pone delante
		if (!encontrado) {
			this.hash[pos] = new NodoDicc(nuevoCodigo, nuevoObjeto, this.hash[pos]);
			this.cant++;
		}

		return !encontrado;
	}

	public boolean eliminar(Object codigo) {
		int pos = codigo.hashCode() % TablaHash.TAMANIO;
		pos = valorAbsoluto(pos);
		NodoDicc predecesor = this.hash[pos];
		boolean encontrado = false;

		while (!encontrado && predecesor.getEnlace() != null) {
			encontrado = predecesor.getEnlace().getCodigo().equals(codigo);
			predecesor = predecesor.getEnlace();
		}

		if (!encontrado) {
			this.hash[pos] = this.hash[pos].getEnlace();
			this.cant--;
		}

		return !encontrado;
	}

	public Object getObjeto(Object codigo) {
		int pos = codigo.hashCode() % TablaHash.TAMANIO;
		pos = valorAbsoluto(pos);
		NodoDicc nodo = this.hash[pos];
		Object encontrado = null;

		while (nodo != null && !nodo.getCodigo().equals(codigo)) {
			nodo = nodo.getEnlace();
		}

		if (nodo != null) {
			encontrado = nodo.getObjeto();
		}

		return encontrado;
	}

	public boolean esVacia() {
		return (this.cant == 0);
	}

	private int valorAbsoluto(int codigo) {
		if (codigo < 0) {
			codigo = -codigo;
		}

		return codigo;
	}

	public Lista hacerLista() {
		// Recorremos con fuerza bruta los nodos del arreglo
		Lista lista = new Lista();
		int pos = 0;
		int posLista = 1;

		while (pos < TAMANIO) {
			NodoDicc nodo = hash[pos];

			while (nodo != null) {
				lista.insertar(nodo.getObjeto(), posLista);
				posLista++;
				nodo = nodo.getEnlace();
			}
			pos++;
		}

		return lista;
	}

	@Override
	public String toString() {
		String texto = "";
		int pos = 0;

		while (pos < TAMANIO) {
			NodoDicc nodo = hash[pos];
			while (nodo != null) {
				texto = texto + nodo.getObjeto().toString() + "\n";
				nodo = nodo.getEnlace();
			}
			pos++;
		}

		return texto;
	}

}
