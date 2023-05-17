package main.java.graphe.implems;

import main.java.graphe.core.IGraphe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe implémente l'interface IGraphe et représente un graphe
 * à l'aide d'une matrice d'adjacence.
 */
public class GrapheMAdj implements IGraphe {

    /**
     * Valeur utilisée pour indiquer une absence de valuation ou un arc inexistant.
     */
    public static final int Vide = -1;

    /**
     * Valeur utilisée pour l'extension de la matrice d'adjacence.
     */
    public static final int PAS_EXTENSION = 1;

    private int[][] matrice; // Matrice d'adjacence représentant le graphe
    private Map<String, Integer> indices; // Map pour associer les sommets à leurs indices dans la matrice

    /**
     * Constructeur par défaut.
     * Crée un graphe vide sans sommets ni arcs.
     */
    public GrapheMAdj() {
        matrice = new int[0][0];
        indices = new HashMap<>();
    }

    /**
     * Constructeur avec un graphe représenté sous forme de chaîne de caractères.
     * Crée un graphe à partir de la chaîne de caractères spécifiée.
     *
     * @param graphe la chaîne de caractères représentant le graphe.
     */
    public GrapheMAdj(String graphe) {
        this();
        peupler(graphe);
    }

    /**
     * Ajoute un sommet au graphe.
     *
     * @param noeud le nom du sommet à ajouter.
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (contientSommet(noeud))
            return;
        int dernierInd = matrice.length;
        indices.put(noeud, dernierInd);
        extend(dernierInd);
    }

    /**
     * Ajoute un arc entre deux sommets avec une valuation spécifiée.
     *
     * @param source      le sommet source de l'arc.
     * @param destination le sommet destination de l'arc.
     * @param valeur      la valuation de l'arc.
     * @throws IllegalArgumentException si la valuation est négative ou si l'arc existe déjà.
     */
    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (valeur < 0 || contientArc(source, destination))
            throw new IllegalArgumentException();
        ajouterSommet(source);
        ajouterSommet(destination);

        int[] indexPair = getIndexPair(source, destination);
        matrice[indexPair[0]][indexPair[1]] = valeur;
    }

    /**
     * Supprime un sommet du graphe.
     *
     * @param noeud le nom du sommet à supprimer.
     */
    @Override
    public void oterSommet(String noeud) {
        if (contientSommet(noeud)) {
            int indSommet = getIndex(noeud);
            clearArcsFromNode(indSommet);
            indices.remove(noeud);
        }
    }

    /**
     * Supprime l'arc entre deux sommets.
     *
     * @param source      le sommet source de l'arc.
     * @param destination le sommet destination de l'arc.
     * @throws IllegalArgumentException si l'arc n'existe pas.
     */
    @Override
    public void oterArc(String source, String destination) {
        if (!contientArc(source, destination))
            throw new IllegalArgumentException();
        int[] indexPair = getIndexPair(source, destination);
        matrice[indexPair[0]][indexPair[1]] = Vide;
    }

    /**
     * Retourne la liste des sommets du graphe.
     *
     * @return une liste contenant les noms des sommets du graphe.
     */
    @Override
    public List<String> getSommets() {
        return new ArrayList<>(indices.keySet());
    }

    /**
     * Retourne la liste des successeurs d'un sommet donné.
     *
     * @param sommet le nom du sommet.
     * @return une liste contenant les noms des successeurs du sommet.
     */
    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        int indSommet = getIndex(sommet);
        for (String som : getSommets()) {
            int ind = getIndex(som);
            if (aUnArc(indSommet, ind)) {
                successeurs.add(som);
            }
        }
        return successeurs;
    }

    /**
     * Retourne la valuation de l'arc entre deux sommets.
     *
     * @param src  le sommet source de l'arc.
     * @param dest le sommet destination de l'arc.
     * @return la valuation de l'arc, ou Vide si l'arc n'existe pas.
     */
    @Override
    public int getValuation(String src, String dest) {
        if (!contientArc(src, dest))
            return Vide;
        int[] indexPair = getIndexPair(src, dest);
        return matrice[indexPair[0]][indexPair[1]];
    }

    /**
     * Vérifie si le graphe contient le sommet spécifié.
     *
     * @param sommet le nom du sommet à vérifier.
     * @return true si le graphe contient le sommet, sinon false.
     */
    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    /**
     * Vérifie si le graphe contient un arc entre deux sommets spécifiés.
     *
     * @param src   le sommet source de l'arc.
     * @param dest  le sommet destination de l'arc.
     * @return true si le graphe contient l'arc, sinon false.
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (!contientSommet(src) || !contientSommet(dest))
            return false;
        int[] indexPair = getIndexPair(src, dest);
        return aUnArc(indexPair[0], indexPair[1]);
    }

    /**
     * Retourne une représentation en chaîne de caractères du graphe.
     *
     * @return une chaîne de caractères représentant le graphe.
     */
    @Override
    public String toString() {
        return toAString();
    }

    /**
     * Étend la matrice d'adjacence du graphe avec la taille spécifiée.
     *
     * @param last la taille spécifiée pour l'extension de la matrice.
     */
    private void extend(int last) {
        int[][] newMat = new int[last + PAS_EXTENSION][last + PAS_EXTENSION];
        for (int i = 0; i < newMat.length; ++i) {
            for (int j = 0; j < newMat.length; ++j) {
                newMat[i][j] = (i >= last || j >= last) ? Vide : matrice[i][j];
            }
        }
        matrice = newMat;
    }

    /**
     * Vérifie si un arc existe entre deux sommets spécifiés dans la matrice d'adjacence.
     *
     * @param src  l'indice du sommet source.
     * @param dest l'indice du sommet destination.
     * @return true si un arc existe entre les sommets, sinon false.
     */
    private boolean aUnArc(int src, int dest) {
        return matrice[src][dest] != Vide;
    }

    /**
     * Supprime tous les arcs sortant d'un sommet spécifié dans la matrice d'adjacence.
     *
     * @param nodeIndex l'indice du sommet à partir duquel les arcs seront supprimés.
     */
    private void clearArcsFromNode(int nodeIndex) {
        for (int i = 0; i < matrice.length; ++i) {
            matrice[nodeIndex][i] = Vide;
        }
    }

    /**
     * Retourne l'indice associé à un sommet spécifié dans la matrice d'adjacence.
     *
     * @param sommet le nom du sommet.
     * @return l'indice associé au sommet.
     */
    private int getIndex(String sommet) {
        return indices.get(sommet);
    }

    /**
     * Retourne un tableau contenant les indices associés aux sommets source et destination dans la matrice d'adjacence.
     *
     * @param src  le nom du sommet source.
     * @param dest le nom du sommet destination.
     * @return un tableau contenant les indices associés aux sommets source et destination.
     */
    private int[] getIndexPair(String src, String dest) {
        int srcIndex = getIndex(src);
        int destIndex = getIndex(dest);
        return new int[]{srcIndex, destIndex};
    }
}
