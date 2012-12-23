package de.bitowl.libdgx3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;

public class TestGame implements ApplicationListener {

	Mesh mesh;
	Camera camera;
	Texture texture;
	
	@Override
	public void create() {	
		/*mesh=new Mesh(true,3,3,new VertexAttribute(Usage.Position,3,"a_position"));
		mesh.setVertices(new float[]{-1.0f, -0.5f, -1f,
                0f, -0.5f, -1f,
                -0.5f, 0.5f, -1f });   
		mesh.setIndices(new short[] { 0, 1, 2 });   
	
		mesh2=new Mesh(true,3,3,new VertexAttribute(Usage.Position,3,"a_position"));
		mesh2.setVertices(new float[]{-0.0f, -0.5f, -1.5f,
                1f, -0.5f, -1.5f,
                0.5f, 0.5f, -1.5f });  
		mesh2.setIndices(new short[] { 0, 1, 2 });  */
		
		// this loads the data from the obj (vertices and uv-data)
		mesh=ObjLoader.loadObj(Gdx.files.internal("data/test.obj").read() ,true);
	 
		// we need to load and bind the texture ourselves
		texture=new Texture(Gdx.files.internal("data/test.png"));
		texture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		// enable textures
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		// enable depth test
        Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	@Override
	public void dispose() {
		mesh.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
	
		// move camera by keys
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
        	camera.translate(0, 0, Gdx.graphics.getDeltaTime());
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){
        	camera.translate(0, 0, -Gdx.graphics.getDeltaTime());
        }
        

        camera.update();
		camera.apply(Gdx.gl10);
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f,1f);
        // clear color buffer and !depth buffer
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
      
		// bind the texture and render the mesh
		texture.bind();
		mesh.render(GL10.GL_TRIANGLES);
        
	}

	@Override
	public void resize(int width, int height) {

        float aspectRatio = (float) width / (float) height;
        camera = new PerspectiveCamera(67, aspectRatio, 1f);
        // move camera so that we can see the 3D-cube
        camera.translate(-1.5f,1.5f,4);


	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
