package com.example.agro_town.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.example.agro_town.Firestore.FirestoreClass
import com.example.agro_town.R
import com.example.agro_town.models.Constans
import com.example.agro_town.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.util.*

class RegisterActivity : BaseActivity() {

   /* companion object {
        val TAG = "RegisterActivity"
    } */

    private lateinit var regETFirstName : EditText
    private lateinit var regETLastName: EditText
    private lateinit var regETEmail : EditText
    private lateinit var regETPassword : EditText
    private lateinit var regETPassword2 : EditText
    private lateinit var terms : CheckBox
    private lateinit var registration : Button
    private lateinit var mDbRef : DatabaseReference





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        regETFirstName = findViewById(R.id.regETFirstName)
        regETLastName = findViewById(R.id.regETLastName)
        regETEmail = findViewById(R.id.regETEmail)
        regETPassword = findViewById(R.id.regETPassword)
        regETPassword2 = findViewById(R.id.regETPassword2)
        terms = findViewById(R.id.terms)
        registration = findViewById(R.id.registrationButton)



        val haveAccount = findViewById<TextView>(R.id.alreadyHaveAnAccount)


        haveAccount.setOnClickListener {
            onBackPressed()
        }
        registration.setOnClickListener {
            registerUser()
            ///val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            ///startActivity(intent)
        }


    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(regETFirstName.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.error_first_name), true)
                false
            }
            TextUtils.isEmpty(regETLastName.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.error_last_name), true)
                false
            }
            TextUtils.isEmpty(regETEmail.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.error_email), true)
                false
            }
            TextUtils.isEmpty(regETPassword.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.error_password), true)
                false
            }
            TextUtils.isEmpty(regETPassword2.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.error_password2), true)
                false
            }
            !terms.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.error_checkbox), true)
                false
            }
            else -> {
                //showErrorSnackBar("Your details are valid." ,false)
                true
            }


        }
    }

    private fun registerUser() {


        showProgressDialog("Please Wait!")

        if (validateRegisterDetails()) {

            val email: String = regETEmail.text.toString().trim { it <= ' ' }
            val password: String = regETPassword.text.toString().trim { it <= ' ' }
            val name: String = regETFirstName.text.toString().trim { it <= ' ' }



            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        hideProgressDialog()

                        if (task.isSuccessful) {

                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                regETFirstName.text.toString().trim { it <= ' ' },
                                regETLastName.text.toString().trim { it <= ' ' },
                                regETEmail.text.toString().trim { it <= ' ' }

                            )
                            ///uploadImageToFirebaseStorage()


                            FirestoreClass().registerUser(this@RegisterActivity, user)
                            addUserToDatabase(name, email, firebaseUser.uid)


                            //FirebaseAuth.getInstance().signOut()

                        } else {

                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(), true)

                        }


                    }
                )

        }

    }


    private fun addUserToDatabase(firstName: String, email: String, id: String){
        mDbRef = FirebaseDatabase.getInstance("https://agro-town-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        mDbRef.child("user").child(id).setValue(User(firstName, email, id))

    }


    fun userRegistrationSuccess() {
        hideProgressDialog()




        Toast.makeText(
            this@RegisterActivity, "You are registered successfully!",
                Toast.LENGTH_SHORT
            ).show()
    }



    /*var selectedPhotoUri: Uri? = null
    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, regETFirstName.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d(TAG, "Finally we saved the user to Firebase Database")

            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to set value to database: ${it.message}")
            }
    }
*/



}