package ng.borrowpower.android.Network;

import static ng.borrowpower.android.Utils.Constants.BASE_URL_PROD;
import static ng.borrowpower.android.Utils.Constants.BASE_URL_TEST;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static Retrofit retrofit;

    public static  Retrofit getRetrofitClient(String environment){
        switch (environment){
            case "LIVE":
                retrofit= new Retrofit.Builder()
                        .baseUrl(BASE_URL_PROD)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
            case "SANDBOX":
                retrofit= new Retrofit.Builder()
                        .baseUrl(BASE_URL_TEST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
        }
        return retrofit;
    };
}