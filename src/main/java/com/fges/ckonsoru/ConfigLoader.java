/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fges.ckonsoru;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author julie.jacques
 */
public class ConfigLoader {
    
    public static String CONF_FILE = "config.properties";
    
    public Properties getProperties() {
	InputStream inputStream;
        Properties properties = new Properties();
        try{   
            inputStream = getClass().getClassLoader()
                                    .getResourceAsStream(CONF_FILE);
            if(inputStream != null){
                properties.load(inputStream);
            }else{
                System.err.println("Le fichier " + CONF_FILE + " n'a pas été trouvé");
            }
        }catch(IOException ioe){
            System.err.println("Problème à l'ouverture du fichier " + CONF_FILE);
        }
        return properties;
    }
}
