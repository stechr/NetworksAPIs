require "vfnetapis/version"
require 'rest-client'
require 'JSON'
require 'launchy'
require 'uri'

module Vfnetapis
  class Location
    attr_accessor :key
    attr_accessor :redirectURI
    attr_accessor :requestId
    attr_accessor :auth_token
    attr_accessor :access_token
    attr_accessor :expires_in
    attr_reader   :longitude
    attr_reader   :latitude
    attr_reader   :altitude 
    attr_reader   :address
    attr_reader   :accuracy
    attr_reader   :timestamp 
    attr_reader   :response
    @@loc_api="http://79.125.107.189/v2/location/queries/location"
    @@auth_server = "http://79.125.107.189/2/oauth/authorize"
    @@acc_server = "http://176.34.213.154/2/oauth/access_token?client_id="
    
    def initialize(key,redirectURI) # => set up tokens ready for request
     @scope = "GET-/location/queries/location" # "POST-/payment/acr:Authorization/transactions/amount"    
     @key          = key
     @redirectURI  = redirectURI
     @requestId    = generateActivationCode()
    end
    
    def location(address, requested_accuracy)
     @auth_token   = get_auth_token(@redirectURI,@scope)
     puts 'Auth Token is:' + @auth_token
     @access_token = get_access_token(@auth_token)
     puts 'Access Token is:' + @access_token

     @response = RestClient.get @@loc_api, :authorization=> 'Oauth ' + @access_token, :params => {:address => URI.escape(address,':'), :requestedAccuracy => requested_accuracy}

     resJson = JSON.parse(@response)
     @longitude = resJson["terminalLocationList"]["terminalLocation"]["currentLocation"]["longitude"]
     @latitude  = resJson["terminalLocationList"]["terminalLocation"]["currentLocation"]["latitude"]
     @altitude  = resJson["terminalLocationList"]["terminalLocation"]["currentLocation"]["altitude"]
     @accuracy  = resJson["terminalLocationList"]["terminalLocation"]["currentLocation"]["accuracy"]
     @timestamp = resJson["terminalLocationList"]["terminalLocation"]["currentLocation"]["timestamp"]
     @address   = resJson["terminalLocationList"]["terminalLocation"]["address"]
    end
   
    def get_auth_token(redirectURI,scope) # => start consent flow to send code to redirectURI
     @uri = @@auth_server + '?client_id=' + @key + '&redirect_uri=' + URI.escape(redirectURI + '/' + @requestId, '/:&\?\.@') + '&scope=' + URI.escape(scope,'/:')
     Launchy.open(@uri) # => opens a browser to acquire consent then redirects to the callback
     while true # => poll to check user consent received by external listener
	   begin
		  @auth_token = RestClient.get redirectURI + '/authToken/' + requestId
		  break if !@auth_token.empty?
	   rescue
		  $stderr.puts "failed to get access token"
		  retry
	   end
	   sleep 1
	 end
	 @auth_token
    end  

  	def get_access_token(authorizationCode)
  	 uri = @@acc_server + @key
  	 accessResponse = RestClient.post uri, :redirect_uri => URI.escape(redirectURI, '/:&\?\.@') , :grant_type => 'authorization_code', :response_type => 'client_credentials', :code => authorizationCode, :content_type => 'application/x-www-form-urlencoded', :accept => :json
  	 # this needs to change in order to get the JSON back
  	 resJson = JSON.parse(accessResponse)
  	 @expires_in = resJson["expires_in"]
     @accessToken = resJson["access_token"]
    end
    
    def generateActivationCode(size= 6)
     charset = %w{ 2 3 4 6 7 9 A C D E F G H J K M N P Q R T V W X Y Z}
  	 (0...size).map{ charset.to_a[rand(charset.size)] }.join
	end
 end
end
