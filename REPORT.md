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

The app consists of a total of 8 activities.
- The LoginActivity enables the user to login or create an account using Google's Firebase Authentication via the email password method. It also enables the user to send a verification email to their email.
  * If the user already has an account, the use activity enables the user to go the BaseActivity.
  * If the user is registering, the activity redirects to the EditProfileActivity.
- The BaseActivity let's the user choose what the user wants to do. So basically it is a redirect activity.
  * If the user wants to eat, the user gets redirected to the OfferListActivity with the OfferRequestType AllOffers.
  * If the user wants to see the offers that he/she created, the user gets redirected to the OfferListActivity with the OfferRequestType MyOffers.
  * If the user wants to see the offers that he/she already joined, the user gets redirected tot he OfferListActivity with the OfferRequestType JoinedOffers
  * If the user wants to create an offer, the user gets redirected to the MakeOfferActivity.
  * If the user wants to see his/her own profile, the User gets redirected to the UserInfoACtivity.
