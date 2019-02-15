# OctoPerf Maven Plugin

The OctoPerf Maven Plugin let's you run JMeter JMX scripts at scale on OctoPerf Saas or On-Premise platform. 

OctoPerf Maven plugin has multiple advantages:

- **CI Integration**: most Continuous Integration servers (like Jenkins, [Bamboo](https://fr.atlassian.com/software/bamboo) or [TravisCI](https://travis-ci.org/) just to name a few) fully support Maven,
- **Versioning**: version your JMeter scripts (like [Github](https://github.com/)), and run them directly from your CI server.

The maven plugin is distributed via [OctoPerf Maven Repository](https://github.com/OctoPerf/maven-repository) hosted on GitHub.

**Current version**: `1.0.0`

## Goals Overview

The OctoPerf plugin has the following goals:

- `octoperf:run`: uploads and runs a JMeter JMX script on OctoPerf load testing tool.

## System Requirements

The following specifies the minimum requirements to run this Maven plugin:

|  Name |   Description      |
|-------|-----|
|Maven |  3.0 |
| JDK |  1.7|
| Memory | No minimum requirement.|
| Disk Space |  No minimum requirement. |

## Other Requirements

There are a number of things you need before getting started:

|  Name |   Description   |
|-------|-----|
| OctoPerf API Key | Login on OctoPerf `Account > Profile`, and copy the API key. |
| Workspace Name | Name of the workspace where to run the test. |
| Project Name | Name of an empty project where to run the test. |
| JMeter JMX | JMeter script containing the thread groups to run. |
| Resources Files | Files associated to your script, like CSV files. |
| Scenario Json | OctoPerf Scenario defined in [Json](https://fr.wikipedia.org/wiki/JavaScript_Object_Notation). |

## Usage

You should specify the version in your project's plugin configuration:

```xml
<project>
  ...
  <build>
    <!-- To use the plugin goals in your POM or parent POM -->
    <plugins>
      <plugin>
        <groupId>com.octoperf</groupId>
        <artifactId>octoperf-maven-plugin</artifactId>
        <version>1.0.0</version>
        <configuration>
          <!-- See configuration below -->
          ...
        </configuration>
      </plugin>
      ...
    </plugins>
  </build>
  ...
  <!-- OctoPerf Maven Repository -->
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
## Goals

### octoperf:run

**Full Name**: `com.octoperf:octoperf-maven-plugin:1.0.0`
**Description**:

Goal which uploads and runs your JMeter JMX script on OctoPerf Load Testing Platform. It performs the following steps: 

- It cleans the entire project and removes all the virtual users, http servers, files and scenarios,
- The JMeter JMX script is uploaded along with the third party files,
- A scenario is created using the `scenario.json` provided by the goal configuration,
- The scenario is then run,
- The plugin waits until the test finishes and downloads JMeter Logs and JTL files if any found.

### Parameters

Let's see all the configuration involved here:

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `apiKey` | `String` | `1.0.0` | Your OctoPerf Account API key is required so the plugin can connect to the platform and run tests on your behalf. | `true` | |
| `jmxFile` | `String` | `1.0.0` | File path to the JMX script which is going to be uploaded to the platform, | `trye` | `${project.basedir}/script.jmx` |
| `workspaceName` | `String` | `1.0.0` | Name of the workspace where to run the script. Workspace name **must be unique**. | `false` | `Default` |
| `projectName` | `String` | `1.0.0` | Name of the workspace where to run the script. Project name **must be unique**. Project Design and Runtime sections are cleared on each test start. | `false` | `Maven` |
| `resourcesFolder` | `String` | `1.0.0` | Path to the third party files required by your script (like CSV files). | `false` |  `${project.basedir}/src/main/resources` |
| `scenarioFile` | `String` | `1.0.0` | Path to the `scenario.json` defining the whole scenario (user profiles to run, load policies etc). | `false` |  `${project.basedir}/scenario.json` |
| `isDownloadJUnitReports` | `String` | `1.0.0` | Should the JUnit report be downloaded at the end of the test. Junit report is downloaded to `${project.basedir}/target/junit-report.xml`. | `false` |  `true` |
| `isDownloadLogs` | `String` | `1.0.0` | Should the JMeter logs be downloaded at the end of the test. Logs are downloaded to `${project.basedir}/target/logs`. | `false` |  `true` |
| `isDownloadJTLs` | `String` | `1.0.0` | Should the JMeter JTL result files be downloaded at the end of the test. JTLs are downloaded to `${project.basedir}/target/jtls`. | `false` |  `false` |

### Parameter Details

#### `<jmxFile>`

The JMeter script must comply the following rules:

- It may contain one or several `ThreadGroup`: each thread group must have **a unique name**,
- The name of each thread group will be used in the `scenario.json` to link the load policies to the thread groups.
- **CSV Dataset configurations** are imported along with the thread groups. Prefer lowercase filenames with no special characters.

#### `<scenarioFile>`


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

**How to Create Your Own scenario.json**

- Login on OctoPerf,
- Upload your JMX Script to create the virtual user profiles,
- [Create a scenario](https://doc.octoperf.com/runtime/) with the configuration you want,
- Open the web developer console of your browser (usually by hitting `F12`), and go to network tab to see http requests. Filter by type and select `XHR`,
- Reopen the scenario to initiate a GET request to the scenario json,
- Locate the request in the developer console and copy the response from the server,
- You can use [JsonLint](https://jsonlint.com) to format the Json properly.

Once you have your `scenario.json`, please make sure to configure the ids inside properly.

**scenario.json Configuration**

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `providerId` | `String` | `1.0.0` | **OctoPerf Provider Name** to use to run the tests. Make sure the name of the provider is unique. If not unique, the provider being used to run the test is undefined. | `true` | |
| `virtualUserId` | `String` | `1.0.0` | T*thread Group Name** associated to the given load policy. The name must be unique as stated before, otherwise the thread group associated is undefined. | `true` | |


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

