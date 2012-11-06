package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RSProcessor {
	void process(ResultSet rs) throws SQLException;
}
