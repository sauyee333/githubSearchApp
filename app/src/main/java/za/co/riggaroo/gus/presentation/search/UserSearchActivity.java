package za.co.riggaroo.gus.presentation.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import za.co.riggaroo.gus.R;
import za.co.riggaroo.gus.data.remote.model.User;

public class UserSearchActivity extends AppCompatActivity implements UserSearchContract.View {

    private static final String TAG = "UserSearchActivity";
    private UserSearchContract.Presenter userSearchPresenter;
    private UsersAdapter usersAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView textViewErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
//userSearchPresenter = new UserSearchPresenter()
    }

    @Override
    public void showSearchResults(List<User> githubUserList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
