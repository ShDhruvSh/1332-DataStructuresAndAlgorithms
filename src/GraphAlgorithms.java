import java.util.*;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Dhruv Sharma
 * @userid dsharma21
 * @GTID 903690386
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Use non-null inputs for start and graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Use a start input that exists in the graph.");
        }

        Queue<Vertex<T>> queued = new LinkedList<>();

        List<Vertex<T>> returnList = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        queued.add(start);
        visitedSet.add(start);
        returnList.add(start);

        while (queued.size() != 0) {

            Vertex<T> currVertex = queued.remove();
            for (VertexDistance<T> currVertexDistance : adjList.get(currVertex)) {
                if (!visitedSet.contains(currVertexDistance.getVertex())) {
                    visitedSet.add(currVertexDistance.getVertex());
                    queued.add(currVertexDistance.getVertex());
                    returnList.add(currVertexDistance.getVertex());
                }
            }
        }
        return returnList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Use non-null inputs for start and graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Use a start input that exists in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnList = new LinkedList<>();

        dfsHelper(start, adjList, visitedSet, returnList);

        return returnList;
    }

    /**
     * A recursive helper method for dps.
     *
     * @param start the current Vertex the graph is on
     * @param adjList the list of Vertexes in adjacent order
     * @param visitedSet Vertexes visited
     * @param returnList List of all traversed vertexes
     * @param <T> data type in graph
     */
    private static <T> void dfsHelper(Vertex<T> start, Map<Vertex<T>, List<VertexDistance<T>>> adjList,
                                      Set<Vertex<T>> visitedSet, List<Vertex<T>> returnList) {
        visitedSet.add(start);
        returnList.add(start);
        for (VertexDistance<T> currVertexDistance : adjList.get(start)) {
            if (!visitedSet.contains(currVertexDistance.getVertex())) {
                dfsHelper(currVertexDistance.getVertex(), adjList, visitedSet, returnList);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Use non-null inputs for start and graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Use a start input that exists in the graph.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Queue<VertexDistance<T>> minHeap = new PriorityQueue<>();

        for (Vertex<T> v : adjList.keySet()) {
            distanceMap.put(v, Integer.MAX_VALUE);
        }

        minHeap.add(new VertexDistance<T>(start, 0));

        while (minHeap.size() != 0 && visitedSet.size() != adjList.size()) {
            VertexDistance<T> currVertex = minHeap.remove();
            if (!visitedSet.contains(currVertex.getVertex())) {
                visitedSet.add(currVertex.getVertex());
                distanceMap.put(currVertex.getVertex(), currVertex.getDistance());
            }
            for (VertexDistance<T> adjDistance : adjList.get(currVertex.getVertex())) {
                if (!visitedSet.contains(adjDistance.getVertex())) {
                    minHeap.add(new VertexDistance<T>(adjDistance.getVertex(),
                            currVertex.getDistance() + adjDistance.getDistance()));
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Use non-null inputs for start and graph.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Use a start input that exists in the graph.");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Set<Edge<T>> edgeSet = new HashSet<>();
        Queue<Edge<T>> minHeap = new PriorityQueue<>();

        for (Edge<T> connected : graph.getEdges()) {
            if (connected.getU().equals(start)) {
                minHeap.add(connected);
            }
        }

        visitedSet.add(start);

        while (minHeap.size() != 0 && visitedSet.size() != adjList.size()) {
            Edge<T> currEdge = minHeap.remove();
            if (!visitedSet.contains(currEdge.getV())) {
                edgeSet.add(currEdge);
                edgeSet.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                visitedSet.add(currEdge.getV());
            }
            for (VertexDistance<T> adjDistance : adjList.get(currEdge.getV())) {
                if (!visitedSet.contains(adjDistance.getVertex())) {
                    minHeap.add(new Edge<>(currEdge.getV(), adjDistance.getVertex(), adjDistance.getDistance()));
                }
            }

        }
        if (edgeSet.size() < 2 * (adjList.size() - 1)) {
            return null;
        }
        return edgeSet;
    }
}