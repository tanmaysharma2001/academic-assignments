#include <bits/stdc++.h>
#include <fstream>

using namespace std;

typedef long long ll;

/* To execute use: g++ -std=c++11 -O2 -Wall file_name.cpp -o file_name */

/* :- To read a whole line from the input, use getline() function.
 * string s;
 * getline(cin, s);
 */

/* :- If the amount of data is unknown:
 * while (cin >> x) {
 *      // code
 * }
 */

// To read files, use:
// freopen(input.txt, r, stdin);
// freopen(output.txt, w, stdout);

int main()
{
    ios::sync_with_stdio(0);
    cin.tie(0);

    ifstream infile("input.txt");

    string given;
    infile >> given;

    vector<string> words;
    string word1;

    while(infile >> word1) {
        words.push_back(word1);
    }

    vector<string> combinations;

    for (int i = 0; i < words.size(); i++)
    {

        for (int j = 0; j < words.size(); j++)
        {

            string resultantString = words.at(i);

            if (i == j)
            {
                continue;
            }

            resultantString += words.at(j);

            for (int k = 0; k < words.size(); k++)
            {
                if ((i == k) || (j == k))
                {
                    continue;
                }
                resultantString += words.at(k);
            }

            combinations.push_back(resultantString);
        }
    }

    vector<int> indexes;

    for(int i = 0; i < combinations.size(); i++) {
        size_t found = given.find(combinations.at(i));
        
        if(found != std::string::npos) {
            indexes.push_back(found);
        }
    }

    sort(indexes.begin(), indexes.end());

    ofstream outfile("output.txt");

    for(int index : indexes) {
        outfile << index << " ";
    }

    return 0;
}