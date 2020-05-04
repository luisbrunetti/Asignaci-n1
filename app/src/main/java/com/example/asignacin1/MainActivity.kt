package com.example.asignacin1
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var Error: Boolean = false // Variable que guarda el valor si el usuario se equivoco o no al encontrar el par
    var Intentos: Int = 0       //Intentos del usuario
    var TextView: TextView? = null  // text View para mostrar un mensaje al usuario
    var JuegoTerminado: Int = 0  //Variable que aumenta cada vez que se encuentra un par
    var PantallasPresionadas: Int = 0  //Cantidad de pantallas presionadas por turno
    var PantallaUnoPresionada: Int = 0 //Guarda el ID de la primera pantalla que se presiono
    var PantallaDosPresionada: Int = 0 //Guareda el ID de la segunda pantalla que se presiono
    var Button1ID: Int = 0  //Guarda el ID del boton del primer boton que se presiono
    var Button2ID: Int = 0 //Guarda el ID del segundo boton que se presiono
    var currentColor = 0  //Guarda el ID del color acutal
    lateinit var buttons: ArrayList<Button>  //Arrays de botones
    lateinit var ArrayAleatorio_IDbutton: ArrayList<Int> // Arrays para ordenar los botones
    lateinit var ArrayEmojis: IntArray //Arrays de los emojis
    lateinit var Hash: MutableMap<Int, Int> //Hash para vincular ID boton con ID de emoji

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TextView = findViewById(R.id.textView)
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
        InicializarJuego()

        for (button in buttons) {
            //Se le asigna el listener a cada botón, dado que es el mismo proceso
            button.setOnClickListener {
                Operaciones(button.id)
            }
            //Se le está poniendo a cada botón un tamaño del texto, cuya dimensión se encuentra en resources.
            button.textSize = resources.getDimension(R.dimen.text_size_button)
        }

    }

    //Función que se ejecutara por cada click en el boton
    fun Operaciones(ID: Int) {
        //Cuando la variable llega al valor de 7 significa que ya se encontraron los 6 pares
        //Y se va a reiniciar el juego
        if (JuegoTerminado == 7) {
            TextView?.text = ""
            InicializarJuego()
        } else {
            //En caso no haya terminado el juego se muestra el boton con una imagen
            LlenarButton(ID)
        }

    }

    fun LlenarButton(ButtonID: Int) {
        // ERROR es true cuando el usuario no ha acertado las dos imagenes
        //Caso no haya acertado se elimanan las imagenes y se cambian las varaibles a 0
        if (!Error) {


            //Contador de pantallas presionadas por turno
            //Solo puede presionar a los 2 pantallas presionadas
            PantallasPresionadas += 1

            var img_id: Int? =
                Hash[ButtonID]   // Se obtiene el ID DE LA IMAGEN(DRAWABLE) definida en el arreglo de ArreglodeImagenes
            findViewById<Button>(ButtonID).text = getEmoji(img_id!!) //Se le pone como texto el emoji en el botón que escogió
            if (PantallasPresionadas == 1) {  // Si recien seleciona para primera imagen
                findViewById<Button>(ButtonID).isClickable = false //Cambia el estado del boton; haciendo que no se puede hacer click
                PantallaUnoPresionada = img_id // Se guarda el ID de la imagen en una variable
                Button1ID = ButtonID  // Se guardar el ID del primer boton en una varaible


            } else if (PantallasPresionadas == 2) { // Cuando ya haaya presionado dos botones
                //Por cada turno se le suma un intento
                Intentos++
                findViewById<Button>(ButtonID).isClickable = false // no se puede volver a presionar el segundo boton
                PantallaDosPresionada =
                    img_id  // Se guarda el ID de la segunda imagen en una varaible
                Button2ID = ButtonID // Se guarda el ID del segundo boton en un varaible

                // Se comparan el ID de las imagenes previamente guardadas en variables
                if (PantallaUnoPresionada.equals(PantallaDosPresionada)) {

                    JuegoTerminado++  // Se suma uno a JuegoTerminado indicando que ya hay un par encontrado
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


                //REINICIANDO VARIABLES para iniciar el siguiente turno
                PantallasPresionadas = 0
                PantallaUnoPresionada = 0
                PantallaDosPresionada = 0
            }

        } else {

            //Eliminando las imagenes mostradas y habilitando los botones
            var b1=findViewById<Button>(Button1ID)
            var b2 = findViewById<Button>(Button2ID)
            b1.text = ""     //Limpiando el Unicode
            b1.isClickable = true   //Cambiando estado del boton a clickeable

            b2.text = ""  //Limpiando el Unicode
            b2.isClickable = true   //Cambiando estado del boton a clickeable

            //Reiniciando varaibles
            Button1ID = 0
            Button2ID = 0
            Error = false   // La varaible Error cambia false , indicando que en el nuevo turno se tednra como referencia que el error es false (No se ha equivado)
        }

    }
    fun InicializarJuego() {
        Intentos = 0 // Inicializar los intentos desde 0

        //Le damos a los botones un color distinto y aleatorio, de la paleta de colores que almacenamos en los recursos.
        fetchButtonsRandomColor()

        //Arreglo que almacena los ID de los botones
        ArrayAleatorio_IDbutton = ArrayList(
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

        ///ORDENAMIENTO ALEATORIO DE LOS ID DE LOS BOTONES /////////////////////////////////////////
        var temp: Int?
        for (v in ArrayAleatorio_IDbutton.indices) {
            findViewById<Button>(ArrayAleatorio_IDbutton[v]).setBackgroundResource(currentColor)
            var r = (0 until ArrayAleatorio_IDbutton.size - 1).random()
            temp = ArrayAleatorio_IDbutton[v]
            ArrayAleatorio_IDbutton[v] = ArrayAleatorio_IDbutton[r]
            ArrayAleatorio_IDbutton[r] = temp
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        ///Guardar el Array ordenado aleatoriamente con un respectivo ID de imagen utilizando mutableHash
        Hash = mutableMapOf()
        var pos = 0
        for (h in ArrayAleatorio_IDbutton.indices)
        {
            pos = if(pos >= ArrayEmojis.size) 0 else pos
            Hash.set(ArrayAleatorio_IDbutton[h], ArrayEmojis[pos])
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
            button.text =""
        }
    }

    //Función que recibe como parámetro el unicode como entero y lo devuelve como string
    private fun getEmoji(unicode: Int):String
    {
        return String(Character.toChars(unicode))
    }

}
