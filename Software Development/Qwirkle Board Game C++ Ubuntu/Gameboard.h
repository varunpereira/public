
#ifndef ASSIGN2_GAMEBOARD_H
#define ASSIGN2_GAMEBOARD_H

#include <vector>
#include <string>
using namespace std;
#include <iostream>
#include "Tile.h"

#define BOARD_SIZE 26
#define CHARS                                                                                                                            \
    {                                                                                                                                    \
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' \
    }

class Gameboard
{
public:
    Gameboard();
    ~Gameboard();

    bool addTile(Tile *tile, char *row, int *col);

    void print();

    std::string getBoardState();

    int getNewTileScore(char row, int col);

private:
    vector<vector<Tile *>> board;
    vector<Tile> getAdjSideTiles(int row, int col);
    vector<Tile> getAdjVerticleTiles(int row, int col);
    bool empty = true;

    bool validatePlacement(Tile tile, vector<Tile> sideTiles, vector<Tile> vertTiles);
};

#endif
