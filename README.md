# Project Pinkie
Relaunch of [altenerding-biber.de](http://www.altenerding-biber.de) based on Java EE and JSF instead of PHP.  
All-new website and REST service for Android app access.

Current test version is visible [here](https://www.praegla.net/pinkie)  

## Backend
* JavaEE 8 
* JPA for database operations

## Frontend
* JSF 2.3
* Bootsfaces 1.3.0
* PrimeFaces 6.1
* Omnifaces 2.6.6

# Environment setup
## Application server
Tested with Tested with [Glassfish 5.0.0](https://javaee.github.io/glassfish/download) <br/>
Setup after this [Tutorial](https://www.nabisoft.com/tutorials/glassfish/installing-glassfish-41-on-ubuntu)<br/><br/>
This Glassfish version only works with a jdk 8u152 or earlier (see [here](https://stackoverflow.com/questions/50374321/why-cant-access-to-glassfish-admin-console-remotely/50374495#50374495) for an explanation)

### Set up Postgres Driver in Glassfish
Downloaded [Postgres Driver](https://jdbc.postgresql.org/download.html) and put it under GLASSFISH_HOME/domains/YOUR_DOMAIN/lib 

### Install nuLiga JSON client library
Download [here](https://hbde-portal.liga.nu/rs/documentation/downloads.html#artifact_java_json_client_library) and put it under GLASSFISH_HOME/domains/YOUR_DOMAIN/lib 

### Datasource Setup
Install local PostgreSQL database or use a remote one.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE resources PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Resource Definitions//EN" "http://glassfish.org/dtds/glassfish-resources_1_5.dtd">
<resources>
    <jdbc-connection-pool allow-non-component-callers="false" associate-with-thread="false" connection-creation-retry-attempts="0" connection-creation-retry-interval-in-seconds="10" connection-leak-reclaim="false" connection-leak-timeout-in-seconds="0" connection-validation-method="auto-commit" datasource-classname="org.postgresql.ds.PGSimpleDataSource" fail-all-connections="false" idle-timeout-in-seconds="300" is-connection-validation-required="false" is-isolation-level-guaranteed="true" lazy-connection-association="false" lazy-connection-enlistment="false" match-connections="false" max-connection-usage-count="0" max-pool-size="32" max-wait-time-in-millis="60000" name="Pinkie" non-transactional-connections="false" pool-resize-quantity="2" res-type="javax.sql.DataSource" statement-timeout-in-seconds="-1" steady-pool-size="8" validate-atmost-once-period-in-seconds="0" wrap-jdbc-objects="false">
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

### E-Mail resource Setup
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE resources PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Resource Definitions//EN"
        "http://glassfish.org/dtds/glassfish-resources_1_5.dtd">
<resources>
    <mail-resource debug="true" host="smtp.strato.de" from="webmaster@praegla.net" user="webmaster@praegla.net" jndi-name="mail/default">
        <property name="mail.user" value="webmaster@praegla.net"></property>
        <property name="mail.smtp.socketFactory.fallback" value="false"></property>
        <property name="mail.smtp.starttls.enable" value="true"></property>
        <property name="mail.password" value="<password>"></property>
        <property name="mail.host" value="smtp.strato.de"></property>
        <property name="mail.port" value="465"></property>
        <property name="mail.smtp.auth" value="true"></property>
        <property name="mail.smtp.socketFactory.port" value="465"></property>
    </mail-resource>
</resources>
```
In Bash:
./asadmin add-resources ../domains/domain1/config/email-ressource.xml

### Configuration
Fill the configuration table with the neccessary properties. 

TODO name neccessary properties
