package br.com.jsantini.testzup.presenter;

import android.content.Context;

import br.com.jsantini.testzup.model.domain.Movie;

/**
 * Created by jsantini on 12/03/16.
 */
public interface IDetailMoviePresenter {
    void save(Context context, Movie movie) throws Exception;

    void executeSaveTask(Context context);

    void showMessageSuccessSave();

    void showMessageFailSave();

    void showMessageHasAlreadyAdded();

    void goFavoriteActivity();

    void gotToBack();
}
