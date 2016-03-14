package br.com.jsantini.testzup.view.holder;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.jsantini.testzup.R;

/**
 * Created by jsantini on 12/03/16.
 * Holder para auxíliar exibição do item da lista.
 * Este padrão melhora o desempenho da lista e é obrigatório para RecycleViews
 */
public class MovieViewHolder {

    private ImageView poster;
    private TextView title;

    public MovieViewHolder(LinearLayout container) {
        poster = (ImageView) container.findViewById(R.id.img_poster);
        title = (TextView) container.findViewById(R.id.movie_item_title);

    }

    public TextView getTitle() {
        return title;
    }

    public ImageView getPoster() {
        return poster;
    }

}
