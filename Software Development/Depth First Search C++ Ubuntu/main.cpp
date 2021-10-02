/*
DESCRIPTION:
MILESTONE 1:
I created environments that tested a range of different scenarios,
including corridors, islands, same dist paths, corners, loops,
open spaces etc. This part was doable.
MILESTONE 2:
This part was challenging and really test my problem solving skills.
I think more than the implementation I struggled with, undrestanding how the 
algorithm worked, because going back and forth, like how the openlist was for the
next round confused me for a while.
I was able to overcome this by breaking the problem down to smaller chunks and trying every
solution I could think of till it worked.
My design was almost identical to algorithm, so no surprises there.
Please see the pseudocode above the functions.
MILESTONE 3:
This part was not as challenging as the pervious part, mainly since I undrestood the problem
better and that all I needed for the solutionlist, was a subset of the closedlist, and I 
was going in the revrse order from before.
Please see the pseudocode above the functions.
*/

#include <iostream>
#include <fstream>
#include <stdexcept>
#include <string>
#include "Types.h"
#include "Node.h"
#include "NodeList.h"
#include "PathSolver.h"

// Read a environment from standard input.
void readEnvStdin(Env env);

// Print out a Environment to standard output with path.
// for Milestone 3
void printEnvStdout(Env env, NodeList *solution);

int main(int argc, char **argv)
{
    // MILESTONE 2
    // Load Environment
    Env env;
    readEnvStdin(env);

    // MILESTONE 2
    // Solve using forwardSearch
    PathSolver *pathSolver = new PathSolver();
    pathSolver->forwardSearch(env);

    NodeList *exploredPositions = nullptr;
    exploredPositions = pathSolver->getNodesExplored();
    // for (int i = 0; exploredPositions->getNode(i) != nullptr && exploredPositions->getLength(); i += 1)
    // {
    //     //std::cout << exploredPositions->getNode(i)->getChar() << std::endl;
    //     std::cout << exploredPositions->getNode(i)->getRow() << "," << exploredPositions->getNode(i)->getCol()
    //               << ": dist trav: " << exploredPositions->getNode(i)->getDistanceTraveled() << std::endl;
    // }

    // MILESTONE 3
    // Get the path
    NodeList *solution = pathSolver->getPath(env);
    // for (int i = 0; solution->getNode(i) != nullptr && i < solution->getLength(); i += 1)
    // {
    //     //std::cout << solution->getNode(i)->getChar() << std::endl;
    //     std::cout << solution->getNode(i)->getRow() << "," << solution->getNode(i)->getCol() << ": dist trav: " << solution->getNode(i)->getDistanceTraveled() << std::endl;
    // }

    printEnvStdout(env, solution);
    delete pathSolver;
    delete exploredPositions;
    delete solution;
}

// MILESTONE 2
// Function Name: readEnvStdin
// Function Purpose: read a grid from text file as char, save into env 2D array
// Function Algo:
// 1. Open the input.
// 2. If file can not be read, print error message.
// 3. If file can be read, save each incoming char into the env 2D array, use
// outer loop for y axis, and inner loop for x axis, make sure only correct chars
// are saved.
// 4. close the input.
void readEnvStdin(Env env)
{
    std::ifstream ifs("tests/test-4.env", std::ifstream::in | std::ifstream::binary);
    if (ifs.is_open())
    {
        char c;
        while (ifs.good())
        {
            for (int y = 0; y < ENV_DIM; y += 1)
            {
                for (int x = 0; x < ENV_DIM + 1; x += 1)
                {
                    if (!ifs.eof())
                    {
                        c = ifs.get();
                        env[y][x] = c;
                    }
                }
            }
            ifs.close();
        }
    }
    else
    {
        std::cout << "Error opening file!" << std::endl;
    }
}

// MILESTONE 3
// Function Name: printEnvStdout
// Function Purpose: print env 2d array, but with shortest path from S to G
// Function Algo:
// 1. Loop through the env 2D array.
// 2. If location (y,x) of any node in env, matches the location (y,x),
// of any node in solution nodelist, change the env symbol to the solution symbol.
// 3. Then print the env 2D array, with the updated changes.
void printEnvStdout(Env env, NodeList *solution)
{
    for (int i = 0; i < solution->getLength(); i += 1)
    {
        for (int y = 0; y < ENV_DIM; y += 1)
        {
            for (int x = 0; x < ENV_DIM + 1; x += 1)
            {
                if (y == solution->getNode(i)->getRow() && x == solution->getNode(i)->getCol())
                {
                    env[y][x] = solution->getNode(i)->getChar();
                }
            }
        }
    }
    for (int y = 0; y < ENV_DIM; y += 1)
    {
        for (int x = 0; x < ENV_DIM; x += 1)
        {
            std::cout << env[y][x];
        }
        std::cout << std::endl;
    }
}
