package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * PhineasAndFerb.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class PhineasAndFerb implements GLEventListener {

    Texture background;

    public static void main(String[] args) {
        Frame frame = new Frame("phineas and ferb");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new PhineasAndFerb());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        //Textures code
        try {
            //load textures here
            background = TextureIO.newTexture(new File("pic.jpg"), true);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        gl.glEnable(GL.GL_TEXTURE_2D);
        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(1f, 1f, 1f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -6.0f);
        background.enable();
        background.bind();
        gl.glColor3f(1f, 1f, 1.0f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-3.5f, 2.5f, 0.0f);   // Top Left
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(3.5f, 2.5f, 0.0f);   // Top Right
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(3.5f, -2.5f, 0.0f);  // Bottom Right
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-3.5f, -2.5f, 0.0f); // Bottom Left
        gl.glEnd();
        background.disable();
        
        //=== Phineas ==============================================
        
        gl.glTranslatef(-2.0f, 0.0f, -6.0f); // Move the "drawing cursor" around
        
        //Red hair 
        float numPoints = 7;
        float Radius = 0.57f;
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(222 / 255f, 24 / 255f, 21 / 255f);
        for (int i = 0; i < numPoints; i++) {
            double Angle = i * (2.0 * Math.PI / numPoints);
            double X = Math.cos(Angle * 4) * Radius;
            double Y = Math.sin(Angle * 4) * Radius;
            gl.glVertex2d(X - 2f, Y + 1.4f);
        }
        gl.glEnd();
        
        // Left eye 
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-0.5f, 0.85f, 0.58f,1.5f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-0.5f, 0.85f, 0.55f,1.5f, gl);
        gl.glColor3f(7/255f, 70/255f, 124/255f);
        drawCircle(-0.3f, 1f, 0.15f ,1.0f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-0.25f, 1f, 0.05f,1.0f, gl);
        
        //Face
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(252 / 255f, 218 / 255f, 188 / 255f);
        gl.glVertex3f(-2.0f, 1.5f, 0.0f);  // Top
        gl.glVertex3f(1.0f, 0.5f, 0.0f);   // Nose
        //gl.glVertex3f(-1.6f, -0.9f, 0.0f); // Neck
        gl.glVertex3f(-1.6f, -1.0f, 0.0f); // Bottom Right
        gl.glVertex3f(-1.9f, -1.0f, 0.0f); // Bottom Left
        gl.glEnd();
        drawCircle(-2.0f, 0.2f, 0.2f,1.0f, gl);
        
        //Right eye
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-1.0f, 0.85f, 0.58f,1.5f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-1.0f, 0.85f, 0.55f,1.5f, gl);
        gl.glColor3f(7/255f, 70/255f, 124/255f);
        drawCircle(-0.9f, 0.85f, 0.15f,1.0f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-0.8f, 0.85f, 0.05f,1.0f, gl);
        
        //Freckle
        gl.glColor3f(234 / 255f, 157 / 255f, 127 / 255f);
        drawCircle(-1.9f, 1.15f, 0.05f,1.0f, gl);
        drawCircle(-1.86f, 1.35f, 0.05f,1.0f, gl);
        drawCircle(-1.7f, 1.25f, 0.05f,1.0f, gl);
        
        //Mouth
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(234 / 255f, 199 / 255f, 175 / 255f);
        gl.glVertex3f(0.05f, 0f, 0.0f);
        gl.glVertex3f(-0.7f, 0f, 0.0f);
        gl.glVertex3f(-0.7f, -0.4f, 0.0f);
        gl.glEnd();
        
        //chlothing
        gl.glColor3f(246/255f, 151/255f, 48/255f);
        gl.glRectf(-2.0f,-1.25f, -1.5f, -1f);
        gl.glColor3f(254/255f, 237/255f, 189/255f);
        gl.glRectf(-2.0f,-1.25f, -1.5f, -1.5f);
        
        
        //=== Ferb ================================================
        
        gl.glTranslatef(3.0f, 0.0f, 0.0f);  // Move the "drawing cursor" around
        
        //Face 
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3f(252 / 255f, 218 / 255f, 188 / 255f);
        gl.glVertex3f(-0.7f, 2.5f, 0.0f);   // Top Left
        gl.glVertex3f(0.7f, 2.5f, 0.0f);   // Top Right
        gl.glVertex3f(0.65f, -1.0f, 0.0f);  // Bottom Right
        gl.glVertex3f(-0.65f, -1.0f, 0.0f); // Bottom Left
        gl.glEnd();
        drawCircle(0.7f, 0.7f, 0.2f,1.0f, gl);
        
        //Green hair
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(42 / 255f, 175/ 255f, 93/ 255f);
        gl.glVertex2d(0f, 2.5f);            //1
        gl.glVertex2d(-1.3f, 2.3f);         //2
        gl.glVertex2d(-0.95f, 2.7f);        //3
        gl.glVertex2d(-0.2f, 3.0f);         //4
        gl.glVertex2d(0.5f, 2.85f);         //5
        gl.glVertex2d(0.2f, 2.8f);          //6
        gl.glVertex2d(0.25f, 3.2f);         //6.1
        gl.glVertex2d(0.6f, 2.4f);          //6.2
        gl.glVertex2d(0.7f, 3f);            //7
        gl.glVertex2d(1f, 2f);              //9
        gl.glEnd();
        
        //Eye
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-0.6f, 1.59f, 0.53f,1.0f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-0.6f, 1.59f, 0.5f,1.0f, gl);
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-0.5f, 1.5f, 0.08f,1.0f, gl);
        
        //Nose
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(252 / 255f, 218 / 255f, 188 / 255f);
        gl.glVertex3f(-1.3f, 1.3f, 0.0f);   // Top Left
        gl.glVertex3f(0.5f, 1.3f, 0.0f);   // Top Right
        gl.glVertex3f(0.5f, 0.3f, 0.0f);  // Bottom Right
        gl.glVertex3f(-1.3f, 0.3f, 0.0f); // Bottom Left
        gl.glEnd();
        
        //Mouth
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glVertex3f(-0.5f, 0f, 0.0f);
        gl.glVertex3f(-0.9f, -0.5f, 0.0f);
        gl.glVertex3f(-0.5f, -0.5f, 0.0f); 
        gl.glEnd();
        
        //Eye
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(0.2f, 1.49f, 0.43f,1.0f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(0.2f, 1.49f, 0.4f,1.0f, gl);
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(0.1f, 1.5f, 0.08f,1.0f, gl);
        
        //clothing
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(1f, 1f, 1f);
        gl.glVertex3f(-0.85f, -1.0f, 0.0f);  // Top Left
        gl.glVertex3f(0.85f, -1.0f, 0.0f);  // Top Right
        gl.glVertex3f(0.43f, -1.5f, 0.0f);  // Bottom Right
        gl.glVertex3f(0f, -1.25f, 0.0f);  // middle
        gl.glVertex3f(-0.43f, -1.5f, 0.0f);  // Bottom Left
        gl.glEnd();
        
        //=== perry the platypus ===================================
        
        gl.glTranslatef(3.0f, 0.0f, 0.0f);  // Move the "drawing cursor" around
        
        //Body 
        gl.glColor3f(45/255f, 164/255f, 158/255f);
        gl.glRectf(-0.8f,-1.25f, 0.5f, 0.5f);
        
        //Hat
        gl.glColor3f(89/255f, 42/255f, 52/255f);
        gl.glRectf(-1.2f,0.5f, 1f, 0.8f);    
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(89/255f, 42/255f, 52/255f);
        gl.glVertex3f(0.5f, 1f, 0.0f);  // Bottom Right
        gl.glVertex3f(0.5f, 1.4f, 0.0f);  // Top Right
        gl.glVertex3f(0.2f, 1.2f, 0.0f);  // Top middle
        gl.glVertex3f(-0.8f, 1.6f, 0.0f);  // Top Left
        gl.glVertex3f(-0.8f, 1f, 0.0f);  // Bottom Left
        gl.glEnd();
        gl.glColor3f(0f, 0f, 0f);
        gl.glRectf(-0.8f, 0.8f, 0.5f, 1f);  
        
        //Mouth
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(252/255f, 228/255f, 52/255f);
        gl.glVertex3f(0f, 0f, 0.0f);  
        gl.glVertex3f(-0.4f, 0.1f, 0.0f); 
        gl.glVertex3f(-0.4f, 0f, 0.0f);
        gl.glVertex3f(-1f, 0f, 0.0f);  
        gl.glVertex3f(-1f, -0.2f, 0.0f);  
        gl.glVertex3f(-0.5f, -0.2f, 0.0f);
        gl.glColor3f(222/255f, 198/255f, 22/255f);
        gl.glVertex3f(-0.5f, -0.3f, 0.0f);
        gl.glVertex3f(0f, -0.3f, 0.0f);  
        gl.glVertex3f(0f, 0f, 0.0f);  
        gl.glEnd();
        
        //Eyes
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-0.6f, 0.3f, 0.33f,1.8f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(-0.6f, 0.3f, 0.3f,1.8f, gl);
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(-0.7f, 0.31f, 0.08f,1.0f, gl);
        
        //Eyes
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(0.1f, 0.3f, 0.33f,1.8f, gl);
        gl.glColor3f(1f, 1f, 1f);
        drawCircle(0.1f, 0.3f, 0.3f,1.8f, gl);
        gl.glColor3f(0f, 0f, 0f);
        drawCircle(0f, 0.31f, 0.08f,1.0f, gl);
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

     public void drawCircle(float x , float y ,float radius ,float oval , GL gl){
        int i;
        int n = 50;
        float Angle = (float) (2.0f * Math.PI)/n;
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2f(x, y); //cinter
        for (i =0 ; i<= n ; i++){
            gl.glVertex2f(
                    x+ (float)(radius * Math.cos(i * Angle)), 
                    y+ (float)(radius * Math.sin(i * Angle)/oval));
        }
        gl.glEnd();
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
