package project.app.warzone.Model;

import java.util.List;

public class Node {

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
