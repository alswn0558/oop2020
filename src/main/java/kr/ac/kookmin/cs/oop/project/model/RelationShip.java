package kr.ac.kookmin.cs.oop.project.model;

import java.util.*;


public class RelationShip {
    /*
     *  고전적방식의 singleton class
     *  songs, students Main Class 에서 파일을 읽어온 후 1회 초기화 이후 read only
     *  filter 에게 추천을 위한 적절한 관계를 제공합니다.
     */

    private static RelationShip instance;
    private static String[] songsVectorColNames = {"singer", "genre", "releaseYear", "issuingCountry", "language"};
    private static String[] studentVectorColNames = {"singer", "genre", "language"};

    private Song[] songs = null; // 모든 노래
    private Student[] students = null; // userID : 해당 유저가 들은 노래.

    private RelationShip(){
    }

    public static synchronized RelationShip getInstance(){
        if (instance == null) {
            instance = new RelationShip();
        }
        return instance;
    }

    public void init(Song[] songs, Student[] students){
        if(this.songs == null && this.students == null ){
            this.songs = songs;
            this.students = students;
        }
    }

    public HashMap<String, HashMap<CharSequence, Integer>> similarSongsVectorMap(Song targetSong){
        HashMap<String, HashMap<CharSequence, Integer>> vectorMap
                = new HashMap<String, HashMap<CharSequence, Integer>>();

        for(Song song : songs) {
            HashMap<CharSequence, Integer> songVector = createSongVector(targetSong, song);
            vectorMap.put(song.getTitle(), songVector);
        }
        return vectorMap;
    }


    private HashMap<CharSequence, Integer> createSongVector(Song targetSong, Song song) {

        HashMap<String, String> songInfo = song.getSongInfo();
        HashMap<CharSequence, Integer> songVector = new HashMap<CharSequence, Integer>(); // 곡 1개의 vector

        if(song.equals(targetSong)){
            for (String key : songsVectorColNames)
                songVector.put(songInfo.get(key), 1);
        }else{
            for (String key : songsVectorColNames) {
                if(targetSong.getSongInfo().get(key).equals(songInfo.get(key)))
                    songVector.put(songInfo.get(key), 1);
                else
                    songVector.put(songInfo.get(key), 0);
            }
        }
        return songVector;
    }

    public HashMap<String, HashMap<CharSequence, Integer>> similarStudentVectorMap(Student targetStudent){
        HashMap<String, HashMap<CharSequence, Integer>> vectorMap
                = new HashMap<String, HashMap<CharSequence, Integer>>();

        for(Student student : students) {
            HashMap<CharSequence, Integer> studentVector = createStudentVector(targetStudent, student);
            vectorMap.put(student.getUserId(), studentVector);
        }
        return vectorMap;
    }

    private HashMap<CharSequence, Integer> createStudentVector(Student targetStudent, Student student) {

        HashMap<String, String> targetStudentPreference = createStudentPreference(targetStudent.getUserId());
        HashMap<String, String> studentPreference = createStudentPreference(student.getUserId());

        HashMap<CharSequence, Integer> studentVector = new HashMap<CharSequence, Integer>(); // 곡 1개의 vector

        if(student.getUserId().equals(targetStudent.getUserId())){
            for (String key : studentVectorColNames)
                studentVector.put(key, 1);
        }else{
            for (String key : studentVectorColNames) {
                if(targetStudentPreference.get(key).equals(studentPreference.get(key)))
                    studentVector.put(key, 1);
                else
                    studentVector.put(key, 0);
            }
        }
        return studentVector;
    }

    private HashMap<String, String> createStudentPreference(String id){
        HashMap<String, String> studentPreference= new HashMap<>();

        Student student = searchStudentById(id);

        for(String colName : studentVectorColNames) {
            HashMap<String, Integer> cntMap = calCntMap(student, colName);
            studentPreference.put(colName, maxKey(cntMap));
        }
        return studentPreference;
    }

    private HashMap<String, Integer> calCntMap(Student student, String colName) {
        HashMap<String, Integer> map = new HashMap<>();

        for(Song song : student.getMySongs()){
            String key = song.getSongInfo().get(colName);
            if(map.containsKey(key))
                map.put(key, map.get(key)+1);
            else
                map.put(key,1);
        }
        return map;
    }

    private String maxKey(HashMap<String, Integer> map) {
        Iterator<String> it = map.keySet().iterator();
        String maxKey = "";
        int cnt = 0;

        while(it.hasNext()) {
            String key = it.next();

            if(map.get(key) > cnt) {
                cnt = map.get(key);
                maxKey = key;
            }
        }
        if(cnt==1)
            maxKey = "all";
        return maxKey;
    }

    public Song[] getSongs() {
        return songs;
    }

    public Student[] getStudents() {
        return students;
    }

    public Song searchSongByTitle(String title){
        for(Song song : songs) {
            if (song.getTitle().equals(title))
                return song;
        }
        return null;
    }

    public Student searchStudentById(String id){
        for(Student student : students) {
            if (student.getUserId().equals(id))
                return student;
        }
        return null;
    }
}