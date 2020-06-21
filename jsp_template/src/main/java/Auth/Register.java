package Auth;

import DB.DBManager;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet")
public class Register extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String password_repeat = req.getParameter("password-repeat");
    RequestDispatcher requestDispatcher;
    if (!password.equals(password_repeat)) {
      requestDispatcher =
          req.getRequestDispatcher("/register.jsp?error=" + "Passwords must be the same");
    } else {

      String encryptedPassword = AuthManager.encrypt(password);
      User user = DBManager.addNewUser(username, encryptedPassword);
      if (user == null) {
        requestDispatcher =
            req.getRequestDispatcher("/register.jsp?error=" + "Username already exists");

      } else {
        Login.logInTheUser(user, req);
        requestDispatcher = req.getRequestDispatcher("/index.jsp");
      }
    }
    requestDispatcher.forward(req, resp);
  }
}
