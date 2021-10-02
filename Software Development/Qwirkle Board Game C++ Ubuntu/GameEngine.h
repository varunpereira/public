
#include "Gameboard.h"
#include "Player.h"
#include "LinkedList.h"

#include <cstdlib>
#include <ctime>
using namespace std;

#include <string>
#include <vector>

class GameEngine
{
public:
    GameEngine();
    ~GameEngine();

    void saveGame(std::string filename);
    void startNewGame(std::string p1name, std::string p2name, bool helpStatus, bool invalidStatus);
    void loadGame(std::vector<std::string> fileInputs, bool helpStatus, bool invalidStatus);
    bool checkEndGameConditions();
    int updatePlayerScore();
    void getNextPlayer();
    void loadPlayers(std::vector<std::string> fileInputs, int playerCount);

    bool helpOptionIsOn;
    bool invalidOptionIsOn;

private:
    Gameboard *board;
    Player *Players[2];
    Player *currentPlayer;
    LinkedList *TileBag;

    Tile *getTileFromBag();
    void assignHand(Player *player);
    void playGame();
};