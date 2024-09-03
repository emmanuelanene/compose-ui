package com.uilover.project1972.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


// Define color schemes to call for dark mode and light mode
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)


// Think of "@Composable" like a special label or sticker you put on a function to tell Jetpack Compose that this function is used to build parts of the user interface (UI). When you mark a function with @Composable, you're saying that this function can create and manage UI elements in your app. It allows Compose to handle things like rendering the UI and reacting to changes. Think of it as a tag that says, “This function builds the UI!"
@Composable
// This is a function an d it has 3 values. The goal of this function is to help define the app's theme depending on whether the phone is in light or dark mode
fun Project1972Theme(
    /*
        The function has 3 parameters/varaibles:
            darkTheme -> This variable returns a T/F value to check if the phone is in dark mode or light mode

            dynamicColor -> This is a feature that is available on android 12+. The dynamic color feature allows android users to align the color scheme of their phone to something simialr to the scheme of their wallpaper. SO if their wallpaper is orange, the setting app will have orange highlights and stuff

            content************************************
     */
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,

    /*
        content -> This is a variable to hold a "function datatype" and it builds the UI (because it's labelled @Composable). The function has no name, () - takes no parameters, and does not return any value (i.e. Unit), and it is marked as a @Composable - meaning that this function is capable of creating, building and managing UI components of the app
        @Composable: This label means the block of UI code that "content" holds can use Compose functions to build UI elements.
        () -> Unit: This is a type that represents a function with no input parameters (()) that doesn’t return anything (Unit).

        ""content: @Composable () -> Unit"" is like saying, “I need a function that builds UI, doesn’t need any input, and doesn’t give anything back.”. We are not defining the function here. We are just giving it as a dataType.
        You’re not creating the function itself here. You’re just saying that this parameter will hold a function with these characteristics.

        HOW IT'S USED:
            @Composable
            fun MyComponent(content: @Composable () -> Unit) {
                // Use the content function to build part of the UI
                content()
            }

            @Composable
            fun MyContent() {
                Text("Hello, World!")
            }

            MyComponent(content = { MyContent() })
     */
    content: @Composable () -> Unit
) {
    /*
        colorScheme --> variable name with switch and case (when...else)
     */
    val colorScheme = when {
        /*
        Case 1 --> check if dynamicCOlor is on in the phone && check if the person phone is running android 12 or greater (with API31). If both are true, then run the code inside
        Case 2 --> If case 1 is false, just check if the person's phone is running a dark theme/dark mode. If yes, then apply the "DarkCOlorScheme" for the app
        Case 3 --> If the persons phone is not in dark mode then use the "LightColorScheme"
         */
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                /*
                LocalContext.current: Think of Context as a box that contains everything your app needs to interact with the Android operating system. It acts as a bridge between your app and the OS.

                LocalContext.current: This is like opening that context box to see the Context currently being used by your app. By using LocalContext.current, you can access details such as:
                        - The phone’s current theme
                        - App resource files(like strings.xml, images files, etc.)
                        - Device sensors (e.g., if you're building a running app)
                 In summary, LocalContext.current lets you peek into the context box to get the information and resources your app needs to function properly.
                 */
                val context = LocalContext.current

                /*
                 "dynamicDarkColorScheme" --> this is a 3rd party library. We'll check if darkMode is on and if it is we'll call "dynamicDarkColorScheme(context)" and this will help us to get a color scheme that is suited for dark mode. "dynamicDarkColorScheme" will call "context" (which is the same as "LocalContext.current" and it will auto-generate a color palette based on the phone's wall paper.

                 *dynamicColorLightScheme(context) --> generates a palette for the theme of the app that matches the phone wallpaper and is consistent with Light mode(i.e. they'll use brighter colors)

                 So basically, if the phone has "dynamic color" feature turned on and the android version is 12 or greater, it'll then check for whether the phone is also in dark mode or light mode and create a theme palette for the app based on colors from the wallpaper.
                 */
                if (darkTheme) {
                    // This function creates a color scheme suited for dark mode. It uses the context (which is obtained through LocalContext.current) to generate a color palette that harmonizes with the phone’s wallpaper and is appropriate for dark mode.
                    dynamicDarkColorScheme(context)
                }
                else {
                    // This function creates a color scheme for light mode. It also uses the context to generate a color palette that matches the phone’s wallpaper and is suited for light mode, typically with brighter colors.
                    dynamicLightColorScheme(context)
                }
            }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }



    // "MaterialTheme" is a function for Jetpack compose. It allows us to add consistent styling to our app. The function has various parameters
    /*
    "colorScheme, typography and content" are the parameters of "MaterialTheme". Since we are callin the "MaterialTheme" function, we are expected to initialize these variables
        colorScheme -> see the "when...else" argument we defined directly above
        Typography      -> See "Type.kt"
        content         -> We just used the empty "content" for now. But if you want to see how it could be used, see line 53 - 65
     */
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}