#### [KiwiQA] Automation Framework

**Pre requisite Plugins to run this automation suite:**
- Jdk
- Maven
- Docker

**Compatible browser till now to run locally:**
 - Chrome: Version 81.0 (min) (Incase of higher version download its compatable driver and kept it under respective driver folder in the project)
 
 **Project TestSuites Execution is:**
- Build/*Indexsuite.xml (Instances and modules vise Indexsuites (Test Suites))

**Browser Driver location for executing script locally:**
- */driver/

**Instruction to run this project:**
 1. git clone the project from master branch
 2. import all dependencies
 3. install all the required plugins
 4. Navigate to project directory, for example if your project is in folder like 
 /webAutomation
         Then navigate to folder using cd /webAutomation
 5. Execute below command sequentially by maven for executing the test suite by maven
    - mvn clean
    - mvn test
 6. Check the automation test report by opening the test-output/index.html file in browser. 