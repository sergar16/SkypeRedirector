package model;

import skype.SkypeController;
import utils.slack.SlackController;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Serhii on 10/30/2015.
 */
public class Chat {
    public static ArrayList<ComboboxItem> getListofAllChats() {
        ArrayList<SkypeComboboxItem> skypeComboboxItems = SkypeController.getInstance().getAllChats()
                .stream().map(SkypeComboboxItem::new).collect(Collectors.toCollection(ArrayList::new));

//        ArrayList<SlackComboboxItem> slackComboboxChannelItems = SlackController.getAllChannels()
//                .stream().
//                        filter(value->!value.toString().equals(""))
//                .map(SlackComboboxItem::new).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<ComboboxItem> list = new ArrayList(skypeComboboxItems);
    //    list.addAll(slackComboboxChannelItems);

        return list;
    }
}
