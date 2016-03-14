package br.com.jsantini.testzup.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.persistence.MovieTable;
import br.com.jsantini.testzup.model.persistence.TestZupDataBaseHelper;

/**
 * Created by jsantini on 13/03/16.
 */
public class MovieDAO {
    /**
     * Método que retorna um mapa com a configuração - coluna, valor
     * @param movie
     * @return
     */
    private static ContentValues contentValuesInsertMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieTable.COLUMN_TITLE, movie.getTitle());
        contentValues.put(MovieTable.COLUMN_YEAR, movie.getYear());
        contentValues.put(MovieTable.COLUMN_RATED, movie.getRated());
        contentValues.put(MovieTable.COLUMN_RUNTIME, movie.getRuntime());
        contentValues.put(MovieTable.COLUMN_GENRE, movie.getGenre());
        contentValues.put(MovieTable.COLUMN_DIRECTOR, movie.getDirector());
        contentValues.put(MovieTable.COLUMN_WRITER, movie.getWriter());
        contentValues.put(MovieTable.COLUMN_ACTORS, movie.getActors());
        contentValues.put(MovieTable.COLUMN_PLOT, movie.getPlot());
        contentValues.put(MovieTable.COLUMN_LANGUAGE, movie.getLanguage());
        contentValues.put(MovieTable.COLUMN_COUNTRY, movie.getCountry());
        contentValues.put(MovieTable.COLUMN_AWARDS, movie.getAwards());
        contentValues.put(MovieTable.COLUMN_POSTER, movie.getPoster());
        contentValues.put(MovieTable.COLUMN_METAS_CORE, movie.getMetascore());
        contentValues.put(MovieTable.COLUMN_IMDB_RATING, movie.getImdbRating());
        contentValues.put(MovieTable.COLUMN_IMDB_VOTES, movie.getImdbVotes());
        contentValues.put(MovieTable.COLUMN_IMDB_ID, movie.getIdImdb());
        contentValues.put(MovieTable.COLUMN_TYPE, movie.getType());
        return contentValues;
    }

    /**
     * Salva um filme na base de dados
     * @param context
     * @param movie
     * @throws Exception
     */
    public void saveMovie(Context context, Movie movie) throws Exception {
        //Pega a instancia única da base de dados alocando a tabela (writable)
        SQLiteDatabase writableDatabase = TestZupDataBaseHelper.getWritableDatabase(context);
        //Abre transação
        writableDatabase.beginTransaction();
        try {
            //Insert content
            ContentValues insertValues = contentValuesInsertMovie(movie);
            //Insere o filme na tabela
            writableDatabase.insert(MovieTable.NAME, null, insertValues);
        } catch (Exception e) {
            //Rollback
            writableDatabase.endTransaction();
            throw e;
        } finally {
            //Comita e fecha a transação
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    /**
     * Busca os filmes da base com ordenação descrescente pelo id
     * @param context
     * @return
     * @throws Exception
     */
    public List<Movie> findAll(Context context) throws Exception {
        try {

            //Pega a única instância do banco de dados. Sem alocar a tabela(readable)
            SQLiteDatabase readableDatabase = TestZupDataBaseHelper.getReadableDatabase(context);
            //Execute sql query com todos os campos
            Cursor data = readableDatabase.query(MovieTable.NAME, MovieTable.PROJECTION, null, null, null, null, "_id DESC");
            //Move para o primeiro elemento
            data.moveToFirst();

            //Se o select não tem resultados retorna null
            if(data.getCount() < 1) {
                return null;
            }
            //Lista que será retornada com os filmes da base
            List<Movie> movies = new ArrayList<>(data.getCount());
            //Percorre o resultSet
            do {
                //Chama método que convert result query em objeto Movie
                movies.add(getMovie(data));
            } while (data.moveToNext());
            return movies;
        } catch (Exception e) {
            throw e;

        }
    }

    /**
     * Converte resultSet em Movie
     * @param cursor
     * @return
     */
    private static Movie getMovie(Cursor cursor) {
        // números de indices baseados em MovieTable#PROJECTION
        final Long id = cursor.getLong(0);
        final String title = cursor.getString(1);
        final String year = cursor.getString(2);
        final String rated = cursor.getString(3);
        final String runtime = cursor.getString(4);
        final String genre = cursor.getString(5);
        final String director = cursor.getString(6);
        final String writer = cursor.getString(7);
        final String actors = cursor.getString(8);
        final String plot = cursor.getString(9);
        final String language = cursor.getString(10);
        final String country = cursor.getString(11);
        final String awards = cursor.getString(12);
        final String poster = cursor.getString(13);
        final String metascore = cursor.getString(14);
        final String imdbRating = cursor.getString(15);
        final String imdbVotes = cursor.getString(16);
        final String idImdb = cursor.getString(17);
        final String type = cursor.getString(18);

        return new Movie(id, title, year, rated, runtime, genre, director, writer, actors, plot, language,
                country, awards, poster, metascore, imdbRating, imdbVotes, idImdb, type);
    }

    /**
     * Busca o filme pelo idImdb.
     * @param context
     * @param idImdb
     * @return Movie
     */
    public Movie getFavoriteMovieByIdImdb(Context context, String idImdb) {
        SQLiteDatabase readableDatabase = TestZupDataBaseHelper.getReadableDatabase(context);
        Cursor data = readableDatabase.query(MovieTable.NAME, MovieTable.PROJECTION, "idImdb LIKE ?", new String[]{idImdb}, null, null, null, null);
        if(data.moveToFirst()) {
            return getMovie(data);
        }
        return null;
    }

    /**
     * Deleta um filme da base de dados
     * @param context
     * @param idImdb
     * @throws Exception
     */
    public void deleteByIdImdb(Context context, String idImdb) throws Exception {
        SQLiteDatabase writableDatabase = TestZupDataBaseHelper.getWritableDatabase(context);
        writableDatabase.beginTransaction();
        try {
            writableDatabase.delete(MovieTable.NAME, "idImdb LIKE ? ", new String[]{idImdb});
        } catch (Exception e) {
            writableDatabase.endTransaction();
            throw e;
        } finally {
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }
}
