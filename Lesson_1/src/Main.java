import java.util.*;

public class Main {

    public static void main(String[] args) {
        Object[] arr1 = new Object[15];
        swap(arr1, 2, 11);

        String[] conv1 = new String[] {"2", "66", "G", "a"};
        Integer[] conv2 = new Integer[] {1, 44, 123, 4, 68, 94};
        convert(conv1);
        convert(conv2);

        Box<Apple> aBox = new Box<>();
        for (int i = 0; i < 3; i++) {
            aBox.addFruit(new Apple());
        }

        Box<Orange> oBox1 = new Box<>(
                new Orange(),
                new Orange()

        );
        Box<Orange> oBox2 = new Box<>(
                new Orange(),
                new Orange(),
                new Orange()

        );

        System.out.println();
        System.out.println("Вес коробки с яблоками: " + aBox.getWeight());
        System.out.println("Вес 1 коробки с апельсинами: " + oBox1.getWeight());
        System.out.println("Вес 2 коробки с апельсинами: " + oBox2.getWeight());

        System.out.println();
        System.out.println("Сравниваем вес 1 коробки с апельсинами со 2 кробкой: " + oBox1.compare(oBox2));
        System.out.println("Сравниваем вес коробки яблок с 1 коробкой апельсинов: " + aBox.compare(oBox1));
        System.out.println("Сравниваем вес коробки яблок со 2 коробкой апельсинов: " + aBox.compare(oBox2));
        System.out.println();

        System.out.println("Перемещаем апельсины из 1 коробки во 2.");
        oBox1.removeTo(oBox2);
        System.out.println("Вес 1 коробки после перемещения: " + oBox1.getWeight());
        System.out.println("Вес 2 коробки после перемещения: " + oBox2.getWeight());
        System.out.println();

        List<SomeClass> list = new LinkedList<>();

        list.add(new SomeClass(1, "Test1"));
        list.add(new SomeClass(2, "Test1"));
        list.add(new SomeClass(3, "Test1"));
        list.add(new SomeClass(4, "Test2"));
        list.add(new SomeClass(5, "Test2"));
        list.add(new SomeClass(6, "Test3"));
        list.add(new SomeClass(7, "Test3"));
        list.add(new SomeClass(8, "Test4"));

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        Map<String, List<Integer>> map = new HashMap<>();
        list.forEach(item -> map.computeIfAbsent(item.name, k -> new ArrayList<>()).add(item.id));
        System.out.println(map);
    }

    private static <T> void convert (T[] conv) {
        ArrayList<T> convArray = new ArrayList<T>(Arrays.asList(conv));
        System.out.println(convArray);
    }

    private static void swap(Object[] arr, int a, int b) {
        Object temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

}
