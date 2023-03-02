import java.time.ZonedDateTime;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле может быть null
    private double weight; //Поле не может быть null, Значение поля должно быть больше 0

    public Person(String name, ZonedDateTime birthday, double weight) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", weight=" + weight +
                '}';
    }
}
