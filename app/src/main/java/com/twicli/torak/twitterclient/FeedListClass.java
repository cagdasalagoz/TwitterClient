package com.twicli.torak.twitterclient;

/**
 * Created by cagdasalagoz on 5/2/16.
 */
class FeedListClass {
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

    public String getPostContent() {
        return postContent;
    }


}
