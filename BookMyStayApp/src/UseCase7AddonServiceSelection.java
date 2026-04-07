import java.util.*;

class AddonService {

    private String serviceName;

    private double cost;

    public AddonService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

class AddonServiceManager {

    private Map<String, List<AddonService>> servicesByReservation;

    public AddonServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, AddonService service) {
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        double total = 0.0;

        List<AddonService> services =
                servicesByReservation.getOrDefault(
                        reservationId,
                        Collections.emptyList()
                );

        for (AddonService service : services) {
            total += service.getCost();
        }

        return total;
    }
}

public class UseCase7AddonServiceSelection {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");

        String reservationId = "Single-1";

        AddonServiceManager serviceManager =
                new AddonServiceManager();

        AddonService breakfast =
                new AddonService("Breakfast", 500.0);
        AddonService spa =
                new AddonService("Spa", 1000.0);

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);

        double totalCost =
                serviceManager.calculateTotalServiceCost(
                        reservationId
                );

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
