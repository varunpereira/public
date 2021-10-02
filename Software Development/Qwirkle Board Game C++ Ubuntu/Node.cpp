
#include "Node.h"

Node::Node(Tile* tile, Node* next)
{
    this->tile=tile;
    this->next=next;
}

Node::Node(){
    this->next= nullptr;
}

Node::Node(Node& other)
{
   // TODO
}

Node* Node::getNext(){
    return this->next;
}


void Node::setNext(Node* n){
    this->next = n;
}


Tile* Node::getTile(){
    return this->tile;
}