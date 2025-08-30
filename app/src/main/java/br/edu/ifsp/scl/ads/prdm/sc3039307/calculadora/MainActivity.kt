package br.edu.ifsp.scl.ads.prdm.sc3039307.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.scl.ads.prdm.sc3039307.calculadora.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        with(amb){
            setContentView(root)
            digitoZero.setOnClickListener { expressaoMatematica.append("0")}
            digitoUm.setOnClickListener { expressaoMatematica.append("1")}
            digitoDois.setOnClickListener { expressaoMatematica.append("2")}
            digitoTres.setOnClickListener { expressaoMatematica.append("3")}
            digitoQuatro.setOnClickListener { expressaoMatematica.append("4")}
            digitoCinco.setOnClickListener { expressaoMatematica.append("5")}
            digitoSeis.setOnClickListener { expressaoMatematica.append("6")}
            digitoSete.setOnClickListener { expressaoMatematica.append("7")}
            digitoOito.setOnClickListener { expressaoMatematica.append("8")}
            digitoNove.setOnClickListener { expressaoMatematica.append("9")}
            decimal.setOnClickListener { expressaoMatematica.append(".") }

            operacaoAdicao.setOnClickListener {operatorEvent('+')}
            operacaoSubtracao.setOnClickListener {operatorEvent('-')}
            operacaoMultiplicacao.setOnClickListener {operatorEvent('*')}
            operacaoDivisao.setOnClickListener {operatorEvent('/')}

            operacaoResultado.setOnClickListener { getResult() }
            botaoRemocao.setOnClickListener { expressaoMatematica.text = expressaoMatematica.text.dropLast(1)}
            limparBotao.setOnClickListener { expressaoMatematica.text = ""}

        }

    }

    fun operatorEvent(operator : Char){
        val actualExpression = amb.expressaoMatematica.text.toString()

        val validOperator = when (operator){
            '+','-','*','/' -> operator
            else -> ""
        }

        if(validOperator == "") return

        if(actualExpression.isEmpty()){
            if(operator == '-') amb.expressaoMatematica.append("-")
            return
        }

        if(actualExpression.length == 1 && actualExpression[0] == '-' && validOperator == '-') return

        val lastChar = actualExpression.last()

        if(lastChar in listOf('+','-','*','/')){
            amb.expressaoMatematica.text = actualExpression.dropLast(1) + operator
            return
        }

        amb.expressaoMatematica.text = actualExpression + operator;

    }

    fun getResult(){
        val actualExpression = amb.expressaoMatematica.text.toString()

        if (Regex("/0\\b").containsMatchIn(actualExpression)) {
            Toast.makeText(this@MainActivity, "Erro: divisão por zero", Toast.LENGTH_LONG).show()
            amb.expressaoMatematica.text = ""
            return
        }

        val resultadoCalculado = Expression(actualExpression).calculate()
        if(resultadoCalculado.isNaN()){
            Toast.makeText(this@MainActivity, "Erro: ocorreu um erro no calculo do resultado", Toast.LENGTH_LONG).show()
            amb.expressaoMatematica.text = ""
            return
        }

        amb.expressaoMatematica.text = resultadoCalculado.toString()

    }

}