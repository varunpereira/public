
#include "Tile.h"
#include "TileCodes.h"
#include <iostream>

Tile::Tile(Colour colour, Shape shape){
    this->colour = colour;
    this->shape=shape;
}

Tile::Tile(Tile* other){
    this->colour = other->getColour();
    this->shape = other->getShape();
}

void Tile::print(){
    std::cout<<colour<<shape;
}

Colour Tile::getColour(){
    return this->colour;
}

Shape Tile::getShape(){
    return this->shape;
}

bool Tile::equals(Tile* tile){
    return (this->colour==tile->getColour() && this->shape==tile->getShape());
}