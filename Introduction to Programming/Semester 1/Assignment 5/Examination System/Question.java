import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Question {
    // Question Type
    String questiontype;

    // Difficulty
    int difficulty;

    // Question Text
    String questionText;

    // Answer String
    String answer;

    // Options
    String options;

    // Printing Questions
    public static String print(List<Question> questions) {

        String questionsText = "";

        int indexOfList = 1;

        while(indexOfList < questions.size() + 1) {
            Question question = questions.get(indexOfList - 1);
            questionsText += String.valueOf(indexOfList) + ") " + question.questionText + "\n" + question.options + "\n"; // + "\n";
            ++indexOfList;
        }

        return questionsText;
    }

    // Exam Answers
    public static String printWithAnswers(List<Question> questions) {
        String answersText = "";

        int indexOfList = 1;

        while(indexOfList < questions.size() + 1) {
            Question question = questions.get(indexOfList - 1);
            answersText += String.valueOf(indexOfList) + ") " + question.questionText + "\n" + question.answer + "\n"; // + "\n";
            ++indexOfList;
        }

        return answersText;
    }

    // Reordering Questions based on their difficulty.
    public static void reorderQuestions(List<Question> questions) {
        Collections.sort(questions, new Comparator<Question>() {
            
            // Difference of difficulties will be used to sort two questions.
            @Override
            public int compare(Question o1, Question o2) {
                return o1.difficulty - o2.difficulty;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
    }
}