package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseQuerier {
	private String username = "username";
	private String password = "password";
	private final String url = "jdbc:mysql://sql.domain.com/username";
	private Connection conn = null;

	public DatabaseQuerier() {
		createConnection();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public Connection getConn() {
		return conn;
	}

	private void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(getUrl(), this.username, this.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query, ArrayList<?> inputs) {
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(query);
			if (inputs != null) {
				for (int i = 0; i < inputs.size(); i++) {
					if (inputs.get(i) instanceof String)
						ps.setString(i + 1, (String) inputs.get(i));
					if (inputs.get(i) instanceof Integer)
						ps.setInt(i + 1, (Integer) inputs.get(i));
				}
			}
			rs = ps.executeQuery();
			// remember to close rs
		} catch (SQLException | NullPointerException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public void executeUpdate(String query, ArrayList<?> inputs) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			if (inputs != null) {
				for (int i = 1; i < inputs.size(); i++) {
					if (inputs.get(i - 1) instanceof String)
						ps.setString(i, (String) inputs.get(i));
					if (inputs.get(i - 1) instanceof Integer)
						ps.setInt(i, (Integer) inputs.get(i));
				}
			}
			ps.executeUpdate();
			ps.close();
		} catch (SQLException | NullPointerException | IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
