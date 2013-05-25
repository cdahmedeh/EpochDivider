package org.cdahmedeh.orgapp.ui.notification;

import org.cdahmedeh.orgapp.context.Context;

public class ContextChangedEvent {
	private Context context;
	public Context getContext() {
		return context;
	}
	
	public ContextChangedEvent(Context context) {
		this.context = context;
	}
}
