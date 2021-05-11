package com.fges.ckonsoru.data.psql;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class BDDAdapterSingleton {

    static private BDDAdapterSingleton instance;
    private Connection db;

    private BDDAdapterSingleton(){  }

    public static BDDAdapterSingleton getInstance(){
        if(BDDAdapterSingleton.instance == null){
            BDDAdapterSingleton.instance = new BDDAdapterSingleton();
        }

        return BDDAdapterSingleton.instance;
    }

    public void init(String bddUrl, String bddUser, String bddPassword) {
        try {
            this.db = DriverManager.getConnection(bddUrl, bddUser, bddPassword);
        } catch (SQLException error) {
            System.err.println(error.getMessage());
            this.db = null;
        }
    }

    PreparedStatement createStatement(String query, ArrayList<Object> parameters) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(query);
        int cpt = 1;
        for (Object param:
             parameters) {
            stmt.setObject(cpt, param);
            cpt++;
        }

        return stmt;
    }

    /**
     * Find elements in the postgresql database
     * @param query sql query to get elements
     * @param parameters sql parameters to set to the query
     * @return A resultSet 
     */
    ResultSet find(String query, ArrayList<Object> parameters) throws SQLException {
        PreparedStatement stmt = this.createStatement(query, parameters);

        ResultSet rs = stmt.executeQuery();
        stmt.close();
        return rs;
    }

    /**
     * Make any data update on postgresql database (update, insert, delete)
     * @param query sql query to update elements
     * @param parameters sql parameters to set to the query
     * @return A boolean which indicate the success
     */
    boolean update(String query, ArrayList<Object> parameters) throws SQLException {
        PreparedStatement stmt = this.createStatement(query, parameters);

        int resultCount = stmt.executeUpdate();
        stmt.close();
        return resultCount > 0;
    }

}
