package graph;

import javafx.util.Pair;

import java.util.*;

/**
 * Implementación genérica de un grafo dirigido.
 * @param <A>
 * @param <V>
 *
 * @author Mariano Córdoba
 */
public class Graph<A, V> {

    /**
     * Excepción que será lanzada cuando un vértice no se encuentre.
     */
    public static class VertexNotFoundException extends RuntimeException {

        public VertexNotFoundException(String message) {
            super(message);
        }

    }

    /**
     * Excepción que será lanzada en caso de vertices duplicados.
     */
    public static class DuplicateVertexException extends RuntimeException {

        public DuplicateVertexException(String message) {
            super(message);
        }

    }

    /**
     * Excepción que será lanzada en caso de que un arco tenga peso negativo.
     */
    public static class NegativeWeightException extends RuntimeException {

        public NegativeWeightException(String message) {
            super(message);
        }

    }

    /**
     * Esta clase representa un vértice en el grafo.
     */
    class Vertex {

        String name;
        A value;
        List<Edge> adjacents;

        // Propiedades usadas por el algoritmo de Dijkstra.
        double weight;
        Vertex from;
        Edge path;
        boolean visited;

        Vertex(String name, A value) {
            this.name = name;
            this.value = value;
            this.adjacents = new ArrayList<>();
            this.weight = Double.POSITIVE_INFINITY;
        }

    }

    /**
     * Esta clase representa un arco en el grafo.
     */
    class Edge {

        String name;
        V value;
        Vertex destination;
        double weight;

        Edge(String name, V value, Vertex destination, double weight) {
            this.name = name;
            this.value = value;
            this.destination = destination;
            this.weight = weight;
        }

    }

    /**
     * Esta clase es usada como entrada en la cola de prioridad en el algorítmo de Dijkstra.
     */
    class Path implements Comparable<Path> {

        Vertex destination;
        double weight;

        public Path(Vertex destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        public int compareTo(Path p) {
            return Double.compare(weight, p.weight);
        }

    }

    private Map<String, Vertex> vertexMap;

    /**
     * Constructor de la clase.
     */
    public Graph() {
        this.vertexMap = new HashMap<>();
    }

    /**
     * Añade un vértice al grafo.
     * @param name nombre del vértice
     * @param value valor del vértice
     * @throws DuplicateVertexException
     */
    public void addVertex(String name, A value) throws DuplicateVertexException {
        if (vertexMap.containsKey(value))
            throw new DuplicateVertexException("Vertex with value " + value.toString() + " already exists.");

        vertexMap.put(name, new Vertex(name, value));
    }

    /**
     * Añade un arco al grafo.
     * @param name nombre del arco
     * @param value valor del arco
     * @param from desde qué vértice
     * @param to hasta qué vértice
     * @param weight peso del arco
     */
    public void addEdge(String name, V value, String from, String to, double weight) {
        Vertex u = getVertex(from);
        Vertex v = getVertex(to);

        u.adjacents.add(new Edge(name, value, v, weight));
    }

    /**
     * Elimina un vértice del grafo.
     * @param value nombre del vértice a eliminar.
     * @throws VertexNotFoundException
     */
    public void removeVertex(A value) throws VertexNotFoundException  {
        if (!vertexMap.containsKey(value))
            throw new VertexNotFoundException("Vertex with value " + value.toString() + " was not found in the graph.");

        vertexMap.remove(value);
    }

    /**
     * Elimina un arco del grafo.
     * @param from desde qué vértice
     * @param to hasta que vértice
     */
    public void removeEdge(String from, String to) {
        Vertex u = getVertex(from);

        for (Edge e : u.adjacents)
            if (e.destination.name.equals(to))
                u.adjacents.remove(e);
    }

    /**
     * Obtiene el camino más corto entre dos vértices
     * @param from vértice inicial
     * @param to vértice final
     * @return una lista con los vértices y arcos que representan el camino más corto
     */
    public List<Pair<A, V>> getShortestPath(String from, String to) {
        return getShortestPath(from, to, "");
    }

    /**
     * Obtiene el camino más corto entre dos vértices evitando pasar por un vértice dado.
     * @param from vértice inicial
     * @param to vértice final
     * @param avoid vértica a evitar
     * @return una lista con los vértices y arcos que representan el camino más corto
     */
    public List<Pair<A, V>> getShortestPath(String from, String to, String avoid) {
        if (from.equals(avoid) || to.equals(avoid))
            return Collections.emptyList();

        dijkstra(from, avoid);

        Vertex v = getVertex(to);
        List<Pair<A, V>> path = new LinkedList<>();

        while (v.path != null) {
            path.add(new Pair<>(v.from.value, v.path.value));
            v = v.from;
        }

        Collections.reverse(path);

        return path;
    }

    /**
     * Implementación del algoritmo de Dijkstra para hallar el camino más corto entre dos vértices.
     * @param start vértice inicial
     * @param avoid vértice a evitar
     * @throws NegativeWeightException
     */
    private void dijkstra(String start, String avoid) throws NegativeWeightException  {
        PriorityQueue<Path> pq = new PriorityQueue<>();

        Vertex startVertex = getVertex(start);

        reset();

        pq.add(new Path(startVertex, 0.0));
        startVertex.weight = 0.0;

        int visitedVertices = 0;

        while (!pq.isEmpty() && visitedVertices < vertexMap.size()) {
            Path path = pq.remove();
            Vertex v = path.destination;

            if (v.visited) continue;

            v.visited = true;
            visitedVertices++;

            if (v.name.equals(avoid)) continue;

            for (Edge e : v.adjacents) {
                Vertex w = e.destination;

                if (e.weight < 0)
                    throw new NegativeWeightException("Negative weight not allowed.");

                if (w.weight > v.weight + e.weight) {
                    w.weight = v.weight + e.weight;
                    w.path = e;
                    w.from = v;
                    pq.add(new Path(w, w.weight));
                }
            }
        }
    }

    /**
     * Obtiene un vértice a partir de su nombre.
     * @param name el nombre del vértice.
     * @return el vértice con el nombre dado
     * @throws VertexNotFoundException
     */
    private Vertex getVertex(String name) throws VertexNotFoundException {
        Vertex v = vertexMap.get(name);

        if (v == null)
            throw new VertexNotFoundException("Vertex with name " + name + " was not found in the graph.");

        return v;
    }

    /**
     * Reinicia las propiedades usadas por el algorítmo de Dijkstra.
     */
    private void reset() {
        for (Vertex v : vertexMap.values()) {
            v.from = null;
            v.path = null;
            v.weight = Double.POSITIVE_INFINITY;
            v.visited = false;
        }
    }

}
