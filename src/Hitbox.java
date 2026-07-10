import java.awt.*;

public class Hitbox {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean displayed = true;
    public Rectangle hitbox;
    public Hitbox(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle(this.x, this.y, this.width, this.height);
    }
    public void changehitpos(int x, int y){
        this.x = x;
        this.y = y;
        this.hitbox.setLocation(x, y);
    }
    public void changehitsize(int width, int height){
        this.width = width;
        this.height = height;
        this.hitbox.setSize(width, height);
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
    public boolean getvisibility(){
        return displayed;
    }
    public void setvisibilitytrue(){
        displayed = true;
    }
    public void setvisibilityfalse(){
        displayed = false;
    }
    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
}
