
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.requestum.android.motoguy.R;

import java.util.ArrayList;
import java.util.List;

public class ValidationHelper {

    @NonNull
    private final Context mContext;
    @NonNull
    public final List<Validator> mValidators;

    public ValidationHelper(Context context) {
        this.mContext = context;
        this.mValidators = new ArrayList<>();
    }

    public ValidationHelper addValidator(Validator validator) {
        mValidators.add(validator);
        return this;
    }


    public interface Validator {

        void validate() throws IllegalArgumentException;
    }

    public static abstract class FieldValidator implements Validator {

        @NonNull
        private EditText editText;
        @NonNull
        private String message;

        public FieldValidator(EditText editText, String message) {
            this.editText = editText;
            this.message = message;
        }

        public FieldValidator(EditText editText, @StringRes int messageResId) {
            this(editText, editText.getContext().getString(messageResId));
        }

        @Override
        public void validate() throws IllegalArgumentException{
            String text = editText.getText().toString();
            if (!isValid(text)) throw new IllegalArgumentException(message);
        }

        public abstract boolean isValid(String input);
    }

    public static final class EmptyFieldValidator extends FieldValidator {

        public EmptyFieldValidator(EditText editText) {
            super(editText, "Field can not be empty");
        }

        @Override
        public boolean isValid(String input) {
            return !input.trim().isEmpty();
        }
    }

    public static final class EmailFieldValidator extends FieldValidator {

        public EmailFieldValidator(EditText editText) {
            super(editText, R.string.irregulart_email_error);
        }

        @Override
        public boolean isValid(String input) {
            return Patterns.EMAIL_ADDRESS.matcher(input).matches();
        }
    }

    public static final class PhoneFieldValidator extends FieldValidator {

        public PhoneFieldValidator(EditText editText) {
            super(editText, R.string.phone_invalid_error_message);
        }

        @Override
        public boolean isValid(String input) {
            return Patterns.PHONE.matcher(input).matches();
        }
    }

    public boolean isValid() {
        try {
            for (Validator validator : mValidators) {
                validator.validate();
            }
            return true;
        } catch (IllegalArgumentException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
