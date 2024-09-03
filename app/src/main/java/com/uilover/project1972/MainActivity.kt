package com.uilover.project1972

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


/*
    ComponentActivity() - notice that we are not extending the usual AppCOmpatActivity. We are extending a Jetpack compose library
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        *************************
        window.setFlags: This method is used to set certain flags (settings that can be used to modify the behaviour of a window in the app) for the activity's window.

        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS: This specific flag removes all limits on the layout of the activity's window. In other words, it allows the layout to extend into areas like the status bar or navigation bar, creating an immersive experience. This allows your content to extend into those areas, creating a full-screen or immersive experience.

        For example, in this app, it gives a full screen experience. So the action bar is removed and it does not show in the app.
         */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        // "setContent" is a Jetpack COmpose function that defines the content that would be rendered in the User Interface
        setContent {
            MyUI()
        }
    }

//    11111111111111111111111111111111111
    @Composable
    @Preview    // Used to preview this Composable function without the need to run the entire app
    fun MyUI() {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            bottomBar = {
                MyBottomBar()
            }, scaffoldState = scaffoldState
        ) {
            // Column -> JC function used to hold the UI elements of the app
            Column(
                // The Modifier parameter of Column is used to modify appearance and behaviour of the column
                modifier = Modifier
                    .fillMaxSize()  // ensures the column fills the entire screen
                    .background(Color.White)    // sets the bg color of the column
                    .verticalScroll(rememberScrollState())      // Makes the content of the column scrollable if it extends the viewport height
                    .padding(paddingValues = it),   // Give the column padding
                horizontalAlignment = Alignment.CenterHorizontally,  // Aligns the children of this column horizontally tot he center
            ) {
                // Children of the Column
                SearchRow()
                Banner()
                Categories()
                PopularCourses()
                ItemList()
            }
        }
    }

    data class BottomMenuItem(
        val label: String,
        val icon: Painter
    )

    @Composable
    fun PrepareBottomMenu(): List<BottomMenuItem> {
        val bottomMenuImemList = arrayListOf<BottomMenuItem>()

        bottomMenuImemList.add(
            BottomMenuItem(
                label = "Explorer",
                icon = painterResource(id = R.drawable.btn_1)
            )
        )
        bottomMenuImemList.add(
            BottomMenuItem(
                label = "Wishlist",
                icon = painterResource(id = R.drawable.btn_2)
            )
        )
        bottomMenuImemList.add(
            BottomMenuItem(
                label = "My Course",
                icon = painterResource(id = R.drawable.btn_3)
            )
        )
        bottomMenuImemList.add(
            BottomMenuItem(
                label = "Profile",
                icon = painterResource(id = R.drawable.btn_4)
            )
        )

        return bottomMenuImemList
    }

    private @Composable
    fun MyBottomBar() {
        val bottomMenuItemsList = PrepareBottomMenu()      // THe variable "bottomMenuItemsList" calls PrepareBottomMenu() to get a list of items (with labels and icons) that will be used in the bottom navigation bar.
        val contextForToast = LocalContext.current.applicationContext   // Retrieves the current application context.

        // "remember" is used to create a STATE holder for the item being selected in the menu and then it assigns that state to the variable "selectedItem". Creates a state holder for selectedItem, initialized with "Profile". This means that initially, the "Profile" item is considered as selected.
        var selectedItem by remember {
            mutableStateOf("Profile")   // "Profile" is the name of one of the labels in "bottomMenuImemList". SO we are setting the default menu item to "Profile" but once anoter item is clicked in the menu, the state uppdates
        }


        // BottomAppBar -> Composable function that creates a bottom navigation bar in the UI
        BottomAppBar(
            cutoutShape = RoundedCornerShape(10.dp),    // Effect is not visible here
            backgroundColor = Color(android.graphics.Color.parseColor("#f8f8f8")),
            elevation = 15.dp // Effect is not visible here. It adds a shadow below the BottomAppBar to give it a raised appearance. The 3.dp value indicates the depth of the shadow.
        ) {
            bottomMenuItemsList.forEachIndexed { index, bottomMenuItem ->       // Iterates over each BottomMenuItem in the bottomMenuItemsList to create a BottomNavigationItem for each.
                // index - this is like numbering for position of each menu item. It's not used tho
                BottomNavigationItem(
                    selected = (selectedItem == bottomMenuItem.label),      // A boolean that determines whether this item is currently selected. It compares selectedItem with bottomMenuItem.label.
                    onClick = { selectedItem = bottomMenuItem.label },      //  A lambda function that updates selectedItem to the label of the clicked item.
                    icon = {        // Defines the icon for the item.
                        Icon(
                            painter = bottomMenuItem.icon,
                            contentDescription = bottomMenuItem.label,
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                        )
                    },
                    label = {       // Defines the text label for the item
                        Text(
                            text = bottomMenuItem.label,
                            modifier = Modifier.padding(4.dp)
                        )
                    },
                    alwaysShowLabel = true,     // Ensures the label text is always visible, not just when the item is selected.
                    enabled = true      // Specifies whether the item is enabled and can be clicked...rrr
                )
            }
        }
    }



    data class Items(
        val title: String,
        val name: String,
        val price: Int,
        val score: Double,
        val picUrl: Int
    )


    @Composable
    fun ItemList() {
        val people: List<Items> = listOf(
            Items("Quick Learn C# Language", "Jammie young", 128, 4.6, R.drawable.pic1),
            Items("Full Course android Kotlin", "Alex Alba", 368, 4.2, R.drawable.pic2),
            Items("Quick Learn C# Language", "Jammie young", 128, 4.6, R.drawable.pic1),
            Items("iOS Developer Bootcamp", "Aleza Jamie", 128, 4.6, R.drawable.pic1),
        )

        LazyRow(    // LazyRow is used when you want to handle an infinite number of items WHILE Row is used when handling a fixed number of items
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),  // Set a padding around the entire content of the row (not the individual items
            horizontalArrangement = Arrangement.spacedBy(16.dp)     // Sets a gap/spacing between the individual components of the LazyROw
        ) {
            // This function takes a list of data (people in this case) and creates a list of items in the UI based on this data. It iterates over each element in the list and provides it to the content lambda function.

            /*
                It's like a loop BUT for Jetpack Compose. So instead of saying:
                    for (item in people) {
                        // Create UI components for each item
                    }
                We say:
                    items(people) { item ->
                        // Create UI components for each item
                    }
             */
            items(people) { item ->
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                        .shadow(3.dp, shape = RoundedCornerShape(10.dp))
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .clickable {       // "clickbable" makes the entire UI clickable. It makes the column behave like a button. And once a component is clicked, we want to print the name of that component to the console.
                            println("Clicked on: ${item.name}")
                        }
                ) {
                    // "IntrinsicSize.Max" - tells the constraintLayout to "wrapContent" height-wise
                    ConstraintLayout(modifier = Modifier.height(IntrinsicSize.Max)) {
                        val (topImg, title, owner, ownerIcon, price, score, scoreIcon) = createRefs()

                        Image(
                            painter = painterResource(id = item.picUrl),
                            contentDescription = null,
                            Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .constrainAs(topImg) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                },
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = item.title,
                            Modifier
                                .background(Color(android.graphics.Color.parseColor("#90000000")))
                                .fillMaxWidth()
                                .padding(6.dp)
                                .constrainAs(title) {
                                    bottom.linkTo(topImg.bottom)
                                    start.linkTo(parent.start)
                                },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Image(painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier
                                .constrainAs(ownerIcon) {
                                    start.linkTo(parent.start)
                                    top.linkTo(topImg.bottom)
                                }
                                .padding(start = 16.dp, top = 16.dp)
                        )

                        Text(text = "${item.name}", modifier = Modifier
                            .constrainAs(owner) {
                                start.linkTo(ownerIcon.end)
                                top.linkTo(ownerIcon.top)
                                bottom.linkTo(ownerIcon.bottom)
                            }
                            .padding(start = 16.dp, top = 16.dp)
                        )
                        Text(text = "$${item.price}",
                            fontWeight = FontWeight.Bold,
                            color = Color(android.graphics.Color.parseColor("#521c98")),
                            modifier = Modifier
                                .constrainAs(price) {
                                    start.linkTo(ownerIcon.start)
                                    top.linkTo(ownerIcon.bottom)
                                    bottom.linkTo(parent.bottom)
                                }
                                .padding(start = 16.dp, top = 8.dp)
                        )
                        Text(
                            text = item.score.toString(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .constrainAs(score)
                                {
                                    end.linkTo(parent.end)
                                    top.linkTo(price.top)
                                    bottom.linkTo(price.bottom)
                                }
                                .padding(end = 16.dp)
                        )

                        Image(painter = painterResource(id = R.drawable.star),
                            contentDescription = null,
                            modifier = Modifier
                                .constrainAs(scoreIcon) {
                                    end.linkTo(score.start)
                                    top.linkTo(score.top)
                                    bottom.linkTo(score.bottom)
                                }
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun PopularCourses() {
        Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {

            Text(
                text = "Popular Courses", color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "See all",
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#521c98")),
                fontSize = 16.sp
            )
        }
    }

    @Composable
    fun Categories() {
        Row(modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {

            Text(
                text = "Category", color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "See all",
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#521c98")),
                fontSize = 16.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,     // Centers the column horizontally within the row
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)

        ) {
            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat1),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )

                Text(
                    text = "Business",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat2),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )

                Text(
                    text = "Creative",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat3),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )

                Text(
                    text = "Coding",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#521c98")),
                )
            }

            Column(
                modifier = Modifier.weight(0.25f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cat4),
                    contentDescription = null,
                    Modifier
                        .padding(top = 8.dp, bottom = 4.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor("#f0e9fa")),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )

                Text(
                    text = "Gaming",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color(android.graphics.Color.parseColor("#521c98")),
                )
            }
        }
    }


