package com.crooks;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by johncrooks on 6/14/16.
 */
public class MainTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test"); //Makes a database in memory named test
        Main.createTables(conn);
        return conn;
    }

    @Test
    public void testUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alice", "");
        User user = Main.selectUser(conn, "Alice");
        conn.close();

        assertTrue(user !=null);  // making sure the user query works

    }

    @Test
    public void testMessage() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alice", "");
        User user = Main.selectUser(conn, "Alice");

        Main.insertMessage(conn, -1,"Hellow World",1);
        Message msg = Main.selectMessage(conn,1);

        conn.close();

        assertTrue(msg !=null);
        assertTrue(msg.author.equals("Alice"));
    }

    @Test
    public void testReplies() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alice","");
        Main.insertUser(conn,"bob", "");

        User alice = Main.selectUser(conn, "Alice");
        User bob = Main.selectUser(conn, "bob");

        Main.insertMessage(conn, -1,"hello World", alice.id);
        Main.insertMessage(conn, 1, "yooooo what up Alice!", bob.id);
        Main.insertMessage(conn, 1, "Another reply", bob.id);

        ArrayList<Message> msgs = Main.selectReplies(conn,1);

        conn.close();

        assertTrue(msgs.size() == 2);

    }

    @Test
    public void testDeleteMessage() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Alice", "");
        Main.insertMessage(conn, -1, "Hello World", 1);
        Main.deleteMessage(conn, 1);
        Message msg = Main.selectMessage(conn, 1);
        conn.close();
        assertTrue(msg==null);
    }

}