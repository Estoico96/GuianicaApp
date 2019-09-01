package com.ninjabyte.guianica.model;

public class Connection {
    private boolean connection;
    private boolean wifi;

    public Connection(boolean connection, boolean type) {
        this.connection = connection;
        this.wifi = type;
    }

    public boolean isConnection() {
        return connection;
    }

    public boolean isWifi() {
        return wifi;
    }
}
