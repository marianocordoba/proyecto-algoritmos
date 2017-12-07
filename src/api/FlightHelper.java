package api;

import graph.Graph;
import javafx.util.Pair;

import java.util.List;

/**
 * Esta clase brinda métodos que permiten mostrar las combinaciones de vuelos
 * más cortas entre dos aeropuertos.
 *
 * @author Mariano Córdoba
 * @author Matías Rodriguez
 */
public class FlightHelper {

    private Graph<Aeropuerto, Vuelo> graph;

    /**
     * Constructor de la clase
     * @param graph el grafo a utilizar como fuente de información
     */
    public FlightHelper(Graph<Aeropuerto, Vuelo> graph) {
        this.graph = graph;
    }

    /**
     * Imprime la combinación de vuelos más corta entre dos aeropuertos.
     * @param from aeropuerto de salida
     * @param to aeropuerto de llegada
     */
    public void printShortestFlight(String from, String to) {
        printShortestFlight(from, to, "");
    }

    /**
     * Imprime la combinación de vuelos más corta entre dos aeropuertos evitando
     * un aeropuerto (en dicho aeropuerto hay paro de aeronavegantes).
     * @param from aeropuerto de salida
     * @param to aeropuerto de llegada
     * @param avoid aeropuerto a evitar
     */
    public void printShortestFlight(String from, String to, String avoid) {
        List<Pair<Aeropuerto, Vuelo>> path = graph.getShortestPath(from, to, avoid);

        if (path.isEmpty()) {
            System.out.println("No hay vuelos disponibles.");
            return;
        }

        int duration = 0;
        System.out.println("La combinación de vuelos más corta desde " + from + " hasta " + to + " es: ");

        for (Pair<Aeropuerto, Vuelo> p : path) {
            Vuelo v = p.getValue();
            System.out.println(v.toString());
            duration += v.getDuracion();
        }

        int hours = duration / 60;
        int minutes = duration % 60;
        System.out.println("El viaje tiene una duración total de " + hours + " horas y " + minutes + " minutos.");
    }

}
