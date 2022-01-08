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

    public boolean pertenece(Object buscado) {
        int pos = buscado.hashCode() % TablaHash.TAMANIO;
        NodoDicc aux = this.hash[pos];
        boolean encontrado = false;

        while (!encontrado && aux != null) {
            encontrado = aux.getCodigo().equals(buscado);
            aux = aux.getEnlace();
        }
        
        return encontrado;
    }

    public boolean insertar(Object nuevoCodigo, Object nuevoObjeto) {
        int pos = nuevoCodigo.hashCode() % TablaHash.TAMANIO;
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

    public boolean eliminar(Object buscado) {
        int pos = buscado.hashCode() % TablaHash.TAMANIO;
        NodoDicc predecesor = this.hash[pos];
        boolean encontrado = false;
        
        while (!encontrado && predecesor.getEnlace() != null) {
            encontrado = predecesor.getEnlace().getCodigo().equals(buscado);
            predecesor = predecesor.getEnlace();
        }

        if (!encontrado) {
            this.hash[pos] = this.hash[pos].getEnlace();
            this.cant--;
        }

        return !encontrado;
    }
    
    public boolean esVacia() {
    	return (this.cant == 0);
    }

    @Override
    public String toString() {
        String texto = "";
        int pos = 0;

        while (pos < TAMANIO) {
            NodoDicc nodo = hash[pos];
            while (nodo != null) {
                texto = texto + nodo.getObjeto().toString() + ", ";
                nodo = nodo.getEnlace();
            }
            pos++;
        }
        
        return texto;
    }

}
