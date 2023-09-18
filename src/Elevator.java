import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator {

    public static int NUMBER_OF_FLOORS = 0; //общее количество этажей в доме
    private static int currentFloorNumber = 5; //текущий номер этажа, на котором находится лифт
    private Lock movingUpLock = new ReentrantLock();
    private Lock movingDownLock = new ReentrantLock();
    private Lock movingLock = new ReentrantLock();


    public Elevator(int count) {
        NUMBER_OF_FLOORS = count;
    }

    public int callElevator(Passenger passenger, int floorNumber) throws InterruptedException {

        System.out.println("Passenger = " + passenger.toString() + " called the elevator from the floor # " + floorNumber);
        return move(floorNumber);
    }

    public int selectFloorToMoveOn(Passenger passenger, int floorNumber) {

        System.out.println("Passenger = " + passenger.toString() + " selected the floor # " + floorNumber + " to move on");
        return move(floorNumber);
    }

    private int move(int floorNumber) {

        //движение вниз
        if (currentFloorNumber > floorNumber) {
            while (!movingUpLock.tryLock()) {
                if (currentFloorNumber == floorNumber) {
                    open(floorNumber);
                    return currentFloorNumber;
                }
            }
            try {
                moveDown(floorNumber);
                open(floorNumber);
            } finally {
                movingUpLock.unlock();
            }

            //движение вверх
        } else {
            if (currentFloorNumber <= floorNumber) {
                while (!movingDownLock.tryLock()) {
                    if (currentFloorNumber == floorNumber) {
                        open(floorNumber);
                        return currentFloorNumber;
                    }
                }
                try {
                    moveUp(floorNumber);
                    open(floorNumber);
                } finally {
                    movingDownLock.unlock();
                }
            }
        }
        return currentFloorNumber = floorNumber;
    }

    private int moveUp(int floorNumber) {
        movingLock.lock();
        try {
            if (currentFloorNumber == floorNumber) ;
            else
                while (currentFloorNumber < floorNumber) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("The elevator on the " + "floor # " + ++currentFloorNumber);
                }
        } finally {
            movingLock.unlock();
        }
        return currentFloorNumber;
    }

    private int moveDown(int floorNumber) {
        movingLock.lock();
        try {
            while (currentFloorNumber > floorNumber) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("The elevator on the " + "floor # " + --currentFloorNumber);
            }
        } finally {
            movingLock.unlock();
        }

        return currentFloorNumber;
    }

    private void open(int floor) {
        System.out.println("The elevator opened on the floor # " + currentFloorNumber);

    }

    public void takeThePassenger(Passenger passenger) {
        System.out.println("Passenger = " + passenger.toString() + " entered the elevator on the floor # " + currentFloorNumber);
    }

    public void disembarkThePassenger(Passenger passenger) {
        System.out.println("Passenger = " + passenger.toString() + " left the elevator on the floor # " + currentFloorNumber);
    }
}
