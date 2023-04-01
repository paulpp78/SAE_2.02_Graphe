package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GrapheLArcs implements IGraphe {
	/*private*/ List<Arc> arcs;
	/* SI attribut arcs en visibilité private add: 
	 * public List<Arc> getArcs() {
		return arcs;
	}
	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}
	SINON METTRE VISIBILITE PACKAGE*/

	@Override
	public List<String> getSommets() {
		List<String> sommets= new ArrayList<String>();
		boolean firstSommet=true;
		for(Arc a : arcs) {
			if(firstSommet) {
				sommets.add(a.getSource());
				if(a.getSource()!=a.getDestination())
					sommets.add(a.getDestination());
				//Collections.addAll(sommets, a.getSource(), a.getDestination());
				firstSommet=false;
			}
			else {
				boolean existePasSource = true, existePasDestination =true;
				for(String s: sommets) {
					if (a.getSource()== s)
						existePasSource=false;	
					if(a.getDestination() == s)
						existePasDestination=false;
				}
				if(existePasSource)
					sommets.add(a.getSource());
				if(existePasDestination)
					sommets.add(a.getDestination());
			}
		}
		Collections.sort(sommets);
		return sommets;
	}
		
	@Override
	public void ajouterArc(String source, String destination, Integer valeur) throws IllegalArgumentException {
		if (valeur < 0) {
	        throw new IllegalArgumentException("La valeur de l'arc doit être positive : " + valeur);
	    }

	    Arc arc = new Arc(source, destination, valeur);
	    if (arcs.contains(arc)) {
	        throw new IllegalArgumentException("L'arc " + arc + " existe déjà dans le graphe.");
	    }

	    arcs.add(arc);
	}

	@Override
	public void oterArc(String source, String destination) throws IllegalArgumentException{
		// Trie la liste arcs par ordre alphabétique des noms de source, puis par ordre alphabétique des noms de destination
		Collections.sort(arcs, Comparator.comparing(Arc::getSource).thenComparing(Arc::getDestination));
		
		int debut = 0;
	    int fin = arcs.size() - 1;
	    int milieu;
	    boolean arcTrouve = false;
	    while (debut <= fin && !arcTrouve) {
	        milieu = (debut + fin) / 2;
	        Arc arc = arcs.get(milieu);
	        if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
	            arcs.remove(milieu);
	            arcTrouve = true;
	        } 
	        else if (source.compareTo(arc.getSource()) < 0 || (source.equals(arc.getSource()) && destination.compareTo(arc.getDestination()) < 0)) 
	            fin = milieu - 1;
	        else 
	            debut = milieu + 1;
	    }

	    // Si l'arc n'a pas été trouvé, on lève une exception
	    if (!arcTrouve) {
	        throw new IllegalArgumentException("L'arc spécifié n'existe pas dans le graphe.");
	    }
	}

	@Override
	public boolean contientArc(String src, String dest) {
	    int debut = 0;
	    int fin = arcs.size() - 1;
	    
	    while (debut <= fin) {
	        int milieu = (debut + fin) / 2;
	        Arc arc = arcs.get(milieu);
	        
	        int comparaison = arc.getSource().compareTo(src);
	        if (comparaison < 0) {
	            debut = milieu + 1;
	        } else if (comparaison > 0) {
	            fin = milieu - 1;
	        } else {
	            comparaison = arc.getDestination().compareTo(dest);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getValuation(String src, String dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contientSommet(String sommet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void ajouterSommet(String noeud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oterSommet(String noeud) {
		// TODO Auto-generated method stub
		
	}

	 public String toString() {
	     //trier arcs avant affichage si pas déjà triés dans les autres méthodes
		 //cf compareTO p.40 cours
		 return null;
	    }

}
