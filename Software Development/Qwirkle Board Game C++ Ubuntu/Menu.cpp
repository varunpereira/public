#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <memory>
#include <fstream>

#include "Menu.h"

Menu::Menu()
{
    this->gameengine = new GameEngine();
    this->helpOptionIsOn = false;
    this->invalidOptionIsOn = false;
}

void Menu::menuOutput()
{
    std::cout << "Menu" << std::endl;
    std::cout << "----" << std::endl;
    std::cout << "1. New Game" << std::endl;
    std::cout << "2. Load Game" << std::endl;
    std::cout << "3. Credits" << std::endl;
    std::cout << "4. Quit" << std::endl;
    std::cout << "5. Help Enhancement: ";
    if (helpOptionIsOn)
    {
        std::cout << "ON" << std::endl;
    }
    else if (!helpOptionIsOn)
    {
        std::cout << "OFF" << std::endl;
    }
    std::cout << "6. Invalid Enhancement: ";
    if (invalidOptionIsOn)
    {
        std::cout << "ON" << std::endl;
    }
    else if (!invalidOptionIsOn)
    {
        std::cout << "OFF" << std::endl;
    }
    std::cout << std::endl;
    std::cout << "> ";
    std::string input;
    std::cin >> input;
    std::cout << std::endl;
    inputCheck(input);
}

void Menu::inputCheck(std::string input)
{
    if (input == "1")
    {
        NewGameOption();
    }
    else if (input == "2")
    {
        LoadGameOption();
    }
    else if (input == "3")
    {
        creditsOption();
    }
    else if (input == "4" || input == "^D")
    {
        quitOption();
    }
    else if (input == "5")
    {
        if (helpOptionIsOn)
        {
            helpOptionIsOn = false;
            menuOutput();
        }
        else if (!helpOptionIsOn)
        {
            helpOptionIsOn = true;
            menuOutput();
        }
    }
    else if (input == "6")
    {
        if (invalidOptionIsOn)
        {
            invalidOptionIsOn = false;
            menuOutput();
        }
        else if (!invalidOptionIsOn)
        {
            invalidOptionIsOn = true;
            menuOutput();
        }
    }
    else if (input == "help" && helpOptionIsOn)
    {
        std::cout << "HELP option! You can input: '1','2','3','4','5','6','help'" << std::endl;
        std::cout << std::endl;
        menuOutput();
    }
    else
    {
        if (invalidOptionIsOn && helpOptionIsOn)
        {
            std::cout << "INVALID option! '" << input << "' is not valid because," << std::endl;
            std::cout << "you can only input: '1','2','3','4','5','6','help'" << std::endl;
            std::cout << std::endl;
            menuOutput();
        }
        else if (invalidOptionIsOn && !helpOptionIsOn)
        {
            std::cout << "INVALID option! '" << input << "' is not valid because," << std::endl;
            std::cout << "you can only input: '1','2','3','4','5','6'" << std::endl;
            std::cout << std::endl;
            menuOutput();
        }
        else if (!invalidOptionIsOn)
        {
            std::cout << "Invalid Option!" << std::endl;
            std::cout << std::endl;
            menuOutput();
        }
    }
}

void Menu::NewGameOption()
{
    std::cout << "Starting a New Game..." << std::endl;
    std::cout << "----------------------" << std::endl
              << std::endl;
    std::cout << "Please enter Player 1 Name (uppercase letters only):" << std::endl
              << std::endl;
    std::cout << "> ";
    std::string p1name;
    std::cin >> p1name;
    if (p1name == "^D")
    {
        std::cout << "Goodbye" << std::endl;
        delete this->gameengine;
        exit(EXIT_SUCCESS);
    }
    while (containsLowerCase(p1name) == true)
    {
        if (p1name == "help" && helpOptionIsOn)
        {
            std::cout << "You can input: 'Player 1 Name','help'" << std::endl;
            std::cout << "> ";
            std::cin >> p1name;
        }
        else
        {
            if (invalidOptionIsOn)
            {
                std::cout << "INVALID! Uppercase Letters Only for Player 1 Name." << std::endl;
                std::cout << "> ";
                std::cin >> p1name;
                containsLowerCase(p1name);
            }
            else if (!invalidOptionIsOn)
            {
                std::cout << "UPPERCASE only." << std::endl;
                std::cout << "> ";
                std::cin >> p1name;
                containsLowerCase(p1name);
            }
        }
    }
    std::cout << std::endl;
    std::cout << "Please enter Player 2 Name (uppercase letters only):" << std::endl
              << std::endl;
    std::cout << "> ";
    std::string p2name;
    std::cin >> p2name;
    if (p2name == "^D")
    {
        std::cout << "Goodbye" << std::endl;
        delete this->gameengine;
        exit(EXIT_SUCCESS);
    }
    while (containsLowerCase(p2name) == true)
    {
        if (p2name == "help" && helpOptionIsOn)
        {
            std::cout << "You can input: 'Player 2 Name','help'" << std::endl;
            std::cout << "> ";
            std::cin >> p2name;
        }
        else
        {
            if (invalidOptionIsOn)
            {
                std::cout << "INVALID! Uppercase Letters Only for Player 2 Name." << std::endl;
                std::cout << "> ";
                std::cin >> p2name;
                containsLowerCase(p2name);
            }
            else if (!invalidOptionIsOn)
            {
                std::cout << "UPPERCASE only." << std::endl;
                std::cout << "> ";
                std::cin >> p2name;
                containsLowerCase(p2name);
            }
        }
    }
    std::cout << std::endl
              << "Lets Play!" << std::endl;
    this->gameengine->startNewGame(p1name, p2name, helpOptionIsOn, invalidOptionIsOn);
}

