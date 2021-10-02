
#ifndef ASSIGN2_NODE_H
#define ASSIGN2_NODE_H

#include "Tile.h"

class Node {
public:


   //TODO Need deconstructor
   Node(Tile* tile, Node* next);
   Node(Node& other);
   Node();

   Node* getNext();
   void setNext(Node* n);

   Tile* getTile();

   

private:

   Tile*    tile;
   Node*    next;

};



#endif // ASSIGN2_NODE_H
