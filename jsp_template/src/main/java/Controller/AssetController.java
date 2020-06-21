package Controller;

import DB.DBManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Asset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AssetController")
public class AssetController extends HttpServlet {

  private JSONObject convertProfileToJsonObject(Asset asset) {
    JSONObject jObj = new JSONObject();
    jObj.put("id", asset.getId());
    jObj.put("user_id", asset.getUserId());
    jObj.put("name", asset.getName());
    jObj.put("description", asset.getDescription());
    jObj.put("value", asset.getValue());
    return jObj;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action");
    System.out.println("Action:");
    System.out.println(action);
    if (action != null && action.equals("addAssets")) {
        String assetsJSON = req.getParameter("entries");
        Integer userId = Integer.parseInt(req.getParameter("userId"));
      System.out.println(assetsJSON);
      System.out.println(userId);
      ObjectMapper mapper = new ObjectMapper();
      Asset[] assets = mapper.readValue(assetsJSON, Asset[].class);
      for(Asset asset: assets){
        DBManager.addNewAsset(asset);
      }
    }
}
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String action = req.getParameter("action");
    System.out.println("Action:");
    System.out.println(action);
    if (action != null && action.equals("getAssets")) {
      int userId = Integer.parseInt(req.getParameter("userId"));
      JSONArray answer = new JSONArray();
      for (Asset asset : AppController.getAllAssets(userId)) {
        JSONObject jsObject = convertProfileToJsonObject(asset);
        answer.add(jsObject);
      }
      try (PrintWriter out = new PrintWriter(resp.getOutputStream())) {
        out.println(answer.toJSONString());
        out.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
