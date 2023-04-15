package graphe;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrapheLArcs implements IGraphe {
	List<Arc> arcs;
	private Set<String> sommets;
	
	public GrapheLArcs() {
	    this.arcs = new ArrayList<>();
	    this.sommets = new HashSet<>();
	}

	public GrapheLArcs(String str) {
		this();
		peupler(str);
 
	}

	public void trierArcs() {
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));
	}
	

	@Override
	public List<String> getSommets() {
	    Set<String> sommets = new HashSet<>();
	    for (Arc a : arcs) {
	    	String source = a.getSource().replace(":", "");
	        String destination = a.getDestination().replace(":", "");
	        sommets.add(source);
	        sommets.add(destination);
	    }
	    
	    List<String> sommetsList = new ArrayList<>(sommets);
	    //Collections.sort(sommetsList);
	    return sommetsList;
	}

	/*@Override
    public List<String> getSommets() {
        Set<String> sommets = new HashSet<>();
        for (Arc a : arcs) {
            sommets.add(a.getSource());
            sommets.add(a.getDestination());
        }
        //List<String> sortedSommets = new ArrayList<>(sommets);
        //Collections.sort(sortedSommets);
        //return sortedSommets;
        /*--------------------------CF SI IL FAUT SORTED POUR TEST--------------------------------------------//
        //return (List<String>) sommets;
        return new ArrayList<>(sommets);

    }
	@Override
	public List<String> getSommets() {
	    Set<String> sommets = new HashSet<>();
	    for (Arc a : arcs) {
	        sommets.add(a.getSource());
	        sommets.add(a.getDestination());
	    }
	    List<String> sommetsList = new ArrayList<>(sommets);
	    sommetsList.removeIf(s -> s.endsWith(":"));
	    return sommetsList;
	}*/

	 @Override
	  public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
	        if (valeur < 0) {
	            throw new IllegalArgumentException("La valeur de l'arc doit être positive : " + valeur);
	        }
	        /*---cf si utile-----*/
	        if (source.equals("") && valeur == 0 && !destination.equals("")) {
	            this.ajouterSommet(destination);
	             
	        } 
	        if(destination.equals("") && valeur==0 && !source.equals("")) {
	        	this.ajouterSommet(source);
	        }
	        /*-------------------*/
	        else{
	        	Arc arc = new Arc(source, destination, valeur);
	        	if (contientArc(source,destination)) 
		            throw new IllegalArgumentException("L'arc " + arc + " existe déjà dans le graphe.");
	        	arcs.add(arc);
	       }
	        trierArcs();
	    }


	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) 
			arcs.add(new Arc(noeud, noeud, 0));
	}

	@Override
	public void oterArc(String source, String destination) throws IllegalArgumentException {
	    if (!contientArc(source, destination)) {
	        throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
	    }
	    else {
	    	//CF AUTRE VERSION AVEC ITERATOR DANS ...
	        Arc arc = new Arc(source, destination, getValuation(source, destination));
	        arcs.remove(arc);
	        // Retirer les sommets impliqués dans l'arc supprimé de la liste sommets
	        sommets.remove(source);
	        sommets.remove(destination);
	    }
	}
	
    @Override
    public int getValuation(String source, String destination) throws IllegalArgumentException {
        if (!contientArc(source, destination)) {
            throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
        }

        for (Arc arc : arcs) {
            if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                return arc.getValuation();
            }
        }
		return -1;
 
    }

    //CF AUTRE VERSION oterSommet ...
	@Override
	public void oterSommet(String sommet) {
	    // Création d'un HashSet pour stocker les arcs
	    Set<Arc> arcsSet = new HashSet<>(arcs);
	    
	    for (Arc arc : arcsSet) {
	        if (arc.getSource().equals(sommet) || arc.getDestination().equals(sommet)) 
	            oterArc(arc.getSource(), arc.getDestination());
	        
	    }
	}
	
		
	@Override
	public boolean contientSommet(String noeud) {
	    for (Arc arc : arcs) {
	        if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
	            return true;
	        }
	    }
	    return false;
	}

	
	@Override
	public boolean contientArc(String source, String destination) {
	    trierArcs();
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


	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<>();
		for (Arc arc : arcs) {
			if (arc.getSource().equals(sommet)&& !arc.getDestination().equals(sommet)) {
				succ.add(arc.getDestination());
			}
		}
		return succ;
	}
	
	
	/* ANCIENNE VERSION TOSTRING MAIS AVEC ERREUR
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    trierArcs();
	    int valuation;
	    List<String> sortedSommets = new ArrayList<>(sommets);
	    // trier les sommets
	    Collections.sort(sortedSommets);
	    boolean isLastArc = false;
	    for (int i = 0; i < arcs.size(); i++) {
	        Arc arc = arcs.get(i);
	        String source = arc.getSource();
	        String destination = arc.getDestination();
	        valuation = arc.getValuation();
	        if (source.equals(destination)) {
	            if (valuation == 0) {
	                sortedSommets.remove(source);
	            } else {
	                sb.append(source + "-" + destination + "(" + valuation + ")");
	            }
	        } else {
	            sb.append(source + "-" + destination + "(" + valuation + ")");
	        }
	        // Si c'est le dernier arc, on ne met pas de virgule
	        if (i == arcs.size() - 1) {
	            isLastArc = true;
	        }
	        // On met une virgule si ce n'est pas le dernier arc
	        if (!isLastArc) {
	            sb.append(", ");
	        }
	    }
	    // ajouter les sommets non reliés aux autres
	    for (String s : sortedSommets) {
	        sb.append(s + ":");
	    }
	    return sb.toString();
	}*/
	
	@Override
	public String toString() {
		return toAString();
	}
}