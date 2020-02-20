default: package

WORKSPACE = $(shell pwd)
THREADS = 2
POM = -f pom.xml
MVN = mvn $(POM)

MVN_REPO ?= $(WORKSPACE)/../maven-repository

clean:
	$(MVN) clean

test: clean
	$(MVN) -T $(THREADS) test

package: clean
	$(MVN) -T $(THREADS) clean package -DskipTests

install: clean
	$(MVN) clean install -DskipTests

deploy: clean
	$(MVN) clean deploy -DskipTests -DdeployPath=$(MVN_REPO)

version:
	$(MVN) versions:set -DgenerateBackupPoms=false -DnewVersion=$(VERSION)
	rm -f **/pom.xml.versionsBackup
