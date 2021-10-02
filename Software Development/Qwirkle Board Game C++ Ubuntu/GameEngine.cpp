#include "GameEngine.h"
#include "TileCodes.h"
#include "LinkedList.h"

#include <array>
#include <iostream>
#include <fstream>
#include <sstream>

GameEngine::GameEngine()
{
    this->TileBag = new LinkedList();
    this->board = new Gameboard();
}

GameEngine::~GameEngine()
{
}

void GameEngine::getNextPlayer()
{
    int index = 0;
    for (int i = 0; i < 2; i++)
    {
        if (Players[i] == currentPlayer)
        {
            index = i;
        }
    }
    if (currentPlayer == Players[1])
    {
        currentPlayer = Players[0];
    }
    else
    {
        currentPlayer = Players[index + 1];
    }
}

void GameEngine::startNewGame(std::string p1name, std::string p2name, bool helpStatus, bool invalidStatus)
{
    helpOptionIsOn = helpStatus;
    invalidOptionIsOn = invalidStatus;

    Players[0] = new Player(p1name, 0);
    Players[1] = new Player(p2name, 0);

    this->board = new Gameboard();

    this->currentPlayer = Players[0];
    char colours[] = {RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE};
    int shapes[] = {CIRCLE, STAR_4, DIAMOND, SQUARE, STAR_6, CLOVER};

    int numTileAttributes = 6; //Find a better way of this thats not hard coded. Needs to reflect colours and shapes array size
    //Adds all the tiles to the tile bag
    for (int i = 0; i < numTileAttributes; i++)
    {
        for (int j = 0; j < numTileAttributes; j++)
        {
            TileBag->addFront(new Tile(colours[i], shapes[j]));
        }
    }

    //Assigns a hand of tiles to the two players
    assignHand(Players[0]);
    assignHand(Players[1]);
    //Game is setup so now the game is run through this helper method
    playGame();
}

void GameEngine::loadGame(std::vector<std::string> fileInputs, bool helpStatus, bool invalidStatus)
{
    helpOptionIsOn = helpStatus;
    invalidOptionIsOn = invalidStatus;

    std::string substr;
    std::vector<Tile> boardTiles;
    this->loadPlayers(fileInputs, 2);
    stringstream s_stream3(fileInputs[6]);
    while (getline(s_stream3, substr, ','))
    {
        TileBag->addFront(new Tile(substr[0], substr[1] - '0'));
    }

    stringstream tilesToAdd(fileInputs[8]);
    std::vector<char> boardTileColours;
    std::vector<int> boardTileShapes;
    std::vector<char> boardTileRows;
    std::vector<int> boardTileColumns;
    int boardTileCount = 0;
    while (getline(tilesToAdd, substr, ','))
    {
        std::cout << substr << std::endl;
        boardTileColours.push_back(substr[0]);
        boardTileShapes.push_back(substr[1] - '0');
        boardTileRows.push_back(substr[3]);
        std::size_t pos = substr.find("@") + 2;
        std::string numstr = substr.substr(pos);
        boardTileColumns.push_back(stoi(numstr));
        boardTileCount++;
    }
    for (int i = 0; i < boardTileCount; i++)
    {
        this->board->addTile(new Tile(boardTileColours[i], boardTileShapes[i]), &boardTileRows[i], &boardTileColumns[i]);
    }

    for (Player *p : Players)
    {
        if (p->getName() == fileInputs[9])
        {
            this->currentPlayer = p;
        }
    }
    //Once all the values are correctly added back to the gamestate, the game can be played
    playGame();
}

void GameEngine::loadPlayers(std::vector<std::string> fileInputs, int playerCount)
{
    std::string substr;
    std::vector<Tile> tmpTiles;
    for (int i = 0; i < playerCount; i++)
    {
        Players[i] = new Player(fileInputs[3 * i], stoi(fileInputs[3 * i + 1]));
        stringstream s_stream1(fileInputs[3 * i + 2]);
        while (getline(s_stream1, substr, ','))
        {
            Players[i]->addTileToHand(new Tile(substr[0], substr[1] - '0'));
            tmpTiles.push_back(Tile(substr[0], substr[1]));
        }
        if (Players[i]->getHand()->size() > 6)
        {
            Players[i]->getHand()->deleteBack();
            tmpTiles.pop_back();
        }
    }
}

