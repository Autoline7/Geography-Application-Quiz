package edu.uga.cs1302.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import java.util.concurrent.ThreadLocalRandom;

public class Quiz {
    private int totalScore;
    private List<Question> questions;
    public Quiz(){
        this.totalScore = 0;
        CountryCollection c = new CountryCollection();
        Set<Integer> uniqueValues = new HashSet<>();

        while (uniqueValues.size() < 6) {
            int randomValue = ThreadLocalRandom.current().nextInt(c.getCountryList().size());
            uniqueValues.add(randomValue);
        }
        questions = new ArrayList<>();
        for(int i : uniqueValues) {
            questions.add(new Question(c.getCountryList().get(i)));
        }
    }


    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<Question> getQuestions(){
        return this.questions;
    }

    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i < 6; i ++){
            str += questions.get(i).toString() + "\n";
        }
        return str;
    }

}
