package com.example.organivy.ui.subpages.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Screen 1
@Composable
fun OnStartOnboarding1 (onNavigateToNext: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Your Photos Leave a Footprint",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            //item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Unused photos contribute to digital waste, " +
                            "increasing energy usage in data centers worldwide.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp,50.dp)

        ) {

            Button(
                onClick = { },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Back",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onNavigateToNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Next",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

//Screen 2
@Composable
fun OnStartOnboarding2 (onNavigateBack: () -> Unit,
                        onNavigateToNext: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Clean Smarter",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            //item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Organivy helps you find duplicate, blurry, old, and " +
                            "unnecessary photos in seconds.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp,50.dp)

        ) {

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Back",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onNavigateToNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Next",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

//Screen 3
@Composable
fun OnStartOnboarding3 (onNavigateBack: () -> Unit,
                        onNavigateToNext: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Grow Your Digital Garden",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            //item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Every cleanup challenge helps your plant " +
                            "grow healthier and stronger.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp,50.dp)

        ) {

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Back",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onNavigateToNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Next",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

//Screen 4
@Composable
fun OnStartOnboarding4 (onNavigateBack: () -> Unit,
                        onNavigateToNext: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Turn Cleanup Into Progress",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            //item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Earn badges, complete challenges, build streaks, " +
                            "and track your impact over time.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp,50.dp)

        ) {

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Back",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onNavigateToNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Next",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}

//Screen 5
@Composable
fun OnStartOnboarding5 (onNavigateBack: () -> Unit,
                        onNavigateToLogin: () -> Unit){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),

        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Ready to Start Cleaning?",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

            //item { Spacer(modifier = Modifier.height(50.dp)) }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Organivy needs photo access to scan your " +
                            "gallery.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(0.dp,50.dp)

        ) {

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Back",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Continue",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )
            }
        }
    }
}


// Preview function goes outside MainActivity class
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewOnStartOnboarding() {
    //OnStartOnboarding1()
    //OnStartOnboarding2()
    //OnStartOnboarding3()
    //OnStartOnboarding4()
    //OnStartOnboarding5()
}