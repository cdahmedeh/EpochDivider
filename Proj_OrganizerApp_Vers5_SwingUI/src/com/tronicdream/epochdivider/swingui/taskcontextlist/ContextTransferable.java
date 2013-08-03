package com.tronicdream.epochdivider.swingui.taskcontextlist;

import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.swingui.components.ObjectTransferable;

public class ContextTransferable extends ObjectTransferable<Context> {
	public ContextTransferable(Context context) {
		super(Context.class, context);
	}
}