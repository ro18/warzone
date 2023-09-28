package project.app.warzone.Model;

import java.util.List;

public class Node {

    private Territory data;
    //private List<String> Edges; // trying with keeping Edges as Nodes only
    private List<Node> borderNodes;
    // public Node() {}

    public Node(Territory data) {
        this.data = data;
    }

    public Territory getData() {
        return data;
    }

    public Territory getDataPtr() {
        return data;
    }

    // public List<String> getE() { // testing something commented out
    //     return Edges;
    // }

    public List<Node> getBorders() { 
        return borderNodes;
    }

    public void setData(Territory data) {
        this.data = data;
    }

    // public void addEdge(String edge) { // testing
    //     Edges.add(edge);
    // }

    public void addBorderTerritories(List<Node> borderCountries){
        borderNodes=borderCountries;
    }
    
}
