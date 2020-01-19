package com.traped.subwayy;

public class schools {

    String school_id;
    String name ;
    String phone;
    String address;
    String latitude;
    String longitude;

    public void schools()
    {}

    public schools(String school_id, String name, String phone, String address, String latitude, String longitude) {
        this.school_id = school_id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSchool_id() {
        return school_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
