#**Introduction**
Fruitpal windows console app test automation project will be used mostly for functional CLI tests.

##**Clone a repository**
Use these steps to clone from GitHub, our client for using the repository command-line free. 
Cloning allows you to work on your files locally. If you don't yet have SourceTree, download and install first. If you prefer to clone from the command line, see Clone a repository.

>You’ll see the clone button under the Source heading. Click that button.
Now click Check out in GitHub. You may need to create a GitHub account or log in.
When you see the Clone New dialog in GitHub, update the destination path and name if you’d like to and then click Clone.
Open the directory you just created to see your repository’s files.
Now that you're more familiar with your Bitbucket repository, go ahead and add a new file locally. 

##**Dependencies**
For running tests - TestNG runner

For sending CLI commands - Expectit |https://github.com/Alexey1Gavrilov/ExpectIt|

For preparing the body and uploading test data - Gson
Logging - slf4j

##**Project structure:**

src/test/java/appManager- will be used for storing general helpers in common packages and application management

src/test/java/model - will be used for storing reference data

src/test/java/tests - will be used to store test cases

src/test/resources - will store config files and test data. Also, stores properties for different environments (app location, etc…)

##**How to run tests:**
By TestNG runner - just run class PurchaseTests in runners package. Default it will run tests on local environment.

By terminal type command: gradle testTrader

##**Known issues:**
Fruitpal app needs to be installed on local machine, commoditydata.json - referring to local dev folder