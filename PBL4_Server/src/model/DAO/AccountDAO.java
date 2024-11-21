package model.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.BO.Account;
import model.database.JDBCUtil;

public class AccountDAO implements DAOInterface<Account>{

	@Override
	public int insert(Account t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Account t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Account t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Account> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account selectById(Account t) {
		try {
			//Tao ket noi toi csdl
			Connection con = JDBCUtil.getConnection();
			System.out.println(con);
			//Tao ra doi tuong statement
			Statement st = con.createStatement();
			
			//Buoc 3: thuc thi cau lenh SQL
			String sql = "SELECT * FROM account where username = '" + t.getUserName() + "'";
 			System.out.println(sql);
			
			ResultSet rs = st.executeQuery(sql);
			
			//Xu ly ket qua
			if (rs.next()) {
                // Lấy dữ liệu từ ResultSet
                String username = rs.getString("username");
                String password = rs.getString("password");
    			//Ngat Ket Noi
                JDBCUtil.closeConnection(con);
                return new Account(username, password);
            } else {
                // Không tìm thấy kết quả
                System.out.println("No account found with username: " + t.getUserName());
                JDBCUtil.closeConnection(con);
                return t;
            }

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Account> selectByCondition(String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
