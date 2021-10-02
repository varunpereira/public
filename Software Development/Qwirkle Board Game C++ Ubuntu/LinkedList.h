
#ifndef ASSIGN2_LINKEDLIST_H
#define ASSIGN2_LINKEDLIST_H

#include "Node.h"

class LinkedList {
public:

   LinkedList();
   ~LinkedList();

   int size();
   int size(Node* node);
   void clear();
   void clear(Node* node);
   Tile* get(int i);

   void addFront(Tile* tile);
   void addBack(Tile* tile);
   void addBack(Node* node);
   void addAt(Tile* tile, int index);
   
   void deleteFront();
   void deleteBack();
   void deleteBack(Node* node);
   void deleteAt(int index);

private:
   Node* head;
};

#endif // ASSIGN2_LINKEDLIST_H
