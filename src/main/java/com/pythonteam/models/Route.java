package com.pythonteam.models;

import org.postgresql.geometric.PGpoint;

public class Route {
    private int idRoute;
    private PGpoint latLong;
    private int idEmployee;
    private int idCustomer;

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }

    public PGpoint getLatLong() {
        return latLong;
    }

    public void setLatLong(PGpoint latLong) {
        this.latLong = latLong;
    }
}
