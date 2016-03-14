package br.com.jsantini.testzup.model.bo;

import android.content.Context;
import java.util.List;
import br.com.jsantini.testzup.model.api.ApiMovie;
import br.com.jsantini.testzup.model.dao.MovieDAO;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.exception.MovieUniqueIdImdbException;
import br.com.jsantini.testzup.model.helper.ImdbResultList;
import retrofit.Call;
import retrofit.Callback;

/**
 * Created by jsantini on 10/03/16.
 */
public class MovieBO {
    private MovieDAO movieDAO;

    public MovieDAO getMovieDAO() {
        if(movieDAO == null) {
            return new MovieDAO();
        }
        return movieDAO;
    }

    /**
     * Busca os filmes no Imdb pelo Título
     * utilizando a chamada retrofit de forma asíncrona.
     * Implementar uma operação indeterminada de forma síncrona pode causar falha na aplicação.
     * @param title
     * @param type
     * @param moviesCallback
     */
    public void findMoviesByTitle(final String title, final String type, final Callback<ImdbResultList> moviesCallback) {
        ApiMovie.OMDbApi service = ApiMovie.getApi();
        Call<ImdbResultList> call = service.findMoviesByTitle(title.trim(), type);
        call.enqueue(moviesCallback);
    }

    /**
     * Busca todos os dados do filme pelo ImdbId usando chamada retrofit asíncrona
     * @param idImdb
     * @param moviesCallback
     */
    public void getMovieByIdImdb(final String idImdb, final Callback<Movie> moviesCallback) {
        ApiMovie.OMDbApi service = ApiMovie.getApi();
        Call<Movie> call = service.findMoviesByIdImdb(idImdb.trim());
        call.enqueue(moviesCallback);
    }

    /**
     * Salva o filme na base de dados. O filme passa a ser favorito
     * Pode retornar uma exception customizada validando campo único idImdb
     * @param context
     * @param movie
     * @throws Exception
     */
    public void save(Context context, Movie movie) throws Exception {
        if(getFavoriteMovieByIdImdb(context, movie.getIdImdb()) != null) {
            throw new MovieUniqueIdImdbException("Movie has already been added");
        }
        getMovieDAO().saveMovie(context, movie);
    }

    /**
     * Busca todos os filmes da base com ordenação decrescente pelo id
     * @param context
     * @return
     * @throws Exception
     */
    public List<Movie> findFavoriteMovies(Context context) throws Exception {
        return getMovieDAO().findAll(context);
    }

    /**
     * Retorna o filme da base de dados pelo idImdb
     * @param context
     * @param idImdb
     * @return
     * @throws Exception
     */
    public Movie getFavoriteMovieByIdImdb(Context context, String idImdb) throws Exception {
        return getMovieDAO().getFavoriteMovieByIdImdb(context, idImdb);
    }

    /**
     * Deleta um filme da base de dados.
     * @param context
     * @param idImdb
     * @throws Exception
     */
    public void deleteByIdImdb(Context context, String idImdb) throws Exception {
        getMovieDAO().deleteByIdImdb(context, idImdb);
    }
}
