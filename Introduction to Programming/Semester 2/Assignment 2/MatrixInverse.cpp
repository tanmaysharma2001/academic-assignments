#include <bits/stdc++.h>
#include <fstream>

using namespace std;

double determinant2by2Matrix(vector<vector<double>> matrix, int row, int column) {
    return (matrix.at(row).at(column))*matrix.at(row+1).at(column + 1) - (matrix.at(row).at(column + 1))*(matrix.at(row+1).at(column));
};

vector<vector<double>> generateMatrix(vector<vector<double>> matrix, int row, int column) {
    vector<vector<double>> newMatrix;
    for(int i = 0; i < matrix.size(); i++) {
        vector<double> rowOfMatrix;
        if(i == row) {
            continue;
        }
        for(int j = 0; j < matrix.at(i).size(); j++) {
            if(j == column) {
                continue;
            }
            rowOfMatrix.push_back(matrix.at(i).at(j));
        }
        newMatrix.push_back(rowOfMatrix);
    }

    return newMatrix;
};

double determinant(vector<vector<double>> matrix, int row, int column) {
    if(matrix.size() == 2) {
        return determinant2by2Matrix(matrix, row, column);
    }

    int result = 0;

    for(int i = column; i < matrix.size(); i++) {

        int sign = (i + 1) % 2 == 0 ? -1 : 1;

        vector<vector<double>> newMatrix = generateMatrix(matrix, row, i);

        result += sign * (matrix.at(row).at(i)) * (determinant(newMatrix, 0, 0));
    }
    return result;
};

vector<vector<double>> transpose(vector<vector<double>> matrix) {
    vector<vector<double>> transposedMatrix;
    for(int i = 0; i < matrix.size(); i++) {
        vector<double> row;
        for(int j = 0; j < matrix.at(i).size(); j++) {
            row.push_back(matrix.at(j).at(i));
        }
        transposedMatrix.push_back(row);
    }
    return transposedMatrix;
};

int main() {
    vector<vector<double>> matrix;

    ifstream infile;
    infile.open("input.txt");

    string inputLine;
    getline(infile, inputLine);

    char a;

    vector<double> row;

    for(int i = 0; i < inputLine.size(); i++) {
        a = inputLine[i];
        if(a == '}' && inputLine[i+1] != '}') {
            matrix.push_back(row);
            row.clear();
        }
        else if(isdigit(a)) {
            row.push_back(a - '0');
        }
    }

    vector<vector<double>> transposedMatrix = transpose(matrix);

    vector<vector<double>> adjointMatrix;

    for(int i = 0; i < matrix.size(); i++) {
        vector<double> row;
        for(int j = 0; j < matrix.at(i).size(); j++) {
            int sign = (i + j) % 2 == 0 ? 1 : -1;
            row.push_back(sign * determinant(generateMatrix(transposedMatrix, i, j), 0, 0));
        }
        adjointMatrix.push_back(row);
    }

    vector<vector<double>> inverseMatrix;

    double determinantOfMatrix = determinant(matrix, 0, 0);

    for(int i = 0; i < adjointMatrix.size(); i++) {
        vector<double> row;
        for(int j = 0; j < adjointMatrix.at(i).size(); j++) {
            double element = adjointMatrix.at(i).at(j) / determinantOfMatrix;
            row.push_back(element);
        }
        inverseMatrix.push_back(row);
    }


    FILE *outfile;
    outfile = fopen("output.txt", "w");

    for(auto &i : inverseMatrix) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }


    infile.close();
    fclose(outfile);

    return 0;
};