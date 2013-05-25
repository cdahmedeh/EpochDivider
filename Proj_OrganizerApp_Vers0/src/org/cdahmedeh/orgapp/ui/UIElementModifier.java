package org.cdahmedeh.orgapp.ui;

import org.eclipse.swt.layout.GridLayout;

public class UIElementModifier {
	public static void removeSpacingAndMargins(GridLayout gl) {
		gl.marginBottom = 0;
		gl.marginLeft = 0;
		gl.marginRight = 0;
		gl.marginTop = 0;
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.verticalSpacing = 0;
		gl.horizontalSpacing = 0;
	}
}
