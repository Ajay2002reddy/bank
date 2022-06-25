package bank;

import java.sql.*;
import java.util.*;

public class Main {

    static String username;
    static String password;
    static int balance;
    static String name;
    static int accno;
    static Scanner sc = new Scanner(System.in);

    static void login() {
        System.out.println("Enter username: ");
        username = sc.next();
        System.out.println("Enter password: ");
        password = sc.next();
        try {
            ConnectDatabase connectDatabase = new ConnectDatabase();
            String sql = "select * from account where username=? and password=?";
            PreparedStatement statement = connectDatabase.connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                home();
            } else {
                System.out.println("Invalid Login. Try Again");
                login();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static void getDetails()
    {
        try
        {
            ConnectDatabase connectDatabase = new ConnectDatabase();
            String sql = "select * from account where username=?";
            PreparedStatement statement = connectDatabase.connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("username");
                name = resultSet.getString("name");
                password = resultSet.getString("password");
                accno = resultSet.getInt("accno");
                balance = resultSet.getInt("balance");
            } else {
                System.out.println("Can't connect to database");
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    static void setBalance()
    {
        try {
            ConnectDatabase connectDatabase = new ConnectDatabase();
            String sql = "update account set balance = ? where username = ? ";
            PreparedStatement statement = connectDatabase.connection.prepareStatement(sql);
            statement.setInt(1, balance);
            statement.setString(2, username);
            int i = statement.executeUpdate();
            if (i > 0) {
                System.out.println("Successfully completed!");
            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    static public void showBalance()
    {
        getDetails();
        System.out.println("Your Current Balance Is: " + balance);
        home();
    }

    static public void deposit()
    {
        getDetails();
        System.out.println("Enter The Amount To Deposit (without commas): ");
        int amount = sc.nextInt();
        balance = balance + amount;
        setBalance();
        showBalance();
        home();
    }

    static public void withdraw()
    {
        getDetails();
        System.out.println("Enter The Amount To Withdraw (without commas): ");
        int amount = sc.nextInt();

        if(amount > balance)
        {
            System.out.println("Can't withdraw. You only have " + balance);
        }
        else
        {
            balance = balance - amount;
            setBalance();
            showBalance();
        }
        home();
    }
    static public void logout()
    {
        System.out.println("See you soon!");
        setBalance();
        login();
    }

    static void home()
    {
        getDetails();
        System.out.println("What do you want to do?");
        System.out.println("Enter '1' to Show Balance\nEnter '2' to Deposit\nEnter '3' to Withdraw\nEnter '4' to Logout");
        int stateb = sc.nextInt();

        switch (stateb) {
            case 1 -> showBalance();
            case 2 -> deposit();
            case 3 -> withdraw();
            case 4 -> logout();
        }
    }

    static void register(){

        System.out.println("Enter Name: ");
        String name = sc.next();

        System.out.println("Set Account no: ");
        int accno = sc.nextInt();

        System.out.println("Set Username: ");
        username = sc.next();

        System.out.println("Set Password: ");
        password = sc.next();

        balance = 0;

        try
        {
            ConnectDatabase connectDatabase = new ConnectDatabase();

            String sql = "insert into account(username, name, password, accno, balance) values(?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDatabase.connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, password);
            statement.setInt(4, accno);
            statement.setInt(5, balance);

            int i = statement.executeUpdate();
            if (i > 0)
            {
                System.out.print("Successfully Registered!");
                start();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    static void start()
    {
        System.out.println("Bank Management System");
        System.out.println("Enter '1' to Login\nEnter '2' to Register\nEnter '3' to exit\nEnter here: ");

        int state = sc.nextInt();

        switch (state)
        {
            case 1 -> login();
            case 2 -> register();
            case 3 -> System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        start();
    }
}
