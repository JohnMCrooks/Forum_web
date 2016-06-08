package com.crooks;

import spark.ModelAndView;
import spark.Session;
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
        staticFileLocation("Resources");

        Spark.init();
        Spark.get(
                "/",
                (request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");

                    String idStr = request.queryParams("replyId");
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
                    m.put("username", username);

                    return new ModelAndView(m, "home.html");
                },
                new MustacheTemplateEngine()

        );

        Spark.post(
                "/login",
                (request, response) -> {

                    String username = request.queryParams("username");
                    if (username==null){
                        throw new Exception("Login Name Not Found");
                    }

                    User user = userHash.get(username);
                    if (user==null){
                        user = new User(username,"");
                        userHash.put(username,user);
                    }

                    Session session = request.session();
                    session.attribute("username", username);

                    response.redirect(request.headers("Referer")); // instead of redirecting back to the "/" route every time, get the Referer which indicates the page the user is coming from which is what we want them to redirect back to
                    return "";
                }
        );

        Spark.post(
                "/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();

                    response.redirect("/");
                    return"";
                }

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
