package graphe;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GrapheHHAdj implements IGraphe {

    private Map<String, Map<String, Integer>> hhadj;

    public GrapheHHAdj() {
        hhadj = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String sommet) {
        hhadj.putIfAbsent(sommet, new HashMap<>());
    }

    @Override
    public void oterSommet(String sommet) {
        hhadj.remove(sommet);
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            entry.getValue().remove(sommet);
        }
    }

    @Override
    public void ajouterArc(String src, String dest, int valuation) {
        ajouterSommet(src);
        ajouterSommet(dest);
        hhadj.get(src).put(dest, valuation);
    }

    @Override
    public void oterArc(String src, String dest) {
        Map<String, Integer> adjacents = hhadj.get(src);
        if (adjacents != null) {
            adjacents.remove(dest);
        }
    }

    @Override
    public List<String> getSommets() {
        return new ArrayList<>(hhadj.keySet());
    }

    @Override
    public List<String> getSucc(String sommet) {
        Map<String, Integer> adjacents = hhadj.get(sommet);
        if (adjacents != null) {
            return new ArrayList<>(adjacents.keySet());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public int getValuation(String src, String dest) {
        Map<String, Integer> adjacents = hhadj.get(src);
        if (adjacents != null) {
            Integer valuation = adjacents.get(dest);
            if (valuation != null) {
                return valuation;
            }
        }
        return -1;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return hhadj.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        Map<String, Integer> adjacents = hhadj.get(src);
        if (adjacents != null) {
            return adjacents.containsKey(dest);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Integer>> entry : hhadj.entrySet()) {
            String src = entry.getKey();
            for (Map.Entry<String, Integer> adj : entry.getValue().entrySet()) {
                String dest = adj.getKey();
                int valuation = adj.getValue();
                sb.append(src).append("-").append(dest).append("(").append(valuation).append("), ");
            }
        }
        return sb.toString();
    }

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		// TODO Auto-generated method stub
		
	}
}
