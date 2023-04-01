package graphe;

import java.util.Objects;

public class Arc {
    private final String source;
    private final String destination;
    private final int valuation;
    
    public Arc(String source, String destination, int valuation) {
        this.source = source;
        this.destination = destination;
        this.valuation = valuation;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public int getValuation() {
        return valuation;
    }
    
    @Override
    public String toString() {
        return source + "-" + destination + "(" + valuation + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        /*CF SI UTILE DE LE METTRE
         * if (obj == this) {
            return true;
        }*/

        if (!(obj instanceof Arc) || obj==null) {
            return false;
        }

        Arc arc2 = (Arc) obj;

        return Objects.equals(source, arc2.source)&&
                this.getDestination().equals(arc2.getDestination()) &&
                this.getValuation() == arc2.getValuation();
    }

}

