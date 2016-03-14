package br.com.jsantini.testzup.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.jsantini.testzup.R;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.presenter.IDetailMoviePresenter;
import br.com.jsantini.testzup.presenter.impl.DetailMoviePresenterImpl;
import br.com.jsantini.testzup.view.helper.EnumTheme;

/**
 * View para exibir o detalhamento do filme
 */
public class DetailMovieActivity extends AppCompatActivity implements IDetailMovieView {
    private IDetailMoviePresenter presenter;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        presenter = new DetailMoviePresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadToolbar();
    }

    /**
     * Configura appbar
     */
    public void loadToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.gotToBack();
            }
        });
    }

    @Override
    public void buildMovieData(Movie movie, EnumTheme theme, boolean isFavorite) {
        this.isFavorite = isFavorite;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, theme.getmColorPrimaryId()));
        LinearLayout lyt = (LinearLayout) findViewById(R.id.lyt_header_detail_movie);
        lyt.setBackgroundColor(ContextCompat.getColor(this, theme.getWindowBackgroundColor()));
        ImageView imgPoster = (ImageView) findViewById(R.id.img_cover_poster);
        Picasso.with(this)
                .load(movie.getPoster())
                .placeholder(R.drawable.place_holder_image_128x128)
                .into(imgPoster);

        TextView txtRating = (TextView) findViewById(R.id.txt_rating);
        txtRating.setText(movie.getImdbRating());
        txtRating.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));

        TextView txtMetascore = (TextView) findViewById(R.id.txt_metascore);
        txtMetascore.setText(movie.getMetascore());
        txtMetascore.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));
        txtMetascore.setBackgroundColor(ContextCompat.getColor(this, theme.getmColorPrimaryId()));

        TextView txtVotos = (TextView) findViewById(R.id.txt_votos);
        txtVotos.setText(movie.getImdbVotes());
        txtVotos.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));
        txtVotos.setBackgroundColor(ContextCompat.getColor(this, theme.getmColorPrimaryId()));

        TextView txtGenre = (TextView) findViewById(R.id.txt_genre);
        txtGenre.setText(movie.getGenre());
        txtGenre.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));

        TextView titleView = (TextView) findViewById(R.id.txt_title_view);
        titleView.setText(movie.getTitle());
        titleView.setTextColor(theme.getmColorText());

        TextView txtRuntime = (TextView) findViewById(R.id.txt_runtime);
        txtRuntime.setText(movie.getRuntime());

        TextView txtYear = (TextView) findViewById(R.id.txt_year);
        txtYear.setText(movie.getYear());

        TextView txtEnredo = (TextView) findViewById(R.id.txt_enredo);
        txtEnredo.setText(movie.getPlot());

        TextView txtDirector = (TextView) findViewById(R.id.txt_director);
        txtDirector.setText(movie.getDirector());

        TextView txtWriter = (TextView) findViewById(R.id.txt_writer);
        txtWriter.setText(movie.getWriter());

        TextView txtActors = (TextView) findViewById(R.id.txt_actors);
        txtActors.setText(movie.getActors());

        TextView txtCountry = (TextView) findViewById(R.id.txt_country);
        txtCountry.setText(movie.getCountry());

        TextView txtLanguages = (TextView) findViewById(R.id.txt_languages);

        TextView lblVotes = (TextView) findViewById(R.id.lbl_votes);
        lblVotes.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));

        TextView lblPoints = (TextView) findViewById(R.id.lbl_points);
        lblPoints.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));

        txtLanguages.setText(movie.getLanguage());

        if(!movie.getAwards().equals("N/A")) {
            LinearLayout lytAward = (LinearLayout) findViewById(R.id.lyt_awards);
            lytAward.setVisibility(View.VISIBLE);
            lytAward.setBackgroundColor(ContextCompat.getColor(this, theme.getmColorPrimaryId()));
            TextView txtAwards = (TextView) findViewById(R.id.txt_awards);
            txtAwards.setText(movie.getAwards());
            txtAwards.setTextColor(ContextCompat.getColor(this, theme.getmColorText()));
            txtLanguages.setText(movie.getLanguage());
        }
    }

    @Override
    public void showSuccessSave() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_success_save_movie, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void showFailSave() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_fail_save_movie, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void showInternalError() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_internal_error, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    /**
     * Configurando o menu na appbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail_movie, menu);
        if(isFavorite) {
            Drawable normalDrawable = menu.getItem(0).getIcon();
            Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            Drawable normalDrawable = menu.getItem(0).getIcon();
            Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorWhite));
        }

        return true;
    }

    /**
     * Listener disparado ao clicar nos itens do menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_list:
                presenter.goFavoriteActivity();
                return true;

            case R.id.add_favorite:
                if(isFavorite) {
                    showMessageHasAlreadyAdded();
                } else {
                    Drawable normalDrawable = item.getIcon();
                    Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
                    DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorAccent));
                    presenter.executeSaveTask(this);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showErrorApi() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_movie_has_favorite, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public void showMessageHasAlreadyAdded() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinatorLayout), R.string.msg_movie_has_favorite, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            presenter.gotToBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
