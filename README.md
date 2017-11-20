# Toucan Client
A client library for Toucan, JavocSoft Push Notification service. This is the toucan_client library ported to Android Studio.

##Project Integration##

This is an Android Studio Library project. To integrate in your project just put in your gradle build:

compile 'es.javocsoft:toucanclient:1.0.0'
You can also clone the project "ToucanClientAS" from GitHub (https://github.com/javocsoft/ToucanClientAS.git).

Also, you have to set this code in your AndroidManifest.xml to declare the service that will send any pending data:

    <service android:name="es.javocsoft.android.lib.toucan.client.service.PendingOperationsDeliveryService" />


##LICENSE##

Copyright 2010-2017 JavocSoft.

JavocSoft Toucan Client is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

JavocSoft Toucan Client is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this library. If not, see http://www.gnu.org/licenses/.
