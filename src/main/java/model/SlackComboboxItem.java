package model;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackUser;

/**
 * Created by Serhii on 10/30/2015.
 */
public class SlackComboboxItem implements ComboboxItem {
    private String name;
    private SlackChannel channel;

    public SlackComboboxItem(SlackChannel channel) {
        this.channel = channel;

        name=channel.getName();
        if (name==null){
        name=((SlackUser)channel.getMembers().toArray()[0]).getRealName();}
    }


    @Override
    public Programs getProgram() {
        return Programs.SLACK;
    }

    @Override
    public String toString() {
        return name;
    }

}
