package org.scribe.builder;

import org.scribe.model.OAuthConstants;
import org.scribe.utils.Preconditions;

public class ServiceBuilder
{
	private String apiKey;
	private String apiSecret;
	private String callback;	
	private String scope;
	private String responseType;

	/**
	 * Default constructor
	 */
	public ServiceBuilder()
	{
		this.callback = OAuthConstants.OUT_OF_BAND;
	}

	/**
	 * Adds an OAuth callback url
	 * 
	 * @param callback callback url. Must be a valid url or 'oob' for out of band OAuth
	 * @return the {@link ServiceBuilder} instance for method chaining
	 */
	public ServiceBuilder callback(String callback)
	{
		Preconditions.checkValidOAuthCallback(callback, "Callback must be a valid URL or 'oob'");
		this.callback = callback;
		return this;
	}

	public ServiceBuilder responseType(String responseType)
	{
		Preconditions.checkResponseType(responseType, "ResponseType must be a valid String");
		this.responseType = responseType;
		return this;
	}  

	/**
	 * Configures the api key
	 * 
	 * @param apiKey The api key for your application
	 * @return the {@link ServiceBuilder} instance for method chaining
	 */
	public ServiceBuilder apiKey(String apiKey)
	{
		this.apiKey = apiKey;
		return this;
	}

	/**
	 * Configures the api secret
	 * 
	 * @param apiSecret The api secret for your application
	 * @return the {@link ServiceBuilder} instance for method chaining
	 */
	public ServiceBuilder apiSecret(String apiSecret)
	{
		this.apiSecret = apiSecret;
		return this;
	}

	/**
	 * Configures the OAuth scope. This is only necessary in some APIs (like Google's).
	 * 
	 * @param scope The OAuth scope
	 * @return the {@link ServiceBuilder} instance for method chaining
	 */
	public ServiceBuilder scope(String scope)
	{
		Preconditions.checkEmptyString(scope, "Invalid OAuth scope");
		this.scope = scope;
		return this;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public String getCallback() {
		return callback;
	}

	public String getScope() {
		return scope;
	}

	public String getResponseType() {
		return responseType;
	}


}
