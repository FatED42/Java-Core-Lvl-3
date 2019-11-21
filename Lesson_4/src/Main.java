public class Main {
    public static class Task1 {
        private final Object obj = new Object();
        private volatile String value = "A";

        Thread treadA = new Thread(() -> {
            synchronized (obj) {
                for (int i = 0; i <= 4; i++) {
                    while (!value.equals("A")) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("A");
                    value = "B";
                    obj.notifyAll();
                }
            }
        });

        Thread treadB = new Thread(() -> {
            synchronized (obj) {
                for (int i = 0; i <= 4; i++) {
                    while (!value.equals("B")) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("B");
                    value = "C";
                    obj.notifyAll();
                }
            }
        });

        Thread treadC = new Thread(() -> {
            synchronized (obj) {
                for (int i = 0; i <= 4; i++) {
                    while (!value.equals("C")) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("C");
                    value = "A";
                    obj.notifyAll();
                }
            }
        });
    }

    static class Test1 {
        public static void main(String[] args) {
            Task1 test = new Task1();
            test.treadA.start();
            test.treadB.start();
            test.treadC.start();
        }
    }
}
