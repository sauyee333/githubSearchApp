package za.co.riggaroo.gus.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sauyee on 25/12/17.
 */

public class UsersList {
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("items")
    @Expose
    private List<User> items = new ArrayList<User>();

    public UsersList(final List<User> githubUsers) {
        this.items = githubUsers;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
