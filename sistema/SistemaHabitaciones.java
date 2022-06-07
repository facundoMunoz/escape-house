package sistema;

import estructuras.*;
import objetos.*;

public class SistemaHabitaciones {

	private static String opcionesMenu() {
		return "1. Mostrar habitación\n" + "2. Habitaciones contiguas\n" + "3. Es posible llegar\n"
				+ "4. Minimo puntaje\n" + "5. Sin pasar por\n" + "0. Volver";
	}

	public static void menuHabitaciones(ArbolAVL habitaciones, Grafo mapa) {
		int opcionMenu;

		do {
			System.out.println("Ingrese una opción: ");
			System.out.println(opcionesMenu());
			opcionMenu = SistemaJuego.pedirCodigo();

			switch (opcionMenu) {
			case 1:
				System.out.println(mostrarHabitacion(habitaciones));
				break;
			case 2:
				System.out.println(habitacionesContiguas(habitaciones, mapa));
				break;
			case 3:
				System.out.println(esPosibleLlegar(habitaciones, mapa));
				break;
			case 4:
				System.out.println(minimoPuntaje(habitaciones, mapa));
				break;
			case 5:
				System.out.println(sinPasarPor(habitaciones, mapa));
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionMenu != 0);

	}

	private static String mostrarHabitacion(ArbolAVL habitaciones) {
		// Cargar habitación
		System.out.println("Ingrese el código de la habitación:");
		int codigo = SistemaJuego.pedirCodigo();
		String habitacion = "El código no corresponde a una habitación cargada";

		if (habitaciones.pertenece(codigo)) {
			habitacion = ((Habitacion) habitaciones.getObjeto(codigo)).fullString();
		}

		return habitacion;
	}

	private static String habitacionesContiguas(ArbolAVL habitaciones, Grafo mapa) {
		// Cargar habitación
		System.out.println("Ingrese el código de la habitación:");
		int codigo = SistemaJuego.pedirCodigo();
		Habitacion habitacion = (Habitacion) habitaciones.getObjeto(codigo);
		Lista adyacentesYEtiquetas = mapa.listarAdyacentesYEtiquetas(habitacion);
		String contiguas = "El código no corresponde a una habitación cargada";

		if (!adyacentesYEtiquetas.esVacia()) {
			contiguas = "Habitaciones contiguas a " + codigo + " - " + habitacion.getNombre() + "\n";
			int posicion = 1;
			int longitudLista = adyacentesYEtiquetas.longitud();

			Habitacion habitacionActual;

			while (posicion <= longitudLista) {
				// Cargamos la habitación
				habitacionActual = (Habitacion) adyacentesYEtiquetas.recuperar(posicion);
				contiguas += habitacionActual.getCodigo() + " - " + habitacionActual.getNombre() + " | ";
				// Cargamos el puntaje necesario
				posicion++;
				contiguas += adyacentesYEtiquetas.recuperar(posicion) + " puntos\n";
				posicion++;
			}
		} else if (habitacion != null) {
			contiguas = "No hay habitaciones contiguas";
		}

		return contiguas;
	}

	private static String esPosibleLlegar(ArbolAVL habitaciones, Grafo mapa) {
		// Cargar habitación origen
		System.out.println("Ingrese la habitación origen:");
		int codigoOrigen = SistemaJuego.pedirCodigo();
		Habitacion habitacionOrigen = (Habitacion) habitaciones.getObjeto(codigoOrigen);
		// Cargar habitación destino
		System.out.println("Ingrese la habitación destino:");
		int codigoDestino = SistemaJuego.pedirCodigo();
		Habitacion habitacionDestino = (Habitacion) habitaciones.getObjeto(codigoDestino);
		// Cargar puntaje máximo
		System.out.println("Ingrese el puntaje máximo:");
		int puntajeMaximo = SistemaJuego.pedirCodigo();

		String esPosible;

		if (habitacionOrigen != null && habitacionDestino != null) {
			if (mapa.existeCaminoPesoMaximo(habitacionOrigen, habitacionDestino, puntajeMaximo)) {
				esPosible = "Es posible llegar de " + habitacionOrigen.getNombre() + " a "
						+ habitacionDestino.getNombre() + " con " + puntajeMaximo + " puntos";
			} else {
				esPosible = "No es posible llegar de " + habitacionOrigen.getNombre() + " a "
						+ habitacionDestino.getNombre() + " con " + puntajeMaximo + " puntos";
			}
		} else {
			esPosible = "Alguna o ambas habitaciones no existen";
		}

		return esPosible;
	}

	private static String minimoPuntaje(ArbolAVL habitaciones, Grafo mapa) {
		// Cargar habitación origen
		System.out.println("Ingrese la habitación origen:");
		int codigoOrigen = SistemaJuego.pedirCodigo();
		Habitacion habitacionOrigen = (Habitacion) habitaciones.getObjeto(codigoOrigen);
		// Cargar habitación destino
		System.out.println("Ingrese la habitación destino:");
		int codigoDestino = SistemaJuego.pedirCodigo();
		Habitacion habitacionDestino = (Habitacion) habitaciones.getObjeto(codigoDestino);

		String salida = "No existe un camino";
		Lista listaYPuntaje = mapa.minimoPesoParaPasar(habitacionOrigen, habitacionDestino);
		int longitudLista = listaYPuntaje.longitud();

		if (longitudLista != 0) {
			salida = "Camino de menos puntaje:\n";

			for (int posicion = 1; posicion < longitudLista; posicion++) {
				salida += ((Habitacion) listaYPuntaje.recuperar(posicion)).getCodigo() + " -> ";
			}
			// El elemento final se cargar a parte porque es el puntaje
			salida += "Puntaje necesario: " + listaYPuntaje.recuperar(longitudLista);

		} else if (habitacionOrigen == null || habitacionDestino == null) {
			salida = "Alguna o ambas habitaciones no existen";
		}

		return salida;
	}

	private static String sinPasarPor(ArbolAVL habitaciones, Grafo mapa) {
		// Cargar habitación origen
		System.out.println("Ingrese el código de la habitación origen:");
		int codigoOrigen = SistemaJuego.pedirCodigo();
		Habitacion habitacionOrigen = (Habitacion) habitaciones.getObjeto(codigoOrigen);
		// Cargar habitación destino
		System.out.println("Ingrese el código de la habitación destino:");
		int codigoDestino = SistemaJuego.pedirCodigo();
		Habitacion habitacionDestino = (Habitacion) habitaciones.getObjeto(codigoDestino);
		// Cargar habitación evitada
		System.out.println("Ingrese el código de la habitación evitada:");
		int codigoEvitada = SistemaJuego.pedirCodigo();
		Habitacion habitacionEvitada = (Habitacion) habitaciones.getObjeto(codigoEvitada);
		// Cargar puntaje máximo
		System.out.println("Ingrese el puntaje máximo:");
		int puntajeMaximo = SistemaJuego.pedirCodigo();

		String salida;
		Lista posiblesCaminos = mapa.pesoMaxSinPasarPor(habitacionOrigen, habitacionDestino, habitacionEvitada,
				puntajeMaximo);
		int longitudLista = posiblesCaminos.longitud();

		if (longitudLista != 0) {
			salida = "Caminos posibles sin superar " + puntajeMaximo + " puntos:\n";
			Lista caminoActual;
			int longitudCamino;

			for (int posicionLista = 1; posicionLista <= longitudLista; posicionLista++) {
				// Recorremos la lista de caminos
				caminoActual = (Lista) posiblesCaminos.recuperar(posicionLista);
				longitudCamino = caminoActual.longitud();
				salida += posicionLista + ") ";

				for (int posicionCamino = 1; posicionCamino < longitudCamino; posicionCamino++) {
					// Listamos las habitaciones del camino
					salida += ((Habitacion) caminoActual.recuperar(posicionCamino)).getCodigo() + " -> ";
				}
				// El último se pone a parte para que no quede una flecha extra
				salida += ((Habitacion) caminoActual.recuperar(longitudCamino)).getNombre();

				salida += "\n";
			}
		} else if (habitacionOrigen == null || habitacionDestino == null) {
			salida = "Alguna o ambas habitaciones no existen";
		} else {
			salida = "No existe un camino";
		}

		return salida;
	}

}
