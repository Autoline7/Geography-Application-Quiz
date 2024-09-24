package edu.uga.cs1302.quiz;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Question {
    private static final String[] continent = new String[] {"Africa","Antartica", "Asia", "Europe", "North America", "Oceania", "South America"};
    private Country country;
    private int correctAnswerIndex;
    private String[] answerChoices;
    public Question(Country country){
        this.country = country;
        this.answerChoices = new String[3];
        // Get the correct continent
        String correctContinent = country.getContinent();

        // Create a list of possible continents excluding the correct one
        ArrayList<String> possibleChoices = new ArrayList<>();
        for (String cont : continent) {
            if (!cont.equals(correctContinent)) {
                possibleChoices.add(cont);
            }
        }

        // Randomly select two wrong continents from the possible choices
        int randomIndex1 = ThreadLocalRandom.current().nextInt(possibleChoices.size());
        String wrongContinent1 = possibleChoices.remove(randomIndex1);

        int randomIndex2 = ThreadLocalRandom.current().nextInt(possibleChoices.size());
        String wrongContinent2 = possibleChoices.remove(randomIndex2);

        // Insert the correct answer at a random position in the answer choices
        correctAnswerIndex = ThreadLocalRandom.current().nextInt(3);
        answerChoices[correctAnswerIndex] = correctContinent;

        // Fill the other positions with wrong answers
        int index = 0;
        for (int i = 0; i < 3; i++) {
            if (i != correctAnswerIndex) {
                answerChoices[i] = index == 0 ? wrongContinent1 : wrongContinent2;
                index++;
            }
        }

    }
    public String[] getAnswerChoices(){
        return this.answerChoices;
    }
    public String getCorrectAnswer() {
        return this.country.getContinent();
    }
    public int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }


    @Override
    public String toString() {
        return "What is the continent does " + country +" belong to ?";
    }

    public String getQuestionText() {
        return "What is the continent of " + country.getName() + "?";
    }
}
