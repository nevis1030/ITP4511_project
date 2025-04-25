/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author local_user
 */
public class EmployeeBean {

    private String region, location, name, role;

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getRole() {
        return role;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("Region: %s, Locatino: %s, Name: %s, Role: %s",
                region, location, name, role);
    }

}
