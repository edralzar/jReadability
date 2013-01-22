jReadability
============

java 1.7 client library for the Readability.com API


usage
-----

 - get an API key from Readability (https://www.readability.com/account/connections/#request-api-key)
 - in a properties file at the root of your classpath called "jreadability.properties", put the api key info ("api_key" and "api_secret")
 - get an instance of the service using the ReadabilityServiceBuilder.build method
 
 ITokenStore have 2 implementations : MemoryStore and FileStore (those are used to store the oauth token)
 IAuthenticationDelegate is an interface to let you decide how to send the user to the authentication page and how to let him input the confirmation code.

