
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import models.BaseModel;
import models.MissionSpecModel;
import models.SecurityLevel;
import models.User;
import models.WeaponsSpecModel;

public class Main {

    // This map contains all objects linked with their UUIDs
    private final static Map<UUID, BaseModel> objectMap = new HashMap<>();

    // This map contains all users linked with their UUIDS
    private final static Map<UUID, User> userMap = new HashMap<>();

    // This set contains the current avalible fields for operations
    private static Set<BaseModel.Field<?>> avalibleFields;

    // The following get___FromString functions are defined in Main due to a dependency on
    // userMap && objectMap
    private static User getUserFromString(String userId) {
        try {
            UUID id = UUID.fromString(userId);
            return userMap.get(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static BaseModel getBaseModelFromString(String baseModelId) {
        try {
            UUID id = UUID.fromString(baseModelId);
            return objectMap.get(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static BaseModel.Field<?> getFieldFromString(String targetField) {
        for (BaseModel.Field<?> f : avalibleFields) {
            if (f.name().equals(targetField)) return f;
        }

        return null;
    }

    public static void main(String[] args) {

        // Initialize Users for Simulation
        User valentine = new User("Valentine", "Davis", SecurityLevel.UNCLASSIFIED);
        User robby = new User("Robert", "Yestur", SecurityLevel.CONFIDENTIAL);
        User brandon = new User("Brandon", "Lee", SecurityLevel.SECRET);
        User pauline = new User("Pauline", "Pugh", SecurityLevel.TOP_SECRET);

        // Add Users to userMap
        userMap.put(valentine.getUserUUID(), valentine);
        userMap.put(robby.getUserUUID(), robby);
        userMap.put(brandon.getUserUUID(), brandon);
        userMap.put(pauline.getUserUUID(), pauline);

        // Initialize Objects for Simulation
        MissionSpecModel ash = new MissionSpecModel("Ash", SecurityLevel.TOP_SECRET);
        MissionSpecModel woods = new MissionSpecModel("Woods", SecurityLevel.CONFIDENTIAL);

        WeaponsSpecModel miracle = new WeaponsSpecModel("Miracle", SecurityLevel.SECRET);
        WeaponsSpecModel bombastic = new WeaponsSpecModel("Bombastic Side Eye", SecurityLevel.UNCLASSIFIED);

        // Add Objects to objectMap
        objectMap.put(ash.getId(), ash);
        objectMap.put(woods.getId(), woods);
        objectMap.put(miracle.getId(), miracle);
        objectMap.put(bombastic.getId(), bombastic);
        
        // List out all current user/objects
        System.out.println("Users: ");
        for (User u : userMap.values()) {
            System.out.println(u.getFullName() + "\t" + u.getUserUUID() + "\t" + u.getUserSecurityLevel().getLevelName());
        }

        System.out.println("\n\nObjects: ");
        for (BaseModel obj : objectMap.values()) {
            System.out.println(obj.getObjectApiName() + "\t" + obj.getId() + "\t" + obj.getObjectSecurityLevel().getLevelName());
        }

        Scanner userInput = new Scanner(System.in);

        while(true) {
            // Gather simulated User
            System.out.print("\nEnter the User Id you wish to simulate as: ");
            String enteredUserId = userInput.nextLine().strip();

            User user = getUserFromString(enteredUserId);

            while(user == null) {
                System.out.print("User does not exists (" + enteredUserId + ") please try again: ");
                enteredUserId = userInput.nextLine().strip();
                user = getUserFromString(enteredUserId);
            }

            // Gather simulated object
            System.out.print("\nEnter the Object Id you wish to touch: ");
            String enteredObjectId = userInput.nextLine();

            BaseModel model = getBaseModelFromString(enteredObjectId);

            while(model == null) {
                System.out.print("Model does not exists (" + enteredObjectId + ") please try again: ");
                enteredObjectId = userInput.nextLine().strip();
                model = getBaseModelFromString(enteredObjectId);
            }

            // Gather simulated action
            System.out.print("\nEnter the operation you wish to preform (READ, WRITE): ");
            String readOrWrite = userInput.nextLine().toUpperCase();

            while(!(readOrWrite.equals("READ") || readOrWrite.equals("WRITE"))) {
                System.out.print("Invalid operation (" + readOrWrite +") please try again: ");
                readOrWrite = userInput.nextLine().toUpperCase();
            }

            // Sets the global avalible fields set (DO NOT REMOVE)
            avalibleFields = model.getAvalibleFields();
            
            System.out.println("\n" + model.getObjectApiName() + ": ");

            for (BaseModel.Field<?> f : avalibleFields) {
                System.out.println(f);
            }

            // Gather simulated field
            System.out.print("\nEnter the field you wish to preform the operation on (case-sensitive): ");
            String enteredTargetField = userInput.nextLine().strip();

            BaseModel.Field<?> targetField = getFieldFromString(enteredTargetField);

            while(targetField == null) {
                System.out.print("Invalid field (" + enteredTargetField + ") please try again: ");
                enteredTargetField = userInput.nextLine().strip();
                targetField = getFieldFromString(enteredTargetField);
            }

            if (readOrWrite.equals("READ")) {
                // READ operation
                try {
                    System.out.println("\n" + model.get(targetField, user));
                } catch (Exception e) {
                    System.out.println("\n" + e);
                }
            } else {    
                System.out.print("Enter Value: ");
                String userWriteValue = userInput.nextLine();

                try {
                    model.set((BaseModel.Field<Object>) targetField, userWriteValue, user);
                    System.out.println("Write Successful.");
                } catch (ClassCastException e) {
                    System.out.println("Error: Type mismatch for field " + targetField.name());
                } catch (Exception e) {
                    System.out.println("\n" + e);
                }
            }
        } 
    }
}
