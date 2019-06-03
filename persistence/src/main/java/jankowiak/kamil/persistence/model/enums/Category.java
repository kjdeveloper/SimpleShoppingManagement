package jankowiak.kamil.persistence.model.enums;

public enum Category {
    A("A"), B("B"), C("C"), D("D");

    private String name;

    Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
