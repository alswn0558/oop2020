package kr.ac.kookmin.cs.oop.project;

public class Message {

    private static final String INPUT_ID_COMMENT = "학생의 ID를 입력해주세요 >> ";

    private static final String INPUT_MODE_MESSAGE = "노래를 추천받을 모드 입력해주세요 >> ";

    private static final String MODE_EXPLANATION = "(1) 랜덤추천 (2) 유사한 학생들이 들은 노래 (3) 들었던 노래와 유사한 노래";

    private static final String[] ERROR_MESSAGES = {
            "정상적인 ID가 아닙니다.(예시: user+숫자)",
            "존재 하지 않는 Id 입니다.",
            "추천 모드는 [1  2  3] 중에 선택해주세요."};

    public static final int  NOT_PROPER_ID = 0;
    public static final int  NOT_EXIST_ID = 1;
    public static final int  NOT_PROPER_MODE = 2;

    private String message;

    public Message(){
        startMessage();
    }

    public void startMessage(){
        System.out.println(INPUT_ID_COMMENT);
    }

    public void modeSelectMessage(){
        System.out.println(INPUT_MODE_MESSAGE);
        System.out.println(MODE_EXPLANATION);
    }

    public void errorMessage(int err){
        switch (err) {
            case NOT_PROPER_ID: System.out.println(ERROR_MESSAGES[0]); break;
            case NOT_EXIST_ID: System.out.println(ERROR_MESSAGES[1]); break;
            case NOT_PROPER_MODE: System.out.println(ERROR_MESSAGES[2]); break;
        }
    }





}
