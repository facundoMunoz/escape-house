package estructuras;

public class NodoVert {

    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object nuevoElem, NodoVert nuevoSigVertice) {
        this.elem = nuevoElem;
        this.sigVertice = nuevoSigVertice;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object nuevoElem) {
        this.elem = nuevoElem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public void setSigVertice(NodoVert nuevoNodoVert) {
        this.sigVertice = nuevoNodoVert;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setPrimerAdy(NodoAdy nuevoAdy) {
        if (this.primerAdy == null) {
            this.primerAdy = nuevoAdy;
        } else {
            this.primerAdy.setSigAdyacente(nuevoAdy);
        }
    }

}
