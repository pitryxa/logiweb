package logiweb.calculating;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.LinkedList;

@Getter
@Setter

public class Route {
    private Integer distance;
    private Deque<Waypoint> waypoints;

    public Route() {
        distance = Integer.MAX_VALUE;
        waypoints = new LinkedList<>();
    }

    public void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public void clear() {
        waypoints.clear();
        distance = Integer.MAX_VALUE;
    }


}
