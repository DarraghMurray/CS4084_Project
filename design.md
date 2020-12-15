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

      Activities -
      
            Theme - 
                  We choose to use the theme of UL's colours instead of a plain white.
                  We matched the padding and text box's with this to give it 
                  a modern clean look while also making it easy to look at.
                  The Item Shop Logo is one we made ourselves using Canva. 
      
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
