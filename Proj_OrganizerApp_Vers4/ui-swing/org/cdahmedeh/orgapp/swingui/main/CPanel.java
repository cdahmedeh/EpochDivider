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

	public CPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		preInit();
	}
	
	// - EventBus -
	private EventBus eventBus;

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new DefaultEventRecorder());
		this.eventBus.register(getEventRecorder());
	}
	
	protected abstract Object getEventRecorder();
	
	class DefaultEventRecorder{
		@Subscribe public void changedSelectedContext(WindowLoadedNotification notification){
			postInit();
		}
	}
	
	protected abstract void preInit();
	protected abstract void postInit();
}
