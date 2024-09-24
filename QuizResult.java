package edu.uga.cs1302.quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class QuizResult implements Serializable {
    private ArrayList<QuizScore> scores;

    public QuizResult(){
        scores = new ArrayList<>();
    }


    public void addQuizScore(QuizScore quizScore){
        scores.add(0,quizScore);
    }
    public static void save(QuizResult quizResult) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("quizzes.dat"))) {
            out.writeObject(quizResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static QuizResult load() {
        File file = new File("quizzes.dat");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return (QuizResult) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new QuizResult();
    }






   /* public void save(QuizResult quizResult) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("quizzes.dat");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(quizResult);
        out.close();
        fileOut.close();;
    }


    public QuizResult load() throws IOException, ClassNotFoundException {
        QuizResult quizResult = null;
        FileInputStream filein = new FileInputStream("quizzes.dat");
        ObjectInputStream in = new ObjectInputStream(filein);
        quizResult = (QuizResult) in.readObject();
        in.close();
        filein.close();
        return quizResult;
    }*/


    public ArrayList<QuizScore> getScores() {
        return scores;
    }
}

