public class Student {
    private String cName;
    private String name;
    private String readingLevel;
    private String id;
    Student() {
        cName = "";
        name = "";
        readingLevel = "";
    }
    Student(String id) {
        this.id = id;
        cName = "";
        name = "";
        readingLevel = "";
    }
    Student(String id, String name) {
        this.id = id;
        this.name = name;
        cName = "";
        readingLevel = "";
    }
    Student(String id, String name, String cName) {
        this.id = id;
        this.name = name;
        this.cName = cName;
        readingLevel = "";
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setReadingLevel(String rLevel) {
        readingLevel = rLevel;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getcName() {
        return cName;
    }
    public String getReadingLevel() {
        return readingLevel;
    }
    public String getName() {
        return name;
    }
    public String getId() {return id;}
}
