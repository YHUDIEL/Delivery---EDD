import java.util.ArrayList;
import java.util.List;

public class ArbolAVL {
    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public void insertarSinDuplicados(int distancia, String municipio) {
        this.raiz = insertar(this.raiz, distancia, municipio);
    }

    private NodoAVL insertar(NodoAVL nodo, int distancia, String municipio) {
        if (nodo == null) {
            return new NodoAVL(distancia, municipio);
        }
        if (distancia < nodo.getDistancia()) {
            nodo.setIzq(insertar(nodo.getIzq(), distancia, municipio));
        } else if (distancia > nodo.getDistancia()) {
            nodo.setDer(insertar(nodo.getDer(), distancia, municipio));
        }
        return nodo; // No permite duplicados
    }

    public int[] extraerDistancias() {
        List<Integer> distancias = new ArrayList<>();
        extraerDistancias(this.raiz, distancias);
        return distancias.stream().mapToInt(Integer::intValue).toArray();
    }

    private void extraerDistancias(NodoAVL nodo, List<Integer> distancias) {
        if (nodo != null) {
            extraerDistancias(nodo.getIzq(), distancias);
            distancias.add(nodo.getDistancia());
            extraerDistancias(nodo.getDer(), distancias);
        }
    }

    private void actualizarAltura(NodoAVL nodo) {
        if (nodo != null) {
            nodo.setAltura(1 + Math.max(determinarAltura(nodo.getIzq()), determinarAltura(nodo.getDer())));
        }
    }

    private NodoAVL balancear(NodoAVL nodo) {
        int balance = determinarAltura(nodo.getDer()) - determinarAltura(nodo.getIzq());

        // Rotación a la izquierda
        if (balance > 1) {
            if (determinarAltura(nodo.getDer().getDer()) >= determinarAltura(nodo.getDer().getIzq())) {
                return rotacionIzq(nodo);
            } else {
                nodo.setDer(rotacionDer(nodo.getDer()));
                return rotacionIzq(nodo);
            }
        }

        // Rotación a la derecha
        if (balance < -1) {
            if (determinarAltura(nodo.getIzq().getIzq()) >= determinarAltura(nodo.getIzq().getDer())) {
                return rotacionDer(nodo);
            } else {
                nodo.setIzq(rotacionIzq(nodo.getIzq()));
                return rotacionDer(nodo);
            }
        }

        return nodo;
    }

    public int determinarAltura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.getAltura();
    }

    public NodoAVL rotacionIzq(NodoAVL x) {
        NodoAVL y = x.getDer();
        NodoAVL z = y.getIzq();

        y.setIzq(x);
        x.setDer(z);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    public NodoAVL rotacionDer(NodoAVL x) {
        NodoAVL y = x.getIzq();
        NodoAVL z = y.getDer();

        y.setDer(x);
        x.setIzq(z);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    public void mostrarEnOrden() {
        mostrarEnOrden(this.raiz);
    }

    private void mostrarEnOrden(NodoAVL nodo) {
        if (nodo != null) {
            mostrarEnOrden(nodo.getIzq()); // Recorre el subárbol izquierdo
            System.out.println("Distancia: " + nodo.getDistancia() + " km, Municipio: " + nodo.getMunicipio());
            mostrarEnOrden(nodo.getDer()); // Recorre el subárbol derecho
        }
    }

}
