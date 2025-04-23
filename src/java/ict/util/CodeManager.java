/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.util;

/**
 *
 * @author local_user
 */
public class CodeManager {

    private String[] roles = {"Shop Staff", "Warehouse Staff", "Senior Manager"};
    private String[] status = {"Ordered", "Approved", "Denied", "Completed"};
    private String[] seasons = {"Spring", "Summer", "Fall", "Winter"};

    public String getRole(int code) {
        String role = null;
        switch (code) {
            case 0:
                role = roles[0];
                break;
            case 1:
                role = roles[1];
                break;
            case 2:
                role = roles[2];
                break;
            default:
                break;
        }
        return role;
    }
    public int toDbRole(String role){
        int code = 3;
        switch (role) {
            case "shopStaff":
                code = 0;
                break;
            case "warehouseStaff":
                code = 1;
                break;
            case "seniorManager":
                code = 2;
                break;
            default:
                break;
        }
        return code;
    }
    
    public String getStatus(int code) {
        String state = null;
        switch (code) {
            case 0:
                state = status[0];
                break;
            case 1:
                state = status[1];
                break;
            case 2:
                state = status[2];
                break;
            case 3:
                state = status[3];
                break;
            default:
                break;
        }
        return state;
    }
    public int toDbStatus(String state){
        int code = 4;
        switch (state) {
            case "ordered":
                code = 0;
                break;
            case "approved":
                code = 1;
                break;
            case "denied":
                code = 2;
                break;
            case "completed":
                code = 3;
                break;
            default:
                break;
        }
        return code;
    }
    
    public String getSeason(int code) {
        String season = null;
        switch (code) {
            case 0:
                season = seasons[0];
                break;
            case 1:
                season = seasons[1];
                break;
            case 2:
                season = seasons[2];
                break;
            case 3:
                season = seasons[3];
                break;
            default:
                break;
        }
        return season;
    }
    public int toDbSeason(String season){
        int code = 4;
        switch (season) {
            case "spring":
                code = 0;
                break;
            case "summer":
                code = 1;
                break;
            case "fall":
                code = 2;
                break;
            case "winter":
                code = 3;
                break;
            default:
                break;
        }
        return code;
    }
}
