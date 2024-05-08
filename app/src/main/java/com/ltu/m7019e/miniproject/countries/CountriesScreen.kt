package com.ltu.m7019e.miniproject.countries

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltu.m7019e.miniproject.countries.ui.screens.CountryDetailScreen
import com.ltu.m7019e.miniproject.countries.ui.screens.CountryListScreen
import com.ltu.m7019e.miniproject.countries.ui.screens.CountrySearchScreen
import com.ltu.m7019e.miniproject.countries.viewmodel.CountriesViewModel
import com.ltu.m7019e.miniproject.countries.viewmodel.CountryViewModel
import com.ltu.m7019e.miniproject.countries.viewmodel.SelectedCountryUiState

enum class CountriesScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.country_detail),
    Saved(title = R.string.saved_countries),
    Search(title = R.string.search_country)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesAppBar(
    currentScreen: CountriesScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    //switchScreen: () -> Unit,
    countriesViewModel: CountriesViewModel,
    modifier: Modifier = Modifier
) {
    var menuExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        //title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = {
                // Set the menu expanded state to the opposite of the current state
                menuExpanded = !menuExpanded
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Open Menu to select different movie lists"
                )
            }
            DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {

                DropdownMenuItem(
                    onClick = {
                        // Set the selected movie list to popular
                        countriesViewModel.getSavedCountries()
                        // Set the menu expanded state to false
                        menuExpanded = false

                    },
                    text = {
                        Text("Saved Countries")
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        // Set the selected movie list to popular
                        countriesViewModel.getAllCountries()
                        // Set the menu expanded state to false
                        menuExpanded = false

                    },
                    text = {
                        Text("All Countries")
                    }
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        //contentDescription = stringResource(R.string.back_button)
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesApp(
                 navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CountriesScreen.valueOf(
        backStackEntry?.destination?.route ?: CountriesScreen.List.name
    )
    val countriesViewModel: CountriesViewModel = viewModel(factory = CountriesViewModel.Factory)

    Scaffold(
        topBar = {
            CountriesAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                countriesViewModel = countriesViewModel
                //switchScreen = {}
            )
        }
    ) { innerPadding ->
        val countriesViewModel: CountriesViewModel = viewModel(factory = CountriesViewModel.Factory)

        NavHost(
            navController = navController,
            startDestination = CountriesScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = CountriesScreen.List.name) {
                CountryListScreen(
                    countryListUiState = countriesViewModel.countryListUiState,
                    countrySearchButtonClicked = {
                        navController.navigate(CountriesScreen.Search.name)},
                    countryListItemClicked = { country ->
                        countriesViewModel.setSelectedCountry(country)
                        navController.navigate(CountriesScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = CountriesScreen.Detail.name) {
                    CountryDetailScreen(
                        selectedCountryUiState = countriesViewModel.selectedCountryUiState,
                        countriesViewModel = countriesViewModel,
                    )
            }
             composable(route = CountriesScreen.Search.name) {
                 CountrySearchScreen(
                     countryListUiState = countriesViewModel.countryListUiState,
                     )
                }
            }
        }
    }
