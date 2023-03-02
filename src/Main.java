import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static TreeMap<Integer, Dragon> dragons;
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        String[] command;
        dragons = new TreeMap();
//        dragons.put(1, new Dragon(1, "Dragon", new Coordinates(100, 100), 30, "Описание",
//                Color.BLACK, DragonCharacter.WISE, new Person("Киллер",
//                ZonedDateTime.now(ZoneId.of("America/Chicago")), 30)));

        while (true) {
            System.out.print("Введите команду: ");
            command = sc.nextLine().split(" ");
            System.out.println();

            if (command[0].equals("help")) {
                printCommands();
            } else if (command[0].equals("info")){
                System.out.println("Тип: Dragon");
                System.out.println("Количество элементов: " + dragons.size());
            } else if (command[0].equals("show")){
                for(Integer id: dragons.keySet()){
                    System.out.println("id = " + id + ", " + dragons.get(id));
                }
            } else if (command[0].equals("insert")){

                boolean check = false;
                int key;
                if (command.length == 2){
                    try {
                        key = Integer.parseInt(command[1]);
                        if (dragons.containsKey(key)) {
                            throw new Exception();
                        }
                        check = true;
                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println("Неверно введен ключ запроса");
                    } catch (Exception e){
                        System.out.println("Такой ключ уже существует");
                    }
                } else
                    System.out.println("Неверно указан запрос");

                if (check) {
                    Dragon dragon = getFileds();
                    dragons.put(dragons.size() + 1, dragon);
                    System.out.println("Элемент успешно добавлен");
                }

            } else if (command[0].equals("update")){
                boolean check = false;
                int key = -1;
                if (command.length == 2){
                    try {
                        key = Integer.parseInt(command[1]);
                        if (!dragons.containsKey(key)) {
                            throw new Exception();
                        }
                        check = true;
                    } catch (IllegalArgumentException illegalArgumentException) {
                        System.out.println("Неверно введен ключ запроса");
                    } catch (Exception e){
                        System.out.println("Такой ключ не найден");
                    }
                } else
                    System.out.println("Неверно указан запрос");

                if (check){
                    Dragon dragon = getFileds();
                    dragons.replace(key, dragon);
                    System.out.println("Элемент успешно обновлен");
                }

            } else if (command[0].equals("remove_key")){
                if (command.length == 2) {
                    try {
                        int key = Integer.parseInt(command[1]);
                        if (dragons.containsKey(key)) {
                            dragons.remove(key);
                        } else
                            throw new Exception();
                        System.out.println("Элемент успешно удален");
                    } catch (Exception e) {
                        System.out.println("Элемент с таким ключом не найден");
                    }
                } else
                    System.out.println("Неверный запрос");

            } else if (command[0].equals("clear")){
                dragons.clear();
                System.out.println("Коллекция успешно очищена");
            } else if (command[0].equals("save")){
                Gson gson = new Gson();
                String json = gson.toJson(dragons);
                try(FileOutputStream out=new FileOutputStream("data.json");
                    BufferedOutputStream bos = new BufferedOutputStream(out))
                {
                    byte[] buffer = json.getBytes();
                    bos.write(buffer, 0, buffer.length);
                    System.out.println("Сохранение прошло успешно");
                } catch(IOException ex){
                    System.out.println("Произошла ошибка при сохранении в файл");
                }
            } else if (command[0].equals("execute_script")){

            } else if (command[0].equals("exit")){
                System.out.println("До свидания!");
                continue;
            } else if (command[0].equals("remove_lower")){

            } else if (command[0].equals("remove_greater_key")){

            } else if (command[0].equals("remove_lower_key")){

            } else if (command[0].equals("count_lest_than_color")){

            } else if (command[0].equals("filter_lest_than_color")){

            } else if (command[0].equals("print_ascending")){
                for (int key: dragons.keySet()) {
                    System.out.println(dragons.get(key));
                }
            } else {
                System.out.println("Команда не опознана");
            }
            System.out.println();
        }
    }

    public static void printCommands(){
        System.out.println("help : вывести справку по доступным командам \n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) \n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении \n" +
                "insert null {element} : добавить новый элемент с заданным ключом \n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному \n" +
                "remove_key null : удалить элемент из коллекции по его ключу \n" +
                "clear : очистить коллекцию \n" +
                "save : сохранить коллекцию в файл \n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме. \n" +
                "exit : завершить программу (без сохранения в файл) \n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный \n" +
                "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный \n" +
                "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный \n" +
                "count_less_than_color color : вывести количество элементов, значение поля color которых меньше заданного \n" +
                "filter_less_than_color color : вывести элементы, значение поля color которых меньше заданного \n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания");
    }

    public static Dragon getFileds(){
        String name;
        while (true) {
            try {
                System.out.print("Введите имя: ");
                name = sc.nextLine();
                if (name.length() == 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        System.out.println(name);
        System.out.print("Введите координаты.");
        int x;
        while (true) {
            System.out.println("Введите координату X (Значение поля должно быть больше -305)");
            try {
                x = sc.nextInt();
                if (x <= -305) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        int y;
        while (true) {
            System.out.print("Введите координату Y (Значение поля должно быть больше -393)");
            try {
                y = sc.nextInt();
                if (y <= -393) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        int age;
        while (true) {
            System.out.print("Введите возраст: ");
            try {
                age = sc.nextInt();
                if (age <= 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        sc.nextLine();
        System.out.print("Введите описание: ");
        String description = sc.nextLine();

        Color color;
        while (true) {
            System.out.print("Введите один из цветов BLACK, BLUE, BROWN: ");
            try {
                String color2 = sc.nextLine();
                if (color2.length() == 0) {
                    color = null;
                    break;
                }
                switch (color2) {
                    case "BLACK":
                        color = Color.BLACK;
                        break;
                    case "BLUE":
                        color = Color.BLUE;
                        break;
                    case "BROWN":
                        color = Color.BROWN;
                        break;
                    default:
                        throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        DragonCharacter character;
        while (true) {
            System.out.print("Введите один из характеров CUNNING, WISE, BROWN, EVIL, CHAOTIC_EVIL, FICKLE: ");
            try {
                String character2 = sc.nextLine();
                switch (character2) {
                    case "CUNNING":
                        character = DragonCharacter.CUNNING;
                        break;
                    case "WISE":
                        character = DragonCharacter.WISE;
                        break;
                    case "EVIL":
                        character = DragonCharacter.EVIL;
                        break;
                    case "CHAOTIC_EVIL":
                        character = DragonCharacter.CHAOTIC_EVIL;
                        break;
                    case "FICKLE":
                        character = DragonCharacter.FICKLE;
                        break;
                    default:
                        throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        String namePerson;
        while (true) {
            try {
                System.out.print("Введите имя персоны: ");
                namePerson = sc.nextLine();
                if (namePerson.length() == 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        int weight;
        while (true) {
            System.out.print("Введите вес: ");
            try {
                weight = sc.nextInt();
                if (weight <= 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Некорректное значение. Попробуйте снова.");
            }
        }

        return new Dragon(dragons.size() + 1, name, new Coordinates(x, y), age, description,
                color, character, new Person(namePerson,
                ZonedDateTime.now(ZoneId.of("America/Chicago")), weight));
    }
}