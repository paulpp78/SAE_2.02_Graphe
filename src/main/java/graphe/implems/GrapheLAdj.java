package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.graphe.core.Arc;


/**
 * Cette classe implémente l'interface IGraphe en utilisant une liste d'adjacence pour stocker les informations du graphe.
 */
public class GrapheLAdj implements IGraphe {

    /**
     * La liste d'adjacence du graphe, représentée par une table de hachage qui associe chaque sommet à une liste de ses arcs adjacents.
     */
    private final Map<String, List<Arc>> ladj;

    /**
     * Constructeur par défaut qui crée une liste d'adjacence vide.
     */
    public GrapheLAdj() {
        ladj = new HashMap<>();
    }

    /**
     * Constructeur qui crée une liste d'adjacence à partir d'une chaîne de caractères donnée en paramètre.
     * La chaîne doit être dans le format suivant : "(source1,destination1,poids1);(source2,destination2,poids2);..."
     * @param str la chaîne de caractères à partir de laquelle créer le graphe
     */
    public GrapheLAdj(String str) {
        this(); // appel au constructeur par défaut pour initialiser la liste d'adjacence
        peupler(str); // appel à la méthode peupler pour ajouter les sommets et les arcs à la liste d'adjacence
    }

    /**
     * Ajoute un sommet au graphe avec l'étiquette spécifiée.
     * Si le sommet existe déjà, cette méthode ne fait rien.
     * @param noeud l'étiquette du sommet à ajouter
     */
    @Override
    public void ajouterSommet(String noeud) {
        ladj.putIfAbsent(noeud, new ArrayList<>()); // si le sommet n'existe pas déjà, ajoute-le à la liste d'adjacence
    }


    /**
     * Ajoute un arc au graphe entre les sommets spécifiés avec la valuation spécifiée.
     * Si l'arc existe déjà, une exception est levée.
     * Si la valuation est négative, une exception est levée.
     * @param source l'étiquette du sommet source de l'arc
     * @param destination l'étiquette du sommet destination de l'arc
     * @param valeur la valuation de l'arc
     * @throws IllegalArgumentException si l'arc existe déjà ou si la valuation est négative
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
        // Vérifie si la valuation est négative
        if (valeur < 0) {
            throw new IllegalArgumentException("La valuation de l'arc ne peut pas être négative");
        }

        // Ajoute un nouvel arc à la liste d'adjacence associée au sommet source
        List<Arc> adjacents = ladj.computeIfAbsent(source, k -> new ArrayList<>());

        // Ajoute un nouvel arc à la liste d'adjacence associée au sommet destination
        List<Arc> adjacents2 = ladj.computeIfAbsent(destination, k -> new ArrayList<>());

        for (Arc a : adjacents2) {
            if (a.getDestination().equals(destination)) {
                throw new IllegalArgumentException("L'arc existe déjà");
            }
        }
        // Vérifie si l'arc existe déjà
        for (Arc a : adjacents) {
            if (a.getDestination().equals(destination)) {
                throw new IllegalArgumentException("L'arc existe déjà");
            }
        }

        // Ajoute le nouvel arc à la liste d'arcs adjacents
        adjacents.add(new Arc(source, destination, valeur));
    }

    /**
     * Supprime le sommet spécifié du graphe.
     * Si le sommet n'existe pas dans le graphe, cette méthode ne fait rien.
     * @param noeud l'étiquette du sommet à supprimer
     */
    @Override
    public void oterSommet(String noeud) {
        // Supprime le sommet de la liste d'adjacence
        if (ladj.remove(noeud) == null) {
            return;
        }

        // Supprime tous les arcs adjacents au sommet à supprimer
        for (Map.Entry<String, List<Arc>> entry : ladj.entrySet()) {
            List<Arc> adjacents = entry.getValue();
            for (int i = adjacents.size() - 1; i >= 0; i--) {
                if (adjacents.get(i).getDestination().equals(noeud)) {
                    adjacents.remove(i);
                }
            }
        }
    }

    /**
     * Supprime l'arc entre les sommets source et destination dans le graphe.
     *
     * @param source      le sommet source de l'arc
     * @param destination le sommet destination de l'arc
     *
     * @throws IllegalArgumentException si le sommet source n'existe pas ou si l'arc à supprimer n'existe pas
     */
    @Override
    public void oterArc(String source, String destination) throws IllegalArgumentException {
        // On récupère la liste des arcs adjacents au sommet source
        List<Arc> adjacents = ladj.get(source);
        if (adjacents == null) {
            // Si cette liste est nulle, cela signifie que le sommet source n'existe pas
            throw new IllegalArgumentException("Le sommet source n'existe pas");
        }
        boolean arcSupprime = false;
        for (int i = adjacents.size() - 1; i >= 0; i--) {
            // On parcourt la liste des arcs adjacents à la recherche de celui qui mène au sommet destination
            if (adjacents.get(i).getDestination().equals(destination)) {
                adjacents.remove(i); // On supprime l'arc
                arcSupprime = true;
            }
        }
        if (!arcSupprime) {
            // Si on n'a pas trouvé l'arc à supprimer, cela signifie qu'il n'existe pas
            throw new IllegalArgumentException("L'arc à supprimer n'existe pas");
        }
    }

