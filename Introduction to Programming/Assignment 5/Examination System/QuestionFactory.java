import org.w3c.dom.Element;

public class QuestionFactory {
    public Question createQuestion(String questiontype, Element element) {
        Question question = new Question();

        // Mostly Strings are used to have ease in printing them.
        question.questionText = "";
        question.options = "";
        question.answer = "";

        // Difficulty is sorted with other function in Question class.
        question.difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());

        /* Short-type questions. */
        if (questiontype.equals("short")) {

            /* This is the case where we do not need any options just a plain one line text.*/
            /* For this there is only one blank line after it ends. */
            question.questionText = element.getElementsByTagName("questiontext").item(0).getTextContent();

            question.options = "Answer: ____________________\n";

            String answerLine = element.getElementsByTagName("answers").item(0).getTextContent();

            String[] answersCollection = answerLine.split(",");

            question.answer = "Accepted answers: [";

            int index = 0;

            while(index < answersCollection.length) {
                question.answer += answersCollection[index];

                if(index != answersCollection.length - 1) {
                    question.answer += ", ";
                }

                index++;
            }
            question.answer += "]\n";

        }
        /* Multiple Choice Type Questions */
        else if (questiontype.equals("multichoice")) {

            /* This is the case where we need 4 options.*/
            /* In the answers, we show solutions by using '-->' by placing it before the options. */

            question.questionText = element.getElementsByTagName("questiontext").item(0).getTextContent();

            for(int i = 0; i < 4; i++) {
                question.options += "    " + (i+1) + ") " + ((Element) element.getElementsByTagName("options").item(0)).getElementsByTagName("answer").item(i).getTextContent() + "\n";
            }

            String solutions = element.getElementsByTagName("solution").item(0).getTextContent();

            String[] solutionsNumbers = solutions.split(",");

            int[] solutionsIndexes = new int[solutionsNumbers.length];

            for(int i = 0; i < solutionsNumbers.length; i++) {
                solutionsIndexes[i] = Integer.parseInt(solutionsNumbers[i]);
            }

            for (int i = 1; i < 5; i++) {
                boolean exist = false;

                for(int j = 0; j < solutionsIndexes.length; j++) {
                    if(solutionsIndexes[j] == i) {
                        exist = true;
                    }
                }
                if(exist) {
                    question.answer += " -> " + i + ") " + ((Element) element.getElementsByTagName("options").item(0)).getElementsByTagName("answer").item(i-1).getTextContent() + "\n";
                }
                else {
                    question.answer += "    " + i + ") " + (((Element) element.getElementsByTagName("options").item(0)).getElementsByTagName("answer").item(i-1).getTextContent()) + "\n";
                }
            }
        }
        /* True False Type Questions*/
        else if (questiontype.equals("truefalse")) {

            /* Here we need only two line questions and one word answer.*/

            question.questionText = element.getElementsByTagName("questiontext").item(0).getTextContent();

            question.options = "Answer: true false (circle the right answer)\n";

            question.answer = "Answer: " + element.getElementsByTagName("answer").item(0).getTextContent() + "\n";

        }
        /* Essay type Questions. */
        else {

            // Here we need to leave around 4 spaces blank for the essay answer and one space for the next line.
            // Need to leave it blank only.

            question.questionText = element.getElementsByTagName("questiontext").item(0).getTextContent();

            question.answer = element.getElementsByTagName("answer").item(0).getTextContent();

            for(int i = 0; i < 5; i++) {
                question.options += "\n";
            }

            question.answer += "\nNote: To be checked manually";
        }

        return question;
    }
}