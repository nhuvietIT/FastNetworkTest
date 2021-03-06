package sega.fastnetwork.test.lib.SliderTypes.Transformers;

/**
 * Created by daimajia on 14-5-29.
 */

import android.view.View;

import sega.fastnetwork.test.util.ViewHelper;


public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        ViewHelper.INSTANCE.setPivotX(view,position < 0 ? 0 : view.getWidth());
        ViewHelper.INSTANCE.setScaleX(view,position < 0 ? 1f + position : 1f - position);
    }

}