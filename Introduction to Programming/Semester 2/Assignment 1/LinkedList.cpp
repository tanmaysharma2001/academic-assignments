#include <bits/stdc++.h>

using namespace std;

class DoublyLinkedList
{

private:
    int size = 0;

public:
    class Node
    {
    public:
        int value;
        Node *prev;
        Node *next;

    public:
        Node(int value)
        {
            this->value = value;
        }
    };

    Node *head = nullptr;
    Node *tail = nullptr;

    void addElement(int value)
    {

        Node *newNode = new Node(value);

        if (head == nullptr)
        {
            head = newNode;
            tail = newNode;
            head->prev = nullptr;
            tail->next = nullptr;
            size++;
            return;
        }

        tail->next = newNode;
        newNode->prev = tail;
        tail = newNode;
        tail->next = nullptr;
        size++;
    }

    void traverse()
    {
        Node *current = head;

        while (current != nullptr)
        {
            cout << current->value << " ";
            current = current->next;
        }
    }

    void performOperation()
    {

        Node *current = head;

        while (current != nullptr && current->next != nullptr)
        {
            int temp = current->value;
            current->value = current->next->value;
            current->next->value = temp;
            current = current->next->next;
        }
    }

    int getElement(int index) {
        Node *current = head;
        
        int count = 0;

        while(count < index) {
            current = current->next;
            count++;
        }

        return current->value;
    }

    int getSize() {
        return size;
    }
};

int main()
{
    DoublyLinkedList newList;

    ifstream infile("input.txt");

    int value;
    while (infile >> value)
    {
        newList.addElement(value);
    }

    newList.performOperation();

    ofstream outfile("output.txt");

    int count = 0;
    int size = newList.getSize();
    while(count < size) {
        outfile << newList.getElement(count) << " ";
        count++;
    }

    return 0;
}