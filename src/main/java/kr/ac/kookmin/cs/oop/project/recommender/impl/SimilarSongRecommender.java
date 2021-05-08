package kr.ac.kookmin.cs.oop.project.recommender.impl;

import kr.ac.kookmin.cs.oop.project.filter.Filter;
import kr.ac.kookmin.cs.oop.project.filter.impl.SimilarSongFilter;
import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Song;
import kr.ac.kookmin.cs.oop.project.model.Student;
import kr.ac.kookmin.cs.oop.project.recommender.Recommender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * A song recommender based on the similarity of songs
 */
public class SimilarSongRecommender implements Recommender {


    private List<Song> studentSongList;
    private Filter<Song> filter = new SimilarSongFilter();
    private HashMap<Song, Double> cosineSimilarityMap;

    public SimilarSongRecommender(String studentId) {
        RelationShip relationShip = RelationShip.getInstance();
        Student student = relationShip.searchStudentById(studentId);
        studentSongList = student.getMySongs();
        cosineSimilarityMap = filter.filter(studentId, relationShip);
    }

    @Override
    public void printStatistics(){
        System.out.println("======학생이 들은 노래와 유사한 노래(중복 포함)=======");
        Iterator<Song> iterator = cosineSimilarityMap.keySet().iterator();
        while(iterator.hasNext()){
            Song key = iterator.next();
            System.out.println(key);
            System.out.println("유사도: "+cosineSimilarityMap.get(key) );
        }
    }

    @Override
    public void recommend() {
        Set<Song> set =  cosineSimilarityMap.keySet();
        for(Song song : studentSongList)
            set.remove(song);
        System.out.println("==========추천 노래 목록==========");
        Iterator it = set.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
