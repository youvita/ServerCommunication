package com.dev.kh.connectionsever.helper.ui.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DlgAlert {
	private static final int DEFAULT_DIALOG_INDEX = 0;

	public enum DIALOG_BTN {
		LEFT_BTN, RIGHT_BTN
	}

	public interface OnClickDlgListener {
		void onClickDlgButton(int dialogIndex, DIALOG_BTN buttonType);
	}

	private static AlertDialog.Builder getDialogCreate(Context context) {
		AlertDialog.Builder ad;
		ad = new AlertDialog.Builder(context);

		return ad;
	}

	/*
	 * alert only one button
	 */
	public static void showAlert(Context context, String msg, String title,  String btn, boolean cancelable, final OnClickDlgListener listener){
		AlertDialog.Builder ad = getDialogCreate(context);
		ad.setCancelable(cancelable);
		ad.setTitle(title);
		ad.setMessage(msg);
		ad.setPositiveButton(btn, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if(listener != null) {
					listener.onClickDlgButton(DEFAULT_DIALOG_INDEX, DIALOG_BTN.RIGHT_BTN);
				}
			}
		});
		ad.show();
	}

	/*
	 * alert two button
	 */
	public static void showAlertOkCancel(Context context, String msg, String title, String leftName, String rightName, final OnClickDlgListener listener, boolean cancelable){
		AlertDialog.Builder ad = getDialogCreate(context);

		ad.setTitle(title);
		ad.setMessage(msg);
		ad.setCancelable(cancelable);
		ad.setPositiveButton(leftName, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if(listener != null) {
					listener.onClickDlgButton(1, DIALOG_BTN.LEFT_BTN);
				}
			}
		});
		ad.setNegativeButton(rightName, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if(listener != null) {
					listener.onClickDlgButton(2, DIALOG_BTN.RIGHT_BTN);
				}
			}
		});
		ad.show();
	}

}