void GameEngine::saveGame(std::string filename)
{
    std::ofstream saveFile;
    saveFile.open(filename);
    for (int i = 0; i < 2; i++)
    {
        saveFile << Players[i]->getName() << std::endl;
        saveFile << Players[i]->getScore() << std::endl;
        for (int j = 0; j < Players[i]->getHand()->size(); j++)
        {
            saveFile << Players[i]->getHand()->get(j)->getColour() << Players[i]->getHand()->get(j)->getShape() << ',';
        }
        saveFile << std::endl;
    }

    for (int i = 0; i < TileBag->size(); i++)
    {
        saveFile << TileBag->get(i)->getColour() << TileBag->get(i)->getShape() << ',';
    }
    saveFile << std::endl;
    saveFile << "26,26" << std::endl;
    saveFile << this->board->getBoardState() << std::endl;

    saveFile << currentPlayer->getName() << std::endl;

    saveFile.close();

    std::cout << std::endl;
    std::cout << "Game '" << filename << "' saved successfully!" << std::endl;
    std::cout << "--------------------------------------" << std::endl;
    playGame();
}

//Gets a random tile from the tilebag and then removes that tile from the tile bag
//before returning a copy of that tile
Tile *GameEngine::getTileFromBag()
{
    Tile *tile;
    if (TileBag->size() == 1)
    {
        tile = new Tile(TileBag->get(0));
        TileBag->deleteAt(0);
    }
    else
    {
        //Make the seed for random equal to the time
        srand(time(0));
        //Gets the the index of where the random tile is situated
        int randIndex = rand() % TileBag->size();

        tile = new Tile(TileBag->get(randIndex));

        TileBag->deleteAt(randIndex);
    }

    return tile;
}

//Gets 6 random tiles from the tilebag and adds them to the players hand
void GameEngine::assignHand(Player *player)
{
    for (int i = 0; i < 6; i++)
    {
        player->addTileToHand(getTileFromBag());
    }
}

