package sega.fastnetwork.test.lib.SliderTypes.Animations;

import android.view.View;

/**
 * This interface gives you chance to inject your own animation or do something when the
 * {@link sega.fastnetwork.test.lib.SliderTypes.Tricks.ViewPagerEx} animation (PagerTransformer) starts or ends.
 *
 *
 * There are two items you have to know. The first item is the slider you are dragging. This item
 * I call it Current Item. The second is the slider that gonna to show. I call that Next Item.
 *
 * When you start to drag the slider in front of you, onPrepareCurrentItemLeaveScreen() and
 * onPrepareNextItemShowInScreen will be called.
 *
 * When you finish drag, the onCurrentItemDisappear and onNextItemAppear will be invoked.
 *
 * You can see a demo class {@link sega.fastnetwork.test.lib.SliderTypes.Animations.DescriptionAnimation},
 * this class gives the description text an animation.
 */
public interface BaseAnimationInterface {

    /**
     * When the current item prepare to start leaving the screen.
     * @param current
     */
    void onPrepareCurrentItemLeaveScreen(View current);

    /**
     * The next item which will be shown in ViewPager/
     * @param next
     */
    void onPrepareNextItemShowInScreen(View next);

    /**
     * Current item totally disappear from screen.
     * @param view
     */
    void onCurrentItemDisappear(View view);

    /**
     * Next item totally show in screen.
     * @param view
     */
    void onNextItemAppear(View view);
}
