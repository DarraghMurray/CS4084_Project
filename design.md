# Technolgies Used
      Firebase Authentication - 
            We used firebase authentication to authenticate users with the ability to create and 
            sign in users with an email and password and to authenticate these on sign-in via
            our custom Log-in and Registration layout.
            
      Firebase Cloud Firestore - 
            We used firebase cloud firestore to add user item posts to a database when they create 
            an item post. This allows us to store all relvant information about items such as name
            and description. This lets us load items for other users and to use queries for search 
            or categories.
            
      Firebase Cloud Storage - 
            We used firebase cloud storage to allow users to add images and store them to items 
            and the Uri to the firebase storage for that image is stored in the item post 
            database to retrieve the image.
            
      Google Maps - 
            We used google maps to display a map for the user and it displays 2 markers at two 
            locations the item location and user location and has a zoom feature. A button to
            get directions was also added which uses an Intent to open the google maps app and 
            enter the two co-ordinates and find a route between them so a user can save the
            route in their google maps app. 
            
      Gallery - 
            We used an Intent to allow the user when creating a post to click pick image from their
            gallery to use for the item. This image is stored in firebase storage and its Uri is
            stored in the firestore database collection of posts.
            
      Email -
            We allow users to type the to,subject and body of an email on the message screen. The 
            from section is automatically filled from the current users email. We also fill in the
            to section if a user clicks on the message seller button on an item page. This is done 
            using an Intent to request a chooser for email apps to send the email from.
      Glide Library -
            We used the commands in this library loading firebase storage images into image views
            on item page and create post.

# Design Choices

      Theme - 
            We choose to use the theme of UL's colours instead of a plain white. We matched the 
            padding and text box's with this to give it a modern clean look while also                   
            making it easy to look at. The Item Shop Logo is one we made ourselves using Canva. 

      Activities -
      
            IntroductionScreen - 
                  This activity contains two buttons to Log-In or Create Account. 
                  Depending on the button pressed it uses an intent to move to the
                  LogIn or RegistrationActivity Activity.
            
            LogIn - 
                  This activity allows a user to enter an email and password in to
                  textboxes and lets them press a log-in button which uses the method
                  from firebase Authentication signInWithEmailAndPassword which takes
                  the entered fields and authenticates that they are valid and logs-in
                  the user. There are also validation methods to ensure the entered
                  password or email are valid. There are also messages provided to
                  the user via snackbars and toasts if these validation methods fail 
                  or if there are exceptions with log-in to explain what happened.
            
            RegistrationActivity -
                  This activity allows a user to enter a username, email and password 
                  in to textboxes and lets them press a register button which uses the method
                  from firebase Authentication createAccountWithEmailAndPassword which takes
                  the entered fields and authenticates that they are valid and registers
                  the user. It stores them so that Log-In can be authenticated. There are 
                  also validation methods to ensure the entered username, email or password
                  are valid. There are also messages provided to the user via snackbars and 
                  toasts if these validation methods fail or if there are exceptions with
                  registration to explain what happened. A profile update is used to set
                  the firebase Authentication display name to the username entered.
                  
            MainActivity -
                  This activity has an action bar with a back arrow and a sign-out button.
                        back arrow - 
                              this pops the fragment Backstack to return the user to the
                              previous fragment they were at.
                        sign-out -
                              this button calls the firebase Authentication sign out method
                              which signs out the current user. An intent is then used to
                              open the LogIn activity.
                  Below the action bar the activity has a searchbox and a search button. It 
                  also provides a Spinner for categories.
                        Searchbox and button -
                              When the button is pressed a search is done using whatever text
                              is in the searchbox. It does this by changing fragment to the
                              FindItem fragment and provides the searchTerm as an argument
                              in a bundle. If there is no searchTerm it simply reloads the
                              MainFeed.
                        Category Spinner -
                              the spinner has an item selected listener which checks if an
                              item has been selected and if they have picked an option other
                              than the default it changes fragment to the FindItem fragment
                              and provides the selected category as an argument in a bundle.
                              If no item was selected or the default it reloads the MainFeed.
                              
                   Below this section is the Fragment Container which stores a fragment over
                   about 80% of the screen. This is where all fragments are used so the majority
                   of the users activity occurs here. Beneath this there are four buttons. These 
                   allow the user to at any point move to different fragments to get to the main 
                   feed, create a post, update their profile or send a message.
                   
                   The Main Activity also has the basic infrastructure/functions for locating
                   a user and keeping this location up to date. Unfortunately we didn't have
                   time to implement this feature fully and test that it worked. It is also
                   where the user will be asked for permission for location services.
      
      Fragments -
      
            MainFeed - 
                  This fragment displays the items in the database on a scrolling view. This is
                  done by creating a database query for posts ordered by timestamp and placing the
                  results in a firestore recyclerview. In the recycler we fill item holders 
                  with item objects and display the information we need for an item listing in 
                  the item holder. There is also a click listener to take the user to the item
                  page of the item listing they click on we do this by swapping to an Item Page
                  fragment and send a parcelled item to this fragment via a bundle.
            CreatePost -
                  This fragment is responsible for allowing a user to create an item post and it
                  adds this to the database. The user can enter details of the item and pick an
                  image via an intent that opens their gallery and if they select an image it is 
                  then uploaded to firebasestorage and its Uri is stored in the firestore database. 
                  Their latitude and longitude is also added to the database. The user doesn't have 
                  to pick an image to advance as we have a default image for the item pages. Validation
                  checks are also performed for the textviews and category spinner as these fields must
                  be filled. Seller details are also added to the Item which is placed in the database.
            Profile -
                  This fragment allows a user to update their account email or password and re-log
                  if they haven't logged in recently. The re-log button was made defunct by the 
                  sign-out button added to main activity. That is now the intended way a user 
                  would re-log. The screen contains two text boxes and two update buttons. one 
                  of these calls the firebase Authentication method updateEmail and the other calls
                  updatePassword. This section was also supposed to contain validation checks similiar
                  to the LogIn screen.
            MessageScreen -
                  This fragment allows users to message each other through emails.
                   We allow users to type the to, subject and body of an email on the 
                   message screen. The from section is automatically filled from the 
                   current users email. We also fill in the to section if a user clicks 
                   on the message seller button on an item page. This is done using an 
                   Intent to request a chooser for email apps to send the email from.
            FindItem -
                  This fragment fills a scroll view with the results of a search query of the
                  database. This operates the same as MainFeed but it can receive a category
                  or search term as an argument which are used to create a query. This query
                  will either find items with the same category as the user selected or it
                  will search for item names beginning with the search term.
            ItemPage - 
                  This fragment is used to generate pages for items to be displayed on and 
                  bought from. It takes a parcelled Item object as an argument and displays
                  the data on screen through textviews mainly. It adds the item image to the
                  imageview using a command from the glide library. The users current latitude and
                  longitude are also retrieved and used to set a marker on a google map alongwith 
                  an item location marker. The camera focuses the mapview on the item location 
                  marker. There is also a get directions method which sends an intent with the two
                  sets of co-ordinates to launch the google maps app and enter the locations to
                  try and find a route from the user to the item. The map also allows zoom.
                  There is a message seller button which changes fragment to the MessageScreen and 
                  adds the email of the seller as an argument. The buy button will remove the 
                  item from the database provided it still exists and swaps to the PurchaseScreen
                  fragment otherwise it will send a toast to say item has been purchased
                  by another user. This section was also supposed to delete the image from
                  firebase storage upon successful purchase but was removed due to a lack
                  of testing.
            PurchaseScreen - 
                  Informs a user of a successful purchase and thanks them. This is where
                  transactions would occur if they were implemented but it currently just
                  displays a thanks for purchasing message.
                  
      Support Classes -
            Item -
                  This class is for creating Item objects that contain all the database
                  information for a single Item. It has get and set methods and a default
                  constructor. It also implements parcelable to allow the Items to be
                  transferred via bundles in an Intent.
            ItemHolder -
                  This class initializes the ItemHolder UI elements and implements all
                  required methods for a ViewHolder class. It also has a layout file that
                  sets the layout for individual ItemHolders.
                  
