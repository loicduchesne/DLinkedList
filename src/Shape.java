public class Shape {
    private String shapeName;
    // Size used for sorting algorithms to determine sorting order.
    private int size;

    public Shape(String shapeName, int size) {
        this.shapeName = shapeName;
        this.size = size;
    }

    public String getShapeName() {
        return this.shapeName;
    }

    public int getSize() {
        return this.size;
    }
}
