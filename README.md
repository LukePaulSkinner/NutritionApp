# NutritionApp

A health and nutrition android application.
<img src="https://i.imgur.com/YIBS2Qp.gif" img align="right" width="250" />
FoodItem
A FoodItem is responsible for storing information about a given piece of food. This includes data 
such as: the name, nutrition information, serving size and links to images of the food.
ExerciseItem
An ExerciseItem is responsible for storing information about a given set of exercise. This includes 
data such as: the type of exercise, calories burned and the length of time the exercise was 
performed for.
Custom Meal
A custom meal is used to store information about meals the user has created. This information 
consists of a list of FoodItems and a name.
HistoryItem
A HistoryItem is used to store all information about a given day. It consists of a name which is the 
date in string format, a list of FoodItems and a list of ExerciseItems.
The rest of the application mainly makes use of activities, the main ones being:
MainActivity
The main page of the application is used for entering what food the user has eaten, as well as their 
exercises performed in a day using the Nutritionix API. Also includes links to other pages.
FoodInfo
Displays information about a given FoodItem, accessed by holding down on an item in MainActivity.
Introduction
Only shown the first time the application is used. Provides a brief description of what the application 
can do using fragments.
AddFood
Allows the user to create custom meals by searching for multiple foods.
9
AccountPage
Allows the user to update personal information such as age, height, weight goals and more.
Achievements
Page used to display the users’ achievements and prompt them to share them on social medias.
History
Displays the user’s history as well as a graph which tracks the users predicted weight using BMR. The 
user can also tap on a HistoryItem to edit it.
HistoryEdit
Allows for a user to alter the information stored in a HistoryItem in the event that they forget to log 
something.
