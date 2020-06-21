package Auth;

import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class Login extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String userName = req.getParameter("username");
    String password = req.getParameter("password");
    System.out.println(userName);
    System.out.println(password);
    String encryptedPassword = AuthManager.encrypt(password);
    System.out.println(encryptedPassword);
    User user = AuthManager.isValidUserLogin(userName, encryptedPassword);
    RequestDispatcher requestDispatcher;
    if (user != null) {
      req.getSession().setAttribute("user", user);
      logInTheUser(user, req);
      requestDispatcher = req.getRequestDispatcher("/index.jsp");
    } else {
      requestDispatcher =
          req.getRequestDispatcher("/login.jsp?error=" + "Username or password is invalid");
    }
    requestDispatcher.forward(req, resp);
  }

  public static void logInTheUser(User user, HttpServletRequest request) {
    request.getSession().setAttribute("user", user);
  }
}
