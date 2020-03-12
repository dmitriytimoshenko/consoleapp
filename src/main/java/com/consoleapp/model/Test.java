package com.consoleapp.model;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class Test {
    @XmlTransient
    private int id;
    @XmlElement(name = "field")
    private int field;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test test = (Test) o;
        return id == test.id &&
                field == test.field;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, field);
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", field=" + field +
                '}';
    }
}
