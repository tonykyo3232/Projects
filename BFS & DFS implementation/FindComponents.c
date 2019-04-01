/****************************************************************************************
*  FindComponent.c
*  Name: Tung-Lin Lee
*  This file will test the functions of List ADT, Graph ADT
*****************************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "List.h"
#include "Graph.h"

#define MAX_LEN 150


int main(int argc, char * argv[]){

    // check command line for correct number of arguments
    if( argc != 3 ){
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      exit(1);
    }

   // open files for reading and writing
   FILE *inFile = fopen(argv[1], "r");
   FILE *outFile = fopen(argv[2], "w");

   if(inFile == NULL){
      printf("Unable to open file %s for reading\n", argv[1]);
      exit(1);
    }

    if(outFile == NULL){
      printf("Unable to open file %s for writing\n", argv[2]);
      exit(1);
    }

    char line[MAX_LEN];
    int start = 0, end = 0;

    // first part of reading: read the first line of the txt. file
    if( fgets(line, MAX_LEN, inFile) != NULL){

        // if the line reads from file contain "\n", remove it
        if(line[strlen(line)-1] == '\n'){
            line[strlen(line)-1] = 0;
        }
    }

    // record the number of the vertex
    // casting the char type of number to integer type
    int vertexCount = atoi(line);

    // create the graphs based on the number of vertex is read
    Graph G = newGraph(vertexCount);
    Graph GT = newGraph(vertexCount);
    List stack = newList();

    for(int i=1; i<=getOrder(G); i++)
        append(stack, i);

    while(fscanf(inFile,"%d %d",&start,&end)){
        if(start == 0 && end == 0){
            break;
        }
        addArc(G,start,end);
    }

    // Call DFS to find the strong connected components for transpose graph
    DFS(G,stack);

    // find the transpose graph of G
    GT = transpose(G);

    // Call DFS to find the strong connected components for transpose graph
    DFS(GT,stack);

    // next, find the number of parents in the stack
    int parentCount = 0;
    moveFront(stack);
    while(index(stack) != -1){
        if(getParent(GT,get(stack)) == NIL)
            parentCount++;
        moveNext(stack);
    }

    // declare an 2D array (int list array)
    int *connectComs = malloc(parentCount+1 * sizeof(List));

    // allocate the memory
    for(int a = 1; a < parentCount+1; a++){
        connectComs[a] = newList();
    }

    int i = 0;
    moveFront(stack);
    while(index(stack) != -1){
        // if the parent is found, increase the i and store the value until the next parent is found
        if(getParent(GT,get(stack)) == NIL){
            i++;
        }
        append(connectComs[i], get(stack));
        moveNext(stack);
    }

    // printing the graph

    // output in .txt file
    fprintf(stdout,"Adjacency list representation of G:\n");
    printGraph(stdout,G);
    fprintf(stdout,"G contains %d strongly connected components:\n",parentCount);

    // output in window
    fprintf(outFile,"Adjacency list representation of G:\n");
    printGraph(outFile,G);
    fprintf(outFile,"G contains %d strongly connected components:\n",parentCount);

    int j = 1;
    for(int b = parentCount; b > 0; b--){

        // output in .txt file
        fprintf(outFile,"Component %d: ",j);
        printList(outFile,connectComs[b]);
        fprintf(outFile,"\n");

        // output in window
        fprintf(stdout,"Component %d: ",j);
        printList(stdout,connectComs[b]);
        fprintf(stdout,"\n");

        j++;
    }

    // release the memory
    freeGraph(&G);
    freeGraph(&GT);
    freeList(&stack);
    freeList(&connectComs);

   // closing the file
   fclose(inFile);
   fclose(outFile);
   return 0;
}

