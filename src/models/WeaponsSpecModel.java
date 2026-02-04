package models;

public class WeaponsSpecModel extends BaseModel {

    public static final Field<String> NAME = new Field<>("Name", String.class);
    public static final Field<String> DESCRIPTION = new Field<>("Description", String.class);
    public static final Field<String> CLASSIFICATION = new Field<>("Classification", String.class);

    public WeaponsSpecModel(String name, SecurityLevel securityLevel) {
        super(securityLevel);
        this.objectApiName = "Weapon Spec";
        this.fields.put(NAME, name);
    }

    public String getName(User user) throws Exception {
        return get(NAME, user);
    }

    public String getDescription(User user) throws Exception {
        return get(DESCRIPTION, user);
    }

    public String getClassification(User user) throws Exception {
        return get(CLASSIFICATION, user);
    }


    public void setName(String name, User user) throws Exception {
        set(NAME, name, user);
        touch();
    }

    public void setDescription(String description, User user) throws Exception {
        set(DESCRIPTION, description, user);
        touch();
    }

    public void setClassification(String classification, User user) throws Exception {
        set(CLASSIFICATION, classification, user);
        touch();
    }
}