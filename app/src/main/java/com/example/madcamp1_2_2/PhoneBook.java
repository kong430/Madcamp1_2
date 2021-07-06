package com.example.madcamp1_2_2;

public class PhoneBook implements Comparable<PhoneBook> {
    private String id;
    private String name;
    private String tel;

    public void setId(String _id) {
        id = _id;
    }
    public void setName(String _name) {
        name = _name;
    }
    public void setTel(String _tel) {
        tel = _tel;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTel() {
        return tel;
    }

    @Override
    public int compareTo(PhoneBook p) {
        return this.name.compareTo(p.getName());
    }
}

