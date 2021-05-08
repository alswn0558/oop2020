package kr.ac.kookmin.cs.oop.project.recommender.impl;


import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Song;
import kr.ac.kookmin.cs.oop.project.recommender.Recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * A song recommender that simply relies on random algorithm.
 * Recommend whatever you want to
 */
public class RandomRecommender implements Recommender{

    List<Song> randomSongList;

    public RandomRecommender() {
        randomSongList = new ArrayList<>();
    }

    @Override
    public void printStatistics(){
        Song[] songs =  RelationShip.getInstance().getSongs();
        int bound = songs.length;
        Random random = new Random();
        System.out.println("======무작위로 선정된 index=======");
        for(int i = 0; i < 30; i++) {
            int idx = random.nextInt(bound);
            System.out.println(idx);
            randomSongList.add(songs[idx]);
        }
    }

    @Override
    public void recommend() {
        System.out.println("==========추천 노래 목록==========");
        for(Song song : randomSongList)
            System.out.println(song);
    }
}
