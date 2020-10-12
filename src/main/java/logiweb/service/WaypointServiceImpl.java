package logiweb.service;

import logiweb.calculating.Dijkstra;
import logiweb.calculating.Node;
import logiweb.calculating.Route;
import logiweb.calculating.Waypoint;
import logiweb.converter.WaypointConverter;
import logiweb.dao.api.WaypointDao;
import logiweb.dto.*;
import logiweb.entity.enums.OperationTypeOnWaypoint;
import logiweb.service.api.CityService;
import logiweb.service.api.DistanceService;
import logiweb.service.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WaypointServiceImpl implements WaypointService {

    @Autowired
    private WaypointDao waypointDao;

    @Autowired
    private WaypointConverter waypointConverter;

    @Autowired
    private CityService cityService;

    @Autowired
    private DistanceService distanceService;

    @Override
    public List<WaypointDto> getAll() {
        return waypointConverter.toListDto(waypointDao.getAll());
    }

    @Override
    @Transactional
    public void add(WaypointDto waypointDto) {
        waypointDao.create(waypointConverter.toEntity(waypointDto));
    }

    @Override
    @Transactional
    public void delete(WaypointDto waypointDto) {
        waypointDao.delete(waypointConverter.toEntity(waypointDto));
    }

    @Override
    @Transactional
    public void edit(WaypointDto waypointDto) {
        waypointDao.update(waypointConverter.toEntity(waypointDto));
    }

    @Override
    public WaypointDto getById(int id) {
        return waypointConverter.toDto(waypointDao.getById(id));
    }

    @Override
    public List<Waypoint> getUnorderedWaypointsFromCargoes(List<CargoDto> cargoes, List<CityDto> allCities) {
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

    @Override
    public Route minRouteBetweenTwoCities(List<CityDto> allCities, List<DistanceDto> allDistances, CityDto cityFrom, CityDto cityTo) {
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

    private List<Route> minRoutesBetweenCityAndCities(List<CityDto> allCities,
                                                      List<DistanceDto> allDistances,
                                                      CityDto startCity,
                                                      List<CityDto> endCities) {
        Dijkstra.initGraph(allCities, allDistances);
        Dijkstra.calculateShortestPathFromSource(startCity);

        List<Route> routes = new ArrayList<>();

        for (CityDto endCity : endCities) {
            Node targetNode = Dijkstra.getGraph().getNodeByCity(endCity);

            Route route = new Route();
            route.setDistance(targetNode.getDistance());

            for (Node intermediateNode : targetNode.getShortestPath()) {
                route.addWaypoint(new Waypoint(intermediateNode.getCity()));
            }
            route.addWaypoint(new Waypoint(endCity));

            routes.add(route);
        }

        return routes;
    }

    @Override
    public Route minRouteByCargoes(List<CargoDto> cargoes, TruckDto truck) {
        List<CityDto> cityList = cityService.getAll();
        List<DistanceDto> distanceList = distanceService.getAll();

        List<Waypoint> unorderedWaypoints = getUnorderedWaypointsFromCargoes(cargoes, cityList);
        Deque<Waypoint> orderedWaypoints = new LinkedList<>();

        if (!initStartWaypoint(unorderedWaypoints, orderedWaypoints, cityService.getCityByNameFromList(cityList, truck.getCity()))) {
            return null;
        }

        return findBestRoute(orderedWaypoints, unorderedWaypoints, truck.getCapacity() * 1000, cityList, distanceList);

        //return getRoute(orderedWaypoints, cityList, distanceList);
    }

    private Route depthFirstSearch(Deque<Waypoint> orderedWaypoints,
                                  List<Waypoint> unorderedWaypoints,
                                  Integer maxCapacity,
                                  Route bestRoute,
                                  List<CityDto> cityList,
                                  List<DistanceDto> distanceList) {


        Set<Waypoint> nextPotentialWaypoints = getPotentialNextWaypoints(unorderedWaypoints, orderedWaypoints, maxCapacity, cityList);

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
            lastWaypoint.setSumWeight(getCurrentWeight(tmpOrderedWaypoints, cityList));
            lastWaypoint.setDistanceFromPrevWaypoint(minRouteBetweenTwoCities(cityList, distanceList, prevWaypoint.getCity(), lastWaypoint.getCity()).getDistance());

            modifyUnorderedWaypoints(newNextWaypoint, tmpUnorderedWaypoints, tmpOrderedWaypoints);

            if (tmpUnorderedWaypoints.isEmpty()) {
                Route route = getRoute(tmpOrderedWaypoints, cityList, distanceList);
                Integer distance = route.getDistance();

                if (distance < bestRoute.getDistance()) {
                    bestRoute = route;
                }

                return bestRoute;
            }

            bestRoute = depthFirstSearch(tmpOrderedWaypoints, tmpUnorderedWaypoints, maxCapacity, bestRoute, cityList, distanceList);
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

    private Route findBestRoute(Deque<Waypoint> orderedWaypoints,
                                  List<Waypoint> unorderedWaypoints,
                                  Integer maxCapacity,
                                  List<CityDto> cityList,
                                  List<DistanceDto> distanceList) {
        Route bestRoute = new Route();

        bestRoute = depthFirstSearch(
                orderedWaypoints,
                unorderedWaypoints,
                maxCapacity,
                bestRoute,
                cityList,
                distanceList
        );


        /*while (true) {
            List<Waypoint> tmpUnorderedWaypoints = new ArrayList<>(unorderedWaypoints);
            Deque<Waypoint> tmpOrderedWaypoints = new LinkedList<>(orderedWaypoints);


        }


        for (Waypoint unorderedWaypoint : unorderedWaypoints) {
            List<Waypoint> tmpUnorderedWaypoints = new ArrayList<>(unorderedWaypoints);
            Deque<Waypoint> tmpOrderedWaypoints = new LinkedList<>(orderedWaypoints);

            while (tmpUnorderedWaypoints.size() > 0) {
                Set<Waypoint> nextPotentialWaypoints = getPotentialNextWaypoints(tmpUnorderedWaypoints, tmpOrderedWaypoints, maxCapacity cityList);

//                Waypoint nextWaypoint = getWaypointWithMinDistanceToCity(
//                        cityList,
//                        distanceList,
//                        nextPotentialWaypoints,
//                        orderedWaypoints.getLast().getCity()
//                );

                for (Waypoint nextWaypoint : nextPotentialWaypoints) {
                    tmpOrderedWaypoints.add(new Waypoint(nextWaypoint));
                    tmpOrderedWaypoints.getLast().setSumWeight(getCurrentWeight(tmpOrderedWaypoints, cityList));


                    modifyUnorderedWaypoints(nextWaypoint, tmpUnorderedWaypoints, tmpOrderedWaypoints);
                }


                //Waypoint nextWaypoint = unorderedWaypoint;


            }

            Route route = getRoute(tmpOrderedWaypoints, cityList, distanceList);
            Integer distance = route.getDistance();

            if (distance < bestDistance) {
                bestDistance = distance;
                bestRoute = route;
            }
        }*/

        return bestRoute;
    }

    private Route getRoute(Deque<Waypoint> orderedWaypoints, List<CityDto> cityList, List<DistanceDto> distanceList) {
        Route route = new Route();

        Waypoint prevWaypoint = orderedWaypoints.getLast();
        Waypoint lastWaypoint = new Waypoint(orderedWaypoints.getFirst());

        if (!prevWaypoint.equals(lastWaypoint)) {
            lastWaypoint.setDistanceFromPrevWaypoint(minRouteBetweenTwoCities(
                    cityList,
                    distanceList,
                    prevWaypoint.getCity(),
                    lastWaypoint.getCity()
            ).getDistance());
            orderedWaypoints.add(lastWaypoint);
        }

        route.setDistance(getFullDistance(orderedWaypoints));
        route.setWaypoints(new LinkedList<>(orderedWaypoints));

        return route;
    }

    private void modifyUnorderedWaypoints(Waypoint nextWaypoint, List<Waypoint> unorderedWaypoints, Deque<Waypoint> orderedWaypoints) {
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
                        if (!w.getCity().getName().equals(cargo.getCityTo()) &&
                                w.getCargoes().containsKey(cargo)) {
                            iterator.remove();
                            break;
                        }
                    }
                }
            }

            if (cargoMap.isEmpty()) {
                unorderedWaypoints.remove(nextWaypoint);
                //unorderedWaypoints.
            }
        }
    }

    private boolean initStartWaypoint(List<Waypoint> unorderedWaypoints, Deque<Waypoint> orderedWaypoints, CityDto startCity) {
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
            nextWaypoint
                    .getCargoes()
                    .entrySet()
                    .removeIf(entry -> entry.getValue() == OperationTypeOnWaypoint.LOAD);
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
                                                    Deque<Waypoint> orderedWaypoints,
                                                    Integer maxCapacity,
                                                    List<CityDto> allCities) {
        Set<Waypoint> nextPotentialWaypoints = new HashSet<>();

        for (Waypoint unorderedWaypoint : unorderedWaypoints) {
            Deque<Waypoint> currentWaypointList = new LinkedList<>(orderedWaypoints);
            currentWaypointList.add(unorderedWaypoint);

            if (unorderedWaypoint.getCargoes().containsValue(OperationTypeOnWaypoint.LOAD) &&
                    getCurrentWeight(currentWaypointList, allCities) <= maxCapacity) {

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

                if(potentialWaypoint == null) {
                    continue;
                }

                currentWaypointList.add(potentialWaypoint);

                if (    entry.getValue() == OperationTypeOnWaypoint.LOAD &&
                        getWaypointByCity(
                                new LinkedList<>(orderedWaypoints).subList(count, orderedWaypoints.size()),
                                targetCity) == null &&
                        getCurrentWeight(currentWaypointList, allCities) <= maxCapacity) {

                    nextPotentialWaypoints.add(potentialWaypoint);
                }
            }

            count++;
        }

        return nextPotentialWaypoints;
    }

    private Integer getCurrentWeight(Deque<Waypoint> waypoints, List<CityDto> allCities) {
        Integer sumWeight = 0;
        int count = 1;

        for (Waypoint waypoint : waypoints) {
            for (Map.Entry<CargoDto, OperationTypeOnWaypoint> entry : waypoint.getCargoes().entrySet()) {
                OperationTypeOnWaypoint op = entry.getValue();

                if (op == OperationTypeOnWaypoint.LOAD) {
                    sumWeight += entry.getKey().getWeight();
                } else if (op == OperationTypeOnWaypoint.UNLOAD) {
                    CargoDto currentCargo = entry.getKey();
                    if (getWaypointByCity(new LinkedList<>(waypoints).subList(0, count), cityService.getCityByNameFromList(allCities, currentCargo.getCityFrom())) != null) {
                        sumWeight -= currentCargo.getWeight();
                    }
                }
            }
            count++;
        }

        return sumWeight;
    }

    private Integer getFullDistance(Deque<Waypoint> waypoints) {
        return waypoints
                .stream()
                .map(Waypoint::getDistanceFromPrevWaypoint)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Waypoint getWaypointWithMinDistanceToCity(List<CityDto> allCities,
                                                      List<DistanceDto> allDistances,
                                                      Set<Waypoint> waypoints,
                                                      CityDto cityDto) {
        Integer minDistance = Integer.MAX_VALUE;
        Waypoint waypoint = null;

        List<Route> routes = minRoutesBetweenCityAndCities(
                allCities,
                allDistances,
                cityDto,
                waypoints
                        .stream()
                        .map(Waypoint::getCity)
                        .collect(Collectors.toList()));

        for (Route route : routes) {
            Integer distance = route.getDistance();
            if (distance < minDistance) {
                minDistance = distance;
                waypoint = waypoints
                        .stream()
                        .filter(waypoint1 -> waypoint1.getCity().equals(route.getWaypoints().getLast().getCity()))
                        .collect(Collectors.toList())
                        .get(0);
                waypoint.setDistanceFromPrevWaypoint(distance);
            }
        }

        return waypoint;
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
}
