package estructuras;

public class Lista {

    private Nodo cabecera;

    public Lista() {

        this.cabecera = null;

    }

    public boolean insertar(Object elemento, int posicion) {
        boolean exito = false;

        if (1 <= posicion && posicion <= this.longitud() + 1) {
            if (posicion == 1) {
                this.cabecera = new Nodo(elemento, this.cabecera);
            } else {
                Nodo auxiliar = this.cabecera;
                int iteracion = 1;
                while (iteracion < posicion - 1) {
                    auxiliar = auxiliar.getEnlace();
                    iteracion++;
                }
                Nodo nuevoNodo = new Nodo(elemento, auxiliar.getEnlace());
                auxiliar.setEnlace(nuevoNodo);
            }
            exito = true;
        }

        return exito;
    }

    public boolean eliminar(int posicion) {
        boolean exito = false;

        if (1 <= posicion && posicion <= this.longitud()) {
            Nodo auxiliar = this.cabecera;
            int iteracion = 1;
            if (posicion == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                while (iteracion < posicion - 1) {
                    auxiliar = auxiliar.getEnlace();
                    iteracion++;
                }
                auxiliar.setEnlace(auxiliar.getEnlace().getEnlace());
            }
            exito = true;
        }

        return exito;
    }

    public Object recuperar(int posicion) {
        Object elemento = null;
        int iteracion = 1;
        Nodo auxiliar = this.cabecera;

        if (1 <= posicion && posicion < this.longitud() + 1) {
            while (iteracion < posicion) {
                iteracion++;
                auxiliar = auxiliar.getEnlace();
            }
            elemento = auxiliar.getElemento();
        }

        return elemento;
    }

    public int localizar(Object elemento) {
        int largo = this.longitud();
        int iteracion = 1, posicion = -1;
        boolean encontrado = false;
        Nodo auxiliar = this.cabecera;

        while (!encontrado && iteracion <= largo) {
            if (auxiliar.getElemento().equals(elemento)) {
                posicion = iteracion;
                encontrado = true;
            } else {
                auxiliar = auxiliar.getEnlace();
                iteracion++;
            }
        }

        return posicion;
    }

    public int longitud() {
        int longitud = 0;
        Nodo nodoAuxiliar = this.cabecera;

        while (nodoAuxiliar != null) {
            longitud++;
            nodoAuxiliar = nodoAuxiliar.getEnlace();
        }

        return longitud;
    }

    public boolean esVacia() {

        return this.cabecera == null;

    }

    public void vaciar() {

        this.cabecera = null;

    }

    @Override
    public Lista clone() {
        Lista clon = new Lista();
        Nodo nodoAdelante = this.cabecera;
        clon.cabecera = new Nodo(nodoAdelante.getElemento(), null);
        nodoAdelante = nodoAdelante.getEnlace();
        Nodo nodoAtras = clon.cabecera;

        while (nodoAdelante != null) {
            nodoAtras.setEnlace(new Nodo(nodoAdelante.getElemento(), null));
            nodoAtras = nodoAtras.getEnlace();
            nodoAdelante = nodoAdelante.getEnlace();
        }

        return clon;
    }

    @Override
    public String toString() {
        String texto;

        if (this.esVacia()) {
            texto = "Lista vacia";
        } else {
            Nodo nodoAuxiliar = this.cabecera;
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
