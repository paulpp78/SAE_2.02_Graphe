package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implémentation de l'interface IGraphe pour un graphe orienté, non pondéré et avec liste d'arcs.
 */
public class GrapheLArcs implements IGraphe {

	/**
	 * Liste des arcs du graphe.
	 */
	private List<Arc> arcs;

	/**
	 * Ensemble des sommets du graphe.
	 */
	private Set<String> sommets;

	/**
	 * Constructeur par défaut qui initialise la liste des arcs et l'ensemble des sommets.
	 */
	public GrapheLArcs() {
		arcs = new ArrayList<>(); // Initialise la liste d'arcs à une liste vide.
		sommets = new HashSet<>(); // Initialise l'ensemble de sommets à un ensemble vide.
		}

	/**
	 * Constructeur qui initialise le graphe à partir d'une liste d'arcs donnée sous forme de chaîne de caractères.
	 * La chaîne de caractères doit être au format suivant :
	 * "source-destination(poids), source-destination(poids), ..."
	 *
	 * @param listeArc la chaîne de caractères représentant la liste d'arcs
	 */
	public GrapheLArcs(String listeArc) {
		arcs = new ArrayList<>(); // Initialise la liste d'arcs à une liste vide.
		sommets = new HashSet<>(); // Initialise l'ensemble de sommets à un ensemble vide.
		String[] arcsString = listeArc.split(", "); // Sépare la chaîne de caractères en arcs individuels.
		for (String arcString : arcsString) {
			String[] parts = arcString.split("[()]"); // Sépare l'arc en ses parties : source, destination, valuation.
			String source = parts.length > 0 ? parts[0] : "";
			String destination = parts.length > 1 ? parts[1] : "";
			int valuation = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
			arcs.add(new Arc(source, destination, valuation)); // Ajoute l'arc à la liste d'arcs.
			sommets.add(source); // Ajoute le sommet source à l'ensemble de sommets.
			sommets.add(destination); // Ajoute le sommet destination à l'ensemble de sommets.
		}
	}


	/**
	 * Supprime un sommet du graphe ainsi que tous les arcs qui y sont connectés.
	 *
	 * @param sommet Le nom du sommet à supprimer.
	 */
	public void oterSommet(String sommet) {
		// Supprime tous les arcs liés au sommet à supprimer
		arcs.removeIf(arc -> arc.getSource().equals(sommet) || arc.getDestination().equals(sommet));
		// Supprime le sommet du graphe
		sommets.remove(sommet);
	}




	/**
	 * Retourne la liste triée par ordre alphabétique des noms des sommets du graphe.
	 *
	 * @return la liste triée des sommets du graphe.
	 */
	public List<String> getSommets() {
		Set<String> sommetsSet = new HashSet<>();

		for (Arc arc : arcs) {
			sommetsSet.add(arc.getSource());
			sommetsSet.add(arc.getDestination());
		}

		List<String> sommetsList = new ArrayList<>(sommetsSet);
		Collections.sort(sommetsList);

		return sommetsList;
	}


	/**
	 * Vérifie si le graphe contient le sommet spécifié.
	 *
	 * @param noeud le nom du sommet à vérifier.
	 * @return true si le graphe contient le sommet spécifié, false sinon.
	 */
	@Override
	public boolean contientSommet(String noeud) {
		// Utilisation de la méthode contains() de l'ensemble sommets pour effectuer une
		// recherche en temps constant. Cela évite de parcourir l'ensemble des sommets.
		return sommets.contains(noeud);
	}

	/**
	 * Ajoute un sommet au graphe si celui-ci n'existe pas déjà.
	 *
	 * @param noeud le nom du sommet à ajouter.
	 */
	@Override
	public void ajouterSommet(String noeud) {
		// Vérifie si le sommet existe déjà dans l'ensemble des sommets du graphe
		if (!contientSommet(noeud)) {
			// Si le sommet n'existe pas déjà, l'ajoute à l'ensemble des sommets du graphe
			sommets.add(noeud);
		}
	}

	/**
	 * Ajoute un arc au graphe avec une valuation spécifiée.
	 *
	 * @param source      le nom du sommet source de l'arc.
	 * @param destination le nom du sommet destination de l'arc.
	 * @param valeur      la valuation de l'arc.
	 * @throws IllegalArgumentException si la valuation de l'arc est négative ou si l'arc existe déjà dans le graphe.
	 */
	@Override
	public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
		// Vérifier que la valuation de l'arc est positive
		if (valeur < 0) {
			throw new IllegalArgumentException("La valeur de l'arc doit être positive : " + valeur);
		}

		// Ajouter les sommets source et destination s'ils ne sont pas déjà présents dans le graphe
		if (!contientSommet(source))
			ajouterSommet(source);
		if (!contientSommet(destination))
			ajouterSommet(destination);

		// Créer un objet Arc pour représenter l'arc à ajouter
		Arc arc = new Arc(source, destination, valeur);

		// Vérifier que l'arc n'existe pas déjà dans le graphe
		if (contientArc(source, destination))
			throw new IllegalArgumentException("L'arc " + arc + " existe déjà dans le graphe.");

