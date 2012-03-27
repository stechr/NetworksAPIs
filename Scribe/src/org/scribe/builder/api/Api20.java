package org.scribe.builder.api;

import org.scribe.builder.ServiceBuilder;

public interface Api20
{

	public String scope = null;
	public String responseType = null;

	/**
	 * Returns the URL where you should redirect your users to authenticate
	 * your application.
	 *
	 * @param config OAuth 2.0 configuration param object
	 * @return the URL where you should redirect your users
	 */

	public String getAuthorizationUrl(ServiceBuilder service);
	public String getAccessTokenUrl(ServiceBuilder service, String authCodeToken);

}
