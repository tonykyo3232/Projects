/****************************************************************************************
*  Graph.c
*  Name: Tung-Lin Lee
*  This file will implement the functions of Graph ADT
*****************************************************************************************/

#include<stdio.h>
#include<stdlib.h>

#include "List.h"
#include "Graph.h"

// structs ----------------------------------

// private GraphObj type
typedef struct GraphObj{
    List *listAry;
    char *color;
    int *parent;
    int *distance;
    int *DT; // discover time (For DFS only)
    int *FT; // finish time (For DFS only)
    int order;
    int size;
    int source;
}GraphObj;

/*** Constructor-Destructors ***/

// newGraph()
// constructor of the graph
Graph newGraph(int n){

    Graph G = malloc(sizeof(GraphObj));

    // allocate the memory
    G->listAry = calloc(n+1, sizeof(List));
    G->color = calloc(n+1, sizeof(char));
    G->parent = calloc(n+1, sizeof(int));
    G->distance = calloc(n+1, sizeof(int));
    G->DT = calloc(n+1, sizeof(int));
    G->FT = calloc(n+1, sizeof(int));
    G->order = n;
    G->size = 0;
    G->source = NIL;

    // initialize the attribute
    for(int i = 1; i < n+1;i++){
        G->listAry[i] = newList();
        G->color[i] = NULL;
        G->parent[i] = 0;
        G->distance[i] = 0;
    }

    // initialize the values and colors
    for(int i = 1; i < getOrder(G)+1; i++){
        G->color[i] = 'w';
        G->distance[i] = INF;
        G->parent[i] = NIL;
        G->DT[i] = UNDEF;
        G->FT[i] = UNDEF;
    }
    return (G);
}

// freeGraph
void freeGraph(Graph* pG){

    //dereference pG
    Graph dpG = *pG;

    // free the memory
    for(int i = 1; i < (*pG)->size+1; i++){
        freeList(&(dpG)->listAry[i]);
    }
    free((*pG)->color);
    free((*pG)->parent);
    free((*pG)->distance);
    free((*pG)->DT);
    free((*pG)->FT);
    free(*pG);
    *pG = NULL;
}

/*** Access functions ***/

// getOrder
int getOrder(Graph G){
    return G->order;
}

// getSize
int getSize(Graph G){
    return G->size;
}

// getSource
int getSource(Graph G){
    // if the BFS is called already, return
    if( G->source != 0)
        return G->source;
    else{
        return NIL;
    }
}

// getParent
// Pre: 1<=u<=n=getOrder(G)
int getParent(Graph G, int u){
    // if the BFS/DFS is called already, return
    if( 1 <= u && u <= getOrder(G) ){
//        printf("inside parent: %d\n",G->parent[u]); // debug
        return G->parent[u];
    }
    else{
        return NIL;
    }
}

// getDiscover
// Pre: 1<=u<=n=getOrder(G)
int getDiscover(Graph G, int u){
    // if the DFS is called already, return
    if( 1 <= u && u <= getOrder(G) ){
        return G->DT[u];
    }
    else{
        return UNDEF;
    }
}

// getFinish
// Pre: 1<=u<=n=getOrder(G)
int getFinish(Graph G, int u){
    // if the DFS is called already, return
    if( 1 <= u && u <= getOrder(G) ){
        return G->FT[u];
    }
    else{
        return UNDEF;
    }
}

// getDist
int getDist(Graph G, int u){
    // if the BFS is called already, return
    if( 1 <= u && u <= getOrder(G) ){
        return G->distance[u];
    }
    else{
        return NIL;
    }
}


