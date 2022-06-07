package sistema;

import estructuras.*;
import objetos.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;

public class SistemaJuego {
	static final Scanner inputInt = new Scanner(System.in);
	static final Scanner inputText = new Scanner(System.in);

	public static int pedirCodigo() {
		return inputInt.nextInt();
	}

	public static String pedirNombre() {
		return inputText.nextLine();
	}

	private static String opcionesMenu() {
		return "1. Altas, bajas y modificaciones\n" + "2. Consulta sobre habitaciones\n"
				+ "3. Consultas sobre desafíos\n" + "4. Consultas sobre equipos participantes\n"
				+ "5. Consulta general\n" + "0. Salir";
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// Log
		String ruta = System.getProperty("user.dir") + "\\src\\log.txt";
		File salida = new File(ruta);
		FileWriter archivoSalida = new FileWriter(salida);
		BufferedWriter log = new BufferedWriter(archivoSalida);

		// Obtenemos la fecha de acceso para log
		Date fecha = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		logUpdate("Ingreso al sistema Escape House " + formato.format(fecha), log);

		// Estructuras para datos
		ArbolAVL habitaciones = new ArbolAVL();
		ArbolAVL desafios = new ArbolAVL();
		TablaHash equipos = new TablaHash();
		Grafo mapa = new Grafo();

		// Carga de datos
		cargarHabitaciones(mapa, habitaciones, log);
		cargarDesafios(desafios, log);
		cargarEquipos(equipos, habitaciones, log);
		cargarPuertasMapa(mapa, habitaciones, log);

		int opcionMenu;

		do {
			System.out.println("Ingrese una opción:");
			System.out.println(opcionesMenu());
			opcionMenu = pedirCodigo();
			switch (opcionMenu) {
			case 1:
				SistemaABM.menuABM(habitaciones, mapa, desafios, equipos, log);
				break;
			case 2:
				SistemaHabitaciones.menuHabitaciones(habitaciones, mapa);
				break;
			case 3:
				SistemaDesafios.menuDesafios(desafios, equipos);
				break;
			case 4:
				SistemaEquipos.menuEquipos(equipos, desafios, habitaciones, mapa);
				break;
			case 5:
				menuConsulta(habitaciones, desafios, equipos, mapa);
				break;
			case 0:
				System.out.println("Cerrando Escape House, ¡Adiós!");
				break;
			default:
				System.out.println("Opción no contemplada");
				break;
			}
			System.out.println();
		} while (opcionMenu != 0);

		fecha = new Date();
		logUpdate("Salida del sistema Escape House " + formato.format(fecha), log);
		logUpdate("", log);

	}

	public static void logUpdate(String texto, BufferedWriter bw) throws IOException {
		// Actualizar el archivo log
		bw.newLine();
		bw.append(texto);
		bw.flush();
	}

	// Carga inicial del sistema
	private static void cargarHabitaciones(Grafo mapa, ArbolAVL habitaciones, BufferedWriter log)
			throws FileNotFoundException, IOException {
		// Obtener archivo
		String ruta = System.getProperty("user.dir") + "\\src\\datos\\habitaciones.txt";
		File archivoAux = new File(ruta);

		FileReader archivo = new FileReader(archivoAux);
		BufferedReader archivoHabitaciones = new BufferedReader(archivo);

		// Cargar en log
		logUpdate("Carga de habitaciones", log);

		String linea;
		while ((linea = archivoHabitaciones.readLine()) != null) {
			StringTokenizer tokens = new StringTokenizer(linea, ";");

			// Contamos que tengamos todos los elementos
			if (tokens.countTokens() == 6 && tokens.nextToken().equals("H")) {
				int codigo = Integer.parseInt(tokens.nextToken());
				String nombre = (String) tokens.nextToken();
				int planta = Integer.parseInt(tokens.nextToken());
				int metrosCuadrados = Integer.parseInt(tokens.nextToken());
				boolean tieneSalida = Boolean.parseBoolean(tokens.nextToken());

				Habitacion habitacionNueva = new Habitacion(codigo, nombre, planta, metrosCuadrados, tieneSalida);
				// Lo ponemos en el mapa
				mapa.insertarVertice(habitacionNueva);
				if (habitaciones.insertar(codigo, habitacionNueva)) {
					// Si no está repetido se inserta con éxito
					logUpdate("Se crea la habitación " + codigo, log);
				} else {
					logUpdate("(Fallo carga) Habitación repetida " + codigo, log);
				}

			} else {
				// Datos mal cargados
				logUpdate("(Fallo carga) Habitación mal escrita", log);
			}
		}

		archivoHabitaciones.close();

	}

	private static void cargarDesafios(ArbolAVL desafios, BufferedWriter log)
			throws FileNotFoundException, IOException {
		// Obtener archivo
		String ruta = System.getProperty("user.dir") + "\\src\\datos\\desafios.txt";
		File archivoAux = new File(ruta);

		FileReader archivo = new FileReader(archivoAux);
		BufferedReader archivoDesafios = new BufferedReader(archivo);

		// Cargar en log
		logUpdate("Carga de desafíos", log);

		String linea;
		while ((linea = archivoDesafios.readLine()) != null) { // Carga y comparación
			StringTokenizer tokens = new StringTokenizer(linea, ";");

			// Contamos que tengamos todos los elementos
			if (tokens.countTokens() == 4 && tokens.nextToken().equals("D")) {
				int puntaje = Integer.parseInt(tokens.nextToken());
				String nombre = tokens.nextToken();
				String tipo = tokens.nextToken();

				Desafio desafioNuevo = new Desafio(nombre, puntaje, tipo);
				if (desafios.insertar(puntaje, desafioNuevo)) {
					// Si no está repetido se inserta con éxito
					logUpdate("Se crea el desafío " + puntaje, log);
				} else {
					logUpdate("(Fallo carga) Desafío repetido " + puntaje, log);
				}
			} else {
				// Datos mal cargados
				logUpdate("(Fallo carga) Desafío mal escrito", log);
			}
		}

		archivoDesafios.close();

	}

