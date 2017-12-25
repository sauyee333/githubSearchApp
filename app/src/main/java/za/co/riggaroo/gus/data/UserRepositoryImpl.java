package za.co.riggaroo.gus.data;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import za.co.riggaroo.gus.data.remote.GithunUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;

/**
 * Created by sauyee on 25/12/17.
 */

public class UserRepositoryImpl implements UserRepository {

    private GithunUserRestService githunUserRestService;

    public UserRepositoryImpl(GithunUserRestService githunUserRestService) {
        this.githunUserRestService = githunUserRestService;
    }

    @Override
    public Observable<List<User>> searchUsers(final String searchTerm) {
        return Observable.defer(() -> githunUserRestService.searchGithubUsers(searchTerm).concatMap(
                userslist -> Observable.from(userslist.getItems())
                        .concatMap(user -> githunUserRestService.getUser(user.getLogin())).toList()))
                .retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }
}
