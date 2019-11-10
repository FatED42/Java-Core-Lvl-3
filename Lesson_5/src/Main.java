import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        final int carsCount = 6;
        Semaphore smp = new Semaphore(carsCount/2);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(smp, 90), new Road(40));
        Car[] cars = new Car[carsCount];
        CyclicBarrier training = new CyclicBarrier(carsCount, new Start());
        CyclicBarrier finish = new CyclicBarrier(carsCount, new Finish());
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(training, race,20 + (int) (Math.random() * 10), finish);
        }
        for (Car car : cars) {
            new Thread(car).start();
        }
    }
}

