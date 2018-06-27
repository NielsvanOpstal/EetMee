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
  * If the user is registering, the activity redirects to the EditProfileActivity.
  * If the user already has an account, the use activity enables the user to go the BaseActivity.
  
- The EditProfileActivity lets an user specify his/her name, bio and diet.
  * When an user presses submit, an User class containing an Diet class gets created and added to the FirebaseDatabase. Then the user gets redirected to the BaseActivity.
  
- The BaseActivity let's the user choose what the user wants to do. So basically it is a redirect activity.
  * If the user wants to eat, the user gets redirected to the OfferListActivity with the OfferRequestType AllOffers.
  * If the user wants to see the offers that he/she created, the user gets redirected to the OfferListActivity with the OfferRequestType MyOffers.
  * If the user wants to see the offers that he/she already joined, the user gets redirected tot he OfferListActivity with the OfferRequestType JoinedOffers
  * If the user wants to create an offer, the user gets redirected to the MakeOfferActivity.
  * If the user wants to see his/her own profile, the User gets redirected to the UserInfoACtivity.
  
- The OfferListActivity shows the Offers that user wanted to see in an ListView. Which offers he gets shown depend on the OfferRequestType the activity got from the baseActivity.
  * If the OfferRequestType was AllOffers, all the offers on the current date are shown. The user also has the possibility to specify another date through the use of an DatePickerFragment on which he/she wants to see the available offerr.
  * If the OfferRequestType was MyOffers, offers created by the user gets shown.
  * If the OfferRequestType was JoinedOffers, offers joined by the uset get shown.
  * If the user clicks on an offer in the ListView the user gets redirected to the DetailActivity which shows the details of the offer.
  
- The DetailActivity shows the details of the offer the user had just clicked on.
  * It shows the price, name of the person who created it, a MapView with a marker on the address from the offer, the time and, if present, clashes between the user's diet and the offer.
  * If the user is not the creater of the offer, already joined and the offer is in the future, the offer shows a button by which you can join the offer.
  * If the user has already joined the offer and the offer is still in the future, it shows a button by which the user can un join the offer.
  * If the user has joined and the offer is in the past, it gives the user the possibility to leave a thank-you-message for the cook of the offer. The message is set into an Review class which is added to user of the profile in Firebase.
  * If the user presses the information button of the cook of the offer, the user get's redirected to the UserInfoActivity of that user.
  
- The UserInfoActivity shows the information of an User.
  * It shows the name, bio and the reviews left bij other users in a listview.
  * If the user is viewing his own profile, there is a button which allows the user to edit his/her profile.
  
- The MakeOfferActivity is the activity where the user can create an offer.
  * The fields to specifiy are: what the user is going to cook, for how many persons, where the food can be picked up/joined, the date of the offer, the time on which the food is ready, the estimated costs per person and if he/she wants the food to be picked up and/or joined for eating.
  * The date is specified by a DatePickerFragment.
  * The time is specified by a TimePickerFragment.
  * The location is choosen by a Google Places Autocomplete.
  * If the user continues, the forms are checked and when complete the user gets redirected to the DietAcitivty. The fields are put into an Offer class and given to the DietActivity
  
- The DietActivity is where the user can specify the specifics of the offer: allergens and whether or not it is vegetarian/vegan.
  * When user completes this activity, the specifics are set into an Diet Class which is added to the Offer class and then set into the Firebase database
  
  

