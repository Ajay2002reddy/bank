package bank;

import java.sql.*;

public class ConnectDatabase
{
    Connection connection;
    Statement statement;

    public ConnectDatabase()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///projectbms","root","6281679329.Hvr");
            statement = connection.createStatement();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