void Menu::LoadGameOption()
{
    std::cout << "Loading Saved Game..." << std::endl;
    std::cout << "---------------------" << std::endl
              << std::endl;
    std::cout << "Please enter file name (with '.save'): " << std::endl;
    std::cout << "> ";
    std::string filePath;
    std::cin >> filePath;
    if (filePath == "^D")
    {
        std::cout << "Goodbye" << std::endl;
        delete this->gameengine;
        exit(EXIT_SUCCESS);
    }
    while (filePath == "help" && helpOptionIsOn)
    {
        std::cout << "You can input: 'Saved Game Name (with '.save')'" << std::endl;
        std::cout << "> ";
        std::cin >> filePath;
    }

    std::ifstream ifile;
    ifile.open(filePath);
    if (ifile)
    {
        std::cout << std::endl;
        std::cout << "Checking file format..." << std::endl;
        std::vector<std::string> fileInputs;
        std::string line;
        while (getline(ifile, line))
        {
            fileInputs.push_back(line);
        }
        bool namesCorrect = (validateASCIItext(fileInputs[0]) && validateASCIItext(fileInputs[3]) && validateASCIItext(fileInputs[9]));
        bool scoresCorrect = ((validateInteger(fileInputs[1])) && (validateInteger(fileInputs[4])));
        bool tilesCorrect = (validateTileList(fileInputs[2]) && validateTileList(fileInputs[5]) && validateTileList(fileInputs[6]));
        bool boardSizeCorrect = (validateBoardSize(fileInputs[7]));
        if (scoresCorrect && namesCorrect && tilesCorrect && boardSizeCorrect)
        {
            std::cout << "Game '" << filePath << "' loaded successfully!" << std::endl;
            std::cout << "----------------------------------" << std::endl;
            this->gameengine->loadGame(fileInputs, helpOptionIsOn, invalidOptionIsOn);
        }
        else
        {
            std::cout << "File Format Unaccepted" << std::endl
                      << std::endl;
            menuOutput();
        }
    }
    else
    {
        if (invalidOptionIsOn)
        {
            std::cout << "INVALID! Input the name of saved game file that exists." << std::endl;
            std::cout << std::endl;
            menuOutput();
        }
        else if (!invalidOptionIsOn)
        {
            std::cout << "Not found!" << std::endl;
            std::cout << std::endl;
            menuOutput();
        }
    }
    ifile.close();
}

bool Menu::validateASCIItext(std::string str)
{
    bool result = false;
    std::regex accepted("^[A-Z]+$");
    if (std::regex_match(str, accepted))
    {
        result = true;
    }
    return result;
}

bool Menu::validateInteger(std::string str)
{
    int count = 0;
    int strLength = str.length();
    for (int i = 0; i < strLength; i++)
    {
        if (isdigit(str[i]) == false)
        {
            count++;
        }
    }
    if (count > 0)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool Menu::validateTileList(std::string str)
{
    std::string substr;
    std::regex acceptedTile("[ROYGBP][123456]");
    int count = 0;
    stringstream s_stream1(str);
    while (getline(s_stream1, substr, ','))
    {
        if (!std::regex_match(substr, acceptedTile))
        {
            count++;
        }
    }
    if (count > 0)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool Menu::validateBoardSize(std::string str)
{
    std::string substr;
    std::regex acceptedSize("^[1234567890]+$");
    int count = 0;
    stringstream s_stream1(str);
    while (getline(s_stream1, substr, ','))
    {
        if (!std::regex_match(substr, acceptedSize))
        {
            count++;
        }
    }
    if (count > 0)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool Menu::containsLowerCase(std::string playerName)
{
    bool containsLowerCase = false;
    int length = playerName.length();
    for (int i = 0; i < length && containsLowerCase == false; i++)
    {
        if (islower(playerName[i]))
        {
            containsLowerCase = true;
        }
    }
    return containsLowerCase;
}

void Menu::creditsOption()
{
    std::cout << "-----------------------------------" << std::endl;
    std::cout << "Name: William Furler" << std::endl;
    std::cout << "Student ID: s3848792" << std::endl;
    std::cout << "Email: s3848792@student.rmit.edu.au" << std::endl;
    std::cout << std::endl;
    std::cout << "Name: James Murray" << std::endl;
    std::cout << "Student ID: s3842096" << std::endl;
    std::cout << "Email: s3842096@student.rmit.edu.au" << std::endl;
    std::cout << std::endl;
    std::cout << "Name: Varun Pereira" << std::endl;
    std::cout << "Student ID: s3842244" << std::endl;
    std::cout << "Email: s3842244@student.rmit.edu.au" << std::endl;
    std::cout << std::endl;
    std::cout << "Name: Muhammad Rosdi" << std::endl;
    std::cout << "Student ID: s3802222" << std::endl;
    std::cout << "Email: s3802222@student.rmit.edu.au" << std::endl;
    std::cout << "-----------------------------------" << std::endl;
    std::cout << std::endl;
    menuOutput();
}

int Menu::quitOption()
{
    std::cout << "Goodbye!" << std::endl;
    delete this->gameengine;
    return EXIT_SUCCESS;
}
