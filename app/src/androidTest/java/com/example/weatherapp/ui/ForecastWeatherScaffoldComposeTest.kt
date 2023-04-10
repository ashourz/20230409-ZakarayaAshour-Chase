package com.example.weatherapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.MainActivity
import com.example.weatherapp.ui.navigation.NavDestinationEnum
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ForecastWeatherScaffoldComposeTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testAppBarTitleIsDisplayed() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            composeTestRule.onNodeWithText("Weather App").assertIsDisplayed()
        }
    }

    @Test
    fun testCitySearchTextFieldIsDisplayed() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            composeTestRule.onNodeWithContentDescription("City Search")
                .assertHasClickAction().assertIsDisplayed()
        }
    }

    @Test
    fun testCurrentLocationButtonIsDisplayed() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            composeTestRule.onNodeWithContentDescription("Get Current Location").assertHasClickAction().assertIsDisplayed()
        }
    }

    @Test
    fun testSearchButtonIsDisplayed() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            composeTestRule.onNodeWithContentDescription("Search City Weather").assertHasClickAction().assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testSearchButtonClicked() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            val searchTextField = composeTestRule.onNodeWithContentDescription("City Search")
                .assertHasClickAction().assertIsDisplayed()
            val searchButton = composeTestRule.onNodeWithContentDescription("Search City Weather")
            searchTextField.performTextClearance()
            searchTextField.performTextInput("Paris")
            searchButton.performClick()
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Dropdown City Item").and(hasText("Paris", substring = true)))
            val dropdownItems = composeTestRule.onAllNodesWithContentDescription("Dropdown City Item")
            dropdownItems.assertAny(hasContentDescription("Dropdown City Item"))
            val firstDropDownItem = dropdownItems.onFirst()
            firstDropDownItem.performClick()
            //Assert Data is retrieved
            val currentDataName = composeTestRule.onNodeWithContentDescription("Current Data Name").assertIsDisplayed()
            composeTestRule.waitUntilAtLeastOneExists(hasText("Paris", substring = true).and(hasContentDescription("Current Data Name")))
            currentDataName.assert(hasText("Paris", substring = true))
            composeTestRule.onAllNodesWithContentDescription("Temperature Value").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("Feels Like").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("Low Temp").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("High Temp").onFirst().assertIsDisplayed()
        }
    }

    /**
     * Requires manual granting of location permissions to succeed.
     * */
    @Test
    fun testCurrentLocationButtonClicked() {
        composeTestRule.waitForIdle()
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription(NavDestinationEnum.FIVEDAY_FORECAST.name).assertIsDisplayed().performClick()
            val locationButton = composeTestRule.onNodeWithContentDescription("Get Current Location").assertHasClickAction()
            locationButton.performClick()
            //Assert Weather Data on Screen
            composeTestRule.onNodeWithContentDescription("Current Data Name").assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("Temperature Value").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("Feels Like").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("Low Temp").onFirst().assertIsDisplayed()
            composeTestRule.onAllNodesWithContentDescription("High Temp").onFirst().assertIsDisplayed()
        }
    }

}