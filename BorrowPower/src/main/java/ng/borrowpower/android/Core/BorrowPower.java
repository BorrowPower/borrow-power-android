package ng.borrowpower.android.Core;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import ng.borrowpower.android.Core.Fragments.BaseFragment;
import ng.borrowpower.android.Utils.Loader;
import ng.borrowpower.android.Listeners.onFailed;
import ng.borrowpower.android.Listeners.onSuccess;
import ng.borrowpower.android.Models.VerifyPartnerRequest;
import ng.borrowpower.android.Models.VerifyPartnerResponse;
import ng.borrowpower.android.Network.ApiService;
import ng.borrowpower.android.Network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowPower {
    Context context;
    String partnerMeta;
    String environment;
    //Add parameter errors to a List
    List<String> paramErrors = new ArrayList<>();


    public BorrowPower with(String partnerMeta, String environment){
        this.partnerMeta = partnerMeta;
        this.environment = environment;
        return this;
    }

    public void build(Context context, onSuccess onSuccess, onFailed onFailed){
        if (verified()){
            initSDK(onSuccess, onFailed, context);
        }else {
            StringBuilder sb=new StringBuilder("The following errors have occurred").append("\n");
            for (int i = 0; i < paramErrors.size(); i++) {
                sb.append(paramErrors.get(i)).append("\n");
            }
            throw  new RuntimeException(sb.toString());
        }
    }

    private void initSDK(onSuccess onSuccess,  onFailed onFailed, Context context) {
        Loader loader = new Loader();
        loader.show(((FragmentActivity)context).getSupportFragmentManager(), "Loader");
        ApiService apiService= RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        VerifyPartnerRequest verifyPartnerRequest = new VerifyPartnerRequest(partnerMeta);
        Call<VerifyPartnerResponse> verifyPartnerResponseCall = apiService.verifyPartner(verifyPartnerRequest);
        verifyPartnerResponseCall.enqueue(new Callback<VerifyPartnerResponse>() {
            @Override
            public void onResponse(Call<VerifyPartnerResponse> call, Response<VerifyPartnerResponse> response) {
                loader.dismiss();
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        new BaseFragment(partnerMeta, onFailed, onSuccess, environment).show(((FragmentActivity)context).getSupportFragmentManager(), "HomeScreen");
                    }else {
                        onFailed.onFailed("Invalid Partner Key Provided");
                    }
                }else{
                    onFailed.onFailed("A network Exception has occurred");
                }
            }

            @Override
            public void onFailure(Call<VerifyPartnerResponse> call, Throwable t) {
                loader.dismiss();
                if(t.getCause() instanceof SocketTimeoutException){
                    onFailed.onFailed("A network Exception has occured");
                } else {
                    onFailed.onFailed("A network Exception has occured");
                }
            }
        });

    }

    private boolean verified() {
        boolean verified;
        if (partnerMeta.equalsIgnoreCase("") || partnerMeta.isEmpty()){
            paramErrors.add("Partner Key not Provided");
            verified = false;
        }   else {
            verified = true;
        }
        return verified;
    }
}
