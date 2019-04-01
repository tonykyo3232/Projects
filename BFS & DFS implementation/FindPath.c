/****************************************************************************************
*  FindPath.c
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
    //int vertexCount = line - '0';
    int vertexCount = atoi(line);

    // create a graph base on the number of vertex is read
    Graph G = newGraph(vertexCount);

    while(fscanf(inFile,"%d %d",&start,&end)){
        if(start == 0 && end == 0){
            break;
        }
        addEdge(G,start,end);
    }

    // printing the graph
    printGraph(stdout, G); // output in window
    printGraph(outFile,G); // output in .txt file

    start = 0;
    end = 0;
    List L = newList();

    while(fscanf(inFile,"%d %d",&start,&end)){
        if(start == 0 && end == 0){
            break;
        }
        BFS(G,start);
        getPath(L,G,end);
        if(getDist(G,end) != INF){

            // output in window
            fprintf(stdout,"\n\nThe distance from %d to %d is %d\n", start, end, getDist(G,end));
            fprintf(stdout,"A shortest %d-%d path is: ",start,end);
            printList(stdout,L);

            // output in .txt file
            fprintf(outFile,"\n\nThe distance from %d to %d is %d\n", start, end, getDist(G,end));
            fprintf(outFile,"A shortest %d-%d path is: ",start,end);
            printList(outFile,L);
        }
        else{
            // output in window
            fprintf(stdout,"\n\nThe distance from %d to %d is infinity\n", start, end);
            fprintf(stdout,"No %d-%d path exists\n",start,end);

            // output in .txt file
            fprintf(outFile,"\n\nThe distance from %d to %d is infinity\n", start, end);
            fprintf(outFile,"No %d-%d path exists\n",start,end);
        }
        clear(L);
    }

    // release the memory
    freeGraph(&G);

   // closing the file
   fclose(inFile);
   fclose(outFile);
   return 0;
}
