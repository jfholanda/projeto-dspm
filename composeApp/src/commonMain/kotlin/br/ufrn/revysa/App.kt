package br.ufrn.revysa

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import androidx.navigation.toRoute

@Composable
fun App(
    deepLink: String? = null,
    deepLinkRequestId: Int = 0,
) {
    val controller = rememberNavController()
    val session = remember { StudySession() }
    var themeName by rememberSaveable { mutableStateOf(ThemeMode.System.name) }
    RevysaTheme(ThemeMode.valueOf(themeName)) {
        AppLayout(controller) {
            AppGraph(controller, session, ThemeMode.valueOf(themeName), deepLink, deepLinkRequestId) { themeName = it.name }
        }
    }
}

@Composable
private fun AppLayout(
    controller: NavHostController,
    content: @Composable () -> Unit,
) {
    val entry by controller.currentBackStackEntryAsState()
    BoxWithConstraints {
        val wide = maxWidth >= 840.dp
        Scaffold(
            topBar = {
                Row(Modifier.statusBarsPadding()) {
                    TextButton(
                        onClick = { if (!controller.popBackStack()) controller.navigate(Home) },
                        modifier = Modifier.heightIn(min = 48.dp).testTag("back"),
                    ) { Text("Voltar") }
                    TextButton(onClick = { controller.navigate(Home) { launchSingleTop = true } }) {
                        Text("Revysa", style = MaterialTheme.typography.titleLarge)
                    }
                }
            },
            bottomBar = { if (!wide) AppNavigation(controller, entry?.destination, false) },
        ) { padding ->
            Row(Modifier.padding(padding).imePadding().fillMaxSize()) {
                if (wide) AppNavigation(controller, entry?.destination, true)
                Column(Modifier.weight(1f)) { content() }
            }
        }
    }
}

@Composable
private fun AppNavigation(
    controller: NavHostController,
    destination: NavDestination?,
    wide: Boolean,
) {
    val items = listOf(Home, LibraryGraph, Review, Progress, Profile)
    val labels = listOf("Início", "Biblioteca", "Estudar", "Progresso", "Perfil")
    val symbols = listOf("⌂", "▤", "◇", "◷", "○")
    val selected =
        listOf(
            destination?.hasRoute<Home>() == true,
            destination?.hierarchy?.any { it.hasRoute<LibraryGraph>() } == true,
            destination?.hasRoute<Review>() == true,
            destination?.hasRoute<Progress>() == true,
            destination?.hasRoute<Profile>() == true,
        )
    val navigate: (Int) -> Unit = { index ->
        controller.navigate(items[index]) {
            popUpTo<Home> { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
    if (wide) {
        NavigationRail(Modifier.testTag("navigation-rail"), containerColor = MaterialTheme.colorScheme.surfaceVariant) {
            items.indices.forEach { index ->
                NavigationRailItem(
                    selected = selected[index],
                    onClick = { navigate(index) },
                    icon = { Text(symbols[index], modifier = Modifier.clearAndSetSemantics { }) },
                    label = { Text(labels[index]) },
                    modifier = Modifier.testTag("nav-$index"),
                )
            }
        }
    } else {
        NavigationBar(Modifier.testTag("navigation-bar"), containerColor = MaterialTheme.colorScheme.surfaceVariant) {
            items.indices.forEach { index ->
                NavigationBarItem(
                    selected = selected[index],
                    onClick = { navigate(index) },
                    icon = { Text(symbols[index], modifier = Modifier.clearAndSetSemantics { }) },
                    label = { Text(labels[index]) },
                    modifier = Modifier.testTag("nav-$index"),
                )
            }
        }
    }
}

@Composable
private fun AppGraph(
    controller: NavHostController,
    session: StudySession,
    theme: ThemeMode,
    deepLink: String?,
    deepLinkRequestId: Int,
    onTheme: (ThemeMode) -> Unit,
) {
    NavHost(controller, startDestination = Home) {
        composable<Home> { HomeScreen(session, controller::navigateTo) }
        navigation<LibraryGraph>(startDestination = Library) {
            composable<Library> { LibraryScreen(session, controller::navigateTo) }
            composable<Subjects> { SubjectsScreen(controller::navigateTo) }
            composable<Material>(deepLinks = listOf(navDeepLink<Material>(basePath = "revysa://material"))) { entry ->
                MaterialScreen(entry.toRoute<Material>().id, session, controller::navigateTo)
            }
            composable<Reader> { entry ->
                ReaderScreen(entry.toRoute(), session, controller::navigateTo)
            }
            composable<Notes> { entry -> NotesScreen(entry.toRoute(), session) }
            composable<CreateCard> { entry ->
                CreateCardScreen(entry.toRoute(), session) { controller.popBackStack() }
            }
            composable<Quiz> { entry -> QuizScreen(entry.toRoute<Quiz>().id, session) }
        }
        composable<Review> { ReviewScreen(session) }
        composable<Progress> { ProgressScreen(session, controller::navigateTo) }
        composable<Profile> { ProfileScreen(theme, onTheme, controller::navigateTo) }
        composable<Login> { AuthScreen(false, controller::navigateTo) }
        composable<Register> { AuthScreen(true, controller::navigateTo) }
    }
    LaunchedEffect(deepLink, deepLinkRequestId) {
        if (deepLink != null && controller.graph.hasDeepLink(NavUri(deepLink))) {
            controller.navigate(NavUri(deepLink), navOptions { launchSingleTop = true })
        }
    }
}

private fun NavHostController.navigateTo(route: Any) {
    navigate(route)
}
