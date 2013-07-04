package com.kodcu;

import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: usta
 * Date: 3/12/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchTwitTask implements Runnable {

    private SseBroadcaster broadcaster;

    public SearchTwitTask(SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Override
    public void run() {

        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        JsonObjectBuilder jsonObject = Json.createObjectBuilder();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("************")
                .setOAuthConsumerSecret("************")
                .setOAuthAccessToken("************")
                .setOAuthAccessTokenSecret("************");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();

        QueryResult result = null;
        try {
            result = twitter.search(new Query("Java EE"));
            for (Status status : result.getTweets()) {
                jsonObject.add("created_at", status.getCreatedAt().toString());
                jsonObject.add("from_user_name", status.getUser().getScreenName());
                jsonObject.add("profile_image_url", status.getUser().getProfileImageURL());
                jsonArray.add(jsonObject.add("text", status.getText()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        String twitJsonArray= jsonArray.build().toString();

        OutboundEvent.Builder b = new OutboundEvent.Builder();
        b.mediaType(MediaType.APPLICATION_JSON_TYPE);

        broadcaster.broadcast(b.data(String.class,twitJsonArray).build());

    }
}
