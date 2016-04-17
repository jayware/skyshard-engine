/**
 * Skyshard Engine -- A 3D game engine written in Java
 *
 * Copyright (C) 2016 Markus Neubauer <markus.neubauer@jayware.org>,
 *                    Alexander Haumann <alexander.haumann@jayware.org>,
 *                    Manuel Hinke <manuel.hinke@jayware.org>,
 *                    Marina Schilling <marina.schilling@jayware.org>,
 *                    Elmar Schug <elmar.schug@jayware.org>,
 *
 *     This file is part of the Skyshard Engine.
 *
 *     The Skyshard Engine is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public License
 *     as published by the Free Software Foundation, either version 3 of
 *     the License, or any later version.
 *
 *     The Skyshard Engine is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jayware.skyshard.graphics.impl;

import org.jayware.skyshard.core.api.Configure;
import org.jayware.skyshard.core.api.Parameter;
import org.jayware.skyshard.core.api.Task;
import org.jayware.skyshard.core.api.TaskContext;
import org.jayware.skyshard.core.api.TaskManager;
import org.jayware.skyshard.graphics.api.GraphicsContextService;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.libffi.Closure;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Executor;

import static java.lang.Math.tan;
import static java.lang.Math.toRadians;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memEncodeUTF8;
import static org.osgi.service.component.annotations.ServiceScope.SINGLETON;


@Component(service = GraphicsContextService.class, scope = SINGLETON)
public class GraphicsContextServiceImpl
implements GraphicsContextService
{
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWWindowSizeCallback wsCallback;
    private Closure debugProc;

    private long window;
    private int width, height;

    private int program;
    private int projectionMatrixUniform;
    private int viewMatrixUniform;
    private int modelMatrixUniform;

    private Executor myMainExecutor;

    private TaskManager myTaskManager;

    public GraphicsContextServiceImpl()
    {
        System.out.println();
    }

    @Configure({@Parameter(name = "executor.id", value = "main")})
    public static class InitializeGLFWTask
    implements Task
    {
        @Override
        public void execute(TaskContext context)
        {

        }
    }

    @Activate
    void activate(BundleContext context)
    {
//        final TaskConfiguration initConfig = myTaskManager.configure(new InitializeGLFWTask()).with("executor.id", "main").build();
        myTaskManager.submit(new InitializeGLFWTask());

        myMainExecutor.execute(() -> {
            try
            {
                init();
                loop();

                // Release window and window callbacks
                glfwDestroyWindow(window);
                keyCallback.release();
                wsCallback.release();
                if (debugProc != null)
                {
                    debugProc.release();
                }

                context.getBundle(0).stop();
            }
            catch (BundleException e)
            {
            }
            finally
            {
                // Terminate GLFW and release the GLFWerrorfun
                glfwTerminate();
                errorCallback.release();
            }
        });
    }

    @Deactivate
    protected void deactivate() {

    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable

        int WIDTH = width = 1024;
        int HEIGHT = height = 768;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    void createProgram()
    {
        try
        {
            int program = glCreateProgram();
            int vshader = createShader("/org/jayware/skyshard/graphics/impl/shaders/simple.vs.c", GL_VERTEX_SHADER, null);
            int fshader = createShader("/org/jayware/skyshard/graphics/impl/shaders/simple.fs.c", GL_FRAGMENT_SHADER, null);
            glAttachShader(program, vshader);
            glAttachShader(program, fshader);
            glBindAttribLocation(program, 0, "in_Position");
            glBindAttribLocation(program, 1, "in_Color");
            glLinkProgram(program);
            int linked = glGetProgrami(program, GL_LINK_STATUS);
            String programLog = glGetProgramInfoLog(program);
            if (programLog.trim().length() > 0)
            {
                System.err.println(programLog);
            }
            if (linked == 0)
            {
                throw new AssertionError("Could not link program");
            }
            this.program = program;
            glUseProgram(program);
            projectionMatrixUniform = glGetUniformLocation(program, "projection");
            viewMatrixUniform = glGetUniformLocation(program, "view");
            modelMatrixUniform = glGetUniformLocation(program, "model");
            glUseProgram(0);
        }
        catch (IOException e)
        {

        }
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        debugProc = GLUtil.setupDebugMessageCallback();

        createProgram();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        int cbo = glGenBuffers();
        int ibo = glGenBuffers();

        float[] vertices = {-1, -1, 2, 1, -1, 2, 1, 1, 2};
        float[] colors = {1, 0, 0, 0, 1, 0, 0, 0, 1};
        int[] indices = {0, 1, 2};

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip(), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, cbo);
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) BufferUtils.createFloatBuffer(colors.length).put(colors).flip(), GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) BufferUtils.createIntBuffer(indices.length).put(indices).flip(), GL_STATIC_DRAW);

        float[] projectionMatrix = createProjectionMatrix4f((float) toRadians(60f), 4/3, 0.5f, 100f);

        float[] viewMatrix = {
            1.0f, 0.0f, 0.0f, -1.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
        };

        float[] modelMatrix = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
        };

        glUseProgram(program);
        glUniformMatrix4fv(projectionMatrixUniform, false, (FloatBuffer) BufferUtils.createFloatBuffer(projectionMatrix.length).put(projectionMatrix).flip());
        glUniformMatrix4fv(viewMatrixUniform, false, (FloatBuffer) BufferUtils.createFloatBuffer(viewMatrix.length).put(viewMatrix).flip());
        glUniformMatrix4fv(modelMatrixUniform, false, (FloatBuffer) BufferUtils.createFloatBuffer(modelMatrix.length).put(modelMatrix).flip());
        glUseProgram(0);

        while ( glfwWindowShouldClose(window) == GL_FALSE ) {

            glUseProgram(program);
            glBindVertexArray(vao);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0L);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

            glfwSwapBuffers(window); // swap the color buffers

            glBindVertexArray(0);
            glUseProgram(0);

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private int createShader(String resource, int type, String version) throws IOException {
        int shader = glCreateShader(type);

        ByteBuffer source = ioResourceToByteBuffer(resource, 8192);

        if ( version == null ) {
            PointerBuffer strings = BufferUtils.createPointerBuffer(1);
            IntBuffer lengths = BufferUtils.createIntBuffer(1);

            strings.put(0, source);
            lengths.put(0, source.remaining());

            glShaderSource(shader, strings, lengths);
        } else {
            PointerBuffer strings = BufferUtils.createPointerBuffer(2);
            IntBuffer lengths = BufferUtils.createIntBuffer(2);

            ByteBuffer preamble = memEncodeUTF8("#version " + version + "\n", false);

            strings.put(0, preamble);
            lengths.put(0, preamble.remaining());

            strings.put(1, source);
            lengths.put(1, source.remaining());

            glShaderSource(shader, strings, lengths);
        }

        glCompileShader(shader);
        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        String shaderLog = glGetShaderInfoLog(shader);
        if (shaderLog.trim().length() > 0) {
            System.err.println(shaderLog);
        }
        if (compiled == 0) {
            throw new AssertionError("Could not compile shader");
        }
        return shader;
    }

    private ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        URL url = getClass().getResource(resource);
        File file = new File(url.getFile());
        if ( file.isFile() ) {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);

            while ( fc.read(buffer) != -1 ) {
                // just let the loop condition do its thing
            }

            fc.close();
            fis.close();
        } else {
            buffer = BufferUtils.createByteBuffer(bufferSize);
            InputStream source = url.openStream();
            if ( source == null )
                throw new FileNotFoundException(resource);

            try {
                ReadableByteChannel rbc = Channels.newChannel(source);
                try {
                    while ( true ) {
                        int bytes = rbc.read(buffer);
                        if ( bytes == -1 )
                            break;
                        if ( buffer.remaining() == 0 )
                            buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                } finally {
                    rbc.close();
                }
            } finally {
                source.close();
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    //
    //  0   1   2   3
    //  4   5   6   7
    //  8   9  10  11
    // 12  13  14  15
    //

    private float[] createProjectionMatrix4f(double fov, double aspect, double nearDist, double farDist)
    {
        //
        // General form of the Projection Matrix
        //
        // uh = Cot( fov/2 ) == 1/Tan(fov/2)
        // uw / uh = 1/aspect
        //
        //   uw         0       0       0
        //    0        uh       0       0
        //    0         0      f/(f-n)  1
        //    0         0    -fn/(f-n)  0
        //
        // Make result to be identity first

        if (fov <= 0)
        {
            throw new IllegalArgumentException("fov <= 0");
        }

        if (aspect == 0)
        {
            throw new IllegalArgumentException("aspect == 0");
        }

        final double frustumDepth = farDist - nearDist;
        final double oneOverDepth = 1 / frustumDepth;

        final float[] result = new float[16];

        result[5]  = (float) (1.0 / tan(0.5f * fov));
        result[0]  = (float) (-1.0 * result[5] / aspect);
        result[10] = (float) (farDist * oneOverDepth);
        result[14] = (float) (-farDist * nearDist * oneOverDepth);
        result[11] = 1;

        return result;
    }

    @Reference(target = "(executor.name=main)")
    public void bindMainExecutor(Executor executor)
    {
        myMainExecutor = executor;
    }

    public void unbindMainExecutor(Executor executor)
    {
        myMainExecutor = null;
    }

    @Reference
    public void bindTaskManager(TaskManager taskManager)
    {
        myTaskManager = taskManager;
    }

    public void unbindTaskManager(TaskManager taskManager)
    {
        myTaskManager = null;
    }
}