//This method is designed to actually run the game through a loop
//This method can only be called once the game has either been loaded or initialised
void GameEngine::playGame()
{
    bool gameDone = false;
    while (!gameDone)
    {

        if (TileBag->size() <= 1 && (Players[0]->getHandSize() <= 0 || Players[1]->getHandSize() <= 0))
        {
            gameDone = true;
            std::cout << std::endl;
            std::cout << "Game Over!" << std::endl;
            std::cout << "Score for " << Players[0]->getName() << ": " << Players[0]->getScore() << std::endl;
            std::cout << "Score for " << Players[1]->getName() << ": " << Players[1]->getScore() << std::endl;
            if (Players[0]->getScore() > Players[1]->getScore())
            {
                std::cout << "Player " << Players[0]->getName() << " won!" << std::endl;
            }
            else if (Players[1]->getScore() > Players[0]->getScore())
            {
                std::cout << "Player " << Players[1]->getName() << " won!" << std::endl;
            }
            else
            {
                std::cout << "It was a tie between both Players!" << std::endl;
            }
            std::cout << std::endl;
            std::cout << "Goodbye!" << std::endl;
            delete this->TileBag;
            delete this->board;
        }
        else
        {
            std::cout << std::endl;
            std::cout << currentPlayer->getName() << ", it's your turn!" << std::endl;
            std::cout << "Score for " << Players[0]->getName() << ": " << Players[0]->getScore() << std::endl;
            std::cout << "Score for " << Players[1]->getName() << ": " << Players[1]->getScore() << std::endl;
            board->print();
            std::cout << std::endl;
            std::cout << std::endl;
            std::cout << "Your hand is:" << std::endl;
            currentPlayer->printHand();
            std::cout << std::endl;
            std::cout << std::endl;

            bool playerMoveDone = false;
            while (!playerMoveDone)
            {
                std::cout << "> ";
                std::string input;
                std::cin >> input;
                if (input == "^D")
                {
                    std::cout << "Goodbye" << std::endl;
                    exit(EXIT_SUCCESS);
                }
                else if (input == "place")
                {
                    std::cin >> input;
                    if (input == "^D")
                    {
                        std::cout << "Goodbye" << std::endl;
                        delete this->TileBag;
                        delete this->board;
                        exit(EXIT_SUCCESS);
                    }
                    Tile *tile = new Tile(input[0], (input[1] - '0'));
                    if (!currentPlayer->hasTile(tile))
                    {
                        std::cout << "Error: This tile is not in your hand!" << std::endl;
                    }
                    else
                    {
                        std::cin >> input;
                        if (input == "at")
                        {
                            std::cin >> input;
                            if (input == "^D")
                            {
                                std::cout << "Goodbye" << std::endl;
                                delete this->TileBag;
                                delete this->board;
                                exit(EXIT_SUCCESS);
                            }
                            char row = input[0];
                            //Converts the column string into an integer.
                            input.erase(input.begin());
                            int col = std::stoi(input);
                            if (!board->addTile(tile, &row, &col))
                            {
                                std::cout << "Error: Tile placed at invalid location" << std::endl;
                            }
                            else
                            {
                                int newTileScore = board->getNewTileScore(row, col);
                                currentPlayer->setScore(newTileScore);

                                currentPlayer->removeTileFromHand(tile);
                                if (TileBag->size() > 0)
                                {
                                    currentPlayer->addTileToHand(getTileFromBag());
                                }
                                playerMoveDone = true;
                            }
                        }
                    }
                }
                else if (input == "replace")
                {
                    std::cin >> input;
                    if (input == "^D")
                    {
                        std::cout << "Goodbye" << std::endl;
                        exit(EXIT_SUCCESS);
                    }
                    Tile *tile = new Tile(input[0], (input[1] - '0'));
                    currentPlayer->deleteTileFromHand(currentPlayer->getTileIndex(tile));
                    TileBag->addBack(tile);
                    currentPlayer->addTileToHand(getTileFromBag());
                    std::cout << std::endl;
                    std::cout << "Your NEW hand is:" << std::endl;
                    currentPlayer->printHand();
                    std::cout << std::endl;
                    playerMoveDone = true;
                }
                else if (input == "save")
                {
                    std::cout << "Please input the name of the file you wish to save as:" << std::endl;
                    std::cout << "> ";
                    std::cin >> input;
                    if (input == "^D")
                    {
                        std::cout << "Goodbye" << std::endl;
                        delete this->TileBag;
                        delete this->board;
                        exit(EXIT_SUCCESS);
                    }
                    input = input + ".save";
                    this->saveGame(input);
                    gameDone = true;
                    playerMoveDone = true;
                }
                else if (input == "quit" || input == "^D")
                {
                    gameDone = true;
                    playerMoveDone = true;
                    std::cout << std::endl;
                    std::cout << "Goodbye!" << std::endl;
                }
                else if (input == "help" && helpOptionIsOn)
                {
                    std::cout << std::endl;
                    std::cout << "HELP input! You can input: 'place', 'replace', 'save', 'quit', 'help'" << std::endl;
                    std::cout << std::endl;
                }
                else
                {
                    if (invalidOptionIsOn)
                    {
                        std::cout << std::endl;
                        std::cout << "INVALID input! You can input: 'place', 'replace', 'save', 'quit', 'help'" << std::endl;
                        std::cout << std::endl;
                    }
                    else if (!invalidOptionIsOn)
                    {
                        std::cout << std::endl;
                        std::cout << "INVALID input!" << std::endl;
                        std::cout << std::endl;
                    }
                }
            }
            this->getNextPlayer();
        }
    }
}
