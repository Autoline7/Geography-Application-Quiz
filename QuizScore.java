package edu.uga.cs1302.quiz;
import java.util.Date;
import java.text.SimpleDateFormat;

public class QuizScore {
    private String date;
    private int quizScore;


    public QuizScore(int quizScore, String date) {
        this.quizScore = quizScore;
        this.date = date;

    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(date);
        return "Quiz Score: " + getQuizScore() + "Date taken: " + formattedDate;
    }
}
