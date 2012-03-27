package org.scribe.examples;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class FacebookExampleToken
{
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";

	public static void main(String[] args) throws MalformedURLException, ParseException, JSONException
	{
		// Replace these with your own api key and secret
		String apiKey = "xxx";
		String apiSecret = "yyy";
		FacebookApi api = new FacebookApi();
		ServiceBuilder service =  new ServiceBuilder()										
										.apiKey(apiKey)
										.apiSecret(apiSecret)
										.callback("http://afternoon-galaxy-4740.herokuapp.com/hello")       
										.responseType("token");
		Scanner in = new Scanner(System.in);

		String authorizationUrl = api.getAuthorizationUrl(service);
		System.out.println("Authorization URL!");
		System.out.println(authorizationUrl);
		System.out.println("Paste the auth code here");
		System.out.print(">>");
		String accessToken = in.nextLine();
		System.out.println();


		// Now let's go and ask for a protected resource!
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		request.addQuerystringParameter("access_token", accessToken);
		Response response = request.send();
		String fbBody = response.getBody();
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(fbBody);
		
		try {
			JSONObject object = (JSONObject) new JSONTokener(fbBody).nextValue();
			String name = object.getString("name");
			System.out.println(name);
			String bio = object.getString("bio");
			System.out.println(bio);
		} catch (JSONException e) {
		}			
	}
}