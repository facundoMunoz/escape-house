package estructuras;

public class NodoAVL {

    private Comparable elemento;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

    public NodoAVL(Comparable elemento){
        this.elemento = elemento;
        this.izquierdo = null;
        this.derecho = null;
    }
    
    public NodoAVL(Comparable elemento, NodoAVL izq, NodoAVL der) {
        this.elemento = elemento;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
    }

    public void setElem(Comparable elemento) {
        this.elemento = elemento;
    }

    public Comparable getElem() {
        return this.elemento;
    }

    public int getAltura() {
        return this.altura;
    }

    public void recalcularAltura() {
        int alturaDerecho = (this.derecho == null) ? -1 : this.derecho.getAltura();
        int alturaIzquierdo = (this.izquierdo == null) ? -1 : this.izquierdo.getAltura();
        this.altura = Math.max(alturaIzquierdo, alturaDerecho) + 1;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }

    public NodoAVL getIzquierdo() {
        return this.izquierdo;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }

    public NodoAVL getDerecho() {
        return this.derecho;
    }

}
