public class Shape implements WeightedObject<Shape> {
    private final String shapeName;
    // Size used for sorting algorithms to determine sorting order.
    private final int size;

    private final int weight;

    private static final String[] shapeList = {"square", "circle", "triangle", "rectangle", "oval", "diamond", "parallelepiped"};

    public Shape(String shapeName, int size) {
        int natOrd = -1;

        for (int i = 0; i < shapeList.length; i++) {
            if (shapeList[i].equals(shapeName)) {
                natOrd = i+1;
                break;
            }
        }
        if (natOrd == -1) {
            throw new IllegalArgumentException("Shape must be contained within the static shape list.");
        }
        this.shapeName = shapeName;
        this.size = size;
        this.weight = size*natOrd;
    }

    public String getShapeName() {return this.shapeName;}

    public int getSize() {return this.size;}

    public int getWeight() {return this.weight;}

    @Override
    public boolean equals(Object object) {
        // Edge case can compare two null objects.
        if (object == null) {
            return false;
        } else if (!(object instanceof Shape)) {
            throw new IllegalArgumentException("Object to compare must be of type Shape");
        }
        if (this.getShapeName().equals(((Shape) object).getShapeName())) {
            return this.getSize() == ((Shape) object).getSize();
        }
        return false;
    }

    public String toString() {
        return getShapeName() + " | size = " + getSize() + " | weight = " + getWeight();
    }
}
