package skype;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.chat.IndividualChat;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.SkypeException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.user.User;
import model.ComboboxItem;
import redirect.Redirector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Serhii on 10/25/2015.
 */
public class SkypeController {
    private static Skype skype;
    private static SkypeController instance = null;


    private SkypeController() {
    }

    public static SkypeController getInstance() {
        if (instance == null) {
            instance = new SkypeController();
        }
        return instance;
    }

    public static String getCurrentUsername() {
        return skype.getUsername();
    }

    public static String asUTF8(String string) {
        byte[] array = string.getBytes();
        String s = new String(array, Charset.forName("UTF-8"));
        return s;

    }

    public static void main(String[] a) {
        SkypeController skypeController = new SkypeController();
        skypeController.getAllGroupChats().forEach(chat -> {
            System.out.println(((GroupChat) chat).getTopic());
        });
        skypeController.getAllIndividualChats().forEach(chat -> {
            System.out.println(((IndividualChat) chat).getIdentity());
        });
        skypeController.getUsers().stream().forEach(user -> System.out.println(user.getUsername()));
        Message message = Message.create();
    }

    public static void signIn(final String username, final String password) throws Exception {
        skype = Skype.login(username, password);
    }

    public static void logout() throws IOException {
        skype.logout();
    }


    public void addRedirectEventListener() {
        try {
            skype.getEventDispatcher().registerListener(new Listener() {
                @EventHandler
                public void onMessage(MessageReceivedEvent e) {
                    Redirector.redirect(e);
                }
            });
            skype.subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Chat> getAllChats() {
        return skype.getAllChats();
    }

    public Collection<Chat> getAllGroupChats() {
        return getAllChats().stream().filter(chat -> chat instanceof GroupChat).collect(Collectors.toList());
    }

    public String getChatName(final Chat chat) {
        final String chatName = getGroupChatName(chat);
        if (chatName != null) {
            return chatName;
        } else return getIndividualChatName(chat);
    }

    private String getGroupChatName(final Chat chat) {
        if (chat instanceof GroupChat) {
            return ((GroupChat) chat).getTopic();
        }
        return null;
    }

    private String getIndividualChatName(final Chat chat) {
        if (chat instanceof IndividualChat) {
            IndividualChat individualChat = (IndividualChat) chat;
            return ((User) chat.getAllUsers().toArray()[0]).getUsername().equals(getCurrentUsername())
                    ? ((User) chat.getAllUsers().toArray()[1]).getUsername() : ((User) chat.getAllUsers().toArray()[0]).getUsername();
        }
        return null;
    }

    public Chat findChatByName(final String name) {
        Chat find = null;
        for (Chat chat : getAllChats()) {
            try {
                if (getChatName(chat).equals(name)) {
                    find = chat;
                    break;
                }
            } catch (ClassCastException cce) {
            }
        }
        return find;
    }

    public Chat findGroupChatByTopic(final String topic) {
        //return getAllGroupChats().stream().filter(chat -> ((GroupChat)chat).getTopic().equals(topic)).findAny().get();
        Chat find = null;
        for (Chat chat : getAllGroupChats()) {
            try {
                if (((GroupChat) chat).getTopic().equals(topic)) {
                    find = chat;
                    break;
                }
            } catch (ClassCastException cce) {
            }
        }
        return find;
    }

    public Chat getIndividualChatWithUserByUserName(final String username) {
        return getUsers().stream().filter(user -> user.getUsername().equals(username))
                .findFirst().get().getChat();
    }

    public List<Chat> getAllIndividualChats() {
        return getAllChats().stream().filter(chat -> chat instanceof IndividualChat).collect(Collectors.toList());
    }

    public static ArrayList<ComboboxItem> getSortedListOfGroupAndUsers() {
        Comparator<ComboboxItem> byName = (e1, e2) ->
                e1.toString().compareTo(e2.toString());
        return getInstance().getAllChats().stream()
                .map(chat -> new ComboboxItem(chat))
                .sorted(byName).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ComboboxItem[] getSortedArrayOfGroupAndUsers() {
        return getSortedListOfGroupAndUsers().stream().toArray(chat -> new ComboboxItem[chat]);
    }


    public void redirectMessage(final Chat chat, final Message message) throws SkypeException {
        chat.sendMessage(message);
        System.out.println("message " + asUTF8(message.asPlaintext()) + " redirected");
    }

    public void sendMessage(final Chat chat, final String message) {

    }

    public List<User> getUsers() {
        return getAllIndividualChats().stream()
                .map(chat -> ((User) chat.getAllUsers().toArray()[0]).getUsername().equals(getCurrentUsername())
                        ? (User) chat.getAllUsers().toArray()[1] : (User) chat.getAllUsers().toArray()[0])
                .collect(Collectors.toList());
    }

    public static Skype getSkype() {
        return skype;
    }

    public static void setSkype(Skype skype) {
        SkypeController.skype = skype;
    }
}


