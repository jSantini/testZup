package br.com.jsantini.testzup.view.holder;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.jsantini.testzup.R;

/**
 * Created by jsantini on 13/03/16.
 * Holder para auxíliar exibição do item da lista.
 * Este padrão melhora o desempenho da lista e é obrigatório para RecycleViews
 */
public class MovieFavoriteHolder {
    private ImageView poster;
    private TextView title;
    private TextView genre;
    private TextView rating;
    private TextView metascore;
    private TextView votes;

    public MovieFavoriteHolder(LinearLayout container) {
        poster = (ImageView) container.findViewById(R.id.img_poster_favorite);
        title = (TextView) container.findViewById(R.id.movie_item_title_favorite);
        genre = (TextView) container.findViewById(R.id.txt_genre_favorite);
        rating = (TextView) container.findViewById(R.id.txt_rating_favorite);
        metascore = (TextView) container.findViewById(R.id.txt_metascore_favorite);
        votes = (TextView) container.findViewById(R.id.txt_votos_favorite);


    }

    public ImageView getPoster() {
        return poster;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getGenre() {
        return genre;
    }

    public TextView getRating() {
        return rating;
    }

    public TextView getMetascore() {
        return metascore;
    }

    public TextView getVotes() {
        return votes;
    }
}
