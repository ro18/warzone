import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Territory class
class Territory {
    private String territoryName;
    private int ownerId;
    private int numberOfArmies;
    private Continent continent;

    public Territory() {}

    public Territory(String territoryName, Continent continent, int ownerId, int numberOfArmies) {
        this.territoryName = territoryName;
        this.ownerId = ownerId;
        this.numberOfArmies = numberOfArmies;
        this.continent = continent;
    }

    public Territory(String territoryName, Continent continent) {
        this.territoryName = territoryName;
        this.continent = continent;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setTerritoryName(String newTerritoryName) {
        territoryName = newTerritoryName;
    }

    public void setOwnerId(int newOwnerId) {
        ownerId = newOwnerId;
    }

    public void setNumberOfArmies(int newNumberOfArmies) {
        numberOfArmies = newNumberOfArmies;
    }

    public void setContinent(Continent newContinent) {
        continent = newContinent;
    }
}

// Continent class
class Continent {
    private String continentName;
    private int bonus;

    public Continent(String continentName) {
        this.continentName = continentName;
    }

    public Continent(String continentName, int bonus) {
        this.continentName = continentName;
        this.bonus = bonus;
    }

    public String getContinentName() {
        return continentName;
    }

    public int getBonus() {
        return bonus;
    }

    public void setContinentName(String newContinentName) {
        continentName = newContinentName;
    }

    public void setBonus(int newBonus) {
        bonus = newBonus;
    }
}

// Node class
class Node {
    private Territory data;
    private List<String> E;

    public Node() {}

    public Node(Territory data) {
        this.data = new Territory(data.getTerritoryName(), data.getContinent());
    }

    public Territory getData() {
        return data;
    }

    public Territory getDataPtr() {
        return data;
    }

    public List<String> getE() {
        return E;
    }

    public void setData(Territory data) {
        this.data = data;
    }

    public void addEdge(String edge) {
        E.add(edge);
    }
}

// Map class
class Map {
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

    public void validate() {
        boolean isErrorThrown = false;

        // Check if the map is a connected Map
        try {
            if (!DFS(V, V.get(0))) {
                throw new RuntimeException("Unconnected Map");
            }
        } catch (Exception e) {
            isErrorThrown = true;
            System.err.println(e.getMessage());
        }

        // Check if all continents are connected sub-graphs
        try {
            for (Continent continent : listOfContinents) {
                List<Node> tempListOfTerritoriesInContinent = new ArrayList<>();

                for (Node node : V) {
                    if (node.getData().getContinent() == continent) {
                        tempListOfTerritoriesInContinent.add(node);
                    }
                }

                if (!DFS(tempListOfTerritoriesInContinent, tempListOfTerritoriesInContinent.get(0))) {
                    throw new RuntimeException("Unconnected Sub-Map: " + continent.getContinentName() + " continent is not connected.");
                }

                tempListOfTerritoriesInContinent.clear();
            }
        } catch (Exception e) {
            isErrorThrown = true;
            System.err.println(e.getMessage());
        }

        // Check if each country belongs to one and only one continent
        try {
            for (Node node : V) {
                int countNumOfMatches = 0;

                for (Continent continent : listOfContinents) {
                    if (node.getData().getContinent() == continent) {
                        countNumOfMatches++;
                    }
                }

                if (countNumOfMatches != 1) {
                    throw new RuntimeException("No 1-1 associate between " + node.getData().getTerritoryName() + " and the continents.");
                }
            }
        } catch (Exception e) {
            isErrorThrown = true;
            System.err.println(e.getMessage());
        }

        if (isErrorThrown) {
            System.exit(1);
        }
    }

    public Continent createContinent(String name, int bonus) {
        Continent continent = new Continent(name, bonus);
        listOfContinents.add(continent);
        return continent;
    }

    private Stack<Node> adjList = new Stack<>();
    private List<Node> visited = new ArrayList<>();

    private boolean DFS(List<Node> MapNodes, Node startNode) {
        boolean isConnected = false;

        for (int i = 0; i < MapNodes.size(); i++) {
            if (startNode == MapNodes.get(i)) {
                MapNodes.remove(i);
            }
        }

        visited.add(startNode);

        for (String str : visited.get(visited.size() - 1).getE()) {
            for (Node node : MapNodes) {
                for (String edge : node.getE()) {
                    if (str.equals(edge)) {
                        adjList.push(node);
                    }
                }
            }
        }

        if (!adjList.isEmpty()) {
            Node temp = adjList.pop();
            return DFS(MapNodes, temp);
        } else {
           
