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

    private String userId, region, location, name, role;

    public EmployeeBean(String userId, String region, String location, String name, String role) {
        this.userId = userId;
        this.region = region;
        this.location = location;
        this.name = name;
        this.role = role;
    }

    
    
    public String getUserId() {
        return userId;
    }

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

    public void setUserId(String userId) {
        this.userId = userId;
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
