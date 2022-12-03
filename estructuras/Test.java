package estructuras;

public class Test {
	public static void main(String[] args) {
		ArbolAVL arbol = new ArbolAVL();
		arbol.insertar(66, 66);
		arbol.insertar(25, 25);
		arbol.insertar(85, 85);
		arbol.insertar(12, 12);
		arbol.insertar(50, 50);
		arbol.insertar(73, 73);
		arbol.insertar(91, 91);
		arbol.insertar(6, 6);
		arbol.insertar(23, 23);
		arbol.insertar(27, 27);
		arbol.insertar(52, 52);
		arbol.insertar(69, 69);
		arbol.insertar(86, 86);
		arbol.insertar(96, 96);
		arbol.insertar(88, 88);
		System.out.println(arbol.toString() + "\n----");
		arbol.eliminar(66);
		System.out.println(arbol.toString());
	}
}
