package logiweb.calculating;

import logiweb.dto.CargoDto;
import logiweb.dto.CityDto;
import logiweb.dto.DistanceDto;
import logiweb.dto.TruckDto;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.service.api.CityService;
import logiweb.service.api.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoutesCalc {
    @Autowired
    private CityService cityService;

    @Autowired
    private DistanceService distanceService;

    private List<CityDto> allCities = new ArrayList<>();

    private List<DistanceDto> allDistances = new ArrayList<>();

//    private int countRecursive = 0;

//    public Integer[][] getMatrixOfDistances() {
//        int size = cityService.countOfCities().intValue();
//        Integer[][] distances = new Integer[size][size];
//
//        List<DistanceDto> distanceList = distanceService.getAll();
//
//        for (DistanceDto dist : distanceList) {
//            int from = dist.getCityFrom().getId();
//            int to = dist.getCityTo().getId();
//
//            distances[from - 1][to - 1] = dist.getDistance();
//        }
//
//        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                if (distances[i][j] == null) {
//                    distances[i][j] = Integer.MAX_VALUE;
//                }
//            }
//        }
//
//        return distances;
//    }

    public Route minRouteByCargoes(List<CargoDto> cargoes, TruckDto truck) {
        allCities = cityService.getAll();
        allDistances = distanceService.getAll();

        List<Waypoint> unorderedWaypoints = getUnorderedWaypointsFromCargoes(cargoes);
        Deque<Waypoint> orderedWaypoints = new LinkedList<>();

        if (!initStartWaypoint(unorderedWaypoints, orderedWaypoints,
                               cityService.getCityByNameFromList(allCities, truck.getCity()))) {
            return null;
        }

        return findBestRoute(orderedWaypoints, unorderedWaypoints, truck.getCapacity() * 1000);
    }

    private List<Waypoint> getUnorderedWaypointsFromCargoes(List<CargoDto> cargoes) {
        Map<CityDto, Waypoint> waypoints = new HashMap<>();

        for (CargoDto cargo : cargoes) {
            Map<CityDto, OperationTypeOnWaypoint> cities = new HashMap<>();

            cities.put(cityService.getCityByNameFromList(allCities, cargo.getCityFrom()), OperationTypeOnWaypoint.LOAD);
            cities.put(cityService.getCityByNameFromList(allCities, cargo.getCityTo()), OperationTypeOnWaypoint.UNLOAD);

            for (Map.Entry<CityDto, OperationTypeOnWaypoint> city : cities.entrySet()) {
                Waypoint waypoint;

                if (waypoints.containsKey(city.getKey())) {
                    waypoint = waypoints.get(city.getKey());
                } else {
                    waypoint = new Waypoint(city.getKey());
                    waypoints.put(city.getKey(), waypoint);
                }

                waypoint.addCargo(cargo, city.getValue());
            }
        }

        List<Waypoint> waypointList = new LinkedList<>(waypoints.values());

        for (Waypoint waypoint : waypointList) {
            Integer loadWeight = 0;

            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> cargo : waypoint.getCargoes().entrySet()) {
                if (cargo.getValue() == OperationTypeOnWaypoint.LOAD) {
                    loadWeight += cargo.getKey().getWeight();
                }
            }

            waypoint.setSumWeight(loadWeight);
        }

        return waypointList;
    }

    private Route minRouteBetweenTwoCities(CityDto cityFrom, CityDto cityTo) {
        Dijkstra.initGraph(allCities, allDistances);
        Dijkstra.calculateShortestPathFromSource(cityFrom);

        Node targetNode = Dijkstra.getGraph().getNodeByCity(cityTo);

        Route route = new Route();
        route.setDistance(targetNode.getDistance());

        for (Node intermediateNode : targetNode.getShortestPath()) {
            route.addWaypoint(new Waypoint(intermediateNode.getCity()));
        }
        route.addWaypoint(new Waypoint(cityTo));

        return route;
    }


    private Route depthFirstSearch(Deque<Waypoint> orderedWaypoints, List<Waypoint> unorderedWaypoints,
                                          Integer maxCapacity, Route bestRoute) {

//        System.out.println(++countRecursive);

        Set<Waypoint> nextPotentialWaypoints =
                getPotentialNextWaypoints(unorderedWaypoints, orderedWaypoints, maxCapacity);

        if (nextPotentialWaypoints.isEmpty()) {
            return bestRoute;
        }

        for (Waypoint nextWaypoint : nextPotentialWaypoints) {
            List<Waypoint> tmpUnorderedWaypoints = getNewWaypointList((LinkedList<Waypoint>) unorderedWaypoints);
            Deque<Waypoint> tmpOrderedWaypoints = getNewWaypointList((LinkedList<Waypoint>) orderedWaypoints);
            Waypoint newNextWaypoint = new Waypoint(nextWaypoint);

            Waypoint prevWaypoint = tmpOrderedWaypoints.getLast();
            tmpOrderedWaypoints.add(nextWaypoint);
            Waypoint lastWaypoint = tmpOrderedWaypoints.getLast();
            lastWaypoint.setSumWeight(getCurrentWeight(tmpOrderedWaypoints));
            lastWaypoint.setDistanceFromPrevWaypoint(
                    minRouteBetweenTwoCities(prevWaypoint.getCity(), lastWaypoint.getCity()).getDistance());

            modifyUnorderedWaypoints(newNextWaypoint, tmpUnorderedWaypoints, tmpOrderedWaypoints);

            if (tmpUnorderedWaypoints.isEmpty()) {
                Route route = getRoute(tmpOrderedWaypoints);
                Integer distance = route.getDistance();

                if (distance < bestRoute.getDistance()) {
                    bestRoute = route;
                }

                return bestRoute;
            }

            bestRoute = depthFirstSearch(tmpOrderedWaypoints, tmpUnorderedWaypoints, maxCapacity, bestRoute);
        }

        return bestRoute;
    }

    private LinkedList<Waypoint> getNewWaypointList(LinkedList<Waypoint> waypoints) {
        LinkedList<Waypoint> collection = new LinkedList<>();

        for (Waypoint waypoint : waypoints) {
            collection.add(new Waypoint(waypoint));
        }

        return collection;
    }

    private Route findBestRoute(Deque<Waypoint> orderedWaypoints, List<Waypoint> unorderedWaypoints,
                                       Integer maxCapacity) {
        Route bestRoute = new Route();

        bestRoute = depthFirstSearch(orderedWaypoints, unorderedWaypoints, maxCapacity, bestRoute);

        return bestRoute;
    }

    private Route getRoute(Deque<Waypoint> orderedWaypoints) {
        Route route = new Route();

        Waypoint prevWaypoint = orderedWaypoints.getLast();
        Waypoint lastWaypoint = new Waypoint(orderedWaypoints.getFirst());

        if (!prevWaypoint.equals(lastWaypoint)) {
            lastWaypoint.setDistanceFromPrevWaypoint(
                    minRouteBetweenTwoCities(prevWaypoint.getCity(), lastWaypoint.getCity()).getDistance());
            lastWaypoint.clearCargoes();
            orderedWaypoints.add(lastWaypoint);
        }

        Deque<Waypoint> waypointsForRoute = getWaypointsForRoute(orderedWaypoints);

        route.setDistance(getFullDistance(orderedWaypoints));
        route.setWaypoints(new LinkedList<>(waypointsForRoute));
        //route.getWaypoints().stream().peek(Waypoint::sortCargoes);

        return route;
    }

    private Deque<Waypoint> getWaypointsForRoute(Deque<Waypoint> orderedWaypoints) {
        List<CargoDto> loadCargoes = new ArrayList<>();
        List<CargoDto> deliveredCargoes = new ArrayList<>();

        Deque<Waypoint> waypointsForRoute = new LinkedList<>(orderedWaypoints);

        for (Waypoint w : waypointsForRoute) {
            Iterator<Map.Entry<CargoDto, OperationTypeOnWaypoint>> iterator = w.getCargoes().entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<CargoDto, OperationTypeOnWaypoint> entry = iterator.next();

                OperationTypeOnWaypoint op = entry.getValue();

                if (deliveredCargoes.contains(entry.getKey())) {
                    iterator.remove();
                } else {
                    if (op == OperationTypeOnWaypoint.LOAD) {
                        loadCargoes.add(entry.getKey());
                    } else if (op == OperationTypeOnWaypoint.UNLOAD) {
                        if (loadCargoes.contains(entry.getKey())) {
                            deliveredCargoes.add(entry.getKey());
                        } else {
                            iterator.remove();
                        }
                    }
                }
            }

            w.sortCargoes();
        }

        return waypointsForRoute;


    }

    private void modifyUnorderedWaypoints(Waypoint nextWaypoint, List<Waypoint> unorderedWaypoints,
                                                 Deque<Waypoint> orderedWaypoints) {
        if (!nextWaypoint.getCargoes().containsValue(OperationTypeOnWaypoint.UNLOAD)) {
            unorderedWaypoints.remove(nextWaypoint);
        } else {
            Map<CargoDto, OperationTypeOnWaypoint> cargoMap = nextWaypoint.getCargoes();
            Iterator<Map.Entry<CargoDto, OperationTypeOnWaypoint>> iterator = cargoMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<CargoDto, OperationTypeOnWaypoint> entry = iterator.next();

                OperationTypeOnWaypoint op = entry.getValue();

                if (op == OperationTypeOnWaypoint.LOAD) {
                    iterator.remove();
                } else if (op == OperationTypeOnWaypoint.UNLOAD) {
                    CargoDto cargo = entry.getKey();

                    for (Waypoint w : orderedWaypoints) {
                        if (!w.getCity().getName().equals(cargo.getCityTo()) && w.getCargoes().containsKey(cargo)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }

            if (cargoMap.isEmpty()) {
                unorderedWaypoints.remove(nextWaypoint);
            }
        }
    }

    private boolean initStartWaypoint(List<Waypoint> unorderedWaypoints, Deque<Waypoint> orderedWaypoints,
                                             CityDto startCity) {
        Waypoint nextWaypoint = getWaypointByCity(unorderedWaypoints, startCity);
        if (nextWaypoint != null) {
            Integer sumWeight = sumWeightLoadCargoes(nextWaypoint.getCargoes());
            orderedWaypoints.add(new Waypoint(nextWaypoint, 0, sumWeight));
        } else {
            return false;
        }

        if (!nextWaypoint.getCargoes().containsValue(OperationTypeOnWaypoint.UNLOAD)) {
            unorderedWaypoints.remove(nextWaypoint);
        } else {
            nextWaypoint.getCargoes().entrySet().removeIf(entry -> entry.getValue() == OperationTypeOnWaypoint.LOAD);
        }

        return true;
    }

    private Integer sumWeightLoadCargoes(Map<CargoDto, OperationTypeOnWaypoint> cargoes) {
        Integer sumWeight = 0;

        for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : cargoes.entrySet()) {
            if (entry.getValue() == OperationTypeOnWaypoint.LOAD) {
                sumWeight += entry.getKey().getWeight();
            }
        }

        return sumWeight;
    }

    private Set<Waypoint> getPotentialNextWaypoints(List<Waypoint> unorderedWaypoints,
                                                           Deque<Waypoint> orderedWaypoints, Integer maxCapacity) {
        Set<Waypoint> nextPotentialWaypoints = new HashSet<>();

        for (Waypoint unorderedWaypoint : unorderedWaypoints) {
            if (unorderedWaypoint.getCity().getId().equals(orderedWaypoints.getLast().getCity().getId())) {
                continue;
            }

            Deque<Waypoint> currentWaypointList = new LinkedList<>(orderedWaypoints);
            currentWaypointList.add(unorderedWaypoint);

            if (unorderedWaypoint.getCargoes().containsValue(OperationTypeOnWaypoint.LOAD) &&
                getCurrentWeight(currentWaypointList) <= maxCapacity) {

                nextPotentialWaypoints.add(unorderedWaypoint);
            }
        }

        int count = 0;
        for (Waypoint orderedWaypoint : orderedWaypoints) {
            Map<CargoDto, OperationTypeOnWaypoint> cargoesInWaypoint = orderedWaypoint.getCargoes();

            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : cargoesInWaypoint.entrySet()) {
                CityDto targetCity = cityService.getCityByNameFromList(allCities, entry.getKey().getCityTo());
                Deque<Waypoint> currentWaypointList = new LinkedList<>(orderedWaypoints);
                Waypoint potentialWaypoint = getWaypointByCity(unorderedWaypoints, targetCity);

                if (potentialWaypoint == null) {
                    continue;
                }

                currentWaypointList.add(potentialWaypoint);

                if (entry.getValue() == OperationTypeOnWaypoint.LOAD &&
                    getWaypointByCity(new LinkedList<>(orderedWaypoints).subList(count, orderedWaypoints.size()),
                                      targetCity) == null &&
                    getCurrentWeight(currentWaypointList) <= maxCapacity) {

                    nextPotentialWaypoints.add(potentialWaypoint);
                }
            }

            count++;
        }

        return nextPotentialWaypoints;
    }

    private Integer getCurrentWeight(Deque<Waypoint> waypoints) {
        Integer sumWeight = 0;
        int count = 1;

        for (Waypoint waypoint : waypoints) {
            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : waypoint.getCargoes().entrySet()) {
                OperationTypeOnWaypoint op = entry.getValue();

                if (op == OperationTypeOnWaypoint.LOAD) {
                    sumWeight += entry.getKey().getWeight();
                } else if (op == OperationTypeOnWaypoint.UNLOAD) {
                    CargoDto currentCargo = entry.getKey();
                    if (getWaypointByCity(new LinkedList<>(waypoints).subList(0, count),
                                          cityService.getCityByNameFromList(allCities, currentCargo.getCityFrom())) !=
                        null) {
                        sumWeight -= currentCargo.getWeight();
                    }
                }
            }
            count++;
        }

        return sumWeight;
    }

    private Integer getFullDistance(Deque<Waypoint> waypoints) {
        return waypoints.stream().map(Waypoint::getDistanceFromPrevWaypoint).mapToInt(Integer::intValue).sum();
    }

    private Waypoint getWaypointByCity(List<Waypoint> waypoints, CityDto city) {
        if (city == null || waypoints == null) {
            return null;
        }

        Waypoint findWaypoint = null;

        for (Waypoint waypoint : waypoints) {
            if (waypoint.getCity().getId().equals(city.getId())) {
                findWaypoint = waypoint;
                break;
            }
        }

        return findWaypoint;
    }

