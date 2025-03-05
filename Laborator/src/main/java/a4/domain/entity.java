package a4.domain;

import java.io.Serializable;

public abstract class entity implements Serializable {
    int id;

    public entity() {
        ID_generator id_nou = new ID_generator();
        id = id_nou.get_id();

    }

    public int getId() {
        return id;
    }
}
