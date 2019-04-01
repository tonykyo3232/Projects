/****************************************************************************************
*  List.c
*  Name: Tung-Lin Lee
*  This file will implement the List ADT operation
*****************************************************************************************/
#include<stdio.h>
#include<stdlib.h>

#include"List.h"

// structs


// private NodeObj type
typedef struct NodeObj{
    int data;
    struct NodeObj* next;
    struct NodeObj* prev;
} NodeObj;

// private Node type
typedef NodeObj* Node;

// private ListObj typedef
typedef struct ListObj{
    Node front;
    Node back;
    Node cursor;
    int length;
    int index;
} ListObj;

// newList()
// Constructor of the List
List newList(){
    List L = malloc(sizeof(ListObj));
    L->front = NULL;
    L->back = NULL;
    L->cursor = NULL;
    L->length = 0;
    L->index = -1;
    return L;
}

// freeList()
// Destructor of the List
void freeList(List* pL){
    // free the memory of the nodes
    clear(*pL);

    // free the heap memory
    (*pL)->back = NULL;
    (*pL)->front = NULL;
    (*pL)->cursor = NULL;
    (*pL)->length = 0;

    free(*pL);
    *pL = NULL;
}

// Access functions

// length()
// return the length of the List
int length(List L){
    if(L == NULL){
//        printf("This List is empty\n");
        return -1;
    }
    return L->length;
}

// index()
// return the index of the cursor
int index(List L){
    if(L->cursor != NULL){
        return L->index;
    }
     else{
        return -1;
    }
}

// front()
// Pre: length() > 0
// return the element of the 1st element in the List
int front(List L){
    if(length(L) > 0){
        return L->front->data;
    }
    else{
        return -1;
    }
}

// back()
// length() > 0
// return the last element in the List
int back(List L){
    if(length(L) > 0){
        return L->back->data;
    }
    else{
        return -1;
    }
}

// get()
// Pre: length>0, index>=0
// return the element of the cursor
int get(List L){
    if(length(L) > 0 && index(L) >= 0){
//        printf("inside stack: %d\n",L->cursor->data);
        return L->cursor->data;
    }
    else{
        return -1;
    }
}

// equal()
// if two list are equal, return 1
// return 0 otherwise
int equals(List A, List B){

    // if both List are empty, return 1
    if(A->front == NULL && B->front == NULL){
        return 1;
    }

    int retval = 1;
    Node walkerA = A->front;
    Node walkerB = B->front;
    while(walkerA != NULL && walkerB != NULL){
        if(walkerA->data != walkerB->data){
            retval = 0;
            break;
        }
        walkerA = walkerA->next;
        walkerB = walkerB->next;
    }

    if((walkerA == NULL && walkerB != NULL) || (walkerA != NULL && walkerB == NULL)) {
        retval = 0;
    }
    return retval;
}

// Manipulation procedures
// clear()
// clear the nodes in the List, and set all the attribute to original state
void clear(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    Node tempNode = L->front;
    Node nodePtr;
    while(tempNode != NULL){
        nodePtr = tempNode;
        tempNode = tempNode->next; //
        free(nodePtr);
    }

    // initialize the attributes to a empty list
    L->front = NULL;
    L->back = NULL;
    L->cursor = NULL;
    L->length = 0;
    L->index = -1;
}

// moveFront()
// move the cursor to the front
void moveFront(List L){
    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(length(L) != 0){
        L->index = 0;
        L->cursor = L->front;
    }
}

// moveBack()
// move the cursor to the back
void moveBack(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(length(L) != 0){
        L->index = length(L)-1;
        L->cursor = L->back;
    }
}

// movePrev()
// move the cursor one step before
void movePrev(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(index(L) != -1 && L->cursor != L->front){
        L->cursor = L->cursor->prev;
        L->index--;
    }
    else if (index(L) != -1 && L->cursor == L->front){
        L->cursor = NULL;
        L->index = -1;
    }
}

// moveNext()
// move the cursor one step after
void moveNext(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(index(L) != -1 && L->cursor != L->back){
        L->cursor = L->cursor->next;
        L->index++;
    }
    else if(index(L) != -1 && L->cursor == L->back){
        L->cursor = NULL;
        L->index = -1;
    }
}

// prepend()
// insert the element in the end of the LIst
void prepend(List L, int data){

    Node newNode = malloc(sizeof(NodeObj));
    newNode->data = data;
    newNode->next= NULL;
    newNode->prev = NULL;

    if(length(L) == 0){
        L->front = newNode;
        L->back = L->front;
        newNode->next = NULL;
        newNode->prev = NULL;
    }
    else{
        newNode->next = L->front;
        L->front->prev = newNode;
        L->front = newNode;
    }
    L->length++;
    if(index(L)!= -1){
        L->index++;
    }
}

