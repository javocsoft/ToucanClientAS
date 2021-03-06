# Toucan Client

An Android Studio client library to connect, in conjuction with ToolBox library (https://github.com/javocsoft/JavocsoftToolboxAS), your application with <b>Pushburst</b>, JavocSoft Push Notification service at http://www.pushburst.com/.

## Project Integration ##

This is an Android Studio Library project. To integrate in your project, just add a new repository to your project build.gradle:

    allprojects {
        repositories {
            jcenter()
            mavenCentral()
            //JavocSoft Repository
            maven {
               url "https://dl.bintray.com/javocsoft/maven"
            }
        }
    }

and in your application build.gradle file:

    compile 'es.javocsoft:toucanclient:1.0.0'

You can also clone the project "ToucanClientAS" from GitHub (https://github.com/javocsoft/ToucanClientAS.git).

In addition to build.gradle, you have to declare a Toucan service in your AndroidManifest.xml, this service is responsible of sending any pending data to the notification service:

    <service android:name="es.javocsoft.android.lib.toucan.client.service.PendingOperationsDeliveryService" />


## LICENSE ##

Copyright 2010-2017 JavocSoft.

JavocSoft Toucan Client is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

JavocSoft Toucan Client is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this library. If not, see http://www.gnu.org/licenses/.
