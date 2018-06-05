
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

public class DialogInfo extends DialogFragment {
    public static final String MESSAGE_DIALOG = "message_dialog";
    private AlertDialog dialog;
    private String message;

    public static DialogInfo newInstance(String message) {
        DialogInfo dialog = new DialogInfo();
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE_DIALOG, message);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        message = getArguments().getString(MESSAGE_DIALOG);
        builder.setMessage(message)
                .setCancelable(false)
                .setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(
                            @SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        dialog = builder.create();
        return dialog;
    }

    public void showDialog(FragmentManager fragmentManager) {
        if (dialog == null || !dialog.isShowing()) {
            show(fragmentManager, null);
        }
    }
}
