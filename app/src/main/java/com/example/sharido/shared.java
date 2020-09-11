package com.example.sharido;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class shared {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;
    String FileName="sdfile";
    String Data="b";

    public shared(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences(FileName,mode);
        editor=sharedPreferences.edit();
    }

    public void seconduser(){
        editor.putBoolean(Data,true);
        editor.commit();
    }
    public void firstuser(){
        if(!login())
        {
            Intent intent=new Intent(context,Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }

    private boolean login(){
        return sharedPreferences.getBoolean(Data,false);
    }
}
