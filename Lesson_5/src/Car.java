import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private CyclicBarrier training;
    private CyclicBarrier finish;
    private int place = 0;
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;

    int getPlace() {
        return place;
    }
    void setPlace(int place) {
        this.place = place;
    }
    String getName() {
        return name;
    }
    int getSpeed() {
        return speed;
    }

    Car(CyclicBarrier training, Race race, int speed, CyclicBarrier finish) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.training = training;
        this.finish = finish;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            training.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
            if (i == race.getStages().size() - 1) {
                race.getStages().get(i).finish(this, finish);
            }
        }
    }
}
