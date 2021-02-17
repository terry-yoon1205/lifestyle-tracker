# Lifestyle Tracker

## What will the application do?

The *two main things* you could do with this application would be:
- Create **workout routines** and save them
- Log your **food intake** and track your calories and macronutrients

For the exercise aspect of this application, you will be able to create and save as many
workout routines as you'd like for future access and use. Each exercise will have its associated
**rep and set count**, **amount of weight**, and the **muscle group** it'll be training. You will be able to
change them as you progress through your workout journey. You can also set, log, and visualize your progress
towards your weight loss/gain goals.

For the food log, you will be able to log what you ate for breakfast, lunch, dinner, and snacks every day.
You can therefore track your **daily calorie intake**, and optionally your **macronutrient**
intake. The application will provide you daily calorie suggestions based on your weight loss/gain goal, and remind you 
if you have met or exceeded your daily calorie goal. You will also be able to set custom calorie or macronutrient 
goals. You will also be able to search foods you've added in the past and quickly log them to your 
daily log.

## Who will use it?

Anyone interested in having a better control of their lifestyle will be able to utilize this application, whether it 
pertains to exercise, food, or both. If you have a workout goal or a weight loss or gain goal, you will find
this application handy.

## Why is this project of interest to you?

To come up with an idea for my personal project, I thought about things that I personally need or would actually 
utilize. I love feeling in control of my life; I go to the gym and lift weights often weekly and track my calories in
order to achieve my weight gain goal. So I thought it would be fun and motivating to develop an
application about something that is a fairly big part of my daily life.

## User stories

As a user, I want to be able to:
- Create a new workout routine and add it to my collection of routines
- Select a routine in my collection and view the workouts inside of the routine
- Change the number of reps and sets of a workout in a routine
- Add the foods I ate in my daily food log
- Select a past date in my food log and view the foods and calories I ate that day
- Set a weight goal and/or a daily calorie goal
- Save my routines and food log to file
- Load my saved routines and past food logs from file

## Phase 4: Task 2

Implemented robust class(es) with methods that throw checked exceptions

- **Class**: Person
- **Methods**:
    - getWeightGoal() throws NotInitializedException
    - getGoalIntensity() throws NotInitializedException
    - getCalorieGoal() throws NotInitializedException
    - getCarbsGoal() throws NotInitializedException
    - getFatGoal() throws NotInitializedException
    - getProteinGoal() throws NotInitializedException
    - setCarbsGoal(int) throws InvalidInputException
    - setFatGoal(int) throws InvalidInputException
    - setProteinGoal(int) throws InvalidInputException
    - setWeightGoal(int) throws InvalidInputException
    - setCalorieGoal(int) throws InvalidInputException
    - setMacroGoal(double, double, double) throws NotInitializedException, InvalidInputException
    - calculateCalories() throws NotInitializedException
    
## Phase 4: Task 3

I definitely think the projected could be refactored to be better. Some changes I could make:
- Change the way some collections are implemented. For example, as to not allow duplicates but keep the
  elements in order, use a TreeSet. For FoodLogEntry, a SortedMap may be a good choice as it can be sorted naturally
  by date.
  
- Make the name field of FoodLogEntry utilize the Date class to make sorting and parsing/restricting user input easier.

- Make the GUI components more structured and hierarchical to eliminate any remaining duplication (improve cohesion).

- Somehow merge the abstract classes Entry and Collection because they are virtually identical except for the fact that
  they are extended by classes of different "levels" in the collection hierarchy.
  
- The abstract class SingleUnit probably wasn't all that necessary as it holds very trivial functionality and nothing
  else except its subclasses depends on it.

- Improve cohesion of the Person class. It does too many things. For example, extract the weight goals and food goals 
  components.