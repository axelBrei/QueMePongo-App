package dds.frba.utn.quemepongo.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import dds.frba.utn.quemepongo.Controllers.GuardarropaController;
import dds.frba.utn.quemepongo.Model.WebServices.Error;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Guardarropa.GetGuardarropasResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatusAndApplication;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenner;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class LoginActivity extends QueMePongoActivity {

    private TextInputEditText mail;
    private TextInputEditText password;
    private TextInputLayout mailLayout;
    private TextInputLayout passwordLayout;
    private ActionProcessButton logInButton;

    private FirebaseAuth mAuth;
    private GuardarropaController guardarropaController;

    @Override
    protected int getView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        guardarropaController = new GuardarropaController(_activity);

        mail = findViewById(R.id.LoginUser);
        mailLayout = findViewById(R.id.LoginUserLayout);
        passwordLayout = findViewById(R.id.LoginPasswordLayout);
        password = findViewById(R.id.LoginPassword);
        logInButton = findViewById(R.id.LoginSignInButton);
        TextView signUpButton = findViewById(R.id.LoginRegisterButton);
        TextView forgottenpasswordButton = findViewById(R.id.LoginForgottenPasswordButton);

        mail.addTextChangedListener(cleanErrors());
        password.addTextChangedListener(cleanErrors());
        logInButton.setOnClickListener(onClickLogIn());

        signUpButton.setOnClickListener( (View v) -> {
            ActivityHelper.startActivity(_activity, RegisterActivity.class);
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
            changeButtonsAccesibility(false);
            try{
                String mailT = mail.getText().toString();
                String passT = password.getText().toString();
                mAuth.signInWithEmailAndPassword(mailT, passT)
                        .addOnCompleteListener(( Task<AuthResult> task) -> {
                                    if(task.isSuccessful()){
                                        guardarropaController.getGuardarropasDelCliente(
                                                new OnCompleteListenerWithStatusAndApplication() {
                                                    @Override
                                                    public void onComplete(Boolean success   , QueMePongo application, Object obj) {
                                                        if(success){
                                                            application.setGuardarropas( (GetGuardarropasResponse) obj);
                                                            logInButton.setProgress(100);
                                                            changeButtonsAccesibility(true);
                                                            ActivityHelper.startActivity(_activity, MainActivity.class);
                                                        }else {
                                                            logInButton.setProgress(0);
                                                        }
                                                    }
                                                }
                                        );
                                    }else{
                                        try{
                                            changeButtonsAccesibility(true);
                                            throw task.getException();
                                        }catch (FirebaseAuthException e){
                                            setFirebaseError(e.getErrorCode());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            logInButton.setProgress(0);
                                            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );
            }catch (IllegalArgumentException e){
                changeButtonsAccesibility(true);
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

    private void changeButtonsAccesibility(Boolean enabled){
        mail.setEnabled(enabled);
        password.setEnabled(enabled);
        logInButton.setEnabled(enabled);
    }
}
