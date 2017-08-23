package sega.fastnetwork.test.activity


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register.*
import sega.fastnetwork.test.R
import sega.fastnetwork.test.customview.CircularAnim
import sega.fastnetwork.test.model.User
import sega.fastnetwork.test.presenter.LoginPresenter
import sega.fastnetwork.test.util.Constants
import sega.fastnetwork.test.util.Validation.validateEmail
import sega.fastnetwork.test.util.Validation.validateFields

/**
 * Created by sega4 on 23/05/2017.
 */

class RegisterActivity : AppCompatActivity(), LoginPresenter.LoginView {


    override fun isRegisterSuccessful(isRegisterSuccessful: Boolean, type: Int) {
        if(isRegisterSuccessful)
        {
            showSnackBarMessage(resources.getString(R.string.message_signin))
            progressBar.visibility = View.GONE
            CircularAnim.show(btn_join).go()
        }
    }

    var mRegisterPresenter: LoginPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mRegisterPresenter = LoginPresenter(this)
        btn_join!!.setOnClickListener {
            CircularAnim.hide(btn_join)
                    .endRadius((progressBar.height / 2).toFloat())
                    .go(object : CircularAnim.OnAnimationEndListener {
                        override fun onAnimationEnd() {
                            progressBar.visibility = View.VISIBLE
                            /*
                                }*/
                            register()
                        }
                    })

        }
        btn_backtologin!!.setOnClickListener {
            gotologin()
        }
    }


    private fun register() {

        setError()


        var err = 0

        if (!validateFields(name!!.text.toString())) {

            err++
            name!!.error = "Name should not be empty !"
        }

        if (!validateEmail(email!!.text.toString())) {

            err++
            email!!.error = "Email should be valid !"
        }

        if (!validateFields(password!!.text.toString())) {

            err++
            password!!.error = "Password should not be empty !"
        }
        if (password!!.text.toString() != repassword!!.text.toString()||repassword!!.text.toString()=="") {

            err++

            repassword!!.error = "Password do not match or empty!"

        }
        if (err == 0) {
            val user = User()
            user.name = name.text.toString()
            user.password = password.text.toString()
            user.email = email.text.toString()
            user.tokenfirebase = FirebaseInstanceId.getInstance().token
           mRegisterPresenter!!.register(user,Constants.LOCAL)

        } else {
            progressBar.visibility = View.GONE
            CircularAnim.show(btn_join).go()
            showSnackBarMessage("Enter Valid Details !")
        }
    }

    private fun gotologin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)

        startActivity(intent)
        finish()
        overridePendingTransition(0, 0)
    }

    private fun setError() {

        name!!.error = null
        email!!.error = null
        password!!.error = null
        repassword!!.error = null
    }



    override fun isLoginSuccessful(isLoginSuccessful: Boolean) {

    }

    override fun setErrorMessage(errorMessage: String) {
        showSnackBarMessage(errorMessage)
    }

    override fun getUserDetail(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun showSnackBarMessage(message: String?) {


        Snackbar.make(findViewById(R.id.root_register), message!!, Snackbar.LENGTH_SHORT).show()

    }


    public override fun onDestroy() {
        super.onDestroy()

    }
}