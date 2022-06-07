package estructuras;

public class Grafo {
	// Grafo etiquetado con int
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
			nodo.setPrimerAdy(null);

			if (nodo == this.inicio) {
				// Si el nodo es el inicio reemplazamos
				this.inicio = nodo.getSigVertice();
			} else {
				// Si no buscamos el anterior para reemplazar sabiendo que pertenece a la lista
				NodoVert anterior = this.inicio;
				while (!buscado.equals(anterior.getSigVertice().getElem())) {
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
			// Si existen y no estaban enlazados insertamos un arco en cada uno
			insertarArcoAux(auxO, new NodoAdy(auxD, etiqueta));
			insertarArcoAux(auxD, new NodoAdy(auxO, etiqueta));
			exito = true;
		}

		return exito;
	}

	private void insertarArcoAux(NodoVert vertice, NodoAdy nuevo) {
		NodoAdy adyacente = vertice.getPrimerAdy();

		if (adyacente != null) {
			// Si tiene más adyacentes verificamos que aún no exista
			while (adyacente.getSigAdyacente() != null) {
				adyacente = adyacente.getSigAdyacente();
			}
			adyacente.setSigAdyacente(nuevo);
		} else {
			vertice.setPrimerAdy(nuevo);
		}
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
						// encontrado es el vértice del arco (para borrar también de ese lado)
						encontrado = adyacente.getSigAdyacente().getVertice();
						// Quitamos el adyacente reemplazándolo con el siguiente
						adyacente.setSigAdyacente(adyacente.getSigAdyacente().getSigAdyacente());
					}
					adyacente = adyacente.getSigAdyacente();
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
		// En orden 'objeto1 -> etiqueta1 -> objeto2 -> etiqueta2 -> ...'
		NodoVert nodo = ubicarVertice(buscado);
		Lista adyacentes = new Lista();

		if (nodo != null) {
			NodoAdy adyacente = nodo.getPrimerAdy();
			int posicionLista = 1;

			while (adyacente != null) {
				// Agregamos el elemento y luego la etiqueta del arco
				adyacentes.insertar(adyacente.getVertice().getElem(), posicionLista);
				posicionLista++;
				adyacentes.insertar(adyacente.getEtiqueta(), posicionLista);
				posicionLista++;
				adyacente = adyacente.getSigAdyacente();
			}
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

	public boolean existeCaminoPesoMaximo(Object origen, Object destino, int peso) {
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
			exito = existeCaminoPesoMaximoAux(auxO, destino, 0, peso, visitados);
		}

		return exito;
	}

