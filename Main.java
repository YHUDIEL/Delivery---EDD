import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Grafo grafoUbicaciones = new Grafo();
        ArbolAVL arbolDistancias = new ArbolAVL();
        ColaDinamica colaPedidos = new ColaDinamica();
        PilaDinamica pilaEntregados = new PilaDinamica();

        // Mapa para almacenar detalles de pedidos
        Map<Integer, Pedido> mapaPedidos = new HashMap<>();
        int pedidoID = 1;

        // Configuración inicial del grafo con Oaxaca de Juárez como punto de partida
        grafoUbicaciones.agregarNodo("Oaxaca de Juárez");

        // Leer archivo CSV e inicializar estructuras
        try (BufferedReader br = new BufferedReader(new FileReader("municipios_oaxaca_100.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 4 && !datos[0].equalsIgnoreCase("Municipio")) {
                    // Datos desde el CSV
                    String municipio = datos[0].replace("\"", "").trim().toLowerCase();
                    int distancia = (int) Float.parseFloat(datos[1].trim());
                    String[] colindancias = datos[2].split(",");

                    // Agregar municipio si no existe
                    if (!grafoUbicaciones.existeVertice(municipio)) {
                        grafoUbicaciones.agregarNodo(municipio);
                    }

                    // Agregar colindancias al grafo
                    for (String vecino : colindancias) {
                        vecino = vecino.trim().toLowerCase();
                        if (!grafoUbicaciones.existeVertice(vecino)) {
                            grafoUbicaciones.agregarNodo(vecino);
                        }
                        grafoUbicaciones.agregarArista(municipio, vecino, distancia / 2.0f);
                    }

                    // Insertar distancias en el árbol AVL (evita duplicados)
                    arbolDistancias.insertarSinDuplicados(distancia, municipio);

                    // Crear un pedido para cada municipio y agregarlo al mapa de pedidos
                    Pedido nuevoPedido = new Pedido(pedidoID++, "Producto_" + pedidoID, municipio);
                    mapaPedidos.put(nuevoPedido.getId(), nuevoPedido);
                    // Agregar a la cola de pedidos
                    colaPedidos.agregar(nuevoPedido);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        // Menú interactivo
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Mostrar municipios pendientes");
            System.out.println("2. Calcular ruta óptima");
            System.out.println("3. Marcar pedido como entregado");
            System.out.println("4. Mostrar pila de pedidos entregados");
            System.out.println("5. Mostrar grafo completo");
            System.out.println("6. Ordenar distancias (QuickSort)");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("Municipios pendientes:");
                    // Mostrar los pedidos y los municipios asociados
                    for (Map.Entry<Integer, Pedido> entry : mapaPedidos.entrySet()) {
                        Pedido pedido = entry.getValue();
                        String municipio = pedido.getMunicipio().toLowerCase(); // Convertir a minúsculas
                        System.out.println("ID Pedido: " + pedido.getId() + ", Producto: " + pedido.getProducto() +
                            ", Municipio: " + municipio);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el municipio de destino: ");
                    String destino = scanner.nextLine().trim().toLowerCase();

                    List<String> ruta = grafoUbicaciones.encontrarRutaMasCorta("oaxaca de juárez", destino);
                    if (ruta == null || ruta.isEmpty()) {
                        System.out.println("No existe una ruta al municipio: " + destino);
                    } else {
                        System.out.println("Ruta más óptima: " + String.join(" -> ", ruta));
                    }

                    break;

                case 3:
                    try {
                        Object pedidoEntregado = colaPedidos.eliminar();
                        System.out.println("Pedido entregado: " + pedidoEntregado);
                        pilaEntregados.push((Integer) pedidoEntregado.hashCode());
                    } catch (Exception e) {
                        System.out.println("No hay pedidos pendientes.");
                    }
                    break;

                case 4:
                    System.out.println("Pedidos entregados:");
                    try {
                        while (!pilaEntregados.estaVacia()) {
                            int id = pilaEntregados.pop();
                            System.out.println("Pedido entregado ID: " + id);
                        }
                    } catch (Exception e) {
                        System.out.println("No hay pedidos en la pila.");
                    }

                    System.out.print("Desea ver los detalles de un pedido? (s/n): ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (respuesta.equals("s")) {
                        System.out.print("Ingrese el ID del pedido: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consumir salto de línea
                        if (mapaPedidos.containsKey(id)) {
                            Pedido pedido = mapaPedidos.get(id);
                            System.out.println("Detalles del pedido:");
                            System.out.println("Producto: " + pedido.getProducto());
                            System.out.println("Lugar de entrega: " + pedido.getMunicipio());
                        } else {
                            System.out.println("El ID del pedido no existe.");
                        }

                    }
                    break;

                case 5:
                    System.out.println("Grafo completo:");
                    System.out.println(grafoUbicaciones.toString());
                    break;

                case 6:
                    System.out.println("Ordenando distancias con QuickSort...");
                    int[] distanciasArray = arbolDistancias.extraerDistancias();
                    Quicksort quicksort = new Quicksort();
                    quicksort.quicksort(distanciasArray, 0, distanciasArray.length - 1);

                    System.out.println("Distancias ordenadas (QuickSort):");
                    for (int distancia : distanciasArray) {
                        System.out.println("Distancia: " + distancia + " km");
                    }
                    break;

                case 7:
                    System.out.println("Saliendo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }
}
