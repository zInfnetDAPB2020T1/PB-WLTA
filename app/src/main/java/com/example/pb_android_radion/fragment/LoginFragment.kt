package com.example.pb_android_radion.fragment

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.pb_android_radion.MainActivity
import com.example.pb_android_radion.model.Usuario
import com.example.pb_android_radion.R
import com.example.pb_android_radion.service.AppDatabaseService
import com.example.pb_android_radion.viewModel.UsuarioViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_cadastro.*

class LoginFragment : Fragment() {

    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            usuarioViewModel = ViewModelProviders.of(it).get(UsuarioViewModel::class.java)
        }

        textoCadastroLogin.setOnClickListener{
            findNavController().navigate(R.id.LoginToCadastrar)
        }

        //val listaUsuarios = usuarioViewModel.listaUsuariosSeriazable
        btnLogarLogin.setOnClickListener {
            OperacaoBancoTask().execute()

        }
    }

    inner class OperacaoBancoTask : AsyncTask<Unit, Unit, Array<Usuario>>(){

        override fun doInBackground(vararg params: Unit?): Array<Usuario> {
            var db = AppDatabaseService.getInstance(activity!!.baseContext)

            return db.usuarioDao().listarUsuarios()
        }

        override fun onPostExecute(result: Array<Usuario>?) {
            super.onPostExecute(result)

            result!!.forEach {

                if(boxEmailLogin.text.toString() == it.email &&
                    boxSenhaLogin.text.toString() == it.senha){
                    var intent = Intent(activity?.baseContext, MainActivity::class.java)
                    intent.putExtra("UsuarioLogado", it)
                    startActivity(intent)
                }else{
                    Toast.makeText(activity!!.baseContext, "Usuário inválido",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
