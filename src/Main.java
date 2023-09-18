import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Elevator elevator = new Elevator(13);

        Runnable firstPassenger = new Passenger(elevator, "Adam Sandler");
        Thread thread1 = new Thread(firstPassenger);

        Runnable secondPassenger = new Passenger(elevator, "Lily Rubich");
        Thread thread2 = new Thread(secondPassenger);

        Runnable thirdPassenger = new Passenger(elevator, "John Doe");
        Thread thread3 = new Thread(thirdPassenger);

        thread1.start();
        thread2.start();
        thread3.start();

    }

}