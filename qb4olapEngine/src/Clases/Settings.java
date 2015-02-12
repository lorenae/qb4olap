package Clases;

//Clase singleton donde se almacena la informacion de configuracion del sistema
//la misma es cargada al comienzo del mismo.
public class Settings {

    public String userName;
    public String password;
    public String driver = "com.mysql.jdbc.Driver";
    public String server;
    public String port;
    public String dbName = "dw2012";
    public static Settings getInstance() {
        return NewSettingsHolder.INSTANCE;
    }

    String getBaseUrl() {
        if(driver.equals("com.mysql.jdbc.Driver"))
            return "jdbc:mysql://"+server+":"+ port + "/" + dbName;
        else
            System.out.println("No se encuentra implementado ese driver.");
            return null;
    }
    
    private static class NewSettingsHolder {
        private static final Settings INSTANCE = new Settings();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
 
    

}




