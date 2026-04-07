import java.util.*;

class Reservation6 {

    private String guestName;
    private String roomType;

    public Reservation6(String guestName, String roomType) {
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

class BookingRequestQueue6{

    private Queue<Reservation6> queue;

    public BookingRequestQueue6() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation6 reservation) {
        queue.offer(reservation);
    }

    public Reservation6 getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory6 {

    private Map<String, Integer> availability;

    public RoomInventory6() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

class RoomAllocationService {

    private Set<String> allocatedRoomIds;

    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation6 reservation, RoomInventory6 inventory) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println(
                    "No available rooms for Guest: " +
                            reservation.getGuestName() +
                            ", Room Type: " + roomType
            );
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);
        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

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

public class UseCase6RoomAllocation {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing");

        RoomInventory6 inventory = new RoomInventory6();

        BookingRequestQueue6 bookingQueue = new BookingRequestQueue6();

        bookingQueue.addRequest(new Reservation6("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation6("Subha", "Single"));
        bookingQueue.addRequest(new Reservation6("Vanmathi", "Suite"));

        RoomAllocationService allocationService =
                new RoomAllocationService();

        while (bookingQueue.hasPendingRequests()) {
            Reservation6 reservation = bookingQueue.getNextRequest();
            allocationService.allocateRoom(reservation, inventory);
        }
    }
}