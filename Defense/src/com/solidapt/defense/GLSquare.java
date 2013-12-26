package com.solidapt.defense;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * Defines a square to draw sprites on
 * Created by Brian on 5/30/13.
 */
public class GLSquare {
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array

    private float[] vertices;
	int loadCount = 0;
    
    private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
    private float texture[];

    // Constructor - Setup the vertex buffer
    public GLSquare(int width, int height) {
        float halfWidth = (float)width/2;
        float halfHeight = (float)height/2;
        vertices = new float[]{  // Vertices for the square
                -halfWidth, -halfHeight,  0.0f,  // 0. left-bottom
                halfWidth, -halfHeight,  0.0f,  // 1. right-bottom
                -halfWidth,  halfHeight,  0.0f,  // 2. left-top
                halfWidth,  halfHeight,  0.0f   // 3. right-top*/
        };
        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
        
        texture = new float[]{    		
        		// Mapping coordinates for the vertices
        		0.0f, 0.0f,		// top left		(V2)
        		1.0f, 0.0f,		// bottom left	(V1)
        		0.0f, 1.0f,		// top right	(V4)
        		1.0f, 1.0f		// bottom right	(V3)
        };
        vbb = ByteBuffer.allocateDirect(texture.length * 4);
        vbb.order(ByteOrder.nativeOrder());
    	textureBuffer = vbb.asFloatBuffer();
    	textureBuffer.put(texture);
    	textureBuffer.position(0);
    }

    // Render the shape
    public void draw(GL10 gl, Texture texture, int currentFrame) {
    	// bind the previously generated texture
        //gl.glColor4f(0.5f, 0.0f, 0.0f, 0.0f);
    	gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTexture(currentFrame));
    	
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY); 
    
        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);
        
        // set the color for the square

        // Point to our vertex buffer
        
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
        
        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

        //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

    }
    

}
