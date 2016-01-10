package controllers.dto;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Zer0 on 06/09/2015.
 */
public abstract class ListDto<T> {

    private int totalItems;
    private int itemsPerPages;
    private Collection<T> items = new LinkedList<>();

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPages() {
        return itemsPerPages;
    }

    public void setItemsPerPages(int itemsPerPages) {
        this.itemsPerPages = itemsPerPages;
    }

    public Collection<T> getItems() {
        return items;
    }
}
