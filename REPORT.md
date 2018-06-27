# EetMee Report
Niels van Opstal

This app makes it possible to share the food you cook, either by people picking it up or joining you for diner. Thereby making cooking and eating cheaper, reducing food waste and creating an easy way for people to connect!

![Alt text](https://github.com/nielske31/EetMee/blob/master/doc/BaseActivity.jpeg)

# Technical design

## Overview

The code consists of several classes which can be divided in different groups. 
- The first group is the activities. These are a single screen with an user interface.
- Second, there are classes. These classes make up the different ways in whicht data is stored, there are User classes, Offer classes etc.
- Third, there are requets. These request data from the Firebase Database based on a query.
- Fourth, there are adapters. These fill ListViews and keep track on which item is shown.
- Fifth and final, there are enumerates. These enable a variable to be a set of predefined constants.

### The activities

The app consists of a total of 
