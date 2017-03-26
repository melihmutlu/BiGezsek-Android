package com.hackathon.getir.bigezsek;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

/**
 * Created by taha on 25/03/17.
 */

@Layout(R.layout.card_view)
public class Card {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public Card(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Click(R.id.cardView)
    private void click(){
        Intent messageactivity = new Intent(mContext,MessageActivity.class);
        messageactivity.putExtra("otherId",mProfile.getId());
        messageactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(messageactivity);
    }

    @Resolve
    private void onResolved(){
        Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
        nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        locationNameTxt.setText(mProfile.getTime());
    }

    @SwipeOut
    private void onSwipedOut(){
        mSwipeView.addView(this);
    }
}