		// Ajouter l'arc à la liste des arcs du graphe
		arcs.add(arc);
	}
	/**
	 * Vérifie si le graphe contient l'arc spécifié.
	 *
	 * @param source      le nom du sommet source de l'arc.
	 * @param destination le nom du sommet destination de l'arc.
	 * @return true si le graphe contient l'arc spécifié, false sinon.
	 */
	@Override
	public boolean contientArc(String source, String destination) {
		// Parcourt la liste des arcs du graphe
		for (Arc arc : arcs) {
			// Vérifie si l'arc a le même sommet source et destination que ceux spécifiés
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination))
				return true; // Si l'arc existe, retourne true
		}
		return false; // Si on a parcouru tous les arcs sans en trouver un correspondant, retourne false
	}


	/**
	 * Supprime l'arc spécifié du graphe.
	 *
	 * @param source le nom du sommet source de l'arc.
	 * @param destination le nom du sommet destination de l'arc.
	 * @throws IllegalArgumentException si l'arc spécifié n'existe pas dans le graphe.
	 */
	@Override
	public void oterArc(String source, String destination) throws IllegalArgumentException {
		// Vérifie si l'arc existe dans le graphe
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
		}

		// Stocke l'arc à supprimer dans une variable
		Arc arcASupprimer = null;
		for (Arc arc : arcs) {
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
				arcASupprimer = arc;
				break;
			}
		}

		// Vérifie que l'arc à supprimer a été trouvé
		if (arcASupprimer == null) {
			throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
		}

		// Supprime l'arc du graphe
		arcs.remove(arcASupprimer);
	}



	/**
	 * Retourne la valuation de l'arc spécifié.
	 *
	 * @param source      le nom du sommet source de l'arc.
	 * @param destination le nom du sommet destination de l'arc.
	 * @return la valuation de l'arc spécifié.
	 * @throws IllegalArgumentException si l'arc spécifié n'existe pas dans le graphe.
	 */
	public int getValuation(String source, String destination) throws IllegalArgumentException {
		// Vérifier si l'arc spécifié existe dans le graphe
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
		}

		// Parcourir la liste des arcs jusqu'à trouver l'arc spécifié
		for (Arc arc : arcs) {
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
				return arc.getValuation();
			}
		}
		// L'arc a été vérifié avant, donc normalement on ne devrait pas arriver ici.
		throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
	}


	/**
	 * Renvoie la liste des successeurs d'un sommet donné.
	 *
	 * @param sommet le sommet dont on veut les successeurs
	 * @return la liste des successeurs du sommet donné
	 */
	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<>();
		for (Arc arc : arcs) {
			if (arc.getSource().equals(sommet)) {
				succ.add(arc.getDestination());
			}
		}
		return succ;
	}

	/**
	 * Trie les arcs par source, puis par destination.
	 */
	public void trierArcs() {
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));
	}

	/**
	 * Vérifie si les arcs sont triés par source, puis par destination.
	 * @return true si les arcs sont triés, false sinon.
	 */
	public boolean estTrie() {
		for (int i = 0; i < arcs.size() - 1; i++) {
			Arc arc1 = arcs.get(i);
			Arc arc2 = arcs.get(i + 1);

			if (arc1.getSource().compareTo(arc2.getSource()) > 0) {
				return false;
			} else if (arc1.getSource().equals(arc2.getSource()) && arc1.getDestination().compareTo(arc2.getDestination()) > 0) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Renvoie une représentation sous forme de chaîne de caractères du graphe.
	 * Les arcs sont triés avant l'affichage si ce n'est pas déjà fait dans les autres méthodes.
	 *
	 * @return la représentation sous forme de chaîne de caractères du graphe
	 */
	@Override
	public String toString() {
		// On trie les arcs par ordre alphabétique des noms de source, puis par ordre alphabétique des noms de destination
		// si ce n'est pas déjà fait dans les autres méthodes.
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));

		StringBuilder sb = new StringBuilder();
		// On parcourt la liste des arcs et on ajoute leur représentation sous forme de chaîne de caractères dans le StringBuilder
		for (Arc arc : arcs) {
			sb.append(arc.toString()).append(", ");
		}
		// Si le StringBuilder n'est pas vide, on enlève la dernière virgule et espace
		if (sb.length() > 0) {
			sb.delete(sb.length() - 2, sb.length());
		}
		// On retourne la chaîne de caractères représentant le graphe
		return sb.toString();
	}

	public static void main(String[] args) {
		GrapheLArcs graphe = new GrapheLArcs();
		graphe.arcs = new ArrayList<Arc>();
		graphe.ajouterArc("D", "A", 3);
		graphe.ajouterSommet("W");
		System.out.println(graphe.contientArc("W", "W"));
		graphe.ajouterArc("A", "D", 5);
		//graphe.ajouterArc("D", "A", 5);
		graphe.ajouterArc("D", "", 3);
		//graphe.ajouterArc("D", "A", 3);
		graphe.ajouterArc("C", "C", 4);
		graphe.ajouterArc("B", "B", 4);
		graphe.ajouterArc("F", "A", 2);
		System.out.println("Sommets avant la suppression : " + graphe.getSommets());

		try {
			graphe.oterArc("A", "D");
			System.out.println("Arcs (A, D) supprimé avec succès !");
			System.out.println(graphe.contientArc("A", "D"));
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur lors de la suppression de l'arc (A, D) : " + e.getMessage());
		}

		System.out.println("Sommets après la suppression : " + graphe.getSommets());

		try {
			graphe.oterArc("C", "C");
			System.out.println("Arc (C, C) supprimé avec succès !");
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur lors de la suppression de  l'arc (C, B) : " + e.getMessage());
		}
		System.out.println("Sommets après la suppression : " + graphe.getSommets());
	}

}
