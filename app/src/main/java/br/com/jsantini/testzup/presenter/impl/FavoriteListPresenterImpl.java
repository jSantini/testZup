package br.com.jsantini.testzup.presenter.impl;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import br.com.jsantini.testzup.model.bo.MovieBO;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.helper.ImdbResultList;
import br.com.jsantini.testzup.presenter.IFavoriteListPresenter;
import br.com.jsantini.testzup.view.activity.DetailMovieActivity;
import br.com.jsantini.testzup.view.activity.IFavoriteListView;
import br.com.jsantini.testzup.view.task.SelectMoviesTask;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by jsantini on 13/03/16.
 * Classe responsável pela comunicação entre view e controller.
 * Implementa um callback da comuncação asincrona com a API Rest
 */
public class FavoriteListPresenterImpl implements IFavoriteListPresenter, Callback<ImdbResultList> {
    private IFavoriteListView favoriteListView;
    private MovieBO movieBO;
    private final String titleView = "Favorite Movies";
    private boolean isLoading;

    public MovieBO getMovieBO() {
        if(movieBO == null) {
            return new MovieBO();
        }
        return movieBO;
    }

    public FavoriteListPresenterImpl(IFavoriteListView view) {
        this.favoriteListView = view;
        favoriteListView.setTitleView(titleView);
    }

    /**
     * Busca os filmes que já estão na base para exibir na lista de favoritos
     * de forma asincrona com AsyncTask e fora da UI.
     */
    @Override
    public void executeTaskFindFavoriteMovies() {
        if(!isLoading) {
            isLoading = true;
            AsyncTask asyncTask = new SelectMoviesTask(favoriteListView.getActivity(), this);
            asyncTask.execute();
        }
    }

    @Override
    public List<Movie> findFavoriteMovies() throws Exception {
        return getMovieBO().findFavoriteMovies(favoriteListView.getActivity());
    }

    /**
     * Exibe a lista na view
     * @param moviesFavorite
     */
    @Override
    public void buildListMovies(List<Movie> moviesFavorite) {
        isLoading = false;
        //Loader desaparece
        favoriteListView.setLoadingVisibility(View.GONE);
        if(moviesFavorite != null && moviesFavorite.size() > 0) {
            //Se houver favoritos exibe na lista
            favoriteListView.setMoviesFavoriteAdapter(moviesFavorite);
        } else {
            //Se não, exibe texto informativo
            favoriteListView.showInfoApp(View.VISIBLE);
        }
    }

    /**
     * Callback para informar ao usuário sobre um erro ao buscar a lista
     */
    @Override
    public void showMessageFailLoadList() {
        favoriteListView.showMessageFailLoadList();
    }

    @Override
    public void clickMovieItemApi(int position) {
        Movie movie = (Movie) favoriteListView.getApiAdapter().getItem(position);
        Intent intent = new Intent(favoriteListView.getActivity(), DetailMovieActivity.class);
        intent.putExtra("idImdb", movie.getIdImdb());
        favoriteListView.startActivity(intent);
    }

    /**
     * Responde ao click do usuário em um item da lista chamando
     * a activity para detalhar o filme
     * @param position
     */
    @Override
    public void clickMovieItemFavorite(int position) {
        Movie movie = (Movie) favoriteListView.getFavoriteAdapter().getItem(position);
        Intent intent = new Intent(favoriteListView.getActivity(), DetailMovieActivity.class);
        intent.putExtra("idImdb", movie.getIdImdb());
        favoriteListView.startActivity(intent);
    }

    /**
     * Pesquisa os filmes na Api Imdb pelo título inserido
     * pelo usuário
     * @param title
     */
    @Override
    public void findMoviesApi(String title) {
        if(!isLoading) {
            isLoading = true;
            favoriteListView.setLoadingVisibility(View.VISIBLE);
            getMovieBO().findMoviesByTitle(title, "movie", this);
        }
    }

    /**
     * Callback de sucesso da resposta da chamada a Api Imdb
     * @param response
     */
    @Override
    public void onResponse(Response<ImdbResultList> response) {
        isLoading = false;
        favoriteListView.setLoadingVisibility(View.GONE);
        ImdbResultList result = response.body();
        Log.d("MainActivity", "response = " + new Gson().toJson(result));
        List<Movie> movieList = result.getSearch();
        if(movieList != null && movieList.size() > 0) {
            favoriteListView.setMoviesApiAdapter(movieList);
        } else {
            favoriteListView.setVisibilityNoResult(View.VISIBLE);
        }
        favoriteListView.removeFocus();
    }

    /**
     * Callback de falha da resposta da chamada a Api Imdb
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        favoriteListView.showErrorApi();
    }
}
