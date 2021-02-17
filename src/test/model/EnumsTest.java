package model;

import org.junit.jupiter.api.Test;

import static model.person.ActivityLevel.*;
import static model.person.GoalIntensity.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnumsTest {

    @Test
    public void testActivityGetDesc() {
        assertEquals("Little to no activity/desk job", SEDENTARY.getDesc());
        assertEquals("Light exercise 1-3 day a week", LIGHTLY_ACTIVE.getDesc());
        assertEquals("Exercise 3-5 days a week", MODERATELY_ACTIVE.getDesc());
        assertEquals("Heavy exercise 6-7 days a week", VERY_ACTIVE.getDesc());
        assertEquals("Very heavy exercise daily/active job", ATHLETE.getDesc());
    }

    @Test
    public void testGoalIntensityGetDesc() {
        assertEquals("Lose around 0.25kg/week", LIGHT_LOSS.getDesc());
        assertEquals("Lose around 0.5kg/week", MODERATE_LOSS.getDesc());
        assertEquals("Lose around 1kg/week; warning: not recommended.", EXTREME_LOSS.getDesc());
        assertEquals("Maintain your weight", MAINTAIN.getDesc());
        assertEquals("Gain around 0.25kg/week", LIGHT_GAIN.getDesc());
        assertEquals("Gain around 0.5kg/week", MODERATE_GAIN.getDesc());
        assertEquals("Gain around 1kg/week", EXTREME_GAIN.getDesc());
    }
}
