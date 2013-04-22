package org.cdahmedeh.orgapp.swingui.notification;

import org.cdahmedeh.orgapp.types.context.Context;

public class SelectedContextChangedNotification {
	private Context context;

	public SelectedContextChangedNotification(Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
