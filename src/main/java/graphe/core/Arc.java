package main.java.graphe.core;

import java.util.Objects;


/**
 * Cette classe représente un arc dans un graphe.
 */
public class Arc {

    /**
     * L'élément source de l'arc.
     */
    private final String source; // Le sommet source de l'arc

    /**
     * L'élément destination de l'arc.
     */
    private final String destination; // Le sommet destination de l'arc

    /**
     * La valuation (poids) de l'arc.
     * La valuation peut être un entier positif ou négatif.
     */
    private final int valuation;

    /**
     * Crée un nouvel arc avec une valuation donnée.
     *
     * @param source       Le sommet source de l'arc.
     * @param destination  Le sommet destination de l'arc.
     * @param valuation    La valuation de l'arc.
     */
    public Arc(String source, String destination, int valuation) {
        this.source = source;
        this.destination = destination;
        this.valuation = valuation;
    }

    /**
     * Retourne le sommet source de l'arc.
     *
     * @return Le sommet source de l'arc.
     */
    public String getSource() {
        return source;
    }

    /**
     * Retourne le sommet destination de l'arc.
     *
     * @return Le sommet destination de l'arc.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Retourne la valuation de l'arc.
     *
     * @return La valuation de l'arc.
     */
    public int getValuation() {
        return valuation;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'arc.
     *
     * @return La représentation sous forme de chaîne de caractères de l'arc.
     */
    @Override
    public String toString() {
        return source + "-" + destination + "(" + valuation + ")";
    }

    /**
     * Compare deux arcs pour savoir s'ils sont égaux.
     *
     * @param obj L'objet à comparer avec cet arc.
     * @return true si l'objet est égal à cet arc, false sinon.
     */
    @Override
    public boolean equals(Object obj) {
        // Si l'objet est la même instance que l'arc, ils sont égaux
        if (obj == this) {
            return true;
        }

        // Si l'objet n'est pas une instance de Arc, ils ne sont pas égaux
        if (!(obj instanceof Arc)) {
            return false;
        }

        // On cast l'objet en Arc
        Arc arc2 = (Arc) obj;

        // On compare la source et la destination des deux arcs
        // ainsi que leur valuation, si elles sont égales, les arcs sont égaux
        return Objects.equals(source, arc2.source) &&
                Objects.equals(destination, arc2.destination) &&
                valuation == arc2.valuation;
    }
}