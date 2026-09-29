package org.example.Objects;

public class Doctor extends User{
    private String field;

    public Doctor(int id, String name, String email, String password, String field) {
        super(id, name, email, password);
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
