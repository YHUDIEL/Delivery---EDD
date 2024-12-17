public class Lista {
    private Arista primero;
    private Arista ultimo;

    public Lista() {
        this.primero = null;
        this.ultimo = null;
    }

    public  boolean estaVacia(){
        return this.primero==null && this.ultimo==null;
    }

    public Arista getPrimero() {
        return primero;
    }

    public void agregarAdyacencia(Object destino){
        if(!existe(destino)){
            Arista nodo = new Arista(destino);
            inserta(nodo);
        }
    }

    public void agregarAdyacencia(String destino, float peso) {
        if (!existeAdyacencia(destino)) { // Verifica si ya existe una conexión al destino
            Arista nueva = new Arista(destino, peso);
            if (primero == null) {
                primero = nueva;
            } else {
                Arista actual = primero;
                while (actual.getSiguiente() != null) {
                    actual = actual.getSiguiente();
                }
                actual.setSiguiente(nueva);
            }
        }
    }

    private boolean existeAdyacencia(String destino) {
        Arista actual = primero;
        while (actual != null) {
            if (actual.getDestino().equals(destino)) {
                return true; // Ya existe una conexión al destino
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    private boolean inserta(Arista nodo) {
        if(estaVacia()){
            this.primero=nodo;
            this.ultimo=nodo;
            return true;
        }
        this.ultimo.setSiguiente(nodo);
        this.ultimo=nodo;
        return  true;
    }

    private boolean existe(Object destino) {
        Arista actual=primero;
        while(actual != null ){
            if(destino.toString().equals(actual.getDestino().toString()))
                return true;
            actual= actual.getSiguiente();
        }
        return  false;
    }

    @Override
    public String toString() {
        String cadena="";
        Arista actual= primero;
        while (actual!=null){
            cadena += actual.getDestino().toString()+";";
            actual=actual.getSiguiente();
        }
        return  cadena;
    }
}