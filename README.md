# ShareNow API tests

Test execution requires gradle installed and sharenow-api-tests repository cloned.

Please, follow the instruction below. 

  1. Clone sharenow-api-tests repository
  2. Install gradle if not exists
  
### Gradle Installation

#### For MacOS & Linux

```sh
$ brew install gradle
```

#### For Windows 
Follow the instuctions here: https://gradle.org/install/


### Tests execution

There are several Tests Suites in repository for different Api methods:
  - RoofTopStatusTests
  - DoorStatusFrontLeftTests


To execute all tests, open Terminal and run
```sh
kkrasotina@MBP% ./path_to_sharenow-api-tests_repo/gradlew clean test --info   
```
To execute Specific Test Suite, run 
```sh
kkrasotina@MBP% ./path_to_sharenow-api-tests_repo/gradle test --tests <SpecificTestSuite>
