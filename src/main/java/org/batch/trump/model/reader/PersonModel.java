package org.batch.trump.model.reader;

/**
 * Created by BOUGHABA on 20/03/2017.
 */
public class PersonModel {
    private String firstPerson;
    private String secondPerson;
    private String relationShip;
    private String source;

    public String getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(String firstPerson) {
        this.firstPerson = firstPerson;
    }

    public String getSecondPerson() {
        return secondPerson;
    }

    public void setSecondPerson(String secondPerson) {
        this.secondPerson = secondPerson;
    }

    public String getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(String relationShip) {
        this.relationShip = relationShip;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
