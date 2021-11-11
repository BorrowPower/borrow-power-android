package ng.borrowpower.android.Core.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;

import ng.borrowpower.android.Models.RegisterAccountRequest;
import ng.borrowpower.android.Models.RegisterAccountResponse;
import ng.borrowpower.android.Network.ApiService;
import ng.borrowpower.android.Network.RetrofitInstance;
import ng.borrowpower.android.R;
import ng.borrowpower.android.Utils.Loader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends BottomSheetDialogFragment {
    View view;
    TextView regText, errorText;
    Button proceed,close;
    EditText f_name, l_name, transaction_pin,phone;
    LinearLayout successLayout, registerLayout;

    String environment,partnerMeta,phoneNumber;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.SheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_fragment, container, false);
        initializeInterface();


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyDetails()){
                    createAccount();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    private void initializeInterface() {
        regText = view.findViewById(R.id.reg_text);
        errorText = view.findViewById(R.id.errorText);
        proceed = view.findViewById(R.id.proceed_1);
        close = view.findViewById(R.id.proceed_2);
        f_name = view.findViewById(R.id.first_name);
        l_name = view.findViewById(R.id.last_name);
        transaction_pin = view.findViewById(R.id.transaction_pin);
        successLayout = view.findViewById(R.id.register_success);
        registerLayout = view.findViewById(R.id.linearLayout4);
        phone = view.findViewById(R.id.phone);
        if (phoneNumber !=null){
            phone.setText(phoneNumber);
        }
    }

    public void createAccount(){
        errorText.setVisibility(View.GONE);
        Loader loader = new Loader();
        loader.show(getChildFragmentManager(), "loader");
        ApiService apiService = RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        RegisterAccountRequest registerAccountRequest = new RegisterAccountRequest(transaction_pin.getText().toString(),phone.getText().toString(),f_name.getText().toString(),l_name.getText().toString(),partnerMeta);
        Log.d("BorrowPower", new Gson().toJson(registerAccountRequest));
        apiService.registerCustomer(registerAccountRequest).enqueue(new Callback<RegisterAccountResponse>() {
            @Override
            public void onResponse(Call<RegisterAccountResponse> call, Response<RegisterAccountResponse> response) {
                loader.dismiss();
                Log.d("BorrowPower", response.body().getMsg());
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        registerLayout.setVisibility(View.GONE);
                        successLayout.setVisibility(View.VISIBLE);
                        regText.setVisibility(View.GONE);
                        proceed.setVisibility(View.GONE);
                    }else{
                        errorText.setVisibility(View.VISIBLE);
                    }
                }else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(R.string.no_internet);
                }
            }

            @Override
            public void onFailure(Call<RegisterAccountResponse> call, Throwable t) {
                loader.dismiss();
                Log.d("BorrowPower", t.getMessage());
                errorText.setVisibility(View.VISIBLE);
                if(t.getCause() instanceof SocketTimeoutException){
                    errorText.setText(R.string.no_internet);
                } else {
                    errorText.setText(R.string.phone_in_use);
                }
            }
        });
    }

    public Registration(String environment, String partnerMeta, String phone){
        this.environment = environment;
        this.partnerMeta = partnerMeta;
        this.phoneNumber = phone;
    }

    public boolean verifyDetails(){
        boolean verified;
        if (f_name.getText().toString().isEmpty()){
            verified = false;
            f_name.setError("Required");
        } else if(l_name.getText().toString().isEmpty()){
            verified = false;
            l_name.setError("Required");
        }else if(phone.getText().toString().isEmpty()){
            verified = false;
            phone.setError("Required");

        }else if(transaction_pin.getText().toString().isEmpty()){
            verified = false;
            transaction_pin.setError("Required");
        }else {
            verified = true;
        }
        return  verified;
    }
}
