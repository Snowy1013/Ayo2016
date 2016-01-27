package org.ayo.view.toast.notification;
/*
 * Copyright 2014 gitonway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

public class Jelly extends BaseEffect {

    @Override
    protected void setInAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", .3f, .5f, .9f,.8f,.9f,1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view,"scaleY",.3f, .5f, .8f,.9f,.8f,1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)
        );
    }

    @Override
    protected void setOutAnimation(View view) {
        getAnimatorSet().playTogether(
//                ObjectAnimator.ofFloat(view, "scaleX", 1,.9f,.8f,.9f,.5f,0).setDuration(mDuration),
//                ObjectAnimator.ofFloat(view,"scaleY",1,.8f,.9f,.8f,.5f,0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration*2/3)
        );
    }

    @Override
    protected long getAnimDuration(long duration) {
        return duration;
    }
}
