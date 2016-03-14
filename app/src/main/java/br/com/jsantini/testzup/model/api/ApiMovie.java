package br.com.jsantini.testzup.model.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;
import br.com.jsantini.testzup.model.helper.ImdbResultList;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jsantini on 10/03/16.
 * Api para comunicação com Imdb
 */
public class ApiMovie {

    private static OMDbApi omDbApi;
    //Key gerada pelo Imdb
    private static final String apiKey = "3a424cf";
    private static String URL_BASE = "http://www.omdbapi.com" ;

    //Configurando api rest retrofit
    public static OMDbApi getApi() {
        if (omDbApi == null) {

            OkHttpClient okClient = new OkHttpClient();
            //Inteceptor para add parâmetro ApiKey em toda requisição
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    HttpUrl url = chain.request().httpUrl()
                            .newBuilder()
                            .addQueryParameter("apiKey", apiKey)
                            .build();
                    Request request = chain.request().newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            });

            //Configuração do ws
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            omDbApi = client.create(OMDbApi.class);
        }
        return omDbApi ;
    }

    public interface OMDbApi {
        //Serviço para buscar filmes pelo titulo by Imdb
        @GET("/")
        Call<ImdbResultList> findMoviesByTitle(@Query("s") String title, @Query("type") String type);

        //Serviço para carregar todas as informações do filme pelo ImdbId
        @GET("/")
        Call<Movie> findMoviesByIdImdb(@Query("i") String idImdb);
    }
}
