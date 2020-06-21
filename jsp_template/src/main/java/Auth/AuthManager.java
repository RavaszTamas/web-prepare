package Auth;

import DB.DBManager;
import model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {

  public static String encrypt(String passwordToHash) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(passwordToHash.getBytes());
      byte[] bytes = md.digest();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return generatedPassword;
  }

  public static User isValidUserLogin(String userName, String password) {

    User user = null;
    try {
      //            Connection dbConnection = DBManager.getConnection();
      //            String sql = "SELECT * FROM `users` where `username` = ? AND `password` = ?";
      //            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
      //            preparedStatement.setString(1,userName);
      //            preparedStatement.setString(2,password);
      //            ResultSet resultSet = preparedStatement.executeQuery();
      String sql =
          "SELECT * FROM `users` where `username` = '"
              + userName
              + "' AND `password` = '"
              + password
              + "';";
      PreparedStatement stmt = DBManager.getConnection().prepareStatement(sql);
      ResultSet resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        user =
            new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

    return user;
  }
}
