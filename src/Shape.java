public class Shape {
    private String shapeName;

    public Shape(String shapeName) {
        this.shapeName = shapeName;
    }

    public String getShapeName() {
        return this.shapeName;
    }

    public void changeShapeName(String newName) {
        this.shapeName = newName;
    }
}
