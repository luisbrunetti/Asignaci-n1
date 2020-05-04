package com.example.asignacin1

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var Error: Boolean = false
    var Intentos: Int = 0
    var TextView: TextView? = null
    var JuegoTerminado: Int = 0
    var PantallasPresionadas: Int = 0
    var PantallaUnoPresionada: Int = 0
    var PantallaDosPresionada: Int = 0
    var Button1ID: Int = 0
    var Button2ID: Int = 0
    var currentColor = 0
    lateinit var buttons: ArrayList<Button>
    lateinit var numerosPos: ArrayList<Int>
    lateinit var ArrayEmojis: IntArray
    lateinit var Hash: MutableMap<Int, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Se crea un arreglo para poder identificar a todos los botones
        buttons = ArrayList(
            listOf(
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
                button12
            )
        )

        // Función que se ejecutara cada vez que empiece el juego
        InicializarArreglo()

        for (button in buttons) {
            //Se le asigna el listener a cada botón, dado que es el mismo proceso
            button.setOnClickListener {
                Operaciones(button.id)
            }
            //Se le está poniendo a cada botón un tamaño del texto, cuya dimensión se encuentra en resources.
            button.textSize = resources.getDimension(R.dimen.text_size_button)
        }

        TextView = findViewById(R.id.textView)


    }

    //Función que se ejecutara por cada click en el boton
    private fun Operaciones(ID: Int) {
        if (JuegoTerminado == 7) {
            InicializarArreglo()
        } else {
            //En caso no haya terminado el juego se muestra el boton con una imagen
            LlenarButton(ID)
        }

    }

    fun LlenarButton(ButtonID: Int) {
        // ERROR es true cuando el usuario no ha acertado las dos imagenes
        //Casono haya acertado se elimanan las imagenes y se cambian las varaibles a 0
        if (!Error) {
            //Por cada turno se le suma un intento
            Intentos++

            //Contador de pantallas presionadas por turno
            //Solo puede presionar a los 2 pantallas presionadas
            PantallasPresionadas += 1

            var img: Drawable? // Se inicializa una variable de tipo Drawable para pintarla en un boton
            var img_id: Int? =
                Hash[ButtonID]   // Se obtiene el ID DE LA IMAGEN(DRAWABLE) definida en el arreglo de ArreglodeImagenes
            findViewById<Button>(ButtonID).text = getEmoji(img_id!!) //Se le pone como texto el emoji en el botón que escogió
            if (PantallasPresionadas == 1) {  // Si recien seleciona para primera imagen
                findViewById<Button>(ButtonID).isClickable = false
                PantallaUnoPresionada = img_id!! // Se guarda el ID de la imagen en una variable
                Button1ID = ButtonID  // Se guardar el ID del primer boton en una varaible


            } else if (PantallasPresionadas == 2) { // Cuando ya haya presionado dos botones

                findViewById<Button>(ButtonID).isClickable = false
                PantallaDosPresionada =
                    img_id!!  // Se gaurda el ID de la segunda imagen en una varaible
                Button2ID = ButtonID // Se gurda el ID del segundo boton en un varaible

                // Se comparan el ID de las imagenes previamente guardadas en variables
                if (PantallaUnoPresionada?.equals(PantallaDosPresionada)!!) {

                    JuegoTerminado++  // Ser suma uno a JuegoTerminado indicando que ya hay un par encontrado
                    Toast.makeText(
                        applicationContext,
                        JuegoTerminado.toString(),
                        Toast.LENGTH_SHORT
                    ).show() //En este Toast, se muestra las veces que va acertando
                    if (JuegoTerminado == 6) //Dado que son 12 botones, son 6 parejas de emojis, por ende debe acertar 6 veces para finalizar el juego
                    {
                        textView?.text =
                            "El juego ha terminado con $Intentos intentos" // Muestra mensaje diciendo que el juego termino
                        JuegoTerminado++
                        for(b in buttons){
                            b.isClickable = true
                        }
                    } else {
                        TextView?.text = "Correcto"  // Se muestra en patanlla "Corecto"
                    }


                } else {

                    TextView?.text = "Error" // Mostrar en el text view Error
                    Error =
                        true // Cambiar aal valor de ERROR a TRUE debido a que ocurrio un error y no encontro dos imagenes iguales
                }


                //REINICIANDO VARIABLES AL FINALIZAR DEL SEGUNDO TURNO
                PantallasPresionadas = 0
                PantallaUnoPresionada = 0
                PantallaDosPresionada = 0
            }

        } else {

            //Eliminando las imagenes mostradas y habilitando los botones
            var b1=findViewById<Button>(Button1ID)
            var b2 = findViewById<Button>(Button2ID)
            b1.text = ""
            b1.isClickable = true
            //b2.setBackgroundResource(currentColor)
            b2.text = ""
            b2.isClickable = true

            //Reiniciando varaibles
            Button1ID = 0
            Button2ID = 0
            Error = false
        }

    }
    fun InicializarArreglo() {
        Intentos = 0 // Inicializar los intentos desde 0

        //Le damos a los botones un color distinto y aleatorio, de la paleta de colores que almacenamos en los recursos.
        fetchButtonsRandomColor()

        //Arreglo que almacena los ID de los botones
        numerosPos = ArrayList(
            listOf(
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
                R.id.button10,
                R.id.button11,
                R.id.button12
            )
        )
        //Arreglo que almacena Los ID de las imagenes a mostrar
        //Recuperamos los colores que se encuentran un in int-array, en strings.xml
        ArrayEmojis = resources.getIntArray(R.array.array_unicodes)


        ///ORDENAMIENTO ALEATORIO DEL ARREGLO "numeroPos" /////////////////////////////////////////
        var temp: Int? = 0
        for (v in numerosPos.indices) {
            //findViewById<Button>(numerosPos[v]).setCompoundDrawables(null,null,null,null)
            findViewById<Button>(numerosPos[v]).setBackgroundResource(currentColor)
            var r = (0 until numerosPos.size - 1).random()
            temp = numerosPos[v]
            numerosPos[v] = numerosPos[r]
            numerosPos[r] = temp
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        ///Guardar el Array ordenado aleatoriamente con un respectivo ID de imagen utilizando mutableHash
        Hash = mutableMapOf()
        var pos = 0
        for (h in numerosPos.indices)
        {
            pos = if(pos >= ArrayEmojis.size) 0 else pos
            Hash?.set(numerosPos[h], ArrayEmojis[pos])
            pos++
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        // Si la variable Juego Terminado llega 7 se renicia para volver a comenzar el juego
        JuegoTerminado = if(JuegoTerminado==7) 0 else JuegoTerminado
    }

    //Método que pondrá todos los botones de un mismo color
    private fun fetchButtonsRandomColor() {
        //Dado que los colores los definimos en los recursos, en un array, los recuperamos en un TypedArray
        //(Estructura recomendada por la documentación) para almacenar los valores
        val colors = resources.obtainTypedArray(R.array.colors_memory)
        //Obtenemos una posición aleatoria, ya que el color debe ser aleatorio
        var pos = ((0 until colors.length()).random())
        //Validamos que el color no esté repetido
        while (colors.getResourceId(0, 0) == currentColor)
        {
            pos = ((0 until colors.length()).random())
        }
        //actualizamos el id del nuevo color
        currentColor = colors.getResourceId(pos, 0)
        //Le cambiamos el backgorund a los botones con el nuevo color
        for (button in buttons)
        {
            button.setBackgroundResource(currentColor)
        }
    }

    //Función que recibe como parámetro el unicode como entero y lo devuelve como string
    private fun getEmoji(unicode: Int):String
    {
        return String(Character.toChars(unicode))
    }

}
