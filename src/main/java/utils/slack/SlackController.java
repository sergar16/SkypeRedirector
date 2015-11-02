package utils.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import utils.IO;

import java.io.IOException;
import java.net.Proxy;
import java.util.Collection;

/**
 * Created by Serhii on 10/29/2015.
 */
public class SlackController {
    private static SlackSession session;
    private String authorisationTolen;
  static {
      try{
        directConnect("xoxp-2787111044-3759195074-12631784853-282d3d9889");}catch (IOException ioe){}
    }
    public static void directConnect(final String authorisationTolen) throws IOException {
        session = SlackSessionFactory.
                createWebSocketSlackSession(authorisationTolen);
        session.connect();
    }

    public static void connectThroughProxy(final String authorisationTolen) throws IOException {
        session = SlackSessionFactory.
                createWebSocketSlackSession(authorisationTolen, Proxy.Type.HTTP, "myproxy", 1234);
        session.connect();
    }

    public static void addMessageListener() {
        session.addMessagePostedListener(new SlackMessagePostedListener() {
            @Override
            public void onEvent(SlackMessagePosted event, SlackSession session) {
                session.sendMessageOverWebSocket(session.findChannelByName("general"), "Message sent : " + event.getMessageContent(), null);
            }
        });
    }
    public SlackChannel findChannelByName(final String channelName){
       return session.findChannelByName(channelName);
    }

    public static Collection<SlackChannel> getAllChannels() {
        return session.getChannels();
    }
    public static SlackChannel [] getAllChannelsArray() {
        return (SlackChannel [])session.getChannels().toArray();
    }

    public static Collection<SlackUser> getAllUsers() {
        return session.getUsers();
    }


}