	private boolean existeCaminoPesoMaximoAux(NodoVert actual, Object dest, int pesoActual, int pesoMaximo,
			Lista visitados) {
		// Precondición: pesoActual comienza en 0
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
					// Sumamos el peso del arco para limitar
					pesoActual += ady.getEtiqueta();
					// Verifica que no esté visitado
					if (visitados.localizar(ady.getVertice().getElem()) < 0 && pesoActual <= pesoMaximo) {

						exito = existeCaminoPesoMaximoAux(ady.getVertice(), dest, pesoActual, pesoMaximo, visitados);

						visitados.eliminar(visitados.longitud());
					}
					pesoActual -= ady.getEtiqueta();
					ady = ady.getSigAdyacente();
				}
			}
		}

		return exito;
	}

	public Lista minimoPesoParaPasar(Object origen, Object destino) {
		// Retorna el camino y mínimo peso para ir de origen a destino en una lista
		// O null si no lo encuentra
		Lista caminoMenosPeso = new Lista();
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
			Lista caminoActual = new Lista();
			caminoMenosPeso = minimoPesoParaPasarAux(auxO, auxD, 0, caminoActual, caminoMenosPeso);
		}

		return caminoMenosPeso;
	}

	private Lista minimoPesoParaPasarAux(NodoVert actual, NodoVert dest, int pesoActual, Lista caminoActual,
			Lista caminoMenosPeso) {
		// Precondición: pesoActual comienza en 0
		if (actual != null) {
			caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

			// Camino encontrado
			if (actual.equals(dest)) {
				// Agregamos el peso a la lista
				caminoActual.insertar(pesoActual, caminoActual.longitud() + 1);
				// Si la lista del posible camino es vacía o requiere más peso se reemplaza
				// por la actual
				if (caminoMenosPeso.esVacia()
						|| (int) caminoActual.recuperar(caminoActual.longitud()) < (int) caminoMenosPeso
								.recuperar(caminoMenosPeso.longitud())) {
					System.out.println("Cambia camino");
					caminoMenosPeso = caminoActual.clone();
				}
				// Quitamos el peso del camino
				caminoActual.eliminar(caminoActual.longitud());
			} else {
				// Si no es el destino verifica entre actual y el destino
				NodoAdy ady = actual.getPrimerAdy();
				while (ady != null) {
					// Verifica que no esté visitado
					if (caminoActual.localizar(ady.getVertice().getElem()) < 0) {
						// Sumamos el peso del arco para luego comparar
						pesoActual += ady.getEtiqueta();

						caminoMenosPeso = minimoPesoParaPasarAux(ady.getVertice(), dest, pesoActual, caminoActual,
								caminoMenosPeso);

						// Quitamos el adyacente del recorrido del camino actual
						caminoActual.eliminar(caminoActual.longitud());
						pesoActual -= ady.getEtiqueta();
					}
					ady = ady.getSigAdyacente();
				}
			}
		}

		return caminoMenosPeso;
	}

	public Lista pesoMaxSinPasarPor(Object origen, Object destino, Object evitada, int peso) {
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
			Lista caminoActual = new Lista();

			caminosPosibles = pesoMaxSinPasarPorAux(auxO, auxD, evitada, 0, peso, caminoActual, caminosPosibles);
		}

		return caminosPosibles;
	}

	private Lista pesoMaxSinPasarPorAux(NodoVert actual, NodoVert dest, Object evitada, int pesoActual, int pesoMaximo,
			Lista caminoActual, Lista caminosPosibles) {
		// Precondición: pesoActual comienza en 0
		if (actual != null) {
			caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

			// Camino posible encontrado
			if (actual.equals(dest)) {
				caminosPosibles.insertar(caminoActual.clone(), caminosPosibles.longitud() + 1);
			} else {
				// Si no es el destino verifica entre n y el destino
				NodoAdy ady = actual.getPrimerAdy();
				while (ady != null) {
					// Sumamos el peso del arco para limitar
					pesoActual += ady.getEtiqueta();
					// Verifica que no esté visitado
					if (caminoActual.localizar(ady.getVertice().getElem()) < 0 && pesoActual <= pesoMaximo
							&& !ady.getVertice().getElem().equals(evitada)) {

						// Sigue buscando si no fue visitado, si no supera la puntuación máxima
						// Y si no es el evitado
						caminosPosibles = pesoMaxSinPasarPorAux(ady.getVertice(), dest, evitada, pesoActual, pesoMaximo,
								caminoActual, caminosPosibles);

						// Quitamos el adyacente del recorrido del camino actual
						caminoActual.eliminar(caminoActual.longitud());
					}
					pesoActual -= ady.getEtiqueta();
					ady = ady.getSigAdyacente();
				}
			}
		}
		return caminosPosibles;
	}

	@Override
	public String toString() {
		NodoVert vertice = this.inicio;
		String texto = "";

		while (vertice != null) {
			NodoAdy adyacente = vertice.getPrimerAdy();
			// Agregamos el vertice actual
			texto += " _ " + vertice.getElem().toString();

			// Si tiene adyacentes los listamos
			while (adyacente != null) {
				// Formato ' -pesoParaPasar-> elemento'
				texto += " -> " + adyacente.getVertice().getElem().toString() + " (" + adyacente.getEtiqueta() + "p)";

				adyacente = adyacente.getSigAdyacente();
			}

			// Movemos el vértice y saltamos de línea
			vertice = vertice.getSigVertice();
			texto += "\n";
		}

		if (texto.equals("")) {
			// Si no se modifica el texto no hay elementos
			texto = "¡No hay elementos cargados!";
		}

		return texto;
	}

}
