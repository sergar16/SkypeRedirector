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
import com.samczsun.skype4j.formatting.Text;
import com.samczsun.skype4j.user.User;
import model.SkypeComboboxItem;
import redirect.Redirector;
import utils.Encoder;
import utils.OS;

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
                    Redirector.redirectBack(e);
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
        return getAllChats().stream().filter(chat -> getChatName(chat).equals(name)).findAny().get();
    }

    public Chat findGroupChatByTopic(final String topic) {
        return getAllGroupChats().stream().filter(chat -> ((GroupChat) chat).getTopic().equals(topic)).findAny().get();
    }

    public Chat getIndividualChatWithUserByUserName(final String username) {
        return getUsers().stream().filter(user -> user.getUsername().equals(username))
                .findFirst().get().getChat();
    }

    public List<Chat> getAllIndividualChats() {
        return getAllChats().stream().filter(chat -> chat instanceof IndividualChat).collect(Collectors.toList());
    }

    public static ArrayList<SkypeComboboxItem> getSortedListOfGroupAndUsers() {
        Comparator<SkypeComboboxItem> byName = (e1, e2) ->
                e1.toString().compareTo(e2.toString());
        return getInstance().getAllChats().stream()
                .map(chat -> new SkypeComboboxItem(chat))
                .distinct()
                .sorted(byName).collect(Collectors.toCollection(ArrayList::new));
    }

    public static SkypeComboboxItem[] getSortedArrayOfGroupAndUsers() {
        return getSortedListOfGroupAndUsers().stream().toArray(chat -> new SkypeComboboxItem[chat]);
    }


    public void redirectMessage(final Chat chat, final Message message) throws SkypeException {
        if (OS.isWindows()) {
            Message encodeMessage = Message.create().with(Text.plain(Encoder.encode(message.asPlaintext())));
            chat.sendMessage(encodeMessage);
        } else
            chat.sendMessage(message);
    }

//    public void sendMessage(final Chat chat, final String message) {
//    }

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


