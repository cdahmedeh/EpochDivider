package org.cdahmedeh.orgapp.swingui.main;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Extension of JPanel with built-in support for a dataContainer reference
 * and an eventBus reference.
 * 
 * @author Ahmed El-Hajjar
 */
public abstract class CPanel extends JPanel {
	private static final long serialVersionUID = 5507618755169356983L;
	
	// - Data and EventBus -
	protected DataContainer dataContainer;
	protected EventBus eventBus;

	public CPanel(DataContainer dataContainer, EventBus eventBus) {
		this.dataContainer = dataContainer;
		
		this.eventBus = eventBus;
		this.eventBus.register(new DefaultEventRecorder());
		this.eventBus.register(getEventRecorder());
		
		windowInit();
	}

	class DefaultEventRecorder{
		@Subscribe public void changedSelectedContext(WindowLoadedNotification notification){
			postWindowInit();
		}
	}
	
	/**
	 * This method is run right after the window is created. 
	 */
	protected abstract void windowInit();
	
	/**
	 * This method is run after the window is rendered. Usually, data is loaded
	 * here. It is called by the WindowLoadedNotification notification.
	 */
	protected abstract void postWindowInit();
	
	/**
	 * Should return an object with methods for responding to EventBus
	 * notifications.
	 * 
	 * @return
	 */
	protected abstract Object getEventRecorder();
}
