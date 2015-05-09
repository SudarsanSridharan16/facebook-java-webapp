# Introduction #

This application aims to provide a quick and easy way for building Java Web Applications for Facebook.

It aims to provide a fast-track and all-inclusive dive deep into how the Facebook Web Applications can be built. So you can take this projects as an application model then you refactor it to meet your company's need.

This project builds on top of proven open source tools. Like spring, struts, hibernate, tiles, etc. And it's free!.

Built on top of these proven Facebook Java APIs:
  * [Facebook Java Api](http://code.google.com/p/facebook-java-api/)
  * [RestFB](http://restfb.com/)


# Showcase #

  * Canvas Page in Facebook
http://apps.facebook.com/java-webapp/

  * Social Plugins, Graph API and JS SDK Website
http://www.consultek.us/facebook-webapp/

## Latest News ##

  * Version 0.4 includes
    * [OAuth v2](#OAuth_v2.md) authentication
    * [Showcase Website](http://www.consultek.us/facebook-webapp/) with Social Plugins, Open Graph and OAuth v2 examples.

## Features ##

It was becoming too big to have it here, that's why we created our <a href='http://code.google.com/p/facebook-java-webapp/wiki/Features'>Features</a> page.


## How to use it ##

There are two ways to run the project.

  1. Download the war file. See "[Running WAR](#Running_WAR.md)".
  1. Download the source. See "[Running Source](#Running_Source.md)".


## Running WAR ##

  1. Download WAR file
  1. Expand the WAR file
  1. [Configure Properties](#Configure_Properties.md)
  1. [Create Database](#Create_Database.md)
  1. Compress the WAR file
  1. Move the WAR file to your application server.
  1. Start your application server
  1. Go to http://your-host:port/java-webapp-version/

## Running Source ##

  1. You can checkout the source using a subversion client. Please follow the steps in the [Source](http://code.google.com/p/facebook-java-webapp/source/checkout) tab at the top.
  1. [Configure Properties](#Configure_Properties.md)
  1. [Create Database](#Create_Database.md)
  1. Create hibernate mappings by executing the following command:
```
$ cd [PATH TO CHECKED OUT SOURCE]
$ mvn -Phibernate process-resources
```
  1. Create WAR file (NOTE: this requires that you have created the hibernate mappings)
```
$ cd [PATH TO CHECKED OUT SOURCE]
$ mvn package
```

Alternatively, and if you have the properties and database configured, you can create the package in just one command that creates the hibernate mappings and package:

```
mvn clean -Dmaven.test.skip=true -Phibernate process-resources -Prelease package
```

## Configure Properties ##

Whether you downloaded the WAR or checked out the source you will need to edit the facebook.properties file.

If you checked out the source this file can be found under:

```
src/main/resources/facebook.properties
```

If you downloaded the WAR file then you can find the properties file under:

```
WEB-INF/classes/facebook.properties
```

These are the properties that you need to modify. There are database properties and facebook settings. Please modify the properties according to your environment:

```
# Facebook Database
facebook.db.host = 127.0.0.1
facebook.db.port = 3306
facebook.db.schema = facebook
facebook.db.username = facebook
facebook.db.password = facebook
facebook.db.driver_class = com.mysql.jdbc.Driver


# Facebook
# Override these values with your own
facebook.api_key=
facebook.secret=
facebook.login_url=http://www.facebook.com/login.php?v=1.0
facebook.callback=http://www.consultek.us/facebook-webapp/canvas
facebook.connect.url=http://www.consultek.us/facebook-webapp
facebook.canvas=http://apps.facebook.com/java-webapp
facebook.devMode=true
# possible values are iframe and fbml
facebook.render.method=fbml


# URL for JS and CSS
server.url=http://www.consultek.us/facebook-webapp
```

### Create Database ###

If you checked out the source code using subversion then you can create the database using maven. Execute the following commands:

```
$ cd [PATH TO CHECKED OUT SOURCE]
$ mvn -Pdb clean
```

If you did not checkout the source then I would recommend you download the database files manually from [here](http://code.google.com/p/facebook-java-webapp/source/browse/#svn/springwebmvc-hibernate/trunk/src/main/db).

Once you have the files. Open a command line and execute the following commands. Assuming your mysql root has no password.

```
$ cd [PATH TO DB FILES]
$ . create_db.sh
```

If your mysql root user has a password edit the files:

```
springwebmvc-hibernate/trunk/src/main/db/create_db.sh
springwebmvc-hibernate/trunk/src/main/db/drop_db.sh
```

If you want to drop the database execute the commands:

```
$ cd [PATH TO DB FILES]
$ . drop_db.sh
```


# OAuth v2 #

**NOTE: Available since version 0.4**

The application has a couple of examples on how to use OAuth. You can see them here:

  * http://your-host:port/facebook-webapp/oauth - Spring MVC Controller
  * http://your-host:port/facebook-webapp/oauth.jsp - JSP with OAuth v2 authentication

The source code for the above mentioned examples are:

  * [OauthController.java](http://code.google.com/p/facebook-java-webapp/source/browse/springwebmvc-hibernate/trunk/src/main/java/com/google/code/facebookwebapp/controller/OauthController.java)
  * [oauth.jsp](http://code.google.com/p/facebook-java-webapp/source/browse/springwebmvc-hibernate/trunk/src/main/webapp/oauth.jsp)