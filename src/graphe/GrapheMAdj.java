package graphe;

import java.util.*;
import java.util.stream.Collectors;

public class GrapheMAdj extends Graphe{

    private List<List<Integer>> matrice;
    private Map<String, Integer> indices;

    int INFINITY = Integer.MAX_VALUE;


    public GrapheMAdj() {

        indices = new HashMap<String, Integer>();
        int nbSommets = 10;
        matrice = new ArrayList<>(nbSommets);
        for (int i = 0; i < nbSommets; i++) {
            List<Integer> row = new ArrayList<>(nbSommets);
            for (int j = 0; j < nbSommets; j++) {
                row.add(INFINITY);
            }
            matrice.add(row);
        }

    }


    public GrapheMAdj(String string){
        this();
        peupler(string);
    }

    @Override
    public void ajouterSommet(String nomSommet) {
        if (!indices.containsKey(nomSommet)) {
            int index = indices.size();
            indices.put(nomSommet, index);

            // Ajouter une nouvelle ligne à la matrice
            List<Integer> row = new ArrayList<>(matrice.size());
            for (int i = 0; i < matrice.size(); i++) {
                row.add(INFINITY);
            }
            matrice.add(row);
        }
    }

    @Override
    public void ajouterArc(String nomDepart, String nomArrivee, Integer poids) {

        if (poids < 0) {
            throw new IllegalArgumentException("La valuation de l'arc ne peut pas être négative");
        }


        if(indices.get(nomDepart)==null){
            ajouterSommet(nomDepart);
        }
        int indexDepart = indices.get(nomDepart);

        if(indices.get(nomArrivee)==null){
            ajouterSommet(nomArrivee);
        }
        int indexArrivee = indices.get(nomArrivee);

        if (matrice.get(indexDepart).get(indexArrivee) != INFINITY) {
            throw new IllegalArgumentException("L'arc existe déjà");
        }


        matrice.get(indexDepart).set(indexArrivee, poids);
    }

    @Override
    public void oterSommet(String sommet) {
        // Vérifie si le sommet existe dans le graphe
        if (!indices.containsKey(sommet)) {
            return;
        }

        // Obtient l'indice du sommet à retirer
        int sommetIndex = indices.get(sommet);

        // Retire le sommet de la map des indices
        indices.remove(sommet);

        // Retire la ligne et la colonne correspondant au sommet de la matrice d'adjacence
        matrice.remove(sommetIndex);
        for (List<Integer> row : matrice) {
            row.remove(sommetIndex);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        // Vérifie si les sommets existent dans le graphe
        if (!indices.containsKey(source) || !indices.containsKey(destination)) {
            throw new IllegalArgumentException("Les sommets n'existent pas dans le graphe");
        }

        // Obtient les indices des sommets source et destination
        int sourceIndex = indices.get(source);
        int destinationIndex = indices.get(destination);

        // Retire l'arc de la matrice d'adjacence
        matrice.get(sourceIndex).set(destinationIndex, INFINITY);
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(indices.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        int i = indices.get(sommet);

        List<Integer> row = matrice.get(i);
        for (int j = 0; j < row.size(); j++) {
            if (row.get(j) != INFINITY) {
                for (Map.Entry<String, Integer> entry : indices.entrySet()) {
                    if (entry.getValue() == j) {
                        successeurs.add(entry.getKey());
                        break;
                    }
                }
            }
        }


        return successeurs;
    }


    @Override
    public int getValuation(String src, String dest) {
        if(contientSommet(src) && contientSommet(dest)) {
            if (contientArc(src, dest)) {
                int indexDepart = indices.get(src);
                int indexArrivee = indices.get(dest);
                return matrice.get(indexDepart).get(indexArrivee);
            }
            else {
                return -1; // ou une autre valeur qui indique que l'arc n'existe pas
            }
        }
        else {
            throw new IllegalArgumentException("Sommet inexistant");
        }
    }


    @Override
    public boolean contientSommet(String sommet) {

        return indices.containsKey(sommet);

    }


    @Override
    public boolean contientArc(String nomSommetDep, String nomSommetArr) {

        if (!indices.containsKey(nomSommetDep) || !indices.containsKey(nomSommetArr)) {
            return false;
        }
        int indexDep = indices.get(nomSommetDep);
        int indexArr = indices.get(nomSommetArr);
        return matrice.get(indexDep).get(indexArr) != INFINITY;
    }
}