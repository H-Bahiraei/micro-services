## BKI OTP Microservice
# Introduction
Welcome to Distributed OTP Microservice.  

1.  A Spring Cloud Config server that is deployed as Docker container and can manage a services configuration information using a file system/ classpath or GitHub-based repository.
2.  A file service that will manage file data used within OTP App.
3.  A MariaDB database used to hold the data.
4.  A Redis used to store temporary data

To build the code as a docker image, open a command-line 
window and execute the following command for each of module:

$ mvn clean package dockerfile:build

Now we are going to use docker-compose to start the actual image.  To start the docker image, stay in the directory containing  your chapter 5 source code and  Run the following command:
 
$ docker-compose -f docker/docker-compose.yml up

The build command
Will execute the [Spotify dockerfile plugin](https://github.com/spotify/dockerfile-maven) defined in the pom.xml file.  

This is a multiple Spring projects that need to be be built and compiled.  Running the above command at the root of the project directory will build all of the projects.  If everything builds successfully you should see a message indicating that the build was successful.

For generating and sending SOTP use this template
POST http://localhost:8080/sotp/keys/{key}/generation
example:
POST  http://localhost:8080/sotp/keys/09216017504/generation

For verification use this template
POST http://localhost:8080/sotp/keys/{key}/verification
Example:
POST http://localhost:8080/sotp/keys/09216017504/verification 