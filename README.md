# Minesweeper (Console Application)

## Overview
Minesweeper is a classic single-player puzzle game where the objective is to clear a grid without detonating hidden mines. The player uses numerical clues about adjacent mines to identify safe cells.

This project is a console-based implementation of Minesweeper, built using Java and Gradle.

## Features
- **Multiple difficulty levels**: Easy, Medium, and Hard.
- **Custom board size**: Create a custom board with a specified size and number of mines.
- **Simple text-based interface**: Play the game via the command line.
- **Win/Loss detection**: Automatically checks for win or loss conditions.

## Prerequisites
- **Java Development Kit (JDK) 17**: Ensure that JDK 17 is installed on your machine.
- **Gradle**: The project uses Gradle as the build automation tool.

## Setup and Installation

### 1. Clone the Repository
First, clone the Minesweeper project from your repository:

```sh
git clone https://github.com/DvinPROG/minesweeper.git
cd minesweeper
```

### 2. Build the Project
Use Gradle to clean and build the project, and create an executable JAR file:

```sh
./gradlew clean jar
```

This command compiles the project and generates the JAR file at:

```
<path-to-project>/minesweeper-console/build/libs/minesweeper-console-1.0-SNAPSHOT.jar
```

### 3. Run the Game
After building the project, you can run the Minesweeper game from the command line using the JAR file:

```sh
java -jar <path-to-project>/minesweeper-console/build/libs/minesweeper-console-1.0-SNAPSHOT.jar
```

## How to Play

### Starting the Game
When you run the game, you will be prompted to select a difficulty level and board size:

There are three levels of complexity: LOW, MEDIUM, HIGH. To start the game, write the width, height, and complexity:

```
<width> <height> <complexity>
```

For example:

```
5 5 LOW
```

### Game Commands
- **Put a flag**: To mark a potential mine, use the command `f` followed by the row and column of the cell (e.g., `f 1 1`).
- **Open a cell**: To reveal a cell, use the command `o` followed by the row and column of the cell (e.g., `o 1 1`).
- **Restart the game**: To restart the game at any time, use the command `r`.

### If You Lose
If you lose the game, you will be prompted to continue, restart, or quit:

- **Continue**: Type `c` to continue the game on the same board.
- **Restart**: Type `r` to restart the current game with a new board.
- **Quit**: Type `q` to exit the game.

### End of the Game
At the end of the game, whether you win or lose, you will be prompted to continue, restart, or quit:

- **Restart**: Type `r` to restart the current game with a new board.
- **Quit**: Type `q` to exit the game.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
If you have any questions, suggestions, or issues, feel free to contact the project maintainer:

- **Name**: David Dvinskykh
- **Email**: david.dvinskykh@gmail.com

Enjoy playing Minesweeper! ☠️💣