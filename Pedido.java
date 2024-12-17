public class Pedido {
    private int id;
    private String producto;
    private String municipio;

    public Pedido(int id, String producto, String municipio) {
        this.id = id;
        this.producto = producto;
        this.municipio = municipio;
    }

    public int getId() {
        return id;
    }

    public String getProducto() {
        return producto;
    }

    public String getMunicipio() {
        return municipio;
    }
}
