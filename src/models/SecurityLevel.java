package models;

public enum SecurityLevel {

    UNCLASSIFIED("Unclassifed", 1),
    CONFIDENTIAL("Confidential", 2),
    SECRET("Secret", 3),
    TOP_SECRET("Top Secret", 4);

    private final String levelName;
    private final int clearanceLevel;
    
    private SecurityLevel(String levelName, int clearanceLevel) {
        this.levelName = levelName;
        this.clearanceLevel = clearanceLevel;
    }

    public String getLevelName() {
        return this.levelName;
    }

    public int getClearanceLevel() {
        return this.clearanceLevel;
    }
}