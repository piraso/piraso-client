![P](http://piraso.org/piraso_32.png)iraso Client [![Build Status](https://buildhive.cloudbees.com/job/alvinrdeleon/job/piraso-client/badge/icon)](https://buildhive.cloudbees.com/job/alvinrdeleon/job/piraso-client/)
====

### Introduction

This is the Piraso client source code. This is created using Netbeans Platform.

http://www.piraso.org/

### NetBeans Platform

Please see  http://netbeans.org/features/platform/.

### Quick Start

Here are the instructions to build and run:

1. Install Maven 3 and JDK 6

2. To build source code go to `${piraso-client.home}` and execute the following command:
```
    mvn clean install
```

3. To run go to `${piraso-client.home}/application`
```
    mvn nbm:run-platform
```

4. To build installers go to `${piraso-client.home}/application`
```
    mvn nbm:build-installers
```

## Author

Designed and built by [Alvin R. de Leon](https://github.com/alvinrdeleon/)

## License

Copyright 2012 Piraso

Code licensed under [Apache License v2.0](http://www.apache.org/licenses/LICENSE-2.0).
