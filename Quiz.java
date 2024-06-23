import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

public class Quiz {
    private ArrayList<Question> questions;
    private int score;
    private Scanner scanner;
    private Timer timer;
    private boolean timeUp;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
        scanner = new Scanner(System.in);
        timer = new Timer();
        timeUp = false;
        loadQuestions();
    }

    private void loadQuestions() {
        questions.add(new Question("What is the capital of France?",
                new String[]{"1. Berlin", "2. Madrid", "3. Paris", "4. Rome"}, 3));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                new String[]{"1. Harper Lee", "2. J.K. Rowling", "3. Mark Twain", "4. Jane Austen"}, 1));
        questions.add(new Question("What is the largest planet in our solar system?",
                new String[]{"1. Earth", "2. Jupiter", "3. Mars", "4. Saturn"}, 2));
        questions.add(new Question("Which element has the chemical symbol 'O'?",
                new String[]{"1. Oxygen", "2. Gold", "3. Silver", "4. Iron"}, 1));
        questions.add(new Question("Who painted the Mona Lisa?",
                new String[]{"1. Vincent van Gogh", "2. Pablo Picasso", "3. Leonardo da Vinci", "4. Claude Monet"}, 3));
    }

    public void startQuiz() {
        for (int i = 0; i < questions.size(); i++) {
            askQuestion(i);
        }
        displayResults();
    }

    private void askQuestion(int index) {
        Question question = questions.get(index);
        System.out.println(question.question);
        for (String option : question.options) {
            System.out.println(option);
        }

        timeUp = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
                System.out.println("\nTime is up!");
            }
        }, 10000);  // 10 seconds timer

        int userAnswer = -1;
        while (!timeUp && (userAnswer < 1 || userAnswer > 4)) {
            System.out.print("Enter your choice (1-4): ");
            if (scanner.hasNextInt()) {
                userAnswer = scanner.nextInt();
            } else {
                scanner.next();  // clear the invalid input
            }
        }
        timer.cancel();  // Cancel the timer if answered in time
        timer = new Timer();  // Reset the timer for next question

        if (userAnswer == question.correctAnswer) {
            score++;
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz finished!");
        System.out.println("Your score: " + score + "/" + questions.size());
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("\nQ: " + question.question);
            for (String option : question.options) {
                System.out.println(option);
            }
            System.out.println("Correct Answer: " + question.options[question.correctAnswer - 1]);
        }
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}
