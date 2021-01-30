package com.example.peselvalidator

import android.os.Build
import androidx.annotation.RequiresApi

class Validator {
    companion object{
        fun isInvalid(pesel: String): Boolean{
            return pesel.length != 11 || !pesel.matches("[0-9]+".toRegex());
        }

        fun isMale(pesel: String): Boolean{
            if(isInvalid(pesel)) throw Error("Pesel contains 11 digits")
            else{
                return (pesel[pesel.length - 2].toInt())%2 != 0
            }
        }


        @RequiresApi(Build.VERSION_CODES.N)
        fun getBirthDate(pesel: String): String{
            if(isInvalid(pesel)) throw Error("Pesel contains 11 digits")
            else {
                val year = pesel.substring(0,2).toInt()
                val month = pesel.substring(2,4)
                val day = pesel.substring(4,6)
                val longYear = year + if(year < 20) 2000 else 1900
                val stringDate = "$day-$month-$longYear"
                return dateInfo(stringDate)
            }
        }

        fun checkSum(pesel: String): Boolean{
            if(isInvalid(pesel)) throw Error("Pesel contains 11 digits")
            else{
                val a = pesel[0].toInt()
                val b = pesel[1].toInt()
                val c = pesel[2].toInt()
                val d = pesel[3].toInt()
                val e = pesel[4].toInt()
                val f = pesel[5].toInt()
                val g = pesel[6].toInt()
                val h = pesel[7].toInt()
                val i = pesel[8].toInt()
                val j = pesel[9].toInt()
                val k = pesel[10].toInt()
                val controlSum = a + 3*b + 7*c + 9*d + e + 3*f + 7*g + 9*h + i + 3*j + k
                return controlSum % 10 == 0
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun dateInfo(stringDate:String):String{
            var infoToReturn = ""
            var simpleFormat =  android.icu.text.SimpleDateFormat("dd-MM-yyyy")
            simpleFormat.isLenient = false
            try {
                simpleFormat.parse(stringDate)
                infoToReturn = stringDate
            }catch (e: Exception) {
                infoToReturn = "date is invalid"
            }
            return "Birthdate: $infoToReturn"
        }
    }
}

