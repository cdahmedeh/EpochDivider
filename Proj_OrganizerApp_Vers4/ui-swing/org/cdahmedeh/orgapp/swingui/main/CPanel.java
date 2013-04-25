package org.cdahmedeh.orgapp.swingui.main;

import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.cdahmedeh.orgapp.swingui.notification.WindowLoadedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Extension of JPanel with built-in support for a dataContainer reference, an 
 * eventBus reference and a logger reference.
 * 
 * It is designed for delayed data loading to ensure that window is rendered as
 * soon as possible. See windowInit() and postWindowInit() methods.
 * 
 * @author Ahmed El-Hajjar
 */
public abstract class CPanel extends JPanel {
	private static final long serialVersionUID = 5507618755169356983L;
	
	// - Data and EventBus -
	protected DataContainer dataContainer;
	protected EventBus eventBus;
	protected Logger logger;

	// - Construct -
	public CPanel(DataContainer dataContainer, EventBus eventBus) {
		this.dataContainer = dataContainer;
		
		this.logger = Logger.getLogger("org.cdahmedeh.orgapp.log");
		
		this.eventBus = eventBus;
		this.eventBus.register(new DefaultEventRecorder());
		this.eventBus.register(getEventRecorder());
		
		windowInit();
	}

	// - Default event for running postWindowInit() after window has rendered.
	class DefaultEventRecorder{
		@Subscribe public void changedSelectedContext(WindowLoadedNotification notification){
			postWindowInit();
			logger.info("Post-load event done for:" + getClass().getSimpleName());
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
