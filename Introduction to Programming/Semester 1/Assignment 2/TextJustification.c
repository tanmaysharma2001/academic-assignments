/* Tanmay Sharma
 * t.sharma@innopolis.university
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1

#define MAX_LINE_LENGTH 300
#define MAX_INT_LENGTH 10

int ceilFunction(int a, int b) {
    if(b==0) {
        return 0;
    }
    return (a + b - 1) / b;
}

int main()
{

    FILE *input_file = NULL; // File pointer to open the file into the Program.

    /* Opening the file into the program using fopen command.
     * syntax for fopen: fopen(const char *filename, const char *mode).
     */
    input_file = fopen("input.txt", "r");

    /* The text which needs to be justified and maxWidth by which.
     * Read into text string(char array) using fgets.
     * syntax for fgets: fgets(char *str, int n, FILE *stream)
     */
    char text[MAX_LINE_LENGTH];
    fgets(text, MAX_LINE_LENGTH, input_file);

    char line2[10];
    fgets(line2, 10, input_file);

    /* The C library function 'int atoi(const char* string)' is
     * used which converts the given string arguement to an integer.
     */
    int maxWidth = atoi(line2);

    /* Character Array which will contain the words splitted from
     * the given text string. The character array needs to be 2-dimensional.
     * Maximum number of words - 30 and Maximum length of a
     * word(array of characters) - 20.
     */
    char arrayOfWords[30][20];

    // Splitting the text and storing it into character arrays.
    int index = 0;
    int n = 0;
    for (int i = 0; i <= (strlen(text)); i++)
    {
        // if space or NULL found, assign NULL into newString[ctr]
        // Important.
        if (text[i] == ' ' || text[i] == '\0' || text[i] == '\n')
        {
            arrayOfWords[n][index] = '\0';
            n++;                  // To move to the next word.
            index = 0;            // When next word starts with zero.
        }
        else
        {
            arrayOfWords[n][index] = text[i];
            index++;
        }
    }

    FILE* output_file = NULL;
    output_file = fopen("output.txt", "w");



    // Justifying the text...!

    int sum = 0;   // The sum of the lengths of all words in single line.
    int count = 0; // The number of words in single line.

    for (int i = 0; i < n; i++)
    {

        /* Case 1: When the line length(sum of length of words + minimum required space between them i.e., 1 )
         * is less than the minimum word length.
         * This is also the case when we are printing the last line as of course the last line length will be
         * less than or equal to maximum width. It will be the only left-justified line in the file.
         */

        sum += strlen(arrayOfWords[i]);
        count += 1;

        if (sum + count - 1 < maxWidth)
        {
            if(i == n - 1) {

                int start = i - count + 1;


                for(int j = start; j <= i; j++) {
                    fprintf(output_file, "%s", arrayOfWords[j]);
                    if(j == i-1) {
                        break;
                    }
                    else {
                        fprintf(output_file, " ");
                    }
                }

                break;
            }

            else {
                continue;
            }
        }

        /* Case 2: When the line length(sum of length of words + minimum required space between them i.e., 1 )
         * is equal to the maximum word length.
         * Here the remaining space except the one necessary will be divide using ceil function.
         * Note: Ceil function is created by myself that is the math library is not used.
         * Syntax: ceilValue = (a + b - 1)/b;
         */

        else if (sum + count - 1 == maxWidth)
        {

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

            for (int j = start; j <= i; j++)
            {
                fprintf(output_file, "%s", arrayOfWords[j]);

                if (j == i)
                {
                    continue;
                }

                // Note here ceilFunction is not used from "math.h" library. It is created by me
                // using simple arithmetic function mentioned above.
                evenSpace = ceilFunction(remainingSpace, numberOfWords - 1);

                for (int k = 0; k < evenSpace; k++)
                {
                    fprintf(output_file, " ");
                }


                // As a word is printed, the number of words reduces, so is the remaining space in a line.

                numberOfWords--;

                remainingSpace = remainingSpace - evenSpace;
            }
            if (i != n - 1)
                fprintf(output_file, "\n");

            sum = 0;

            count = 0;
        }
        else
        {

            // Since the last word can be only possible reason for the line length to cross the maxWidth
            // we need to remove that word from our printing iteration.
            // also decrement i so that the 'removed' word will be printed in the next loop.
            // Count variable too.

            sum = sum - strlen(arrayOfWords[i]);
            i--;
            count--;


            int remainingSpace = maxWidth - sum;

            int evenSpace;

            int start = i - count + 1;

            int numberOfWords = count;

            for (int j = start; j <= i; j++)
            {
                fprintf(output_file, "%s", arrayOfWords[j]);

                if (j == i)
                {
                    continue;
                }

                evenSpace = ceilFunction(remainingSpace, numberOfWords - 1);


                for (int k = 0; k < evenSpace; k++)
                {
                    fprintf(output_file, " ");
                }

                numberOfWords--;

                remainingSpace = remainingSpace - evenSpace;
            }

            fprintf(output_file, "\n");

            sum = 0;

            count = 0;
        }
    }

    fclose(input_file);
    fclose(output_file);

    return 0;
}
/*
This is an example of text justification.
16

What must be acknowledgment shall be.
16

*/