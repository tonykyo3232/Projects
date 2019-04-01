/****************************************************************************************
*  List.h
*  Name: Tung-Lin Lee
*  This file will declare the functions of List ADT
*****************************************************************************************/

#ifndef LIST_H_INCLUDED
#define LIST_H_INCLUDED

// Exported type
typedef struct ListObj* List;

// Constructor and Destructor
List newList(void);
void freeList(List* pL);

// Access functions
int length(List L);
int index(List L);
int front(List L);
int back(List L);
int get(List L);
int equals(List A, List B);

// Manipulation procedures
void clear(List L);
void moveFront(List L);
void moveBack(List L);
void movePrev(List L);
void moveNext(List L);
void prepend(List L, int data);
void append(List L, int data);
void insertBefore(List L, int data);
void insertAfter(List L, int data);
void deleteFront(List L);
void deleteBack(List L);
void delete(List L);

//Other operations
void printList(FILE* out, List L);
List copyList(List L);

#endif // LIST_H_INCrLUDED
