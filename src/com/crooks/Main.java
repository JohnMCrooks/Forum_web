package com.crooks;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<String, User> userHash = new HashMap<>();
    static ArrayList<Message> messageList = new ArrayList<>();

    public static void main(String[] args) {
        addTestMsg();
        addTestUsers();
    }

    static void addTestUsers(){
        userHash.put("Alice", new User("Alice", ""));
        userHash.put("Bob", new User("Bob", ""));
        userHash.put("Charlie", new User("Charlie", ""));

    }

    static void addTestMsg(){
        messageList.add(new Message(0,-1,"Alice","Hello World!"));
        messageList.add(new Message(1,-1,"Bob","This is a new thread"));
        messageList.add(new Message(2,0,"Charlie", "Cool Thread, Alice."));
    }
}
