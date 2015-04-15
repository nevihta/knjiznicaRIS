package ris.projekt.knjiznica.baza;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

//source: http://www.javatips.net/blog/2013/12/dbcp-connection-pooling-example?page=1
public class DataSource {

    private static DataSource     datasource;
    private BasicDataSource ds;

    private DataSource() throws IOException, SQLException, PropertyVetoException {
        ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost/knjiznica");
       
     // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(1);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(50);

    }

    public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
        if (datasource == null) {
            datasource = new DataSource();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }

}