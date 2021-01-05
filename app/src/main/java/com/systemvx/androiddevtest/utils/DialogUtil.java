package com.systemvx.androiddevtest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.systemvx.androiddevtest.orderClientActivity;

public class DialogUtil {
    public static void showDialog(Context ctx, String msg, boolean goHome)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(ctx)
                .setMessage(msg).setCancelable(false);
        if(goHome)
        {
            builder.setPositiveButton("确定",((dialog, which) -> {
                Intent i=new Intent(ctx, orderClientActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ctx.startActivity(i);
            }));
        }
        else
        {
            builder.setPositiveButton("确定",null);
        }
        builder.create().show();
    }
    public static void showDialog(Context ctx, View view)
    {
        new AlertDialog.Builder(ctx)
                .setView(view).setCancelable(false)
                .setPositiveButton("确定",null)
                .create()
                .show();
    }
    public static void showDialog(Context ctx, String msg, Intent intent)
    {
        ctx.startActivity(intent);
}}

