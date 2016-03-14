package br.com.jsantini.testzup.view.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ListAdapter;

import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;

/**
 * Created by jsantini on 13/03/16.
 */
public interface IFavoriteListView {
    Context getActivity();

    void setMoviesFavoriteAdapter(List<Movie> moviesFavorite);

    ListAdapter getFavoriteAdapter();

    void setMoviesApiAdapter(List<Movie> movies);

    ListAdapter getApiAdapter();

    void showMessageFailLoadList();

    void startActivity(Intent intent);

    void setTitleView(String title);

    void setLoadingVisibility(int visibility);

    void setVisibilityNoResult(int visibility);

    void showErrorApi();

    void removeFocus();

    void showInfoApp(int visibility);
}
