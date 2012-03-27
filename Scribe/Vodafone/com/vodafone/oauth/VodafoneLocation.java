package com.vodafone.oauth;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Request;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class VodafoneLocation {
  
  private static final String PROTECTED_RESOURCE_URL = "http://api.developer.vodafone.com/v2/location/queries/location?address=tel%3A447999999999&requestedAccuracy=1500";
  
  public static void main(String[] args) throws MalformedURLException, ParseException, JSONException
  {

	String redirect_uri = "http://www.example/";
	String scope = "GET-/location/queries/location";
    String apiKey = "xxx";
    String responseType = "code";
    VodafoneAPI api = new VodafoneAPI();
    ServiceBuilder service =  new ServiceBuilder()
                                  .apiKey(apiKey)
                                  .callback(redirect_uri)
                                  .scope(scope)
                                  .responseType(responseType);
    
    Scanner in = new Scanner(System.in);
    System.out.println();

//    Obtain the Authorization URL
    System.out.println("Fetching the Authorization URL...");
    System.out.println("Consent Screen URL. Copy and paste the following URL on a browser. Provide consent");
    String authorizationUrl = api.getAuthorizationUrl(service);    
    System.out.println(authorizationUrl);    
    System.out.println("Locate code=xxx, copy and paste that value here...");
    System.out.print(">>");    
    String authCode = in.nextLine();
    System.out.println();
    
//    Obtain the access token URL
    System.out.println("Fetching the access token URL...");
    String accessTokenURL = api.getAccessTokenUrl(service, authCode);
    System.out.println("<INFO> access token URL...");
    System.out.println(accessTokenURL);
    Request req = new Request(Verb.POST, accessTokenURL);
    req.addBodyParameter("redirect_uri", redirect_uri);
    req.addBodyParameter("grant_type", "authorization_code");
    req.addBodyParameter("code", authCode);
    Response resp = req.send();
    String body = resp.getBody();
	JSONObject object = (JSONObject) new JSONTokener(body).nextValue();
	String accessToken = object.getString("access_token");  
    
    // Now let's go and ask for a protected resource!
    System.out.println("Now we're going to access a protected resource...");
    OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
    request.addHeader("Authorization", "OAuth "+accessToken);
    Response response = request.send();
    System.out.println("Got it! Lets see what we found...");
    System.out.println();
    System.out.println(response.getCode());
    System.out.println(response.getBody());

  }
}