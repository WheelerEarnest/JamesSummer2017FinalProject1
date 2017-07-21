package com.example.wheeler.jamessummer2017finalproject.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;

import com.example.wheeler.jamessummer2017finalproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wheeler on 6/21/17.
 */

public class EraseDialog extends Dialog {

    private final ICustomDialogListener listener;

    public interface ICustomDialogListener{
        public void onOkClicked(String msg);

    }

    @BindView(R.id.settings_erase_cb)
    CheckBox confirmErase;

    @OnClick(R.id.settings_erase_submit)
    public void ok(View view){
        if(confirmErase.isChecked()) {
            listener.onOkClicked("You have successfully erased your data");
        }
        else {
            listener.onOkClicked("Check the box to confirm erase");
        }
        cancel();
    }

    public EraseDialog(@NonNull Context context, ICustomDialogListener listener) {
        super(context, R.style.dialog);
        setContentView(R.layout.dialog_erase);
        ButterKnife.bind(this);
        this.listener = listener;
    }


}
