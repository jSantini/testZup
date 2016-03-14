package br.com.jsantini.testzup.presenter;

import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;

/**
 * Created by jsantini on 13/03/16.
 */
public interface IFavoriteListPresenter {
    void executeTaskFindFavoriteMovies();

    List<Movie> findFavoriteMovies() throws Exception;

    void buildListMovies(List<Movie> moviesFavorite);

    void showMessageFailLoadList();

    void clickMovieItemApi(int position);

    void clickMovieItemFavorite(int position);

    void findMoviesApi(String title);
}
