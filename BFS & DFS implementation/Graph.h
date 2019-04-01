/****************************************************************************************
*  Graph.h
*  Name: Tung-Lin Lee
*  This file will declare the functions of Graph ADT
*****************************************************************************************/
#ifndef GRAPH_H_INCLUDED
#define GRAPH_H_INCLUDED

#include "List.h"

#define NIL -1
#define INF -2
#define UNDEF -3

// Exported type--------------------------------------------------------------------
typedef struct GraphObj *Graph;

/*** Constructor-Destructors ***/
Graph newGraph(int n);
void freeGraph(Graph *pG);

/*** Access functions ***/
int getOrder(Graph G);
int getSize(Graph G);
int getSource(Graph G);
int getParent(Graph G, int u);
int getDiscover(Graph G, int u);
int getFinish(Graph G, int u);
int getDist(Graph G, int u);
void getPath(List L, Graph G, int u);

/*** Manipulation procedures ***/
void makeNull(Graph G);
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void BFS(Graph G, int s);
void DFS(Graph G, List s);

/*** Other Operations ***/
Graph transpose(Graph G); // constructors
Graph copyGraph(Graph G); // constructors
void printGraph(FILE *out , Graph G);


/*** Helper function ***/
void visit(Graph G, List S, int *time, int u);

#endif // GRAPH_H_INCLUDED
