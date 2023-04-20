package graphe;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class AppliGrapheLAdj {

    @Test
    public void testAjouterSommet() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        assertTrue(g.contientSommet("A"));
        assertTrue(g.contientSommet("B"));
        assertTrue(g.contientSommet("C"));
        assertFalse(g.contientSommet("D"));
    }

    @Test
    public void testAjouterArc() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterArc("A", "B", 3);
        g.ajouterArc("A", "C", 4);
        assertTrue(g.contientSommet("A"));
        assertTrue(g.contientSommet("B"));
        assertTrue(g.contientSommet("C"));
        assertTrue(g.getValuation("A","B") == 3);
        assertTrue(g.contientArc("A", "B"));
        assertTrue(g.contientArc("A", "C"));
        assertFalse(g.contientArc("B", "A"));
        assertFalse(g.contientArc("C", "A"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterArcNegatif() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterArc("A", "B", -1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAjouterArcExistant() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterArc("A", "B", 3);
        g.ajouterArc("A", "B", 5);
    }

    @Test
    public void testOterSommet() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        g.ajouterArc("A", "B", 3);
        g.ajouterArc("A", "C", 4);
        g.oterSommet("B");
        assertFalse(g.contientSommet("B"));
        assertFalse(g.contientArc("A", "B"));
        assertTrue(g.contientSommet("A"));
        assertTrue(g.contientSommet("C"));
        assertTrue(g.contientArc("A", "C"));
    }

    @Test
    public void testOterArc() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterArc("A", "B", 3);
        g.ajouterArc("A", "C", 4);
        g.oterArc("A", "B");
        assertFalse(g.contientArc("A", "B"));
        assertTrue(g.contientArc("A", "C"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOterArcInexistant() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterArc("A", "B", 3);
        g.oterArc("A", "C");
    }

    @Test
    public void testGetSommets() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        g.ajouterSommet("A");
        List<String> sommets = g.getSommets();
        assertEquals(3, sommets.size());
        assertTrue(sommets.contains("A"));
        assertTrue(sommets.contains("B"));
        assertTrue(sommets.contains("C"));
    }
    @Test
    public void testGetSucc() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        g.ajouterArc("A", "B", 2);
        g.ajouterArc("A", "C", 5);

        assertEquals(g.getSucc("A").size(), 2);
        assertTrue(g.getSucc("A").contains("B"));
        assertTrue(g.getSucc("A").contains("C"));
    }

    @Test
    public void testGetValuation() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterArc("A", "B", 2);

        assertEquals(g.getValuation("A", "B"), 2);
        assertEquals(g.getValuation("B", "A"), -1);
        assertEquals(g.getValuation("A", "C"), -1);
    }

    @Test
    public void testContientSommet() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");

        assertTrue(g.contientSommet("A"));
        assertTrue(g.contientSommet("B"));
        assertFalse(g.contientSommet("C"));
    }

    @Test
    public void testContientArc() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        g.ajouterArc("A", "B", 2);

        assertTrue(g.contientArc("A", "B"));
        assertFalse(g.contientArc("B", "A"));
        assertFalse(g.contientArc("A", "C"));
    }

    @Test
    public void testToString() {
        GrapheLAdj g = new GrapheLAdj();
        g.ajouterSommet("A");
        g.ajouterSommet("B");
        g.ajouterSommet("C");
        g.ajouterArc("A", "B", 2);
        g.ajouterArc("A", "C", 5);

        String expected = "A-B(2), A-C(5), B:, C:";
        assertEquals(g.toString(), expected);
    }
/*
    public static void main(String[] args) {
        GrapheLAdj graphe = new GrapheLAdj();

        // Ajout de sommets
        graphe.ajouterSommet("A");
        graphe.ajouterSommet("B");
        graphe.ajouterSommet("C");
        graphe.ajouterSommet("D");
        graphe.ajouterSommet("E");

        // Ajout d'arcs
        graphe.ajouterArc("A", "B", 2);
        graphe.ajouterArc("A", "C", 1);
        graphe.ajouterArc("B", "D", 4);
        graphe.ajouterArc("B", "E", 3);
        graphe.ajouterArc("C", "B", 1);
        graphe.ajouterArc("C", "D", 2);
        graphe.ajouterArc("D", "E", 1);

        // Vérification de l'existence de sommets et d'arcs
        System.out.println("Contient sommet A ? " + graphe.contientSommet("A"));
        System.out.println("Contient sommet F ? " + graphe.contientSommet("F"));
        System.out.println("Contient arc A -> B ? " + graphe.contientArc("A", "B"));
        System.out.println("Contient arc B -> A ? " + graphe.contientArc("B", "A"));

        // Suppression de sommets et d'arcs
        graphe.oterSommet("C");
        graphe.oterArc("B", "E");

        // Récupération de la liste des sommets et de leurs successeurs
        System.out.println("Sommets : " + graphe.getSommets());
        System.out.println("Successeurs de A : " + graphe.getSucc("A"));
        System.out.println("Successeurs de D : " + graphe.getSucc("D"));

        // Récupération de la valuation d'un arc
        System.out.println("Valuation de A -> B : " + graphe.getValuation("A", "B"));

        // Affichage du graphe
        System.out.println(graphe);
    }*/
}
