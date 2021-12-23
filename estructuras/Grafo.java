package estructuras;

public class Grafo {

    private NodoVert inicio;

    public Grafo() {

    }

    public boolean insertarVertice(Object nuevoVertice) {
        // Solo lo inserta si no existe previamente 
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);

        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio);
            exito = true;
        }

        return exito;
    }

    public NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;

        // Recorre hasta encontrarlo o hasta terminar la lista
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }

        return aux;
    }

    public boolean eliminarVertice(Object origen, Object destino) {
        boolean exito = false;
        // Buscamos el origen
        NodoVert aux = this.ubicarVertice(origen);
        NodoAdy auxAdy;

        // Si existe buscamos el arco que lo enlaza al destino
        if (aux != null) {
            auxAdy = aux.getPrimerAdy();
            while (auxAdy != null && auxAdy.getVertice().getElem().equals(destino)) {

            }
        }

        return exito;
    }

    public boolean existeVertice(Object buscado) {
        NodoVert aux = this.inicio;

        // Recorre hasta encontrarlo o hasta terminar la lista
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }

        return (aux != null);
    }

    public boolean insertarArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        // Verifica si ambos vertices existen
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            // Si existen insertamos un arco en cada uno
            auxO.setPrimerAdy(new NodoAdy(auxD));
            auxD.setPrimerAdy(new NodoAdy(auxO));
            exito = true;
        }

        return exito;
    }

    public boolean eliminarArco() {

    }

    public boolean existeArco() {

    }

    public boolean vacio() {
        return inicio == null;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        // Verifica si ambos vertices existen
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;

        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        }

        if (auxO != null && auxD != null) {
            // Si existen buscamos un camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }

        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;

        if (n != null) {
            // Camino encontrado
            if (n.getElem().equals(dest)) {
                exito = true;
            } else {
                // Si no es el destino verifica entre n y el destino
                // Marcamos n como visitado
                vis.insertar(n.getElem(), vis.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    // Verifica que no esté visitado
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }

        return exito;
    }

    public Lista listarEnProfundidad() {

    }

    public Lista listarEnAnchura() {

    }

    public Lista caminoMasCorto() {

    }

}
