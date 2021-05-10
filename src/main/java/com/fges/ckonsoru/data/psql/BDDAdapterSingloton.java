package com.fges.ckonsoru.data.psql;

import com.fges.ckonsoru.models.Availability;
import com.fges.ckonsoru.models.xml.XMLFriendly;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BDDAdapterSingloton {

    static private BDDAdapterSingloton instance;
    private Connection db;
    private String[] jours = {"lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimannche"};

    private BDDAdapterSingloton(){  }

    public BDDAdapterSingloton getInstance(){
        if(BDDAdapterSingloton.instance != null){
            BDDAdapterSingloton.instance = new BDDAdapterSingloton();
        }

        return BDDAdapterSingloton.instance;
    }

    public void init(String bddUrl, String bddUser, String bddPassword) {
        try {
            BDDAdapterSingloton.instance.db = DriverManager.getConnection(bddUrl, bddUser, bddPassword);
        } catch (SQLException error) {
            System.out.println(error.getMessage());
            BDDAdapterSingloton.instance.db = null;
        }
    }

    /**
     * Find elements in the postgresql database
     * @param query sql query to get elements
     * @param parameters sql parameters to set to the query
     * @return A resultSet 
     */
    ResultSet find(String query, ArrayList parameters) {
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            int cpt = 1;



            Iterator it = parameters.iterator();
            while(it.hasNext()){
                Object param = it.next();

                if(param instanceof String){
                    stmt.setString(cpt, (String) param);
                }
                else if(param instanceof LocalTime){
                    Time sqlTime = Time.valueOf((LocalTime) param);
                    stmt.setTime(cpt, sqlTime);
                }
                else if(param instanceof LocalDate){
                    Date sqlDate = Date.valueOf((LocalDate) param);
                    stmt.setDate(cpt, sqlDate);
                }
                else if(param instanceof DayOfWeek){
                    stmt.setString(cpt, this.jours[((DayOfWeek) param).getValue() - 1]);
                }
                else if(param instanceof LocalDateTime){
                    Timestamp sqlTimestamp = Timestamp.valueOf((LocalDateTime) param);
                    stmt.setTimestamp(cpt, sqlTimestamp);
                }

                cpt++;
            }


            ResultSet rs = stmt.executeQuery();
            stmt.close();
            return rs;
        }
        catch (SQLException error){
            System.out.println("avaibality.getAvailabilityByDay :");
            System.out.println(error.getMessage());
            return null;
        }
    }

    /**
     * Make any data update on postgresql database (update, insert, delete)
     * @param query sql query to update elements
     * @param parameters sql parameters to set to the query
     * @return A boolean which indicate the success
     */
    boolean update(String query, ArrayList parameters) {
        try {
            PreparedStatement stmt = db.prepareStatement(query);
            int cpt = 1;



            Iterator it = parameters.iterator();
            while(it.hasNext()){
                Object param = it.next();

                if(param instanceof String){
                    stmt.setString(cpt, (String) param);
                }
                else if(param instanceof LocalTime){
                    Time sqlTime = Time.valueOf((LocalTime) param);
                    stmt.setTime(cpt, sqlTime);
                }
                else if(param instanceof LocalDate){
                    Date sqlDate = Date.valueOf((LocalDate) param);
                    stmt.setDate(cpt, sqlDate);
                }
                else if(param instanceof DayOfWeek){
                    stmt.setString(cpt, this.jours[((DayOfWeek) param).getValue() - 1]);
                }
                else if(param instanceof LocalDateTime){
                    Timestamp sqlTimestamp = Timestamp.valueOf((LocalDateTime) param);
                    stmt.setTimestamp(cpt, sqlTimestamp);
                }

                cpt++;
            }


            int insertedCount = stmt.executeUpdate();
            stmt.close();
            return insertedCount > 0;
        }
        catch (SQLException error){
            System.out.println("avaibality.getAvailabilityByDay :");
            System.out.println(error.getMessage());
            return false;
        }
    }

}
