import java.time.LocalDateTime;
import java.util.List;

public class DeliveryPerson extends Staff {
    private String assignedPostalCodes;  // Postal codes this person is assigned to
    private boolean isAvailable;
    private LocalDateTime nextAvailableTime;

    public DeliveryPerson(int id, String name, String position, String email, String phoneNumber, String address, String username, String password, String assignedPostalCodes) {
        super(id, name, position, email, phoneNumber, address, username, password);
        this.assignedPostalCodes = assignedPostalCodes;
        isAvailable = true;
        nextAvailableTime = LocalDateTime.now();
    }

    // Check if the delivery person can deliver to the specified postal code
    public boolean canDeliver(String postalCode) {
        updateAvailability();  // Check if the person is available now
        return isAvailable && assignedPostalCodes.contains(postalCode);
    }

    // Assign the delivery and make the person unavailable for 30 minutes
    public void assignDelivery() {
        this.isAvailable = false;
        this.nextAvailableTime = LocalDateTime.now().plusMinutes(30);
    }

    // Update availability after the 30-minute window
    public void updateAvailability() {
        if (LocalDateTime.now().isAfter(nextAvailableTime)) {
            this.isAvailable = true;
        }
    }

    // Getters and setters for postal codes and availability
    public String getAssignedPostalCodes() {
        return assignedPostalCodes;
    }

    public void setAssignedPostalCodes(String assignedPostalCodes) {
        this.assignedPostalCodes = assignedPostalCodes;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public LocalDateTime getNextAvailableTime() {
        return nextAvailableTime;
    }
    public String toString(){
        return "Name: " + super.getName() + ", Email: " + super.getEmail() + ", Phone Number: " + super.getPhoneNumber() + ", Assigned Postal Codes: " + assignedPostalCodes;
    }
}
