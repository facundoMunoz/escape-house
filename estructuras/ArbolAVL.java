package estructuras;

public class ArbolAVL {

	private NodoAVL raiz;

	public ArbolAVL() {
		this.raiz = null;
	}

	public boolean pertenece(Comparable buscado) {
		boolean encontrado = false;

		if (!this.vacio()) {
			encontrado = perteneceAux(this.raiz, buscado);
		}

		return encontrado;
	}

	private boolean perteneceAux(NodoAVL nodo, Comparable buscado) {
		boolean encontrado = false;

		if (nodo != null) {
			if (buscado.compareTo(nodo.getClave()) == 0) {
				encontrado = true;
			} else {
				if (buscado.compareTo(nodo.getClave()) < 0) {
					encontrado = perteneceAux(nodo.getIzquierdo(), buscado);
				} else {
					encontrado = perteneceAux(nodo.getDerecho(), buscado);
				}
			}
		}

		return encontrado;
	}

	public boolean insertar(Comparable nuevaClave, Object nuevoObjeto) {
		boolean exito;

		if (!this.vacio()) {
			exito = insertarAux(null, this.raiz, nuevaClave, nuevoObjeto);
		} else {
			this.raiz = new NodoAVL(nuevaClave, nuevoObjeto);
			exito = true;
		}

		return exito;
	}

	private boolean insertarAux(NodoAVL padre, NodoAVL nodo, Comparable nuevaClave, Object nuevoObjeto) {
		boolean exito = true;

		if ((nuevaClave.compareTo(nodo.getClave()) == 0)) {
			// Si existe no lo insertamos
			exito = false;
		} else if (nuevaClave.compareTo(nodo.getClave()) < 0) {
			if (nodo.getIzquierdo() != null) {
				exito = insertarAux(nodo, nodo.getIzquierdo(), nuevaClave, nuevoObjeto);
			} else {
				nodo.setIzquierdo(new NodoAVL(nuevaClave, nuevoObjeto));
			}
		} else {
			if (nodo.getDerecho() != null) {
				exito = insertarAux(nodo, nodo.getDerecho(), nuevaClave, nuevoObjeto);
			} else {
				nodo.setDerecho(new NodoAVL(nuevaClave, nuevoObjeto));
			}
		}

		if (exito) {
			balancear(padre, nodo);
		}

		return exito;
	}

	public boolean eliminar(Comparable buscado) {
		return eliminarAux(null, this.raiz, buscado);
	}

	private boolean eliminarAux(NodoAVL padre, NodoAVL hijo, Comparable buscado) {
		boolean eliminado = false;

		if (hijo != null) {
			if (buscado.compareTo(hijo.getClave()) == 0) {
				if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) {
					eliminarHoja(padre, buscado);
				} else {
					if (hijo.getIzquierdo() == null || hijo.getDerecho() == null) {
						eliminarUnicoHijo(padre, hijo, buscado);
					} else {
						eliminarDosHijos(hijo);
					}
				}
				eliminado = true;
			} else {
				if (buscado.compareTo(hijo.getClave()) < 0) {
					eliminado = eliminarAux(hijo, hijo.getIzquierdo(), buscado);
				} else {
					eliminado = eliminarAux(hijo, hijo.getDerecho(), buscado);
				}
			}

			if (eliminado) {
				balancear(padre, hijo);
			}
		}

