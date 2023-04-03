package graphe;
/*DMD ZIANE getValuation : 
 * Plutôt que de parcourir la liste arcs deux fois pour chercher l'arc et sa valuation, il est possible 
 * de faire les deux en même temps. Pour cela, on peut modifier la méthode contientArc pour qu'elle retourne
 *  l'arc trouvé si celui-ci existe dans le graphe. On peut alors appeler cette méthode dans getValuation
 *   et retourner la valuation de l'arc trouvé.Plutôt que de trier la liste arcs à chaque appel de contientArc, 
 *   on peut trier la liste une seule fois au moment de sa création, ou lorsqu'on y ajoute un nouvel arc. 
 *   Cela permet d'optimiser les appels répétés à contientArc.*/

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


	/*public GrapheLArcs(String listeArc) {
        this();
        String[] arcsString = listeArc.split(", "); // Sépare la chaîne de caractères en arcs individuels.
        for (String arcString : arcsString) {
            String[] parts = arcString.split("[()]"); // Sépare l'arc en ses parties : source, destination, valuation.
            String source = parts[0];
            String destination = parts[1];
            int valuation = Integer.parseInt(parts[2]);
            arcs.add(new Arc(source, destination, valuation)); // Ajoute l'arc à la liste d'arcs.
            sommets.add(source); // Ajoute le sommet source à l'ensemble de sommets.
            sommets.add(destination); // Ajoute le sommet destination à l'ensemble de sommets.
        }
    } */
	public GrapheLArcs(String chaineArcs) {
    this();
    String[] listeArcs = chaineArcs.split(", ");
    for (String s : listeArcs) {
        if (s.contains("-")) {
            String[] parts = s.split("-");
            String source = parts[0].trim();
            String[] destVal = parts[1].split("\\(");
            String destination = destVal[0].trim();
            int valuation = Integer.parseInt(destVal[1].substring(0, destVal[1].length()-1).trim());
            ajouterArc(source, destination, valuation);
        } else {
            String sommet = s.trim();
            ajouterSommet(sommet);
        }
    }
}



	public void trierArcs() {
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));
	}
	

    @SuppressWarnings("unchecked")
	@Override
    public List<String> getSommets() {
        Set<String> sommets = new HashSet<>();
        for (Arc a : arcs) {
            sommets.add(a.getSource());
            sommets.add(a.getDestination());
        }
        //List<String> sortedSommets = new ArrayList<>(sommets);
        //Collections.sort(sortedSommets);
        //return sortedSommets;
        return (List<String>) sommets;
    }
    
	 @Override
	    public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
	        if (valeur < 0) {
	            throw new IllegalArgumentException("La valeur de l'arc doit être positive : " + valeur);
	        }

	        Arc arc = new Arc(source, destination, valeur);
	        if (contientArc(source,destination)) {
	            throw new IllegalArgumentException("L'arc " + arc + " existe déjà dans le graphe.");
	        }
	        
	        arcs.add(arc);
	        trierArcs();
	    }


	//si on doit lever une exception, implémenter cette méthode:
	public void ajouterSommet(String noeud) throws IllegalArgumentException {
		if (contientSommet(noeud)) {
			throw new IllegalArgumentException("Le sommet " + noeud + " existe déjà dans le graphe.");
		}
		arcs.add(new Arc(noeud, noeud, 0));
	}

	@Override
	public void oterArc(String source, String destination) throws IllegalArgumentException {
		
	    // Si l'arc n'a pas été trouvé, on lève une exception
	    if (!contientArc(source, destination)) {
	        throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
	    }
	    else {
	    	//CF AUTRE VERSION AVEC ITERATOR DANS ...CG
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

        // On ne devrait jamais atteindre ce point, mais on lève une exception au cas où
        throw new IllegalStateException("Une erreur est survenue lors de la recherche de la valuation de l'arc.");
    }

    //CF AUTRE VERSION oterSommet ...CG
	@Override
	public void oterSommet(String sommet) {
	    // Création d'un HashSet pour stocker les arcs
	    Set<Arc> arcsSet = new HashSet<>(arcs);
	    
	    // Parcourir le HashSet pour supprimer les arcs liés au sommet
	    for (Arc arc : arcsSet) {
	        if (arc.getSource().equals(sommet) || arc.getDestination().equals(sommet)) 
	            oterArc(arc.getSource(), arc.getDestination());
	        
	    }
	}
	
	/*FONCTIONNE PAS EN L ETAT
	 * @Override
	 *
	public boolean contientSommet(String noeud) {
		// la méthode contains() de HashSet pour effectuer une 
		//recherche en temps constant.
		//évite de parcourir l'ensemble des sommets
	    return sommets.contains(noeud);
	}*/
		
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
		// Trie la liste arcs par ordre alphabétique pour pouvoir faire recherche dicho
		trierArcs();
		
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
	

	//-----------------------------------------------------------------------------------
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
	/* Autre version cg:
	 * public List<String> getSucc(String sommet) {
    return arcs.stream()
            .filter(arc -> arc.getSource().equals(sommet))
            .map(Arc::getDestination)
            .collect(Collectors.toList());
}

	 */
	
	@Override
	 public String toString() {
		return null;
	}
}