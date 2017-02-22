package com.atestkit.atestkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atestkit.atestkitcore.TestKit;
import com.atestkit.atestkitcore.test.event.TestMethod;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEtExecute;
    TextView mTvNum;
    Button mBtAddNum;
    Button mBtSubNum;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);

        mEtExecute = (EditText) findViewById(R.id.et_execute_cmd);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mBtAddNum = (Button) findViewById(R.id.bt_add_num);
        mBtSubNum = (Button) findViewById(R.id.bt_sub_num);

        mBtAddNum.setOnClickListener(this);
        mBtSubNum.setOnClickListener(this);

        // 查看命令行输出
        mEtExecute.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    Editable etext = mEtExecute.getText();
                    TestKit.executeCmd(etext.toString());
                    mEtExecute.setText(etext.toString());

                    mEtExecute.setSelection(etext.length());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        TestKit.registActivity(this);
        TestKit.registEvent(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TestKit.unRegistActivity(this);
        TestKit.unRegistEvent(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_add_num) {
            addOne();
        } else if (v.getId() == R.id.bt_sub_num) {
            subOne();
        }
    }

    @TestMethod(name = "addOne")
    private void addOne() {
        mTvNum.setText(String.valueOf(num++));
    }

    @TestMethod(name = "subOne")
    private void subOne() {
        mTvNum.setText(String.valueOf(num--));
    }

    @TestMethod(name = "showToast", description = "just show Toast!", args = {"hello world!"})
    private void showToast(String toast) {
        Toast.makeText(getApplication(), toast, Toast.LENGTH_SHORT).show();
    }
}
