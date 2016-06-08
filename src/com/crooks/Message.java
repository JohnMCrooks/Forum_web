package com.crooks;

/**
 * Created by johncrooks on 6/8/16.
 */
public class Message {
    String msgText;
    int id;
    int replyId;
    String author;

    public Message( int id, int replyId, String author,String msgText) {
        this.msgText = msgText;
        this.id = id;
        this.replyId = replyId;
        this.author = author;
    }

}

