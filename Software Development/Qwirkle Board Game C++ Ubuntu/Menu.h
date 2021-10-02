
#include <iostream>
#include <string>
#include <memory>
#include <fstream>

#include "GameEngine.h"
class Menu
{
public:
   Menu();

   void menuOutput();
   void inputCheck(std::string input);
   bool validateASCIItext(std::string str);
   bool validateInteger(std::string str);
   bool validateTileList(std::string str);
   bool validateBoardSize(std::string str);
   bool validateBoardTiles(std::string str);
   bool containsLowerCase(std::string playerName);
   void NewGameOption();
   void LoadGameOption();
   void creditsOption();
   int quitOption();

   bool helpOptionIsOn;
   bool invalidOptionIsOn;

private:
   GameEngine *gameengine;
};