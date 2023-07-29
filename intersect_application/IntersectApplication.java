public class IntersectApplication {
    public static void main(String[] args) {

        Vec3 faceA = new Vec3(-1.00, 0.437, -0.012);
        Vec3 faceB = new Vec3(-0.848, 0.437, -0.012);
        Vec3 faceC = new Vec3(-1.00, 0.411, 0.08);

        Vec3 lineA = new Vec3(-0.930, 0.495, 0.032);
        Vec3 lineB = new Vec3(-0.973, 0.353, 0.0);

        Vec3 lineN = new Vec3(-0.908, 0.494, 0.062);
        Vec3 lineO = new Vec3(-0.951, 0.353, 0.031);

        Vec3 lineP = new Vec3(-0.954, 0.433, 0.025);
        Vec3 lineQ = new Vec3(-0.910, 0.575, 0.056);

        Vec3 parallelLineA = new Vec3(-1.00, 0.437, 0.5);
        Vec3 parallelLineB = new Vec3(-0.848, 0.437, 0.5);

        Vec3 planeLineA = new Vec3(-0.99, 0.42, 0.05);
        Vec3 planeLineB = new Vec3(-0.98, 0.43, 0.02);


        System.out.println("Test 1: " + (intersects(lineA, lineB, faceA, faceB, faceC) == true));
        System.out.println("Test 2: " + (intersects(lineA, lineB, faceC, faceB, faceA) == true));
        System.out.println("Test 3: " + (intersects(lineN, lineO, faceA, faceB, faceC) == false));
        System.out.println("Test 4: " + (intersects(lineN, lineO, faceC, faceB, faceA) == false));
        System.out.println("Test 5: " + (intersects(lineP, lineQ, faceA, faceB, faceC) == false));
        System.out.println("Test 6: " + (intersects(lineP, lineQ, faceC, faceB, faceA) == false));
        System.out.println("Test 7a (parallel): " + (intersects(parallelLineA, parallelLineB, faceA, faceB, faceC) == false));
        System.out.println("Test 7b (parallel): " + (intersects(parallelLineA, parallelLineB, faceB, faceC, faceA) == false));
        System.out.println("Test 7c (parallel): " + (intersects(parallelLineA, parallelLineB, faceC, faceB, faceA) == false));
        System.out.println("Test 8a (coplanar): " + (intersects(planeLineA, planeLineB, faceA, faceB, faceC) == false));
        System.out.println("Test 8b (coplanar): " + (intersects(planeLineA, planeLineB, faceB, faceC, faceA) == false));
        System.out.println("Test 8c (coplanar): " + (intersects(planeLineA, planeLineB, faceC, faceB, faceA) == false));


    }

    public static boolean intersects(Vec3 p0, Vec3 p1, Vec3 f1, Vec3 f2, Vec3 f3) {
        // Normal of line
        Vec3 lineNormal = new Vec3(p0.x - p1.x, p0.y - p1.y, p0.z - p1.z);
        // Normal of Face
        Vec3 faceNormal = normal(f1, f2, f3);
        // Normal of a point of Line and point of Face
        Vec3 ap = new Vec3(f1.x - p0.x, f1.y - p0.y, f1.z - p0.z);

        double t = dotProduct(ap, faceNormal) / dotProduct(lineNormal, faceNormal);

        Vec3 intersectionPoint = new Vec3(p0.x + t * lineNormal.x, p0.y + t * lineNormal.y, p0.z + t * lineNormal.z);

        if(dotProduct(lineNormal,faceNormal)==0.0){
            // Test if Line is perpendicular to Face's Normal
            return false;
        } else{
            return isPointInPolygon(intersectionPoint, f1, f2, f3) && isPointOnLine(p0,p1,intersectionPoint);
        }
    }

    public static Vec3 normal(Vec3 vectorA, Vec3 vectorB, Vec3 vectorC){
        Vec3 v1 = new Vec3(vectorB.x - vectorA.x, vectorB.y - vectorA.y, vectorB.z - vectorA.z);
        Vec3 v2 = new Vec3(vectorC.x - vectorA.x, vectorC.y - vectorA.y, vectorC.z - vectorA.z);

        double x = v1.y * v2.z - v1.z * v2.y;
        double y = v1.z * v2.x - v1.x * v2.z;
        double z = v1.x * v2.y - v1.y * v2.x;

        return new Vec3(x,y,z);
    }

    public static double dotProduct(Vec3 vector1, Vec3 vector2){
        double result = (vector1.x * vector2.x) + (vector1.y * vector2.y) + (vector1.z * vector2.z);

        return result;
    }

    public static boolean isPointInPolygon(Vec3 intersectPoint, Vec3 faceA, Vec3 faceB, Vec3 faceC){
        Vec3 v1 = new Vec3(faceB.x - faceA.x, faceB.y - faceA.y, faceB.z - faceA.z);
        Vec3 v2 = new Vec3(faceC.x - faceA.x, faceC.y - faceA.y, faceC.z - faceA.z);
        Vec3 v3 = new Vec3(intersectPoint.x - faceA.x, intersectPoint.y - faceA.y, intersectPoint.z - faceA.z);

        double dot00 = dotProduct(v1, v1);
        double dot01 = dotProduct(v1, v2);
        double dot02 = dotProduct(v1, v3);
        double dot11 = dotProduct(v2, v2);
        double dot12 = dotProduct(v2, v3);

        double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

        return (u >= 0) && (v >= 0) && (u + v < 1);
    }

    public static boolean isPointOnLine(Vec3 lineA, Vec3 lineB, Vec3 testPoint){
        boolean isTestPointWithin;

        if(lineA.x != lineB.x){
            isTestPointWithin = isWithin(lineA.x, testPoint.x, lineB.x);
        } else {
            isTestPointWithin = isWithin(lineA.y, testPoint.y, lineB.y);
        }

        return isTestPointWithin;
    }

    public static boolean isWithin(double min, double test, double max){
        return (min <= test && test <= max) || (max <= test && test <= min);
    }
}