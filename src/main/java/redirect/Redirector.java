package redirect;

import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.SkypeException;
import model.RedirectRecord;
import skype.SkypeController;
import ui.ComboCellInsetsDemo;

/**
 * Created by Serhii on 10/26/2015.
 *
 */
public class Redirector {
    private static SkypeController skypeController = SkypeController.getInstance();

    public static void redirect(MessageReceivedEvent event) {
        for (RedirectRecord redirectRecord : ComboCellInsetsDemo.getInstance().getDataTable()) {
            System.out.print(redirectRecord.from);
            System.out.print(redirectRecord.to);
            if (skypeController.getChatName(event.getChat()).equals(redirectRecord.from) && !event.getMessage().getSender().getUsername().equals(SkypeController.getCurrentUsername())) {
              //TODO redirect from combobox value
                try {
                    System.out.println(" message redirecting to ==="+redirectRecord.to);
                    skypeController.redirectMessage(skypeController.findChatByName(redirectRecord.to), event.getMessage().getMessage());
                } catch(SkypeException se){se.printStackTrace();}
                System.out.println("redirect completed!!!");
            }
        }
    }
    public static void redirectBack(MessageReceivedEvent event) {
        for (RedirectRecord redirectRecord : ComboCellInsetsDemo.getInstance().getDataTable()) {
            System.out.print(redirectRecord.from);
            System.out.print(redirectRecord.to);
            if (skypeController.getChatName(event.getChat()).equals(redirectRecord.to)
                    && !event.getMessage().getSender().getUsername().equals(SkypeController.getCurrentUsername())) {
                //TODO redirect from combobox value
                try {
                    skypeController.redirectMessage(skypeController.findChatByName(redirectRecord.from), event.getMessage().getMessage());
                } catch(SkypeException se){se.printStackTrace();}
                System.out.println("redirect completed!!!");
            }
        }
    }

}
