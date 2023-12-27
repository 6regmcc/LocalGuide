



App Functionality 

My app is built using a mixture of Activities Fragments and Jetpack Compose components. I used Firebase Auth, Firebase Real Time Database and Firebase storage for hosting images. 

My app allows people to register and sign into existing accounts. The app lists all reviews created by all users. Users can view existing reviews and edit the reviews they created. Users can also create new reviews. A review consists of a title, body, map coordinates and an image. 

List to UML diagram  https://i.postimg.cc/KYQvR1Rw/Screenshot-2023-12-27-at-13-42-57.png


UX  / DX 

The Login and Register views are created using Activities and ViewPresenters 
The rest of the app is created using Fragments and view models with Jetpack Compose components and a Nav Graph for navigation. For the fragments I implemented
unidirectional data flow using UI State flow

Personal Statment

I found this assignment very difficult. I decided very early to build the UI using Jetpack Compose and this turned out to be a huge mistake because I struggled to implement even the most basic functionality. But I was getting the hang of it by the end. The last functionality I added was the ability to add and edit a marker on a map component. This is probably the most complex functionality in the app but only took me 3 hours to build. If I had another week I probably could have added x3 more features. I would also have refactored ReviewViewModel, this file is a bit of a mess. Some of the code I used to trigger re-composition is a bit of a hack job.


References

I didn't realise I would need to reference the code so I don't have a reference for everything I used but I mostly relied on the official docs and Android Code Labs to complete the project.

https://developer.android.com/codelabs/basic-android-kotlin-training-fragments-navigation-component

https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state

https://developer.android.com/codelabs/jetpack-compose-state

https://developer.android.com/codelabs/jetpack-compose-basics

https://stackoverflow.com/questions/76447182/load-image-from-gallery-and-show-it-with-jetpack-compose

