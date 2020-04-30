package com.example.asignacin1

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var Error: Boolean = false
    var Intentos:Int = 0
    var contadorTurno:Int = 0
    var Button1:Button? = null
    var Button2:Button? = null
    var Button3:Button? = null
    var Button4:Button? = null
    var Button5:Button? = null
    var Button6:Button? = null
    var Button7:Button? = null
    var Button8:Button? = null
    var Button9:Button? = null
    var Button10:Button? = null
    var Button11:Button? = null
    var Button12:Button? = null
    var TextView:TextView? = null
    var JuegoTerminado:Int = 0
    var PantallasPresionadas:Int = 0
    var PantallaUnoPresionada: Int = 0
    var PantallaDosPresionada: Int = 0
    var Button1ID :Int = 0
    var Button2ID: Int = 0
    lateinit var numerosPos : ArrayList<Int>
    lateinit var ArregloImagenes: ArrayList<Int>
    lateinit var ArregloColores: ArrayList<String>
    lateinit var Hash:  MutableMap<Int,Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var random :Int = 0
        InicializarArreglo()

        var buttons = ArrayList(listOf(
            button1,
            button2,
            button3,
            button4,
            button5,
            button6,
            button7,
            button8,
            button9,
            button10,
            button11,
            button12))

        for(button in buttons)
        {
            button.setOnClickListener {
                Operaciones(button.id)
            }
        }

        TextView = findViewById(R.id.textView)

        /*Button1 = findViewById(R.id.button1)
        Button2 = findViewById(R.id.button2)
        Button3 = findViewById(R.id.button3)
        Button4 = findViewById(R.id.button4)
        Button5 = findViewById(R.id.button5)
        Button6 = findViewById(R.id.button6)
        Button7 = findViewById(R.id.button7)
        Button8 = findViewById(R.id.button8)
        Button9 = findViewById(R.id.button9)
        Button10 = findViewById(R.id.button10)
        Button11 = findViewById(R.id.button11)
        Button12 = findViewById(R.id.button12)

        Button1?.setOnClickListener {
            Operaciones(R.id.button1)
        }
        Button2?.setOnClickListener {
            Operaciones(R.id.button2)}
        Button3?.setOnClickListener {
            Operaciones(R.id.button3)}
        Button4?.setOnClickListener {
            Operaciones(R.id.button4)}
        Button5?.setOnClickListener {
            Operaciones(R.id.button5)}
        Button6?.setOnClickListener {
            Operaciones(R.id.button6)}
        Button7?.setOnClickListener {
            Operaciones(R.id.button7)}
        Button8?.setOnClickListener {
            Operaciones(R.id.button8)}
        Button9?.setOnClickListener {
            Operaciones(R.id.button9)}
        Button10?.setOnClickListener {
            Operaciones(R.id.button10)}
        Button11?.setOnClickListener {
            Operaciones(R.id.button11)}
        Button12?.setOnClickListener {
            Operaciones(R.id.button12)}*/


    }
    private fun Operaciones(ID:Int){
        if(JuegoTerminado==7){
            InicializarArreglo()
        }else{
            LlenarButton(ID)
        }

    }

    fun LlenarButton(ButtonID:Int){
        if(!Error){
            if(JuegoTerminado == 6) {
                TextView?.text = "El juego ha terminado con "+Intentos.toString()+" intentos"
                JuegoTerminado++
            } else{
                Intentos++
                PantallasPresionadas += 1
                var img : Drawable?
                var img_id: Int? = Hash[ButtonID]
                img = applicationContext?.resources?.getDrawable(img_id!!,theme)
                img?.setBounds(0,0,60,60)

                findViewById<Button>(ButtonID).setCompoundDrawables(img,img,null,null)
                if (PantallasPresionadas==1){
                    PantallaUnoPresionada = img_id!!
                    Button1ID = ButtonID
                }else if (PantallasPresionadas == 2){
                    PantallaDosPresionada = img_id!!
                    Button2ID = ButtonID
                    if(PantallaUnoPresionada?.equals(PantallaDosPresionada)!!){
                        TextView?.text = "Correcto"
                        JuegoTerminado++
                        Toast.makeText(applicationContext,JuegoTerminado.toString(),Toast.LENGTH_SHORT).show()
                    }else{
                        TextView?.text = "Error"
                        Error = true
                    }
                    PantallasPresionadas = 0
                    PantallaUnoPresionada = 0
                    PantallaDosPresionada = 0
                }
            }
        }else{
            findViewById<Button>(Button1ID).setCompoundDrawables(null,null,null,null)
            findViewById<Button>(Button2ID).setCompoundDrawables(null,null,null,null)
            Button1ID = 0
            Button2ID = 0
            Error = false
        }

    }
    fun InicializarArreglo(){
        numerosPos = ArrayList(listOf(R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10,R.id.button11,R.id.button12))
        ArregloImagenes = ArrayList(listOf(R.drawable.ic_android_1,R.drawable.ic_call_black_2,R.drawable.ic_insert_emoticon_black_3,R.drawable.ic_smartphone_black_4,R.drawable.ic_my_location_black_5,R.drawable.ic_signal_wifi_4_bar_black_6,
            R.drawable.ic_android_1,R.drawable.ic_call_black_2,R.drawable.ic_insert_emoticon_black_3,R.drawable.ic_smartphone_black_4,R.drawable.ic_my_location_black_5,R.drawable.ic_signal_wifi_4_bar_black_6
        ))
        ArregloColores = ArrayList(listOf("#A78781","#DC5137","#A3DC37","#C4F16D","#52770A","#354517","#FFC205","#1005FF","#4E4C7E","#D727D2","#00EEDC","#8F78CA"))

        var temp:Int?=0
        for(v in numerosPos.indices){
            findViewById<Button>(numerosPos[v]).setCompoundDrawables(null,null,null,null)
            var r = (0 until numerosPos.size -1).random()
            temp = numerosPos[v]
            numerosPos[v]=numerosPos[r]
            numerosPos[r] = temp
        }
        Log.d("Lista",numerosPos.toString())
        Log.d("Lista D",ArregloImagenes.toString())
        Hash = mutableMapOf()
        for(h in numerosPos.indices){
            Log.d("LISTA A",ArregloImagenes[h].toString())
            Log.d("LISTA B",numerosPos[h].toString())
            Hash?.set(numerosPos[h], ArregloImagenes[h])
        }
        Log.d("Lista HASH",Hash.toString())
        if(JuegoTerminado == 7){
            for(c in numerosPos.indices){
               findViewById<Button>(numerosPos[c]).setBackgroundColor(Color.parseColor(ArregloColores[c]))
            }
            JuegoTerminado = 0
        }

    }
}
