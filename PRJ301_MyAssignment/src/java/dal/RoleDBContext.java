package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BaseModel;
import model.iam.Feature;
import model.iam.Role;

public class RoleDBContext extends DBContext {

    // Lấy 1 role theo id, kèm luôn các feature của role đó
    @Override
    public Role get(int roleId) {
        Role role = null;
        try {
            // 1. Lấy thông tin role
            String sqlRole = """
                             SELECT role_id, role_name, [description]
                             FROM [Role]
                             WHERE role_id = ?
                             """;
            PreparedStatement stmRole = connection.prepareStatement(sqlRole);
            stmRole.setInt(1, roleId);
            ResultSet rsRole = stmRole.executeQuery();
            if (rsRole.next()) {
                role = new Role();
                role.setRoleId(rsRole.getInt("role_id"));
                role.setRoleName(rsRole.getString("role_name"));
                role.setDescription(rsRole.getString("description"));

                // 2. Lấy luôn danh sách feature của role này
                ArrayList<Feature> features = getFeaturesByRole(roleId);
                role.setFeatures(features);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    // Lấy tất cả role (không bắt buộc phải lấy feature – cho nhẹ)

   
    @Override
    public ArrayList<Role> list() {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            String sql = """
                         SELECT role_id, role_name, [description]
                         FROM [Role]
                         """;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Role r = new Role();
                r.setRoleId(rs.getInt("role_id"));
                r.setRoleName(rs.getString("role_name"));
                r.setDescription(rs.getString("description"));
                roles.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return roles;
    }

    // Hàm phụ: lấy danh sách feature theo role
    private ArrayList<Feature> getFeaturesByRole(int roleId) {
        ArrayList<Feature> features = new ArrayList<>();
        try {
            String sql = """
                         SELECT f.feature_id, f.feature_name, f.feature_url
                         FROM [Feature] f
                         INNER JOIN [Role_Feature] rf ON f.feature_id = rf.feature_id
                         WHERE rf.role_id = ?
                         """;
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, roleId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Feature f = new Feature();
                f.setFeatureId(rs.getInt("feature_id"));
                f.setFeatureName(rs.getString("feature_name"));
                f.setFeatureUrl(rs.getString("feature_url"));
                features.add(f);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return features;
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
