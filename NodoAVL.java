public class NodoAVL {
    private int distancia;
    private String municipio; // Nuevo campo para almacenar el municipio
    private int altura;
    private NodoAVL izq, der;

    public NodoAVL(int distancia, String municipio) {
        this.distancia = distancia;
        this.municipio = municipio;
        this.altura = 1; // La altura inicial es 1
        this.izq = null;
        this.der = null;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getMunicipio() { // Nuevo método getter para municipio
        return municipio;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVL getIzq() {
        return izq;
    }

    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    public NodoAVL getDer() {
        return der;
    }

    public void setDer(NodoAVL der) {
        this.der = der;
    }
}
