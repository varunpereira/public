#ifndef COSC_ASSIGN_ONE_NODE
#define COSC_ASSIGN_ONE_NODE

#include "Types.h"

class Node
{
public:
    /*                                           */
    /* DO NOT MOFIFY ANY CODE IN THIS SECTION    */
    /*                                           */

    // Constructor/Desctructor
    Node(int row, int col, int dist_traveled);
    ~Node();

    // get row-coodinate of the node
    int getRow();

    // get column-coodinate of the node
    int getCol();

    //getter and setter for distance traveled
    int getDistanceTraveled();
    void setDistanceTraveled(int dist_traveled);

    //getter for estimated dist to goal - need to return -> Manhatten distance + distance traveled
    int getEstimatedDist2Goal(Node *goal);

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */

    // getter to return a node's symbol
    char getChar();

    // setter to update a node's symbol
    void setChar(char symbol);

    // setter to  update a node's row
    void setRow(int row);

    // setter to  update a node's column
    void setCol(int row);

private:
    /*                                           */
    /* DO NOT MOFIFY THESE VARIABLES             */
    /*                                           */

    int row;
    int col;
    int dist_traveled; // distance from start node

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */

    // alows a node's symbol to be returned or changed
    char symbol;
};

#endif //COSC_ASSIGN_ONE_NODE
