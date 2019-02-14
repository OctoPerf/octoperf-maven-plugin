# OctoPerf Maven Plugin

OctoPerf integrates with [Maven](https://maven.apache.org/), a leading open-source solution for building and organizing software development. 

OctoPerf Maven plugin has multiple advantages:

- **Integration**: most Continuous Integration servers (like Jenkins, [Bamboo](https://fr.atlassian.com/software/bamboo) or [TravisCI](https://travis-ci.org/) just to name a few) fully support Maven,
- **Versioning**: version your JMeter scripts (like [Github](https://github.com/)), and run them directly from your CI server.

## Getting Started

Let's see how to setup an automated test using the **OctoPerf Maven Plugin**.

### Pre-requisites

There are a number of things you need before getting started:

- **OctoPerf Account**: an account is needed to run tests on our platform. Go to your OctoPerf `Account > Profile`, and copy the API key,
- **Workspace**: name of the workspace where the test is going to be run,
- **Empty Project**: name of an empty dedicated OctoPerf project is required. The maven plugin will clrear the entire project, then upload your JMeter JMX and run it there,
- **JMeter JMX**: a JMeter JMX script which is going to be run on the platform,
- **Files (CSVs etc.)**: files needed by your scripts like CSVs.

Let's see now how the maven plugin configuration files are organized.

### File Structure

Here is the base file structure you have:

- `pom.xml`: Maven main configuration file,
- `script.jmx`: your JMeter script.
- `src/main/resources`: resources folder containing the files linked to your JMeter script like CSV files,
- `scenario.json`: the scenario defines the user profiles to run and their load policies,
- `target/junit-report.xml`: the JUnit report is downloaded once the test is finished. It reflects the results for all the containers within your JMeter script,
- `target/logs`: JMeter logs are downloaded and decompressed in this folder at the end of the test,
- `target/jtls`: JMeter JTL files are downloaded and decompressed in this folder at the end of the test.

Most of the configurations above can be overriden in maven plugin configuration to fit your needs. Before diving into how to configure the plugin, let's see how it works.

### How It Works

This is how the maven plugin works:

1. It cleans the entire project and removes all the virtual users, http servers, files and scenarios,
2. The JMeter JMX script is uploaded along with the third party files,
3. A scenario is created using the `scenario.json` provided in the maven configuration,
4. The scenario is then run,
5. The plugin waits until the test finishes and downloads JMeter Logs and JTL files if any found.

Let's see now how to configure the maven plugin to run a test.

### pom.xml

The `pom.xml` contains the Maven plugin configuration. Let's see a sample `pom.xml`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <packaging>pom</packaging>
  <groupId>com.octoperf</groupId>
  <artifactId>octoperf-test</artifactId>
  <version>1.0.0-SNAPSHOT</version>

  <build>
    <plugins>
      <plugin>
        <groupId>com.octoperf</groupId>
        <artifactId>octoperf-maven-plugin</artifactId>
        <version>USE_LATEST_VERSION</version>
        <configuration>
          <apiKey>YOUR_API_KEY</apiKey>
          <jmxFile>${project.basedir}/script.jmx</jmxFile>
          <workspaceName>WORKSPACE_NAME</workspaceName>
          <projectName>PROJECT_NAME</projectName>
          <resourcesFolder>${project.basedir}/src/main/resources</resourcesFolder>
          <scenarioFile>${project.basedir}/scenario.json</scenarioFile>
          <isDownloadJUnitReports>true</isDownloadJUnitReports>
          <isDownloadLogs>true</isDownloadLogs>
          <isDownloadJTLs>false</isDownloadJTLs>
        </configuration>
      </plugin>
    </plugins>
  </build>

  <pluginRepositories>
  	<pluginRepository>
      <id>octoperf-snapshots</id>
      <name>OctoPerf Snapshots</name>
      <url>https://github.com/octoperf/maven-repository/raw/master/snapshots</url>
    </pluginRepository>
    <pluginRepository>
      <id>octoperf-releases</id>
      <name>OctoPerf Releases</name>
      <url>https://github.com/octoperf/maven-repository/raw/master/releases</url>
    </pluginRepository>
  </pluginRepositories>
</project>
```

The maven plugin is distributed via our own [maven repository](https://github.com/OctoPerf/maven-repository) hosted on GitHub. Replace `USE_LATEST_VERSION` by the [latest version available](https://github.com/OctoPerf/octoperf-maven-plugin).

Let's see all the configuration involved here:

- `<apiKey>YOR_API_KEY</apiKey>`: your account API key is required so the plugin can connect to the platform and run tests on your behalf,
- `<jmxFile>${project.basedir}/script.jmx</jmxFile>`: the path to the JMX script which is going to be uploaded to the platform,
- `<workspaceName>WORKSPACE_NAME</workspaceName>`: name of the workspace where the project is located. **The workspace name must be unique**,
- `<projectName>PROJECT_NAME</projectName>`: name of the project inside which the script is going to be uploaded. **The project name must be unique**. The project will be emptied before each test, so don't use it for anything else,
- `<resourcesFolder>${project.basedir}/src/main/resources</resourcesFolder>`: the folder containing third party files required by your script (like CSV files),
- `<scenarioFile>${project.basedir}/scenario.json</scenarioFile>`: location of the `scenario.json` which describes the profiles to run and the load to simulate.
- `<isDownloadJUnitReports>true</isDownloadJUnitReports>`: `true` if a JUnit report should be downloaded at the end of the test,
- `<isDownloadLogs>true</isDownloadLogs>`: `true` if JMeter logs should be downloaded at the end of the test, into `target/logs` by default,
- `<isDownloadJTLs>false</isDownloadJTLs>`: `false` by default, whenever JMeter JTL files should be downloaded at the end of the test.

### JMeter Script

The JMeter script must comply the following rules:

- It may contain one or several `ThreadGroup`: each thread group must have **a unique name**,
- The name of each thread group will be used in the `scenario.json` to link the load policies to the thread groups.
- **CSV Dataset configurations** are imported along with the thread groups. Prefer lowercase filenames with no special characters.

### Scenario Json

Here is a sample `scenario.json` file.

```json

{
  "description": "",
  "name": "HTTPBin",
  "userLoads": [{
    "providerId": "Amazon EC2",
    "region": "eu-west-1",
    "strategy": {
      "@type": "UserLoadOctoPerf",
      "iterations": null,
      "onSampleError": "CONTINUE",
      "points": [{
        "threadsCount": 1,
        "timeInMs": 0
      }, {
        "threadsCount": 10,
        "timeInMs": 300000
      }]
    },
    "virtualUserId": "THREAD_GROUP_NAME"
  }]
}
```

This is a very simple scenario configured as following:

- Run the test using `Amazon EC2` provider, in region `eu-west-1`,
- Run a 5min test (300sec), starting with 1 concurrent user and ramping up to `10` concurrent users after 5mins,
- the played thread group is `THREAD_GROUP_NAME`.

Some important things here:

- `providerId`: name of the provider to use to run the tests. Make sure the name of the provider is unique. If not, the provider being used to run the test is undefined,
- `virtualUserId`: name of the thread group associated to the given load policy. The name must be unique as stated before, otherwise the thread group associated is undefined.

There are [many possible configurations](https://doc.octoperf.com/runtime/edit-scenario/). For now, the best way to create a `scenario.json` is:

- Login on OctoPerf,
- Upload your JMX Script to create the virtual user profiles,
- [Create a scenario](https://doc.octoperf.com/runtime/) with the configuration you want,
- Open the web developer console of your browser (usually by hitting `F12`), and go to network tab to see http requests. Filter by type and select `XHR`,
- Reopen the scenario to initiate a GET request to the scenario json,
- Locate the request in the developer console and copy the response from the server,
- You can use [JsonLint](https://jsonlint.com) to format the Json properly.

Then, you need to replace the `virtualUserId` and `providerId` by the relevant names. The maven plugin will take care of doing the reverse work of replacing those names by their respective ids when uploading your script.

### Running an Test

Once your maven project is ready, run the following command to start a new test:

```bash
mvn octoperf:run
```

The test should start within a few minutes. Here is an example console output of a test run using OctoPerf Maven Plugin:

```bash
[INFO] Preparing test.. (PREPARING)
[INFO] Preparing test.. (PREPARING)
[INFO] Preparing test.. (PREPARING)
[INFO] Preparing test.. (PREPARING)
[INFO] [1.13%] 17:19:24 - Hits: 0.00 Errors: 0.00 Avg Elapsed: NaNs Total Throughput: 0.00MB Duration: 0s
[INFO] [4.48%] 17:19:34 - Hits: 4.00 Errors: 0.00 Avg Elapsed: 0.51s Total Throughput: 0.04MB Duration: 10s
[INFO] [7.83%] 17:19:44 - Hits: 11.00 Errors: 0.00 Avg Elapsed: 0.46s Total Throughput: 0.11MB Duration: 20s
....
[INFO] [94.97%] 17:24:06 - Hits: 191.00 Errors: 0.00 Avg Elapsed: 0.45s Total Throughput: 1.89MB Duration: 281s
[INFO] [98.33%] 17:24:16 - Hits: 198.00 Errors: 0.00 Avg Elapsed: 0.45s Total Throughput: 1.96MB Duration: 291s
[INFO] [100.00%] 17:24:26 - Hits: 208.00 Errors: 0.00 Avg Elapsed: 0.45s Total Throughput: 2.06MB Duration: 301s
[INFO] Test finished with state: FINISHED
[INFO] Downloading '*..log' Files into '/home/ubuntu/octoperf-maven-plugin/target/logs'...
[INFO] Remote Files: [insufficient_cellar.log.gz]
[INFO] Cleaning folder: '/home/ubuntu/octoperf-maven-plugin/target/logs'
[INFO] Downloading file: insufficient_cellar.log.gz
[INFO] Saved File: /home/ubuntu/octoperf-maven-plugin/target/logs/insufficient_cellar.log
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 06:03 min
[INFO] Finished at: 2019-02-14T17:24:36+01:00
[INFO] ------------------------------------------------------------------------
```