//append()
// insert the element in the beginning of the List
void append(List L, int data){

    Node newNode = malloc(sizeof(NodeObj));
    newNode->data = data;
    newNode->next= NULL;
    newNode->prev = NULL;

    if(length(L) == 0){
        L->front = newNode;
        L->back = L->front;
        newNode->next = NULL;
        newNode->prev = NULL;
    }
    else{
        newNode->prev = L->back;
        L->back->next = newNode;
        L->back = newNode;
        newNode->next = NULL;
    }
    L->length++;
}

// insertBefore()
// Pre: index>=0
// insert the element before the cursor
void insertBefore(List L, int data){

    Node newNode = malloc(sizeof(NodeObj));
    newNode->data = data;
    newNode->next= NULL;
    newNode->prev = NULL;

    if(L->cursor->prev == L->front){
        newNode->next = L->cursor;
        L->cursor->prev = newNode;
        L->front->next = newNode;
        newNode->prev = L->front;
    }
    else if(L->cursor == L->front){
        L->front->prev = newNode;
        newNode->next = L->front;
        L->front = newNode;
    }
    else{
        Node* oriBeforeCur = L->cursor->prev;
        L->cursor->prev->next = newNode;
        newNode->next = L->cursor;
        L->cursor->prev = newNode;
        newNode->prev = oriBeforeCur;
    }

    L->index++;
    L->length++;
}


// insertAfter()
// Pre: index >=0
// insert the element after the cursor
void insertAfter(List L, int data){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    Node newNode = malloc(sizeof(NodeObj));
    newNode->data = data;
    newNode->next= NULL;
    newNode->prev = NULL;

    if(L->cursor == L->back){
        L->back->next = newNode;
        newNode->prev = L->back;
        L->back = newNode;
        newNode->next = NULL;
    }
    else{
        Node oriAftCur = L->cursor->next;
        L->cursor->next = newNode;
        newNode->prev = L->cursor;
        oriAftCur->prev = newNode;
        newNode->next = oriAftCur;
    }
    L->length++;
}


//deleteFront()
// Pre: length > 0
// delete the element in the 1st position
void deleteFront(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(length(L) > 0){

        if(length(L) == 1){
            clear(L);
        }
        else{
            if(L->cursor == L->front){
            L->cursor = NULL;
            L->index = -1;
            }
            Node newFront = L->front->next;
            free(L->front);
            L->front = newFront;
            L->front->prev = NULL;
            L->length--;
        }

        if(index(L) != -1){
            L->index--;
        }
    }
}

// deleteBack()
// length >0
// delete the element in the last position
void deleteBack(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    if(length(L) > 0){
        if(L->back == L->cursor){
            L->cursor = NULL;
            L->index = -1;
        }
        if(length(L) == 1){
            clear(L);
        }
        else{
            Node newBack = L->back->prev;
            free(L->back);
            L->back = newBack;
            newBack->next = NULL;
            L->length--;
        }
    }
}


// delete()
// length()>0, index()>=0
// delete the element in the cursor's position
void delete(List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }
    if(length(L) == 1){
        clear(L);
    }
    else{
         if(length(L) >= 0 && index(L) >= 0){
            if(L->cursor != L->front && L->cursor != L->back){
                Node pre = L->cursor->prev;
                Node aft = L->cursor->next;
                pre->next = aft;
                aft->prev = pre;
            }
            if(L->cursor == L->front){
                if(length(L) == 2){
                    L->front = L->back;
                    L->back->prev = NULL;
                }
                else{
                    L->front = L->cursor->next;
                    L->front->prev = NULL;
                }
            }
            if(L->cursor == L->back){
                if(length(L) == 2){
                    L->back = L->front;
                    L->front->next = NULL;
                }
                else{
                    L->back = L->cursor->prev;
                    L->back->next = NULL;
                }
            }
        }
        free(L->cursor);
        L->cursor = NULL;
        L->index = -1;
        L->length--;
    }
}

//Other operations

// printList()
// print out the elements in the List
void printList(FILE* out, List L){

    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    Node walker = L->front;
    while(walker != NULL){
        fprintf(out, "%d ", walker->data);
        walker = walker->next;
    }
}

// copyList()
// copy the List to the other
List copyList(List L){
    if(L == NULL){
//        printf("This List is empty\n");
        exit(1);
    }

    List copyList = newList();
    Node oriWalker = L->front;
    while(oriWalker != NULL){
        append(copyList, oriWalker->data);
        oriWalker = oriWalker->next;
    }

    return copyList;
}

