package br.com.jsantini.testzup.model.helper;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;

/**
 * Created by jsantini on 11/03/16.
 * Objeto que corresponde ao json retornado pelo Imdb
 */
public class ImdbResultList {
    @SerializedName("Search")
    private List<Movie> search;

    private int totalResults;

    @SerializedName("Response")
    private boolean response;

    public List<Movie> getSearch() {
        return search;
    }

    public void setSearch(List<Movie> search) {
        this.search = search;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
