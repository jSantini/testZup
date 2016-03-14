package br.com.jsantini.testzup.view.task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.presenter.IDetailMoviePresenter;
import br.com.jsantini.testzup.presenter.IFavoriteListPresenter;

/**
 * Created by jsantini on 13/03/16.
 * AsyncTask para buscar os dados do filme na base de forma asincrona e fora da ui thread
 */
public class SelectMoviesTask extends AsyncTask<Object, Integer, Integer> {

    private Context context;
    private IFavoriteListPresenter presenter;
    private final Integer RESULT_SUCCESS = 1;
    private final Integer RESULT_FAIL = 2;
    private List<Movie> movies;

    public SelectMoviesTask() {
        super();
    }

    public SelectMoviesTask(Context context, IFavoriteListPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    protected Integer doInBackground(Object... params) {
        try {
            movies = presenter.findFavoriteMovies();
        } catch (Exception e) {
            return RESULT_FAIL;
        }
        return RESULT_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer numero){
        switch (numero) {
            case 1:
                presenter.buildListMovies(movies);
                break;
            case 2:
                presenter.showMessageFailLoadList();
                break;
        }
    }
}
