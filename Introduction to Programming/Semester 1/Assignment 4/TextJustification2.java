/* Tanmay Sharma - t.sharma@innopolis.university */
/* Final submission of Text Justification. */
import java.io.*;
import java.util.*;

public class TextJustification2 {

    // If file is empty then FileIsEmptyException is thrown.
    static class FileIsEmptyException extends Exception
    {
        public FileIsEmptyException() {}

        public FileIsEmptyException(String message)
        {
            super(message);
        }
    }

    // If Text Size is more than 300 characters, then ExceedTextSizeException is thrown.
    static class ExceedTextSizeException extends Exception
    {
        public ExceedTextSizeException() {}

        public ExceedTextSizeException(String message)
        {
            super(message);
        }
    }

    // If indentation width is not specified, then IntendationException is thrown.
    static class IntendationException extends Exception
    {
        public IntendationException() {}

        public IntendationException(String message)
        {
            super(message);
        }
    }

    // If length of a word is negative or zero, then LineWidthException is thrown.
    static class LineWidthException extends Exception
    {
        public LineWidthException() {}

        public LineWidthException(String message)
        {
            super(message);
        }
    }

    // If the line contains an empty word, then EmptyWordException is thrown.
    static class EmptyWordException extends Exception
    {
        public EmptyWordException() {}

        public EmptyWordException(String message)
        {
            super(message);
        }
    }

    // If the length of a word is more than 20, then ExceedingWordLimitException is thrown.
    static class ExceedingWordLimitException extends Exception
    {
        public ExceedingWordLimitException() {}

        public ExceedingWordLimitException(String message)
        {
            super(message);
        }
    }

    // If the length of a word is more than intendation limit, then ExceedingMaxWidthException is thrown.
    static class ExceedingMaxWidthException extends Exception
    {
        public ExceedingMaxWidthException() {}

        public ExceedingMaxWidthException(String message)
        {
            super(message);
        }
    }

    // If the text contains a symbol, which is forbidden according to the task, then ForbiddenSymbolException is thrown.
    static class ForbiddenSymbolException extends Exception
    {
        public ForbiddenSymbolException() {}

        public ForbiddenSymbolException(String message)
        {
            super(message);
        }
    }

    
    // Ceil Function --- used for spaces.
    public static int ceilFunction(int a, int b) {
        if (b == 0) {
            return 0;
        }
        return (a + b - 1) / b;
    }

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");

            FileReader fr = new FileReader(file);

            FileWriter fw = new FileWriter("output.txt");

            BufferedReader br = new BufferedReader(fr);

            String[] inputs = new String[2];

            String line;

            int a = 0;

            while ((line = br.readLine()) != null) {
                inputs[a] = line;
                a++;
            }

            // File is empty.
            if(file.length() == 0) {
                fw.write("Exception, file is empty!");
                fr.close();
                fw.close();
                throw new FileIsEmptyException();
            }

            if(inputs[0].length() > 300) {
                fw.write("Exception, input exceeds text max size!");
                fr.close();
                fw.close();
                throw new ExceedTextSizeException();
            }

            if(inputs[1] == null) {
                fw.write("Exception, intended line width is not specified!");
                fr.close();
                fw.close();
                throw new IntendationException();
            }

            int maxWidth = Integer.parseInt(inputs[1]);

            if(maxWidth <= 0) {
                fw.write("Exception, line width cannot be negative or zero!");
                fr.close();
                fw.close();
                throw new LineWidthException();
            }

            String[] arrayOfWords = inputs[0].split("\\s+");

            Character[] symbols = new Character[] {'.',',','!','?','-',':',';','(',')','"'};
            List<Character> allowedSymbols = Arrays.asList(symbols);

            for(int i = 0; i < arrayOfWords.length; i++) {

                char[] wordCharacters = arrayOfWords[i].toCharArray();

                if(arrayOfWords[i].length() == 0) {
                    fw.write("Exception, input contains an empty word!");
                    fr.close();
                    fw.close();
                    throw new EmptyWordException();
                }

                for(int j = 0; j < wordCharacters.length; j++) {
                    char c = wordCharacters[j];
                    if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || allowedSymbols.contains(c)) {
                        continue;
                    }
                    else {
                        fw.write("Exception, input contains forbidden symbol '" + wordCharacters[j] + "'!");
                        fr.close();
                        fw.close();
                        throw new ForbiddenSymbolException();
                    }
                }

