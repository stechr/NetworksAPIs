require 'vfnetapis'

# 1. pull and run authTrap.rb available

# 2. initialise with your application key and use the authTrap.rb as the callback URI
loc=Vfnetapis::Location.new("bb3c7ce07bf33fea491b58e24ec8b610","http://localhost:4567/tokenStore")

# 3. make the location request, params are MSISDN and requested accuracy in metres 
loc.location("tel:447825116832",500)

# 4. Do something with the results!
puts 'Results for address: ' + loc.address
puts 'longitude is ' + loc.longitude
puts 'latitude is '  + loc.latitude
puts 'altitude is '  + loc.altitude
puts 'accuracy is ' + loc.accuracy
puts 'timestamp is ' + loc.timestamp

puts 'the full response is: ' + loc.response.to_s # => the raw JSON if you want it...

puts 'the access token is: ' + loc.access_token
puts 'the access token expiry is: ' + loc.expires_in

# TODO 
# Add Payments
# Add SMS
# Add ability to reuse an access token if a valid one is available for the MSISDN
