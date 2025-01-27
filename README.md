# PROP-FIB
Programming Project design of a "Recommender System"

A recommender system is a system that establishes a set of criteria on the characteristics of users in order to make predictions about recommendations of items that may be of use or value for a specific user. The items can be anything, such as movies, songs, books, restaurants, or any type of product. These recommendations normally vary depending on each user, and there are different types of recovery systems depending on the needs of the system.

The idea is to implement an item recommendation system for a user, called the active user, given a set of users and a set of items of the same type that have been purchased/used/selected/rated, etc. by the users. The items must be able to be identified in a generic way by an identifier plus a series of attributes that may vary according to the specific type of item. At a minimum, there must be numerical, boolean or categorical attributes (e.g.: bruise, green, red).

To implement the recommender system we used the following methods:

- Collaborative filtering: k-means” + “Slope one”
- Content-based filtering is a strategy based on the “k-nearest neighbors (k-NN)” algorithm has been implemented.
- Hybrid approaches: Collaborative filtering + Content-based filtering.

The project is developed entirely in the Java programming language, using a 3-layer architecture:

It consists of:
- Presentation Layer
- Domain Layer
- Data Management Layer (or Persistence Layer)

## Project Execution Description

This project contains two versions of a Java application: a terminal version and a graphical user interface (GUI) version. The project includes a Makefile that automates the process of unpacking files and running the application versions.

Features:

- Automatic Decompression: Decompresses a .7z file with the resources needed for the project.
- Version Choice: Allows you to choose between running the terminal version or the GUI version of the Java application.
- Makefile Automation: Uses a Makefile to facilitate the configuration and execution process.

## Requirements:

- Java 8 or higher
- 7-Zip
## Execution

To run this recommendation system, follow the steps below:
1. Download and Extract

    Clone this repository or download it as a .7z file:
 > 

    git clone https://github.com/christianFIB/PROP-FIB.git
 >

    cd PROP-FIB/EXE

Ensure the following files are present in the EXE folder:

    recommender_system.jar: The main executable file of the recommendation system.
    archivos.zip: A compressed archive containing the required datasets.

    
2. Run the Recommendation System

Use the make command to extract the archivos.7z file and prepare the datasets, run the recommendation system using the following command:

    make all

---------------------------------------------------
|                                                 |
|             Recommender system                  |
|                                                 |
|-------------------------------------------------|
|                                                 |
|             Select the version to run           |
|             [1] Terminal version                |
|             [2] GUI version                     |
|-------------------------------------------------|                                        
|-------------------------------------------------|
| Option: 1 or 2                                  | 
|                                                 |

This will execute the recommender_system_terminal.jar and recommender_system_GUI files and launch the program.

3. Clean Up Files

If you want to delete the extracted files to free up space or reset the setup, run:

    make clean
    
This will remove the archivos/ folder created during the extraction.

Once the program is executed, the main window (main view) will appear.

## Testing the "Recommender systems"

> [See example of use](DOCS/Testing_the_recommender.pdf)

