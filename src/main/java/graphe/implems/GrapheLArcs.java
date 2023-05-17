package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;
import main.java.graphe.core.Arc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrapheLArcs implements IGraphe {
	private List<Arc> arcs;
	private Set<String> sommets;
	private static final int valuationFactice=0, valuationInexistante= -1;
	private static final String noeudFactice = "";


	/**
	 * Constructeur par défaut. Initialise la liste d'arcs et l'ensemble de sommets.
	 */
	public GrapheLArcs() {
		this.arcs = new ArrayList<>();
		this.sommets = new HashSet<>();
	}

	/**
	 * Constructeur prenant une chaîne de caractères représentant les arcs du graphe au format "source:destination:valeur".
	 * Les arcs sont ajoutés à la liste d'arcs du graphe.
	 * @param str La chaîne de caractères représentant les arcs du graphe.
	 */
	public GrapheLArcs(String str) {
		this();
		peupler(str);
	}

	/**
	 * Trie la liste d'arcs par ordre alphabétique de leur sommet source, puis par ordre alphabétique de leur sommet destination.
	 */
	public void trierArcs() {
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));
	}


	/**
	 * Retourne la liste des sommets du graphe.
	 * Les sommets sont extraits à partir de la liste d'arcs.
	 * @return La liste des sommets du graphe.
	 */
	@Override
	public List<String> getSommets() {
		//Création d'un ensemble pour stocker les sommets sans doublons
		Set<String> sommets = new HashSet<>();
		for (Arc a : arcs) {
			String source = a.getSource().replace(":", "");
			String destination = a.getDestination().replace(":", "");
			if (source != noeudFactice)
				sommets.add(source);
			if (destination != noeudFactice)
				sommets.add(destination);
		}
		//Conversion de l'ensemble en liste, pour permettre le retour
		List<String> sommetsList = new ArrayList<>(sommets);
		//Collections.sort(sommetsList);
		return sommetsList;
	}


	/**
	 * Ajoute un arc au graphe avec les sommets source et destination et la valuation spécifiés.
	 * @param source  Le sommet source de l'arc.
	 * @param destination Le sommet destination de l'arc.
	 * @param valeur  La valuation de l'arc.
	 * @throws IllegalArgumentException Si la valuation est négative ou si l'arc existe déjà dans le graphe.
	 */
	@Override
	public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
		if (valeur < 0) {
			throw new IllegalArgumentException("La valeur de l'arc doit être positive : " + valeur);
		} else {
			Arc arc = new Arc(source, destination, valeur);
			if (contientArc(source, destination))
				throw new IllegalArgumentException("L'arc " + arc + " existe déjà dans le graphe.");
			arcs.add(arc);
		}
		//trierArcs(); //d'après toAString pas forcément utile
	}


	/**
	 * Ajoute un sommet au graphe s'il n'existe pas déjà.
	 * @param noeud le nom du sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud))
			arcs.add(new Arc(noeud, noeudFactice, valuationFactice));
	}

	/**
	 * Retire l'arc spécifié par sa source et sa destination du graphe, et ajoute les sommets orphelins
	 * si nécessaire.
	 * @param source la source de l'arc à retirer
	 * @param destination la destination de l'arc à retirer
	 * @throws IllegalArgumentException si l'arc spécifié n'existe pas dans le graphe
	 */
	@Override
	public void oterArc(String source, String destination) throws IllegalArgumentException {
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
		}

		// sauvegarde des sommets avant de retirer l'arc
		List<String> sommetsAvantRetraitArc = new ArrayList<>(getSommets());

		arcs.remove(new Arc(source, destination, getValuation(source, destination)));

		// vérifie si des sommets sont devenus orphelins et les ajoute si nécessaire
		for (String sommet : sommetsAvantRetraitArc) {
			if (getSucc(sommet).isEmpty() && !contientSommet(sommet)) {
				ajouterSommet(sommet);
			}
		}
	}


	/**
	 * Renvoie la valuation de l'arc spécifié par sa source et sa destination.
	 * @param source la source de l'arc dont on veut la valuation
	 * @param destination la destination de l'arc dont on veut la valuation
	 * @return la valuation de l'arc spécifié, ou -1 si l'un ou l'autre des sommets n'existe pas dans le graphe
	 */
	@Override
	public int getValuation(String source, String destination){
		if (!(contientSommet(source) && contientSommet(destination))) {
			return valuationInexistante;
		}

		for (Arc arc : arcs) {
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
				return arc.getValuation();
			}
		}
		return valuationInexistante;
	}

	/**
	 * Retire un sommet du graphe en supprimant tous les arcs qui lui sont liés.
	 * @param sommet le sommet à retirer du graphe
	 */
	//à modifier
	@Override
	public void oterSommet(String sommet) {
		// Création d'un HashSet pour stocker les arcs
		Set<Arc> arcsSet = new HashSet<>(arcs);

		for (Arc arc : arcsSet) {
			if (arc.getSource().equals(sommet) || arc.getDestination().equals(sommet))
				oterArc(arc.getSource(), arc.getDestination());

		}

		// Vérifier si le sommet est toujours présent dans la liste des sommets
		if (contientSommet(sommet))
			sommets.remove(sommet);
	}

	/**
	 * Vérifie si le graphe contient un sommet spécifié.
	 * @param noeud le sommet à vérifier
	 * @return true si le graphe contient le sommet, false sinon
	 */
	@Override
	public boolean contientSommet(String noeud) {
		for (Arc arc : arcs) {
			if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Vérifie si un arc existe entre deux sommets spécifiés.
	 * Si source est nulle, renvoie true si au moins un arc arrive à destination.
	 * Trie d'abord les arcs pour pouvoir mettre en place une recherche dichotomique.
	 * @param source Le sommet source de l'arc.
	 * @param destination Le sommet destination de l'arc.
	 * @return True si l'arc existe dans le graphe, False sinon.
	 */
	@Override
	public boolean contientArc(String source, String destination) {
		trierArcs(); //recherche dichotomique
		if (source == null) {
			for (Arc arc : arcs) {
				if (arc.getDestination().equals(destination)) {
					return true;
				}
			}
			return false;
		}

		int debut = 0;
		int fin = arcs.size() - 1;

		while (debut <= fin) {
			int milieu = (debut + fin) / 2;
			Arc arc = arcs.get(milieu);

			int comparaison = arc.getSource().compareTo(source);
			if (comparaison < 0) {
				debut = milieu + 1;
			} else if (comparaison > 0) {
				fin = milieu - 1;
			} else {
				comparaison = arc.getDestination().compareTo(destination);
				if (comparaison < 0) {
					debut = milieu + 1;
				} else if (comparaison > 0) {
					fin = milieu - 1;
				} else {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Renvoie une liste des sommets de destination atteignables à partir d'un sommet source spécifié.
	 * @param sommet Le sommet source à partir duquel rechercher les arcs sortants.
	 * @return Une liste des sommets de destination atteignables à partir du sommet source spécifié.
	 */
	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<>();
		for (Arc arc : arcs) {
			if (arc.getSource().equals(sommet)&& !arc.getDestination().equals(noeudFactice) ) {
				succ.add(arc.getDestination());
			}
		}
		return succ;
	}

	/**
	 * Renvoie une représentation sous forme de chaîne de caractères du graphe orienté.
	 * @return Une chaîne de caractères représentant ce graphe orienté.
	 */
	@Override
	public String toString() {
		trierArcs();
		return toAString();
	}
}