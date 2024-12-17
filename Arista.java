public class Arista {
    private  Object destino;
    private float peso;
    private  Arista siguiente;

    public Arista(Object destino) {
        this.destino = destino;
        this.siguiente=null;
    }

    public Arista(Object destino, float peso) {
        this.destino = destino;
        this.peso = peso;
        this.siguiente=null;
    }

    public Object getDestino() {
        return destino;
    }

    public void setDestino(Object destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Arista getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Arista siguiente) {
        this.siguiente = siguiente;
    }
}
