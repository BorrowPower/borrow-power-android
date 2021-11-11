package ng.borrowpower.android.Network;

import ng.borrowpower.android.Models.DisburseLoanRequest;
import ng.borrowpower.android.Models.DisburseLoanResponse;
import ng.borrowpower.android.Models.RegisterAccountRequest;
import ng.borrowpower.android.Models.RegisterAccountResponse;
import ng.borrowpower.android.Models.SendOtpRequest;
import ng.borrowpower.android.Models.SendOtpResponse;
import ng.borrowpower.android.Models.ValidateMeterRequest;
import ng.borrowpower.android.Models.ValidateMeterResponse;
import ng.borrowpower.android.Models.VerifyCustomerRequest;
import ng.borrowpower.android.Models.VerifyCustomerResponse;
import ng.borrowpower.android.Models.VerifyPartnerRequest;
import ng.borrowpower.android.Models.VerifyPartnerResponse;
import ng.borrowpower.android.Models.getRechargeTokenRequest;
import ng.borrowpower.android.Models.getRechargeTokenResponse;
import ng.borrowpower.android.Models.getUtilityServiceProviderResponse;
import ng.borrowpower.android.Models.linkMeterRequest;
import ng.borrowpower.android.Models.linkMeterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    //Verify Partner
    @POST("partners/fetchData")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<VerifyPartnerResponse> verifyPartner(@Body VerifyPartnerRequest partnerMeta);

    //Verify Customer
    @POST("verifyPhoneNumber")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<VerifyCustomerResponse> verifyCustomer(@Body VerifyCustomerRequest partnerMeta);

    //Get Discos
    @GET("getUtilityServiceProviders")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<getUtilityServiceProviderResponse> getUtilityServiceProviders();

    //Validate Request
    @POST("validateRequest")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<ValidateMeterResponse> validateMeter(@Body ValidateMeterRequest validateMeterRequest);

    //Get OTP
    @POST("agent/getOtp")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<SendOtpResponse> sendVerificationOtp(@Body SendOtpRequest validateMeterRequest);

    //Disburse Loan
    @POST("agent/disburseLoan")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<DisburseLoanResponse> disburseLoan(@Body DisburseLoanRequest disburseLoanRequest);


    //Get Recharge Loan
    @POST("getRechargeToken")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<getRechargeTokenResponse> getRechargeToken(@Body getRechargeTokenRequest getRechargeTokenRequest);

    //Get Recharge Loan
    @POST("linkMeterNumber")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<linkMeterResponse> linkMeter(@Body linkMeterRequest linkMeterRequest);

    //Create new Account
    @POST("registerCustomer")
    @Headers({"X-Requested-With:XMLHttpRequest"})
    Call<RegisterAccountResponse> registerCustomer(@Body RegisterAccountRequest registerAccountRequest);


}
