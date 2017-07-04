package br.ipti.org.apptag.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import br.ipti.org.apptag.models.CredentialsReturn;
import br.ipti.org.apptag.models.SchoolReportReturn;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Filipi Andrade on 04-Jul-17.
 */

public class TAGAPI {

    private static final String BASE_URL = "http://10.0.2.2:9090/api-tag/";
    private static TAGInterfaceAPI mTagInterfaceAPI;

    public static TAGInterfaceAPI getClient() {
        try {
            if (mTagInterfaceAPI == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                return chain.proceed(chain.request());
                            }
                        }).build();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                mTagInterfaceAPI = retrofit.create(TAGInterfaceAPI.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTagInterfaceAPI;
    }

    public interface TAGInterfaceAPI {
        // --------------- USERS ------------------ //
        @FormUrlEncoded
        @POST("login")
        Call<ArrayList<CredentialsReturn>> getCredentials(@Field("username") String username, @Field("password") String password);

        // --------------- STUDENT ------------------ //
        @GET("student/parent/{responsable_cpf}/{send_year}")
        Call<ArrayList<SchoolReportReturn>> getStudentParent(@Path("responsable_cpf") String responsable_cpf,
                                                             @Path("send_year") String send_year);
    }
}
