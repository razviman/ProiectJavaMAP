package a4.domain;

public class tort extends entity {
    protected String tip;

    public tort(String tip) {

        super();
        this.tip = tip;


    }

    public tort(int id1, String tip) {
        id = id1;
        this.tip = tip;


    }
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String toString() {
        return this.getId() + "," + tip;
    }
}
