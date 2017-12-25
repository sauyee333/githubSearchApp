package za.co.riggaroo.gus.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import za.co.riggaroo.gus.data.remote.GithunUserRestService;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sauyee on 25/12/17.
 */
public class UserRepositoryImplTest {

    private static final String USER_LOGIN_NAME = "Andrea";
    private static final String USER_LOGIN_NAME2 = "Tracy";

    @Mock
    GithunUserRestService githunUserRestService;
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userRepository = new UserRepositoryImpl(githunUserRestService);
    }

    @Test
    public void searchUsers_200OkResponse_InvokeCorrectApiCalls() {

        //mock searchGithubUsers api by stubbing response
        when(githunUserRestService.searchGithubUsers(anyString()))
                .thenReturn(Observable.just(githubUserList()));

        when(githunUserRestService.getUser(anyString()))
                .thenReturn(Observable.just(user1FullDetails()), Observable.just(user2FullDetails()));

        //create subscriber to call api
        TestSubscriber<List<User>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers(USER_LOGIN_NAME).subscribe(subscriber);

        //wait for response
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        //get the response
        List<List<User>> onNextEvents = subscriber.getOnNextEvents();
        List<User> users = onNextEvents.get(0);

        //check if equal
        Assert.assertEquals(USER_LOGIN_NAME, users.get(0).getLogin());
        Assert.assertEquals(USER_LOGIN_NAME2, users.get(1).getLogin());

        //check if sequence of events happen
        verify(githunUserRestService).searchGithubUsers(USER_LOGIN_NAME);
        verify(githunUserRestService).getUser(USER_LOGIN_NAME);
        verify(githunUserRestService).getUser(USER_LOGIN_NAME2);
    }

    private UsersList githubUserList() {
        User user = new User(USER_LOGIN_NAME);
        User user2 = new User(USER_LOGIN_NAME2);

        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);
        return new UsersList(githubUsers);
    }

    private User user1FullDetails(){
        return new User(USER_LOGIN_NAME, "Rigs Fransk", "avatar_url", "Bio1");
    }

    private User user2FullDetails(){
        return new User(USER_LOGIN_NAME2, "Rebecca Fransk", "avatar_url2", "Bio2");
    }
}