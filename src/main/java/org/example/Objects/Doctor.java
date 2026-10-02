package org.example.Objects;

public class Doctor extends User{
    private int docID;
    private String field;
    private int emrgphone;

    public Doctor(String name, String email, String password, String field, int phone, int emrgphone, int docID) {
        super(name, email, password, phone);
        this.field = field;
        this.emrgphone = emrgphone;
        this.docID = docID;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getEmrgphone() {
        return emrgphone;
    }

    public void setEmrgphone(int emrgphone) {
        this.emrgphone = emrgphone;
    }
}
