package com.musclemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.musclemate.data.ExerciseRepository
import com.musclemate.data.local.AppDatabase
import com.musclemate.data.local.ExerciseEntity
import com.musclemate.ui.AppViewModel
import com.musclemate.ui.AppViewModelFactory

private val BG = Color(0xFF07090D)
private val SURFACE = Color(0xFF111722)
private val BLUE = Color(0xFF3D8BFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.get(this)
        setContent { MuscleMateTheme { MuscleMateApp(ExerciseRepository(database.exerciseDao())) } }
    }
}

@Composable
private fun MuscleMateTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(primary = BLUE, background = BG, surface = SURFACE),
        content = content
    )
}

@Composable
private fun MuscleMateApp(repository: ExerciseRepository) {
    val vm: AppViewModel = viewModel(factory = AppViewModelFactory(repository))
    var started by remember { mutableStateOf(false) }
    if (!started) {
        Column(
            Modifier.fillMaxSize().background(BG),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("M", fontSize = 72.sp, fontWeight = FontWeight.Black, color = BLUE)
            Text("MUSCLEMATE", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(20.dp))
            Button({ started = true }) { Text("GET STARTED") }
        }
        return
    }

    val nav = rememberNavController()
    Scaffold(containerColor = BG, bottomBar = { BottomBar(nav) }) { padding ->
        NavHost(navController = nav, startDestination = "home", modifier = Modifier.padding(padding)) {
            composable("home") { Home(vm.exercises.collectAsState().value, nav) }
            composable("exercises") { Library(vm.exercises.collectAsState().value, nav) }
            composable("muscles") { Muscles(nav) }
            composable("muscle/{name}") { entry ->
                val name = entry.arguments?.getString("name") ?: ""
                Library(vm.exercises.collectAsState().value.filter { it.muscleGroup.equals(name, true) }, nav, name)
            }
            composable("favorites") { Favorites(vm, nav) }
            composable("detail/{id}") { entry ->
                val id = entry.arguments?.getString("id") ?: ""
                Detail(vm.exercises.collectAsState().value.firstOrNull { it.id == id }, vm, nav)
            }
            composable("workouts") { Simple("Workouts", "Workout builder is reserved for the next stage.") }
            composable("progress") { Simple("Progress", "Progress tracking is reserved for the next stage.") }
            composable("profile") { Simple("Profile", "MuscleMate profile.") }
        }
    }
}

@Composable
private fun BottomBar(nav: NavHostController) {
    val tabs = listOf(
        "home" to Icons.Default.Home,
        "exercises" to Icons.Default.FitnessCenter,
        "workouts" to Icons.Default.List,
        "progress" to Icons.Default.TrendingUp,
        "profile" to Icons.Default.Person
    )
    NavigationBar(containerColor = SURFACE) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = false,
                onClick = { nav.navigate(tab.first) { launchSingleTop = true } },
                icon = { Icon(tab.second, tab.first) },
                label = { Text(tab.first.uppercase(), fontSize = 8.sp) }
            )
        }
    }
}

@Composable
private fun Title(title: String, subtitle: String? = null) {
    Column(Modifier.padding(20.dp)) {
        Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        if (subtitle != null) Text(subtitle, color = Color.Gray)
    }
}

