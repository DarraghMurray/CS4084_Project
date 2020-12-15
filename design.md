# Technolgies Used
      Firebase Authentication - 
            We used firebase authentication to authenticate users with the ability to create and sign in users with an email and password and to authenticate   
            these on sign-in via our custom Log-in and Registration layout.
            
      Firebase Cloud Firestore - 
            We used firebase cloud firestore to add user item posts to a database when they create an item post. This allows us to store all relvant 
            information about items such as name and description. This lets us load items for other users and to use queries for search or categories.
            
      Firebase Cloud Storage - 
            We used firebase cloud storage to allow users to add images and store them to items and the Uri to the firebase storage for that image is stored in 
            the item post database to retrieve the image.
            
      Google Maps - 
            We used google maps to display a map for the user and it displays 2 markers at two locations the item location and user location and has a zoom feature.
            A button to get directions was also added which uses an Intent to open the google maps app and enter the two co-ordinates and find a route between them so a 
            user can save the route to their google maps app. 
            
      Gallery - 
            We used an Intent to allow the user when creating a post to click pick image from their gallery to use for the item. This image is stored in firebase storage and
            its Uri is stored in the firestore database collection of posts.
            
      Email -
            We allow users to type the to,subject and body of an email on the message screen. The from section is automatically filled from the current users email. We also fill
            in the to section if a user clicks on the message seller button on an item page. This is done using an Intent to request a chooser for email apps to send the email
            from.

# Design Choices

      Theme - 
            We choose to use the theme of UL's colours instead of a plain white. We matched the padding and text box's with this to give it a modern clean look while also                   making it easy to look at. The Item Shop Logo is one we made ourselves using Canva. 

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
                  registration to explain what happened.
                  
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
                   about 80% of the screen. Beneath this there are four buttons. These allow
                   the user to at any point move to different fragments to get to the main feed, 
                   create a post, update their profile or send a message.
                   
                   The Main Activity also has the basic infrastructure/functions for locating
                   a user and keeping this location up to date. Unfortunately we didn't have
                   time to implement this feature fully and test that it worked. It is also
                   where the user will be asked for permission for location services.
      
      Fragments -
      
            MainFeed - 
                  This fragment displays the items in the database on a scrolling view. This is
                  done creating a database query for posts order by timestamp and placing the
                  results in a firestore recyclerview.
                       
                 
