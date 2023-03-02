public class Coordinates {
    private long x; //Значение поля должно быть больше -305, Поле не может быть null
    private double y; //Значение поля должно быть больше -393

    public Coordinates(long x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
