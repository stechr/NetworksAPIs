# Vfnetapis

Simple OAUTH and OneAPI calls against Vodafone's OneAPI endpoint

## Installation

Add this line to your application's Gemfile:

    gem 'vfnetapis'

And then execute:

    $ bundle

Or install it yourself as:

    $ gem install vfnetapis

## Usage

  USAGE: Initialize with Vfnetapis::Location.new(application key, callbackURI) , then location(MSISDN, requested accuracy in metres)
  
  The gem returns lat/long based on mobile network location, incorporating and greatly simplifying the OAUTH flow to gain user consent.
  
  The result is a JSON location based on the OneAPI location API, http://oneapi.gsma.com/reference-location-restful-api/
  
  For convenience the JSON is pre-parsed into several instance variables, see example below.
  
  For this version the callbackURI must points to a web service with specific routes,
  to trap the authorization and access token flows. See https://github.com/OneAPI/NetworksAPIs/ruby/auth_trap.rb
  for a Sinatra implementation.
  
  EXAMPLE:
	require 'vfnetapis'
	
	# 1. run auth_trap.rb from https://github.com/OneAPI/NetworksAPIs/ruby
	
	# 2. initialise with your application key and use the auth_trap.rb as the callback URI
	loc=Vfnetapis::Location.new({my_app_key},'http://localhost:4567/tokenStore')
	
	# 3. make the location request, params are MSISDN in tel:441234567890 format, and requested accuracy in metres 
	loc.location({MSISDN},{metres})
	
	# 4. Do something with the results!
	puts 'Results for address: ' + loc.address
	puts 'longitude is ' + loc.longitude
	puts 'latitude is '  + loc.latitude
	puts 'altitude is '  + loc.altitude
	puts 'accuracy is ' + loc.accuracy
	puts 'timestamp is ' + loc.timestamp
	
	puts 'the full response is: ' + loc.response.to_s # => the raw JSON if you want it...
	
	puts 'the access token is: ' + loc.access_token
	puts 'the access token expiry is: ' + loc.expires_in"

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
