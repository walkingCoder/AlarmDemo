package com.beijing.zzu.alarmdemo.manager;


import com.beijing.zzu.alarmdemo.widget.CommonDialog;

import java.util.Stack;

/**
 * @author jiayk
 * @date 2019/10/27
 */
public class DialogManager {


    private static DialogManager instance;

    private Stack<CommonDialog> mDialogStack = new Stack<>();
    private Stack<CommonDialog> mDialogStack2 = new Stack<>();

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (instance == null) {
            synchronized (DialogManager.class) {
                if (instance == null) {
                    instance = new DialogManager();
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
                if (!mDialogStack.empty()) {
                    mDialogStack.pop().show();
                }
            }
        });
        if (mDialogStack.size() > 0) {
            CommonDialog topDialog = mDialogStack.peek();
            if (dialog.getPriority() >= topDialog.getPriority()) {
                mDialogStack.push(dialog);
                dialog.show();
                topDialog.dismiss();
                return true;
            } else {
                pushStack(dialog);
                return false;
            }
        } else {
            mDialogStack.push(dialog);
            dialog.show();
            return true;
        }
    }

    /**
     * 利用两个栈 来实现
     *
     * @param dialog
     */
    private void pushStack(CommonDialog dialog) {
        if (mDialogStack.empty()) {
            mDialogStack.push(dialog);
        } else {
            while (!mDialogStack.empty()) {
                if (mDialogStack.peek().getPriority() > dialog.getPriority()) {
                    mDialogStack2.push(mDialogStack.pop());
                } else {
                    mDialogStack.push(dialog);
                    break;
                }
            }

            if (mDialogStack.empty()) {
                mDialogStack.push(dialog);
            }

            while (!mDialogStack2.empty()) {
                mDialogStack.push(mDialogStack2.pop());
            }
        }
    }

    private void dismiss(CommonDialog dialog) {
        if (!mDialogStack.isEmpty()) {
            mDialogStack.remove(dialog);
        }
    }


}
