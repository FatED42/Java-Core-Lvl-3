import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            //task1();
            task2();
            //task3();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль.
    public static void task1() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream("test/1.txt"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int x;
        while((x = in.read()) != -1) {
            out.write(x);
        }

        byte[] bytes = out.toByteArray();
        System.out.println(Arrays.toString(bytes));

        in.close();
        out.close();
    }

    //2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт). Может пригодиться следующая конструкция: ArrayList<InputStream> al = new ArrayList<>(); ... Enumeration<InputStream> e = Collections.enumeration(al);
    public static void task2() throws IOException {
        ArrayList<InputStream> al = new ArrayList<>();
        al.add(new FileInputStream("test/2.txt"));
        al.add(new FileInputStream("test/3.txt"));
        al.add(new FileInputStream("test/4.txt"));
        al.add(new FileInputStream("test/5.txt"));
        al.add(new FileInputStream("test/6.txt"));

        BufferedInputStream in = new BufferedInputStream(new SequenceInputStream(Collections.enumeration(al)));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("test/out.txt"));

        int x;
        while ((x = in.read()) != -1) {
            out.write(x);
            System.out.print((char) x);
        }

        in.close();
        out.close();
    }

    //3. Читалка.
    public static void task3() throws IOException {
        final int page = 1800;
        RandomAccessFile raf = new RandomAccessFile("test/book.txt", "r");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер страницы: ");
        int selectedPage = scanner.nextInt() - 1;
        raf.seek(selectedPage * page);
        byte[] arr = new byte[1800];
        raf.read(arr);

        System.out.println(new String(arr));

        raf.close();
    }
}
