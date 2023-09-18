public class Passenger implements Runnable {

    private final Elevator elevator;
    private int passengerStartFloorNumber;
    private int passengerEndFloorNumber;
    private String name = "";

    public Passenger(Elevator elevator, String name) {
        this.elevator = elevator;
        this.name = name;
    }

    @Override
    public void run() {

        passengerStartFloorNumber = (int) (Math.random() * Elevator.NUMBER_OF_FLOORS) + 1;
        passengerEndFloorNumber = (int) (Math.random() * Elevator.NUMBER_OF_FLOORS) + 1;

        try {
            elevator.callElevator(this, passengerStartFloorNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        elevator.takeThePassenger(this);
        elevator.selectFloorToMoveOn(this, passengerEndFloorNumber);
        elevator.disembarkThePassenger(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
