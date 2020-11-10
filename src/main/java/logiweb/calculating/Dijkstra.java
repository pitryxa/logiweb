package logiweb.calculating;

import logiweb.dto.CityDto;
import logiweb.dto.DistanceDto;
import logiweb.service.api.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Dijkstra {

    private static Map<Integer, Node> nodes;
    private static Graph graph;

    public static Graph getGraph() {
        return graph;
    }

    public static void calculateShortestPathFromSource(CityDto city){
        Node source = nodes.get(city.getId());

        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0){
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;

        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();

        if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static void initGraph(List<CityDto> cities, List<DistanceDto> distances) {
        graph = new Graph();
        nodes = new HashMap();

        for (CityDto city : cities) {
            nodes.put(city.getId(), new Node(city));
        }

        for (DistanceDto distance : distances) {
            String cityFrom = distance.getCityFrom();
            String cityTo = distance.getCityTo();

            int idFrom = 0;
            int idTo = 0;

            for (CityDto city : cities) {
                if (city.getName().equals(cityFrom)) {
                    idFrom = city.getId();
                }
                if (city.getName().equals(cityTo)) {
                    idTo = city.getId();
                }
                if (idFrom > 0 && idTo > 0) {
                    break;
                }
            }

            nodes.get(idFrom).addDestination(nodes.get(idTo), distance.getDistance());
        }

        for (Map.Entry<Integer, Node> node : nodes.entrySet()) {
            graph.addNode(node.getKey(), node.getValue());
        }
    }
}