// getPath
void getPath(List L, Graph G, int u){

    // declare local variable
    int distance = 0;
    int parent = 0;
    int tempParent = 0;

    // check the condition: if u is in the range[1,getOrder(G)] or not
    if( !(1 <= u && u <= getOrder(G)) ){
       return;
    }

    // check the condition: if the BFS is called or not
    if(getSource(G) == NIL){
        return;
    }

    distance = getDist(G,u); // get the distance of the vertex u from source vertex
    parent = getParent(G,u); // get the parent of the vertex u

    if(parent != NIL){
        append(L,u);         // append the destination of the vertex into the list
        prepend(L,parent);   // prepend the parent before the destination
        distance--;
        while(distance > 0){
            tempParent = getParent(G,parent);
            prepend(L,tempParent);
            parent = tempParent;
            distance--;
        }
    }
    // if the destination is itself, append itself
    else if(G->source == u){
        append(L,u);
    }
    // if no such path, append NIL to the list
    else{
        append(L,NIL);
    }
}

/*** Manipulation procedures ***/

// makeNull
void makeNull(Graph G){
    for(int i = 1; i < G->size+1; i++){
        // move the cursor of the current list to the front
        moveFront(G->listAry[i]);

        // if the cursor is not empty, means the list is not empty
        while(index(G->listAry[i]) != -1){
            clear(G->listAry[i]); // use List ADT function to clear the list
        }
    }
}

// addEdge
// append the element into the list array in "both" direction
void addEdge(Graph G, int u, int v)
{
    if(1 <= u && u <= getOrder(G) && 1 <= v && v <= getOrder(G)){
        // case 1: if the adjacency list is empty
        if(length(G->listAry[u]) == 0){
            append(G->listAry[u], v);
        }
        // case 2: if the list is not empty, start comparing
        else{
            moveFront(G->listAry[u]); // move the cursor to front
            if(get(G->listAry[u]) > v){
                prepend(G->listAry[u], v);
            }
            else{
                while(index(G->listAry[u]) != -1){
                    moveNext(G->listAry[u]);
                    if(get(G->listAry[u]) > v){
                        prepend(G->listAry[u], v);
                        break;
                    }
                    else if(index(G->listAry[u]) == -1){
                        append(G->listAry[u],v);
                    }
                } // end while
            }
        }
        // case 1: if the adjacency list is empty
        if(length(G->listAry[v]) == 0){
            append(G->listAry[v], u);
        }
        // case 2: if the list is not empty, start comparing
        else{
            moveFront(G->listAry[v]); // move the cursor to front
            if(get(G->listAry[v]) > u){
                prepend(G->listAry[v],u);
            }
            else{
                while(index(G->listAry[v]) != -1){
                    moveNext(G->listAry[v]);
                    if(get(G->listAry[v]) > u){
                        prepend(G->listAry[v],u);
                        break;
                    }
                    else if(index(G->listAry[v]) == -1){
                        append(G->listAry[v],u);
                    }
                } // end while
            }
        }

        // increase the number of edge by 1
        G->size++;
    }
}

// addArc
// append the element into the list ary in "single" direction
void addArc(Graph G, int u, int v){

    if(1 <= u && u <= getOrder(G) && 1 <= v && v <= getOrder(G)){
        // case 1: if the adjacency list is empty
        if(length(G->listAry[u]) == 0){
            append(G->listAry[u], v);
        }
        // case 2: if the list is not empty, start comparing
        else{
            moveFront(G->listAry[u]); // move the cursor to front
            if(get(G->listAry[u]) > v){
                prepend(G->listAry[u], v);
            }
            else{
                while(index(G->listAry[u]) != -1){
                    moveNext(G->listAry[u]);
                    if(get(G->listAry[u]) > v){
                        prepend(G->listAry[u], v);
                        break;
                    }
                    else if(index(G->listAry[u]) == -1){
                        append(G->listAry[u],v);
                    }
                } // end while
            }
        }
        // increase the number of edge by 1
        G->size++;
    }
}

