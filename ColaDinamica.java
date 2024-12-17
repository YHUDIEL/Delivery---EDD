public class ColaDinamica {
    private NodoObj primero;
    private NodoObj ultimo;

    public ColaDinamica() {
        this.primero = null;
        this.ultimo = null;
    }

    public boolean estaVacia() {
        return this.primero == null;
    }

    public void agregar(Object valor) {
        NodoObj nuevo = new NodoObj(valor); // Crear NodoObj en lugar de Nodo
        if (estaVacia()) {
            this.primero = nuevo;
            this.ultimo = nuevo;
        } else {
            this.ultimo.setSiguiente(nuevo);
            this.ultimo = nuevo;
        }
    }

    public Object eliminar() throws Exception {
        if (estaVacia()) throw new Exception("Cola vacía");
        Object valor = this.primero.getValor();
        this.primero = this.primero.getSiguiente();
        if (this.primero == null) this.ultimo = null; // Actualiza el último nodo si queda vacío
        return valor;
    }

    public Object verPrimero() throws Exception {
        if (estaVacia()) throw new Exception("Cola vacía");
        return this.primero.getValor();
    }

    public void mostrar() {
        NodoObj actual = primero;
        while (actual != null) {
            System.out.print(actual.getValor() + " -> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }
}
