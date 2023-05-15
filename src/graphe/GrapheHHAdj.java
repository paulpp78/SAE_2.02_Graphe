package graphe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GrapheHHAdj extends Graphe{
    private Map<String, Map<String,Integer>> hhadj;



    public GrapheHHAdj() {

        hhadj = new HashMap<>();
    }

    public GrapheHHAdj(String string){
        this();
        peupler(string);
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud))
            hhadj.put(noeud, new HashMap<>());
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if(valeur<0){
            throw new IllegalArgumentException("Valeur invalide");
        }
        if(contientArc(source, destination)) {
            throw new IllegalArgumentException("Arc déjà existante");
        }
        if(!contientSommet(source)){
            ajouterSommet(source);
        }
        if(!contientSommet(destination)){
            ajouterSommet(destination);
        }
        hhadj.get(source).put(destination, valeur);
    }

    @Override
    public void oterSommet(String noeud) {
        if(contientSommet(noeud)) {
            for (Map<String, Integer> voisins : hhadj.values()) {
                voisins.remove(noeud);
            }
            hhadj.remove(noeud);
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination)) {
            hhadj.get(source).remove(destination);
        }
        else {
            throw new IllegalArgumentException("Arc inexistante");
        }
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(hhadj.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        return (contientSommet(sommet))? new ArrayList<>(hhadj.get(sommet).keySet()):null;
    }


    @Override
    public int getValuation(String src, String dest) {
        if(contientSommet(src) && contientSommet(dest)) {
            if (contientArc(src, dest)) {
                return hhadj.get(src).get(dest);
            }
            else {
                return -1; // ou une autre valeur qui indique que l'arc n'existe pas
            }
        }
        else {
            throw new IllegalArgumentException("Sommet inexistante");
        }
    }


    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }


    @Override
    public boolean contientArc(String src, String dest) {
        return contientSommet(src) && hhadj.get(src).containsKey(dest);
    }
}