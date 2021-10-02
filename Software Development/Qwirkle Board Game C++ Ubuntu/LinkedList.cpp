
#include "LinkedList.h"
#include <iostream>

LinkedList::LinkedList()
{
   Node *node = nullptr;
   this->head = node;
}

LinkedList::~LinkedList()
{
}

int LinkedList::size()
{
   int s = 0;
   Node *tmp = this->head;
   if (tmp != nullptr)
   {
      while (tmp->getNext() != nullptr)
      {
         s++;
         tmp = tmp->getNext();
      }
      s++;
   }
   return s;
}

int LinkedList::size(Node *node)
{
   int retval = 0;
   if (node == nullptr)
   {
      retval = 0;
   }
   else
   {
      retval = 1 + size(node->getNext());
   }
   return retval;
}

void LinkedList::clear()
{
   for (int i = 0; i < this->size(); i++)
   {
      delete get(i);
   }
   Node *node = new Node();
   node = nullptr;
   this->head = node;
}

void LinkedList::clear(Node *node)
{
   if (node->getNext() == nullptr)
   {
      delete node;
   }
   else
   {
      clear(node->getNext());
      delete node;
   }
}

Tile *LinkedList::get(int i)
{
   Node *tmp = this->head;
   int index = 0;
   while (index < i)
   {
      tmp = tmp->getNext();
      index++;
      if (tmp == nullptr)
      {
         std::cout << "The index is not available. ";
      }
   }
   return tmp->getTile();
}

void LinkedList::addFront(Tile *tile)
{
   Node *newHead = new Node(tile, this->head);
   this->head = newHead;
}

void LinkedList::addBack(Tile *tile)
{
   if (this->head == nullptr)
   {
      this->head = new Node(tile, nullptr);
   }
   else
   {
      Node *newBack = new Node(tile, nullptr);
      Node *tmp = this->head;
      int index = 0;
      while (tmp->getNext() != nullptr)
      {
         index++;
         tmp = tmp->getNext();
      }
      tmp->setNext(newBack);
   }
}

void LinkedList::addAt(Tile *tile, int index)
{
   Node *n = this->head;
   for (int i = 1; i < index; i++)
   {
      n = n->getNext();
   }

   Node *nAfter = n->getNext();
   n->setNext(new Node(tile, nAfter));
}

void LinkedList::deleteFront()
{
   //works for now, but bad memory management
   //need to create a pointer to delete the old head, then delete the pointer
   this->head = head->getNext();
}

void LinkedList::deleteBack()
{
   if (head->getNext() == NULL)
   {
      delete head;
      head = NULL;
   }
   else
   {
      Node *nextToEnd = head;
      Node *end = head->getNext();
      while (end->getNext() != NULL)
      {
         nextToEnd = end;
         end = end->getNext();
      }
      delete end;
      nextToEnd->setNext(nullptr);
   }
}

void LinkedList::deleteAt(int index)
{
   if (index == 0)
   {
      Node *n = this->head;

      if (this->size() == 0)
      {
         this->head = nullptr;
      }
      else
      {
         this->head = n->getNext();
      }

      delete n;
   }
   else
   {
      Node *n = this->head;
      for (int i = 1; i < index; i++)
      {
         n = n->getNext();
      }

      Node *deleted = n->getNext();
      Node *nAfter = n->getNext()->getNext();
      n->setNext(nAfter);
      delete deleted;
   }
}
