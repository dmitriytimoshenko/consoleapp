package com.consoleapp.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fields {

    @XmlElement(name="entry")
    private List<Test> Tests;

    public List<Test> getTests() {
        return Tests;
    }

    public void setTests(List<Test> tests) {
        Tests = tests;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "Tests=" + Tests +
                '}';
    }
}
