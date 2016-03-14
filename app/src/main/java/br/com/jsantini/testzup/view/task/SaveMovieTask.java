package br.com.jsantini.testzup.view.task;

import android.content.Context;
import android.os.AsyncTask;

import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.exception.MovieUniqueIdImdbException;
import br.com.jsantini.testzup.presenter.IDetailMoviePresenter;

/**
 * Created by jsantini on 13/03/16.
 * AsyncTask para salvar filme na base de forma asincrona e fora da ui thread
 */
public class SaveMovieTask extends AsyncTask<Object, Integer, Integer> {

    private Movie movie;
    private Context context;
    private IDetailMoviePresenter presenter;
    private final Integer RESULT_SUCCESS = 1;
    private final Integer RESULT_FAIL = 2;
    private final Integer RESULT_VALIDATION_UNIQUE = 3;

    public SaveMovieTask() {
        super();
    }

    public SaveMovieTask(Context context, Movie movie, IDetailMoviePresenter presenter) {
        this.context = context;
        this.movie = movie;
        this.presenter = presenter;
    }

    @Override
    protected Integer doInBackground(Object... params) {
        try {
            presenter.save(context, movie);
        } catch (MovieUniqueIdImdbException e) {
            return RESULT_VALIDATION_UNIQUE;
        } catch (Exception e) {
            return RESULT_FAIL;
        }
        return RESULT_SUCCESS;
    }

    @Override
    protected void onPostExecute(Integer numero){
        switch (numero) {
            case 1:
                presenter.showMessageSuccessSave();
                break;
            case 2:
                presenter.showMessageFailSave();
                break;
            case 3:
                presenter.showMessageHasAlreadyAdded();
                break;
        }
    }
}
