package com.assetapi.stepdefinitions;

import com.assetapi.utils.ConfigReader;
import com.assetapi.utils.CredentialsReader;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyOTPAndChangePass {

    // API Request and Response
    private RequestSpecification request;
    private Response response;

    // Base URL and Endpoint
    private String BaseURL = ConfigReader.getProperty("base.url");
    private String endPoint = ConfigReader.getProperty("verify.otp.mail");

    // Authentication token
    private String token = CredentialsReader.getCredential("valid.AuthenticationToken");
    private String invalidToken = CredentialsReader.getCredential("invalid.AuthenticationToken");



}
