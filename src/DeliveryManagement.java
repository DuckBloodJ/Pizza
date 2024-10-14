import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryManagement {
    private List<Order> orders;
    private List<DeliveryPerson> deliveryPersons;

    public DeliveryManagement() {
        orders = new ArrayList<>();
        deliveryPersons = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addDeliveryPerson(DeliveryPerson deliveryPerson) {
        deliveryPersons.add(deliveryPerson);
    }

    public void assignDelivery(String postalCode) {
        List<Order> pendingOrders = getPendingOrdersForPostalCode(postalCode);

        // Check if orders can be grouped (within 3 minutes and <= 3 pizzas)
        List<Order> groupedOrders = groupOrders(pendingOrders);

        if (groupedOrders.size() > 0) {
            for (DeliveryPerson person : deliveryPersons) {
                if (person.canDeliver(postalCode)) {
                    person.assignDelivery();
                    for (Order order : groupedOrders) {
                        order.setStatus("out for delivery");
                    }
                    System.out.println("Delivery assigned to " + person.getName());
                    return;
                }
            }
            System.out.println("No available delivery person for postal code: " + postalCode);
        }
    }

    private List<Order> getPendingOrdersForPostalCode(String postalCode) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus().equals("being prepared") && order.getPostalCode().equals(postalCode)) {
                result.add(order);
            }
        }
        return result;
    }

    private List<Order> groupOrders(List<Order> pendingOrders) {
        List<Order> grouped = new ArrayList<>();
        LocalDateTime baseTime = null;
        int totalPizzas = 0;

        for (Order order : pendingOrders) {
            if (baseTime == null) {
                baseTime = order.getOrderTime();
            }

            // Check if the order is within 3 minutes and the total pizzas are <= 3
            if (order.getOrderTime().isBefore(baseTime.plusMinutes(3)) && (totalPizzas + order.getPizzaCount()) <= 3) {
                grouped.add(order);
                totalPizzas += order.getPizzaCount();
            }
        }

        return grouped;
    }
}