package dds.frba.utn.quemepongo.View.Activity;

import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import dds.frba.utn.quemepongo.Controllers.ClienteController;
import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.Helpers.RetrofitInstanciator;
import dds.frba.utn.quemepongo.Model.Cliente;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Repository.ClienteRepository;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class RegisterActivity extends QueMePongoActivity {

    private ActionProcessButton registerButton;
    private TextInputEditText mail;
    private TextInputEditText password;
    private TextInputEditText username;
    private TextInputLayout usernameLayout;
    private TextInputLayout mailLayout;
    private TextInputLayout passwordLayout;

    private ClienteController clienteController;

    private ClienteRepository clienteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mail = findViewById(R.id.RegisterMail);
        password = findViewById(R.id.RegisterPassword);
        username = findViewById(R.id.RegisterUsername);
        usernameLayout = findViewById(R.id.RegisterUsernameLayout);
        registerButton = findViewById(R.id.RegisterButton);
        mailLayout = findViewById(R.id.RegisterMailLayout);
        passwordLayout = findViewById(R.id.RegisterPasswordLayout);

        registerButton.setOnClickListener( (view) -> registerNewUser());

        password.addTextChangedListener(cleanErrors());
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    //Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    registerButton.performClick();
                }
                return false;
            }
        });

        clienteController = new ClienteController(this);
        clienteRepository = RetrofitInstanciator.instanciateRepository(ClienteRepository.class);
    }

    @Override
    protected int getView() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void registerNewUser(){
        registerButton.setProgress(10);
        try{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                       if(task.isSuccessful()){
                           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                           UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                   .setDisplayName(username.getText().toString())
                                   .build();
                           user.updateProfile(profileChangeRequest)
                                   .addOnCompleteListener(task1 -> {
                                       if(task1.isSuccessful()){
                                           registerButton.setProgress(100);
                                           registerUser(task.getResult().getUser().getUid());
                                       }
                                   })
                                   .addOnFailureListener(e -> ErrorHelper.showGenericError(_activity))
                                   .addOnCanceledListener(() -> {
                                       ErrorHelper.showSimpleError(_activity, "Ha ocurrido un error y se ha cancelado la peticion, por favor intente de nuevo");
                                   });

                       }else{
                           handleFirebaseError(task);
                       }
                    });
        }catch (IllegalArgumentException e){
            if(mail.getText().length() == 0)
                mailLayout.setError("El campo es obligatorio");
            if(password.getText().length() == 0)
                passwordLayout.setError("El campo es obligatorio");
            setButtonError();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


private void handleFirebaseError(Task<AuthResult> task){
        try{
            throw task.getException();
        }catch (FirebaseAuthException e){
            switch (e.getErrorCode()){
                case "ERROR_INVALID_EMAIL":{
                    mailLayout.setError("Formato de mail invalido");
                    break;
                }
                case "ERROR_EMAIL_ALREADY_IN_USE":{
                    mailLayout.setError("El mail ingresado ya esta en uso");
                    break;
                }
                case "ERROR_WEAK_PASSWORD": {
                    passwordLayout.setError("La contraseña debe tener como minimo 6 caracteres");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

}

    private TextWatcher cleanErrors(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                registerButton.setProgress(0);
                mailLayout.setError(null);
                passwordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void setButtonError(){
        registerButton.setProgress(-1);
        new Handler().postDelayed( () -> {
            registerButton.setProgress(0);
        }, 1500);
        registerButton.clearFocus();
    }

    private void registerUser(String userId){
        Cliente cliente = new Cliente(userId, mail.getText().toString(), username.getText().toString());
        clienteController.registrarClienteNuevo(
                cliente,
                new OnCompleteListenerWithStatus() {
                    @Override
                    public void onComplete(Boolean succed, Object obj) {
                        if(succed){
                            Bundle bundle = new Bundle();
                            bundle.putBoolean(CrearGuardarropaActivity.SHOW_INTRO_TEXT, true);
                            ActivityHelper.startActivityWithBacbButtonBlocked(
                                    RegisterActivity.this,
                                    CrearGuardarropaActivity.class,
                                    bundle
                            );
                        }else {
                            setButtonError();
                        }
                    }
                }
        );
    }
}
