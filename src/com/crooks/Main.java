package com.crooks;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.staticFileLocation;

public class Main {

    static HashMap<String, User> userHash = new HashMap<>();
    static ArrayList<Message> messageList = new ArrayList<>();

    public static void main(String[] args) {
        addTestMsg();
        addTestUsers();
        staticFileLocation("src");

        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    String idStr = request.queryParams("replyId")  ;
                    int replyId = -1;
                    if (idStr!= null){
                        replyId = Integer.valueOf(idStr);
                    }


                    ArrayList<Message> subset = new ArrayList<Message>();
                    for (Message msg: messageList){         //Filtering by reply id to only display top level posts
                        if (msg.replyId== replyId){
                            subset.add(msg);
                        }
                    }
                    HashMap m = new HashMap();
                    m.put("messages", subset);
                    return new ModelAndView(m, "home.html");
                },
                new MustacheTemplateEngine()

        );
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
