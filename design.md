# Technolgies Used
      Firebase Authentication - We used firebase authentication to authenticate users with the ability to create and sign in users with an email and password and to authenticate   
                                these on sign-in via our custom Log-in and Registration layout.
      Firebase Cloud Firestore - We used firebase cloud firestore to add user item posts to a database when they create an item post. This allows us to store all relvant 
                                 information about items such as name and description. This lets us load items for other users and to use queries for search or categories.
      Firebase Cloud Storage - We used firebase cloud storage to allow users to add images and store them to items and the Uri to the firebase storage for that image is stored in 
                               the item post database to retrieve the image.
      Google Maps - We used google maps to display a map for the user and it displays 2 markers at two locations the item location and user location and has a zoom feature.
                    A button to get directions was also added which uses an Intent to open the google maps app and enter the two co-ordinates and find a route between them so a 
                    user can save the route to their google maps app. 
      Gallery - We used an Intent to allow the user when creating a post to click pick image from their gallery to use for the item. This image is stored in firebase storage and
                its Uri is stored in the firestore database collection of posts.
      Email - We allow users to type the to,subject and body of an email on the message screen. The from section is automatically filled from the current users email. We also fill
              in the to section if a user clicks on the message seller button on an item page. This is done using an Intent to request a chooser for email apps to send the email
              from.
