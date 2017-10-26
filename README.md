# Project Pinkie
Relaunch of [altenerding-biber.de](http://www.altenerding-biber.de) based on Java EE instead of PHP.  
All-new website and REST service for Android app access.

# Environment setup
## Application server
Tested with Tested with [Glassfish4](http://download.java.net/glassfish/4.1/release) Version 4.1 <br/>
Setup after this [Tutorial](https://www.nabisoft.com/tutorials/glassfish/installing-glassfish-41-on-ubuntu)

### Set up Postgres Driver in Glassfish
Downloaded [Postgres Driver](https://jdbc.postgresql.org/download.html) and put it under GLASSFISH_HOME/domains/YOUR_DOMAIN/lib 

### Datasource Setup
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE resources PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Resource Definitions//EN" "http://glassfish.org/dtds/glassfish-resources_1_5.dtd">
<resources>
    <jdbc-connection-pool allow-non-component-callers="false" associate-with-thread="false" connection-creation-retry-attempts="0" connection-creation-retry-interval-in-seconds="10" connection-leak-reclaim="false" connection-leak-timeout-in-seconds="0" connection-validation-method="auto-commit" datasource-classname="org.postgresql.ds.PGSimpleDataSource" fail-all-connections="false" idle-timeout-in-seconds="300" is-connection-validation-required="false" is-isolation-level-guaranteed="true" lazy-connection-association="false" lazy-connection-enlistment="false" match-connections="false" max-connection-usage-count="0" max-pool-size="32" max-wait-time-in-millis="60000" name="Pinkie" non-transactional-connections="false" pool-resize-quantity="2" res-type="javax.sql.DataSource" statement-timeout-in-seconds="-1" steady-pool-size="8" validate-atmost-once-period-in-seconds="0" wrap-jdbc-objects="false">
        <property name="serverName" value="<IP>"/>
        <property name="portNumber" value="5432"/>
        <property name="databaseName" value="pinkieTest"/>
        <property name="User" value="<username>"/>
        <property name="Password" value="<password>"/>
        <property name="URL" value="jdbc:postgresql://<IP>:5432/pinkieTest"/>
        <property name="driverClass" value="org.postgresql.Driver"/>
    </jdbc-connection-pool>
    <jdbc-resource enabled="true" jndi-name="jdbc/pinkie" object-type="user" pool-name="Pinkie"/>
</resources>
```
In Bash:
./asadmin add-resources ../domains/domain1/config/glassfish4-ressource.xml

### Update of Jackson Libs in Glassfish
This is necessary because of the old jackson libs cannot convert object lists to json

https://stackoverflow.com/questions/27334528/upgrading-jackson-in-glassfish-4-1
Replace the old 5 Jackson Libs from /glassfish/modules with newer version, e.g. 2.8.9 and add jackson-module-jaxb-annotations.jar 
#### Already existing:
jackson-annotations
jackson-core
jackson-databind
jackson-jaxrs-base
jackson-jaxrs-json-provider

#### Addditional Jackson Lib
jackson-module-jaxb-annotations

#StartUp
At start up the JVM Option -DresourceFolder=[Path to folder] has to be provided. Inside this folder must exist following folders:
'imageOfWeek'
'teamImages'
'profileImages'
'documents'