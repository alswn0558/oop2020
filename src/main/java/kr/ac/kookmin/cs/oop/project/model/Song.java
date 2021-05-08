package kr.ac.kookmin.cs.oop.project.model;

import java.util.HashMap;

/*
 * A class to store the information about each song entry
 * It should contain all the fields that are registered in the song list file
 * You have to declare fields and way to parse comma separated string to the Song class
 */
public class Song {


    private static String[] fieldNames = {"title", "singer", "genre", "releaseYear",
            "albumName", "songWriter", "producer", "issuingCountry", "language", "playingTime"};

    private String title;
    private HashMap<String, String> songInfo;

    public Song(String[] fieldValues) {

        title = fieldValues[0];
        songInfo = new HashMap<>();

        for(int i=0; i< fieldValues.length; i++)
            songInfo.put(fieldNames[i], fieldValues[i]);
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, String> getSongInfo() {
        return songInfo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false; // 널값이거나 song 클래스가 아니면 false.

        Song song = (Song) obj;
        for(int i=0; i< fieldNames.length; i++)
            if(!songInfo.get(fieldNames[i])
                    .equals(song.getSongInfo().get(fieldNames[i])))
                return false;
        return true;
    }

    @Override
    public int hashCode() {
        return songInfo.get("title").hashCode()+songInfo.get("singer").hashCode();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(String key : fieldNames){
            stringBuilder.append(key+":" +songInfo.get(key)+" ");
        }
        return stringBuilder.toString();
    }
}
