package controller.iam;

import dal.UserDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.iam.User;

/**
 * Đăng nhập: check username/password, nạp User (kèm Role/Features) vào session.
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Nếu đã đăng nhập, redirect về trang chính (home)
        HttpSession ses = req.getSession(false);
        if (ses != null && ses.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");  // Email hoặc Username
        String password = req.getParameter("password");

        // Check login
        UserDBContext udb = new UserDBContext();
        User user = udb.getByUsernamePassword(login, password); // Hàm tìm login bằng username hoặc email

        if (user == null) {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(30 * 60);  // 30 phút

        resp.sendRedirect(req.getContextPath() + "/home");
    }
}

