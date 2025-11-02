package controller.iam;

import dal.RoleDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.iam.Feature;
import model.iam.Role;
import model.iam.User;

/**
 * Yêu cầu: đã đăng nhập + có quyền (feature) với URL hiện tại.
 * Controller con override doAuthorizedGet/doAuthorizedPost để xử lý.
 */
public abstract class BaseRequiredAuthorizationController extends BaseRequiredAuthenticationController {

    private boolean isAuthorized(HttpServletRequest req, User user) {
        // Nếu chưa có role/features thì nạp từ DB theo roleId (1 user - 1 role)
        if (user.getRole() == null
                || user.getRole().getFeatures() == null
                || user.getRole().getFeatures().isEmpty()) {
            RoleDBContext rdb = new RoleDBContext();
            Role role = rdb.get(user.getRoleId());
            user.setRole(role);
            req.getSession().setAttribute("user", user); // cache lại
        }

        String currentUrl = req.getServletPath(); // ví dụ: /request/list
        if (user.getRole() == null || user.getRole().getFeatures() == null) return false;

        for (Feature f : user.getRole().getFeatures()) {
            // so sánh đúng tên getter theo model của bạn
            if (currentUrl.equalsIgnoreCase(f.getFeatureUrl())) {
                return true;
            }
        }
        return false;
    }

    protected final void processGet(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        if (isAuthorized(req, user)) {
            doAuthorizedGet(req, resp, user);
        } else {
            req.setAttribute("message", "Access denied!");
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    protected final void processPost(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        if (isAuthorized(req, user)) {
            doAuthorizedPost(req, resp, user);
        } else {
            req.setAttribute("message", "Access denied!");
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    protected abstract void doAuthorizedGet(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException;

    protected abstract void doAuthorizedPost(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException;
}
