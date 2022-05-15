#include <bits/stdc++.h>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);

    ifstream infile("input.txt");

    vector<int> arr;
    int n;
    while(infile >> n) {
        arr.push_back(n);
    }

    int sum = 0;

    int best = 0;

    for(int i = 0; i < arr.size(); i++) {
        sum = max(arr[i], sum + arr[i]);
        best = max(best, sum);
    }

    ofstream outfile("output.txt");

    outfile << best;

    return 0;
}