package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra {
    public static List<String> dijkstra(IGrapheConst graphe, String depart, Map<String, Integer> dist, Map<String, String> prev) {
        PriorityQueue<String> nonVisites = new PriorityQueue<>(graphe.getSommets().size(), (s1, s2) -> dist.getOrDefault(s1, Integer.MAX_VALUE) - dist.getOrDefault(s2, Integer.MAX_VALUE));

        dist.put(depart, 0);
        nonVisites.add(depart);

        while (!nonVisites.isEmpty()) {
            String sommetCourant = nonVisites.poll();

            for (String successeur : graphe.getSucc(sommetCourant)) {
                if (!graphe.contientArc(sommetCourant, successeur)) {
                    continue;
                }

                int poidsArc = graphe.getValuation(sommetCourant, successeur);
                int distanceViaSommetCourant = dist.get(sommetCourant) + poidsArc;

                if (!dist.containsKey(successeur) || distanceViaSommetCourant < dist.get(successeur)) {
                    dist.put(successeur, distanceViaSommetCourant);
                    prev.put(successeur, sommetCourant);
                    nonVisites.add(successeur);
                }
            }
        }

        return new ArrayList<>(dist.keySet());
    }

    public static List<String> plusCourtChemin(IGrapheConst graphe, String depart, String arrivee) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> predecesseur = new HashMap<>();

        dijkstra(graphe, depart, distance, predecesseur);

        if (!predecesseur.containsKey(arrivee)) {
            return null; // Pas de chemin trouvé
        }

        List<String> chemin = new ArrayList<>();
        String sommet = arrivee;

        while (!sommet.equals(depart)) {
            chemin.add(0, sommet);
            sommet = predecesseur.get(sommet);
        }

        chemin.add(0, depart);
        return chemin;
    }
}
