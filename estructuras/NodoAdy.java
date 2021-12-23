package estructuras;

public class NodoAdy {

    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private Object etiqueta;

    public NodoAdy(NodoVert nuevoVertice) {
        this.vertice = nuevoVertice;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public Object getEtiqueta() {
        return etiqueta;
    }

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

    public void setEtiqueta(Object nuevaEtiqueta) {
        this.etiqueta = nuevaEtiqueta;
    }

}
