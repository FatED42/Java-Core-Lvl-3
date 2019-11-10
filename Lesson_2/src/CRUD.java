import java.sql.*;

class CRUD {
    private Connection connection;
    private Statement stmt;

    void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    void close() throws SQLException {
        connection.close();
    }

    void creationTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE students (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL ," +
                "score TEXT NOT NULL )");
    }

    void addData(String name, int score) throws SQLException {
        stmt.executeUpdate(String.format("INSERT INTO students (name, score) VALUES ('%s','%d')", name, score));
    }

    void updateData(String name, String newName) throws SQLException {
        stmt.executeUpdate(String.format("UPDATE students SET name = '%s' WHERE name = '%s'", newName, name));
    }

    void deleteData(Integer id, String name) throws SQLException {
        if (id != null) {
            stmt.executeUpdate(String.format("DELETE FROM students WHERE id = '%d'", id));
        }
        if (name != null) {
            stmt.executeUpdate(String.format("DELETE FROM students WHERE name = '%s'", name));
        }
    }

    void deleteTable(String nameTable) throws SQLException {
        stmt.execute(String.format("DROP TABLE IF EXISTS '%s'", nameTable));
    }

    void selectData(boolean isMetaData) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM students");
        ResultSetMetaData rsmd = rs.getMetaData();
        if (isMetaData) {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnName(i) + "   ");
            }
            System.out.println();
        } else {
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "   ");
                }
                System.out.println();
            }
        }
    }
}