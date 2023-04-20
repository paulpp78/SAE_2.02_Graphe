package graphe;

import java.util.*;
import java.util.stream.Collectors;

public class GrapheMAdj extends Graphe{

    private int[][] matrice;
    private Map<String, Integer> indices;

    int INFINITY = Integer.MAX_VALUE;


    public GrapheMAdj() {

        indices = new HashMap<String, Integer>();
        int nbSommets = 10;
        matrice = new int[nbSommets][nbSommets];
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                matrice[i][j] = INFINITY;
            }
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
        }
    }

    @Override
    public void ajouterArc(String nomDepart, String nomArrivee, Integer poids) {

        if (poids < 0) {
            throw new IllegalArgumentException("La valuation de l'arc ne peut pas être négative");
        }

        int indexDepart = indices.get(nomDepart);
        int indexArrivee = indices.get(nomArrivee);

        if(matrice[indexDepart][indexArrivee] != INFINITY){
            throw new IllegalArgumentException("L'arc existe déjà");
        }


        matrice[indexDepart][indexArrivee] = poids;
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
        for (int i = sommetIndex; i < matrice.length - 1; i++) {
            matrice[i] = matrice[i + 1];
        }

        for (int i = 0; i < matrice.length; i++) {
            for (int j = sommetIndex; j < matrice.length - 1; j++) {
                matrice[i][j] = matrice[i][j + 1];
            }
        }

        for (int i = sommetIndex; i < matrice.length - 1; i++) {
            for (int j = 0; j < matrice.length; j++) {
                matrice[i][j] = matrice[i + 1][j];
            }
        }

        for (int i = 0; i < matrice.length - 1; i++) {
            matrice[i] = Arrays.copyOf(matrice[i], matrice.length - 1);
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
        matrice[sourceIndex][destinationIndex] = 0;
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(indices.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        List<String> successeurs = new ArrayList<>();
        int i = indices.get(sommet);

        for (int j = 0; j < matrice[i].length; j++) {
            if (matrice[i][j] != INFINITY) {
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
                return matrice[indexDepart][indexArrivee];
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
        return matrice[indexDep][indexArr] != INFINITY;
    }
}