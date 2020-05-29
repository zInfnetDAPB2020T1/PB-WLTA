package com.example.pb_android_radion.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.pb_android_radion.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.layout_cadastro.view.*

class UsuarioViewModel: ViewModel() {

    var usuario: Usuario? = null
    var usuarioLogado: FirebaseUser? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    fun verificarNulo(
        view: View, context: Context): Boolean {
        //Verifico se algum campo está nulo ou vazio
        if (
            view.boxNomeCadastro.text.isNullOrBlank() ||
            view.boxApelidoCadastro.text.isNullOrBlank() ||
            view.boxEmailCadastro.text.isNullOrBlank() ||
            view.boxEstadoCadastro.text.isNullOrBlank() ||
            view.boxDDDCadastro.text.isNullOrBlank() ||
            view.boxTelefoneCadastro.text.isNullOrBlank()
        ) {
            return false
        } else {
            //Caso tudo ocorra ok, começo a alimentar o view model com o resto das informações
            usuario = Usuario(
                apelido = view.boxApelidoCadastro.text.toString(),
                //imagem = view.imageViewPerfil.toString(),
                email = view.boxEmailCadastro.text.toString(),
                senha = view.boxSenhaCadastro2.text.toString(),
                nomeCompleto = view.boxNomeCadastro.text.toString(),
                estado = view.boxEstadoCadastro.text.toString(),
                ddd = view.boxDDDCadastro.text.toString(),
                telefone = view.boxTelefoneCadastro.text.toString()
            )
            return true
        }
    }

    fun salvarNoFirestore(context: Context){

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

       var collection = firestore.collection("usuarios")

        var user: MutableMap<String, Any> = HashMap()
        user["apelido"] = usuario!!.apelido
        //user["imagem"] = usuario!!.imagem!!
        user["email"] = usuario!!.email
        user["senha"] = usuario!!.senha
        user["nomeCompleto"] = usuario!!.nomeCompleto
        user["estado"] = usuario!!.estado
        user["ddd"] = usuario!!.ddd
        user["telefone"] = usuario!!.telefone

        var document = collection.document(usuario!!.email)

        firebaseAuth.createUserWithEmailAndPassword(usuario!!.email, usuario!!.senha)
            .addOnSuccessListener {
                if(it != null){
                    document.set(user)
                    Toast.makeText(context, "Cadastro realizado com sucesso",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                if(it.message == "The email address is already in use by another account"){
                    Toast.makeText(context, "Email já cadastrado!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    
    @SuppressLint("SetTextI18n")
    fun infoPerfil(txtNomeCompleto: TextView, txtApelido: TextView, txtEstado: TextView,
                   txtTelefone: TextView, txtEmail: TextView){

        var document = FirebaseFirestore.getInstance()
            .collection("usuarios")
            .document(usuarioLogado!!.email!!)

            document.get()
                .addOnSuccessListener {
                    if(it != null){
                        txtNomeCompleto.text = "${it["nomeCompleto"]}"
                        txtApelido.text = "${it["apelido"]}"
                        txtEstado.text = "${it["estado"]}"
                        txtTelefone.text = "${it["ddd"]}"+"${it["telefone"]}"
                        txtEmail.text = "${it["email"]}"
                    }else{
                        Log.i("Problema", "algum problema ocorreu aqui")
                    }
                }
    }
}