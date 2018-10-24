# Lob Orders Management API
# Language : Java
## Version : 1.8.0
## Shubham Gulati


## Pre requirements
Please Install Java on system tested
brew cask install java

## Installation Guide
I have used maven as a build system. So first install maven if it is not there

1) brew install maven

Then do

2) mvn -v

3) First untar the file using below command
    ## tar -cvzf LobCodingChallenge.tar.gz


4) Go Inside the LobCodingChallenge folder to compile the project. Run below command for the same

    ## mvn clean compile

    This command will run in root directory where there is a pom.xml file. Maven will automatically get all the
    dependencies mentioned in the pom file.

5) Now we want to have executable jar. For that
    ## mvn package


6) This will generate a jar in target folder. Go to target folder and find Lob-Coding-Challenge-0.1.0.jar. Run it like below

   ## java -jar Lob-Coding-Challenge-0.1.0.jar

7) This will run the main program and automatically generate output files which are there in output folder.

# In case of any problems.
8) Please open this project in IDE and manually run LobApiHandler class Main function. This will automatically generate output files.

