package redirect;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.SkypeException;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;
import skype.SkypeController;
import ui.ComboCellInsetsDemo;

/**
 * Created by Serhii on 10/26/2015.
 */
public class Redirector {
    private static SkypeController skypeController = SkypeController.getInstance();

    public static void redirect(MessageReceivedEvent event) {
        final String senderName = event.getMessage().getSender().getUsername();
        final Message recievedMessage = event.getMessage().getMessage();
        final Message redirectMessage = senderName.equals(SkypeController.getCurrentUsername()) ? recievedMessage :
                Message.create().with(Text.plain(senderName + " : " + recievedMessage.asPlaintext()));
        ComboCellInsetsDemo.getInstance().getDataTable()
                .stream()
                .forEach(redirectRecord -> {
                    if (skypeController.getChatName(event.getChat()).equals(redirectRecord.from)) {
                        //TODO redirect from combobox value
                        if (!(redirectRecord.doubleDirection
                                && senderName.equals(SkypeController.getCurrentUsername()))) {
                            try {
                                final Chat chatReceiver = skypeController.findChatByName(redirectRecord.to);
                                skypeController.redirectMessage(chatReceiver, redirectMessage);
                            } catch (SkypeException se) {
                                se.printStackTrace();
                            }
                        }
                    }
                });
    }

    public static void redirectBack(MessageReceivedEvent event) {
        final String senderName = event.getMessage().getSender().getUsername();
        final Message recievedMessage=event.getMessage().getMessage();
        ComboCellInsetsDemo.getInstance().getDataTable()
                .stream()
                .forEach(redirectRecord -> {
                    if (skypeController.getChatName(event.getChat()).equals(redirectRecord.to)
                            && !senderName.equals(SkypeController.getCurrentUsername())
                            && redirectRecord.doubleDirection) {
                        try {
                            final Chat chatReceiver = skypeController.findChatByName(redirectRecord.from);
                            skypeController.redirectMessage(chatReceiver,recievedMessage );
                        } catch (SkypeException se) {
                            se.printStackTrace();
                        }
                    }
                });
    }

}