//    private List<Route> minRoutesBetweenCityAndCities(CityDto startCity, List<CityDto> endCities) {
//        Dijkstra.initGraph(allCities, allDistances);
//        Dijkstra.calculateShortestPathFromSource(startCity);
//
//        List<Route> routes = new ArrayList<>();
//
//        for (CityDto endCity : endCities) {
//            Node targetNode = Dijkstra.getGraph().getNodeByCity(endCity);
//
//            Route route = new Route();
//            route.setDistance(targetNode.getDistance());
//
//            for (Node intermediateNode : targetNode.getShortestPath()) {
//                route.addWaypoint(new Waypoint(intermediateNode.getCity()));
//            }
//            route.addWaypoint(new Waypoint(endCity));
//
//            routes.add(route);
//        }
//
//        return routes;
//    }

//    private Waypoint getWaypointWithMinDistanceToCity(Set<Waypoint> waypoints, CityDto cityDto) {
//        Integer minDistance = Integer.MAX_VALUE;
//        Waypoint waypoint = null;
//
//        List<Route> routes = minRoutesBetweenCityAndCities(cityDto, waypoints.stream()
//                                                                             .map(Waypoint::getCity)
//                                                                             .collect(Collectors.toList()));
//
//        for (Route route : routes) {
//            Integer distance = route.getDistance();
//            if (distance < minDistance) {
//                minDistance = distance;
//                waypoint = waypoints.stream()
//                                    .filter(waypoint1 -> waypoint1.getCity()
//                                                                  .equals(route.getWaypoints().getLast().getCity()))
//                                    .collect(Collectors.toList())
//                                    .get(0);
//                waypoint.setDistanceFromPrevWaypoint(distance);
//            }
//        }
//
//        return waypoint;
//    }
}
