#ifndef COSC_ASSIGN_ONE_PATHSOLVER
#define COSC_ASSIGN_ONE_PATHSOLVER

#include "Node.h"
#include "NodeList.h"
#include "Types.h"

class PathSolver
{
public:
    /*                                           */
    /* DO NOT MOFIFY ANY CODE IN THIS SECTION    */
    /*                                           */

    // Constructor/Destructor
    PathSolver();
    ~PathSolver();

    // Execute forward search algorithm
    // To be implemented for Milestone 2
    void forwardSearch(Env env);

    // Get a DEEP COPY of the explored NodeList in forward search
    // To be implemented for Milestone 2
    NodeList *getNodesExplored();

    // Execute backtracking and Get a DEEP COPY of the path the
    // robot should travel
    // To be implemented for Milestone 3
    NodeList *getPath(Env env);

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */
    // identifies startnode in grid
    Node *startNode;
    // identifies goalnode in grid
    Node *goalNode;

private:
    /*                                           */
    /* DO NOT MOFIFY THESE VARIABLES             */
    /*                                           */

    // Nodes explored in forward search algorithm
    NodeList *nodesExplored;
    NodeList *nodeList;

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */
};

#endif //COSC_ASSIGN_ONE_PATHSOLVER
