# RRB4J
Java implementation of RasPiRobotBoard3 (RRB3) library.

RRB4J is built on top of Pi4J:
* http://pi4j.com

For information about the MonkMakes RRB3, see: https://www.monkmakes.com/rrb3/

Released builds are available from [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ccom.callidusrobotics.rrb4j).

# Maven Coordinates
Use the following coordinates to include RRB4J in your project:

```
<dependency>
  <groupId>com.callidusrobotics.rrb4j</groupId>
  <artifactId>raspirobotboard</artifactId>
  <version>${rrb4j.version}</version>
</dependency>
```

# Setup
You'll need Apache Maven to compile RRB4J from source.

You'll need Pi4J to run your application with RRB4J.

## Maven
Install Apache Maven. The preferred method is to use the Maven Version Manager (mvnvm):

```
curl -s https://bitbucket.org/mjensen/mvnvm/raw/master/mvn > /usr/bin/mvn && chmod 0755 /usr/bin/mvn
```

## Pi4J
Install Pi4J:

```
curl -s get.pi4j.com/install | sudo bash
```

# Compiling

```
mvn clean install
```

# Running
To run the sample application on a platform supported by Pi4J:

```
sudo java -jar target/raspirobotboard3-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

Overriding the default voltage settings:

```
sudo java -jar target/raspirobotboard3-1.0.0-SNAPSHOT-jar-with-dependencies.jar $BATTERY_VOLTAGE $MOTOR_VOLTAGE
```
