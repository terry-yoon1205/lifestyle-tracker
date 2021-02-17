package ui;

import exceptions.DoesNotExistException;
import exceptions.InvalidInputException;
import exceptions.NotInitializedException;
import model.food.Food;
import model.food.FoodLog;
import model.food.FoodLogEntry;
import model.food.Meal;
import model.person.ActivityLevel;
import model.person.GoalIntensity;
import model.person.Person;
import model.routine.MuscleGroup;
import model.routine.Routine;
import model.routine.RoutineCollection;
import model.routine.Workout;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.Scanner;

import static model.person.ActivityLevel.*;
import static model.person.Person.DATE_FORMAT;
import static model.person.GoalIntensity.*;
import static model.food.Meal.*;
import static model.routine.MuscleGroup.*;

/**
 * LifestyleTrackerApp is a console-based user interface for this application.
 */

public class LifestyleTrackerApp {
    private Person user;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Lifestyle Tracker application
    public LifestyleTrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        System.out.println("Welcome to Lifestyle Tracker!");

        boolean keepGoing = true;
        int command;

        init();

        while (keepGoing) {
            initialMenu();
            input.nextLine();
            command = parseInputInt(input.nextLine());

            if (command == 2) {
                saveUser();
                keepGoing = false;
            } else {
                processInitialCommand(command);
            }
        }
    }

    // EFFECTS: saves the user to file
    private void saveUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(user);
            jsonWriter.close();
        } catch (IOException e) {
            System.out.println("File could not be saved.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes application and load user from file; if the file does not exist, create new user
    private void init() {
        jsonWriter = new JsonWriter("./data/user.json");
        input = new Scanner(System.in);
        try {
            jsonReader = new JsonReader("./data/user.json");
            user = jsonReader.read();
        } catch (IOException e) {
            createNewUser();
        }
    }

    // EFFECTS: parses user's string input to int; if cannot parse, prompt user to input again
    private int parseInputInt(String command) {
        try {
            return Integer.parseInt(command);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input type. Please enter an integer.");
            return parseInputInt(input.nextLine());
        }
    }

    // EFFECTS: parses user's string input to double; if cannot parse, prompt user to input again
    private double parseInputDouble(String command) {
        try {
            return Double.parseDouble(command);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input type. Please enter a non-integer value.");
            return parseInputDouble(input.nextLine());
        }
    }

    // EFFECTS: displays initial menu to user
    private void initialMenu() {
        System.out.println("\nSelect an option (1-2):");
        System.out.println("\t1: start tracking!");
        System.out.println("\t2: quit the app");
    }

    // MODIFIES: this
    // EFFECTS: processes user inputs to the initial menu
    private void processInitialCommand(int command) {
        if (command == 1) {
            userActions();
        } else {
            System.out.println("Invalid input! Please choose again (1-2).");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new user
    private void createNewUser() {
        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("Select your biological sex (m/f).");
        String command = input.nextLine().toLowerCase();
        boolean sex = chooseSex(command);

        System.out.println("What is your age?");
        int age = parseInputInt(input.nextLine());

        System.out.println("What is your height in centimeters?");
        int height = parseInputInt(input.nextLine());

        System.out.println("What is your weight in kilograms?");
        int weight = parseInputInt(input.nextLine());

        System.out.println("Select your activity level (1-5):");
        System.out.println("\t1: Sedentary - " + SEDENTARY.getDesc());
        System.out.println("\t2: Lightly active - " + LIGHTLY_ACTIVE.getDesc());
        System.out.println("\t3: Moderately active - " + MODERATELY_ACTIVE.getDesc());
        System.out.println("\t4: Very active - " + VERY_ACTIVE.getDesc());
        System.out.println("\t5: Athlete - " + ATHLETE.getDesc());
        ActivityLevel activity = chooseActivityLevel(parseInputInt(input.nextLine()));

        user = new Person(name, sex, age, height, weight, activity);

        userActions();
    }

    // EFFECTS: returns true if user chose male, false if female
    private boolean chooseSex(String command) {
        if (command.equals("m")) {
            return true;
        } else if (command.equals("f")) {
            return false;
        } else {
            System.out.println("Invalid input! Please choose again (m/f).");
            chooseSex(input.nextLine().toLowerCase());
        }
        return false;
    }

    // EFFECTS: returns an activity level of the user's choice
    private ActivityLevel chooseActivityLevel(int command) {
        switch (command) {
            case 1: return SEDENTARY;
            case 2: return LIGHTLY_ACTIVE;
            case 3: return MODERATELY_ACTIVE;
            case 4: return VERY_ACTIVE;
            case 5: return ATHLETE;
            default:
                System.out.println("Invalid input! Please choose again (1-5).");
                chooseActivityLevel(parseInputInt(input.nextLine()));
        }
        return null;
    }

    // EFFECTS: accesses user's routine collection or food log based on user's choice, or sets user's goals
    private void userActions() {
        System.out.println("Select an option (1-7):");
        System.out.println("\t1: access your workout routines");
        System.out.println("\t2: access your food log");
        System.out.println("\t3: view your goals");
        System.out.println("\t4: set a weight goal");
        System.out.println("\t5: set a daily calorie goal");
        System.out.println("\t6: set a daily macronutrients goal");
        System.out.println("\t7: go back");

        int command = parseInputInt(input.nextLine());
        if (!(command == 7)) {
            chooseAction(command);
        }
    }

    // EFFECTS: processes user input for userActions method
    private void chooseAction(int command) {
        switch (command) {
            case 1: operateRoutines();
                break;
            case 2: operateFoodLog();
                break;
            case 3: printGoals();
                break;
            case 4: setUserWeightGoal();
                break;
            case 5: setUserCalorieGoal();
                break;
            case 6: setUserMacroGoal();
                break;
            default: System.out.println("Invalid input!");
        }
        userActions();
    }

    // EFFECTS: prints out the user's goals; or a message if the user doesn't have goals set
    private void printGoals() {
        try {
            System.out.println("Weight goal: " + user.getWeightGoal() + " kg");
            System.out.println("\tGoal intensity: " + user.getGoalIntensity().getDesc());
        } catch (NotInitializedException e) {
            System.out.println("You have not set your weight goals yet!");
        }

        try {
            System.out.println("Calorie goal: " + user.getCalorieGoal() + " kcal");
        } catch (NotInitializedException e) {
            System.out.println("You have not set a calorie goal yet!");
        }

        try {
            System.out.println("\tCarbs goal: " + user.getCarbsGoal() + " g");
            System.out.println("\tFat goal: " + user.getFatGoal() + " g");
            System.out.println("\tProtein goal: " + user.getProteinGoal() + " g");
        } catch (NotInitializedException e) {
            System.out.println("You have not set your macro goals yet!");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the user's weight goal and goal intensity
    private void setUserWeightGoal() {
        System.out.println("Enter your target weight:");
        int weightGoal = parseInputInt(input.nextLine());
        try {
            user.setWeightGoal(weightGoal);
        } catch (InvalidInputException e) {
            System.out.println("Invalid weight. Please try again.");
        }

        System.out.println("Select the speed at which you want to achieve your goal:");
        chooseSetWeightGoal(weightGoal);
    }

    // EFFECTS: displays input options to set user's goal intensity for the method setUserWeightGoal
    private void chooseSetWeightGoal(int weightGoal) {
        if (user.getWeight() > weightGoal) {
            System.out.println("\t1: " + LIGHT_LOSS.getDesc());
            System.out.println("\t2: " + MODERATE_LOSS.getDesc());
            System.out.println("\t2: " + EXTREME_LOSS.getDesc());

            int command = parseInputInt(input.nextLine());
            user.setGoalIntensity(chooseGoalIntensity(command));
        } else if (user.getWeight() < weightGoal) {
            System.out.println("\t4: " + LIGHT_GAIN.getDesc());
            System.out.println("\t5: " + MODERATE_GAIN.getDesc());
            System.out.println("\t6: " + EXTREME_GAIN.getDesc());

            int command = parseInputInt(input.nextLine());
            user.setGoalIntensity(chooseGoalIntensity(command));
        } else {
            System.out.println("Your goal is to maintain your weight.");
            user.setGoalIntensity(MAINTAIN);
        }
    }

    // EFFECTS: chooses the user's weight goal intensity based on the user's command
    private GoalIntensity chooseGoalIntensity(int command) {
        switch (command) {
            case 1: return LIGHT_LOSS;
            case 2: return MODERATE_LOSS;
            case 3: return EXTREME_LOSS;
            case 4: return LIGHT_GAIN;
            case 5: return MODERATE_GAIN;
            case 6: return EXTREME_GAIN;
            default:
                System.out.println("Invalid input! Please choose again.");
                chooseGoalIntensity(parseInputInt(input.nextLine()));
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the user's calorie goal, either by recommendation or custom
    private void setUserCalorieGoal() {
        System.out.println("Select an option (1-3):");
        System.out.println("\t1: set a calorie goal based on your weight goal");
        System.out.println("\t2: set a custom calorie goal");
        System.out.println("\t3: go back");

        int command = parseInputInt(input.nextLine());
        if (!(command == 3)) {
            chooseSetUserCalorieGoal(command);
        }
    }

    // EFFECTS: processes user input and sets the user's calorie goal for the method setUserCalorieGoal
    private void chooseSetUserCalorieGoal(int command) {
        switch (command) {
            case 1:
                setRecommendedCalorieGoal();
                break;
            case 2:
                try {
                    System.out.println("Enter your custom calorie goal:");
                    user.setCalorieGoal(parseInputInt(input.nextLine()));
                    System.out.println("Your new calorie goal is: " + user.getCalorieGoal() + " kcal");
                } catch (NotInitializedException e) {
                    System.out.println("Unexpected error.");
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid amount of calories.");
                }
                break;
            default:
                System.out.println("Invalid input!");
                setUserCalorieGoal();
        }
    }

    // EFFECTS: sets the user's calorie goal according to the user's weight goal
    private void setRecommendedCalorieGoal() {
        try {
            user.setCalorieGoal(user.calculateCalories());
            System.out.println("Your new calorie goal is: " + user.getCalorieGoal() + " kcal");
        } catch (NotInitializedException e) {
            System.out.println("Please set a weight goal first.");
        } catch (InvalidInputException e) {
            System.out.println("Please enter a valid amount of calories.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the user's macronutrient goals
    private void setUserMacroGoal() {
        try {
            user.getCalorieGoal();

            System.out.println("Enter the proportion of carbs in your calorie goal. (e.g. 0.5)");
            double carbs = parseInputDouble(input.nextLine());

            System.out.println("Enter the proportion of fat in your calorie goal. (e.g. 0.2)");
            double fat = parseInputDouble(input.nextLine());

            System.out.println("Enter the proportion of protein in your calorie goal. (e.g. 0.3)");
            double protein = parseInputDouble(input.nextLine());

            user.setMacroGoal(carbs, fat, protein);
            System.out.println("Your current goals are:");
            System.out.println("\tcarbs: " + user.getCarbsGoal() + " g");
            System.out.println("\tfat: " + user.getFatGoal() + " g");
            System.out.println("\tprotein: " + user.getProteinGoal() + " g");
        } catch (NotInitializedException e) {
            System.out.println("Please set a calorie goal first.");
        } catch (InvalidInputException e) {
            System.out.println("Please enter valid proportions that add up to 1.");
            setUserMacroGoal();
        }
    }

    // MODIFIES: this
    // EFFECTS: performs operations on the user's routine collection
    private void operateRoutines() {
        RoutineCollection routines = user.getRoutines();

        System.out.println("Select an option (1-4):");
        System.out.println("\t1: list your routines");
        System.out.println("\t2: create a new routine");
        System.out.println("\t3: search and select a routine by name");
        System.out.println("\t4: go back");

        int command = parseInputInt(input.nextLine());
        switch (command) {
            case 1: System.out.println(routines.toString());
                operateRoutines();
                break;
            case 2: createAndAddRoutine(routines);
                operateRoutines();
                break;
            case 3: searchRoutine(routines);
                operateRoutines();
                break;
            case 4: break;
            default: System.out.println("Invalid input!");
                operateRoutines();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a routine to the user's routine collection
    private void createAndAddRoutine(RoutineCollection routines) {
        System.out.println("Name your routine:");
        String name = input.nextLine();

        Routine newRoutine = new Routine(name);
        routines.add(newRoutine);

        System.out.println("Would you like to add some workouts to your routine? (y/n)");
        String command = input.nextLine().toLowerCase();

        if (command.equals("y")) {
            addWorkout(newRoutine);
        }

        operateRoutine(newRoutine);
    }

    // EFFECTS: searches given routine collection for a routine, gives user the option to edit the routine or delete it
    private void searchRoutine(RoutineCollection routines) {
        System.out.println("Type the name of the routine:");
        String name = input.nextLine();

        Routine searchResult;

        try {
            searchResult = routines.search(name);

            System.out.println("Select an option (1-2):");
            System.out.println("\t1: view and edit the routine");
            System.out.println("\t2: delete the routine from the collection");

            int command = parseInputInt(input.nextLine());
            switch (command) {
                case 1: operateRoutine(searchResult);
                    break;
                case 2: routines.delete(searchResult);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        } catch (DoesNotExistException e) {
            System.out.println("Routine does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a workout to user's given routine
    private void addWorkout(Routine routine) {
        System.out.println("Name your workout:");
        String name = input.nextLine();

        System.out.println("How many reps per set?");
        int reps = parseInputInt(input.nextLine());

        System.out.println("How many sets?");
        int sets = parseInputInt(input.nextLine());

        System.out.println("How much weight in pounds? Type 0 if it's a bodyweight workout.");
        int weight = parseInputInt(input.nextLine());

        System.out.println("Select which muscle group it mainly trains (0-9):");
        displayMuscles();
        MuscleGroup muscle = chooseMuscle(parseInputInt(input.nextLine()));

        Workout newWorkout = new Workout(name, reps, sets, weight, muscle);
        routine.add(newWorkout);
    }

    // EFFECTS: displays a list of muscle groups
    private void displayMuscles() {
        System.out.println("\t0: " + CHEST.getDesc());
        System.out.println("\t1: " + ABS.getDesc());
        System.out.println("\t2: " + SHOULDERS.getDesc());
        System.out.println("\t3: " + BICEPS.getDesc());
        System.out.println("\t4: " + TRICEPS.getDesc());
        System.out.println("\t5: " + LATS.getDesc());
        System.out.println("\t6: " + QUADS.getDesc());
        System.out.println("\t7: " + GLUTES.getDesc());
        System.out.println("\t8: " + HAMSTRINGS.getDesc());
        System.out.println("\t9: " + CALVES.getDesc());
    }

    // EFFECTS: choose a muscle group for a workout based on user's choice
    private MuscleGroup chooseMuscle(int command) {
        switch (command) {
            case 0: return CHEST;
            case 1: return ABS;
            case 2: return SHOULDERS;
            case 3: return BICEPS;
            case 4: return TRICEPS;
            case 5: return LATS;
            case 6: return QUADS;
            case 7: return GLUTES;
            case 8: return HAMSTRINGS;
            case 9: return CALVES;
            default:
                System.out.println("Invalid input! Please choose again.");
                chooseMuscle(parseInputInt(input.nextLine()));
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: performs operations on the user's given routine
    private void operateRoutine(Routine routine) {
        System.out.println("Select an option (1-4):");
        System.out.println("\t1: list your workouts");
        System.out.println("\t2: add a workout");
        System.out.println("\t3: search and select a workout by name");
        System.out.println("\t4: go back");

        int command = parseInputInt(input.nextLine());
        switch (command) {
            case 1:
                System.out.println(routine.listToString());
                operateRoutine(routine);
                break;
            case 2: addWorkout(routine);
                operateRoutine(routine);
                break;
            case 3: searchWorkout(routine);
                operateRoutine(routine);
                break;
            case 4: break;
            default:
                System.out.println("Invalid input!");
                operateRoutine(routine);
        }
    }

    // EFFECTS: searches given routine for a workout, gives user the option to edit the workout or delete it
    private void searchWorkout(Routine routine) {
        System.out.println("Type the name of the workout:");
        String name = input.nextLine();

        Workout searchResult;

        try {
            searchResult = routine.search(name);

            System.out.println("Select an option (1-2):");
            System.out.println("\t1: view and edit the workout");
            System.out.println("\t2: delete the workout from the routine");

            int command = parseInputInt(input.nextLine());
            switch (command) {
                case 1: operateWorkout(searchResult);
                    break;
                case 2: routine.delete(searchResult);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        } catch (DoesNotExistException e) {
            System.out.println("Workout does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: perform operations on user's given workout
    private void operateWorkout(Workout w) {
        System.out.println(w.toString() + "\n");
        System.out.println("Select an option (1-6):");
        System.out.println("\t1: change the name of the workout");
        System.out.println("\t2: change the amount of reps per set");
        System.out.println("\t3: change the amount of sets");
        System.out.println("\t4: change the amount of weight");
        System.out.println("\t5: change the main muscle group trained by the workout");
        System.out.println("\t6: go back");

        int command = parseInputInt(input.nextLine());
        if (!(command == 6)) {
            chooseOperateWorkout(command, w);
        }
    }

    // EFFECTS: processes user input for operateWorkout
    private void chooseOperateWorkout(int command, Workout w) {
        switch (command) {
            case 1: System.out.println("Type the new name:");
                w.setName(input.nextLine());
                break;
            case 2: System.out.println("Type the new amount of reps:");
                w.setReps(parseInputInt(input.nextLine()));
                break;
            case 3: System.out.println("Type the new amount of sets:");
                w.setSets(parseInputInt(input.nextLine()));
                break;
            case 4: System.out.println("Type the new amount of weight:");
                w.setWeight(parseInputInt(input.nextLine()));
                break;
            case 5: System.out.println("Choose the muscle group:");
                displayMuscles();
                w.setMuscle(chooseMuscle(parseInputInt(input.nextLine())));
                break;
            default: System.out.println("Invalid input!");
        }
        operateWorkout(w);
    }

    // MODIFIES: this
    // EFFECTS: perform operations on the user's food log
    private void operateFoodLog() {
        FoodLog foodLog = user.getFoodLog();

        System.out.println("Select an option (1-4):");
        System.out.println("\t1: list your food log entries");
        System.out.println("\t2: access today's log entry");
        System.out.println("\t3: search and select a log entry by date");
        System.out.println("\t4: go back");

        int command = parseInputInt(input.nextLine());
        switch (command) {
            case 1: System.out.println(foodLog.toString());
                operateFoodLog();
                break;
            case 2: todayEntry(foodLog);
                operateFoodLog();
                break;
            case 3: searchLog(foodLog);
                operateFoodLog();
                break;
            case 4: break;
            default: System.out.println("Invalid input!");
                operateFoodLog();
        }
    }

    // MODIFIES: this
    // EFFECTS: accesses today's food log entry; if does not exist, create new
    private void todayEntry(FoodLog log) {
        System.out.println("Enter today's date (" + DATE_FORMAT + "):");
        String name = input.nextLine();
        FoodLogEntry todayEntry = null;

        try {
            todayEntry = log.search(name);
        } catch (DoesNotExistException e) {
            todayEntry = new FoodLogEntry(name);
            log.add(todayEntry);
        } finally {
            System.out.println("Would you like to add some foods to today's log? (y/n)");
            String command = input.nextLine().toLowerCase();

            if (command.equals("y")) {
                addFood(todayEntry);
            }
            operateFoodLogEntry(todayEntry);
        }
    }

    // EFFECTS: searches given food log for an entry, gives user the option to edit the entry or delete it
    private void searchLog(FoodLog log) {
        System.out.println("Type the date of the log you wish to obtain (" + DATE_FORMAT + "):");
        String name = input.nextLine();

        FoodLogEntry searchResult;

        try {
            searchResult = log.search(name);

            System.out.println("Select an option (1-2):");
            System.out.println("\t1: view and edit the entry");
            System.out.println("\t2: delete the entry from the log");

            int command = parseInputInt(input.nextLine());
            switch (command) {
                case 1: operateFoodLogEntry(searchResult);
                    break;
                case 2: log.delete(searchResult);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        } catch (DoesNotExistException e) {
            System.out.println("Entry with given date does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: performs operations on the user's selected food log entry
    private void operateFoodLogEntry(FoodLogEntry e) {
        System.out.println("Select an option (1-6):");
        System.out.println("\t1: list your foods");
        System.out.println("\t2: list your foods of a particular meal");
        System.out.println("\t3: add a food");
        System.out.println("\t4: search and select a food by name");
        System.out.println("\t5: get total calories");
        System.out.println("\t6: go back");

        int command = parseInputInt(input.nextLine());
        if (!(command == 6)) {
            chooseOperateFoodLogEntry(command, e);
        }
    }

    // EFFECTS: processes user input for operateFoodLogEntry
    private void chooseOperateFoodLogEntry(int command, FoodLogEntry e) {
        switch (command) {
            case 1:
                System.out.println(e.listToString());
                break;
            case 2: e.getFoodsByMeal(chooseMeal());
                break;
            case 3: addFood(e);
                break;
            case 4: searchFood(e);
                break;
            case 5:
                System.out.println("Your total calories in this entry is: " + e.sumCalories());
                System.out.println("\t" + BREAKFAST.getDesc() + ": " + e.sumCalories(BREAKFAST));
                System.out.println("\t" + LUNCH.getDesc() + ": " + e.sumCalories(LUNCH));
                System.out.println("\t" + DINNER.getDesc() + ": " + e.sumCalories(DINNER));
                System.out.println("\t" + SNACKS.getDesc() + ": " + e.sumCalories(SNACKS));
                break;
            default:
                System.out.println("Invalid input!");
        }
        operateFoodLogEntry(e);
    }

    // EFFECTS: chooses a meal based on the user's input
    private Meal chooseMeal() {
        System.out.println("Choose a meal:");
        System.out.println("\t1: " + BREAKFAST.getDesc());
        System.out.println("\t2: " + LUNCH.getDesc());
        System.out.println("\t3: " + DINNER.getDesc());
        System.out.println("\t4: " + SNACKS.getDesc());

        int command = parseInputInt(input.nextLine());
        switch (command) {
            case 1: return BREAKFAST;
            case 2: return LUNCH;
            case 3: return DINNER;
            case 4: return SNACKS;
            default:
                System.out.println("Invalid input! Please choose again.");
                chooseMeal();
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: creates and adds a new food in the given food log entry
    private void addFood(FoodLogEntry e) {
        System.out.println("Name your food:");
        String name = input.nextLine();

        Meal meal = chooseMeal();

        System.out.println("What is the amount of calories in this food?");
        int calories = parseInputInt(input.nextLine());

        System.out.println("Would you also like to record the amount of macronutrients? (y/n)");
        String command = input.nextLine().toLowerCase();

        if (command.equals("y")) {
            System.out.println("What is the amount of carbs in grams?");
            int carbs = parseInputInt(input.nextLine());

            System.out.println("What is the amount of fat in grams?");
            int fat = parseInputInt(input.nextLine());

            System.out.println("What is the amount of protein in grams?");
            int protein = parseInputInt(input.nextLine());

            Food newFood = new Food(name, meal, calories, carbs, fat, protein);
            e.add(newFood);
        } else if (command.equals("n")) {
            Food newFood = new Food(name, meal, calories);
            e.add(newFood);
        } else {
            System.out.println("Invalid input!");
        }
    }

    // EFFECTS: searches for a food in the given food log entry
    private void searchFood(FoodLogEntry e) {
        System.out.println("Type the name of the food:");
        String name = input.nextLine();

        Food searchResult;

        try {
            searchResult = e.search(name);

            System.out.println("Select an option (1-2):");
            System.out.println("\t1: view and edit the food");
            System.out.println("\t2: delete the food from the entry");

            int command = parseInputInt(input.nextLine());
            switch (command) {
                case 1: operateFood(searchResult);
                    break;
                case 2: e.delete(searchResult);
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        } catch (DoesNotExistException ee) {
            System.out.println("Food with given name does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: performs operations on the given food
    private void operateFood(Food f) {
        System.out.println(f.toString() + "\n");
        System.out.println("Select an option (1-7):");
        System.out.println("\t1: change the name of the food");
        System.out.println("\t2: change the meal at which the food was eaten");
        System.out.println("\t3: change the amount of calories");
        System.out.println("\t4: change the amount of carbs");
        System.out.println("\t5: change the amount of fat");
        System.out.println("\t6: change the amount of protein");
        System.out.println("\t7: go back");

        int command = parseInputInt(input.nextLine());
        if (!(command == 7)) {
            chooseOperateFood(command, f);
        }
    }

    // EFFECTS: processes user input for operateFood
    private void chooseOperateFood(int command, Food f) {
        switch (command) {
            case 1:
                System.out.println("Type the new name:");
                f.setName(input.nextLine());
                break;
            case 2: f.setMeal(chooseMeal());
                break;
            case 3:
                System.out.println("Type the new amount of calories:");
                try {
                    f.setCalories(parseInputInt(input.nextLine()));
                } catch (InvalidInputException e) {
                    System.out.println("Please enter a valid non-negative amount.");
                }
                break;
            case 4 - 6: chooseMacros(command, f);
                break;
            default:
                System.out.println("Invalid input!");
        }
        operateFood(f);
    }

    // EFFECTS: processes user input for chooseOperateFood for entering macronutrients
    private void chooseMacros(int command, Food f) {
        try {
            switch (command) {
                case 4:
                    System.out.println("Type the new amount of carbs:");
                    f.setCarbs(parseInputInt(input.nextLine()));
                    break;
                case 5:
                    System.out.println("Type the new amount of fat:");
                    f.setFat(parseInputInt(input.nextLine()));
                    break;
                case 6:
                    System.out.println("Type the new amount of protein.");
                    f.setProtein(parseInputInt(input.nextLine()));
                    break;
            }
        } catch (InvalidInputException e) {
            System.out.println("Please enter a valid non-negative amount.");
        }
        operateFood(f);
    }
}
