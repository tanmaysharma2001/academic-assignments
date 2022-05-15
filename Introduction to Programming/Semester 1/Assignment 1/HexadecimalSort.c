#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Conversion of Integer Values to Hexadecimal Values

int hexToInteger(char *hex)
{
    int dec = 0;

    int x, i;

    int pow = 1;

    for (i = strlen(hex) - 1; i >= 0; --i)
    {
        if (hex[i] >= '0' && hex[i] <= '9')
        {
            x = hex[i] - '0';
            dec = dec + x * pow;
        }
        else
        {
            x = hex[i] - 'A' + 10;
            dec = dec + x * pow;
        }
        pow *= 16;
    }
    return dec;
}

// conversion of hexadeciaml values back to integer after Sorting
void integerToHexadecimal(FILE *file, int number)
{
    char hexadeciamlValues[100];
    int id = 0;
    while (number > 0)
    {
        int rem = number % 16;
        if (rem >= 10)
        {
            hexadeciamlValues[id] = (char)('A' + (rem - 10));
        }
        else
        {
            hexadeciamlValues[id] = (char)(rem + '0');
        }
        number /= 16;
        ++id;
    }
    hexadeciamlValues[id] = '\0';

    // Reversing the array as the values got printed in reversed form
    // like 16 got printed in '01' not '10'

    for (int i = 0; i < id / 2; i++)
    {
        int t = hexadeciamlValues[i];
        hexadeciamlValues[i] = hexadeciamlValues[id - i - 1];
        hexadeciamlValues[id - i - 1] = t;
    }

    // printing to the file
    fprintf(file, "%s ", hexadeciamlValues);
}

void swap(int *a, int *b)
{
    *b = *a + *b;
    *a = *b - *a;
    *b = *b - *a;
}

int main()
{
    int integerValues[1000];
    int index = 0;

    FILE *input = fopen("input.txt", "r");
    char hexadeciamlValues[100];

    // Reading the Hexadecimal values

    while (fscanf(input, "%s", &hexadeciamlValues) > 0)
    {
        integerValues[index] = hexToInteger(hexadeciamlValues);
        ++index;
    }

    integerValues[index] = '\0';

    // Sorting the array!

    for (int i = 0; i < index; i++)
    {
        for (int j = 0; j + 1 < index; j++)
        {
            if (integerValues[j] > integerValues[j + 1])
            {
                swap(&integerValues[j], &integerValues[j + 1]);
            }
        }
    }

    FILE *output = fopen("output.txt", "w");

    for (int i = 0; i < index + 1; i++)
    {
        integerToHexadecimal(output, integerValues[i]);
    }

    fclose(input);
    fclose(output);
}