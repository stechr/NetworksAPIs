require 'sinatra'
require 'JSON'

configure do
 set :authTokens, Hash.new
 set :accTokens,  Hash.new
 set :authComplete, false
end 

# store the approved authorization code against the request Id
get '/tokenStore/:requestId' do |r|
settings.authTokens[r] = params[:code]
settings.authComplete=true
'Approval confirmed - this tab may now be closed'
end

# route to return the auth token for the request
get '/tokenStore/authToken/:requestId' do |r|
 logger.info 'checking for auth token...'
 logger.info settings.authTokens[r].to_s if !settings.authTokens[r].nil?
 if !settings.authComplete then status 204 else settings.authTokens[r] end
end





