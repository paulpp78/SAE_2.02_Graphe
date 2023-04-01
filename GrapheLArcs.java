package graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrapheLArcs implements IGraphe {
	private List<Arc> arcs;
	
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
	public boolean contientArc(String src, String dest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void ajouterSommet(String noeud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oterSommet(String noeud) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void oterArc(String source, String destination) {
		// TODO Auto-generated method stub
		
	}

	 public String toString() {
	     //trier arcs avant affichage si pas déjà triés dans les autres méthodes
		 //cf compareTO p.40 cours
		 return null;
	    }
}
