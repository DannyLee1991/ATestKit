package com.atestkit.atestkitcore.test.activity;

import android.app.Activity;

import com.atestkit.atestkitcore.test.AbsTest;
import com.atestkit.atestkitcore.utils.reflect.FieldInfo;

import java.util.List;

import static com.atestkit.atestkitcore.utils.ReflectUtils.getFieldInfos;


/**
 * Created by lijianan on 16/11/7.
 */

public class ActivityTest extends AbsTest {
    private Activity activity;

    public ActivityTest(Activity activity) {
        this.activity = activity;
    }

    public void doFinishAction() {
        if (activity != null) {
            activity.finish();
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public String getActivityName() {
        return activity.getLocalClassName();
    }

    public List<FieldInfo> getActivityFieldInfos() {
        try {
            return getFieldInfos(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getActivityFieldInfosHtmlStr() {
        List<FieldInfo> infos = getActivityFieldInfos();
        String infoStr = "";
        if (infos != null) {
            for (FieldInfo info : infos) {
                infoStr += info.toHtmlString() + "<br/><br/>";
            }
        }
        return infoStr;
    }
}
