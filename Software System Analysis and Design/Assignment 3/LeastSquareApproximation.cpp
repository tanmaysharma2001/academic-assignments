#include <bits/stdc++.h>
#include <fstream>

using namespace std;

vector<vector<double>> inverse(vector<vector<double>> matrix) {

    vector<vector<double>> inverseMatrix;

    int n = matrix.size();

    for(int i = 0; i < n; i++) {
        vector<double> row;
        for(int j = 0; j < n; j++) {
            if(i==j) {
                row.push_back(1.00);
                continue;
            }
            row.push_back(0.00);
        }
        inverseMatrix.push_back(row);
    }

    for(int i = 0; i < n; i++) {

        double value = matrix.at(i).at(i);

        for(int m = 0; m < n; m++) {
            matrix.at(i).at(m) = matrix.at(i).at(m)/value;
            inverseMatrix.at(i).at(m) = inverseMatrix.at(i).at(m)/value;
        }

        for(int j = 0; j < n; j++) {

            if(i==j) {
                continue;
            }

            double ratio = matrix.at(j).at(i)/matrix.at(i).at(i);

            for(int k = 0; k < n; k++) {

                matrix.at(j).at(k) -= ratio*matrix.at(i).at(k);

                inverseMatrix.at(j).at(k) -= ratio*inverseMatrix.at(i).at(k);
            }
        }
    }

    return inverseMatrix;

};

vector<vector<double>> multiply(vector<vector<double>> matrixATranspose, vector<vector<double>> matrixA) {
    vector<vector<double>> result;

    for(int i = 0; i < matrixATranspose.size(); i++) {
        vector<double> row;
        for(int j = 0; j < matrixA.at(0).size(); j++) {
            double sum = 0;
            for(int k = 0; k < matrixA.size(); k++) {
                sum += matrixATranspose.at(i).at(k)*matrixA.at(k).at(j);
            }
            row.push_back(sum);
        }
        result.push_back(row);
        row.clear();
    }

    return result;
};

vector<vector<double>> transpose(vector<vector<double>> matrix) {
    vector<vector<double>> transposedMatrix;

    for(int i = 0; i < matrix.at(0).size(); i++) {
        vector<double> row;
        for(int j = 0; j < matrix.size(); j++) {
            row.push_back(matrix.at(j).at(i));
        }
        transposedMatrix.push_back(row);
    }
    return transposedMatrix;
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ifstream infile;
    infile.open("input.txt");

    cout << fixed << setprecision(2);

    int n, m;

    infile >> n >> m;

    vector<vector<double>> matrixA;
    vector<vector<double>> matrixB;

    vector<double> row;

    for(int j = 0; j < m; j++) {
        row.push_back(1);
        matrixA.push_back(row);
        row.clear();
    }

    for(int j = 0; j < m; j++) {
        double value;

        for(int i = 0; i < n; i++) {
            infile >> value;
            matrixA.at(j).push_back(value);
        }
        infile >> value;
        vector<double> row;
        row.push_back(value);
        matrixB.push_back(row);
    }

    vector<vector<double>> matrixATranspose = transpose(matrixA);

    vector<vector<double>> AtA = multiply(matrixATranspose, matrixA);

    vector<vector<double>> inverseMatrix = inverse(AtA);

    vector<vector<double>> AtAIAt = multiply(inverseMatrix, matrixATranspose);

    vector<vector<double>> x = multiply(AtAIAt, matrixB);

    FILE *outfile;
    outfile = fopen("output.txt", "w");

    fprintf(outfile, "A:\n");
    for(auto &i : matrixA) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }

    fprintf(outfile, "\n");

    fprintf(outfile, "b:\n");
    for(auto &i : matrixB) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }

    fprintf(outfile, "\n");

    fprintf(outfile, "A_T*A:\n");
    for(auto &i : AtA) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }

    fprintf(outfile, "\n");

    fprintf(outfile, "(A_T*A)_-1:\n");
    for(auto &i : inverseMatrix) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }

    fprintf(outfile, "\n");

    fprintf(outfile, "(A_T*A)_-1*A_T:\n");
    for(auto &i : AtAIAt) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }

    fprintf(outfile, "\n");

    fprintf(outfile, "x:\n");
    for(auto &i : x) {
        for(double j : i) {
            fprintf(outfile, "%.2f ", j);
        }
        fprintf(outfile, "\n");
    }


    infile.close();
    fclose(outfile);

    return 0;
}