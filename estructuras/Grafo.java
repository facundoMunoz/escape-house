package estructuras;

import java.util.HashMap;

public class Grafo {
	// Grafo etiquetado implementado para funcionar como mapa de la casona
	private NodoVert inicio;

	public Grafo() {
		this.inicio = null;
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

	public boolean eliminarVertice(Object buscado) {
		boolean exito = false;
		// Buscamos el origen
		NodoVert nodo = this.ubicarVertice(buscado);

		if (nodo != null) {
			NodoAdy nodoAdyacente = nodo.getPrimerAdy();
			while (nodoAdyacente != null) {
				// Quitamos todos los arcos que apuntan al nodo antes de eliminar el vértice
				this.eliminarArcoAux(nodoAdyacente.getVertice(), buscado);
				nodoAdyacente = nodoAdyacente.getSigAdyacente();
			}

			if (nodo == this.inicio) {
				// Si el nodo es el inicio reemplazamos
				this.inicio = this.inicio.getSigVertice();
			} else {
				// Si no buscamos el anterior para reemplazar sabiendo que pertenece a la lista
				NodoVert anterior = this.inicio;
				while (!buscado.equals(this.inicio.getSigVertice().getElem())) {
					anterior = anterior.getSigVertice();
				}
				// Anidamos el anterior con el siguiente del nodo
				anterior.setSigVertice(nodo.getSigVertice());
			}

			exito = true;
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

	public boolean insertarArco(Object origen, Object destino, int etiqueta) {
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
			auxO.setPrimerAdy(new NodoAdy(auxD, etiqueta));
			auxD.setPrimerAdy(new NodoAdy(auxO, etiqueta));
			exito = true;
		}

		return exito;
	}

	public boolean eliminarArco(Object origen, Object destino) {
		boolean exito = false;
		// Ubicamos el primer vértice
		NodoVert nodoOrigen = this.ubicarVertice(origen);

		if (nodoOrigen != null) {
			NodoVert encontrado = eliminarArcoAux(nodoOrigen, destino);

			if (encontrado != null) {
				// Si se eliminó el arco hacemos lo mismo con el vértice del otro lado
				eliminarArcoAux(encontrado, origen);
				// Luego se tuvo éxito
				exito = true;
			}
		}

		return exito;
	}

	private NodoVert eliminarArcoAux(NodoVert nodoOrigen, Object destino) {
		// Método que elimina un arco dado el vértice y el adyacente, devuelve la
		// referencia del vértice del arco eliminado
		NodoVert encontrado = null;
		// Ubicamos el primer adyacente del vértice
		NodoAdy adyacente = nodoOrigen.getPrimerAdy();

		if (adyacente != null) {
			if (destino.equals(adyacente.getVertice().getElem())) {
				// Si el primer adyacente es el que buscamos ponemos el primerAdy del origen
				// como el siguiente
				nodoOrigen.setPrimerAdy(adyacente.getSigAdyacente());
			} else {
				// Si no buscamos en sus adyacentes
				while (encontrado == null && adyacente.getSigAdyacente() != null) {
					if (destino.equals(adyacente.getSigAdyacente().getVertice().getElem())) {
						// encontrado es el vértice del arco (por si se quiere borrar también de ese
						// lado)
						encontrado = adyacente.getSigAdyacente().getVertice();
						// Quitamos el adyacente reemplazándolo con el siguiente
						adyacente.setSigAdyacente(adyacente.getSigAdyacente());
					}
				}
			}
		}

		return encontrado;
	}

	public boolean existeArco(Object vertice, Object adyacente) {
		// Recorrido con fuerza bruta de los adyacentes de un vértice
		// Vértice inicial
		NodoVert nodoVertice = this.ubicarVertice(vertice);
		NodoAdy nodoAdyacente = null;

		if (nodoVertice != null) {
			// Mientras encontremos adyacentes revisamos si es el buscado
			nodoAdyacente = nodoVertice.getPrimerAdy();
			while (nodoAdyacente != null && !adyacente.equals(nodoAdyacente.getVertice().getElem())) {
				nodoAdyacente = nodoAdyacente.getSigAdyacente();
			}
		}

		// El arco existe si encontramos el adyacente para el vértice
		return (nodoAdyacente != null);
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
		Lista visitados = new Lista();
		NodoVert vertice = this.inicio;

		while (vertice != null) {
			if (visitados.localizar(vertice.getElem()) < 0) {
				// Si el vértice no fue visitado, avanza en profundidad
				listarEnProfundidadAux(vertice, visitados);
			}
			vertice = vertice.getSigVertice();
		}

		return visitados;
	}

	private void listarEnProfundidadAux(NodoVert vertice, Lista visitados) {
		if (vertice != null) {
			// Marcamos el vértice como visitado
			visitados.insertar(vertice.getElem(), visitados.longitud() + 1);
			NodoAdy adyacente = vertice.getPrimerAdy();

			while (adyacente != null) {
				// Visita en profundidad los adyacentes de n aún no visitados
				if (visitados.localizar(adyacente.getVertice().getElem()) < 0) {
					listarEnProfundidadAux(adyacente.getVertice(), visitados);
				}
				adyacente = adyacente.getSigAdyacente();
			}
		}
	}

	public Lista listarAnchura() {
		Lista listados = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		NodoVert nodo = this.inicio;

		while (nodo != null) {
			if (visitados.get(nodo.getElem().toString()) == null) {
				this.listarAnchuraAux(nodo, listados, visitados);
			}
			nodo = nodo.getSigVertice();
		}

		return listados;
	}

	private void listarAnchuraAux(NodoVert nodo, Lista listados, HashMap<String, Object> visitados) {
		Cola colaAux = new Cola();
		visitados.put(nodo.getElem().toString(), inicio);
		colaAux.poner(nodo);

		while (!colaAux.esVacia()) {
			NodoVert nodoAux = (NodoVert) colaAux.obtenerFrente();
			listados.insertar(nodoAux.getElem(), listados.longitud() + 1);
			colaAux.sacar();
			NodoAdy adyacente = nodoAux.getPrimerAdy();

			while (adyacente != null) {
				if (visitados.get(adyacente.getVertice().getElem().toString()) == null) {
					colaAux.poner(adyacente.getVertice());
					visitados.put(adyacente.getVertice().getElem().toString(), adyacente.getVertice());
				}
				adyacente = adyacente.getSigAdyacente();
			}
		}
	}

	public Lista caminoMasCorto(Object origen, Object destino) {
		Lista camino = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
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
			// Si existen buscamos el camino mas corto entre ambos
			camino = this.caminoMasCortoAux(auxO, auxD, visitados);
		}

		return camino;
	}

	private Lista caminoMasCortoAux(NodoVert origen, NodoVert destino, HashMap<String, Object> visitados) {
		Lista camino = new Lista();
		// El primer recorrido es el origen
		visitados.put(origen.getElem().toString(), origen);
		if (origen.equals(destino)) {
			camino.insertar(origen.getElem(), 1);
		} else {
			NodoAdy adyacente = origen.getPrimerAdy();
			Lista caminoPosible = new Lista();

			while (adyacente != null) {
				if (visitados.get(adyacente.getVertice().getElem().toString()) == null && camino.longitud() == 0) {
					if (camino.longitud() == 0) {
						camino = this.caminoMasCortoAux(adyacente.getVertice(), destino, visitados);
					} else {
						caminoPosible = this.caminoMasCortoAux(adyacente.getVertice(), destino, visitados);
						if (caminoPosible.longitud() <= camino.longitud()) {
							camino = caminoPosible;
						}
					}

					adyacente = adyacente.getSigAdyacente();
				}
			}
		}

		return camino;
	}

	public Lista listarAdyacentes(Object buscado) {
		NodoVert nodo = ubicarVertice(buscado);
		Lista adyacentes = new Lista();

		if (nodo != null) {
			NodoAdy adyacente = nodo.getPrimerAdy();
			do {
				adyacentes.insertar(adyacente.getVertice().getElem(), adyacentes.longitud() + 1);
			} while ((adyacente = adyacente.getSigAdyacente()) != null);
		}

		return adyacentes;
	}
	
	public Object etiquetaArco(Object vertice, Object adyacente) {
		// Recorrido con fuerza bruta de los adyacentes de un vértice
		// Vértice inicial
		NodoVert nodoVertice = this.ubicarVertice(vertice);
		NodoAdy nodoAdyacente = null;
		Object etiqueta = null;

		if (nodoVertice != null) {
			// Mientras encontremos adyacentes revisamos si es el buscado
			nodoAdyacente = nodoVertice.getPrimerAdy();
			while (nodoAdyacente != null && !adyacente.equals(nodoAdyacente.getVertice().getElem())) {
				nodoAdyacente = nodoAdyacente.getSigAdyacente();
			}
		}

		if(nodoAdyacente != null) {
			etiqueta = nodoAdyacente.getEtiqueta();
		}
		
		return etiqueta;
	}

}