                if(arrayOfWords[i].length() > 20 || arrayOfWords[i].length() > maxWidth) {
                    if(arrayOfWords[i].length() > 20) {
                        fw.write("Exception, '" + arrayOfWords[i] + "' exceeds the limit of 20 symbols!");
                        fr.close();
                        fw.close();
                        throw new ExceedingWordLimitException();
                    }
                    else if(arrayOfWords[i].length() > maxWidth) {
                        fw.write("Exception, '" + arrayOfWords[i] + "' exceeds " + maxWidth + " symbols!");
                        fr.close();
                        fw.close();
                        throw new ExceedingMaxWidthException();
                    }
                }
            }

            int sum = 0;   // The sum of the lengths of all words in single line.
            int count = 0; // The number of words in single line.

            int n = arrayOfWords.length;

            // System.out.println("N - " + n);

            for (int i = 0; i < n; i++) {

                /* Case 1: When the line length(sum of length of words + minimum required space between them i.e., 1 )
                 * is less than the minimum word length.
                 * This is also the case when we are printing the last line as of course the last line length will be
                 * less than or equal to maximum width. It will be the only left-justified line in the file.
                 */

                // System.out.println("I - " + i);
                sum += arrayOfWords[i].length();
                // System.out.print("Sum - " + sum);
                count += 1;
                // System.out.println("Count - " + count);
                // System.out.println();

                if (sum + count - 1 < maxWidth) {
                    if (i == n - 1) {

                        int start = i - count + 1;


                        for (int j = start; j <= i; j++) {
                            // fprintf(output_file, "%s", arrayOfWords[j]);
                            fw.write(arrayOfWords[j]);
                            // System.out.print(arrayOfWords[j]);
                            if (j == n - 1) {
                                break;
                            } else {
                                // fprintf(output_file, " ");
                                fw.write(" ");
                                // System.out.print(" ");
                            }
                        }

                        break;
                    } else {
                        continue;
                    }
                }

                /* Case 2: When the line length(sum of length of words + minimum required space between them i.e., 1 )
                 * is equal to the maximum word length.
                 * Here the remaining space except the one necessary will be divide using ceil function.
                 * Note: Ceil function is created by myself that is the math library is not used.
                 * Syntax: ceilValue = (a + b - 1)/b;
                 */

                else if (sum + count - 1 == maxWidth) {

                    /* Here remaining space is the space which is left after subtracting the words' length
                     * from the maximum width.
                     * This is the one which is needed to be divided evenly.
                     */

                    int remainingSpace = maxWidth - sum;


                    /* Variable which will be used to store the even spaces between any two words.
                     * Changes according to new words.
                     */

                    int evenSpace;


                    // Starting index of the iteration that will print the words to the file.
                    // Changes every line.
                    int start = i - count + 1;

                    // Number of words on a single line.
                    int numberOfWords = count;

                    for (int j = start; j <= i; j++) {
                        // fprintf(output_file, "%s", arrayOfWords[j]);
                        fw.write(arrayOfWords[j]);
                        // System.out.print(arrayOfWords[j]);

                        if (j == i) {
                            continue;
                        }

                        // Note here ceilFunction is not used from "math.h" library. It is created by me
                        // using simple arithmetic function mentioned above.
                        evenSpace = ceilFunction(remainingSpace, numberOfWords - 1);

                        for (int k = 0; k < evenSpace; k++) {
                            // fprintf(output_file, " ");
                            fw.write(" ");
                            // System.out.print(" ");
                        }

                        // As a word is printed, the number of words reduces, so is the remaining space in a line.

                        numberOfWords--;

                        remainingSpace = remainingSpace - evenSpace;
                    }
                    if (i != n - 1)
                        // fprintf(output_file, "\n");
                        fw.write("\n");
                        // System.out.println();

                    sum = 0;

                    count = 0;
                } else {

                    // Since the last word can be only possible reason for the line length to cross the maxWidth
                    // we need to remove that word from our printing iteration.
                    // also decrement i so that the 'removed' word will be printed in the next loop.
                    // Count variable too.

                    sum = sum - arrayOfWords[i].length();
                    i--;
                    count--;


                    int remainingSpace = maxWidth - sum;

                    int evenSpace;

                    int start = i - count + 1;

                    int numberOfWords = count;

                    for (int j = start; j <= i; j++) {
                        // fprintf(output_file, "%s", arrayOfWords[j]);
                        fw.write(arrayOfWords[j]);
                        // System.out.print(arrayOfWords[j]);

                        if (j == i) {
                            continue;
                        }

                        evenSpace = ceilFunction(remainingSpace, numberOfWords - 1);
                        // System.out.println("Even Space - " + evenSpace);


                        for (int k = 0; k < evenSpace; k++) {
                            // fprintf(output_file, " ");
                            fw.write(" ");
                            // System.out.print(" ");
                        }

                        numberOfWords--;

                        remainingSpace = remainingSpace - evenSpace;
                    }

                    // fprintf(output_file, "\n");
                    fw.write("\n");
                    // System.out.println();

                    sum = 0;

                    count = 0;
                }
            }

            fw.close();
            fr.close();

        } catch (FileNotFoundException e) {
            System.out.println("Exception, file not found!");
        }
        catch (IOException e) {
            System.out.println();
        } catch (FileIsEmptyException e) {
            System.exit(0);
        }
        catch (ExceedTextSizeException e) {
            System.exit(0);
        } catch (IntendationException e) {
            System.exit(0);
        }
        catch (LineWidthException e) {
            System.exit(0);
        } catch (EmptyWordException e) {
            System.exit(0);
        } catch (ExceedingMaxWidthException e) {
            System.exit(0);
        } catch (ExceedingWordLimitException e) {
            System.exit(0);
        } catch (ForbiddenSymbolException e) {
            System.exit(0);
        }
    }
}