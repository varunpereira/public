#include "Player.h"

Player::Player(std::string n, int s)
{
    this->name = n;
    this->score = s;
    this->hand = new LinkedList();
}

Player::~Player()
{
    delete hand;
}

std::string Player::getName()
{
    return name;
}

int Player::getScore()
{
    return score;
}

void Player::setScore(int newTileScore)
{
    score += newTileScore;
}

LinkedList *Player::getHand()
{
    return hand;
}

void Player::addTileToHand(Tile *tile)
{
    hand->addBack(tile);
}

void Player::deleteTileFromHand(int index)
{
    hand->deleteAt(index);
}

void Player::printHand()
{
    int size = hand->size();
    for (int i = 0; i < size - 1; i++)
    {
        hand->get(i)->print();
        std::cout << ",";
    }
    if(size == 0){
        hand->get(0)->print();
    }else{
        hand->get(size - 1)->print();
    }
    
}

int Player::getHandSize() {
    int handSize = hand->size();
    return handSize;
}

int Player::getTileIndex(Tile *tile)
{
    int tileIndex = -1;
    int size = hand->size();
    for (int i = 0; i < size && tileIndex == -1; i++)
    {
        if (hand->get(i)->getColour() == tile->getColour() && hand->get(i)->getShape() == tile->getShape())
        {
            tileIndex = i;
        }
    }
    // return it even if its -1, which is invalid
    return tileIndex;
}

bool Player::hasTile(Tile *tile)
{
    bool hasTile = false;
    for (int i = 0; i < hand->size(); i++)
    {
        if (hand->get(i)->equals(tile))
        {
            hasTile = true;
        }
    }
    return hasTile;
}

bool Player::removeTileFromHand(Tile *tile)
{
    bool done = false;
    for (int i = 0; i < hand->size(); i++)
    {
        if (hand->get(i)->equals(tile))
        {
            hand->deleteAt(i);
            done = true;
        }
    }
    return done;
}
