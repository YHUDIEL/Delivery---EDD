import java.util.*;

public class Grafo {
    private  NodoGrafo primero;
    private  NodoGrafo ultimo;

    public Grafo() {
        this.primero=null;
        this.ultimo=null;
    }

    public  boolean estaVacio(){
        return  this.primero==null && this.ultimo==null;
    }

    public boolean existeVertice(Object dato) {
        dato = dato.toString().trim().toLowerCase(); // Normalización del dato
        if (estaVacio())
            return false;
        NodoGrafo actual = primero;
        while (actual != null) {
            if (actual.getDato().toString().equals(dato))
                return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public  boolean agregarArista(Object origen, Object destino){
        if(!existeVertice(origen) || !existeVertice(destino)){
            System.out.println("No se puede agregar");
            return false;
        }

        NodoGrafo actual= primero;
        while(!actual.getDato().toString().equals(origen.toString())){
            actual= actual.getSiguiente();
        }
        actual.getListaAdyacencia().agregarAdyacencia(destino);
        return  true;
    }

    public boolean agregarNodo(Object dato) {
        dato = dato.toString().trim().toLowerCase(); // Normalización del dato
        if (existeVertice(dato))
            return false;
        NodoGrafo nodo = new NodoGrafo(dato);
        if (estaVacio()) {
            this.primero = nodo;
            this.ultimo = nodo;
            return true;
        }
        this.ultimo.setSiguiente(nodo);
        this.ultimo = nodo;
        return true;
    }

    public boolean agregarArista(Object origen, Object destino, float peso) {
        // Comprobar si el origen y el destino existen en el grafo
        if (!existeVertice(origen) || !existeVertice(destino)) {
            System.out.println("Error: No existe el nodo origen o destino.");
            return false;
        }

        // Buscar el nodo de origen
        NodoGrafo actual = buscarNodo(origen);
        if (actual != null) {
            // Hacer el cast explícito de Object a String (o el tipo adecuado en tu caso)
            String destinoNodo = (String) destino;  // O usa el tipo correcto que necesitas
            actual.getListaAdyacencia().agregarAdyacencia(destinoNodo, peso);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        if (primero == null) {
            return "El grafo está vacío.";
        }

        StringBuilder resultado = new StringBuilder();
        NodoGrafo actual = primero;

        while (actual != null) {
            resultado.append(actual.getDato()).append(" -> ");
            Arista aristaActual = actual.getListaAdyacencia().getPrimero();
            if (aristaActual == null) {
                resultado.append("Sin conexiones");
            } else {
                while (aristaActual != null) {
                    resultado.append(aristaActual.getDestino()).append(" (")
                    .append(aristaActual.getPeso()).append(" km); ");
                    aristaActual = aristaActual.getSiguiente();
                }
            }
            resultado.append("\n");
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    private NodoGrafo buscarNodo(Object dato) {
        dato = dato.toString().trim().toLowerCase(); // Normalización
        NodoGrafo actual = primero;
        while (actual != null) {
            if (actual.getDato().toString().trim().toLowerCase().equals(dato)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public List<String> encontrarRutaMasCorta(Object origen, Object destino) {
        origen = origen.toString().trim().toLowerCase(); // Normalización
        destino = destino.toString().trim().toLowerCase(); // Normalización

        if (!existeVertice(origen) || !existeVertice(destino)) {
            return null;
        }

        // Crear mapas para distancias, rutas previas y los nodos visitados
        Map<NodoGrafo, Float> distancias = new HashMap<>();
        Map<NodoGrafo, NodoGrafo> anteriores = new HashMap<>();
        Set<NodoGrafo> visitados = new HashSet<>();
        PriorityQueue<NodoGrafo> cola = new PriorityQueue<>(Comparator.comparing(distancias::get));

        NodoGrafo nodoOrigen = buscarNodo(origen);
        NodoGrafo nodoDestino = buscarNodo(destino);

        // Inicializar las distancias
        NodoGrafo actual = primero;
        while (actual != null) {
            distancias.put(actual, Float.MAX_VALUE); // Inicializar todas las distancias a infinito
            actual = actual.getSiguiente();
        }
        distancias.put(nodoOrigen, 0f); // La distancia al nodo de origen es 0
        cola.add(nodoOrigen); // Agregar el nodo de origen a la cola

        // Algoritmo de Dijkstra
        while (!cola.isEmpty()) {
            NodoGrafo nodoActual = cola.poll();
            if (visitados.contains(nodoActual)) continue; // Si ya se visitó, saltarlo

            visitados.add(nodoActual);

            Arista arista = nodoActual.getListaAdyacencia().getPrimero(); // Obtener la lista de adyacencias
            while (arista != null) {
                NodoGrafo vecino = buscarNodo(arista.getDestino());
                if (vecino == null || visitados.contains(vecino)) {
                    arista = arista.getSiguiente();
                    continue;
                }

                float nuevaDistancia = distancias.get(nodoActual) + arista.getPeso();
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    anteriores.put(vecino, nodoActual); // Guardar el nodo anterior para reconstruir el camino
                    cola.add(vecino);
                }
                arista = arista.getSiguiente();
            }
        }

        // Reconstruir la ruta más corta
        if (!distancias.containsKey(nodoDestino) || distancias.get(nodoDestino) == Float.MAX_VALUE) {
            return null; // Si no se puede llegar al destino
        }

        LinkedList<String> ruta = new LinkedList<>();
        NodoGrafo nodo = nodoDestino;
        while (nodo != null) {
            ruta.addFirst(nodo.getDato().toString()); // Insertar al principio para tener la ruta en el orden correcto
            nodo = anteriores.get(nodo);
        }

        return ruta;
    }

}
