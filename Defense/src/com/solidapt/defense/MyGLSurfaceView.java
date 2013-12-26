package com.solidapt.defense;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Brian on 5/29/13.
 */
public class MyGLSurfaceView extends GLSurfaceView {
	public MyRenderer myRenderer;
	
    public MyGLSurfaceView(Context context) {
        super(context);
        // Set the Renderer for drawing on the GLSurfaceView
        super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        setRenderer(myRenderer = new MyRenderer(context));
    }
  
}
