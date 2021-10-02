#include "Node.h"
#include <iostream>

Node::Node(int row, int col, int dist_traveled)
{
    this->dist_traveled = dist_traveled;
    this->row = row;
    this->col = col;
}

Node::~Node()
{
}

int Node::getRow()
{
    return row;
}

int Node::getCol()
{
    return col;
}

int Node::getDistanceTraveled()
{
    return dist_traveled;
}

void Node::setRow(int row)
{
    this->row = row;
}

void Node::setCol(int col)
{
    this->col = col;
}

void Node::setDistanceTraveled(int dist_traveled)
{
    this->dist_traveled = dist_traveled;
}

int Node::getEstimatedDist2Goal(Node *goal)
{
    int manhatDist = abs(goal->getCol() - getCol()) + abs(goal->getRow() - getRow());
    int estDist = dist_traveled + manhatDist;
    return estDist;
}

// my functions
char Node::getChar()
{
    return symbol;
}

void Node::setChar(char symbol)
{
    this->symbol = symbol;
}

