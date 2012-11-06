package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBHelper {
	public static void iterate(Connection conn, String sql, RSProcessor processor) {
		try {
			Statement st = conn.createStatement();
			try {
				ResultSet rs = st.executeQuery(sql);
				try {
					while (rs.next()) {
						processor.process(rs);
					}
				}
				finally {
					rs.close();
				}
			}
			finally {
				st.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
