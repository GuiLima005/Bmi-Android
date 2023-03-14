package br.senai.sp.jandira.bmicalculator

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicalculator.calculate.calculate
import br.senai.sp.jandira.bmicalculator.calculate.getBmiClassification
import br.senai.sp.jandira.bmicalculator.model.Client
import br.senai.sp.jandira.bmicalculator.model.Product
import br.senai.sp.jandira.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BMICalculatorTheme {
                CalculatorScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CalculatorScreen() {

    var weightState by rememberSaveable {
        mutableStateOf("")
    }

    var heightState by rememberSaveable {
        mutableStateOf("")
    }

    var bmiState by rememberSaveable {
        mutableStateOf("0.0")
    }

    var bmiClassificationState by rememberSaveable {
        mutableStateOf("")
    }

    var context = LocalContext.current.applicationContext
    var context2 = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // HEADER
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = br.senai.sp.jandira.bmicalculator.R.drawable.bmi),
                    contentDescription = "",
                    modifier = Modifier.size(105.dp)
                )
                Text(
                    text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.title),
                    fontSize = 32.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp)
            ) {
                Text(
                    text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.weight_label),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = weightState,
                    onValueChange = {
                        Log.i("ds2m", it)
                        weightState = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 25.dp),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Text(
                    text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.height_label),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = heightState,
                    onValueChange = {
                        heightState = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(30.dp))
                Button(

                    onClick = {
                        var w = weightState.toDouble()
                        var h = heightState.toDouble()
                        var bmi = calculate(weight = w, height = h)

                        bmiState = String.format("%.1f", bmi)
                        bmiClassificationState = getBmiClassification(bmi, context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(79, 54, 232))
                )
                {
                    Text(
                        text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.button_calculate),
                        color = Color.White,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
            Column() {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                    backgroundColor = Color(79, 54, 232),
                    //border = BorderStroke(2.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(
                            text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.your_score),
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiState,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = bmiClassificationState,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Row() {
                            Button(
                                onClick = {
                                weightState = ""
                                heightState = ""
                                bmiClassificationState = ""
                                bmiState = ""
                            },
                            ) {
                                Text(text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.reset))
                            }
                            Spacer(modifier = Modifier.width(30.dp))
                            Button(onClick = {
                                val openOther = Intent(context2, SignUpActivity::class.java)
                                context2.startActivity(openOther)
                            }) {
                                Text(text = stringResource(id = br.senai.sp.jandira.bmicalculator.R.string.share))
                            }
                        }
                    }
                }
            }
        }
    }
}