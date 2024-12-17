public class NodoObj {
    private Object valor; // Acepta cualquier tipo de dato
    private NodoObj siguiente;

    public NodoObj(Object valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public NodoObj getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoObj siguiente) {
        this.siguiente = siguiente;
    }
}
