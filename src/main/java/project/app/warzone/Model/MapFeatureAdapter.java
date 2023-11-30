package project.app.warzone.Model;

import project.app.warzone.Features.MapFeatures;
public class MapFeatureAdapter extends MapFeatures {
    ConquestFileReader conquestReader;

    public MapFeatureAdapter(ConquestFileReader conquestReader){
        this.conquestReader= conquestReader;

    }

    public Map readMap(String fileName)
    {
        return conquestReader.readMap(fileName);
    }
}
