package com.tronicdream.epochdivider.swingui.context;

import com.tronicdream.epochdivider.types.context.Context;

import com.tronicdream.epochdivider.swingui.components.ObjectTransferable;

public class ContextTransferable extends ObjectTransferable<Context> {
	public ContextTransferable(Context context) {
		super(Context.class, context);
	}
}