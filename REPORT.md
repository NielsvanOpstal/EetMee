# EetMee Report
Niels van Opstal

This app makes it possible to share the food you cook, either by people picking it up or joining you for diner. Thereby making cooking and eating cheaper, reducing food waste and creating an easy way for people to connect!

![Alt text](https://github.com/nielske31/EetMee/blob/master/doc/BaseActivity.jpeg)

# Technical Design

## Overview

The code consists of several classes which can be divided in different groups. 
- The first group is the activities. These are a single screen with an user interface.
- Second, there are classes. These classes make up the different ways in whicht data is stored, there are User classes, Offer classes etc.
- Third, there are requets. These request data from the Firebase Database based on a query.
- Fourth, there are adapters. These fill ListViews and keep track on which item is shown.
- Fifth and final, there are enumerates. These enable a variable to be a set of predefined constants.

### The Activities

The app consists of a total of 8 activities:
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
  
### The Classes

The app consists of a total of 5 classes:
- The Diet class contains booleans for whether or not a user/class has allergens and/or diet preferences
  * The allergens are nuts, gluten, lactose, epanut, shellfish and soya
  * For the diet preferences are choose vegetarian and or vegan
 
- The Offer class contains all the info of an Offer.
  * the variables in the class are:
  * String what, firebaseKey, address, dateString, userID  (userID of the user who made the offer)
  * int costs, persons, personsLeft
  * Date dateTime
  * boolean eatTogheter, pickup
  * Diet diet
  * double lat, lng
  * ArrayList<String> eaters = new ArrayList<>()  (Filled with userID's from the eaters)
  * float distance  (Filled in the OfferListAdapter, the distance from the offer to the current location of the user in km)
  * It also contains setters and getters for all the variables, a function to add and remove eaters to/from the eaters arrayList and a function which makes it posible to sort the offers on the distance variable.
 
- The Review class contains a review.
 * the variables in the class are:
 * String review, reviewWriter
 * Date date
 * It also contains getters and setters for all the variables
 
 - The user Class contains all the information about an user.
 * the variables in the class are:
 * String name, bio
 * ArrayList<Review> reviews =  new ArrayList<>()
 * Diet diet
 * ArrayList<String> joinedOffers = new ArrayList<>()
 * It also contains getters and setters for all the variables and two functions to add or remove new items tot he arrayLists
 
- The MyRefChecker class is a class which checks whether or not there is still a reference to the Firebase Databse and Firebase authentication. If there is not, it gets a new reference.

### The Requests

There are 2 request classes in the app:
- The UserRequest requests an user from the Firebase databse based on the given userID. If it received an user it also gives back the UserRequestType to the activity so that the activity can handle the user correct

- The offerRequest request the offers from the Firebase database. It has three different queries that also handle the received data differently
 * One query receives all the offers of a given date
 * One query receives all the offers where the userID is equal to the current user's ID
 * One query first receives the JoinedOffers ArraList from an user, and then finds the offers from that arraylist.
 * They also give back the OfferRequestType so that the requesting activity can handle the request well.
 
 ### The adapters
 
 There are 2 adapters in the app:
- The Review Adapter fills the review list in the UserInfoActivity.
- The OfferListAdapter fills the OfferList activity and calculates the distance from each offer to the current location of the user.

### The enums

There are 2 Enums in the app:
- The first one is the OfferRequestType which consists of: ALLOFFERS, JOINEDOFFERS, MYOFFERS
- The second one is the UserRequestType which consists of: CURRENTUSER, OFFERCREATER

# Challenges
There were several things that I found challeging. They were making the classes so that it has everything it needed, making the firebase so that it was easy to use and had no double information, making the fragments work and the API's and minimizing duplicate-code.

The classes are much different from what I had expected them to be. In my design.md they are fairly simple. For example, in design.md the Offer class had 7 variables but in the app in the end it had 16 variables including one other class (Diet class which contains another 8 variables). The class became bigger and bigger the further I got into making the app. I believe the way it is now is better than what I imagined it to be in the design.md. The way the Offer class works now offers more functionality and I also didn't before know what was neccessary for all the functionality in the app.
Related to the classes was Firebase. To make that intuitivly and well functioning I had to alter the classes so that it works well. The offers need to contain the UserID from the user that created the offer so that the user could be easily lookup when someone wanted to see details about the creater from an offer. There went a lot of work into making it work well but in te end it came out nicely.

The fragments and API's were another headbreaker. I had never worked with a Fragment before but when I found out that letting users fill in the Time and Date wasn't going to work I needed to use them. To fix that I figured out how to use the fragments so that I could use the DatePickerFragment and the TimePickerFragment. Altough these were not in the design.md, it made, in my opinion, the user experience a lot better. 
Another example is the way the addresses are filled in by the user. First it was an extra activity where the user filled in his/her street, housenumber, postalcode, city and eventually a building. I then made a string of this and used that to get the coordinates via the Geocoding API. This didn't work very well because if the api gave back something else, the user would never know. Also the coordinates were always like 30 meters off. I found out about the google autcomplete and implemented that. That required a change from the Geocoding API to the Places API and some figuring out how to use that but in the end, way better user experience and much easier to handle code-wise.

Minimizing the duplicate-code also took some time and thinking. I had almost made three different activies that almost did the exact same thing (show offers) and three requests types that in principle did the same thing. At first it felt like I was doing more than was neccessary but didn't have clue how to fix it. Then when I was not working on the problem, it always happen when not trying to solve the problem, I came with a way to reduce the amount of duplicate code. I solved the problems through intents and Enums.

Another challenging thing in the end was the size of the app. Before I only made some small apps so the size was never a problem but this time I sometimes lost the overview (apparently this is the correct way to write this). On the other side, by working on the app for four weeks in a row I can now describe every activity and view without looking at the code, which I find pretty cool.

 
  

