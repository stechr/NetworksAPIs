package org.scribe.builder.api;

import org.scribe.builder.ServiceBuilder;
import org.scribe.utils.URLUtils;

public class FacebookApi implements Api20
{
	@Override
	public String getAuthorizationUrl(ServiceBuilder service)
	{
		final String AUTHORIZE_URL = "https://graph.facebook.com/oauth/authorize?response_type=%s&client_id=%s&redirect_uri=%s&scope=%s";	
		return String.format(AUTHORIZE_URL, service.getResponseType(), service.getApiKey(), 
				URLUtils.urlEncodeWrapper(service.getCallback()), service.getScope());
	}

	@Override
	public String getAccessTokenUrl(ServiceBuilder service, String authCodeToken) {
	    String accesstokenURL = "https://graph.facebook.com/oauth/access_token?" + 
	    	    "client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";
	    return String.format(accesstokenURL, service.getApiKey(), URLUtils.urlEncodeWrapper(service.getCallback()), 
	    		service.getApiSecret(), authCodeToken);		
	}
}
