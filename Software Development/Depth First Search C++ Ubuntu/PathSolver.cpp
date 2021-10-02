#include "PathSolver.h"
#include <iostream>

PathSolver::PathSolver()
{
}

PathSolver::~PathSolver()
{
}

// MILESTONE 2
// Function Name: forwardSearch
// Function Purpose: From S node, try all possible paths to get to
// G node, where environment is made by symbols.
// Function Algo:
// 1. Find and save the startnode and goalnode in the env 2D array.
// 2. If goal is not found, print error message.
// 3. Else, if goal node does exists:
// 4. While goalnode is not found, find currentnode, which is the node
// from openlist that has smallest estimated distance to goalnode.
// 5. Then, select all reachable nodes that currentnode can reach from its
// current location.
// 6. And, this node will be represented by p, set dist travelleed of q to 1
// more than currentnode, and updates it char, and add q to openlist only if
// its not already in there.
// 7. Add currentnode to closedlist.
// 8. Only finish loop when goalnode is found, knowing that it exists.
void PathSolver::forwardSearch(Env env)
{
    this->nodeList = new NodeList();
    int startNodeDist = 0;
    this->startNode = new Node(-1, -1, -1);
    this->goalNode = new Node(-1, -1, -1);
    startNode->getChar();
    goalNode->getChar();
    bool goalNodeExists = false;

    for (int y = 0; y < ENV_DIM; y += 1)
    {
        for (int x = 0; x < ENV_DIM; x += 1)
        {
            std::cout << env[y][x];
            Node *nodeN = new Node(y, x, startNodeDist);
            nodeN->setChar(env[y][x]);
            nodeList->addElement(nodeN);
            if (nodeN->getChar() == 'S')
            {
                startNode = nodeN;
            }
            else if (nodeN->getChar() == 'G')
            {
                goalNode = nodeN;
                goalNodeExists = true;
            }
        }
        std::cout << std::endl;
    }

    // closed list
    this->nodesExplored = new NodeList();
    // openlist length is 1
    NodeList *openList = new NodeList();

    if (goalNodeExists == false)
    {
        std::cout << std::endl
                  << "No Goal Node exists on grid!" << std::endl;
    }

    Node *currentNode = startNode;
    bool goalNodeFound = false;

    while (goalNodeFound != true && goalNodeExists == true)
    {
        //want currentNode value to be updated
        //first round
        if (nodesExplored->getLength() == 0)
        {
            openList->addElement(currentNode);
            currentNode = openList->getNode(0);
        }
        else if (nodesExplored->getLength() > 0)
        {
            NodeList *openListFiltered = new NodeList();
            for (int i = 0; i < openList->getLength(); i += 1)
            {
                bool exists = false;
                for (int j = 0; j < nodesExplored->getLength(); j += 1)
                {
                    if (openList->getNode(i) == nodesExplored->getNode(j))
                    {
                        exists = true;
                    }
                }
                if (exists == false)
                {
                    openListFiltered->addElement(openList->getNode(i));
                }
            }

            Node *openListShortestNode = openListFiltered->getNode(0);
            for (int i = 0; i < openListFiltered->getLength(); i += 1)
            {
                if (openListShortestNode->getEstimatedDist2Goal(goalNode) > openListFiltered->getNode(i)->getEstimatedDist2Goal(goalNode))
                {
                    openListShortestNode = openListFiltered->getNode(i);
                }
            }
            currentNode = openListShortestNode;
        }
        //gives currentnode which is lowest estdist to goalnode

        // Add q to openList
        // up
        if (env[currentNode->getRow() - 1][currentNode->getCol()] == '.' || env[currentNode->getRow() - 1][currentNode->getCol()] == 'G')
        {
            Node *q = new Node(currentNode->getRow() - 1, currentNode->getCol(), 0);
            q->setDistanceTraveled(currentNode->getDistanceTraveled() + 1);
            q->setChar('.');
            if (openList->inList(q) == false)
            {
                openList->addElement(q);
            }
        }
        // right
        if (env[currentNode->getRow()][currentNode->getCol() + 1] == '.' || env[currentNode->getRow()][currentNode->getCol() + 1] == 'G')
        {
            Node *q = new Node(currentNode->getRow(), currentNode->getCol() + 1, 0);
            q->setDistanceTraveled(currentNode->getDistanceTraveled() + 1);
            q->setChar('.');
            if (openList->inList(q) == false)
            {
                openList->addElement(q);
            }
        }
        // down
        if (env[currentNode->getRow() + 1][currentNode->getCol()] == '.' || env[currentNode->getRow() + 1][currentNode->getCol()] == 'G')
        {
            Node *q = new Node(currentNode->getRow() + 1, currentNode->getCol(), 0);
            q->setDistanceTraveled(currentNode->getDistanceTraveled() + 1);
            q->setChar('.');
            if (openList->inList(q) == false)
            {
                openList->addElement(q);
            }
        }

        // left
        if (env[currentNode->getRow()][currentNode->getCol() - 1] == '.' || env[currentNode->getRow()][currentNode->getCol() - 1] == 'G')
        {
            Node *q = new Node(currentNode->getRow(), currentNode->getCol() - 1, 0);
            q->setDistanceTraveled(currentNode->getDistanceTraveled() + 1);
            q->setChar('.');
            if (openList->inList(q) == false)
            {
                openList->addElement(q);
            }
        }

        nodesExplored->addElement(currentNode);

        if (env[currentNode->getRow()][currentNode->getCol()] == 'G')
        {
            // now loop ends, so no more
            currentNode->setChar('G');
            goalNodeFound = true;
        }
    }
}

