package com.example.madcamp1_2_2;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ImageClickListener implements View.OnClickListener {
    Context context;
    int imageID;

    public ImageClickListener(Context context, int imageID){
        this.context = context;
        this.imageID = imageID;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("image ID", imageID);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
