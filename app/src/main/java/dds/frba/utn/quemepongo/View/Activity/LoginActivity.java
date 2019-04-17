package dds.frba.utn.quemepongo.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.List;

import dds.frba.utn.quemepongo.Model.Prenda;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.PrendasJsonParser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mail;
    private TextInputEditText password;
    private TextInputLayout mailLayout;
    private TextInputLayout passwordLayout;
    private ActionProcessButton logInButton;
    private TextView signUpButton;
    private TextView forgottenpasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        List<Prenda> prendas = PrendasJsonParser.getJsonPrendasJson(this);

        mail = findViewById(R.id.LoginUser);
        mailLayout = findViewById(R.id.LoginUserLayout);
        passwordLayout = findViewById(R.id.LoginPasswordLayout);
        password = findViewById(R.id.LoginPassword);
        logInButton = findViewById(R.id.LoginSignInButton);
        signUpButton = findViewById(R.id.LoginRegisterButton);
        forgottenpasswordButton = findViewById(R.id.LoginForgottenPasswordButton);

        mail.addTextChangedListener(cleanErrors());
        password.addTextChangedListener(cleanErrors());
        logInButton.setOnClickListener(onClickLogIn());

        signUpButton.setOnClickListener( (View v) -> {
            Intent inten = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(inten);
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    logInButton.performClick();
                }
                return false;
            }
        });
    }



    private View.OnClickListener onClickLogIn(){
        return (View v) -> {
            logInButton.setProgress(10);
            try{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(( Task<AuthResult> task) -> {
                                    if(task.isSuccessful()){
                                        logInButton.setProgress(100);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else{
                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthException e){
                                            setFirebaseError(e.getErrorCode());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        );
            }catch (IllegalArgumentException e){
                e.printStackTrace();
                if(mail.getText().length() == 0)
                    mailLayout.setError("El campo no puede estar vacio");
                if(password.getText().length() == 0)
                    passwordLayout.setError("El campo no puede estar vacio");

                setButtonError();
            }

        };
    }

    private TextWatcher cleanErrors(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                logInButton.setProgress(0);
                mailLayout.setError(null);
                passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


    private void setFirebaseError(String error){
        switch (error){
            case "ERROR_INVALID_EMAIL":{
                mailLayout.setError("Mail invalido");
                break;
            }
            case "ERROR_WRONG_PASSWORD":{
                passwordLayout.setError("Contrañesa incorrecta");
                break;
            }
            case "ERROR_USER_DISABLED":{
                mailLayout.setError("El usuario ingresado esta deshabilitado");
                break;
            }
            case "ERROR_USER_NOT_FOUND":{
                mailLayout.setError("Mail inexistente");
                break;
            }
            default:{
                mailLayout.setError("Credenciales Invalidas");
                break;
            }
        }
        setButtonError();
    }

    private void setButtonError(){
        logInButton.setProgress(-1);
        new Handler().postDelayed( () -> {
            logInButton.setProgress(0);
        }, 1500);
        logInButton.clearFocus();
    }
}
