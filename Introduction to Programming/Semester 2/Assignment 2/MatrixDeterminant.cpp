#include <bits/stdc++.h>
#include <fstream>

using namespace std;

int determinant2by2Matrix(vector<vector<int>> matrix, int row, int column) {
    return (matrix.at(row).at(column))*matrix.at(row+1).at(column + 1) - (matrix.at(row).at(column + 1))*(matrix.at(row+1).at(column));
};

vector<vector<int>> generateMatrix(vector<vector<int>> matrix, int row, int column) {
    vector<vector<int>> newMatrix;
    for(int i = 0; i < matrix.size(); i++) {
        vector<int> rowOfMatrix;
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

int determinant(vector<vector<int>> matrix, int row, int column) {
    if(matrix.size() == 2) {
        return determinant2by2Matrix(matrix, row, column);
    }

    int result = 0;

    for(int i = column; i < matrix.size(); i++) {

        int sign = (i + 1) % 2 == 0 ? -1 : 1;

        vector<vector<int>> newMatrix = generateMatrix(matrix, row, i);

        result += sign * (matrix.at(row).at(i)) * (determinant(newMatrix, 0, 0));
    }
    return result;
};

int main() {
    vector<vector<int>> matrix;

    ifstream infile;
    infile.open("input.txt");

    string inputLine;
    getline(infile, inputLine);

    char a;

    vector<int> row;

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

    ofstream outfile("output.txt");

    outfile << determinant(matrix, 0, 0) << endl;


    infile.close();

    return 0;
};