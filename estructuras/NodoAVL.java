package estructuras;

public class NodoAVL {

	// La clave se utiliza para buscar
    private Comparable clave;
    // El objeto tiene el resto de los atributos
    private Object objeto;
    private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;
    
    // Constructores
    public NodoAVL(Comparable clave, Object objeto){
        this.clave = clave;
        this.objeto = objeto;
        this.izquierdo = null;
        this.derecho = null;
    }
    
    public NodoAVL(Comparable clave, Object objeto, NodoAVL izq, NodoAVL der) {
        this.clave = clave;
        this.objeto = objeto;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
    }
    
    public Comparable getClave() {
        return this.clave;
    }
    
    public void setClave(Comparable claveNueva) {
    	this.clave = claveNueva;
    }

    public Object getObjeto() {
    	return this.objeto;
    }
    
    public void setObjeto(Object objetoNuevo) {
    	this.objeto = objetoNuevo;
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