// MILESTONE 2
// Function Name: getNodesExplored
// Function Purpose: return all the nodes that have been explored
// in the closedlist.
// Function Algo:
// 1. return closedlist
NodeList *PathSolver::getNodesExplored()
{
    return nodesExplored;
}

// MILESTONE 3
// Function Name: getpath
// Function Purpose: from the closedlist, chose nodes that
// will give you shortest path from S node to G node.
// Function Algo:
// 1. While tempNode's dist travelled is at least 1:
// 2. Then, starting from the goalnode location (y,x),
// loop through the closedlist nodes, and if any of those nodes
// have a dist travelled to tempnode dist travlled - 1,
// and, is only 1 node away in any of the 4 directions,
// then add it to solution nodelist.
// 3. After the above loop is complete, Reverse the order of the solution,
// so that it is now from startnode to goalnode (instead of other way around).
// 4. For this reversedSolution nodelist, if the next element is 1 node away from
// the next next element's location, then change the symbol of that node to the
// corresponding direction.
// 5. reutnr the reversedsolution nodelist
NodeList *PathSolver::getPath(Env env)
{
    std::cout << std::endl;
    Node *goalNode = new Node(-1, -1, -1);
    NodeList *solutionNodes = new NodeList;
    goalNode = nodesExplored->getNode(nodesExplored->getLength() - 1);
    Node *tempNode = goalNode;
    solutionNodes->addElement(goalNode);
    while (tempNode->getDistanceTraveled() >= 1)
    {
        for (int i = 0; i < nodesExplored->getLength(); i++)
        {
            if (nodesExplored->getNode(i)->getDistanceTraveled() == (tempNode->getDistanceTraveled()) - 1)
            {

                if (nodesExplored->getNode(i)->getCol() == tempNode->getCol() - 1 && nodesExplored->getNode(i)->getRow() == tempNode->getRow())
                {
                    tempNode = nodesExplored->getNode(i);
                    solutionNodes->addElement(tempNode);
                }
                else if (nodesExplored->getNode(i)->getCol() == tempNode->getCol() + 1 && nodesExplored->getNode(i)->getRow() == tempNode->getRow())
                {
                    tempNode = nodesExplored->getNode(i);
                    solutionNodes->addElement(tempNode);
                }
                else if (nodesExplored->getNode(i)->getCol() == tempNode->getCol() && nodesExplored->getNode(i)->getRow() == tempNode->getRow() - 1)
                {
                    tempNode = nodesExplored->getNode(i);
                    solutionNodes->addElement(tempNode);
                }
                else if (nodesExplored->getNode(i)->getCol() == tempNode->getCol() && nodesExplored->getNode(i)->getRow() == tempNode->getRow() + 1)
                {
                    tempNode = nodesExplored->getNode(i);
                    solutionNodes->addElement(tempNode);
                }
            }
        }
    }

    NodeList *reversedSolutionNodes = new NodeList();
    for (int i = solutionNodes->getLength() - 1; i >= 0; i -= 1)
    {
        reversedSolutionNodes->addElement(solutionNodes->getNode(i));
    }

    for (int i = 0; i < reversedSolutionNodes->getLength() - 2; i += 1)
    {
        //right
        if (reversedSolutionNodes->getNode(i + 1)->getCol() + 1 == reversedSolutionNodes->getNode(i + 2)->getCol())
        {
            reversedSolutionNodes->getNode(i + 1)->setChar('>');
        }
        //left
        else if (reversedSolutionNodes->getNode(i + 1)->getCol() - 1 == reversedSolutionNodes->getNode(i + 2)->getCol())
        {
            reversedSolutionNodes->getNode(i + 1)->setChar('<');
        }
        //down
        else if (reversedSolutionNodes->getNode(i + 1)->getRow() + 1 == reversedSolutionNodes->getNode(i + 2)->getRow())
        {
            reversedSolutionNodes->getNode(i + 1)->setChar('v');
        }
        //up
        else if (reversedSolutionNodes->getNode(i + 1)->getRow() - 1 == reversedSolutionNodes->getNode(i + 2)->getRow())
        {
            reversedSolutionNodes->getNode(i + 1)->setChar('^');
        }
    }

    return reversedSolutionNodes;
}
