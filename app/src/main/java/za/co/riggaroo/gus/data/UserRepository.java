package za.co.riggaroo.gus.data;

import java.util.List;

import rx.Observable;
import za.co.riggaroo.gus.data.remote.model.User;

/**
 * Created by sauyee on 25/12/17.
 */

public interface UserRepository {
    Observable<List<User>> searchUsers(String searchTerm);
}
