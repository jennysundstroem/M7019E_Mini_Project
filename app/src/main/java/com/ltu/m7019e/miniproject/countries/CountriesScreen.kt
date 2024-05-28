package com.ltu.m7019e.miniproject.countries

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.ltu.m7019e.miniproject.countries.ui.screens.CountryMapScreen
import com.ltu.m7019e.miniproject.countries.viewmodel.CountriesViewModel

enum class CountriesScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.country_detail),
    Saved(title = R.string.saved_countries),
    Map(title = R.string.map_country)
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
        title = { Text(stringResource(currentScreen.title),
            style = MaterialTheme.typography.displayMedium
        ) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            IconButton(onClick = {
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
                        countriesViewModel.getSavedCountries()
                        menuExpanded = false

                    },
                    text = {
                        Text("Saved Countries",
                            style = MaterialTheme.typography.labelSmall)
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        countriesViewModel.getAllCountries()
                        menuExpanded = false

                    },
                    text = {
                        Text("All Countries",
                            style = MaterialTheme.typography.labelSmall)
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
                        contentDescription = stringResource(R.string.back_button)
                        //contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CountriesScreen.valueOf(
        backStackEntry?.destination?.route ?: CountriesScreen.List.name
    )
    val countriesViewModel: CountriesViewModel = viewModel(factory = CountriesViewModel.Factory)
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            CountriesAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                countriesViewModel = countriesViewModel
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CountriesScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = CountriesScreen.List.name) {
                var columns : Int
                if (isLandscape) {
                    columns = 2
                } else {
                    columns = 1
                }
                CountryListScreen(
                    countryListUiState = countriesViewModel.countryListUiState,
                    countryListItemClicked = { country ->
                        countriesViewModel.setSelectedCountry(country)
                        navController.navigate(CountriesScreen.Detail.name)
                    },
                    countriesViewModel = countriesViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    columns = columns
                )
            }
            composable(route = CountriesScreen.Detail.name) {
                if (isLandscape) {
                    Row(Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f)) {
                            CountryDetailScreen(
                                selectedCountryUiState = countriesViewModel.selectedCountryUiState,
                                countriesViewModel = countriesViewModel,
                                modifier = Modifier
                            ) {
                                countriesViewModel.setSelectedCountry(it)
                                navController.navigate(CountriesScreen.Map.name)
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            CountryListScreen(
                                countryListUiState = countriesViewModel.countryListUiState,
                                countryListItemClicked = { country ->
                                    countriesViewModel.setSelectedCountry(country)
                                },
                                countriesViewModel = countriesViewModel,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp),
                                columns = 1
                            )
                        }
                    }
                } else {
                    Column {
                        CountryDetailScreen(
                            selectedCountryUiState = countriesViewModel.selectedCountryUiState,
                            countriesViewModel = countriesViewModel,
                            modifier = Modifier
                        ) {
                            countriesViewModel.setSelectedCountry(it)
                            navController.navigate(CountriesScreen.Map.name)
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        CountryListScreen(
                            countryListUiState = countriesViewModel.countryListUiState,
                            countryListItemClicked = { country ->
                                countriesViewModel.setSelectedCountry(country)
                            },
                            countriesViewModel = countriesViewModel,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            columns = 1
                        )
                    }
                }
            }
            composable(route = CountriesScreen.Map.name) {
                CountryMapScreen(
                    selectedCountryUiState = countriesViewModel.selectedCountryUiState
                )
            }
        }
    }
}