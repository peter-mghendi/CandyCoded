# Android Project
This project adds Explicit and Implicit Intents to the sample app for the Super Sweet Android Time course.  The sample app is a candy store app called Candy Coded that displays a list of the store's candy and details about each candy.  There is one Intent already implemented that launches the DetailActivity when a user selects a candy in the candy list in the MainActivity.

![GitHub Logo](/images/DetailIntent.png)

It's your job to add 4 more Intents to finish the functionality for the app!

## What We're Building Overview Video
http://www.viddler.com/v/a2575089

## Task 1 - Show Store Information Activity

![GitHub Logo](/images/InfoIntent.png)

We have already added an Information MenuItem to the MainActivity. Now we'd like to launch the InfoActivy class when that MenuItem is selected.
1. We want to override the `onOptionsItemSelected(MenuItem item)` method in `MainActivity.java`.  This is where will create the Intent to open the InfoActivity. (Hint: Ctrl+O will list the Overrride methods.  Then, type in the box to search and select the one you want.)
2. Our method needs to return a boolean, we will return the default call to the super class `return super.onOptionsItemSelected(item);`.
3. On the first line of the `onOptionsItemSelected()` method, create the Intent for launching the InfoActivity.  Call the `Intent infoIntent` and the two parameters to the `new Intent()` constructor will be `this` for the Context and `InfoActivity.class` for the Activity we want to start.
3. Next, call `startActivity()` with the `infoIntent` as a parameter to start our Intent.

## Task 2 - Launch the Google Maps Activity

![GitHub Logo](/images/MapIntent.png)

Our InfoActivy has a TextView with the address of our store.  We want to launch Google Maps at that address when that TextView is clicked.
1. In `InfoActivity.java`, create a method called `public void createMapIntent(View view)`.  This is the method we'll attach to the Click Listener on the TextView later.
2. Create a `Uri` from the address by calling the `Uri.parse()` method and passing in the String with the geo location of our store `"geo:0,0?q=618 E South St Orlando, FL 32801"`.
3. Create an `Intent` called `mapIntent` and pass two parameters to the constructor - first is the action which will be `Intent.ACTION_VIEW`, second is the Uri we just created.
4. Make the Intent explicit by setting the Google Maps package. We can do this with the Intent's `setPackage()` method and pass in the String `"com.google.android.apps.maps"`.
5. We will attempt to start an activity that can handle the Intent.  To do this create an if statement, inside call `mapIntent.resolveActivity(getPackageManager())` and check that the result is not equal to `null`.
6. Inside the if statement we can call `startActivity()` with our `mapIntent`.
7. Now we need to connect this method to the TextView's click event. Go into the layout file `activity_info.xml` and add the following properties to the TextView with the id `text_view_address` - `android:clickable="true"` and `android:onClick="createMapIntent"`.

## Task 3 - Launch the Phone Activity

![GitHub Logo](/images/PhoneIntent.png)

Our InfoActivity also has a TextView with the phone number of our store.  We want to launch a dial Intent with that phone number when that TextView is clicked.
1. In `InfoActivity.java`, create a method called `public void createPhoneIntent(View view)`.  This is the method we'll attach to the Click Listener on the TextView later.
2. Create an `Intent` with action `Intent.ACTION_DIAL`.
3. Use the Intent `setData()` method and pass in a `URI` of the telephone number `"tel:0123456789"`. You can create a `URI` with the `Uri.parse()` method.
4. Start the Activity with the Intent.
5. Now we need to connect this method to the button click. Go into `activity_info.xml` and add the following properties to the TextView with the id `text_view_phone` - `android:clickable="true"` and `android:onClick="createPhoneIntent"`.

## Task 4 - Share the Current Candy with an Intent

![GitHub Logo](/images/ShareIntent.png)

We have already added a Share MenuItem to the DetailActivity so that the user can share the selected Candy. We can do that by launching an Intent with action ACTION_SEND when that MenuItem is selected.
1. Override the `public boolean onOptionsItemSelected(MenuItem item)` method and have it return the default call to the super class `return super.onOptionsItemSelected(item);`.  (Hint: Ctrl+O will list the Overrride methods.  Then, type in the box to search and select the one you want.)
2. Since there is a big chunk of code to create the Intent let's do it in a separate method. Create a method called `private void createShareIntent()`.
3. In `onOptionsItemSelected()`, let's call the method we just wrote `createShareIntent()`.
4. Now inside our new `createShareIntent()` method, let's create an Intent called `shareIntent` with action `ACTION_SEND`.
5. Use the Intent's `setType()` method to set the type to `"text/plain"`.
6. To create the String that we want to share, we can use the String variables created at the top of this file - `SHARE_DESCRIPTION`, `mCandyImageUrl`, and `HASHTAG_CANDYCODED`.  We can concatenate them into one String variable set equal to `SHARE_DESCRIPTION + mCandyImageUrl + HASHTAG_CANDYCODED`
7. Use the Intent's `putExtra()` method to add the text we want to share. The first parameter is the type of content `Intent.EXTRA_TEXT`, and the second is our concatenated `String`.
8. Finally, we can finish off the `createShareIntent()` method by calling `startActivity()` with our `shareIntent`.
