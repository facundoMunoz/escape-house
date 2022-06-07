package sistema;

import estructuras.*;
import objetos.*;
import java.io.BufferedWriter;
import java.io.IOException;

public class SistemaABM {

	private static String opcionesMenu() {
		return "1. Modificar habitaciones\n" + "2. Modificar desafíos\n" + "3. Modificar equipos\n" + "0. Volver";
	}

	public static void menuABM(ArbolAVL habitaciones, Grafo mapa, ArbolAVL desafios, TablaHash equipos,
			BufferedWriter bw) throws IOException {
		// Menú principal
		int opcionMenu;

		do {
			System.out.println("Ingrese una opción:");
			System.out.println(opcionesMenu());
			opcionMenu = SistemaJuego.pedirCodigo();

			switch (opcionMenu) {
			case 1:
				menuHabitaciones(habitaciones, mapa, equipos, bw);
				break;
			case 2:
				menuDesafios(desafios, bw);
				break;
			case 3:
				menuEquipos(equipos, habitaciones, bw);
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

	// Habitaciones
	private static void menuHabitaciones(ArbolAVL habitaciones, Grafo mapa, TablaHash equipos, BufferedWriter bw)
			throws IOException {
		int opcionCrear;

		do {
			System.out.println("Ingrese una opción:");
			System.out.println(
					"1. Crear habitación\n" + "2. Eliminar habitación\n" + "3. Modificar habitación\n" + "0. Volver");
			opcionCrear = SistemaJuego.pedirCodigo();

			switch (opcionCrear) {
			case 1:
				System.out.println(crearHabitacion(habitaciones, mapa, bw));
				break;
			case 2:
				System.out.println(eliminarHabitacion(habitaciones, mapa, equipos, bw));
				break;
			case 3:
				System.out.println(modificarHabitacion(habitaciones));
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionCrear != 0);
	}

	private static String crearHabitacion(ArbolAVL habitaciones, Grafo mapa, BufferedWriter bw) throws IOException {
		// Cargar habitación
		System.out.println("Ingrese el código:");
		int codigo = SistemaJuego.pedirCodigo();

		// El código no se puede repetir
		while (habitaciones.pertenece(codigo)) {
			System.out.println("El código ya existe, intente de nuevo:");
			codigo = SistemaJuego.pedirCodigo();
		}

		System.out.println("Ingrese el nombre:");
		String nombre = SistemaJuego.pedirNombre();
		System.out.println("Ingrese la planta:");
		int planta = SistemaJuego.pedirCodigo();
		System.out.println("Ingrese los metros cuadrados:");
		int metrosCuadrados = SistemaJuego.pedirCodigo();

		// Cargamos el booleano con texto
		String textoSalidaExterior;
		do {
			System.out.println("¿Tiene salida exterior? (s/n)");
			textoSalidaExterior = SistemaJuego.pedirNombre();
		} while (!textoSalidaExterior.equals("s") && !textoSalidaExterior.equals("n"));

		boolean salidaExterior = (textoSalidaExterior.equals("s")) ? true : false;

		// Crear y agregar
		Habitacion habitacionNueva = new Habitacion(codigo, nombre, planta, metrosCuadrados, salidaExterior);
		habitaciones.insertar(codigo, habitacionNueva);
		mapa.insertarVertice(habitacionNueva);

		// Update log
		SistemaJuego.logUpdate("Se crea habitación " + codigo, bw);

		return "Habitación creada:\n" + habitacionNueva.toString();
	}

	private static String eliminarHabitacion(ArbolAVL habitaciones, Grafo mapa, TablaHash equipos, BufferedWriter bw)
			throws IOException {
		System.out.println("Ingrese el código de la habitación:");
		int codigo = SistemaJuego.pedirCodigo();
		Habitacion habitacion = (Habitacion) habitaciones.getObjeto(codigo);
		String eliminado;

		if (habitacion != null) {
			Lista lista = equipos.hacerLista();
			int pos = 1;
			int tamanio = lista.longitud();
			boolean habitacionEnUso = false;
			// Dejamos eliminado inicializado
			eliminado = "Habitación " + codigo + " eliminada";

			while (pos <= tamanio && !habitacionEnUso) {
				// Tenemos que asegurar que ningún equipo apunte a la habitación que se quiere
				// eliminar
				Equipo equipo = (Equipo) lista.recuperar(pos);

				if (equipo.getHabitacion().getCodigo() == codigo) {
					habitacionEnUso = true;
					// Cambiamos eliminado si la habitación está en uso
					eliminado = "Equipo " + equipo.getNombre() + " se encuentra en la habitación";
				}
				pos++;
			}

			if (!habitacionEnUso) {
				habitaciones.eliminar(codigo);
				mapa.eliminarVertice(habitacion);
				SistemaJuego.logUpdate("Se elimina habitación " + codigo, bw);
			}
		} else {
			eliminado = "El código no corresponde a una habitación cargada";
		}

		return eliminado;
	}

	private static String modificarHabitacion(ArbolAVL habitaciones) {
		System.out.println("Ingrese el código:");
		int codigo = SistemaJuego.pedirCodigo();
		Habitacion habitacion = (Habitacion) habitaciones.getObjeto(codigo);
		String cambios;

		if (habitacion != null) {
			int opcionMod;
			System.out.println("¿Qué va a modificar?");
			System.out.println("1. Nombre\n" + "2. Planta\n" + "3. Metros cuadrados\n"
					+ "4. Cambiar salida al exterior\n" + "0. Volver");
			opcionMod = SistemaJuego.pedirCodigo();

			switch (opcionMod) {
			case 1:
				System.out.println("Ingrese el nuevo nombre:");
				habitacion.setNombre(SistemaJuego.pedirNombre());
				break;
			case 2:
				System.out.println("Ingrese la nueva planta:");
				habitacion.setPlanta(SistemaJuego.pedirCodigo());
				break;
			case 3:
				System.out.println("Ingrese los nuevos metros cuadrados:");
				habitacion.setMetrosCuadrados(SistemaJuego.pedirCodigo());
				break;
			case 4:
				habitacion.setSalidaExterior(!habitacion.getSalidaExterior());
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			cambios = "La habitación queda como:\n" + habitacion.toString();
		} else {
			cambios = "El código no corresponde a una habitación cargada";
		}

		return cambios;
	}

	// Desafíos
	private static void menuDesafios(ArbolAVL desafios, BufferedWriter bw) throws IOException {
		int opcionCrear;

		do {
			System.out.println("Ingrese una opción:");
			System.out.println("1. Crear desafío\n" + "2. Eliminar desafío\n" + "3. Modificar desafío\n" + "0. Volver");
			opcionCrear = SistemaJuego.pedirCodigo();

			switch (opcionCrear) {
			case 1:
				System.out.println(crearDesafio(desafios, bw));
				break;
			case 2:
				System.out.println(eliminarDesafio(desafios, bw));
				break;
			case 3:
				System.out.println(modificarDesafio(desafios));
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionCrear != 0);
	}

	private static String crearDesafio(ArbolAVL desafios, BufferedWriter bw) throws IOException {
		// Cargar desafío
		System.out.println("Ingrese el puntaje:");
		int puntaje = SistemaJuego.pedirCodigo();

		// El puntaje no se puede repetir
		while (desafios.pertenece(puntaje)) {
			System.out.println("El puntaje ya existe, intente de nuevo:");
			puntaje = SistemaJuego.pedirCodigo();
		}

		System.out.println("Ingrese el nombre:");
		String nombre = SistemaJuego.pedirNombre();
		System.out.println("Ingrese el tipo:");
		String tipo = SistemaJuego.pedirNombre();

		// Crear y agregar
		Desafio desafioNuevo = new Desafio(nombre, puntaje, tipo);
		desafios.insertar(puntaje, desafioNuevo);

		// Update log
		SistemaJuego.logUpdate("Se crea desafío " + puntaje, bw);

		return "Desafío creado:\n" + desafioNuevo.toString();
	}

	private static String eliminarDesafio(ArbolAVL desafios, BufferedWriter bw) throws IOException {
		System.out.println("Ingrese el puntaje del desafío:");
		int puntaje = SistemaJuego.pedirCodigo();
		String eliminado;

		if (desafios.eliminar(puntaje)) {
			eliminado = "Desafío " + puntaje + " eliminado";
			// Update log
			SistemaJuego.logUpdate("Se elimina desafío " + puntaje, bw);
		} else {
			eliminado = "El puntaje no corresponde a un desafío cargado";
		}

		return eliminado;
	}

	private static String modificarDesafio(ArbolAVL desafios) {
		System.out.println("Ingrese el puntaje:");
		int puntaje = SistemaJuego.pedirCodigo();
		Desafio desafio = (Desafio) desafios.getObjeto(puntaje);
		String cambios;

		if (desafio != null) {
			int opcionMod;
			System.out.println("¿Qué va a modificar?");
			System.out.println("1. Nombre\n" + "2. Tipo\n" + "0. Volver");
			opcionMod = SistemaJuego.pedirCodigo();

			switch (opcionMod) {
			case 1:
				System.out.println("Ingrese el nuevo nombre:");
				desafio.setNombre(SistemaJuego.pedirNombre());
				break;
			case 2:
				System.out.println("Ingrese el nuevo tipo:");
				desafio.setTipo(SistemaJuego.pedirNombre());
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			cambios = "El desafío queda como:\n" + desafio.toString();
		} else {
			cambios = "El puntaje no corresponde a un desafío cargado";
		}

		return cambios;
	}

	// Equipos
	private static void menuEquipos(TablaHash equipos, ArbolAVL habitaciones, BufferedWriter bw) throws IOException {
		int opcionCrear;

		do {
			System.out.println("Ingrese una opción:");
			System.out.println("1. Crear equipo\n" + "2. Eliminar equipo\n" + "3. Modificar equipo\n" + "0. Volver");
			opcionCrear = SistemaJuego.pedirCodigo();

			switch (opcionCrear) {
			case 1:
				System.out.println(crearEquipo(equipos, habitaciones, bw));
				break;
			case 2:
				System.out.println(eliminarEquipo(equipos, bw));
				break;
			case 3:
				System.out.println(modificarEquipo(equipos, habitaciones));
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionCrear != 0);
	}

	private static String crearEquipo(TablaHash equipos, ArbolAVL habitaciones, BufferedWriter bw) throws IOException {
		String exito;

		if (!habitaciones.vacio()) {
			// Debemos asegurar que hay habitaciones disponibles para el equipo
			// Cargar equipo
			System.out.println("Ingrese el nombre:");
			String nombre = SistemaJuego.pedirNombre();

			// El puntaje no se puede repetir
			while (equipos.pertenece(nombre)) {
				System.out.println("El nombre ya existe, intente de nuevo:");
				nombre = SistemaJuego.pedirNombre();
			}

			System.out.println("Ingrese el puntaje para salir:");
			int puntajeSalida = SistemaJuego.pedirCodigo();
			System.out.println("Ingrese el puntaje total acumulado:");
			int puntajeAcumulado = SistemaJuego.pedirCodigo();
			System.out.println("Ingrese el puntaje en la habitación actual:");
			int puntajeActual = SistemaJuego.pedirCodigo();

			System.out.println("Ingrese el código de la habitación actual:");
			int codigoHabitacion = SistemaJuego.pedirCodigo();
			Habitacion habitacion = (Habitacion) habitaciones.getObjeto(codigoHabitacion);

			while (habitacion == null) {
				System.out.println("El codigo no corresponde a una habitación cargada, intente de nuevo:");
				codigoHabitacion = SistemaJuego.pedirCodigo();
				habitacion = (Habitacion) habitaciones.getObjeto(codigoHabitacion);
			}
			// Crear y agregar
			Equipo equipoNuevo = new Equipo(nombre, puntajeSalida, puntajeAcumulado, puntajeActual, habitacion);
			equipos.insertar(nombre, equipoNuevo);
			// Marcamos la habitación actual como visitada
			equipoNuevo.getHabitacionesVisitadas().insertar(habitacion, 1);
			exito = "Equipo creado:\n" + equipoNuevo.toString();

			// Update log
			SistemaJuego.logUpdate("Se crea equipo " + nombre, bw);
		} else {
			exito = "¡No hay ninguna habitación cargada!";
		}

		return exito;
	}

	private static String eliminarEquipo(TablaHash equipos, BufferedWriter bw) throws IOException {
		System.out.println("Ingrese el nombre del equipo:");
		String nombre = SistemaJuego.pedirNombre();
		String eliminado;

		if (equipos.eliminar(nombre)) {
			eliminado = "Equipo '" + nombre + "' eliminado";

			// Update log
			SistemaJuego.logUpdate("Se elimina equipo " + nombre, bw);
		} else {
			eliminado = "El nombre no corresponde a un equipo cargado";
		}

		return eliminado;
	}

	private static String modificarEquipo(TablaHash equipos, ArbolAVL habitaciones) {
		System.out.println("Ingrese el nombre:");
		String nombre = SistemaJuego.pedirNombre();
		Equipo equipo = (Equipo) equipos.getObjeto(nombre);
		String cambios;

		if (equipo != null) {
			int opcionMod;
			System.out.println("¿Qué va a modificar?");
			System.out.println("1. Puntaje para salir\n" + "2. Puntaje total\n" + "3. Puntaje actual\n"
					+ "4. Habitación actual\n" + "0. Volver");
			opcionMod = SistemaJuego.pedirCodigo();

			switch (opcionMod) {
			case 1:
				System.out.println("Ingrese el nuevo puntaje para salir:");
				equipo.setPuntajeSalida(SistemaJuego.pedirCodigo());
				break;
			case 2:
				System.out.println("Ingrese el nuevo puntaje total:");
				equipo.setPuntajeAcumulado(SistemaJuego.pedirCodigo());
				break;
			case 3:
				System.out.println("Ingrese el nuevo puntaje actual:");
				equipo.setPuntajeActual(SistemaJuego.pedirCodigo());
				break;
			case 4:
				System.out.println("Ingrese el código de la habitación:");
				Habitacion habitacion = (Habitacion) habitaciones.getObjeto(SistemaJuego.pedirCodigo());
				if (habitacion != null) {
					equipo.setHabitacion(habitacion);
					// Marcamos la habitación actual como visitada
					equipo.getHabitacionesVisitadas().insertar(habitacion, 1);
				} else {
					System.out.println("El código no corresponde a una habitación cargada");
				}
				break;
			case 0:
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			cambios = "El equipo queda como:\n" + equipo.toString();
		} else {
			cambios = "El nombre no corresponde a un equipo cargado";
		}

		return cambios;
	}

}
