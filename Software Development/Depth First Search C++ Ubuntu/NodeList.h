#ifndef COSC_ASSIGN_ONE_NODELIST
#define COSC_ASSIGN_ONE_NODELIST

#include "Types.h"
#include "Node.h"

class NodeList
{
public:
    /*                                           */
    /* DO NOT MOFIFY ANY CODE IN THIS SECTION    */
    /*                                           */

    // Constructor/Desctructor
    NodeList();
    ~NodeList();

    // Copy Constructor
    // Produces a DEEP COPY of the NodeList
    NodeList(NodeList &other);

    // Number of elements in the NodeList
    int getLength();

    // Add a COPY node element to the BACK of the nodelist.
    void addElement(Node *newNode);

    // Get a pointer to the ith node in the node list
    Node *getNode(int i);

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */

    // checks is a node is already in the nodelist
    bool inList(Node *newNode);

private:
    /*                                           */
    /* DO NOT MOFIFY THESE VARIABLES             */
    /*                                           */

    // NodeList: list of node objects
    // You may assume a fixed size for M1, M2, M3
    Node *nodes[NODE_LIST_ARRAY_MAX_SIZE];

    // Number of nodes currently in the NodeList
    int length;

    /*                                           */
    /* YOU MAY ADD YOUR MODIFICATIONS HERE       */
    /*                                           */
};

#endif //COSC_ASSIGN_ONE_NODELIST
