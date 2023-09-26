package project.app.warzone.Model;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private List<Node> V;
    private List<Continent> listOfContinents;

    public Map() {
        V = new ArrayList<>();
        listOfContinents = new ArrayList<>();
    }

    public List<Node> getV() {
        return V;
    }

    public List<Continent> getListOfContinents() {
        return listOfContinents;
    }

    public void insertATerritory(Territory data) {
        V.add(new Node(data));
    }

    public void insertAndConnectTwoTerritories(Territory dataA, Territory dataB) {
        insertATerritory(dataA);
        insertATerritory(dataB);
        connectTwoNodes(V.get(V.size() - 1), V.get(V.size() - 2));
    }

    public void connectTwoNodes(Node A, Node B) {
        String edgeName = A.getData().getTerritoryName() + B.getData().getTerritoryName();
        A.addEdge(edgeName);
        B.addEdge(edgeName);
    }

    public boolean areConnected(Node A, Node B) {
        String possibleEdge1 = A.getData().getTerritoryName() + B.getData().getTerritoryName();
        String possibleEdge2 = B.getData().getTerritoryName() + A.getData().getTerritoryName();

        for (String edge : A.getE()) {
            if (edge.equals(possibleEdge1) || edge.equals(possibleEdge2)) {
                return true;
            }
        }

        return false;
    }
    
}
