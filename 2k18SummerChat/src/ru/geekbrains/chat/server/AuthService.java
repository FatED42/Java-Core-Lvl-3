package ru.geekbrains.chat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<String> LoadBlackList(String name) {
        List<String> blacklist = new ArrayList<>();
        try {
            String sql = String.format("SELECT m1.nickname FROM main as m1 Inner Join BlackList as b on m1.id=b.id_block_user Inner Join main as m2 on m2.id=b.id_user WHERE m2.nickname='%s'",name);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                blacklist.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blacklist;
    }

    static boolean addBlackList(String nickThis, String nickBlock) {
        boolean res = false;
        if (!nickThis.equalsIgnoreCase(nickBlock)) {
            try {
                String id_user;
                String id_block_user;
                String sql = String.format("SELECT main.id FROM main WHERE main.nickname='%s'",nickBlock);
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    sql = String.format("SELECT main.id FROM main WHERE main.nickname='%s'", nickThis);
                    rs = stmt.executeQuery(sql);
                    id_user = rs.getString(1);
                    sql = String.format("SELECT main.id FROM main WHERE main.nickname='%s'", nickBlock);
                    rs = stmt.executeQuery(sql);
                    id_block_user = rs.getString(1);
                    sql = String.format("INSERT INTO BlackList (id_user,id_block_user) VALUES ('%s','%s')", id_user, id_block_user);
                    stmt.execute(sql);
                    res = true;
                } else {
                    res = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            res = false;
        }
        return res;
    }

    static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
