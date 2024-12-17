public class PilaDinamica {
    private Nodo tope;

    public PilaDinamica() {
        this.tope = null;
    }

    public boolean estaVacia() {
        return this.tope == null;
    }

    public void push(int valor) {
        Nodo nuevo = new Nodo(valor);
        nuevo.setSiguiente(tope);
        tope = nuevo;
    }

    public int pop() throws Exception {
        if (estaVacia()) throw new Exception("Pila vacía");
        int valor = tope.getValor();
        tope = tope.getSiguiente();
        return valor;
    }

    public int verTope() throws Exception {
        if (estaVacia()) throw new Exception("Pila vacía");
        return tope.getValor();
    }

    public void mostrar() {
        Nodo actual = tope;
        while (actual != null) {
            System.out.print(actual.getValor() + " -> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }
}
