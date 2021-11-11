package ng.borrowpower.android.Core.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import moe.feng.common.stepperview.VerticalStepperItemView;
import ng.borrowpower.android.Listeners.onFailed;
import ng.borrowpower.android.Listeners.onSuccess;
import ng.borrowpower.android.Models.DisburseLoanRequest;
import ng.borrowpower.android.Models.DisburseLoanResponse;
import ng.borrowpower.android.Models.Discos;
import ng.borrowpower.android.Models.SendOtpRequest;
import ng.borrowpower.android.Models.SendOtpResponse;
import ng.borrowpower.android.Models.ValidateMeterRequest;
import ng.borrowpower.android.Models.ValidateMeterResponse;
import ng.borrowpower.android.Models.VerifyCustomerRequest;
import ng.borrowpower.android.Models.VerifyCustomerResponse;
import ng.borrowpower.android.Models.getRechargeTokenRequest;
import ng.borrowpower.android.Models.getRechargeTokenResponse;
import ng.borrowpower.android.Models.getUtilityServiceProviderResponse;
import ng.borrowpower.android.Models.linkMeterRequest;
import ng.borrowpower.android.Models.linkMeterResponse;
import ng.borrowpower.android.Network.ApiService;
import ng.borrowpower.android.Network.RetrofitInstance;
import ng.borrowpower.android.R;
import ng.borrowpower.android.Utils.Loader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment extends BottomSheetDialogFragment {
    String partnerMeta;
    onSuccess onSuccess;
    onFailed onFailed;
    String environment,discoSlug;
    VerifyCustomerResponse customerData;
    List<Discos> discos;
    boolean changeMeterState;
    private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[4];

//    views
    View view;
    Toolbar toolbar;
    EditText phoneNumber, meterNumber,amount, loanAmount, meterNumberConfirm,otp, transactionPin;
    Button proceed_1,proceed_2,editLoanDetails,disburseLoan,cancelMeterValidation;
    TextView createAccount,changeMeterNumber, stdToken,stdAmount,stdMeter,stdUnit;
    Spinner discosSpinner;
    LinearLayout discoContainer,amountContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.borrowpower);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.base_activity, container, false);
        initializeUserInterface();
        fetchDiscos(discosSpinner);
        proceed_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber.getText().toString().isEmpty()){
                    phoneNumber.setError("Required");
                }else {
                    doVerifyInput(phoneNumber);
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Registration(environment, partnerMeta, phoneNumber.getText().toString()).show(getChildFragmentManager(), "register");
                createAccount.setVisibility(View.VISIBLE);
            }
        });

        editLoanDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[2].prevStep();

            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFailed.onFailed("loan process terminated");
                dismiss();
            }
        });

        changeMeterNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleContainers();

            }
        });

        cancelMeterValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[1].prevStep();
            }
        });
        proceed_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[2].setErrorColor(Color.RED);
                mSteppers[2].setErrorText(null);
                if (amountContainer.getVisibility() == View.VISIBLE){
                    changeMeterState = true;
                    changeMeterNumber.setVisibility(View.GONE);
                    validateMeterNumberAndProceed();
                }   else {
                    //change Meter before proceeding
                    changeMeterState = false;
                    changeMeterNumber();
                }
            }
        });

        disburseLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp.getText().toString().isEmpty()){
                    otp.setError("Required");
                }else {
                    Loader loader = new Loader();
                    loader.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "Loader");
                    ApiService apiService = RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
                    DisburseLoanRequest loanRequest = new DisburseLoanRequest(phoneNumber.getText().toString(),loanAmount.getText().toString().trim().replace("₦", ""), partnerMeta, otp.getText().toString());
                    Log.d("BorrowPower",  new Gson().toJson(loanRequest));

                    apiService.disburseLoan(loanRequest).enqueue(new Callback<DisburseLoanResponse>() {
                        @Override
                        public void onResponse(Call<DisburseLoanResponse> call, Response<DisburseLoanResponse> response) {
                            Log.d("BorrowPower",  response.body().getMsg());

                            if (response.code() == 200){
                                if (response.body().isStatus()){
                                   //get Recharge token
                                    loader.dismiss();
                                    getRechargeToken(response.body().getData().getPaymentReference(), discoSlug.trim().toUpperCase());
                                }else{
                                    loader.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<DisburseLoanResponse> call, Throwable t) {
                            loader.dismiss();
                            Log.d("BorrowPower", t.getMessage());
                            if(t.getCause() instanceof SocketTimeoutException){
                                onFailed.onFailed("A network Exception has occurred");
                            } else {
                                onFailed.onFailed("A network Exception has occurred");
                            }
                        }
                    });
                }
            }
        });
        return view;
    }

    private void getRechargeToken(String ref, String disco) {
        //get token then show Dialog
        Loader loader = new Loader();
        loader.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "Loader");
        ApiService apiService = RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        getRechargeTokenRequest rechargeTokenRequest = new getRechargeTokenRequest(ref,disco);
        apiService.getRechargeToken(rechargeTokenRequest).enqueue(new Callback<getRechargeTokenResponse>() {
            @Override
            public void onResponse(Call<getRechargeTokenResponse> call, Response<getRechargeTokenResponse> response) {
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        //get Recharge token
                        mSteppers[2].nextStep();
                        loader.dismiss();
                        stdAmount.setText(Utils.getCurrencySymbol("NGN")+amount.getText().toString());
                        stdToken.setText(response.body().getData().getStdToken());
                        stdUnit.setText(response.body().getData().getUnits());
                        stdMeter.setText(meterNumber.getText().toString());
                    }else{
                        mSteppers[2].prevStep();
                        mSteppers[2].setErrorColor(Color.RED);
                        mSteppers[2].setErrorText(response.body().getMsg());
                        onFailed.onFailed(response.body().getMsg());
                        loader.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<getRechargeTokenResponse> call, Throwable t) {
                loader.dismiss();
                Log.d("BorrowPower", t.getMessage());
                onFailed.onFailed("A network Exception has occurred");
            }
        });

    }



    private void toggleContainers() {
        if (discoContainer.getVisibility() == View.VISIBLE){
            amountContainer.setVisibility(View.VISIBLE);
            discoContainer.setVisibility(View.GONE);
            meterNumber.setEnabled(false);
            changeMeterState = true;
            changeMeterNumber.setText("Change Meter Number");

        }  else {
            amountContainer.setVisibility(View.GONE);
            discoContainer.setVisibility(View.VISIBLE);
            meterNumber.setEnabled(true);
            changeMeterNumber.setText("Use Current Meter Number");
            changeMeterState = false;
        }
    }


    private void proceedCustomerVerification() {
        Loader loader = new Loader();
        loader.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "Loader");
        ApiService apiService= RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        VerifyCustomerRequest verifyCustomerRequest = new VerifyCustomerRequest(phoneNumber.getText().toString().trim(),partnerMeta);
        apiService.verifyCustomer(verifyCustomerRequest).enqueue(new Callback<VerifyCustomerResponse>() {
            @Override
            public void onResponse(Call<VerifyCustomerResponse> call, Response<VerifyCustomerResponse> response) {
                loader.dismiss();
                Log.d("BorrowPower", response.body().getMsg());
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        //Proceed to Next Step
                        mSteppers[0].nextStep();
                        customerData = response.body();
                        setupCustomerData(customerData);
                    }else {
                        createAccount.setVisibility(View.VISIBLE);
                    }
                }else {
                    onFailed.onFailed("A network Exception has occurred");
                }
            }

            @Override
            public void onFailure(Call<VerifyCustomerResponse> call, Throwable t) {
                loader.dismiss();
                if(t.getCause() instanceof SocketTimeoutException){
                    onFailed.onFailed("A network Exception has occured");
                } else {
                    onFailed.onFailed("A network Exception has occured");
                }
            }
        });
    }


    private void validateMeterNumberAndProceed() {
        Loader loader = new Loader();
        loader.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "Loader");
        ApiService apiService = RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        Discos discos = (Discos) discosSpinner.getSelectedItem();
        discoSlug = discos.getSlug().toUpperCase();
        ValidateMeterRequest validateMeterRequest =
                new ValidateMeterRequest(
                        meterNumber.getText().toString().trim(),
                        phoneNumber.getText().toString().trim(),
                        partnerMeta, amount.getText().toString(),
                        discoSlug);
        apiService.validateMeter(validateMeterRequest).enqueue(new Callback<ValidateMeterResponse>() {
            @Override
            public void onResponse(Call<ValidateMeterResponse> call, Response<ValidateMeterResponse> response) {
                loader.dismiss();
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        //Proceed to Next Step

                        SendOtpRequest  otpRequest = new SendOtpRequest(partnerMeta, phoneNumber.getText().toString(), transactionPin.getText().toString());
                        apiService.sendVerificationOtp(otpRequest).enqueue(new Callback<SendOtpResponse>() {
                            @Override
                            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                                changeMeterNumber.setVisibility(View.VISIBLE);
                                if (response.code() == 200){
                                    if (response.body().isStatus()){
                                        mSteppers[1].nextStep();
                                        meterNumberConfirm.setText(meterNumber.getText().toString().toString());
                                        loanAmount.setText(Utils.getCurrencySymbol("NGN")+amount.getText().toString().trim());
                                    }else {
                                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }   else {
                                    onFailed.onFailed("A network Exception has occurred");
                                }
                            }

                            @Override
                            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                                if(t.getCause() instanceof SocketTimeoutException){
                                    onFailed.onFailed("A network Exception has occured");
                                } else {
                                    onFailed.onFailed("A network Exception has occured");
                                }
                            }
                        });

                    }else {
                        changeMeterNumber.setText(response.body().getMsg() + " " + " or Tap to Change");
                        changeMeterNumber.setTextColor(Color.RED);
                    }
                }else {
                    onFailed.onFailed("A network Exception has occurred");
                }
            }

            @Override
            public void onFailure(Call<ValidateMeterResponse> call, Throwable t) {
                loader.dismiss();
                if(t.getCause() instanceof SocketTimeoutException){
                    onFailed.onFailed("A network Exception has occurred");
                } else {
                    onFailed.onFailed("A network Exception has occurred");
                }
            }
        });
    }


    public void changeMeterNumber(){
        Loader loader = new Loader();
        loader.show(((FragmentActivity)getContext()).getSupportFragmentManager(), "Loader");
        ApiService apiService = RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        linkMeterRequest linkMeterRequest = new linkMeterRequest(phoneNumber.getText().toString(), partnerMeta, discoSlug.trim().toUpperCase(), meterNumber.getText().toString());
        Log.d("BorrowPower", new Gson().toJson(linkMeterRequest));
        apiService.linkMeter(linkMeterRequest).enqueue(new Callback<linkMeterResponse>() {
            @Override
            public void onResponse(Call<linkMeterResponse> call, Response<linkMeterResponse> response) {
                Log.d("BorrowPower", new Gson().toJson(response.body()));
                if (response.code() == 200){
                    if (response.body().isStatus()){
                        //Proceed to Next Step
                        SendOtpRequest  otpRequest = new SendOtpRequest(partnerMeta, phoneNumber.getText().toString(), transactionPin.getText().toString());
                        apiService.sendVerificationOtp(otpRequest).enqueue(new Callback<SendOtpResponse>() {
                            @Override
                            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                                changeMeterNumber.setVisibility(View.VISIBLE);
                                loader.dismiss();
                                if (response.code() == 200){
                                    if (response.body().isStatus()){
                                        mSteppers[1].nextStep();
                                        meterNumberConfirm.setText(meterNumber.getText().toString().toString());
                                        loanAmount.setText(Utils.getCurrencySymbol("NGN")+amount.getText().toString().trim());
                                    }else {
                                        Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }   else {
                                    onFailed.onFailed("A network Exception has occurred");
                                }
                            }

                            @Override
                            public void onFailure(Call<SendOtpResponse> call, Throwable t) {
                                if(t.getCause() instanceof SocketTimeoutException){
                                    onFailed.onFailed("A network Exception has occured");
                                } else {
                                    onFailed.onFailed("A network Exception has occured");
                                }
                            }
                        });

                    }else {
                        changeMeterNumber.setText(response.body().getMsg());
                        changeMeterNumber.setTextColor(Color.RED);
                    }
                }
            }

            @Override
            public void onFailure(Call<linkMeterResponse> call, Throwable t) {
                loader.dismiss();
                if(t.getCause() instanceof SocketTimeoutException){
                    onFailed.onFailed("A network Exception has occurred");
                } else {
                    onFailed.onFailed("A network Exception has occurred");
                }
            }
        });
    }


    private void setupCustomerData(VerifyCustomerResponse customerData) {
        if (customerData.getData().isHasMeter()){
            meterNumber.setText(customerData.getData().getMeterNumber());
        }else{
            amountContainer.setVisibility(View.GONE);
            discoContainer.setVisibility(View.VISIBLE);
            meterNumber.setEnabled(true);
            changeMeterNumber.setText("Use Current Meter Number");
            changeMeterState = false;
        }
    }


    public void fetchDiscos(Spinner spinner){
        ApiService apiService= RetrofitInstance.getRetrofitClient(environment).create(ApiService.class);
        apiService.getUtilityServiceProviders().enqueue(new Callback<getUtilityServiceProviderResponse>() {
            @Override
            public void onResponse(Call<getUtilityServiceProviderResponse> call, Response<getUtilityServiceProviderResponse> response) {
                discos = new ArrayList<>();
                if (response.code()== 200){
                    discos.addAll(response.body().getData());
                    setupDiscos(discos, spinner);
                }else{
                    onFailed.onFailed("A network Exception has occurred");
                }
            }

            @Override
            public void onFailure(Call<getUtilityServiceProviderResponse> call, Throwable t) {

            }
        });

    }

    private void setupDiscos(List<Discos> discos, Spinner spinner) {
        ArrayAdapter<Discos> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, discos);
        spinner.setAdapter(spinnerArrayAdapter);
        Discos disco = (Discos) discosSpinner.getSelectedItem();
        discoSlug = disco.getSlug().toUpperCase();
    }

    private void initializeUserInterface() {
        phoneNumber = view.findViewById(R.id.phone_number);
        proceed_1 = view.findViewById(R.id.proceed_1);
        createAccount = view.findViewById(R.id.create_account);
        discosSpinner = view.findViewById(R.id.disco);
        meterNumber = view.findViewById(R.id.meter_number);
        amount = view.findViewById(R.id.amount);
        changeMeterNumber = view.findViewById(R.id.change_meter);
        discoContainer = view.findViewById(R.id.discoContainer);
        amountContainer = view.findViewById(R.id.amountContainer);
        proceed_2 = view.findViewById(R.id.proceed_2);
        editLoanDetails = view.findViewById(R.id.edit_loan);
        disburseLoan = view.findViewById(R.id.disburse);
        cancelMeterValidation = view.findViewById(R.id.cancelMeterValidation);
        loanAmount = view.findViewById(R.id.loan_amount);
        meterNumberConfirm = view.findViewById(R.id.meter_number_2);
        otp = view.findViewById(R.id.otp);
        transactionPin = view.findViewById(R.id.transaction_pin);
        stdToken = view.findViewById(R.id.std_token);
        stdAmount = view.findViewById(R.id.std_amount);
        stdMeter = view.findViewById(R.id.std_meter);
        stdUnit = view.findViewById(R.id.std_unit);
        toolbar = view.findViewById(R.id.toolbar);


        mSteppers[0] = view.findViewById(R.id.stepper_0);
        mSteppers[1] = view.findViewById(R.id.stepper_1);
        mSteppers[2] = view.findViewById(R.id.stepper_2);
        mSteppers[3] = view.findViewById(R.id.stepper_3);
        VerticalStepperItemView.bindSteppers(mSteppers);
    }

    public BaseFragment(String partnerMeta,  onFailed onFailed, onSuccess onSuccess, String environment) {
        this.partnerMeta = partnerMeta;
        this.onSuccess = onSuccess;
        this.onFailed = onFailed;
        this.environment = environment;
    }

    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels ;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupFullHeight(bottomSheetDialog);
            }
        });
        return  dialog;
    }

    private void doVerifyInput(EditText phoneNumber) {
        if(phoneNumber.getText().length()< 11){
            phoneNumber.setError("Must be 11 Digits");
        }else{
            createAccount.setVisibility(View.GONE);
            proceedCustomerVerification();
        }
    }


    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }

        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }
    }

}