    /**
     * Retourne une liste de tous les sommets dans le graphe, triée par ordre alphabétique.
     *
     * @return une liste de tous les sommets dans le graphe
     */
    @Override
    public List<String> getSommets() {
        // On récupère toutes les clés du dictionnaire qui représentent les étiquettes des sommets
        List<String> sommets = new ArrayList<>(ladj.keySet());
        // On trie la liste des sommets par ordre alphabétique
        Collections.sort(sommets);
        return sommets;
    }
    /**
     * Retourne une liste de tous les successeurs du sommet donné, triée par ordre alphabétique.
     *
     * @param sommet le sommet dont on veut connaître les successeurs
     * @return une liste de tous les successeurs du sommet donné, triée par ordre alphabétique
     */
    @Override
    public List<String> getSucc(String sommet) {
        // Initialisation d'une nouvelle liste pour stocker les successeurs du sommet donné
        List<String> successeurs = new ArrayList<>();

        // Parcours de tous les arcs sortants du sommet donné
        for (Arc a : ladj.get(sommet)) {
            // Ajout du sommet destination de l'arc à la liste des successeurs
            successeurs.add(a.getDestination());
        }

        // Tri de la liste des successeurs par ordre alphabétique
        Collections.sort(successeurs);

        // Retour de la liste des successeurs triée
        return successeurs;
    }


    /**
     * Retourne la valuation de l'arc entre les sommets source et destination dans le graphe.
     *
     * @param src      le sommet source de l'arc
     * @param dest     le sommet destination de l'arc
     * @return la valuation de l'arc entre les sommets source et destination, ou -1 si l'un des sommets n'existe pas ou si l'arc n'existe pas
     */
    @Override
    public int getValuation(String src, String dest) {
        // Vérification que le sommet source existe dans le graphe
        if (!ladj.containsKey(src)) {
            // Si le sommet source n'existe pas, retourne -1
            return -1;
        }

        // Parcours de tous les arcs sortants du sommet source
        for (Arc a : ladj.get(src)) {
            // Vérification que le sommet destination est égal à celui recherché
            if (a.getDestination().equals(dest)) {
                // Si l'arc existe, retourne sa valuation
                return a.getValuation();
            }
        }

        // Si l'arc n'existe pas, retourne -1
        return -1;
    }

    /**
     * Vérifie si le graphe contient le sommet donné.
     *
     * @param sommet le sommet à rechercher dans le graphe
     * @return true si le graphe contient le sommet donné, false sinon
     */
    @Override
    public boolean contientSommet(String sommet) {
        // Utilisation de la méthode containsKey de la classe HashMap pour vérifier si le sommet est présent dans la liste d'adjacence
        return ladj.containsKey(sommet);
    }

    /**
     * Vérifie si le graphe contient l'arc entre les sommets source et destination.
     *
     * @param src  le sommet source de l'arc à rechercher
     * @param dest le sommet destination de l'arc à rechercher
     * @return true si le graphe contient l'arc entre les sommets source et destination, false sinon
     */
    @Override
    public boolean contientArc(String src, String dest) {
        // Vérifie si le sommet source existe dans le graphe
        if (!ladj.containsKey(src)) {
            return false;
        }
        // Parcours tous les arcs partant du sommet source
        for (Arc a : ladj.get(src)) {
            // Si l'arc a pour destination le sommet recherché, retourne true
            if (a.getDestination().equals(dest)) {
                return true;
            }
        }
        // Si l'arc n'a pas été trouvé, retourne false
        return false;
    }


    /**
     * Retourne une représentation sous forme de chaîne de caractères du graphe.
     *
     * @return une représentation sous forme de chaîne de caractères du graphe
     */
    @Override
    public String toString() {
        return toAString();
        /*// Crée un StringBuilder pour stocker la chaîne de caractères résultante
        StringBuilder sb = new StringBuilder();
        // Obtient la liste de tous les sommets
        List<String> sommets = getSommets();
        // Crée une liste pour stocker tous les arcs sous forme de chaînes de caractères
        List<String> arcs = new ArrayList<>();
        // Parcours tous les sommets
        for (String sommet : sommets) {
            // Obtient la liste des successeurs du sommet
            List<String> successeurs = getSucc(sommet);
            // Si la liste n'est pas vide, parcours tous les successeurs
            if (successeurs != null && !successeurs.isEmpty()) {
                for (String successeur : successeurs) {
                    // Obtient la valuation de l'arc entre le sommet et son successeur
                    int valuation = getValuation(sommet, successeur);
                    // Ajoute la chaîne de caractères représentant l'arc dans la liste d'arcs
                    arcs.add(sommet + "-" + successeur + "(" + valuation + ")");
                }
            }
            // Si la liste est vide, ajoute simplement le sommet suivi d'un deux-points
            if (successeurs == null || successeurs.isEmpty()) {
                arcs.add(sommet + ":");
            }
        }
        // Trie la liste d'arcs par ordre alphabétique
        Collections.sort(arcs);
        // Parcours tous les arcs et les ajoute dans le StringBuilder
        for (String arc : arcs) {
            sb.append(arc).append(", ");
        }
        // Retourne la chaîne de caractères résultante, en supprimant la dernière virgule et l'espace
        return sb.delete(sb.length() - 2, sb.length()).toString();*/
    }
}
