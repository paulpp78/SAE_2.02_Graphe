package graphe;

import java.util.ArrayList;
import java.util.List;

public class AppliGrapheLArcs {
	
	// POUR QUE WIWI VERIFIE QUE LES METHODES MARCHENT
	//VOUS ETES PAS OBLIGES DE LIRE
	
	//TEST METHODE GETSOMMETS
	/*
	public static void main(String[] args) {
	    GrapheLArcs graphe = new GrapheLArcs();
	   /* graphe.arcs = new ArrayList<Arc>();
	    graphe.arcs.add(new Arc("A", "B", 5));
	    graphe.arcs.add(new Arc("B", "C", 10));
	    graphe.arcs.add(new Arc("C", "D", 15));
	    graphe.arcs.add(new Arc("D", "E", 20));
	    graphe.arcs.add(new Arc("E", "F", 25));
	    graphe.arcs.add(new Arc("F", "G", 30));

	    List<String> sommets = graphe.getSommets();

	    System.out.println("Liste des sommets du graphe : ");
	    for (String s : sommets) {
	        System.out.print(s + " ");*
	    }
	}*/
	
	//TEST METHODE ajouterArc: 2e exception marche pas quand la première est levé: normal?!!!!!
	
	/*public static void main(String[] args) {
	    GrapheLArcs graphe = new GrapheLArcs();
	    graphe.arcs = new ArrayList<Arc>();
	    try {
	        graphe.ajouterArc("A", "B", 5);
	        graphe.ajouterArc("B", "C", 7);
	        //graphe.ajouterArc("C", "D", -2); // Lève une exception IllegalArgumentException
	        graphe.ajouterArc("A", "B", 5); // Lève une exception IllegalArgumentException
	    } catch (IllegalArgumentException e) {
	        System.err.println("Erreur : " + e.getMessage());
	    }
	    
	    List<String> sommets = graphe.getSommets();
	    System.out.println("Liste des sommets du graphe : " + sommets);
	}*/

	//TEST METHODE oterArc
	/*public static void main(String[] args) {
	    GrapheLArcs graphe = new GrapheLArcs();
	    graphe.arcs = new ArrayList<Arc>();
	    graphe.ajouterArc("A", "B", 5);
	    graphe.ajouterArc("A", "B", 2);
	    graphe.ajouterArc("A", "B", 7);
	    graphe.ajouterArc("B", "C", 3);
	    graphe.ajouterArc("C", "C", 4);
	    graphe.ajouterArc("C", "D", 2);
	    System.out.println("Sommets avant la suppression : " + graphe.getSommets());

	    try {
	        graphe.oterArc("A", "B");
	        System.out.println("Arcs (A, B) supprimé avec succès !");
	        System.out.println(graphe.contientArc("A", "B"));
	    } catch (IllegalArgumentException e) {
	        System.err.println("Erreur lors de la suppression de l'arc (A, B) : " + e.getMessage());
	    }

	    System.out.println("Sommets après la suppression : " + graphe.getSommets());

	    try {
	        graphe.oterArc("C", "B");
	        System.out.println("Arc (C, B) supprimé avec succès !");
	    } catch (IllegalArgumentException e) {
	        System.err.println("Erreur lors de la suppression de  l'arc (C, B) : " + e.getMessage());
	    } 
	}
	*/
	//TEST METHODE contientArc
	/*public static void main(String[] args) {
	    GrapheLArcs graphe = new GrapheLArcs();
	    graphe.arcs = new ArrayList<Arc>();
	    graphe.ajouterArc("A", "B", 1);
	    graphe.ajouterArc("B", "C", 2);
	    graphe.ajouterArc("C", "D", 3);
	    graphe.ajouterArc("D", "E", 4);

	    System.out.println(graphe.contientArc("A", "B")); // true
	    System.out.println(graphe.contientArc("B", "C")); // true
	    System.out.println(graphe.contientArc("C", "D")); // true
	    System.out.println(graphe.contientArc("D", "E")); // true

	    System.out.println(graphe.contientArc("A", "C")); // false
	    System.out.println(graphe.contientArc("B", "D")); // false
	    System.out.println(graphe.contientArc("C", "E")); // false
	}*/

	
}
