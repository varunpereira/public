
#include "LinkedList.h"
#include "Tile.h"
#include "TileCodes.h"
#include "Menu.h"
#include "Gameboard.h"

#include <iostream>
#include <string>
#include <memory>
#include <fstream>

#define EXIT_SUCCESS 0

int main(void)
{
   std::cout << std::endl;
   std::cout << "Welcome to Qwirkle!" << std::endl;
   std::cout << "-------------------" << std::endl;
   std::cout << std::endl;
   Menu menu = Menu();
   menu.menuOutput();
   return EXIT_SUCCESS;
}
