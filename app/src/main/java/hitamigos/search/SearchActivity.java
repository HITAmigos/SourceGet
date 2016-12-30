/*
 * Copyright (c) 2016.
 * 个人版权所有
 * kuangmeng.net
 */

package hitamigos.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import hitamigos.sourceget.R;

public class SearchActivity extends Activity {
    EditText searchTextView;
    ImageButton actionVoiceBtn;
    ImageButton actionEmptyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        searchTextView.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(searchTextView.getText())) {       //如果输入的内容是空的话，显示语音的按钮
                    actionEmptyBtn.setVisibility(View.INVISIBLE);
                    actionVoiceBtn.setVisibility(View.VISIBLE);
                } else {
                    actionEmptyBtn.setVisibility(View.VISIBLE);
                    actionVoiceBtn.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_SEARCH)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        actionVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        actionEmptyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchTextView.setText("");
                return false;
            }
        });

    }


}
