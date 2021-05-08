package kr.ac.kookmin.cs.oop.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

import kr.ac.kookmin.cs.oop.project.model.RelationShip;
import kr.ac.kookmin.cs.oop.project.model.Song;
import kr.ac.kookmin.cs.oop.project.model.Student;
import kr.ac.kookmin.cs.oop.project.recommender.Recommender;
import kr.ac.kookmin.cs.oop.project.recommender.impl.RandomRecommender;
import kr.ac.kookmin.cs.oop.project.recommender.impl.SimilarSongRecommender;
import kr.ac.kookmin.cs.oop.project.recommender.impl.SimilarStudentRecommender;

public class Main {

    private static final int STUDENT_ID_IDX = 0;
    private static final int SONG_INFO_START_IDX = 1;

    public int MODE_RANDOM = 1;
    public int MODE_SIMILAR_SONG = 2;
    public int MODE_SIMILAR_STUDENT = 3;

    // declare recommendedSongs field. It should store recommended songs for each students
    private Recommender musicRecommender;
    private List<String> recommendedSongs;
    private RelationShip relationShip;
    private Message message;

    public Main(String filePath) {
        setUpMain(filePath);
        message = new Message();
        relationShip = RelationShip.getInstance();
    }

    private static void setUpMain(String filePath){
        File inputFile = new File(filePath);

        HashMap<String,Student> studentsIds = new HashMap<>();
        List<Song> songs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"))) {
            for (String line; (line = br.readLine()) != null;) {
                String[] fieldValues = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                Song song = extractSongs(Arrays.copyOfRange(fieldValues, SONG_INFO_START_IDX, fieldValues.length));
                songs.add(song);
                extractStudentsIds(fieldValues[STUDENT_ID_IDX],studentsIds, song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        makeRelationShip(studentsIds, songs);
    }
    /*
     * From the input file path that contains information about the song
     * preference list, it should extract unique students IDs and it should
     * return it as a String array. There are 36 unique student IDs and you
     * should return only the unique IDs.
     */
    private static void makeRelationShip(HashMap<String,Student> studentsIds, List<Song> songs){
        RelationShip relationShip =RelationShip.getInstance();
        Song[] arrSongs = songs.toArray(new Song[songs.size()]);
        Student[] arrStudents = studentsIds.values().toArray(new Student[studentsIds.size()]);
        relationShip.init(arrSongs,arrStudents);
    }

    private static void extractStudentsIds(String id, HashMap<String,Student> studentsIds, Song song) {
        if(studentsIds.containsKey(id)){
            studentsIds.get(id).getMySongs().add(song);
        }else{
            studentsIds.put(id, new Student(id, song));
        }
    }

    private static Song extractSongs(String[] songInfo){
        Song song = new Song(songInfo);
        return song;
    }

    private void setMusicRecommender(String mode, String studentId){
        int selectedMode = Integer.valueOf(mode);
        if(selectedMode == MODE_RANDOM)
            musicRecommender = new RandomRecommender();
        if(selectedMode == MODE_SIMILAR_SONG)
            musicRecommender = new SimilarSongRecommender(studentId);
        if(selectedMode == MODE_SIMILAR_STUDENT)
            MODE_SIMILAR_STUDENT: musicRecommender = new SimilarStudentRecommender(studentId);

    }

    public boolean idValidation(String id){
        id.trim(); // 공백 제거.
        if(isProperId(id) && isExistedId(id)) {
            message.modeSelectMessage();
            return true;
        }
        return false;
    }

    public boolean isProperId(String id){
        if(Pattern.matches("^user[0-9]+$", id))
            return true;
        message.errorMessage(Message.NOT_PROPER_ID);
        return false;
    }

    public boolean isExistedId(String id){
        for(Student student : relationShip.getStudents()){
            if(student.getUserId().equals(id))
                return true;
        }
        message.errorMessage(Message.NOT_EXIST_ID);
        return false;
    }

    public boolean modeValidation(String mode){
        if(Pattern.matches("^[1-3]$", mode))
            return true;
        message.errorMessage(Message.NOT_PROPER_MODE);
        return false;
    }


    /*
     * A function to print out basic statistics about song and students
     */
    public void printStatistics() {
        musicRecommender.printStatistics();
    }

    /*
     * A function that calls recommend function that is implemented in different
     * classes
     */
    public void recommend() {
        musicRecommender.recommend();
    }


    public static void main(String[] args) {  //가장 먼저 실행
        Main runner = new Main("resource/song-list.csv");
        Scanner scanner = new Scanner(System.in);

        String id = scanner.nextLine();
        while(!runner.idValidation(id)){
            id = scanner.nextLine();
        }

        String mode = scanner.nextLine();
        while(!runner.modeValidation(mode)){
            mode = scanner.nextLine();
        }

        runner.setMusicRecommender(mode, id);
        runner.printStatistics();
        runner.recommend();
    }
}
