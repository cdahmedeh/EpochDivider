package org.cdahmedeh.orgapp.swingui.context;

import org.cdahmedeh.orgapp.swingui.components.ObjectTransferable;
import org.cdahmedeh.orgapp.types.context.Context;

public class ContextTransferable extends ObjectTransferable<Context> {
	public ContextTransferable(Context context) {
		super(Context.class, context);
	}
}