public class Road  {
    private final String id;
    private final District source;
    private final District destination;
    private final int weight;

    public Road(String id, District source, District destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }
    public District getDestination() {
        return destination;
    }

    public District getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}