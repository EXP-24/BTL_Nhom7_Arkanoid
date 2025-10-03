#include <iostream>
using namespace std;

struct Node {
    int data;
    Node* next;
    Node(int d, Node* n = nullptr) : data(d), next(n) {}
};

struct linkedList {
    Node* head;
    Node* tail;

    linkedList() {
        head = nullptr;
        tail = nullptr;
    }

    int get(int x) {
        if (x < 0) {
            return -1;
        }
        Node* current = head;
        for (int i = 0; i < x; i++){
            if (current == nullptr) {
                return -1;
            }
            current = current->next;
        }
        return current->data;
    } //Truy cap O(n)

    void addFirst(int val) {
        Node* newNode = new Node(val, head);
        head = newNode;
        if (tail == nullptr) {
            tail = newNode;
        }
    } //Them phan tu dau O(1)

    void addLast(int val) {
        Node* newNode = new Node(val, nullptr);
        if (head == nullptr) {
            head = newNode;
            tail = newNode;
        }
        tail->next = newNode;
        tail = newNode;
    } //Them phan tu cuoi O(1)

    void insert(int val, int pos) {
        if (pos < 0) {
            cout << "error" << endl;
            return;
        }

        if (pos == 0) {
            Node* newNode = new Node(val, head);
            head = newNode;
            return;
        }

        Node* current = head;
        for (int i = 0; i < pos - 1 && current != nullptr; i++) {
            current = current->next;
        }

        if (current == nullptr) {
            cout << "error";
            return;
        }

        Node* newNode = new Node(val, current->next);
        current->next = newNode;
    } //Them vao vi tri pos O(n)

    void deleteFirst() {
        if (head == nullptr){
            cout << "cannot delete" << endl;
            return;
        }
        Node* current = head;
        head = head->next;
        delete current;
        if (head == nullptr) {
            tail = nullptr;
        }
    } //Xoa phan tu dau O(1)

    void deleteLast() {
        if (head == nullptr) {
            cout << "cannot delete" << endl;
            return;
        }
        if (head->next == nullptr){
            delete head;
            head = nullptr;
            tail = nullptr;
            return;
        }
        Node* current = head;
        while (current->next->next != nullptr) {
            current = current->next;
        }
        delete current->next;
        current->next = nullptr;
    } //Xoa phan tu cuoi O(n)

    void remove(int pos) {
        if (head == nullptr){
            cout << "error";
            return;
        }
        if (pos < 0){
            cout << "error" << endl;
            return;
        }
        if (pos == 0) {
            Node* current = head;
            head = head->next;
            delete current;
            return;
        }
        Node* current = head;
        for(int i = 0; i < pos-1 && current != nullptr; i++){
            current = current->next;
        }
        if (current == nullptr || current->next == nullptr) {
            cout << "error" << endl;
            return;
        }
        Node* temp = current->next;
        current->next = temp->next;
        delete temp;
    } //Xoa vi tri pos O(n)

    void traverseForward(){
        Node* current = head;
        while (current != nullptr){
            cout << current->data << " ";
            current = current->next;
        }
        cout << endl;
    } //Duyet xuoi O(n)

    void help(Node* node){
        if (node == nullptr) return;
        help(node->next);
        cout << node->data << " ";
    }

    void traverseBackward(){
        help(head);
        cout << endl;
    } //Duyet nguoc O(n)

    int Count() {
        int count = 0;
        Node* current = head;
        while (current != nullptr){
            current = current->next;
            count++;
        }
        return count;
    }
};
