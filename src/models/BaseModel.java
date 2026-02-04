package models;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public abstract class BaseModel {

    // Field is a static sub-class for the Base Model class which provides Class Level Field Declarations
    // These field objects are then placed in the fields map.
    // The primary idea here is to create a dynamic map, that can safely cast back into it's proper type.
    static final class Field<T> {
        private final String name;
        private final Class<T> type;

        public Field(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }

        public String name() {
            return name;
        }

        T cast(Object value) {
            return type.cast(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            
            // Is the object a field object
            if(!(o instanceof Field<?>)) return false;
            
            // If so cast the object, and compare vaules
            Field<?> other = (Field<?>) o;
            return name.equals(other.name) && type.equals(other.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, type);
        }
    }

    protected String objectApiName = "Base Model";
    protected final UUID id;
    protected final SecurityLevel securityLevel;
    protected final Instant createdAt;
    protected Instant lastModifedAt;

    protected Map<Field<?>, Object> fields = new HashMap<>();

    protected BaseModel(SecurityLevel securityLevel) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.lastModifedAt = this.createdAt;
        this.securityLevel = securityLevel;
    }

    public UUID getId() {
        return this.id;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getLastModfiedAt() {
        return this.lastModifedAt;
    }

    protected void touch() {
        this.lastModifedAt = Instant.now();
    }

    protected <T> void set(Field<T> field, T value, User currentUser) throws Exception {
        if (hasWriteAcess(currentUser)) {
            fields.put(field, value);
        } else {
            throw new Exception("Current User: " + currentUser.getFullName() + " does not have access to object: " + this.getId());
        }
    }

    protected <T> T get(Field<T> field, User currentUser) throws Exception {
        // Since the Map stores all values as objects, they must be
        // casted before returning the value.
        if (hasReadAcess(currentUser)) {
            return field.cast(fields.get(field));
        } else {
            throw new Exception("Current User: " + currentUser.getFullName() + " does not have access to object: " + this.getId());
        }
    }

    private boolean hasReadAcess(User currentUser) {
        return currentUser.getUserSecurityLevel().getClearanceLevel() >= this.securityLevel.getClearanceLevel();
    }

    private boolean hasWriteAcess(User currentUser) {
        return currentUser.getUserSecurityLevel().getClearanceLevel() <= this.securityLevel.getClearanceLevel();
    }
}