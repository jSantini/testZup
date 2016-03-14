package br.com.jsantini.testzup.model.persistence;

import android.provider.BaseColumns;

/**
 * Created by jsantini on 13/03/16.
 * Tabela Movie
 */
public interface MovieTable {

    String NAME = "movie";

    String COLUMN_ID = BaseColumns._ID;
    String COLUMN_TITLE = "title";
    String COLUMN_YEAR = "year";
    String COLUMN_RATED = "rated";
    String COLUMN_RUNTIME = "runtime";
    String COLUMN_GENRE = "genre";
    String COLUMN_DIRECTOR = "director";
    String COLUMN_WRITER = "writer";
    String COLUMN_ACTORS = "actors";
    String COLUMN_PLOT = "plot";
    String COLUMN_LANGUAGE = "language";
    String COLUMN_COUNTRY = "country";
    String COLUMN_AWARDS = "awards";
    String COLUMN_POSTER = "poster";
    String COLUMN_METAS_CORE = "metascore";
    String COLUMN_IMDB_RATING = "imdbRating";
    String COLUMN_IMDB_VOTES = "imdbVotes";
    String COLUMN_IMDB_ID = "idImdb";
    String COLUMN_TYPE = "type";

    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_RATED, COLUMN_RUNTIME,
            COLUMN_GENRE, COLUMN_DIRECTOR, COLUMN_WRITER, COLUMN_ACTORS, COLUMN_PLOT, COLUMN_LANGUAGE,
            COLUMN_COUNTRY, COLUMN_AWARDS, COLUMN_POSTER, COLUMN_METAS_CORE, COLUMN_IMDB_RATING, COLUMN_IMDB_VOTES,
            COLUMN_IMDB_ID, COLUMN_TYPE};

    String CREATE = "CREATE TABLE " + NAME + " ("
            + COLUMN_ID + " TEXT PRIMARY KEY, "
            + COLUMN_TITLE + " TEXT NOT NULL, "
            + COLUMN_YEAR + " TEXT, "
            + COLUMN_RATED + " TEXT, "
            + COLUMN_RUNTIME + " TEXT, "
            + COLUMN_GENRE + " TEXT, "
            + COLUMN_DIRECTOR + " TEXT, "
            + COLUMN_WRITER + " TEXT, "
            + COLUMN_ACTORS + " TEXT, "
            + COLUMN_PLOT + " TEXT, "
            + COLUMN_LANGUAGE + " TEXT, "
            + COLUMN_COUNTRY + " TEXT, "
            + COLUMN_AWARDS + " TEXT, "
            + COLUMN_POSTER + " TEXT, "
            + COLUMN_METAS_CORE + " TEXT, "
            + COLUMN_IMDB_RATING + " TEXT, "
            + COLUMN_IMDB_VOTES + " TEXT, "
            + COLUMN_IMDB_ID + " TEXT NOT NULL, "
            + COLUMN_TYPE + " TEXT);";
}