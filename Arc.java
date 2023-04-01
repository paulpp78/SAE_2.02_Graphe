package graphe;

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
    
    public boolean equals(Arc b) {
    	return this.getSource()== b.getSource() && this.getDestination()== b.getDestination() &&
    			this.getValuation()== b.getValuation();
    }
}

