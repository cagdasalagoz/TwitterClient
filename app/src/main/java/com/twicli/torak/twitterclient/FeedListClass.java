package com.twicli.torak.twitterclient;

/**
 * Created by torak on 5/2/16.
 */
public class FeedListClass {
    private String listName,postContent;


    public FeedListClass(String listName, String postContent) {

        this.listName = listName;
        this.postContent = postContent;
    }

    @Override
    public String toString() {
        return listName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }


}