		return eliminado;
	}

	private void eliminarHoja(NodoAVL padre, Comparable buscado) {

		if (padre != null) {
			if (padre.getDerecho() != null && buscado.compareTo(padre.getDerecho().getClave()) == 0) {
				padre.setDerecho(null);
			} else {
				padre.setIzquierdo(null);
			}
		} else {
			this.raiz = null;
		}

	}

	private void eliminarUnicoHijo(NodoAVL padre, NodoAVL hijo, Comparable buscado) {

		NodoAVL subArbol;
		if (hijo.getDerecho() != null) {
			subArbol = hijo.getDerecho();
		} else {
			subArbol = hijo.getIzquierdo();
		}

		if (padre != null) {
			if (padre.getDerecho() != null && buscado.compareTo(padre.getDerecho().getClave()) == 0) {
				padre.setDerecho(subArbol);
			} else {
				padre.setIzquierdo(subArbol);
			}
		} else {
			this.raiz = subArbol;
		}

	}

	private void eliminarDosHijos(NodoAVL hijo) {
		NodoAVL padreNuevoNodo = buscarPadreNodoMinimo(hijo.getDerecho());
		NodoAVL nuevoNodo = padreNuevoNodo.getIzquierdo();

		// Candidato con el menor hijo del subarbol derecho
		// Ponemos el elemento de dicho candidato
		if (nuevoNodo != null) {
			hijo.setClave(nuevoNodo.getClave());
			hijo.setObjeto(nuevoNodo.getObjeto());
			// Eliminamos al candidato
			eliminarAux(hijo, hijo.getDerecho(), nuevoNodo.getClave());
		} else {
			hijo.setClave(padreNuevoNodo.getClave());
			hijo.setObjeto(padreNuevoNodo.getObjeto());
			// Eliminamos al candidato
			eliminarAux(hijo, padreNuevoNodo, padreNuevoNodo.getClave());
		}

	}

	private NodoAVL buscarPadreNodoMinimo(NodoAVL nodo) {
		NodoAVL encontrado = nodo;

		if (nodo != null) {
			if (nodo.getIzquierdo() != null && nodo.getIzquierdo().getIzquierdo() != null) {
				encontrado = buscarPadreNodoMinimo(nodo.getIzquierdo());
			}
		}

		return encontrado;
	}

	private void balancear(NodoAVL padre, NodoAVL nodo) {
		NodoAVL retorno;

		nodo.recalcularAltura();
		if (this.balance(nodo) < -1) {
			// Está desbalanceado hacia la derecha
			if (this.balance(nodo.getDerecho()) < 0) {
				// Si ambos están desbalanceados a la derecha es rotación simple izquierda
				retorno = rotarIzquierda(nodo);
			} else {
				// Si no rota derecha-izquierda
				nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
				retorno = rotarIzquierda(nodo);
			}
			if (padre != null) {
				if (padre.getDerecho().equals(nodo)) {
					// Si era el derecho asignamos ese
					padre.setDerecho(retorno);
				} else {
					// Sino el izquierdo
					padre.setIzquierdo(retorno);
				}
			}
		} else if (this.balance(nodo) > 1) {
			// Está desbalanceado hacia la izquierda
			if (this.balance(nodo.getIzquierdo()) > 0) {
				// Si ambos están desbalanceados a la izquierda es rotación simple derecha
				retorno = rotarDerecha(nodo);
			} else {
				// Si no rota izquierda-derecha
				nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
				retorno = rotarDerecha(nodo);
			}
			if (padre != null) {
				if (padre.getDerecho().equals(nodo)) {
					// Si era el derecho asignamos ese
					padre.setDerecho(retorno);
				} else {
					// Sino el izquierdo
					padre.setIzquierdo(retorno);
				}
			}
		}
		nodo.recalcularAltura();
	}

	private int balance(NodoAVL nodo) {
		int alturaDerecho = (nodo.getDerecho() == null) ? -1 : nodo.getDerecho().getAltura();
		int alturaIzquierdo = (nodo.getIzquierdo() == null) ? -1 : nodo.getIzquierdo().getAltura();
		return alturaIzquierdo - alturaDerecho;
	}

	private NodoAVL rotarDerecha(NodoAVL nodo) {
		NodoAVL hijoIzq = nodo.getIzquierdo();
		NodoAVL temp = hijoIzq.getDerecho();
		hijoIzq.setDerecho(nodo);
		nodo.setIzquierdo(temp);
		// Recalculo sus alturas
		nodo.recalcularAltura();
		hijoIzq.recalcularAltura();

		if (nodo == this.raiz) {
			this.raiz = hijoIzq;
		}

		return hijoIzq;
	}

	private NodoAVL rotarIzquierda(NodoAVL nodo) {
		NodoAVL hijoDer = nodo.getDerecho();
		NodoAVL temp = hijoDer.getIzquierdo();
		hijoDer.setIzquierdo(nodo);
		nodo.setDerecho(temp);
		// Recalculo sus alturas
		nodo.recalcularAltura();
		hijoDer.recalcularAltura();

		if (nodo == this.raiz) {
			this.raiz = hijoDer;
		}

		return hijoDer;
	}

	public Lista listar() {
		Lista lista = new Lista();

		listarAux(this.raiz, lista);

		return lista;
	}

	private void listarAux(NodoAVL nodo, Lista lista) {
		if (nodo != null) {
			listarAux(nodo.getIzquierdo(), lista);

			lista.insertar(nodo.getObjeto(), lista.longitud() + 1);
			listarAux(nodo.getDerecho(), lista);
		}
	}

	public Lista listarRango(Comparable min, Comparable max) {
		Lista lista = new Lista();

		listarRangoAux(this.raiz, min, max, lista);

		return lista;
	}

	private void listarRangoAux(NodoAVL nodo, Comparable min, Comparable max, Lista lista) {
		if (nodo != null) {
			if (nodo.getClave().compareTo(min) > 0) {
				listarRangoAux(nodo.getIzquierdo(), min, max, lista);
			}

			if (nodo.getClave().compareTo(min) >= 0 && nodo.getClave().compareTo(max) <= 0) {
				lista.insertar(nodo.getObjeto(), lista.longitud() + 1);
			}
			if (nodo.getClave().compareTo(max) < 0) {
				listarRangoAux(nodo.getDerecho(), min, max, lista);
			}
		}
	}

	public Comparable minimoElem() {
		return minimoElemAux(this.raiz);
	}

	private Comparable minimoElemAux(NodoAVL nodo) {
		Comparable encontrado = null;

		if (nodo != null) {
			if (nodo.getIzquierdo() != null) {
				encontrado = minimoElemAux(nodo.getIzquierdo());
			} else {
				encontrado = nodo.getClave();
			}
		}

		return encontrado;
	}

	public Comparable maximoElem() {
		return maximoAux(this.raiz);
	}

	private Comparable maximoAux(NodoAVL nodo) {
		Comparable encontrado = null;

		if (nodo != null) {
			if (nodo.getDerecho() != null) {
				encontrado = maximoAux(nodo.getDerecho());
			} else {
				encontrado = nodo.getClave();
			}
		}

		return encontrado;
	}

	public int diferenciarCandidatos(Comparable buscado) {
		int resultado;
		// Buscamos el nodo
		NodoAVL nodo = this.buscarNodo(this.raiz, buscado);

		// Si tiene ambos hijos podemos operar
		if (nodo.getClave().compareTo(buscado) == 0) {
			if (nodo.getDerecho() != null && nodo.getIzquierdo() != null) {
				resultado = (int) this.minimoElemAux(nodo.getDerecho()) - (int) this.maximoAux(nodo.getIzquierdo());
			} else {
				resultado = -2;
			}
		} else {
			resultado = -1;
		}

		return resultado;
	}

	private NodoAVL buscarNodo(NodoAVL nodo, Comparable buscado) {
		if (nodo != null) {
			if (nodo.getClave().compareTo(buscado) != 0) {
				if (nodo.getClave().compareTo(buscado) < 0) {
					nodo = buscarNodo(nodo.getDerecho(), buscado);
				} else {
					nodo = buscarNodo(nodo.getIzquierdo(), buscado);
				}
			}
		}

		return nodo;
	}

	public Object getObjeto(Comparable clave) {
		// Dada la clave retorna el objeto correspondiente del nodo
		NodoAVL nodo = buscarNodo(this.raiz, clave);
		Object objeto = null;

		if (nodo != null) {
			objeto = nodo.getObjeto();
		}

		return objeto;
	}

	public boolean vacio() {
		return this.raiz == null;
	}

	@Override
	public String toString() {
		String cadena = "Arbol vacio";

		if (this.raiz != null) {
			cadena = stringAux(this.raiz, "");
		}
		return cadena;
	}

	private String stringAux(NodoAVL nodo, String cadena) {
		cadena += "Nodo: " + nodo.getObjeto().toString();

		if (nodo.getIzquierdo() != null) {
			cadena += " | HI: " + nodo.getIzquierdo().getObjeto().toString();
		} else {
			cadena += " | HI: -";
		}

		if (nodo.getDerecho() != null) {
			cadena += " | HD: " + nodo.getDerecho().getObjeto().toString() + "";
		} else {
			cadena += " | HD: -";
		}

		cadena += " | Altura : " + nodo.getAltura() + "\n";

		if (nodo.getIzquierdo() != null) {
			cadena = stringAux(nodo.getIzquierdo(), cadena);
		}

		if (nodo.getDerecho() != null) {
			cadena = stringAux(nodo.getDerecho(), cadena);
		}

		return cadena;
	}

}
