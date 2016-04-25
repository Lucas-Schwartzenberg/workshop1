/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionPools;

import com.zaxxer.hikari.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Gebruiker
 */

/**
 * Hier kan je de HikariCP van downloaden en dan de HikariCP 2.4.5 de jar file
 * http://search.maven.org/#search|ga|1|com.zaxxer.hikaricp
 * Ga naar libraries in netbeans rechtermuisknop workshop1 properties
     * Druk op libraries
     * add library
     * create
     * dan add the file commons-validator-1.5.0 aan de classpath
     * 
     * verder heb je ook nog de slf4j-api nodig en de slf4j-simple
     * zelfde manier als hikariCP te vinden op http://www.slf4j.org/download.html
 * @author Peter
 */

public class HermanConnectionPoolHikari {
    HikariDataSource ds;
    public HermanConnectionPoolHikari(){
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(1);
        config.setMaximumPoolSize(2);
        config.setInitializationFailFast(true);
        //config.setConnectionTestQuery("VALUES 1");
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", "localhost");
        config.addDataSourceProperty("port", "3306");
        config.addDataSourceProperty("databaseName", "workshopdb");
        config.addDataSourceProperty("user", "rsvier");
        config.addDataSourceProperty("password", "tiger");
        
                this.ds = new HikariDataSource(config);
    }
    public Connection getConnection ()throws SQLException, ClassNotFoundException{
        return ds.getConnection();
    }
}