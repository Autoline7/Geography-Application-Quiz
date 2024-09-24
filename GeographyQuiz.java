package edu.uga.cs1302.quiz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GeographyQuiz extends Application {

    private Stage primaryStage;
    private Stage quizStage;
    private Text feedbackText;
    private Text questionText;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private ToggleGroup answersGroup;
    private int currentQuestionIndex;
    private int totalScore;
    private QuizResult quizResult;
    private List<Question> questions;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Geography Quiz");

        // Load quiz results from disk
        quizResult = QuizResult.load();

        // Brief explanation of the purpose of the game
        Label explanationLabel = new Label("Welcome to the Geography Quiz!\n"
                + "Choose an option below to start:");
        explanationLabel.setWrapText(true);
        explanationLabel.setMaxWidth(300);

        // Create the buttons
        Button startQuizButton = new Button("Start Quiz");
        Button viewResultsButton = new Button("View Results");
        Button helpButton = new Button("Help");
        Button quitButton = new Button("Quit");

        // Set the action for the buttons
        startQuizButton.setOnAction(this::startQuizButtonHandler);
        viewResultsButton.setOnAction(this::viewResultsButtonHandler);
        helpButton.setOnAction(this::helpButtonHandler);
        quitButton.setOnAction(this::quitButtonHandler);

        // Layout the explanation and buttons in a VBox
        VBox vbox = new VBox(20); // 20 is the spacing between elements
        vbox.getChildren().addAll(explanationLabel, startQuizButton, viewResultsButton, helpButton, quitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        // Create the scene and set it on the stage
        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startQuizButtonHandler(ActionEvent event) {
        quizStage = new Stage();
        quizStage.setTitle("New Quiz");

        Quiz quiz = new Quiz();

        // Initialize the quiz components
        feedbackText = new Text("");
        questionText = new Text();
        answer1 = new RadioButton();
        answer2 = new RadioButton();
        answer3 = new RadioButton();
        answersGroup = new ToggleGroup();
        answer1.setToggleGroup(answersGroup);
        answer2.setToggleGroup(answersGroup);
        answer3.setToggleGroup(answersGroup);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(this::submitAnswerHandler);

        VBox vbox = new VBox(20, feedbackText, questionText, answer1, answer2, answer3, submitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 400, 300);
        quizStage.setScene(scene);

        quizStage.initModality(Modality.APPLICATION_MODAL);
        quizStage.initOwner(primaryStage);
        quizStage.setX(primaryStage.getX() + 200);
        quizStage.setY(primaryStage.getY() + 100);

        // Initialize quiz questions
        initializeQuestions(quiz);
        loadNextQuestion();

        quizStage.showAndWait();
    }

    public void submitAnswerHandler(ActionEvent event) {
        RadioButton selectedAnswer = (RadioButton) answersGroup.getSelectedToggle();
        if (selectedAnswer != null) {
            String answerText = selectedAnswer.getText();
            if (isCorrectAnswer(answerText)){
                feedbackText.setText("Correct!");
                totalScore++;
            } else {
                feedbackText.setText("Incorrect. Correct answer is " + questions.get(currentQuestionIndex).getCorrectAnswer());
            }

            if (currentQuestionIndex < questions.size() - 1) {
                currentQuestionIndex++;
                loadNextQuestion();
            } else {
                showFinalScore();
            }
        }
    }

    private boolean isCorrectAnswer(String answer) {
        return questions.get(currentQuestionIndex).getCorrectAnswer().equals(answer);
    }

    private void loadNextQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionText.setText(question.getQuestionText());
        String[] choices = question.getAnswerChoices();
        answer1.setText(choices[0]);
        answer2.setText(choices[1]);
        answer3.setText(choices[2]);
        answersGroup.selectToggle(null);
    }

    private void showFinalScore() {
        feedbackText.setText("Final Score: " + totalScore + "/" + questions.size());
        quizResult.addQuizScore(new QuizScore(totalScore, new Date().toString()));
        QuizResult.save(quizResult);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> quizStage.close());
        VBox vbox = new VBox(20, feedbackText, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        quizStage.getScene().setRoot(vbox);
    }

    private void initializeQuestions(Quiz quiz) {
        questions = new ArrayList<>();
        for(int i = 0; i < 6; i ++){
            questions.add(quiz.getQuestions().get(i));
        }
        currentQuestionIndex = 0;
        totalScore = 0;
    }


    public void viewResultsButtonHandler(ActionEvent event) {
        Stage resultsStage = new Stage();
        resultsStage.setTitle("Quiz Results");

        VBox resultsVBox = new VBox(10);
        resultsVBox.setPadding(new Insets(10));
        resultsVBox.setAlignment(Pos.TOP_LEFT);

        for (QuizScore score : quizResult.getScores()) {
            Label scoreLabel = new Label("Score: " + score.getQuizScore() + ", Date: " + score.getDate());
            resultsVBox.getChildren().add(scoreLabel);
        }

        ScrollPane scrollPane = new ScrollPane(resultsVBox);
        scrollPane.setFitToWidth(true);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> resultsStage.close());

        VBox vbox = new VBox(10, scrollPane, closeButton);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 400);
        resultsStage.setScene(scene);

        resultsStage.initModality(Modality.APPLICATION_MODAL);
        resultsStage.initOwner(primaryStage);
        resultsStage.setX(primaryStage.getX() + 200);
        resultsStage.setY(primaryStage.getY() + 100);

        resultsStage.showAndWait();
    }

    public void helpButtonHandler(ActionEvent event) {
            Stage helpStage = new Stage();
            helpStage.setTitle("Help");

            String helpText = "Geography Quiz Help\n\n"
                    + "Start Quiz: Click 'Start Quiz' to begin a new quiz.\n"
                    + "Select the correct continent for the given country from the three options and click 'Submit'.\n"
                    + "Click 'View Results' to see past quiz scores.\n"
                    + "Click 'Help' to see this information.\n"
                    + "Click 'Quit' to exit the application.";

            Label helpLabel = new Label(helpText);
            helpLabel.setWrapText(true);

            ScrollPane scrollPane = new ScrollPane(helpLabel);
            scrollPane.setFitToWidth(true);

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> helpStage.close());

            VBox vbox = new VBox(10, scrollPane, closeButton);
            vbox.setPadding(new Insets(10));
            vbox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vbox, 400, 300);
            helpStage.setScene(scene);

            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.initOwner(primaryStage);
            helpStage.setX(primaryStage.getX() + 200);
            helpStage.setY(primaryStage.getY() + 100);

            helpStage.showAndWait();
    }

    public void quitButtonHandler(ActionEvent event) {
        Platform.exit();
    }

    private void showModalWindow(String title, String message) {
        Label messageLabel = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());

        VBox vbox = new VBox(20, messageLabel, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 300, 200);
        Stage modalStage = new Stage();
        modalStage.setTitle(title);
        modalStage.setScene(scene);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(primaryStage);
        modalStage.setX(primaryStage.getX() + 200);
        modalStage.setY(primaryStage.getY() + 100);

        modalStage.showAndWait();
    }

    /*public void saveQuizResult(QuizResult quizResult) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("quizzes.dat"))) {
            out.writeObject(quizResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public QuizResult loadQuizResult() {
        File file = new File("quizzes.dat");
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                return (QuizResult) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new QuizResult();
    }*/

    public static void main(String[] args) {
        launch(args);
    }


}