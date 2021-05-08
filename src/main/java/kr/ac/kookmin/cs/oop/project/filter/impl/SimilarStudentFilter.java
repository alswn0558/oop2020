package kr.ac.kookmin.cs.oop.project.filter.impl;

import kr.ac.kookmin.cs.oop.project.filter.CosineSimilarity;
import kr.ac.kookmin.cs.oop.project.filter.Filter;
import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Student;


import java.util.HashMap;
import java.util.Iterator;

public class SimilarStudentFilter implements Filter<Student> {




    public HashMap<Student, Double> filter(String id, RelationShip relationShip) {
        Student student = relationShip.searchStudentById(id);

        HashMap<Student, Double> studentsSimilarMap = new HashMap<>();
        HashMap<String, HashMap<CharSequence, Integer>> vectorMap
                = relationShip.similarStudentVectorMap(student);

        Iterator<String> iterator = vectorMap.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            double similarity = cosineSimilarity.cosineSimilarity(vectorMap.get(id), vectorMap.get(key));
            if (similarity > THRESHOLD && !student.getUserId().equals(key)) {
                studentsSimilarMap.put(relationShip.searchStudentById(key), similarity);
            }
        }


        return studentsSimilarMap;
    }
}

