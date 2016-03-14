package br.com.jsantini.testzup.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import br.com.jsantini.testzup.R;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.presenter.IFavoriteListPresenter;
import br.com.jsantini.testzup.presenter.impl.FavoriteListPresenterImpl;
import br.com.jsantini.testzup.view.adapter.FavoriteMoviesAdapter;
import br.com.jsantini.testzup.view.adapter.MoviesListAdapter;

/**
 * View para carregar listagem de filmes favoritos e retorno da pesquisa na Api IMdb
 */
public class FavoriteListActivity extends AppCompatActivity implements IFavoriteListView {
    private ListView favoriteListView;
    private ListView moviesApiListView;
    private IFavoriteListPresenter presenter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        loadToolbar();
        this.presenter = new FavoriteListPresenterImpl(this);
        favoriteListView = ((ListView)findViewById(R.id.list_favorite_movies));
        moviesApiListView = ((ListView)findViewById(R.id.list_movies));
        presenter.executeTaskFindFavoriteMovies();
        buildListeners();
    }

    /**
     * Configurando listeners para os clicks no item da lista de pesquisa
     * e no item da lista de favoritos
     */
    private void buildListeners() {
        moviesApiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.clickMovieItemApi(position);
            }
        });
        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.clickMovieItemFavorite(position);
            }
        });
    }

    public void loadToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void setMoviesFavoriteAdapter(List<Movie> moviesFavorite) {
        setVisibilityNoResult(View.GONE);
        moviesApiListView.setVisibility(View.GONE);
        favoriteListView.setVisibility(View.VISIBLE);
        favoriteListView.setAdapter(new FavoriteMoviesAdapter(this, moviesFavorite));
    }

    @Override
    public ListAdapter getFavoriteAdapter() {
        return favoriteListView.getAdapter();
    }

    @Override
    public void setMoviesApiAdapter(List<Movie> movies) {
        setVisibilityNoResult(View.GONE);
        favoriteListView.setVisibility(View.GONE);
        moviesApiListView.setVisibility(View.VISIBLE);

        moviesApiListView.setAdapter(new MoviesListAdapter(this, movies));
    }

    @Override
    public ListAdapter getApiAdapter() {
        return moviesApiListView.getAdapter();
    }

    @Override
    public void showMessageFailLoadList() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_fail_load_movies, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.findMoviesApi(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_list:
                presenter.executeTaskFindFavoriteMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitleView(String title) {
        TextView titleView = (TextView) findViewById(R.id.txt_title_view);
        titleView.setText(title);
        titleView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }

    @Override
    public void setLoadingVisibility(int visibility) {
        RelativeLayout lyt = (RelativeLayout) findViewById(R.id.loading_panel);
        lyt.setVisibility(visibility);
    }

    /**
     * Exibe texto quando pesquisa não retorna nenhum resultado
     * @param visibility
     */
    @Override
    public void setVisibilityNoResult(int visibility) {
        showInfoApp(View.GONE);
        if(visibility == View.VISIBLE) {
            favoriteListView.setVisibility(View.GONE);
            moviesApiListView.setVisibility(View.GONE);
        }
        TextView txtNoResult = (TextView) findViewById(R.id.txt_no_results);
        txtNoResult.setVisibility(visibility);

    }

    @Override
    public void showErrorApi() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_error_api, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    /**
     * Remove o foco atual
     */
    @Override
    public void removeFocus() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Exibe um texto informativo na tela
     * @param visibility
     */
    @Override
    public void showInfoApp(int visibility) {
        if(visibility == View.VISIBLE) {
            favoriteListView.setVisibility(View.GONE);
            moviesApiListView.setVisibility(View.GONE);
        }
        TextView txtInfo = (TextView) findViewById(R.id.txt_info_app);
        txtInfo.setVisibility(visibility);
    }
}