//  333333333333333333333333333333333333333333333333
    @Composable
    fun Banner() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .height(160.dp)
                .background(
                    color = Color(android.graphics.Color.parseColor("#521c98")),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            val (img, text, button) = createRefs()      // Creates ID/references for the image, text, and button, allowing you to position them relative to each other and the parent.
            Image(
                painter = painterResource(id = R.drawable.girl2),
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(img) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )
            Text(text = "Advanced certification\nProgram in AI",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )
            Text(text = "Buy Now",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(android.graphics.Color.parseColor("#521c98")),
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .constrainAs(button) {
                        top.linkTo(text.bottom)
                        bottom.linkTo(parent.bottom)
                    }

                    .background(
                        Color(android.graphics.Color.parseColor("#f0e9fa")),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp)
            )

        }
    }


//    222222222222222222222222222222222222222222
    @Composable
    fun SearchRow() {
        // Similar to "Column" function but row arranges it scontent horizontally while column arranges its content vertically
        Row(
            // similar to the modifier of column - they are parameters to modify the layout
            modifier = Modifier
                .fillMaxWidth() // This ROw should take up the entire width
                .padding(top = 48.dp, start = 16.dp, end = 8.dp), // Padding to the search box
            verticalAlignment = Alignment.CenterVertically // Aligns the children within this function to be centered vertically
        ) {
            // "rememberSaveable" is used to create a state variable for "text" . "text" hold the data of the inputText when data is entered into the searchBox. And "rememberSavable" helps to make "text" a state varaible and rememberSaveable ensures that the state is saved across configuration changes (like screen rotations).

            // so if you're typing something and you rotate your phone, that thing you typed will remain in the searchbox
            var text by rememberSaveable { mutableStateOf("") }

            // UI element to create a textbox
            TextField(
                value = text,   // value for the textBox
                onValueChange = { text = it },      // callback function that updates the "text" when the input text is changed
                label = { Text(text = "Searching ...", fontStyle = FontStyle.Italic) }, // placeholder text shown in the searchBox when is empty
                leadingIcon = {     // Icon displayed in the textBox before the text
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(23.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),      // shape of the textBox -> here, its rounded corners


                // Customizes the colors of the TextField (background, borders, text, etc.)
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White,
                    focusedBorderColor = Color.Blue,    // applied once the user begins to type
                    unfocusedBorderColor = Color.Red,   // applied when the user is not interacting with the textBox
                    textColor = Color(android.graphics.Color.parseColor("#5e5e5e")),
                    unfocusedLabelColor = Color(android.graphics.Color.parseColor("#5e5e5e"))   // This color is applied to the label (the text that appears above or inside the TextField) when the TextField is not focused (i.e., when the user is not actively interacting with it).
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)     // This allows the TextField to take up the remaining space in the Row
                    .border(
                        1.dp,
                        Color(android.graphics.Color.parseColor("#521C98")),
                        shape = RoundedCornerShape(8.dp)
                    )

                    .background(Color.White, CircleShape)
            )

            // "Image" - JC function used to display an image (Notification Icon)
            Image(
                // Looad the image
                painter = painterResource(id = R.drawable.bell),
                // Image Description
                contentDescription = null,
                // modifies the image
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
