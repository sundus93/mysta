package inspire.tech.mysta

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        var var_email = findViewById<EditText>(R.id.input_email)
        var vr_password = findViewById<EditText>(R.id.input_password)

        var btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {view ->
            signIn(view,var_email.getText().toString().trim(), vr_password.getText().toString().trim())
        }
    }
    fun signIn(view: View, email: String, password: String){
        showMessage(view,"Authenticating...")




        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if(task.isSuccessful){
                var intent = Intent(this, AddImagesActivity::class.java)
                intent.putExtra("id", mAuth!!.currentUser?.email)
                startActivity(intent)

            }else{
                showMessage(view,"Error: ${task.exception?.message}")
            }
        })




    }

    fun showMessage(view:View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }


}


