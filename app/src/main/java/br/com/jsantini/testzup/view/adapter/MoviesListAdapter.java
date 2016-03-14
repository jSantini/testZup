package br.com.jsantini.testzup.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.jsantini.testzup.R;
import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.view.holder.MovieViewHolder;

/**
 * Created by jsantini on 12/03/16.
 */
public class MoviesListAdapter extends BaseAdapter {

    private List<Movie> movieList;
    private final LayoutInflater mLayoutInflater;
    private Context mContext;

    public MoviesListAdapter(Activity activity) {
        mLayoutInflater = LayoutInflater.from(activity.getApplicationContext());
    }

    public MoviesListAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    //retornar id
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.movie_item_list, parent, false);
            convertView.setTag(new MovieViewHolder((LinearLayout) convertView));
        }
        MovieViewHolder holder = (MovieViewHolder) convertView.getTag();
        Movie movie = (Movie) getItem(position);
        holder.getTitle().setText(movie.getTitle() + " (" + movie.getYear() + ")");
        Picasso.with(mContext)
                .load(movie.getPoster())
                .placeholder(R.drawable.place_holder_image_128x128)
                .into(holder.getPoster());
        return convertView;
    }
}
