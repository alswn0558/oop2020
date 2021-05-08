package kr.ac.kookmin.cs.oop.project.model;

import java.util.ArrayList;
import java.util.List;

public class Student {


    private final String userId;
    private List<Song> mySongs;



    public Student(String userId) {
        this(userId, new ArrayList<Song>());
    }

    public Student(String userId, Song song) {
        this.userId = userId;
        mySongs = new ArrayList<>();
        this.mySongs.add(song);
    }

    public Student(String userId, List<Song> mySongs) {
        this.userId = userId;
        this.mySongs = mySongs;
    }

    public String getUserId() {
        return userId;
    }

    public List<Song> getMySongs() {
        return mySongs;
    }

    @Override
    public String toString() {
        return "id: " + userId + " mySongs: " + mySongs.toString();
    }
}
