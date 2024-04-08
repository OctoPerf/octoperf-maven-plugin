# OctoPerf Maven Plugin

The OctoPerf Maven Plugin let's you run JMeter JMX scripts at scale on OctoPerf Saas or On-Premise platform. 

OctoPerf Maven plugin has multiple advantages:

- **CI Integration**: most Continuous Integration servers (like Jenkins, [Bamboo](https://fr.atlassian.com/software/bamboo) or [TravisCI](https://travis-ci.org/) just to name a few) fully support Maven,
- **Versioning**: version your JMeter scripts (like [Github](https://github.com/)), and run them directly from your CI server.

The maven plugin is distributed via [OctoPerf Maven Repository](https://github.com/OctoPerf/maven-repository) hosted on GitHub.

**Current version**: `2.3.0`

## Goals Overview

The OctoPerf plugin has the following goals:

- `octoperf:wipe-project`: Deletes all virtual users, files and scenarios within the project,
- `octoperf:import-jmx`: Imports JMeter JMX Project along with resource files (like csvs etc.) on OctoPerf platform,
- `octoperf:import-scenario`: Imports `scenario.json` on OctoPerf platform,
- `octoperf:execute-scenario`: Executes the scenario with the specified name (or the previously imported scenario if no name specified).

## System Requirements

The following specifies the minimum requirements to run this Maven plugin:

|  Name |   Description      |
|-------|-----|
|Maven |  3.0 |
| JDK |  11|
| Memory | No minimum requirement.|
| Disk Space |  No minimum requirement. |

## Mandatory Parameters

There are a number of things you need before getting started:

|  Name |   Description   |
|-------|-----|
| OctoPerf API Key | Login on OctoPerf `Account > Profile`, and copy the API key. |
| Workspace Name | Name of the workspace where to run the test. |
| Project Name | Name of an empty project where to run the test. |

Depending on the goal you plan to use, additional parameters may be required.

## Usage

You should specify the version in your project's plugin configuration:

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
        <version>2.3.0</version>
        <configuration>
          <!-- See configuration below -->
        </configuration>
      </plugin>
    </plugins>
  </build>

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
## Common configuration

The following parameters are shared by all the goals:

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `apiKey` | `String` | `1.0.0` | Your OctoPerf Account API key is required so the plugin can connect to the platform and run tests on your behalf. | `true` | |
| `workspaceName` | `String` | `1.0.0` | Name of the workspace where to run the script. Workspace name **must be unique**. | `false` | `Default` |
| `projectName` | `String` | `1.0.0` | Name of the project where to run the script. Project name **must be unique**. | `false` | `Maven` |
| `serverUrl` | `String` | `1.0.0` | URL of the OctoPerf API server. Can be changed to use an Enterprise OctoPerf server. | `false` |  `https://api.octoperf.com` |

## octoperf:wipe-project

### Summary

**Full Name**: `com.octoperf:octoperf-maven-plugin:wipe-project`
**Description**:

Wipes the entire project specified with name from property `projectName`, in workspace with name `workspaceName`. It removes in the following order:

- Scenarios,
- Virtual users,
- Http servers,
- variables,
- and project files.

### Additional Parameters

This goal has no additional parameters.

### Example

On OctoPerf platform, you must first:

- Choose a workspace to work in. Example: `Default`,
- Choose a project to work in. Example: `Maven`,
- Provide a `script.jmx` and place it next to the maven `pom.xml`,
- Provide resource files (like csv files) in

**WARNING**: the project scripts and scenarios are going to be wiped on each test.

Next, configure octoperf-maven-plugin in `pom.xml` with the following parameters:

```xml
<configuration>
  <apiKey>YOUR_API_KEY</apiKey>
  <workspaceName>WORKSPACE_NAME</workspaceName>
  <projectName>PROJECT_NAME</projectName>
</configuration>

```

Please replace the placeholders with the relevant parameters. Once done, run the following command:

```bash
mvn octoperf:wipe-project
```

The output should look like:

```bash
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.octoperf:octoperf-test >---------------------
[INFO] Building octoperf-test 1.0.0-SNAPSHOT
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- octoperf-maven-plugin:2.3.0:wipe-project (default-cli) @ octoperf-test ---
[INFO] Workspace: Personal
[INFO] Project: Maven
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.865 s
[INFO] Finished at: 2019-06-06T16:12:05+02:00
[INFO] ------------------------------------------------------------------------
```

All the project virtual users, resource files, http servers and scenarios are deleted.

## octoperf:import-jmx

### Summary

**Full Name**: `com.octoperf:octoperf-maven-plugin:import-jmx`
**Description**:

Uploads and imports a JMeter JMX along with its resource files on OctoPerf platform.

**WARNING**:
First, wipe the project.
Then import your JMX. That way, you won't have multiple virtual users with same name.

#### Additional Parameters

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `jmxFile` | `String` | `1.0.0` | File path to the JMX script which is going to be uploaded to the platform | `true` | `${project.basedir}/script.jmx` |
| `resourcesFolder` | `String` | `1.0.0` | Path to the third party files required by your script (like CSV files). | `false` |  `${project.basedir}/src/main/resources` |

### Parameter Details

#### `<jmxFile>`

The JMeter script must comply the following rules:

- It may contain one or several `ThreadGroup`: each thread group must have **a unique name**,
- The name of each thread group will be used in the `scenario.json` to link the load policies to the thread groups.
- **CSV Dataset configurations** are imported along with the thread groups. Prefer lowercase filenames with no special characters.

### Example

On OctoPerf platform, you must first:

- Choose a workspace to work in. Example: `Default`,
- Choose a project to work in. Example: `Maven`,
- Provide a `script.jmx` and place it next to the maven `pom.xml`,
- Provide resource files (like csv files) in `src/main/resources` folder.

Next, configure octoperf-maven-plugin in `pom.xml` with the following parameters:

```xml
<configuration>
  <apiKey>YOUR_API_KEY</apiKey>
  <workspaceName>WORKSPACE_NAME</workspaceName>
  <projectName>PROJECT_NAME</projectName>
</configuration>

```

Please replace the placeholders with the relevant parameters. Once done, run the following command:

```bash
mvn octoperf:import-jmx
```

The output should look like:

```bash
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.octoperf:octoperf-test >---------------------
[INFO] Building octoperf-test 1.0.0-SNAPSHOT
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- octoperf-maven-plugin:2.3.0:import-jmx (default-cli) @ octoperf-test ---
[INFO] Workspace: Personal
[INFO] Project: Maven
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.865 s
[INFO] Finished at: 2019-06-06T16:12:05+02:00
[INFO] ------------------------------------------------------------------------
```

Browse to the OctoPerf in the relevant workspace and project. It should contain the imported JMX along with the resource files.

## octoperf:import-scenario

### Summary

**Full Name**: `com.octoperf:octoperf-maven-plugin:import-scenario`
**Description**:

Imports the `scenario.json` which contains the description of the scenario to execute.
Make sure the project already contains the referenced virtual users, otherwise the import will fail.

**WARNING**: First, wipe the project. Then, import the scenario. That way, you won't have multiple scenarios with same name.

#### Additional Parameters

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `scenarioFile` | `String` | `1.0.0` | File path to the scenario json definition. | `true` | `${project.basedir}/scenario.json` |

### Parameter Details

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
- Click on the [Export Scenario](https://doc.octoperf.com/runtime/edit-scenario/export-scenario-maven/) menu,
- Click on the _Copy JSON_ button,
- Paste the JSON content in a text file named `scenario.json`.

### Example

On OctoPerf platform, you must first:

- Choose a workspace to work in. Example: `Default`,
- Choose a project to work in. Example: `Maven`,
- Provide a `scenario.json` file next to the `pom.xml`.

Next, configure octoperf-maven-plugin in `pom.xml` with the following parameters:

```xml
<configuration>
  <apiKey>YOUR_API_KEY</apiKey>
  <workspaceName>WORKSPACE_NAME</workspaceName>
  <projectName>PROJECT_NAME</projectName>
</configuration>

```

Please replace the placeholders with the relevant parameters. Once done, run the following command:

```bash
mvn octoperf:import-scenario
```

The output should look like:

```bash
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------------< com.octoperf:octoperf-test >---------------------
[INFO] Building octoperf-test 1.0.0-SNAPSHOT
[INFO] --------------------------------[ pom ]---------------------------------
[INFO]
[INFO] --- octoperf-maven-plugin:2.3.0:import-scenario (default-cli) @ octoperf-test ---
[INFO] Workspace: Personal
[INFO] Project: Maven
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.865 s
[INFO] Finished at: 2019-06-06T16:12:05+02:00
[INFO] ------------------------------------------------------------------------
```

Browse to OctoPerf Platform and check the relevant workspace / project contain the imported scenario.

## octoperf:execute-scenario

### Summary

**Full Name**: `com.octoperf:octoperf-maven-plugin:execute-scenario`
**Description**:

Executes the scenario with name specified by `scenarioName` parameter (or the single scenario within the project if left empty).

**IMPORTANT**: Scenario name **must be unique**. If the project contains multiple scenarios with the same name, the execution will throw an error.

### Additional Parameters

| Name | Type | Since | Description | Required | Default Value |
|------|------|-------|-------------|----------|---------------|
| `scenarioName` | `String` | `2.0.0` | Scenario name. If empty, a single scenario within the project is expected to exist. | `false` | `` |
| `reportTemplateName` | `String` | `2.2.0` | Name of the bench report template to use. Default template is used if none specified. | `` | `` |
| `isDownloadJUnitReports` | `boolean` | `1.0.0` | Should the JUnit report be downloaded at the end of the test. Junit report is downloaded to `${project.basedir}/target/junit-report.xml`. | `false` |  `true` |
| `isDownloadLogs` | `boolean` | `1.0.0` | Should the JMeter logs be downloaded at the end of the test. Logs are downloaded to `${project.basedir}/target/logs`. | `false` |  `true` |
| `isDownloadJTLs` | `boolean` | `1.0.0` | Should the JMeter JTL result files be downloaded at the end of the test. JTLs are downloaded to `${project.basedir}/target/jtls`. | `false` |  `false` |
| `stopTestIfThreshold` | `String` | `2.0.0` | Stops the tests if an alarm with this severity is raised. Set to `WARNING` or `CRITICAL`. | `false` |  `` |
| `testName` | `String` | `2.3.0` | Scenario name. If empty, a single scenario within the project is expected to exist. | `` | `` |

### Example

On OctoPerf platform, you must first:

- Choose a workspace to work in. Example: `Default`,
- Choose a project to work in. Example: `Maven`,
- Import a virtual user and create an associated scenario.

Next, configure octoperf-maven-plugin in `pom.xml` with the following parameters:

```xml
<configuration>
    <apiKey>YOUR_API_KEY</apiKey>
    <workspaceName>WORKSPACE_NAME</workspaceName>
    <projectName>PROJECT_NAME</projectName>
    <scenarioName>SCENARIO_NAME</scenarioName>
</configuration>
```

Please replace the placeholders with the relevant parameters. Once done, run the following command:

```bash
mvn octoperf:execute-scenario
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
[INFO] Finished at: 2019-06-01T17:24:36+01:00
[INFO] ------------------------------------------------------------------------
```

## Other Examples

### Import and Execute JMX

To import and run a JMX script, you need to provide:

- The JMX script,
- The resource files,
- The `scenario.json` containing the scenario to execute.

Then run the command:

```bash
mvn octoperf:wipe-project octoperf:import-jmx octoperf:import-scenario octoperf:execute-scenario
```

This will:

- Wipe the project virtual users, resource files, http servers and scenarios,
- Import the JMX and resource files which in turn creates the virtual user scripts on the platform,
- Import the scenario JMX which defines the virtual user scripts to run,
- Launch the scenario previously imported.


