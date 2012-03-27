package org.scribe.utils;

import java.io.*;
import java.net.*;
import java.util.*;

import org.scribe.exceptions.OAuthException;

/**
 * Utils to deal with URL and url-encodings
 *
 * @author Pablo Fernandez
 */
public class URLUtils
{
  private static final String EMPTY_STRING = "";
  private static final String UTF_8 = "UTF-8";
  private static final char PAIR_SEPARATOR = '=';
  private static final char PARAM_SEPARATOR = '&';
  private static final char QUERY_STRING_SEPARATOR = '?';
  private static final String HTTP_PROTOCOL = "http";
  private static final int HTTP_DEFAULT_PORT = 80;
  private static final String HTTPS_PROTOCOL = "https";
  private static final int HTTPS_DEFAULT_PORT = 443;

  private static final String ERROR_MSG = String.format("Cannot find specified encoding: %s", UTF_8);

  private static final Set<EncodingRule> ENCODING_RULES;

  static
  {
    Set<EncodingRule> rules = new HashSet<EncodingRule>();
    rules.add(new EncodingRule("*","%2A"));
    rules.add(new EncodingRule("+","%20"));
    rules.add(new EncodingRule("%7E", "~"));
    ENCODING_RULES = Collections.unmodifiableSet(rules);
  }

  /**
   * Turns a map into a form-url-encoded string (key=value&key2&key3=value)
   * 
   * @param map any map
   * @return form-url-encoded string
   */
  public static String formURLEncodeMap(Map<String, String> map)
  {
    Preconditions.checkNotNull(map, "Cannot url-encode a null object");
    return (map.size() <= 0) ? EMPTY_STRING : doFormUrlEncode(map);
  }

  private static String doFormUrlEncode(Map<String, String> map)
  {
    StringBuffer encodedString = new StringBuffer(map.size() * 20);
    for (String key : map.keySet())
    {
      if(encodedString.length() > 0) 
      {
        encodedString.append(PARAM_SEPARATOR);
      }
      encodedString.append(urlEncodeWrapper(key));
      if (map.get(key).length() > 0) {
    	  encodedString.append(PAIR_SEPARATOR).append(urlEncodeWrapper(map.get(key)));
      } 
    }
    return encodedString.toString();
  }

  /**
   * Percent encodes a string
   * 
   * @param plain
   * @return percent encoded string
   */
  public static String percentEncode(String string)
  {
    Preconditions.checkNotNull(string, "Cannot encode null string");
    try
    {
      String encoded = URLEncoder.encode(string, UTF_8);
      for(EncodingRule rule : ENCODING_RULES)
      {
        encoded = rule.apply(encoded);
      }
      return encoded;
    } 
    catch (UnsupportedEncodingException uee)
    {
      throw new IllegalStateException(ERROR_MSG, uee);
    }
  }

  /**
   * URL encodes a string
   * 
   * @param plain
   * @return percent encoded string
   */
  public static String urlEncodeWrapper(String string)
  {
    Preconditions.checkNotNull(string, "Cannot encode null string");
    try
    {
      return URLEncoder.encode(string, UTF_8);
    } 
    catch (UnsupportedEncodingException uee)
    {
      throw new IllegalStateException(ERROR_MSG, uee);
    }
  }
  
  /**
   * URL decodes a string
   * 
   * @param string percent encoded string
   * @return plain string
   */
  public static String urlDecodeWrapper(String string)
  {
    Preconditions.checkNotNull(string, "Cannot decode null string");
    try
    {
      return URLDecoder.decode(string, UTF_8);
    }
    catch (UnsupportedEncodingException uee)
    {
      throw new IllegalStateException(ERROR_MSG, uee);
    }
  }

  /**
   * Append given parameters to the query string of the url
   *
   * @param url the url to append parameters to
   * @param params any map
   * @return new url with parameters on query string
   */
  public static String appendParametersToQueryString(String url, Map<String, String> params)
  {
    Preconditions.checkNotNull(url, "Cannot append to null URL");
    String queryString = URLUtils.formURLEncodeMap(params);
    if (queryString.length() == 0) return url;

    // Check if there are parameters in the url already and use '&' instead of '?'
    url += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
    url += queryString;
    return url;
  }

  private static final class EncodingRule
  {
    private final String ch;
    private final String toCh;

    EncodingRule(String ch, String toCh)
    {
      this.ch = ch;
      this.toCh = toCh;
    }

    String apply(String string) {
      return string.replace(ch, toCh);
    }
  }

  public static String concatSortedPercentEncodedParams(Map<String, String> params) {
	  	StringBuilder target = new StringBuilder();
	    for (String key:params.keySet()){
	    	target.append(key);
	    	target.append(PAIR_SEPARATOR);
	    	target.append(params.get(key));
	    	target.append(PARAM_SEPARATOR);
	    }
	    return target.deleteCharAt(target.length() - 1).toString();
}
  
  public static String convertUrlToBaseStringURI(URL url){
	    URI uri = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e1) {
			throw new OAuthException("URL Error", e1);
		}
		String scheme = uri.getScheme().toLowerCase();
		String host = uri.getHost().toLowerCase();
		int port = uri.getPort();
		if (
				(scheme.equals(HTTP_PROTOCOL) && port == HTTP_DEFAULT_PORT)
			||  (scheme.equals(HTTPS_PROTOCOL) && port == HTTPS_DEFAULT_PORT)
			){
			port = -1;
		}
		URI baseUri = null;
		try {
			baseUri = new URI(scheme,null, host, port, uri.getPath(), null, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return baseUri.toString();
  }
}
