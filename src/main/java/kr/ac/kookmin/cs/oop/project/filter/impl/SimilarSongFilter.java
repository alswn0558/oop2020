package kr.ac.kookmin.cs.oop.project.filter.impl;

import kr.ac.kookmin.cs.oop.project.filter.Filter;
import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Song;
import kr.ac.kookmin.cs.oop.project.model.Student;

import java.util.*;

public class SimilarSongFilter implements Filter<Song> {


    @Override
    public HashMap<Song, Double> filter(String id, RelationShip relationShip){

        HashMap<Song, Double> songSimilarMap = new HashMap<>();
        Student student = relationShip.searchStudentById(id);
        List<Song> songList = student.getMySongs();

        for(Song song : songList) {
            HashMap<String, HashMap<CharSequence, Integer>> vectorMap
                    = relationShip.similarSongsVectorMap(song);
            Iterator<String> iterator = vectorMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                double similarity = cosineSimilarity.cosineSimilarity(vectorMap.get(song.getTitle()), vectorMap.get(key));
                if (similarity > THRESHOLD && !song.getTitle().equals(key)) {
                    songSimilarMap.put(relationShip.searchSongByTitle(key), similarity);
                }
            }
        }
        return songSimilarMap;
    }


}