# Lessons Learned    
      
      Planning Features - 
            Our proposal was quite detailed but I noticed there some nice features from other
            marketplace websites and apps that you don't notice but are quite useful. For
            example I didn't think of a back button or sign-out button until the last two
            weeks of the project. I think that updating the proposal document throughout
            would've helped to see these small features that make an app easy to use.
     Prioritization -
            We tended to try and focus on adding new big features to the app but at times
            it would've been better to improve existing features to make them more complete
            or we spent a lot of time on a feature when it may have been better to add a few
            small features to other parts of the app.
     Search/database -
            We may have been better off using a different type of database as cloud firestore
            doesn't provide a means of full text search although it does suggest solutions like
            Algolia which I attempted to use however it required firebase cloud functions which 
            would've forced us to upgrade our firebase project to a pay as you go plan in order 
            to use.
      Search/Categories - 
            If we had the time it would've been better to implement these in such a way where we 
            could use both at the same time as search or category will both override the other if 
            the they are used.
      Locations -
            Unfortunately we didn't get to implement locations in time for the submission deadline 
            but we have managed to work out what went wrong as there was a permission check that was
            checking if we need to build a dialog box to ask the user for permission which wasn't
            showing properly so never let a user accept and advance but we realized that this was 
            actually uneccessary as we already check permissions on application start. 
            So we removed the if statement and dialogbox and the locations and permissions now 
            work in the latest version.
      CreatePost -
            We could've implemented checks apart from if fields are empty as we don't check for size
            of data a user enters which means they can make way too long item names and other issues.
            This is a way we need to limit the user as they can abuse this if it isn't properly
            validated.
                       
                 
