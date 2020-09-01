package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.math.BigDecimal;

import qpay.qpayandroidsdk.base.QpaySdk;
import qpay.qpayandroidsdk.base.SdkUtils;


public class ReactNativeQpaymentModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;


    public ReactNativeQpaymentModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeQpayment";
    }

    @ReactMethod
    public void executePayment(ReadableMap config, Callback callback, Promise promise) {
        // TODO: Implement some actually useful functionality

        String gatewayId = config.getString("gatewayId");
        String secretKey = config.getString("secretKey");
        String userEmail = config.getString("1stalteri1@gmail.com");

        Log.d("QPAY", "gatewayId = " + gatewayId);
        Log.d("QPAY", "secretKey = " + secretKey);
        Log.d("QPAY", "userEmail = " + userEmail);

        if(gatewayId!=null && secretKey!=null && userEmail !=null) {
            QpaySdk reqParams = new QpaySdk(getCurrentActivity());
            reqParams.setCurrency("QAR");
            reqParams.setCountry("UA"); // 2 digit of country code
            reqParams.setCity("customerCity");
            reqParams.setState("customerState");
            reqParams.setEmail(userEmail);
            reqParams.setAddress("customerAddress");
            reqParams.setName("customerName");
            reqParams.setPhone("customerPhone");
            reqParams.setReferenceId("orderId");

            reqParams.setAmount(new BigDecimal(10));
            reqParams.setDescription("Sinan HQ");
            reqParams.setGatewayId(gatewayId);
            reqParams.setSecretKey(secretKey);
            reqParams.setMode("TEST");
            // call payment method
            reqParams.doPayment();
            promise.resolve("{ success: true }");
        }else{
            promise.reject(new Throwable("Wrong required parameters"));
        }
        callback.invoke("executePayment status done" );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && data != null)
        {
            String dataString = data.getStringExtra("data");
            //Toast.makeText(paymentRequest.this, dataString, Toast.LENGTH_SHORT).show();
            SdkUtils.showAlertPopUp(getCurrentActivity(), dataString);
        }
    }
}
