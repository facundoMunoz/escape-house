package estructuras;

public class NodoDicc {

    private Object codigo;
    private Object objeto;
    private NodoDicc enlace;

    // Constructor
    public NodoDicc(Object codigo, Object objeto, NodoDicc enlace) {

        this.codigo = codigo;
        this.objeto = objeto;
        this.enlace = enlace;

    }
    
    // Observadores
    public Object getCodigo() {

        return this.codigo;

    }
    
    public Object getObjeto() {

    	return this.objeto;

    }

    public NodoDicc getEnlace() {

        return this.enlace;

    }

    // Modificadores
    public void setElemento(Object nuevoCodigo) {

        this.codigo = nuevoCodigo;

    }
    
    public void setObjecto(Object nuevoObjeto) {

    	this.objeto = nuevoObjeto;

    }

    public void setEnlace(NodoDicc nuevoEnlace) {

        this.enlace = nuevoEnlace;

    }

}