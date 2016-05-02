package com.twicli.torak.twitterclient;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by torak on 5/2/16.
 */
public class FeedListAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FeedListClass> posts_list;

    public FeedListAdapter(AppCompatActivity activity, List<FeedListClass> kisiler) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        posts_list = kisiler;
    }

    @Override
    public Object getItem(int position) {
        return posts_list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return posts_list.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.feed_listview_row, parent, false);

        TextView listname_textview =
                (TextView) satirView.findViewById(R.id.search_listName_textview);

        TextView content_textview =
                (TextView) satirView.findViewById(R.id.search_about_textview);


        FeedListClass post = posts_list.get(position);

        listname_textview.setText(post.getListName());
        content_textview.setText(post.getPostContent());

        return satirView;
    }
}