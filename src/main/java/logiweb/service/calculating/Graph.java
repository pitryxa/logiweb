package logiweb.service.calculating;

import logiweb.dto.CityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Graph {
    private Map<Integer, Node> nodes = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getCity().getId(), node);
    }

    public void addNode(Integer id, Node node) {
        nodes.put(id, node);
    }

    public Node getNodeByCity(CityDto city){
        return nodes.get(city.getId());
    }
}
