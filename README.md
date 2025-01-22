# PROP-FIB
Programming Project design of a "Recommender System"

A recommender system is a system that establishes a set of criteria on the characteristics of users in order to make predictions about recommendations of items that may be of use or value for a specific user. The items can be anything, such as movies, songs, books, restaurants, or any type of product. These recommendations normally vary depending on each user, and there are different types of recovery systems depending on the needs of the system.

The idea is to implement an item recommendation system for a user, called the active user, given a set of users and a set of items of the same type that have been purchased/used/selected/rated, etc. by the users. The items must be able to be identified in a generic way by an identifier plus a series of attributes that may vary according to the specific type of item. At a minimum, there must be numerical, boolean or categorical attributes (e.g.: bruise, green, red).

To implement the recommender system we used the following methods:

- Collaborative filtering: k-means” + “Slope one”
- Content-based filtering is a strategy based on the “k-nearest neighbors (k-NN)” algorithm has been implemented.
- Hybrid approaches: Collaborative filtering + Content-based filtering.
  
## Execution

To run this recommendation system, follow the steps below:
1. Download and Extract

    Clone this repository or download it as a ZIP file:
 >

    git clone https://github.com/your_username/your_repository.git
  >

    cd your_repository/EXE

Ensure the following files are present in the EXE folder:

    recommender_system.jar: The main executable file of the recommendation system.
    archivos.zip: A compressed archive containing the required datasets.

Use the make command to extract the archivos.zip file and prepare the datasets:

    make unzip
    
2. Run the Recommendation System

After extracting the datasets, run the recommendation system using the following command:

    make all
    
This will execute the recommender_system.jar file and launch the program.

3. Clean Up Files

If you want to delete the extracted files to free up space or reset the setup, run:

    make clean
    
This will remove the archivos/ folder created during the extraction.

