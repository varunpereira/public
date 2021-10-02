#include "NodeList.h"
#include "Node.h"
#include <iostream>

// initialise to 0 since intially there are no nodes in nodelist
NodeList::NodeList()
{
    // NodeList: list of node objects
    // You may assume a fixed size for M1, M2, M3

    //this value is 1600
    // why do we need below
    //*nodes[NODE_LIST_ARRAY_MAX_SIZE] = {-1,-1,-1};
    // Number of nodes currently in the NodeList
    this->length = 0;
}

NodeList::~NodeList()
{
}

NodeList::NodeList(NodeList &other)
{
    // deep copy of node list, what does that mean?
}

int NodeList::getLength()
{
    return this->length;
}

void NodeList::addElement(Node *newPos)
{
    nodes[length] = newPos;
    this->length += 1;
}

Node *NodeList::getNode(int i)
{
    return nodes[i];
}

// my functions
// inList algo:
// 1. loop through nodes array, and see if received node's location (y,x),
// matches with any nodes' location already in nodelist
bool NodeList::inList(Node *qNode)
{
    bool inList = false;
    for (int i = 0; i < length; i += 1)
    {
        if (nodes[i]->getCol() == qNode->getCol() && nodes[i]->getRow() == qNode->getRow())
        {
            inList = true;
        }
    }
    return inList;
};
