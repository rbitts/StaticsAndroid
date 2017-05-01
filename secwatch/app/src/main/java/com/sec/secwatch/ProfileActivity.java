package com.sec.secwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sec.widget.pinview.PinView;
import com.sec.secwatch.widgets.UserProfilePlaceHolder;

import static com.sec.secwatch.App.getCurrentUserInfo;
import static com.sec.secwatch.MainActivity.SEC_WATCH_ACTIVITY_TAG_EXTRA_ID;
import static com.sec.secwatch.MainActivity.SEC_WATCH_ACTIVITY_TAG_EXTRA_PIN;

/**
 * Created by rbitt on 2017-04-24.
 */

public class ProfileActivity extends Activity implements UserProfilePlaceHolder.UserProfileClickListener {

    private EditText mUserInput = null;

    private UserProfilePlaceHolder mProfilePlaceHolder = null;

    private Context mContext = null;

    private PinView mPinView;

    private String[] titles;

    private String[] titlesAux;

    private String RetryPinNumber = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_profile);

        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.profile_container), iconFont);

        mUserInput = (EditText) findViewById(R.id.profile_input);
        mProfilePlaceHolder = (UserProfilePlaceHolder) findViewById(R.id.profile_place_holder);
        mProfilePlaceHolder.setUserProfileClickListener( this );
//
//        mProfilePlaceHolder.findViewById(R.id.profile_place_holder_portrait).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        findViewById(R.id.profile_activity_search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });
        findViewById(R.id.profile_activity_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        mUserInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    Search();
                }
                return false;
            }
        });

        String intentID = getIntent().getStringExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID);
        mUserInput.setText(intentID);
        mProfilePlaceHolder.setDataSet(getCurrentUserInfo());

    }

    private void Search() {
        String userID = mUserInput.getText().toString().replace("#","-");
        mProfilePlaceHolder.requestProfile(userID);
    }

    private void setInitTitles() {
        titles = getResources().getStringArray(R.array.titles);
        titlesAux = new String[4];
        System.arraycopy(titles, 0, titlesAux, 0, 4);
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    public void onBlankProfileClick() {

    }

    @Override
    public void onProfileClick() {
        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .title(R.string.profile_dialog_title)
                .customView(R.layout.profile_dialog_layout, false)
                .autoDismiss(false)
                .positiveText(R.string.profile_dialog_positive_text)
                .negativeText(R.string.profile_dialog_negative_text)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        RetryPinNumber = null;
                        dialog.dismiss();
                        hideKeyboard(mContext);
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String pinResults = mPinView.getPinResults();
                        // 핀번호 처음 입력 시
                        if( mProfilePlaceHolder.getUserInfo().getPin().isEmpty() ) {
                            if( RetryPinNumber == null ) {
                                RetryPinNumber = pinResults;
                                mPinView.clear();
                                ((TextView) dialog.findViewById(R.id.profile_dialog_content_text)).setText(R.string.profile_dialog_description_retry_text);
                            } else {
                                if( RetryPinNumber.equals(pinResults) ) {
                                    dialog.dismiss();
                                    hideKeyboard(mContext);

                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, mProfilePlaceHolder.getUserID());
                                    resultIntent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_PIN, pinResults);
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                }
                            }
                        }
                        // 핀번호가 이미 있을 시 확인만
                        else {
                            if( !mProfilePlaceHolder.getUserInfo().getPin().equals(pinResults) ) {
                                ((TextView) dialog.findViewById(R.id.profile_dialog_content_text)).setText(R.string.profile_dialog_description_failed_text);
                            } else {
                                dialog.dismiss();
                                hideKeyboard(mContext);

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_ID, mProfilePlaceHolder.getUserID());
                                resultIntent.putExtra(SEC_WATCH_ACTIVITY_TAG_EXTRA_PIN, pinResults);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }
                        }
                    }
                })
                .show();

        mPinView = (PinView) dialog.findViewById(R.id.profile_dialog_pin_view);

        setInitTitles();
    }
}
