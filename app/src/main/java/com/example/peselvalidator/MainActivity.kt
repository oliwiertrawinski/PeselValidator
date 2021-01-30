package com.example.peselvalidator

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.peselvalidator.ui.theme.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeselValidatorTheme {
                MainPage()
            }
        }
    }
}

//@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainPage() {
    Surface(
        color = gray200, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight(1f)
        ) {
            Text(
                text = "Pesel validator",
                color = gray800,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            PeselInput()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun PeselInput() {
    val peselState = remember { mutableStateOf(TextFieldValue("")) }
    val empty = peselState.value.text.isEmpty()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = peselState.value,
            onValueChange = {
                peselState.value = it
            },
            label = {
                val labelContent = "Enter your pesel"
                Text(text = labelContent)
            },
            placeholder = { Text(text = "e.g. 98011302136") },
            isErrorValue = Validator.isInvalid(peselState.value.text) and !empty,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onImeActionPerformed = { action, softwareController ->
                if (action == ImeAction.Done) {
                    softwareController?.hideSoftwareKeyboard()
                }
            }
        )

        Spacer(modifier = Modifier.padding(12.dp))

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!empty){
                if(Validator.isInvalid(peselState.value.text) ){
                    Text(text = "Pesel contains 11 digits", color = Color.Red)
                }else{
                    if(Validator.checkSum(peselState.value.text)){
                        Text(text = "Pesel is valid", color = Color(0xFF2E7431))
                    }else{
                        Text(text = "Pesel is invalid", color = Color.Red)
                    }
                }
            }else{
                Text(text = "")
            }

            Spacer(modifier = Modifier.padding(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (!empty and !Validator.isInvalid(peselState.value.text)) {
                        if (Validator.isMale(peselState.value.text)) "Gender: MALE" else "Gender: FEMALE"
                    } else ""
                )

                Text(
                    text = if (!empty and !Validator.isInvalid(peselState.value.text)) {
                        Validator.getBirthDate(peselState.value.text)
                    } else ""
                )
            }
        }
    }
}