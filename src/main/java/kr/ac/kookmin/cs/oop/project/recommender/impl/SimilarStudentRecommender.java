package kr.ac.kookmin.cs.oop.project.recommender.impl;

import kr.ac.kookmin.cs.oop.project.filter.Filter;
import kr.ac.kookmin.cs.oop.project.filter.impl.SimilarStudentFilter;
import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Song;
import kr.ac.kookmin.cs.oop.project.model.Student;
import kr.ac.kookmin.cs.oop.project.recommender.Recommender;

import java.util.*;

/*
 * A song recommender based on the students' preference similarity
 */
public class SimilarStudentRecommender implements Recommender{

    private Set<Song> similarStudentsSongList;
    private Filter<Student> filter;
    private HashMap<Student, Double> cosineSimilarityMap;

    public SimilarStudentRecommender(String id) {
        filter = new SimilarStudentFilter();
        cosineSimilarityMap = filter.filter(id, RelationShip.getInstance());
        similarStudentsSongList = new HashSet<>();

    }

    @Override
    public void printStatistics(){
        System.out.println("======현재 유저와 비슷한 성향의 학생들=======");
        Iterator<Student> iterator = cosineSimilarityMap.keySet().iterator();
        while(iterator.hasNext()){
            Student key = iterator.next();
            System.out.println(key);
            System.out.println("유사도: "+ cosineSimilarityMap.get(key) );
        }
    }

    @Override
    public void recommend() {
        System.out.println("==========추천 노래 목록==========");
        Set<Student> similarStudentSet = cosineSimilarityMap.keySet();
        Iterator<Student> iterator = similarStudentSet.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            similarStudentsSongList.addAll(student.getMySongs());
        }

        for(Song song : similarStudentsSongList){
            System.out.println(song);
        }
    }


}
