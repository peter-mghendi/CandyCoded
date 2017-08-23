# Android Project
This project adds Explicit and Implicit Intents to the sample app for the Super Sweet Android Time course.  The sample app is a candy store app called Candy Coded.

## Task 1 - Show Store Information Activity
We have already added an Information MenuItem to the MainActivity. Now we'd like to launch the InfoActivy class when that MenuItem is selected.
1. Override the `onOptionsItemSelected(MenuItem item)` method to create the Intent to open the InfoActivity.
2. Create the Intent to create the InfoActivity.  Call the `Intent infoIntent` and the two parameters will be `this` for the Context and `InfoActivity.class` for the Activity we want to start.
3. Call `startActivity()`` with the `infoIntent` as a parameter to start our Intent.
4. The method needs to return a boolean, we will `return true` to mean that we launched our Intent.

## Task 2 - Launch the Google Maps Activity
Our InfoActivy has a TextView with the address of our store.  We want to launch Google Maps at that address when that TextView is clicked.
1. Create a method called `public void createMapIntent(View view)`
2. Create a `Uri` from the address by calling the `Uri.parse()` method and passing in the String with the geo location of our store `"geo:0,0?q=618 E South St Orlando, FL 32801"`.
3. Create an `Intent` called `mapIntent` and pass two parameters to the constructor - first is the action which will be `Intent.ACTION_VIEW`, second is the Uri we just created.
4. Make the Intent explicit by setting the Google Maps package. We can do this with the Intent's `setPackage()` method and pass in the String `"com.google.android.apps.maps"`.
5. We will attempt to start an activity that can handle the Intent.  To do this create an if statement, inside call `mapIntent.resolveActivity(getPackageManager())` and make sure the result is not equal to `null`.
6. Inside the if statement we can call `startActivity()` with our `mapIntent`.
7. Now we need to connect this method to the button click. Go into activity_info.xml and add the following properties to the TextView with the id `text_view_address` - `android:clickable="true"` and `android:onClick="createMapIntent"`.

## Task 3 - Launch the Phone Activity
Our InfoActivity also has a TextView with the phone number of our store.  We want to launch a dial Intent with that phone number when that TextView is clicked.
1. In `InfoActivity.java`, create a method called `public void createPhoneIntent(View view)`.
2. Create an `Intent` with `ACTION_DIAL`.
3. Use the Intent `setData()` method and pass in a URI of the telephone number `"tel:0123456789"`.
4. Start the Activity with the Intent.
5. Now we need to connect this method to the button click. Go into `activity_info.xml` and add the following properties to the TextView with the id `text_view_phone` - `android:clickable="true"` and `android:onClick="createPhoneIntent"`.

## Task 4 - Share the Current Candy with an Intent
We have already added a Share MenuItem to the DetailActivity so that the user can share the selected Candy. We can do that by launching an Intent with action ACTION_SEND when that MenuItem is selected.
1. Override the `onOptionsItemSelected()`` method and have it `return true` only for now.
2. Since there is a big chunk of code to create the Intent let's do it in a separate method. Create a method called `private void createShareIntent()`.
3. Create an Intent called `shareIntent` with action `ACTION_SEND`.
4. Use the Intent's `setType()`` method to set the type to ``"text/plain"``.
5. Use the Intent's `putExtra()`` method to add the text we want to share. The first paramether is the type of content `Intent.EXTRA_TEXT`, and the second is our String `"..."`
6. We can now call `startActivity()` with our `shareIntent`.
7. Finally we want to go back to onOptionsItemSelected() and call the method we just wrote `createShareIntent()`.
