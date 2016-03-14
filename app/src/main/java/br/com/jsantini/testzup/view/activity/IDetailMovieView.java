package br.com.jsantini.testzup.view.activity;

import android.content.Context;
import android.content.Intent;

import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.view.helper.EnumTheme;

/**
 * Created by jsantini on 12/03/16.
 */
public interface IDetailMovieView {
    Intent getIntent();

    void buildMovieData(Movie movie, EnumTheme theme, boolean isFavorite);

    void showSuccessSave();

    void showFailSave();

    Context getActivity();

    void startActivity(Intent intent);

    void showInternalError();

    void showErrorApi();

    void showMessageHasAlreadyAdded();
}
