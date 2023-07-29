public class Vec3 {
    
    public double x;
    public double y;
    public double z;

    public Vec3(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString(){
        return "x: " + this.x + ", y: " + this.y + ", z: " + this.z;
    }
}