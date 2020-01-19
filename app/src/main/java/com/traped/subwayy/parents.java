package com.traped.subwayy;

public class parents {

    String parent_id;
    String email;
    String name;
    String password;
    String created;

    public parents()
    {

    }

    public parents(String parent_id, String email, String name, String password, String created) {
        this.parent_id = parent_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.created = created;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getCreated() {
        return created;
    }
}
