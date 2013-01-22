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

maven
-----
Warning: The 0.1 version is a SNAPSHOT and is susceptible to change.

 add the following repository in your pom:
 
 <repositories>
		<repository>
			<id>net.edralzar.jreadability</id>
			<url>https://github.com/edralzar/jreadability/mvn-repo/raw/master</url>
			<!-- use snapshot version -->
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
		
		...
	</repositories>
	
then add the following dependency:

	<dependencies>
		<dependency>
			<groupId>net.edralzar</groupId>
			<artifactId>jreadability</artifactId>
			<version>0.1-SNAPSHOT</version>
		</dependency>
		<dependency>
		
		...
	</dependencies>