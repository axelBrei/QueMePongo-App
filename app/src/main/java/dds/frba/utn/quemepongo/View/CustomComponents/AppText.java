package dds.frba.utn.quemepongo.View.CustomComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.Validable;
import me.grantland.widget.AutofitTextView;

public class AppText  extends TextInputLayout implements Validable {

    private Context context;
    private Boolean editable;

    @BindView(R.id.AppText_Container)
        ConstraintLayout container;

    @BindView(R.id.AppText_EditText)
        TextInputEditText editText;
    @BindView(R.id.AppText_Text)
        AutofitTextView text;
    @BindView(R.id.AppText_Layout)
        TextInputLayout layout;
    @BindView(R.id.AppText_RightImage)
        ImageView rightImage;
    @BindView(R.id.AppText_LeftImage)
        ImageView leftImage;
    @BindView(R.id.AppText_Divider)
        View divider;


    public AppText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.app_text,this, true);
        ButterKnife.bind(this,v);
        init(attrs);
    }

    public AppText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.app_text,this, true);
        ButterKnife.bind(this,v);
        init(attrs);
    }

    public AppText(Context context) {
        super(context);
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.app_text,this, true);
        ButterKnife.bind(this,v);
        init(null);
    }

    private void init(@Nullable AttributeSet attributeSet){

        if(attributeSet == null) return;

        TypedArray array = getContext().obtainStyledAttributes(attributeSet,R.styleable.AppText);
        String hint = array.getString(R.styleable.AppText_hint);
        String attrText = array.getString(R.styleable.AppText_text);
        Drawable leftDrawable = array.getDrawable(R.styleable.AppText_leftSrc);
        Drawable rightDrawable = array.getDrawable(R.styleable.AppText_rightSrc);
        this.editable = array.getBoolean(R.styleable.AppText_editable, true);
        Boolean showDivider = array.getBoolean(R.styleable.AppText_showDivider, false);

        text.setVisibility(editable ? GONE : VISIBLE);
        divider.setVisibility((!editable && showDivider) ? VISIBLE : GONE);
        layout.setVisibility(editable ? VISIBLE : GONE);
        editText.setVisibility(editable ? VISIBLE : GONE);

        if(isNotNull(rightDrawable)){
            rightImage.setVisibility(VISIBLE);
            rightImage.setImageDrawable(rightDrawable);
            rightImage.setColorFilter(array.getColor(
                    R.styleable.AppText_rightSrcTint,
                    context.getColor(R.color.colorPrimary))
            );
        }
        if(isNotNull(leftDrawable)){
            leftImage.setVisibility(VISIBLE);
            leftImage.setImageDrawable(leftDrawable);
            leftImage.setColorFilter(array.getColor(
                    R.styleable.AppText_leftSrcTint,
                    context.getColor(R.color.colorPrimary))
            );
        }
        if(isNotNull(hint)) {
            if(editable){
                layout.setHint(hint);
            }else {
                text.setHint(hint);
            }
        }
        if(isNotNull(attrText)) setText(attrText);

        text.setEnabled(!array.getBoolean(R.styleable.AppText_disabled, false));

        array.recycle();
    }

    @Override
    public void setError(@Nullable CharSequence errorText) {
        layout.setError(errorText);
    }

    @Override
    public Object getContent() {
        String string = getText();
        return string;
    }

    @Override
    public void cleanErrorOnChange() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                layout.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                layout.setError(null);
            }
        });
    }

    @Override
    public void setError(String error) {
        layout.setError(error);
    }

    private <T> boolean isNotNull(T o){
        return o != null;
    }

    public void setText(String text){
        if(editable){
            this.editText.setText(text);
        }else {
            this.text.setText(text);
        }


    }
    public String getText(){
        if(!editable){
            return text.getText().toString();
        }else{
            return editText.getText().toString();
        }
    }
}
