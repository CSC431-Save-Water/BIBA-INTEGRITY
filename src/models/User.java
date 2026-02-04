package models;

import java.util.UUID;

public class User {
    final private String firstName;
    final private String lastName;

    final private UUID userId;

    final private SecurityLevel securityLevel;

    public User(String firstName, String lastName, SecurityLevel securityLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityLevel = securityLevel; 
        this.userId = UUID.randomUUID();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public UUID getUserUUID() {
        return this.userId;
    }

    public SecurityLevel getUserSecurityLevel() {
        return this.securityLevel;
    }
}
