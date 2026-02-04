package models;

public class MissionSpecModel extends BaseModel {

    public static final Field<String> TITLE = new Field<>("Title", String.class);
    public static final Field<String> CONTENT = new Field<>("Content", String.class);

    public MissionSpecModel(String title, SecurityLevel securityLevel) {
        super(securityLevel);
        this.objectApiName = "Mission Spec";
        this.fields.put(TITLE, title);
    }
    
    public String getTitle(User user) throws Exception {
        return get(TITLE, user);
    }

    public String getContent(User user) throws Exception {
        return get(CONTENT, user);
    }

    public void setTitle(String title, User user) throws Exception {
        set(TITLE, title, user);
        touch();
    }

    public void setContent(String content, User user) throws Exception {
        set(CONTENT, content, user);
        touch();
    }
}
