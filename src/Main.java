import api.FlightHelper;
import api.FlightLoader;

/**
 * Trabajo práctico de Estructuras de Datos y Algoritmos - Algoritmos I
 *
 * @author Mariano Córdoba
 */
public class Main {

    public static void main(String[] args) {
        FlightHelper flightHelper = new FlightHelper(FlightLoader.load());

        System.out.println("Prueba desde TPA a DCA:");
        flightHelper.printShortestFlight("TPA", "DCA");

        System.out.println();

        System.out.println("Prueba desde TPA a DCA evitando BOS:");
        flightHelper.printShortestFlight("TPA", "DCA", "BOS");

        System.out.println();

        System.out.println("Prueba desde SEA a DCA:");
        flightHelper.printShortestFlight("SEA", "DCA");

        System.out.println();

        System.out.println("Prueba desde SEA a DCA evitando MSP:");
        flightHelper.printShortestFlight("SEA", "DCA", "MSP");

        System.out.println();

        System.out.println("Prueba desde PHL a RDU:");
        flightHelper.printShortestFlight("PHL", "RDU");

        System.out.println();

        System.out.println("Prueba desde PHL a RDU evitando BOS:");
        flightHelper.printShortestFlight("PHL", "RDU", "BOS");
    }

}
