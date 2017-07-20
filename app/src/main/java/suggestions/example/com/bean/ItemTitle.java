package suggestions.example.com.bean;

import java.util.ArrayList;

/**
 * Created by root on 17-7-14.
 */

public class ItemTitle {

    public String name;
    public ArrayList <ItemContent> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ItemContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<ItemContent> content) {
        this.content = content;
    }
}
