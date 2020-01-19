package com.traped.subwayy;

public class drivers {

    String driver_id;
    String name ;

    public drivers()
    {

    }

    public drivers(String driver_id, String name) {
        this.driver_id = driver_id;
        this.name = name;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getName() {
        return name;
    }
}
