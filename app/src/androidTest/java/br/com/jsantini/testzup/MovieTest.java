package br.com.jsantini.testzup;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.view.MotionEvent;

import junit.framework.TestCase;

import org.junit.Before;

import br.com.jsantini.testzup.model.bo.MovieBO;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.view.activity.DetailMovieActivity;

/**
 * Created by jsantini on 10/03/16.
 */
public class MovieTest extends AndroidTestCase {

    Context mContext = null;

    @Before
    public void setUp() {
        mContext = getContext();
    }

    /**
     * Test para validar a inserção e deleção de um filme na base
     * @throws Exception
     */
    public void testSaveAndDeleteMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Test Movie 1");
        movie.setIdImdb("123");
        MovieBO movieBO = new MovieBO();
        movieBO.save(mContext, movie);
        Movie movie2 = movieBO.getFavoriteMovieByIdImdb(mContext, "123");
        assertNotNull(movie2);
        movieBO.deleteByIdImdb(mContext, movie2.getIdImdb());
        movie2 = movieBO.getFavoriteMovieByIdImdb(mContext, "123");
        assertNull(movie2);
    }
}
