package com.example.composecodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                MyApp(modifier = Modifier.fillMaxSize())
        }
    }

    @Composable
    private fun MyApp(modifier: Modifier = Modifier){

        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

        Surface(modifier, color = MaterialTheme.colorScheme.background) {
            if (shouldShowOnboarding) {
                OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            } else {
                Greetings()
            }
        }
    }

    @Composable
    fun OnboardingScreen(
        onContinueClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the CodeWithShery!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) { "$it" }){
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)){
            items(items = names){ name ->
                var expanded by rememberSaveable { mutableStateOf(false) }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    onClick = { expanded = !expanded }
                ) {
                    CardContent(name,expanded)
                }            }
        }
    }

    @Composable
    private fun Greeting(name: String) {

        val expanded = rememberSaveable { mutableStateOf(false) }

        val extraPadding by animateDpAsState(
            if (expanded.value) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = "Hello, ")
                    Text(text = name)
                }
                ElevatedButton(
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Text(if(expanded.value) "Show Less" else "Show more")
                }
            }
        }
    }

    @Composable
    private fun CardContent(name: String, expanded : Boolean) {
        Column (
            modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )){
            Row{
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)
                ) {
                    Text(text = "Hello, ")
                    Text(
                        text = name, style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
                IconButton({}) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) {
                            stringResource(R.string.show_less)
                        } else {
                            stringResource(R.string.show_more)
                        }
                    )
                }
            }
            if (expanded) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }

    }

}
