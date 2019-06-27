# SmartHomeServer
Server of Smart Home App

## Smart Home Repository
https://github.com/estg-masters/SmartHome

## How it works
This server is responsible for 2 things:
* Notify the user when a notification requisite is met
* Change the values of the sensors based on external input

## Notifications
When a sensor is changed on the database (``onChildChanged``) the server
 will query the database, searching all the notification that has the 
 same sensor id of the sensor that was change:
 ``Select from notifications where sensor_id = 'changeSensorId'``
 and for each sensor found, it will verify if notification requisite is met,
 if that happens it will send a notification to the tokens of the user (stored in database)
