package com.techun.paxcomponents.sdk_pax.module_emv.views;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.techun.paxcomponents.R;


public class EditTextAmount extends androidx.appcompat.widget.AppCompatEditText {
    Instrumentation in;

    public EditTextAmount(Context context, AttributeSet attrs) {
        super(context, attrs);
        in = new Instrumentation();
        this.setLongClickable(false);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyEditText);
        a.recycle();
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    public void setIMEEnabled(/*boolean enable, boolean showCursor*/) {
        ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
       /* if (!enable) {
            if (showCursor) {
                ((Activity) getContext()).getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                try {
                    Class<EditTextAmount> cls = EditTextAmount.class;
                    Method setSoftInputShownOnFocus;
                    setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                    setSoftInputShownOnFocus.setAccessible(true);
                    setSoftInputShownOnFocus.invoke(this, false);
                } catch (Exception e) {
                    Log.e("setIMEEnabled", e.getMessage());
                    //e.printStackTrace();
                }
            } else {
                this.setInputType(InputType.TYPE_NULL);
            }

        }*/
    }
}
