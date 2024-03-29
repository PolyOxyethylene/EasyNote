package com.kongzue.drawerbox;

import android.content.res.Resources;
import android.graphics.Color;

import com.kongzue.dialogx.interfaces.DialogLifecycleCallback;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.style.MaterialStyle;

/**
 * @author: Kongzue
 * @github: https://github.com/kongzue/
 * @homepage: http://kongzue.com/
 * @mail: myzcxhh@live.cn
 * @createTime: 2022/4/28 15:33
 */
public class DrawerBox {
    
    OnBindView<DrawerBoxDialog> onBindView;
    DrawerBoxDialog dialog;
    int minHeight = 0;
    
    public DrawerBox() {}

    /**
     * 创建一个默认最小高度为 height 的底部抽屉，height 单位为 dp
     * @param height 抽屉的最小高度
     */
    public DrawerBox(Integer height) {
        minHeight = dip2px(height);
    }
    
    public static DrawerBox build() {
        return new DrawerBox();
    }

    /**
     * 创建一个默认最小高度为 height 的底部抽屉，height 单位为 dp
     * @param height 抽屉的最小高度
     * @return 创建的抽屉
     */
    public static DrawerBox build(Integer height) {return new DrawerBox(height);}
    
    public DrawerBox show(OnBindView<DrawerBoxDialog> onBindView) {
        dialog = DrawerBoxDialog.build(onBindView)
                .setStyle(MaterialStyle.style())
                .setBackgroundColor(Color.parseColor("#F1F1F1"))
                .setMinHeight(minHeight)
                .setDialogLifecycleCallback(new DialogLifecycleCallback<DrawerBoxDialog>() {
                    @Override
                    public void onShow(DrawerBoxDialog dialog) {
                        DrawerBox.this.dialog = dialog;
                        super.onShow(dialog);
                    }
                });
        dialog.show();
        return this;
    }
    
    private int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    
    public DrawerBoxDialog getDialog() {
        return dialog;
    }
    
    public int getMinHeight() {
        return minHeight;
    }
    
    public DrawerBox setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }
    
    public void close() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    
    /**
     * 展开
     *
     * @return DrawerBox
     */
    public DrawerBox unfold() {
        if (dialog != null) {
            dialog.unfold();
        }
        return this;
    }
    
    /**
     * 收起
     *
     * @return DrawerBox
     */
    public DrawerBox fold() {
        if (dialog != null) {
            dialog.fold();
        }
        return this;
    }
    
    public boolean isFold() {
        if (dialog != null) {
            return dialog.isFold();
        }
        return false;
    }
}
