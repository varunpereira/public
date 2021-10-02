#include "Gameboard.h"

Gameboard::Gameboard()
{
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        vector<Tile *> row(BOARD_SIZE, nullptr);
        board.push_back(row);
        //Test for delete
    }
}

Gameboard::~Gameboard()
{
    int size = BOARD_SIZE;
    for (int i = 0; i < size; i++)
    {
        for (Tile *t : board[i])
        {
            delete t;
        }
        board[i].clear();
    }
    board.clear();
}

bool Gameboard::addTile(Tile *tile, char *row, int *col)
{
    char chars[] = CHARS;
    bool done = false;
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        if (chars[i] == *row && board[i][*col] == nullptr)
        {

            //Gets the tiles above and below this tile position
            vector<Tile> sideTiles = getAdjSideTiles(i, *col);
            vector<Tile> colTiles = getAdjVerticleTiles(i, *col);

            if (validatePlacement(*tile, sideTiles, colTiles))
            {
                board[i][*col] = tile;
                done = true;
                this->empty = false;
            }
        }
    }

    return done;
}

void Gameboard::print()
{
    char chars[] = CHARS;

    std::cout << "  ";

    for (int i = 0; i < BOARD_SIZE && i < 10; i++)
    {
        std::cout << i << "  ";
    }
    for (int i = 10; i < BOARD_SIZE; i++)
    {
        std::cout << i << " ";
    }
    std::cout << "\n  ";
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        std::cout << "---";
    }

    for (int i = 0; i < BOARD_SIZE; i++)
    {

        std::cout << "\n"
                  << chars[i] << "|";
        for (int j = 0; j < BOARD_SIZE; j++)
        {
            if (board[i][j] == nullptr)
            {
                std::cout << "  ";
            }
            else
            {
                board[i][j]->print();
            }
            std::cout << "|";
        }
    }
}

std::string Gameboard::getBoardState()
{
    char chars[] = CHARS;
    std::string boardState = "";
    for (int i = 0; i < BOARD_SIZE; i++)
    {
        for (int j = 0; j < BOARD_SIZE; j++)
        {
            if (board[i][j] != nullptr)
            {
                boardState = boardState + board[i][j]->getColour() + to_string(board[i][j]->getShape()) + "@" + chars[i] + to_string(j);
                boardState = boardState + ',';
            }
        }
    }
    return boardState;
}

int Gameboard::getNewTileScore(char charRow, int col)
{
    int intRow = charRow;
    int row = intRow - 65;
    int newTileScore = 0;

    // check up the row by 1, from new tile.
    vector<Tile> sideTiles = getAdjSideTiles(row, col);
    vector<Tile> vertTiles = getAdjVerticleTiles(row, col);
    if (sideTiles.size() == 0 && vertTiles.size() == 0)
    {
        newTileScore = 1;
    }
    else
    {
        //Qwirkle occurs
        if (sideTiles.size() == 5)
        {
            std::cout << "QWIRKLE!!!" << std::endl;
            newTileScore += 6;
        }
        if (vertTiles.size() == 5)
        {
            std::cout << "QWIRKLE!!!" << std::endl;
            newTileScore += 6;
        }
        if (vertTiles.size() == 0 || sideTiles.size() == 0)
        {
            newTileScore += sideTiles.size() + vertTiles.size() + 1;
        }
        else
        {
            newTileScore += sideTiles.size() + 1 + vertTiles.size() + 1;
        }
    }

    return newTileScore;
}

vector<Tile> Gameboard::getAdjSideTiles(int row, int col)
{
    vector<Tile> tiles;
    bool done = false;
    for (int i = col - 1; i >= 0 && !done; i--)
    {
        if (board[row][i] == nullptr)
        {
            done = true;
        }
        else
        {
            tiles.push_back(board[row][i]);
        }
    }
    done = false;
    for (int i = col + 1; i < BOARD_SIZE && !done; i++)
    {
        if (board[row][i] == nullptr)
        {
            done = true;
        }
        else
        {
            tiles.push_back(board[row][i]);
        }
    }
    return tiles;
}

vector<Tile> Gameboard::getAdjVerticleTiles(int row, int col)
{
    vector<Tile> tiles;
    bool done = false;
    for (int i = row - 1; i >= 0 && !done; i--)
    {
        if (board[i][col] == nullptr)
        {
            done = true;
        }
        else
        {
            tiles.push_back(board[i][col]);
        }
    }
    done = false;
    for (int i = row + 1; i < BOARD_SIZE && !done; i++)
    {
        if (board[i][col] == nullptr)
        {
            done = true;
        }
        else
        {
            tiles.push_back(board[i][col]);
        }
    }
    return tiles;
}

bool Gameboard::validatePlacement(Tile tile, vector<Tile> sideTiles, vector<Tile> vertTiles)
{
    //TODO reduce some code duplication and Comment code
    bool valid = true;

    if (sideTiles.size() == 0 && vertTiles.size() == 0 && this->empty == false)
    {
        valid = false;
    }
    bool sideColour = true;
    for (int i = 0; i < (int)sideTiles.size() && sideColour; i++)
    {
        if (sideTiles[i].getColour() != tile.getColour())
        {
            sideColour = false;
        }
    }
    bool sideShape = true;
    for (int i = 0; i < (int)sideTiles.size() && sideShape; i++)
    {
        if (sideTiles[i].getShape() != tile.getShape())
        {
            sideShape = false;
        }
    }

    if ((!sideColour && !sideShape))
    {
        valid = false;
    }
    else if (sideColour && sideShape)
    {
        //Nothing on sides therefore, check the tops
        bool vertShape = true;
        //check all tiles above and below are the same shape and there are no duplicate colours
        for (int i = 0; i < (int)vertTiles.size() && valid; i++)
        {
            if (vertTiles[i].getShape() != tile.getShape() || vertTiles[i].getColour() == tile.getColour())
            {
                vertShape = false;
            }
        }
        bool vertColour = true;
        //check all tiles above and below are the same colour and there are no duplicate shapes
        for (int i = 0; i < (int)vertTiles.size() && valid; i++)
        {
            if (vertTiles[i].getColour() != tile.getColour() || vertTiles[i].getShape() == tile.getShape())
            {
                vertColour = false;
            }
        }
        if (!vertColour && !vertShape)
        {
            valid = false;
        }
    }
    else if (sideColour && !sideShape)
    {

        //Check the row to see if there are duplicate shapes in the row
        for (int i = 0; i < (int)sideTiles.size(); i++)
        {
            if (sideTiles[i].getShape() == tile.getShape())
            {
                valid = false;
            }
        }
        //check all tiles above and below are the same shape and there are no duplicate colours
        for (int i = 0; i < (int)vertTiles.size() && valid; i++)
        {
            if (vertTiles[i].getShape() != tile.getShape() || vertTiles[i].getColour() == tile.getColour())
            {
                valid = false;
            }
        }
    }
    else if (sideShape && !sideColour)
    {

        //Check there are no duplicate colours in the row
        for (int i = 0; i < (int)sideTiles.size(); i++)
        {
            if (sideTiles[i].getColour() == tile.getColour())
            {
                valid = false;
            }
        }
        //check all tiles above and below are the same colour and there are no duplicate shapes
        for (int i = 0; i < (int)vertTiles.size() && valid; i++)
        {
            if (vertTiles[i].getColour() != tile.getColour() || vertTiles[i].getShape() == tile.getShape())
            {
                valid = false;
            }
        }
    }

    return valid;
}
