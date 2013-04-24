package org.cdahmedeh.orgapp.swingui.main;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public abstract class CPanel extends JPanel {
	private static final long serialVersionUID = 5507618755169356983L;
	
	// - Data -
	protected DataContainer dataContainer;

	public CPanel(DataContainer dataContainer, EventBus eventBus) {
		this.dataContainer = dataContainer;
		
		this.eventBus = eventBus;
		this.eventBus.register(new DefaultEventRecorder());
		this.eventBus.register(getEventRecorder());
		
		windowInit();
	}
	
	// - EventBus -
	protected EventBus eventBus;

	protected abstract Object getEventRecorder();
	
	class DefaultEventRecorder{
		@Subscribe public void changedSelectedContext(WindowLoadedNotification notification){
			postWindowInit();
		}
	}
	
	protected abstract void windowInit();
	protected abstract void postWindowInit();
}
