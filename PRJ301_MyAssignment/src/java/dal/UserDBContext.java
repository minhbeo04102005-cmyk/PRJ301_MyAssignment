package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaseModel;
import model.Employee;
import model.iam.Role;
import model.iam.User;

public class UserDBContext extends DBContext {
public User getByUsernamePassword(String login, String password) {
    try {
        // Câu SQL sửa lại để login bằng email hoặc username
        String sql = """
                     SELECT 
                         u.user_id,
                         u.username,
                         u.[password],
                         u.email        AS user_email,
                         u.role_id,
                         u.created_at,
                         r.role_name,
                         r.[description],
                         e.emp_id,
                         e.full_name,
                         e.dob,
                         e.phone,
                         e.email        AS emp_email,
                         e.department_id,
                         e.supervisor_id,
                         e.hire_date,
                         e.[status]     AS emp_status
                     FROM [User] u
                     INNER JOIN [Role] r ON u.role_id = r.role_id
                     LEFT JOIN [Employee] e ON e.email = u.email
                     WHERE (u.username = ? OR u.email = ?) AND u.[password] = ?
                     """;

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, login);   // set username hoặc email
        stm.setString(2, login);   // set username hoặc email
        stm.setString(3, password); // set mật khẩu
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            // --- USER ---
            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setEmail(rs.getString("user_email"));
            u.setRoleId(rs.getInt("role_id"));
            u.setCreatedAt(rs.getTimestamp("created_at"));

            // --- ROLE ---
            Role r = new Role();
            r.setRoleId(rs.getInt("role_id"));
            r.setRoleName(rs.getString("role_name"));
            r.setDescription(rs.getString("description"));
            u.setRole(r);

            // --- EMPLOYEE (có thể null) ---
            int empId = rs.getInt("emp_id");
            if (!rs.wasNull()) {  // Nếu có employee
                Employee e = new Employee();
                e.setEmpId(empId);
                e.setFullName(rs.getString("full_name"));
                e.setDob(rs.getDate("dob"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("emp_email"));
                e.setDepartmentId(rs.getInt("department_id"));
                e.setSupervisorId((Integer) rs.getObject("supervisor_id"));
                e.setHireDate(rs.getDate("hire_date"));
                e.setStatus(rs.getString("emp_status"));
                u.setEmployee(e);
            }

            return u;
        }

    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        closeConnection();
    }
    return null;
}

        
    @Override
    public ArrayList list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public BaseModel get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