// BFS
void BFS(Graph G, int s){
    if(1 <= s && s <= getOrder(G)){
        int x = 0;
        // initialize the values and colors
        for(int i = 1; i < getOrder(G)+1; i++){
            G->color[i] = 'w';
            G->distance[i] = INF;
            G->parent[i] = NIL;
        }

        // assign the value to the source
        G->color[s] = 'g';
        G->distance[s] = 0;
        G->parent[s] = NIL;
        List queue = newList(); // create a queue from List ADT
        append(queue,s); // implement the idea of enqueue
        x = 0;
        int y = 0;

        while(length(queue) != 0){
            x = front(queue);    // implement the idea of dequeue
            deleteFront(queue);  // implement the idea of dequeue
            moveFront(G->listAry[x]);
            while(index(G->listAry[x]) != -1){
                y = get(G->listAry[x]);
                if(G->color[y] == 'w'){
                    G->color[y] = 'g';
                    G->distance[y] = G->distance[x] + 1;
                    G->parent[y] = x;
                    append(queue,y); // implement the idea of enqueue
                }
                moveNext(G->listAry[x]);
            } // end inner while
            G->color[x] = 'b';
        } // end outer while

        // set the the source vertex
        G->source = s;
    }
}

// DFS
// Pre: length(S) == getOrder(G)
void DFS(Graph G, List S){
    // check if the condition is valid to call
    if(length(S) == getOrder(G)){

        // create a local stack
        List stack = newList();

        // initialize the values and colors
        for(int i = 1; i < getOrder(G)+1; i++){
            G->color[i] = 'w';
            G->parent[i] = NIL;
        }

        // move the input stack to 1st position
        moveFront(S);

        // local variable for visit function
        int time = 0;
        int count = 0;

        // visiting the vertex
        while(index(S) != -1){
            count = get(S); // get cursor position
            if(G->color[count] == 'w') // check if the vertex is 'visited' or not
            {
                visit(G, stack, &time, count); // helper function of DFS
            }

            // update the cursor
            moveNext(S);
        }

        clear(S);
        moveFront(stack);
        while(index(stack) != -1){
            append(S,get(stack));
            moveNext(stack);
        }

        // free the memory
        freeList(&stack);
    }
}

/*** Other Operations ***/

// transpose
Graph transpose(Graph G){
    Graph GT = newGraph(getOrder(G)); // create a empty Graph with same size as original Graph
    for(int i = 1; i < getOrder(G)+1; i++){
        moveFront(G->listAry[i]);
        while(index(G->listAry[i]) != -1){
            addArc(GT, get(G->listAry[i]),i);
            moveNext(G->listAry[i]);
        }
    }
    return GT;
}

// copyGraph
// deep copy
Graph copyGraph(Graph G){
    Graph copyG = newGraph(getOrder(G)); // create a empty Graph with same size as original Graph
    for(int i = 1; i < getOrder(copyG); i++){
        if(length(G->listAry[i]) != 0){ // if the current index of adjacency list has elements inside, start copying
             moveFront(G->listAry[i]); // set up the cursor
        while(index(G->listAry[i]) != -1){
            addArc(copyG, i, get(G->listAry[i])); // insert the value
            moveNext(G->listAry[i]);
            }
        }
    }
    return copyG;
}

// printGraph
void printGraph(FILE *out ,Graph G){
    for(int i = 1; i <= getOrder(G) ; i++){
        fprintf(out, "%d: ",i);
        printList(out, G->listAry[i]);
        fprintf(out,"\n");
    }
}


/*** Helper function ***/

//visit
// helper function of DFS
void visit(Graph G, List S, int *time, int u){
    int neighbor = 0;
    G->color[u] = 'g';                            // line#1 in DFS
    (*time)++;                                    // line#2 in DFS
    G->DT[u] = *time;                             // line#2 in DFS
    moveFront(G->listAry[u]);
    while(index(G->listAry[u])!= -1){            // line#3 in DFS
        neighbor = get(G->listAry[u]);
        if(G->color[neighbor] == 'w'){           // line#4 in DFS
            G->parent[neighbor] = u;             // line#5 in DFS
            visit(G, S, time, neighbor);         // line#6 in DFS
        }
        moveNext(G->listAry[u]);
    }
    G->color[u] = 'b';                           // line#7 in DFS
    (*time)++;                                   // line#8 in DFS
    G->FT[u] = *time;
    prepend(S,u);                                // push the finished vertex into Stack
}
