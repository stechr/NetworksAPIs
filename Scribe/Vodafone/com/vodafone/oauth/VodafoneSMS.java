package com.vodafone.oauth;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.ParseException;

import org.json.JSONException;
import org.scribe.model.Request;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class VodafoneSMS {

	public static void main(String[] args) throws MalformedURLException, ParseException, JSONException
	{
		String PROTECTED_RESOURCE_URL = "http://api.developer.vodafone.com/v2/smsmessaging/outbound/tel:441234567/requests";

		System.out.println("Now we're going to access a protected resource...");
		Request req = new Request(Verb.POST, PROTECTED_RESOURCE_URL);
		req.addBodyParameter("message", "Hi");
		req.addBodyParameter("address", URLEncoder.encode("tel:447999999999"));
		req.addBodyParameter("key", "xxx");
		Response response = req.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

	}
}