@Composable
private fun Home(exercises: List<ExerciseEntity>, nav: NavHostController) {
    LazyColumn {
        item {
            Title("MuscleMate", "Your visual gym companion")
            Button(
                onClick = { nav.navigate("muscles") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) { Text("EXPLORE MUSCLES") }
            Title("Popular Exercises")
        }
        items(exercises.take(8)) { exercise ->
            ExerciseCard(exercise) { nav.navigate("detail/" + exercise.id) }
        }
    }
}

@Composable
private fun Muscles(nav: NavHostController) {
    val muscles = listOf("Chest", "Back", "Shoulders", "Biceps", "Triceps", "Legs", "Glutes", "Abs", "Forearms", "Calves")
    LazyColumn {
        item { Title("Muscle Groups", "Choose a target") }
        items(muscles) { muscle ->
            SimpleCard(muscle) { nav.navigate("muscle/" + muscle) }
        }
    }
}

@Composable
private fun Library(
    exercises: List<ExerciseEntity>,
    nav: NavHostController,
    title: String = "Exercise Library"
) {
    var query by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("All") }

    val filtered = exercises.filter {
        (query.isBlank() || it.name.contains(query, true) || it.muscleGroup.contains(query, true) || it.equipment.contains(query, true)) &&
            (difficulty == "All" || it.difficulty == difficulty)
    }

    Column {
        Title(title, "Search by exercise, muscle or equipment")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            placeholder = { Text("Search") }
        )
        Row(Modifier.padding(10.dp)) {
            listOf("All", "Beginner", "Intermediate", "Advanced").forEach { level ->
                FilterChip(
                    selected = difficulty == level,
                    onClick = { difficulty = level },
                    label = { Text(level) },
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
        LazyColumn {
            items(filtered) { exercise ->
                ExerciseCard(exercise) { nav.navigate("detail/" + exercise.id) }
            }
        }
    }
}

@Composable
private fun SimpleCard(text: String, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(SURFACE),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text.uppercase(), fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ChevronRight, null, tint = BLUE)
        }
    }
}

@Composable
private fun ExerciseCard(exercise: ExerciseEntity, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(SURFACE),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(74.dp).background(Color(0xFF182232), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) { Text("ANIMATION", fontSize = 9.sp, color = BLUE, fontWeight = FontWeight.Bold) }
            Column(Modifier.padding(start = 14.dp)) {
                Text(exercise.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text(exercise.targetMuscle + " • " + exercise.equipment, color = Color.Gray, fontSize = 12.sp)
                Text(exercise.difficulty, color = BLUE, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun Detail(exercise: ExerciseEntity?, vm: AppViewModel, nav: NavHostController) {
    if (exercise == null) {
        Simple("Exercise", "Not found")
        return
    }
    LazyColumn {
        item {
            Column(Modifier.padding(20.dp)) {
                Box(
                    Modifier.fillMaxWidth().height(250.dp).background(Color(0xFF182232), RoundedCornerShape(22.dp)),
                    contentAlignment = Alignment.Center
                ) { Text("LOTTIE PLACEHOLDER\n" + exercise.name, color = BLUE, fontWeight = FontWeight.Bold) }

                Text(exercise.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 18.dp))
                Text(exercise.targetMuscle + " • " + exercise.equipment + " • " + exercise.difficulty, color = Color.Gray)

                Row(Modifier.padding(vertical = 16.dp)) {
                    Button({ vm.toggleFavorite(exercise.id) }) {
                        Text(if (exercise.isFavorite) "★ FAVORITED" else "☆ FAVORITE")
                    }
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton({ nav.navigate("workouts") }) { Text("ADD TO WORKOUT") }
                }

                Section("HOW TO PERFORM") {
                    exercise.instructions.split("|").forEachIndexed { index, instruction ->
                        Text((index + 1).toString() + ". " + instruction)
                    }
                }
                Section("COMMON MISTAKES") {
                    exercise.commonMistakes.split("|").forEach { Text("• " + it) }
                }
                Section("TARGET MUSCLES") {
                    Text(exercise.targetMuscle + " • " + exercise.secondaryMuscles)
                }
                Section("EQUIPMENT") { Text(exercise.equipment) }
            }
        }
    }
}

@Composable
private fun Section(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.padding(top = 18.dp)) {
        Text(title, color = BLUE, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        content()
    }
}

@Composable
private fun Favorites(vm: AppViewModel, nav: NavHostController) {
    val favorites = vm.favorites.collectAsState().value
    LazyColumn {
        item { Title("Favorites", "My Exercises") }
        if (favorites.isEmpty()) {
            item { Text("No favorite exercises yet.", color = Color.Gray, modifier = Modifier.padding(20.dp)) }
        }
        items(favorites) { exercise ->
            ExerciseCard(exercise) { nav.navigate("detail/" + exercise.id) }
        }
    }
}

@Composable
private fun Simple(title: String, message: String) {
    Column {
        Title(title)
        Text(message, color = Color.Gray, modifier = Modifier.padding(20.dp))
    }
}