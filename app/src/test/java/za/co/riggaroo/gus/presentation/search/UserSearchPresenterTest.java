package za.co.riggaroo.gus.presentation.search;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import za.co.riggaroo.gus.data.UserRepository;
import za.co.riggaroo.gus.data.remote.model.User;
import za.co.riggaroo.gus.data.remote.model.UsersList;
import za.co.riggaroo.gus.presentation.base.BasePresenter;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sauyee on 25/12/17.
 */
public class UserSearchPresenterTest {

    private static final String USER_LOGIN_NAME = "andrea";
    private static final String USER_LOGIN_NAME2 = "Tracy";

    @Mock
    UserRepository userRepository;
    @Mock
    UserSearchContract.View view;
    UserSearchPresenter userSearchPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userSearchPresenter = new UserSearchPresenter(userRepository, Schedulers.immediate(), Schedulers.immediate());
        userSearchPresenter.attachView(view);

    }

    @Test
    public void search_ValidSearchterm_ReturnResults() {

        //if call searchUsers, return this list of dummy users instead of calling actual api
        UsersList userList = getDummyUserList();
        when(userRepository.searchUsers(anyString()))
                .thenReturn(Observable.just(userList.getItems()));

        //do the search
        userSearchPresenter.search(USER_LOGIN_NAME);

        //verify following called or happened
        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view).showSearchResults(userList.getItems());
        verify(view, never()).showError(anyString());
    }

    @Test
    public void search_UserRepositoryError_ErrorMsg() {
        String errorMsg = "No internet";
        when(userRepository.searchUsers(anyString()))
                .thenReturn(Observable.error(new IOException(errorMsg)));

        userSearchPresenter.search("bookdash");

        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).showSearchResults(anyList());
        verify(view).showError(errorMsg);
    }

    @Test(expected = BasePresenter.MvpViewNotAttachedException.class)
    public void search_NotAttached_ThrowMvpException(){
        userSearchPresenter.detachView();

        userSearchPresenter.search("test");

        verify(view, never()).showLoading();
        verify(view, never()).showSearchResults(anyList());
    }

    UsersList getDummyUserList() {
        List<User> githubUsers = new ArrayList<>();
        githubUsers.add(user1FullDetails());
        githubUsers.add(user2FullDetails());
        return new UsersList(githubUsers);
    }

    private User user1FullDetails() {
        return new User(USER_LOGIN_NAME, "Rigs Fransk", "avatar_url", "Bio1");
    }

    private User user2FullDetails() {
        return new User(USER_LOGIN_NAME2, "Rebecca Fransk", "avatar_url2", "Bio2");
    }
}