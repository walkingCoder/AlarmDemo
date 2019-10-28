package com.beijing.zzu.alarmdemo.manager;

import com.beijing.zzu.alarmdemo.widget.CommonDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author jiayk
 * @date 2019/10/27
 */
public class DialogManager2 {

    private static DialogManager2 instance;

    private List<CommonDialog> mDialogLevels = new ArrayList<>();

    private DialogManager2() {
    }

    public static DialogManager2 getInstance() {
        if (instance == null) {
            synchronized (DialogManager2.class) {
                if (instance == null) {
                    instance = new DialogManager2();
                }
            }
        }
        return instance;
    }

    public boolean canShow(final CommonDialog dialog) {
        dialog.setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dismiss(dialog);
                if (mDialogLevels.size() > 0) {
                    CommonDialog firstDialog = mDialogLevels.remove(0);
                    firstDialog.show();
                }
            }
        });
        if (mDialogLevels.size() > 0) {
            CommonDialog topDialog = mDialogLevels.get(0);
            if (dialog.getPriority() >= topDialog.getPriority()) {
                mDialogLevels.add(0, dialog);
                dialog.show();
                topDialog.dismiss();
                return true;
            } else {
                mDialogLevels.add(dialog);
                /**
                 * 利用系统自带的排序
                 */
                Collections.sort(mDialogLevels, new Comparator<CommonDialog>() {
                    @Override
                    public int compare(CommonDialog o1, CommonDialog o2) {
                        return o2.getPriority() - o1.getPriority();
                    }
                });
                return false;
            }
        } else {
            mDialogLevels.add(dialog);
            dialog.show();
            return true;
        }
    }

    private void dismiss(CommonDialog dialog) {
        mDialogLevels.remove(dialog);
    }

}
