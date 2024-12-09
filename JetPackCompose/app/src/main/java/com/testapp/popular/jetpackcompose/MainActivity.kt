package com.testapp.popular.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testapp.popular.jetpackcompose.ui.theme.JetPackComposeTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			JetPackComposeTheme {
				CircleExample()
			}
		}
	}
}

@Composable
fun CircleExample() {
	Column(
		modifier = Modifier.fillMaxSize().padding(16.dp),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Canvas(modifier = Modifier.size(200.dp)) {
			drawCircle(
				color = Color.Red,
				radius = size.minDimension / 2f,
				style = Fill
			)
			drawCircle(
				color = Color.Green,
				radius = size.minDimension / 2f,
				style = Stroke(width = 8.dp.toPx())
			)
		}
	}
}

@Composable
fun MyApp(modifier: Modifier = Modifier, names: List<String> = listOf("World", "Compose")) {
	Column(modifier = modifier.padding(vertical = 4.dp)) {
		for (name in names) {
			Greeting(name = name)
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Surface(
		modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
		color = MaterialTheme.colorScheme.primary
	) {
		Column(modifier = modifier.padding(24.dp).fillMaxWidth()) {
			Text(text = "Hello")
			Text(text = name)
		}

	}
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
	JetPackComposeTheme {
		CircleExample()
	}
}