package org.dimitrovchi.maptest.stat;

/**
 * @author Dmitry Ovchinnikov
 */
public class StatObject {

    private final int id;

    public StatObject(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == getClass() && ((StatObject) obj).id == id;
    }
}
