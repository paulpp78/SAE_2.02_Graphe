package graphe;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppliGrapheLArcs {

    @Test
    public void testAjoutSommet() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterSommet("A");
        assertTrue(graphe.contientSommet("A"));
        assertFalse(graphe.contientSommet("B"));
    }

    @Test
    public void testOterSommet() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterSommet("A");
        graphe.ajouterSommet("B");
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("B", "A", 3); // Ajout d'un arc de B vers A pour tester la suppression des arcs dans les deux sens
        graphe.oterSommet("A");
        assertFalse(graphe.contientSommet("A"));
        assertTrue(graphe.contientSommet("B"));
        assertFalse(graphe.contientArc("A", "B"));
        assertFalse(graphe.contientArc("B", "A"));
    }




    @Test
    public void testAjouterArc() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterSommet("A");
        graphe.ajouterSommet("B");
        graphe.ajouterArc("A", "B", 5);
        assertTrue(graphe.contientArc("A", "B"));
        assertFalse(graphe.contientArc("B", "A"));
        assertEquals(5, graphe.getValuation("A", "B"));
    }


    @Test
    public void testOterArc() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterArc("A", "B", 6);
        graphe.ajouterArc("B", "C", 2);
        graphe.oterArc("A", "B");
        assertFalse(graphe.contientArc("A", "B"));
        assertTrue(graphe.contientArc("B", "C"));
    }

    @Test
    public void testGetValuation() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterArc("A", "B", 6);
        graphe.ajouterArc("B", "C", 2);
        assertEquals(6, graphe.getValuation("A", "B"));
        assertEquals(2, graphe.getValuation("B", "C"));
        assertThrows(IllegalArgumentException.class, () -> {
            graphe.getValuation("A", "C");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            graphe.getValuation("B", "A");
        });
    }


    @Test
    public void testGetSucc() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterArc("A", "B", 6);
        graphe.ajouterArc("A", "C", 3);
        graphe.ajouterArc("B", "C", 2);
        assertEquals(2, graphe.getSucc("A").size());
        assertEquals(1, graphe.getSucc("B").size());
        assertEquals(0, graphe.getSucc("C").size());
    }

    @Test
    public void testToString() {
        GrapheLArcs graphe = new GrapheLArcs();
        graphe.ajouterArc("A", "B", 6);
        graphe.ajouterArc("A", "C", 3);
        graphe.ajouterArc("B", "C", 2);
        graphe.ajouterArc("C", "D", 5);
        graphe.ajouterArc("D", "A", 1);
        String attendu = "A-B(6), A-C(3), B-C(2), C-D(5), D-A(1)";
        assertEquals(attendu, graphe.toString());
    }
    @Test
    public void testGraphe() {
        GrapheLArcs graphe = new GrapheLArcs();
        // Ajout des arcs
        graphe.ajouterArc("A", "B", 6);
        graphe.ajouterArc("A", "C", 3);
        graphe.ajouterArc("B", "C", 47);
        graphe.ajouterArc("C", "D", 2);
        graphe.ajouterArc("D", "B", 4);

        // Vérification de la liste des sommets
        String[] sommetsAttendus = {"A", "B", "C", "D"};
        assertArrayEquals(sommetsAttendus, graphe.getSommets().toArray());

        // Vérification de la présence de sommets
        assertTrue(graphe.contientSommet("A"));
        assertFalse(graphe.contientSommet("E"));

        // Suppression d'un sommet et vérification
        graphe.oterSommet("A");
        String[] sommetsAttendusApresSuppression = {"B", "C", "D"};
        assertArrayEquals(sommetsAttendusApresSuppression, graphe.getSommets().toArray());

        // Ajout d'un sommet et vérification
        graphe.ajouterSommet("E");
        assertTrue(graphe.contientSommet("E"));

        // Ajout d'un arc et vérification
        graphe.ajouterArc("E", "D", 10);
        assertTrue(graphe.contientArc("E", "D"));

        // Suppression d'un arc et vérification
        graphe.oterArc("B", "C");
        assertFalse(graphe.contientArc("B", "C"));

        // Vérification de la valuation d'un arc
        assertEquals(2, graphe.getValuation("C", "D"));

        // Vérification des successeurs d'un sommet
        String[] successeursAttendus = {"D"};
        assertArrayEquals(successeursAttendus, graphe.getSucc("C").toArray());

        // Vérification de la représentation du graphe
        String grapheAttendu = "C-D(2), D-B(4), E-D(10)";
        assertEquals(grapheAttendu, graphe.toString());
    }
}



