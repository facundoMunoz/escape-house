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
				System.out.println(habitacionesContiguas(mapa));
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
			habitacion = habitaciones.getObjeto(codigo).toString();
		}

		return habitacion;
	}

	private static String habitacionesContiguas(Grafo mapa) {
		// Cargar habitación
		System.out.println("Ingrese el código de la habitación:");
		int codigo = SistemaJuego.pedirCodigo();
		Lista adyacentesYEtiquetas = mapa.listarAdyacentesYEtiquetas(codigo);
		String contiguas;

		if (!adyacentesYEtiquetas.esVacia()) {
			contiguas = "Código habitación - Nombre habitación | Puntaje para cruzar/n";
			int posicion = 1;
			int longitudLista = adyacentesYEtiquetas.longitud();

			Habitacion habitacionActual;

			while (posicion <= longitudLista) {
				// Cargamos la habitación
				habitacionActual = (Habitacion) adyacentesYEtiquetas.recuperar(posicion);
				contiguas += habitacionActual.getCodigo() + " - " + habitacionActual.getNombre() + " | ";
				// Cargamos el puntaje necesario
				posicion++;
				contiguas += adyacentesYEtiquetas.recuperar(posicion) + "\n";
				posicion++;
			}
		} else {
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
			if (mapa.existeCaminoPuntajeMaximo(habitacionOrigen, habitacionDestino, puntajeMaximo)) {
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

		String salida;
		Lista listaYPuntaje = mapa.minimoPuntajeParaPasar(habitacionOrigen, habitacionDestino);
		int longitudLista = listaYPuntaje.longitud();

		if (longitudLista != 0) {
			salida = "Camino:\n";

			for (int posicion = 1; posicion < longitudLista; posicion++) {
				salida += ((Habitacion) listaYPuntaje.recuperar(posicion)).getNombre() + " -> ";
			}
			// El elemento final se cargar a parte porque es el puntaje
			salida += "Puntaje necesario: " + listaYPuntaje.recuperar(longitudLista);

		} else {
			salida = "Alguna o ambas habitaciones no existen o no hay un camino";
		}

		return salida;
	}

	private static String sinPasarPor(ArbolAVL habitaciones, Grafo mapa) {
		// Cargar habitación origen
		System.out.println("Ingrese la habitación origen:");
		int codigoOrigen = SistemaJuego.pedirCodigo();
		Habitacion habitacionOrigen = (Habitacion) habitaciones.getObjeto(codigoOrigen);
		// Cargar habitación destino
		System.out.println("Ingrese la habitación destino:");
		int codigoDestino = SistemaJuego.pedirCodigo();
		Habitacion habitacionDestino = (Habitacion) habitaciones.getObjeto(codigoDestino);
		// Cargar habitación evitada
		System.out.println("Ingrese la habitación evitada:");
		int codigoEvitada = SistemaJuego.pedirCodigo();
		Habitacion habitacionEvitada = (Habitacion) habitaciones.getObjeto(codigoEvitada);
		// Cargar puntaje máximo
		System.out.println("Ingrese el puntaje máximo:");
		int puntajeMaximo = SistemaJuego.pedirCodigo();

		String salida;
		Lista posiblesCaminos = mapa.puntajeMaxSinPasarPor(habitacionOrigen, habitacionDestino, habitacionEvitada,
				puntajeMaximo);
		int longitudLista = posiblesCaminos.longitud();

		if (longitudLista != 0) {
			salida = "Caminos posibles:\n";
			Lista caminoActual;

			for (int posicionLista = 1; posicionLista <= longitudLista; posicionLista++) {
				// Recorremos la lista de caminos
				caminoActual = (Lista) posiblesCaminos.recuperar(posicionLista);

				for (int posicionCamino = 1; posicionCamino < longitudLista; posicionCamino++) {
					// Listamos las habitaciones del camino
					salida += ((Habitacion) caminoActual.recuperar(posicionCamino)).getNombre() + " -> ";
				}
				
				salida += "\n";
			}
		} else {
			salida = "Alguna o ambas habitaciones no existen o no hay un camino";
		}
		
		return salida;
	}

}
