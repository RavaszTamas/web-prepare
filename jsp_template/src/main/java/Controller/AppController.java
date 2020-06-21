package Controller;

import DB.DBManager;
import model.Asset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AppController {

  public static List<Asset> getAllAssets(Integer userID) {
    List<Asset> result = new ArrayList<>();
    System.out.println("Obtaining all assets");
    try {
      PreparedStatement preparedStatment =
          DBManager.getConnection().prepareStatement("SELECT * FROM `assets` WHERE user_id = ?");
      preparedStatment.setInt(1, userID);
      ResultSet resultSet = preparedStatment.executeQuery();
      while (resultSet.next()) {
          Asset asset = new Asset(
                  resultSet.getInt("id"),
                  resultSet.getInt("user_id"),
                  resultSet.getString("name"),
                  resultSet.getString("description"),
                  resultSet.getInt("value")
          );
        System.out.println(asset);
          result.add(asset
        );
      }
    } catch (SQLException ex) {

    }
    System.out.println(result);
    return result;
  }
}
