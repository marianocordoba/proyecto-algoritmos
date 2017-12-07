package api;

import graph.Graph;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * Esta clase se encarga de cargar los archivos en un grafo para poder obtener los vuelos
 * más cortos de una manera eficiente.
 *
 * @author Mariano Córdoba
 * @author Matías Rodriguez
 */
public class FlightLoader {

    private static final String AEROPUERTOS_FILE_PATH = "data/aeropuertos.txt";
    private static final String VUELOS_FILE_PATH = "data/vuelos.txt";

    /**
     * Carga los datos de los aeropuertos y vuelos en un grafo dirigido con peso.
     * Dicho grafo será utilizado para calcular los vuelos más cortos.
     * @return un grafo con los datos de aeropuertos y vuelos
     */
    public static Graph<Aeropuerto, Vuelo> load() {
        Graph<Aeropuerto, Vuelo> graph = new Graph<>();
        Path path = Paths.get("");
        ApiVuelos api = new ApiVuelos();

        try {
            api.cargarAeropuertos(path.resolve(AEROPUERTOS_FILE_PATH).toAbsolutePath().toString());
            api.cargarVuelos(path.resolve(VUELOS_FILE_PATH).toAbsolutePath().toString());

            for (Aeropuerto a : api.getAeropuertos())
                graph.addVertex(a.getCodigo(), a);

            for (Vuelo v : api.getVuelos()) {
                v.setDuracion(calculateDuration(
                        v.getaPartida().getAjusteHorario(),
                        v.getaArribo().getAjusteHorario(),
                        v.getHsPartida(),
                        v.getPartidaAPm(),
                        v.getHsArribo(),
                        v.getArriboApm()
                ));

                graph.addEdge(
                    v.getNombre(),
                    v,
                    v.getaPartida().getCodigo(),
                    v.getaArribo().getCodigo(),
                    v.getDuracion()
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

    /**
     * Calcula la duración del vuelo (en minutos).
     * @param depAdj ajuste horario del aeropuerto de salida
     * @param arrAdj ajuste horario del aeropuerto de llegada
     * @param depTime horario de salida
     * @param depAPM AM/PM de salida
     * @param arrTime horario de llegada
     * @param arrAPM AM/PM de llegada
     * @return la duración del vuelo en minutos
     */
    private static int calculateDuration(int depAdj, int arrAdj, int depTime, char depAPM, int arrTime, char arrAPM) {
        // Se convierte la hora a formato de 24 hs.
        if (depAPM == 'P' && depTime < 1200)
            depTime += 1200;

        if (arrAPM == 'P' && arrTime < 1200)
            arrTime += 1200;

        if (arrAPM == 'A' && arrAdj > 1200)
            arrTime -= 1200;

        if (depAPM == 'A' && depTime > 1200)
            depTime -= 1200;

        depTime = toMinutes(depTime);
        arrTime = toMinutes(arrTime);

        // Se calcula la diferencia entre los husos horarios.
        int adj = toMinutes(Math.abs(depAdj) - Math.abs(arrAdj));

        int duration;
        if (depAPM == 'P' && arrAPM == 'A')
            duration = 1440 + arrTime - depTime - adj;
        else
            duration = arrTime - depTime - adj;

        /*
        Corrección de la representación.
        Con el formato usado 100 = 1 hora = 60 minutos.
        Se realizan los cálculos necesarios para que el resultado sea la duración
        en minutos con base sexagesimal.
         */
        return duration;
    }

    /**
     * Convierte un horario en formato HHmm en minutos desde las 00:00 hs.
     * @param time
     * @return el tiempo en minutos
     */
    private static int toMinutes(int time) {
        return (int) ((time - (time % 100)) * 0.6) + (time % 100);
    }

}
