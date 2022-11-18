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

    /**
     * Overrides the .equals method in Java Objects. Compare if a Shape is equal to another Shape (in terms of size and shape name).
     * @param obj Object to compare with this Shape.
     * @return Return false if objects are not equal, return true if they are.
     */
    @Override
    public boolean equals(Object obj) {
        // Edge case can compare two null objects.
        if (obj == null) {
            return false;
        } else if (!(obj instanceof Shape)) {
            throw new IllegalArgumentException("Object to compare must be of type Shape");
        }
        if (this.getShapeName().equals(((Shape) obj).getShapeName())) {
            return this.getSize() == ((Shape) obj).getSize();
        }
        return false;
    }
}
