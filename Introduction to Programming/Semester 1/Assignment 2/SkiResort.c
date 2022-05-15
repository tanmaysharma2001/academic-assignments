#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_ARRAY_LENGTH 20
#define MAX_ARRAY_LENGTH_2 50

int verifyName(char name[30]) {

    for(int i = 0; i < strlen(name); i++) {

        // To verify if the name and title of equipment have only english alphabets and spaces.
        if((name[i] >= 'a' && name[i] <= 'z') || (name[i] >= 'A' && name[i] <= 'Z') || (name[i] == ' ')) {
            continue;
        }
        else {
            return 0;
        }
    }
    return 1;
}

int verifyLeap(int year) {

    // Verifying that a year is leap or not.
    if(year % 400 == 0 || year % 100 != 0) {
        if(year % 4 == 0) {
            return 1;
        }
    }
    return 0;
}

int verifyDate(int day, int month, int year) {
    if(day < 1 || day > 31) {
        return 0;
    }

    if(year > 9999 || year < 1800) {
        return 0;
    }


    if(month < 1 || month > 12) {
        return 0;
    }

    // Here the month February will have 29 days on a leap year and
    // 28 days on a normal year.

    if(month != 2) {
        // Month like June, April, september and november have 30 days.
        // So:
        if(month==6 || month==9 || month == 4 || month == 11) {
            if (day <= 30){
                return 1;
            }
            else {
                return 0;
            }
        }
    }
    else {
        // for the month february.
        if(verifyLeap(year)) {
            if (day <= 29) {
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            if (day <= 28) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    return 1;
}

int verifyTime(int hour, int minute, int second) {
    if((hour >=0 && hour <= 23) && (minute>=0 && minute <= 59) && (second >=0 && second <= 59)) {
        return 1;
    }
    else {
        return 0;
    }
}

int verifyEquipments(char title[15], float size, int amount, char measurementUnit[5]) {

    // Verifying equipments according to given assumptions.
    if(strlen(title) < 1 || strlen(title) < 4 || strlen(title) > 15 || !verifyName(title)) {
        return 0;
    }

    if(size < 0.00 || size > 200.00) {
        return 0;
    }

    if(amount < 0 || amount > 30) {
        return 0;
    }

    if(!strcmp(measurementUnit, "pcs") || !strcmp(measurementUnit, "pair")) {
        return 1;
    }
    else {
        return 0;
    }
}

struct Items
{
    char title[15];
    float size;
    int amount;
    char measurementUnit[5];
};

struct Date {
    int d;
    int m;
    int y;
};

struct Time {
    int h;
    int m;
    int s;
};

struct Lease
{
    char name[15];
    char surname[15];
    struct Date date;
    struct Time time;
    struct Items items[MAX_ARRAY_LENGTH];
    int equipment_array_size;
};

int main()
{
    char line[30];

    FILE *input_file = NULL;
    input_file = fopen("input.txt", "rt");

    struct Lease data[MAX_ARRAY_LENGTH];

    int current_line = 0;

    // index of the struct data
    int indexOfData = 0;

    // index of the struct equipmets.
    int indexOfItems = 0;

    // number of elements in equipment array as indexOfItems will not availaible after the file reading.
    int count_equip_array = 0;

    // If Invalid Input will be provided, the flag will turn to 1.
    int flag = 0;

    while (fgets(line, 30, input_file) != NULL)
    {
        current_line++;

        if (line[0] != '\n') // Skips the line.
        {
            if (current_line == 1)
            {
                sscanf(line, "%s %s", &data[indexOfData].name, &data[indexOfData].surname);
                int totalLength = strlen(data[indexOfData].name) + strlen(data[indexOfData].surname);

                // assumptions for Invalid input of name.
                if(totalLength < 2 || totalLength > 30 || (verifyName(data[indexOfData].name) != 1) || (verifyName(data[indexOfData].surname) != 1) ) {
                    printf("Invalid Input!\n");
                    flag = 1;
                    break;
                }
            }
            else if (current_line == 2)
            {
                sscanf(line, "%d/%d/%d %d:%d:%d", &data[indexOfData].date.d, &data[indexOfData].date.m, &data[indexOfData].date.y, &data[indexOfData].time.h, &data[indexOfData].time.m, &data[indexOfData].time.s);

                // Verifying both date and time by using verifyDate() and verifyTime() function.
                if(verifyDate(data[indexOfData].date.d, data[indexOfData].date.m, data[indexOfData].date.y) != 1) {
                    flag = 1;
                    break;
                }


                if(verifyTime(data[indexOfData].time.h, data[indexOfData].time.m, data[indexOfData].time.s) != 1) {
                    flag = 1;
                    break;
                }
            }
            else
            {
                count_equip_array++;
                sscanf(line, "%s %f %d %s", &data[indexOfData].items[indexOfItems].title, &data[indexOfData].items[indexOfItems].size, &data[indexOfData].items[indexOfItems].amount, &data[indexOfData].items[indexOfItems].measurementUnit);
                if(verifyEquipments(data[indexOfData].items[indexOfItems].title, data[indexOfData].items[indexOfItems].size, data[indexOfData].items[indexOfItems].amount, data[indexOfData].items[indexOfItems].measurementUnit) == 0) {
                    flag = 1;
                    break;
                }
                data[indexOfData].equipment_array_size = count_equip_array;
                indexOfItems++;
            }
        }
        else
        {
            indexOfData++;
            count_equip_array = 0;
            indexOfItems = 0;
            current_line = 0;
        }
    }

    // Array size of the Data Array. Needed for printing the data in the output file.
    int input_array_size = indexOfData + 1;

    FILE *output_file = NULL;
    output_file = fopen("output.txt", "w");


    // If there was a invalid input in the input.txt file, then no printing will be done. Rather the control flow will be transffered
    // to the else condition writing the "Invalid input!" in the output file.
    if (flag != 1) {

        for (int i = 0; i < input_array_size; i++) {

            // Name and surname
            fprintf(output_file, "%s %s has rented ", data[i].name, data[i].surname);

            for (int j = 0; j < data[i].equipment_array_size; j++) {

                // Basic Grammar: If amount > 1, then word pairs will be used, and pair will be used in vice-versa.
                if (data[i].items[j].amount > 1 && data[i].items[j].measurementUnit == 'pair') {
                    fprintf(output_file, "%d %ss of %s of size %.f", data[i].items[j].amount,
                            data[i].items[j].measurementUnit, data[i].items[j].title, data[i].items[j].size);
                }
                else {
                    fprintf(output_file, "%d %s of %s of size %.f", data[i].items[j].amount,
                            data[i].items[j].measurementUnit, data[i].items[j].title, data[i].items[j].size);
                }


                // Conjunctions used.
                if (j == data[i].equipment_array_size - 1) {
                    fprintf(output_file, " ");
                } else if ((j == data[i].equipment_array_size - 2) && (j > 0)) {
                    fprintf(output_file, " and ");
                } else {
                    fprintf(output_file, ", ");
                }
            }

            // Here %02d is used as it prints '3/2/2021' as '03/02/2021' which is the required date format.
            fprintf(output_file, "on %02d/%02d/%04d at %02d:%02d:%02d.", data[i].date.d, data[i].date.m, data[i].date.y,
                    data[i].time.h, data[i].time.m, data[i].time.s);

            if (i == input_array_size - 1) {
                // end of printing.
                break;
            } else {
                // new line
                fprintf(output_file, "\n");
            }
        }
    }
    else {

        // Text "Invalid input!" will be printed to the output file.
        fprintf(output_file, "Invalid input!");
    }

    fclose(input_file);
    fclose(output_file);
    return 0;
}