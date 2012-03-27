package com.vodafone.oauth;

import java.net.MalformedURLException;
import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class VodafoneChargingToken {

	private static final String PROTECTED_RESOURCE_URL = "http://api.developer.vodafone.com/v2/location/queries/location?address=tel%3A447999999999&requestedAccuracy=1500";

	public static void main(String[] args) throws MalformedURLException	{

		String redirect_uri = "http://www.example/";
		String scope = "POST-/payment/acr:Authorization/transactions/amount";
		String client_id = "xxx";
		String responseType = "token";
		VodafoneAPI api = new VodafoneAPI();
		ServiceBuilder service = new ServiceBuilder()
		.apiKey(client_id)
		.callback(redirect_uri)
		.scope(scope)
		.responseType(responseType);

		Scanner in = new Scanner(System.in);
		System.out.println();

		//	    Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		System.out.println("Consent Screen URL. Copy and paste the following URL on a browser. Provide consent");
		String authorizationUrl = api.getAuthorizationUrl(service);
		System.out.println(authorizationUrl);
		System.out.println("And paste access token here");
		System.out.print(">>");
		String token = in.nextLine();
		System.out.println();

		// Now let's go and ask for a protected resource!
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_URL);
		request.addBodyParameter("onBehalfOf", "myCoolApp_Developer");
		request.addBodyParameter("currency", "en_GB");
		request.addBodyParameter("description", "cheap and cool app");
		request.addBodyParameter("contentName", "myCoolApp");
		request.addBodyParameter("referenceCode", "Transaction-1234");
		request.addBodyParameter("amount", "20");
		request.addHeader("Authorization", "OAuth "+token);
		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());
	}
}
