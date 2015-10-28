import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import com.samczsun.skype4j.user.User;

import java.security.acl.Group;

/**
 * Created by Serhii on 10/24/2015.
 */
public class Test {

    public static void main(String[] ar) {
        try {
            String username = "***";
            String password = "***";
            Skype skype = Skype.login(username, password);
            for (Chat chat :skype.getAllChats()){
                System.out.println("===============================================================");

                System.out.println(chat.getIdentity());

             try{
String topic= ((GroupChat)chat).getTopic();
                    if(topic.equals("skype forwarding 2015")){
                        Message message=com.samczsun.skype4j.formatting.Message.create();
                        message.with(new Text() {
                            @Override
                            public String write() {
                                return "message sent to chat skype forwarding 2015 ";
                            }
                        });
                        chat.sendMessage(message);
                    }
             }catch (ClassCastException cce){}
                   for (User user : chat.getAllUsers()){
                       System.out.println(user.getUsername());

                   }
                System.out.println("===============================================================");

            }

            skype.getEventDispatcher().registerListener(new Listener() {
                @EventHandler
                public void onMessage(MessageReceivedEvent e) {
                    System.out.println("Got message: " + e.getMessage());
                    System.out.println(e.toString());

                }
            });
            skype.subscribe();
// Do stuff
            skype.logout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
