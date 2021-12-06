package com.homies.hovedopgave.utils;

import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.ImageButton;
=======
>>>>>>> origin/JacqquesMasterpiece
import android.widget.TextView;

/* Written by **Jacob Ravn** jaco8748 */
public class EditTextEnterClicked {
    // When person clicks enter, we we click the appropriate add button.
    public static void setPressEnterOnEditTextBtnClick(EditText editText, Button button) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    button.performClick();
                }

                return true;
            }
        });
    }

    // overload
    // with imagebutton instead of button
    public static void setPressEnterOnEditTextBtnClick(EditText editText, ImageButton button) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    button.performClick();
                }

                return true;
            }
        });
    }
    // We jump to the appropriate next EditText
    public static void setPressEnterOnEditTextNextEditText(EditText editText, EditText nextEditText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    nextEditText.requestFocus();
                    System.out.println("worked?");
                }

                return true;
            }
        });
    }
}
