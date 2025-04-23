package ict.db;

import ict.bean.UserBean;
import ict.bean.ShopBean;
import ict.bean.WarehouseBean;
import ict.bean.CityBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDB {
    private ProjectDB db;
    
    public TestDB() {
        db = ProjectDB.getInstance();
    }
    
    // Test user authentication
    public boolean testAuthenticateUser(String username, String password) {
        return db.authenticateUser(username, password);
    }

    
   
}
