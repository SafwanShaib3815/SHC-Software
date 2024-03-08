# Smart Home Controller

The Smart Home Controller is a versatile solution that combines software and hardware components to create a comprehensive smart home automation system. This README provides an overview of the project and its key features.

## Hardware Description

The Smart Home utilizes the following sensors:

-Temperature Sensor: Monitors and regulates the room temperature for optimal comfort.

-Motion Sensor: Detects human presence and triggers the activation of external lights.

-Smoke Sensor: Provides an extra layer of security by detecting the presence of gas or smoke in the air.

-RFID Sensor: Enables secure door unlocking using RFID key/fob.

-The sensors are connected to a Raspberry Pi, which serves as the central processing unit for the system. They are soldered onto a PCB, which is then mounted on the GPIO of the Raspberry Pi.


## Software Description

The Smart Home Controller features a mobile app that provides seamless control and monitoring of the smart home system. The key functionalities of the app include:

Motion Detection: The app sends notifications to the user when motion is detected, enabling them to take necessary actions.

Lighting Automation: Based on the motion detected, the app allows the user to control the external lights, ensuring enhanced safety and convenience.

Access Control: The app verifies the code generated from the RFID key/fob against a database, granting access or alerting the user of unauthorized attempts.

Temperature Regulation: The app continuously receives data from the temperature sensor and uses it to control the heating or cooling systems, maintaining the desired room temperature.

Smoke Detection: The app monitors the smoke sensor data and alerts the user through notifications and an alarm in the event of any detected smoke or gas.


## Build Instructions:
For detailed instructions on building and setting up the Smart Home Controller, please refer to the following document:

https://1drv.ms/w/s!AriJTbx-myH1jkqTvC0Wvu7zeUjM?e=I8z6uj
