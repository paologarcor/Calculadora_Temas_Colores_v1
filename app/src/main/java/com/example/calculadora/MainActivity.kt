package com.example.calculadora

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Temas
    enum class AppTheme { LAVANDA, MENTA, DURAZNO }
    private var temaActual = AppTheme.LAVANDA

    // Operadores
    private val SUMA = "+"
    private val RESTA = "-"
    private val MULTI = "×"
    private val DIV = "÷"

    private var operacionActual = ""
    private var primerNumero: Double = Double.NaN
    private var segundoNumero: Double = Double.NaN

    private lateinit var tvOperation: TextView
    private lateinit var tvResult: TextView
    private lateinit var rootLayout: View
    private lateinit var btnMenu: ImageView
    private lateinit var decorationView: View
    private lateinit var formatoDecimal: DecimalFormat

    private val opButtonIds = listOf(
        R.id.btnAC, R.id.btnPlusMinus, R.id.btnPercent, 
        R.id.btnDivide, R.id.btnMultiply, R.id.btnMinus, R.id.btnPlus
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.main)
        tvOperation = findViewById(R.id.tvOperation)
        tvResult = findViewById(R.id.tvResult)
        btnMenu = findViewById(R.id.btnMenu)
        
        // El indicador decorativo es el último hijo del primer ConstraintLayout
        val displaySection = findViewById<View>(R.id.displaySection) as androidx.constraintlayout.widget.ConstraintLayout
        decorationView = displaySection.getChildAt(displaySection.childCount - 1)

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatoDecimal = DecimalFormat("#.########")

        setupButtons()
        setupMenu()
        
        // Aplicar tema inicial (LAVANDA por defecto en XML)
        applyAppTheme(AppTheme.LAVANDA)
    }

    private fun setupMenu() {
        btnMenu.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menu.add(0, 1, 0, "Lavanda")
            popup.menu.add(0, 2, 1, "Menta")
            popup.menu.add(0, 3, 2, "Durazno")
            
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> applyAppTheme(AppTheme.LAVANDA)
                    2 -> applyAppTheme(AppTheme.MENTA)
                    3 -> applyAppTheme(AppTheme.DURAZNO)
                }
                true
            }
            popup.show()
        }
    }

    private fun applyAppTheme(theme: AppTheme) {
        temaActual = theme
        val bgColor = when (theme) {
            AppTheme.LAVANDA -> R.color.lavender_bg
            AppTheme.MENTA -> R.color.mint_bg
            AppTheme.DURAZNO -> R.color.peach_bg
        }
        val opColor = when (theme) {
            AppTheme.LAVANDA -> R.color.lavender_op
            AppTheme.MENTA -> R.color.mint_op
            AppTheme.DURAZNO -> R.color.peach_op
        }
        val equalColor = when (theme) {
            AppTheme.LAVANDA -> R.color.lavender_equal
            AppTheme.MENTA -> R.color.mint_equal
            AppTheme.DURAZNO -> R.color.peach_equal
        }
        val textColor = when (theme) {
            AppTheme.LAVANDA -> R.color.lavender_text
            AppTheme.MENTA -> R.color.mint_text
            AppTheme.DURAZNO -> R.color.peach_text
        }
        val textSecondaryColor = when (theme) {
            AppTheme.LAVANDA -> R.color.lavender_text_secondary
            AppTheme.MENTA -> R.color.mint_text_secondary
            AppTheme.DURAZNO -> R.color.peach_text_secondary
        }

        rootLayout.setBackgroundColor(ContextCompat.getColor(this, bgColor))
        tvResult.setTextColor(ContextCompat.getColor(this, textColor))
        tvOperation.setTextColor(ContextCompat.getColor(this, textSecondaryColor))
        decorationView.setBackgroundColor(ContextCompat.getColor(this, opColor))
        btnMenu.setColorFilter(ContextCompat.getColor(this, textColor))

        // Actualizar botones de operación
        opButtonIds.forEach { id ->
            val btn = findViewById<Button>(id)
            btn.backgroundTintList = ContextCompat.getColorStateList(this, opColor)
            btn.setTextColor(ContextCompat.getColor(this, textColor))
        }

        // Actualizar botón igual
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        btnEqual.backgroundTintList = ContextCompat.getColorStateList(this, equalColor)
    }

    private fun setupButtons() {
        // Números
        val numButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnComma
        )
        numButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onNumberClick((it as Button).text.toString()) }
        }

        // Operaciones
        findViewById<Button>(R.id.btnPlus).setOnClickListener { onOperationClick(SUMA) }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { onOperationClick(RESTA) }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperationClick(MULTI) }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperationClick(DIV) }

        // Acciones
        findViewById<Button>(R.id.btnAC).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { onPlusMinusClick() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { onPercentClick() }
    }

    private fun onNumberClick(valor: String) {
        if (tvResult.text.toString() == "0" || !primerNumero.isNaN() && operacionActual.isNotEmpty() && tvResult.text.toString() == formatoDecimal.format(primerNumero)) {
            tvResult.text = valor
        } else {
            if (valor == "," && tvResult.text.contains(",")) return
            tvResult.append(valor)
        }
    }

    private fun onOperationClick(op: String) {
        val currentText = tvResult.text.toString().replace(",", ".")
        if (currentText.isNotEmpty()) {
            primerNumero = currentText.toDouble()
            operacionActual = op
            tvOperation.text = "${formatoDecimal.format(primerNumero)} $op"
            tvResult.text = "0"
        }
    }

    private fun onEqualClick() {
        val currentText = tvResult.text.toString().replace(",", ".")
        if (!primerNumero.isNaN() && currentText.isNotEmpty()) {
            segundoNumero = currentText.toDouble()
            val resultado = when (operacionActual) {
                SUMA -> primerNumero + segundoNumero
                RESTA -> primerNumero - segundoNumero
                MULTI -> primerNumero * segundoNumero
                DIV -> if (segundoNumero != 0.0) primerNumero / segundoNumero else Double.NaN
                else -> primerNumero
            }

            tvOperation.text = "${formatoDecimal.format(primerNumero)} $operacionActual ${formatoDecimal.format(segundoNumero)} ="
            tvResult.text = if (resultado.isNaN()) "_error_" else formatoDecimal.format(resultado).replace(".", ",")
            
            // Reiniciar para la siguiente operación
            primerNumero = if (resultado.isNaN()) Double.NaN else resultado
            operacionActual = ""
        }
    }

    private fun onClearClick() {
        primerNumero = Double.NaN
        segundoNumero = Double.NaN
        operacionActual = ""
        tvResult.text = "0"
        tvOperation.text = ""
    }

    private fun onPlusMinusClick() {
        val currentText = tvResult.text.toString()
        if (currentText != "0" && currentText.isNotEmpty()) {
            if (currentText.startsWith("-")) {
                tvResult.text = currentText.substring(1)
            } else {
                tvResult.text = "-$currentText"
            }
        }
    }

    private fun onPercentClick() {
        val currentText = tvResult.text.toString().replace(",", ".")
        if (currentText.isNotEmpty()) {
            val valor = currentText.toDouble() / 100
            tvResult.text = formatoDecimal.format(valor).replace(".", ",")
        }
    }
}
