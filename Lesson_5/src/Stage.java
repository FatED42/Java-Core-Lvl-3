import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Stage {
    int length;
    String description;
    private AtomicInteger finishing = new AtomicInteger(0);
    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);

    public void finish(Car c, CyclicBarrier cb) {
        String winner = null;
        int position = finishing.incrementAndGet();
        c.setPlace(position);
        if (position == 1) {
            winner = "Победил: " + c.getName();
        }
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        if (winner != null) {
            System.out.println(winner);
        }
    }
}