	private static void cargarEquipos(TablaHash equipos, ArbolAVL habitaciones, BufferedWriter log)
			throws FileNotFoundException, IOException {
		// Obtener archivo
		String ruta = System.getProperty("user.dir") + "\\src\\datos\\equipos.txt";
		File archivoAux = new File(ruta);

		FileReader archivo = new FileReader(archivoAux);
		BufferedReader archivoEquipos = new BufferedReader(archivo);

		// Cargar en log
		logUpdate("Carga de equipos", log);

		String linea;
		while ((linea = archivoEquipos.readLine()) != null) {
			StringTokenizer tokens = new StringTokenizer(linea, ";");

			// Contamos que tengamos todos los elementos
			if (tokens.countTokens() == 6 && tokens.nextToken().equals("E")) {
				String nombre = tokens.nextToken();
				int puntajeSalida = Integer.parseInt(tokens.nextToken());
				int puntajeTotal = Integer.parseInt(tokens.nextToken());
				// La habitación actual debe existir
				int codigoHabitacion = Integer.parseInt(tokens.nextToken());
				Object habitacionActual = habitaciones.getObjeto(codigoHabitacion);
				if (habitacionActual != null) {
					int puntajeActual = Integer.parseInt(tokens.nextToken());

					Equipo equipo = new Equipo(nombre, puntajeSalida, puntajeTotal, puntajeActual,
							(Habitacion) habitacionActual);
					if (equipos.insertar(nombre, equipo)) {
						// Si no está repetido se inserta con éxito
						logUpdate("Se crea el equipo " + nombre, log);
						// Marcamos la habitación actual como visitada
						equipo.getHabitacionesVisitadas().insertar(habitacionActual, 1);
					} else {
						logUpdate("(Fallo carga) Equipo repetido " + nombre, log);
					}
				} else {
					// Error en carga (no existe el equipo)
					logUpdate("(Fallo carga) Habitación no existe para equipo " + nombre, log);
				}
			} else {
				// Datos mal cargados
				logUpdate("(Fallo carga) Equipo mal escrito", log);
			}
		}

		archivoEquipos.close();

	}

	private static void cargarPuertasMapa(Grafo mapa, ArbolAVL habitaciones, BufferedWriter log)
			throws FileNotFoundException, IOException {
		// Obtener archivo
		String ruta = System.getProperty("user.dir") + "\\src\\datos\\puertas.txt";
		File archivoAux = new File(ruta);

		FileReader archivo = new FileReader(archivoAux);
		BufferedReader archivoDesafios = new BufferedReader(archivo);

		// Cargar en log
		logUpdate("Carga del mapa", log);

		String linea;
		while ((linea = archivoDesafios.readLine()) != null) { // Carga y comparación
			StringTokenizer tokens = new StringTokenizer(linea, ";");

			// Contamos que tengamos todos los elementos
			if (tokens.countTokens() == 4 && tokens.nextToken().equals("P")) {
				// Las habitaciones deben existir
				int primerCodigo = Integer.parseInt(tokens.nextToken());
				Object primeraHabitacion = habitaciones.getObjeto(primerCodigo);
				int segundoCodigo = Integer.parseInt(tokens.nextToken());
				Object segundaHabitacion = habitaciones.getObjeto(segundoCodigo);

				if (primeraHabitacion != null && segundaHabitacion != null) {
					int puntajeParaPasar = Integer.parseInt(tokens.nextToken());

					if (mapa.insertarArco((Habitacion) primeraHabitacion, (Habitacion) segundaHabitacion,
							puntajeParaPasar)) {
						// Si no está repetido se inserta con éxito
						logUpdate("Se crea la puerta de " + primerCodigo + " a " + segundoCodigo, log);
					} else {
						logUpdate("(Fallo carga) Puerta repetida de " + primerCodigo + " a " + segundoCodigo, log);
					}
				} else {
					// Error en carga (no existe el equipo)
					logUpdate("(Fallo carga) Habitación no existe para puerta", log);
				}
			} else {
				// Datos mal cargados
				logUpdate("(Fallo carga) Puerta mal escrita", log);
			}
		}

		archivoDesafios.close();
	}

	// Consulta general
	private static void menuConsulta(ArbolAVL habitaciones, ArbolAVL desafios, TablaHash equipos, Grafo mapa) {
		int opcionMenu;

		do {
			System.out.println("\nIngrese una opción:");
			System.out.println("1. Ver árbol AVL de habitaciones\n" + "2. Ver árbol AVL de desafíos\n"
					+ "3. Ver Tabla Hash de equipos\n" + "4. Ver Grafo del mapa\n" + "0. Volver");
			opcionMenu = pedirCodigo();
			switch (opcionMenu) {
			case 1:
				System.out.println(habitaciones.toString());
				break;
			case 2:
				System.out.println(desafios.toString());
				break;
			case 3:
				System.out.println(equipos.toString());
				break;
			case 4:
				System.out.println(mapa.toString());
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

}
