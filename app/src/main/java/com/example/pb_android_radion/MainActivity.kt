package com.example.pb_android_radion

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.example.pb_android_radion.model.Usuario
import com.example.pb_android_radion.viewModel.UsuarioViewModel
import kotlinx.android.synthetic.main.layout_cadastro.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var usuarioViewModel: UsuarioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuarioViewModel = ViewModelProviders.of(this).get(UsuarioViewModel::class.java)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_playlist, R.id.nav_historico, R.id.nav_perfil, R.id.nav_search
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //colocando as informacoes na tela de perfil
        usuarioViewModel = ViewModelProviders.of(this)[UsuarioViewModel::class.java]
        //val usuarioLogado = intent.getSerializableExtra("usuario") as Usuario
        //val nomeCompleto = ("${usuarioLogado.nome} "+"${usuarioLogado.sobrenome}")
        //usuarioViewModel.usuarioLogadoApelido = usuarioLogado.apelido
        //usuarioViewModel.usuarioLogadoNome = nomeCompleto
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        usuarioViewModel = ViewModelProviders.of(this)[UsuarioViewModel::class.java]
        //val usuarioLogado = intent.getSerializableExtra("usuario") as Usuario
        //val nomeCompleto = ("${usuarioLogado.nome} "+"${usuarioLogado.sobrenome}")

        /*var nomeCompleto = usuarioViewModel.usuarioLogado!!.nome +
                usuarioViewModel.usuarioLogado!!.sobrenome
        txtVwNomeCompleto.text = nomeCompleto
        txtVwEmail.text = usuarioViewModel.usuarioLogado!!.email

        imagemUsuario = usuarioViewModel.usuarioLogado!!.imagem*/
       // imagemUsuario.setImage()

        val usuarioLogado = intent.getSerializableExtra("it") as Usuario
        /*usuarioViewModel.usuarioLogado = usuarioLogado
        txtVwNomeCompleto.text = usuarioLogado.nome
        txtVwEmail.text = usuarioLogado.email*/

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
}