import java.io.*;
import java.util.*;

class RoomInventory12 {

    private Map<String, Integer> availability;

    public RoomInventory12() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public Map<String, Integer> getAvailabilityMap() {
        return availability;
    }

    public void setAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

class FilePersistenceService {

    public void saveInventory(
            RoomInventory12 inventory,
            String filePath
    ) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry :
                    inventory.getAvailabilityMap().entrySet()) {

                writer.write(
                        entry.getKey() + "=" + entry.getValue()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(
            RoomInventory12 inventory,
            String filePath
    ) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println(
                    "No valid inventory data found. Starting fresh."
            );
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                String roomType = parts[0];
                int count = Integer.parseInt(parts[1]);

                inventory.setAvailability(roomType, count);
            }

        } catch (IOException e) {
            System.out.println("Error loading inventory.");
        }
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory12 inventory = new RoomInventory12();
        FilePersistenceService persistenceService =
                new FilePersistenceService();

        persistenceService.loadInventory(inventory, filePath);

        System.out.println();
        System.out.println("Current Inventory:");
        System.out.println(
                "Single: " + inventory.getAvailability("Single")
        );
        System.out.println(
                "Double: " + inventory.getAvailability("Double")
        );
        System.out.println(
                "Suite: " + inventory.getAvailability("Suite")
        );

        persistenceService.saveInventory(inventory, filePath);
        System.out.println("Inventory saved successfully.");
    }
}