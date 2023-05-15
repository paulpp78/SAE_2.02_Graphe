# SAE_2.02_Graphe

## *Groupe 109 : Zghrata_Wissal -- Boukessassa Raafet -- DEGROLARD Victor -- Perigault Paul*                          

Projet Graphe et Algorithme de Dijkstra
=======================================

Ce projet implémente différentes représentations de graphes orientés valués et l'algorithme de Dijkstra pour calculer le plus court chemin dans un graphe. Les représentations de graphes implémentées sont : liste d'arcs, matrice d'adjacence, liste d'adjacence et table de hachage. L'implémentation respecte l'interface IGraphe pour les graphes modifiables et IGrapheConst pour les graphes non modifiables utilisés par l'algorithme de Dijkstra.

Partie I : Représentation des graphes orientés valués
-----------------------------------------------------

Les différentes représentations de graphes sont implémentées dans des classes dédiées qui respectent l'interface IGraphe. Les nœuds sont identifiés par une chaîne de caractères et un graphe ne contient pas de valuation négative. Toutes les classes de graphes ont un constructeur avec une chaîne de la forme "A-B(5), A-C(10), B-C(3), C-D(8), E:" qui décrit un graphe avec les sommets A,B,C,D,E et les arcs A vers B de valuation 5, A vers C de valuation 10 etc. Notez que E n’a pas d’arcs sortants.



=======

Les différentes classes de graphes implémentées sont :

*   GrapheLArcs : représente un graphe à l'aide d'une liste d'arcs.
*   GrapheMAdj : représente un graphe à l'aide d'une matrice d'adjacence.
*   GrapheLAdj : représente un graphe par des listes d'adjacence.
*   GrapheHHAdj : représente un graphe par des tables de hachage imbriquées.

Partie II : Algorithme du plus court chemin de Dijkstra
-------------------------------------------------------

L'algorithme de Dijkstra est implémenté sur l'interface IGrapheConst sans utiliser explicitement les classes de graphes.

Partie III : Étude comparative des différentes représentations
--------------------------------------------------------------

Cette partie vise à comparer l'efficacité en temps et en espace des différentes représentations de graphes implémentées lors de la première partie.
