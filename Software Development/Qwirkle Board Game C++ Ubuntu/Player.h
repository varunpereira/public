
#ifndef ASSIGN2_PLAYER_H
#define ASSIGN2_PLAYER_H

#include <string>
#include "LinkedList.h"
#include <iostream>

class Player
{

public:
    Player(std::string n, int s);
    ~Player();

    std::string getName();
    int getScore();
    LinkedList *getHand();

    void setScore(int newTileScore);

    void addTileToHand(Tile *tile);

    void deleteTileFromHand(int index);
    int getTileIndex(Tile *tile);

    int getHandSize();

    void printHand();

    bool hasTile(Tile *tile);

    bool removeTileFromHand(Tile *tile);

private:
    std::string name;
    int score;
    LinkedList *hand;
};

#endif