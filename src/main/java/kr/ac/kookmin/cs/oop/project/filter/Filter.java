package kr.ac.kookmin.cs.oop.project.filter;

import kr.ac.kookmin.cs.oop.project.model.RelationShip;


import java.util.HashMap;

public interface Filter<T> {

    public double THRESHOLD = 0.8;
    public CosineSimilarity cosineSimilarity = new CosineSimilarity();

    public HashMap<T, Double> filter(String id, RelationShip relationShip);

}
