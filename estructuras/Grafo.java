package estructuras;

import objetos.Habitacion;

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

	private NodoVert ubicarVertice(Object buscado) {
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
				System.out.println("ELIMINADO");
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

	private boolean existeCaminoAux(NodoVert actual, Object dest, Lista visitados) {
		boolean exito = false;

		if (actual != null) {
			// Camino encontrado
			if (actual.getElem().equals(dest)) {
				exito = true;
			} else {
				// Si no es el destino verifica entre n y el destino
				// Marcamos n como visitado
				visitados.insertar(actual.getElem(), visitados.longitud() + 1);
				NodoAdy ady = actual.getPrimerAdy();
				while (!exito && ady != null) {
					// Verifica que no esté visitado
					if (visitados.localizar(ady.getVertice().getElem()) < 0) {
						exito = existeCaminoAux(ady.getVertice(), dest, visitados);
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
		TablaHash visitados = new TablaHash();
		NodoVert nodo = this.inicio;

		while (nodo != null) {
			if (visitados.getObjeto(nodo.getElem().toString()) == null) {
				this.listarAnchuraAux(nodo, listados, visitados);
			}
			nodo = nodo.getSigVertice();
		}

		return listados;
	}

	private void listarAnchuraAux(NodoVert nodo, Lista listados, TablaHash visitados) {
		Cola colaAux = new Cola();
		visitados.insertar(nodo.getElem().toString(), inicio);
		colaAux.poner(nodo);

		while (!colaAux.esVacia()) {
			NodoVert nodoAux = (NodoVert) colaAux.obtenerFrente();
			listados.insertar(nodoAux.getElem(), listados.longitud() + 1);
			colaAux.sacar();
			NodoAdy adyacente = nodoAux.getPrimerAdy();

			while (adyacente != null) {
				if (visitados.getObjeto(adyacente.getVertice().getElem().toString()) == null) {
					colaAux.poner(adyacente.getVertice());
					visitados.insertar(adyacente.getVertice().getElem().toString(), adyacente.getVertice());
				}
				adyacente = adyacente.getSigAdyacente();
			}
		}
	}

	public Lista caminoMasCorto(Object origen, Object destino) {
		Lista camino = new Lista();
		TablaHash visitados = new TablaHash();
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

	private Lista caminoMasCortoAux(NodoVert origen, NodoVert destino, TablaHash visitados) {
		Lista camino = new Lista();
		// El primer recorrido es el origen
		visitados.insertar(origen.getElem().toString(), origen);
		if (origen.equals(destino)) {
			camino.insertar(origen.getElem(), 1);
		} else {
			NodoAdy adyacente = origen.getPrimerAdy();
			Lista caminoPosible = new Lista();

			while (adyacente != null) {
				if (visitados.getObjeto(adyacente.getVertice().getElem().toString()) == null
						&& camino.longitud() == 0) {
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

	public Lista listarAdyacentesYEtiquetas(Object buscado) {
		// Método que genera lista de los adyacentes y las etiquetas que lo conectan al
		// buscado
		// En orden 'objeto -> etiqueta -> objeto -> etiqueta -> ...'
		NodoVert nodo = ubicarVertice(buscado);
		Lista adyacentes = new Lista();

		if (nodo != null) {
			NodoAdy adyacente = nodo.getPrimerAdy();
			int posicionLista = 1;

			do {
				// Agregamos el elemento y luego la etiqueta del arco
				adyacentes.insertar(adyacente.getVertice().getElem(), posicionLista);
				posicionLista++;
				adyacentes.insertar(adyacente.getEtiqueta(), posicionLista);
				posicionLista++;
			} while ((adyacente = adyacente.getSigAdyacente()) != null);
		}

		return adyacentes;
	}

	public int etiquetaArco(Object vertice, Object adyacente) {
		// Recorrido con fuerza bruta de los adyacentes de un vértice
		// Vértice inicial
		NodoVert nodoVertice = this.ubicarVertice(vertice);
		NodoAdy nodoAdyacente = null;
		// Retorna -1 si no lo encuentra
		int etiqueta = -1;

		if (nodoVertice != null) {
			// Mientras encontremos adyacentes revisamos si es el buscado
			nodoAdyacente = nodoVertice.getPrimerAdy();
			while (nodoAdyacente != null && !adyacente.equals(nodoAdyacente.getVertice().getElem())) {
				nodoAdyacente = nodoAdyacente.getSigAdyacente();
			}
		}

		if (nodoAdyacente != null) {
			etiqueta = nodoAdyacente.getEtiqueta();
		}

		return etiqueta;
	}

	public boolean existeCaminoPuntajeMaximo(Object origen, Object destino, int puntos) {
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
			exito = existeCaminoPuntajeMaximoAux(auxO, destino, 0, puntos, visitados);
		}

		return exito;
	}

	private boolean existeCaminoPuntajeMaximoAux(NodoVert actual, Object dest, int puntosActuales, int puntosMaximos,
			Lista visitados) {
		// Precondición: puntosActuales comienza en 0
		boolean exito = false;

		if (actual != null) {
			// Camino encontrado
			if (actual.getElem().equals(dest)) {
				exito = true;
			} else {
				// Si no es el destino verifica entre n y el destino
				// Marcamos n como visitado
				visitados.insertar(actual.getElem(), visitados.longitud() + 1);
				NodoAdy ady = actual.getPrimerAdy();
				while (!exito && ady != null) {
					// Sumamos el puntaje del arco para limitar
					puntosActuales += ady.getEtiqueta();
					// Verifica que no esté visitado
					if (visitados.localizar(ady.getVertice().getElem()) < 0 && puntosActuales <= puntosMaximos) {
						exito = existeCaminoPuntajeMaximoAux(ady.getVertice(), dest, puntosActuales, puntosMaximos,
								visitados);
					}
					ady = ady.getSigAdyacente();
				}
			}
		}

		return exito;
	}

	public Lista minimoPuntajeParaPasar(Object origen, Object destino) {
		// Retorna el camino y mínimo puntaje para ir de origen a destino en una lista
		// O null si no lo encuentra
		Lista caminoMenosPuntos = new Lista();
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
			minimoPuntajeParaPasarAux(auxO, destino, 0, visitados, caminoMenosPuntos);
		}

		return caminoMenosPuntos;
	}

	private void minimoPuntajeParaPasarAux(NodoVert actual, Object dest, int puntosActuales, Lista visitados,
			Lista caminoMenosPuntos) {
		// Precondición: puntosActuales comienza en 0
		if (actual != null) {
			// Marcamos n como visitado
			visitados.insertar(actual.getElem(), visitados.longitud() + 1);

			// Camino encontrado
			if (actual.getElem().equals(dest)) {
				// Agregamos el puntaje a la lista
				visitados.insertar(puntosActuales, visitados.longitud() + 1);
				// Si la lista del posible camino es vacía o requiere más puntaje se reemplaza
				// por la actual
				if (caminoMenosPuntos.esVacia()
						|| (int) visitados.recuperar(visitados.longitud()) < (int) caminoMenosPuntos
								.recuperar(caminoMenosPuntos.longitud())) {
					caminoMenosPuntos = visitados;
				}
			} else {
				// Si no es el destino verifica entre actual y el destino
				NodoAdy ady = actual.getPrimerAdy();
				while (ady != null) {
					// Sumamos el puntaje del arco para luego comparar
					puntosActuales += ady.getEtiqueta();
					// Verifica que no esté visitado
					if (visitados.localizar(ady.getVertice().getElem()) < 0) {
						minimoPuntajeParaPasarAux(ady.getVertice(), dest, puntosActuales, visitados, caminoMenosPuntos);
					}
					ady = ady.getSigAdyacente();
				}
			}
		}
	}

	public Lista puntajeMaxSinPasarPor(Object origen, Object destino, Object evitada, int puntos) {
		// Verifica si ambos vertices existen
		NodoVert auxO = null;
		NodoVert auxD = null;
		NodoVert aux = this.inicio;
		Lista caminosPosibles = new Lista();

		while ((auxO == null || auxD == null) && aux != null) {
			if (aux.getElem().equals(origen)) {
				auxO = aux;
			}
			if (aux.getElem().equals(destino)) {
				auxD = aux;
			}
			aux = aux.getSigVertice();
		}

		// Si la evitada es el origen o el destino no tiene sentido recorrer
		if (auxO != null && auxD != null && !auxO.equals(evitada) && !auxD.equals(evitada)) {
			// Si existen y no son iguales a la evitada buscamos un camino entre ambos
			Lista visitados = new Lista();

			puntajeMaxSinPasarPorAux(auxO, destino, evitada, 0, puntos, visitados, caminosPosibles);
		}

		return caminosPosibles;
	}

	private void puntajeMaxSinPasarPorAux(NodoVert actual, Object dest, Object evitada, int puntosActuales,
			int puntosMaximos, Lista visitados, Lista caminos) {
		// Precondición: puntosActuales comienza en 0

		if (actual != null) {
			// Camino posible encontrado
			if (actual.getElem().equals(dest)) {
				caminos.insertar(visitados, caminos.longitud() + 1);
			} else {
				// Si no es el destino verifica entre n y el destino
				// Marcamos n como visitado
				visitados.insertar(actual.getElem(), visitados.longitud() + 1);
				NodoAdy ady = actual.getPrimerAdy();
				while (ady != null) {
					// Sumamos el puntaje del arco para limitar
					puntosActuales += ady.getEtiqueta();
					// Verifica que no esté visitado
					if (visitados.localizar(ady.getVertice().getElem()) < 0 && puntosActuales <= puntosMaximos
							&& !ady.getVertice().getElem().equals(evitada)) {
						// Sigue buscando si no fue visitado, si no supera la puntuación máxima y si no
						// es el evitado
						puntajeMaxSinPasarPorAux(ady.getVertice(), dest, evitada, puntosActuales, puntosMaximos,
								visitados, caminos);
					}
					ady = ady.getSigAdyacente();
				}
			}
		}
	}

	@Override
	public String toString() {
		NodoVert vertice = this.inicio;
		String texto = "";

		while (vertice != null) {
			Habitacion habitacion = (Habitacion) vertice.getElem();
			NodoAdy adyacente = vertice.getPrimerAdy();
			// Agregamos el vertice actual
			texto += " _ " + habitacion.getCodigo() + " - " + habitacion.getNombre();

			// Si tiene adyacentes los listamos
			while (adyacente != null) {
				Habitacion habitacionAdy = (Habitacion) adyacente.getVertice().getElem();

				// Formato ' -puntajeParaPasar-> codigoHabitacion - nombreHabitacion'
				texto += " -> " + habitacionAdy.getNombre() + " (" + adyacente.getEtiqueta() + "p)";

				adyacente = adyacente.getSigAdyacente();
			}

			// Movemos el vértice y saltamos de línea
			vertice = vertice.getSigVertice();
			texto += "\n";
		}

		if (texto.equals("")) {
			// Si no se modifica el texto no hay habitaciones
			texto = "¡No hay habitaciones cargadas!";
		}

		return texto;
	}

}
