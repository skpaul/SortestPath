
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Route {
    private final List<District> nodes;
    private final List<Road> edges;
    private Set<District> settledNodes;
    private Set<District> unSettledNodes;
    private Map<District, District> predecessors;
    private Map<District, Integer> distance;

    public Route(List<District> districts, List<Road> roads) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<District>(districts);
        this.edges = new ArrayList<Road>(roads);
    }

    public void execute(District source) {
        settledNodes = new HashSet<District>();
        unSettledNodes = new HashSet<District>();
        distance = new HashMap<District, Integer>();
        predecessors = new HashMap<District, District>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            District node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(District node) {
        List<District> adjacentNodes = getNeighbors(node);
        for (District target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(District node, District target) {
        for (Road edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<District> getNeighbors(District node) {
        List<District> neighbors = new ArrayList<District>();
        for (Road edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private District getMinimum(Set<District> vertexes) {
        District minimum = null;
        for (District vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(District vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(District destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<District> getPath(District target) {
        LinkedList<District> path = new LinkedList<District>();
        District step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}