import java.util.*;

/**
 * ============================================================
 * CLASS – Reservation
 * ============================================================
 *
 * Represents a booking request made by a guest.
 *
 * @version 11.0
 */
class Reservation11 {

    private String guestName;
    private String roomType;

    public Reservation11(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * ============================================================
 * CLASS – BookingRequestQueue
 * ============================================================
 *
 * Thread-safe booking request queue (FIFO).
 *
 * @version 11.0
 */
class BookingRequestQueue11 {

    private Queue<Reservation11> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation11 reservation) {
        queue.offer(reservation);
    }

    public synchronized Reservation11 getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * ============================================================
 * CLASS – RoomInventory
 * ============================================================
 *
 * Shared room inventory accessed by multiple threads.
 *
 * @version 11.0
 */
class RoomInventory11 {

    private Map<String, Integer> availability;

    public RoomInventory11() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }

    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
}

/**
 * ============================================================
 * CLASS – RoomAllocationService
 * ============================================================
 *
 * Performs synchronized room allocation.
 *
 * @version 11.0
 */
class RoomAllocationService11 {

    private Set<String> allocatedRoomIds = new HashSet<>();

    public void allocateRoom(Reservation11 reservation, RoomInventory11 inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);
        inventory.decrementAvailability(roomType);

        System.out.println(
                "Booking confirmed for Guest: " +
                        reservation.getGuestName() +
                        ", Room ID: " + roomId
        );
    }

    private String generateRoomId(String roomType) {
        int counter = 1;
        String roomId;

        do {
            roomId = roomType + "-" + counter;
            counter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}

/**
 * ============================================================
 * CLASS – ConcurrentBookingProcessor
 * ============================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Represents a booking processor that
 * can be executed by multiple threads.
 *
 * Shared resources are accessed safely
 * using synchronization.
 *
 * @version 11.0
 */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue11 bookingQueue;
    private RoomInventory11 inventory;
    private RoomAllocationService11 allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue11 bookingQueue,
            RoomInventory11 inventory,
            RoomAllocationService11 allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {
            Reservation11 reservation;

            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

/**
 * ============================================================
 * MAIN CLASS – UseCase11ConcurrentBookingSimulation
 * ============================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * Simulates multiple users attempting
 * to book rooms at the same time.
 *
 * Demonstrates how synchronization
 * prevents race conditions.
 *
 * @version 11.0
 */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        // Shared components
        BookingRequestQueue11 bookingQueue = new BookingRequestQueue11();
        RoomInventory11 inventory = new RoomInventory11();
        RoomAllocationService11 allocationService =
                new RoomAllocationService11();

        // Add booking requests
        bookingQueue.addRequest(new Reservation11("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation11("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation11("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation11("Subha", "Single"));

        // Create booking processor threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService
                )
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Display remaining inventory
        System.out.println();
        System.out.println("Remaining Inventory:");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));
    }
}