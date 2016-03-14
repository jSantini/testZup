package br.com.jsantini.testzup.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import br.com.jsantini.testzup.model.bo.MovieBO;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.helper.ImdbResultList;
import br.com.jsantini.testzup.presenter.IDetailMoviePresenter;
import br.com.jsantini.testzup.view.activity.FavoriteListActivity;
import br.com.jsantini.testzup.view.activity.IDetailMovieView;
import br.com.jsantini.testzup.view.helper.EnumTheme;
import br.com.jsantini.testzup.view.task.SaveMovieTask;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by jsantini on 12/03/16.
 * Classe responsável pela comunicação entre View e Controller
 */
public class DetailMoviePresenterImpl implements IDetailMoviePresenter, Callback<Movie> {

    private IDetailMovieView detailMovieView;
    private MovieBO movieBO;
    private Movie movie;

    public MovieBO getMovieBO() {
        if(movieBO == null) {
            return new MovieBO();
        }
        return movieBO;
    }

    public DetailMoviePresenterImpl(IDetailMovieView view) {
        detailMovieView = view;
        loadMovieData();
    }

    /**
     * Carrega o filme selecionado
     */
    public void loadMovieData() {
        //Recuperando parâmetro passado pela activity anterior
        String idImdb = detailMovieView.getIntent().getStringExtra("idImdb");
        try {
            //Busca filme na base
            movie = getMovieBO().getFavoriteMovieByIdImdb(detailMovieView.getActivity(), idImdb);
            if(movie != null) {
                //Filme já existe na base - chamando método para exibir na view
                String genreMain = movie.getGenre().split(",")[0].toLowerCase();
                buildMovieInView(genreMain, true);
            } else {
                //Filme não existe na base. Buscando dados na api de forma asincrona
                getMovieBO().getMovieByIdImdb(idImdb, this);
            }
        } catch (Exception e) {
            //Exibe um erro personalizado ao usuário
            detailMovieView.showInternalError();
        }
    }

    /**
     * Callback da chamada na api para pegar os dados do filme
     * @param response
     */
    @Override
    public void onResponse(Response<Movie> response) {
        movie = response.body();
        String genreMain = movie.getGenre().split(",")[0].toLowerCase();
        //Carrega o filme na view
        buildMovieInView(genreMain, false);
    }

    /**
     * Exibe o filme na view com estilos dinamicos dependendo do genero
     * @param genre
     * @param isFavorite
     */
    private void buildMovieInView(String genre, boolean isFavorite) {
        EnumTheme theme = EnumTheme.main;
        switch (genre) {
            case "comedy":
                theme = EnumTheme.comedy;
                break;

            case "drama":
                theme = EnumTheme.drama;
                break;

            case "action":
                theme = EnumTheme.action;
                break;

            case "animation":
                theme = EnumTheme.action;
                break;

            case "horror":
                theme = EnumTheme.horror;
                break;

            case "crime":
                theme = EnumTheme.horror;
                break;

        }
        detailMovieView.buildMovieData(movie, theme, isFavorite);
    }

    /**
     * Callback para o caso de falha da api para buscar os dados do filme
     * @param t
     */
    @Override
    public void onFailure(Throwable t) {
        detailMovieView.showErrorApi();
    }

    /**
     * Salva o filme na base
     * @param context
     * @param movie
     * @throws Exception
     */
    @Override
    public void save(Context context, Movie movie) throws Exception {
        getMovieBO().save(context, movie);
    }


    /**
     * Executa a tarefa de salvar o filme na base de forma asincrona e fora da ui thread
     * usando AsyncTask
     * @param context
     */
    @Override
    public void executeSaveTask(Context context) {
        AsyncTask asyncTask = new SaveMovieTask(context, movie, this);
        asyncTask.execute();
    }

    /**
     * Callback para informa o usuário que o filme foi salvo com sucesso
     */
    @Override
    public void showMessageSuccessSave() {
        detailMovieView.showSuccessSave();
    }

    /**
     * Callback para informar user que houve erro e o filme não foi salvo
     */
    @Override
    public void showMessageFailSave() {
        detailMovieView.showFailSave();
    }

    /**
     * Callback para informar ao usuário que este filme já foi salvo na lista
     * de favoritos
     */
    @Override
    public void showMessageHasAlreadyAdded() {
        detailMovieView.showMessageHasAlreadyAdded();
    }

    /**
     * Inicia a próxima activity
     */
    @Override
    public void goFavoriteActivity() {
        Intent intent = new Intent(detailMovieView.getActivity(), FavoriteListActivity.class);
        detailMovieView.startActivity(intent);
    }

    /**
     * Volta para a activity anterior
     */
    @Override
    public void gotToBack() {
        Intent intent = new Intent(detailMovieView.getActivity(), FavoriteListActivity.class);
        detailMovieView.startActivity(intent);
    }
}
