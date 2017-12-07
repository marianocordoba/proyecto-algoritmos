import api.FlightHelper;
import api.FlightLoader;

/**
 * Trabajo práctico de Estructuras de Datos y Algoritmos - Algoritmos I
 *
 * @author Mariano Córdoba
 * @author Matías Rodriguez
 */
public class Main {

    public static void main(String[] args) {
        FlightHelper flightHelper = new FlightHelper(FlightLoader.load());

        System.out.println("Prueba desde ABQ a ATL:");
        flightHelper.printShortestFlight("ABQ", "ATL");

        System.out.println();

        System.out.println("Prueba desde BOS a LAX:");
        flightHelper.printShortestFlight("BOS", "LAX");

        System.out.println();

        System.out.println("Prueba desde BOS a LAX evitando DTW:");
        flightHelper.printShortestFlight("BOS", "LAX", "DTW");

        System.out.println();

        System.out.println("Prueba desde JFK a LAX:");
        flightHelper.printShortestFlight("JFK", "LAX");

        System.out.println();

        System.out.println("Prueba desde BOS a DCA:");
        flightHelper.printShortestFlight("BOS", "DCA");
    }

}
