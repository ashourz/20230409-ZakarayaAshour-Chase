package com.example.weatherapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScaffoldTest {

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
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithText("Weather App").assertIsDisplayed()
        }
    }

    @Test
    fun testCitySearchTextFieldIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription("City Search")
                .assertHasClickAction().assertIsDisplayed()
        }
    }

    @Test
    fun testCurrentLocationButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription("Get Current Location")
                .assertHasClickAction().assertIsDisplayed()
        }
    }

    @Test
    fun testSearchButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            composeTestRule.onNodeWithContentDescription("Search City Weather").assertHasClickAction().assertIsDisplayed()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testSearchButtonClicked() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            val searchTextField = composeTestRule.onNodeWithContentDescription("City Search")
                .assertHasClickAction().assertIsDisplayed()
            val searchButton = composeTestRule.onNodeWithContentDescription("Search City Weather")
            searchTextField.performTextClearance()
            searchTextField.performTextInput("Paris")
            searchButton.performClick()
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Dropdown City Item"))
            val dropdownItems = composeTestRule.onAllNodesWithContentDescription("Dropdown City Item")
            dropdownItems.assertAny(hasContentDescription("Dropdown City Item"))
            val firstDropDownItem = dropdownItems.onFirst()
            firstDropDownItem.performClick()
            composeTestRule.waitForIdle()
            //Assert Data is retrieved
            val currentDataName = composeTestRule.onNodeWithContentDescription("Current Data Name").assertIsDisplayed()
            currentDataName.assert(hasText("Paris", substring = true))
            composeTestRule.waitUntilAtLeastOneExists(hasText("Paris", substring = true))
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Weather Icon"))
            composeTestRule.onNodeWithContentDescription("Weather Icon").assertIsDisplayed()
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Temperature Value"))
            composeTestRule.onNodeWithContentDescription("Temperature Value").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Feels Like").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Low Temp").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("High Temp").assertIsDisplayed()
        }
    }

    /**
     * Requires manual granting of location permissions to succeed.
     * */
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testCurrentLocationButtonClicked() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            val locationButton = composeTestRule.onNodeWithContentDescription("Get Current Location").assertHasClickAction()
            locationButton.performClick()
            composeTestRule.waitForIdle()
            //Assert Weather Data on Screen
            composeTestRule.onNodeWithContentDescription("Current Data Name").assertIsDisplayed()
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Weather Icon"))
            composeTestRule.onNodeWithContentDescription("Weather Icon").assertIsDisplayed()
            composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Temperature Value"))
            composeTestRule.onNodeWithContentDescription("Temperature Value").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Feels Like").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Low Temp").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("High Temp").assertIsDisplayed()
        }
    }

}