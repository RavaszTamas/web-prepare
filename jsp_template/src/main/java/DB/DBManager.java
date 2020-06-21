package DB;

import model.Asset;
import model.User;

import java.sql.*;

public class DBManager {

  private static Connection connection;

  public static void connect() {
    loadDriver();
    if (connection == null) {
      String url = "jdbc:mysql://localhost/jsp_db";
      try {
        connection = DriverManager.getConnection(url, "ubbstudent", "forclasspurposes");
      } catch (SQLException throwable) {
        throwable.printStackTrace();
      }
    }
  }

  public static void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connection = null;
  }

  private static void loadDriver() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("Can’t load driver");
    }
  }

  public static Connection getConnection() {
    if (connection == null) connect();
    return connection;
  }

  public static User addNewUser(String username, String password) {
    String sql =
        "INSERT INTO `users` (username, password) VALUES ('" + username + "','" + password + "');";
    System.out.println(sql);
    // Execute the query
    User user = null;
    try {
      PreparedStatement stmt = DBManager.getConnection().prepareStatement(sql);
      stmt.execute();

      String sqlForID =
          "SELECT * FROM `users` where `username` = '"
              + username
              + "' AND `password` = '"
              + password
              + "';";
      PreparedStatement stmtForID = DBManager.getConnection().prepareStatement(sqlForID);
      ResultSet resultsForID = stmtForID.executeQuery();

      while (resultsForID.next()) {
        user =
            new User(
                resultsForID.getInt("id"),
                resultsForID.getString("username"),
                resultsForID.getString("password"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

    public static void addNewAsset(Asset asset) {
      try{
        String sql = "INSERT INTO `assets` (user_id,name,description,value) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,asset.getUserId());
        preparedStatement.setString(2,asset.getName());
        preparedStatement.setString(3,asset.getDescription());
        preparedStatement.setInt(4,asset.getValue());
        preparedStatement.execute();
      }
      catch (SQLException ex){
        ex.printStackTrace();
      }
    }
}
