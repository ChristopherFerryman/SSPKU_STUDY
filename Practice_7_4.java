package assignment_4;

import java.util.Scanner;

public class Practice_7_4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Practice_7_4 eq = new Practice_7_4();
        String all_coordinate = input.nextLine();//获取输入的字符串，合规输入的单个点坐标格式为xx.xx,xx.xx
        input.close();

        String coordinate = "[+\\-]?(0(\\.[\\d]+)?|[1-9][0-9]*(\\.[\\d]+)?)";//表示任一坐标的字段正则表达式，即xx.xx
        String parse = coordinate + "," + coordinate;//表达"[+\\-]?(0(\\.[\\d]+)?|[1-9][0-9]*(\\.[\\d]+)?),[+\\-]?(0(\\.[\\d]+)?|[1-9][0-9]*(\\.[\\d]+)?)"
        String concat_parse = "\\s" + parse;
        boolean rough_match = all_coordinate.matches("^[12345]:" + parse + "(" + concat_parse + ")*" + "$");

        if(!rough_match) {
            System.out.print("Wrong Format");
            return;
        }

        String pointInfo = all_coordinate.substring(2);
        String[] parses = pointInfo.split(" ");
        int parselen = parses.length;
        char item = all_coordinate.charAt(0);
        switch(item) {
            case '1':{
                if (parselen != 3) {
                    System.out.print("wrong number of points");
                    return;
                }

                point[] points = eq.fillPoints(3, parses);
                triangle tri = new triangle(points[0], points[1], points[2]);
                int feature = eq.getFeature(tri);

                if (feature == 0 || feature == -1) {
                    System.out.print("data error");
                    return;
                }

                boolean isIso, isEqui;
                if (eq.doubleEq(tri.la.len, tri.lb.len) && eq.doubleEq(tri.lb.len, tri.lc.len)) {
                    isEqui = isIso = true;
                } else if (eq.doubleEq(tri.la.len, tri.lb.len) || eq.doubleEq(tri.lb.len, tri.lc.len) || eq.doubleEq(tri.la.len, tri.lc.len)) {
                    isIso = true;
                    isEqui = false;
                } else isEqui = isIso = false;
                System.out.print(isIso + " " + isEqui);

                break;
            }

            case '2':{
                if (parselen != 3) {
                    System.out.print("wrong number of points");
                    return;
                }

                point[] points = eq.fillPoints(3, parses);
                triangle tri = new triangle(points[0], points[1], points[2]);
                int feature = eq.getFeature(tri);

                if (feature == 0 || feature == -1) {
                    System.out.print("data error");
                    return;
                }

                point pG = new point((tri.pa.x + tri.pb.x + tri.pc.x) / 3.0, (tri.pa.y + tri.pb.y + tri.pc.y) / 3.0);
                System.out.print(Math.round(tri.circumference * 1000000) / 1000000.0 + " " + Math.round(tri.getArea() * 1000000) / 1000000.0 + " " + Math.round(pG.x * 1000000) / 1000000.0 + "," + Math.round(pG.y * 1000000) / 1000000.0);

                break;
            }

            case '3':{
                if (parselen != 3) {
                    System.out.print("wrong number of points");
                    return;
                }

                point[] points = eq.fillPoints(3, parses);
                triangle tri = new triangle(points[0], points[1], points[2]);
                int feature = eq.getFeature(tri);

                if (feature == 0 || feature == -1) {
                    System.out.print("data error");
                    return;
                }

                int shape;//1为直角三角形，2为钝角三角形，3为锐角三角形，A、B、C为三个角。
                double a = tri.la.len;
                double b = tri.lb.len;
                double c = tri.lc.len;
                double cosA = (-a * a + b * b + c * c) / (2 * b * c);
                double cosB = (-b * b + a * a + c * c) / (2 * a * c);
                double cosC = (-c * c + b * b + a * a) / (2 * a * b);
                if (eq.doubleEq(cosA * cosB * cosC, 0)) {
                    shape = 1;
                } else if (cosA * cosB * cosC < 0) {
                    shape = 2;
                } else {
                    shape = 3;
                }
                System.out.print((shape == 2) + " " + (shape == 1) + " " + (shape == 3));
                break;
            }

            case '4':{
                if (parselen != 5) {
                    System.out.print("wrong number of points");
                    return;
                }

                point[] points = eq.fillPoints(5, parses);
                if (eq.doubleEq(points[0].x, points[1].x) && eq.doubleEq(points[0].y, points[1].y)) {
                    System.out.print("points coincide");
                    return;
                }

                triangle tri = new triangle(points[2], points[3], points[4]);
                int feature = eq.getFeature(tri);
                if (feature == 0 || feature == -1) {
                    System.out.print("data error");
                    return;
                }

                vector vab, vbc, vca, vad, vbd, vcd, ved, vae, vbe, vce;
                point pa, pb, pc;
                pa = tri.pa;
                pb = tri.pb;
                pc = tri.pc;
                vab = new vector(pa, pb);
                vbc = new vector(pb, pc);
                vca = new vector(pc, tri.pa);
                vad = new vector(pa, points[0]);
                vae = new vector(pa, points[1]);
                vbd = new vector(pb, points[0]);
                vbe = new vector(pb, points[1]);
                vcd = new vector(pc, points[0]);
                vce = new vector(pc, points[1]);
                ved = new vector(points[1], points[0]);

                if (eq.doubleEq(ved.cross(vab), 0) && eq.doubleEq(vad.cross(vae), 0)
                        || eq.doubleEq(ved.cross(vbc), 0) && eq.doubleEq(vbd.cross(vbe), 0)
                        || eq.doubleEq(ved.cross(vca), 0) && eq.doubleEq(vad.cross(vae), 0))
                {
                    System.out.print("The point is on the edge of the triangle");
                    return;
                }

                int intersection;
                line ld;
                ld = new line(points[0], points[1]);
                double[] setAB, setBC, setCA, setD;
                setAB = eq.getMKB(tri.lc);
                setBC = eq.getMKB(tri.la);
                setCA = eq.getMKB(tri.lb);
                setD = eq.getMKB(ld);

                if ((vad.cross(ved) * vbd.cross(ved) < 0) || (vad.cross(ved) * vcd.cross(ved) < 0) || (vcd.cross(ved) * vbd.cross(ved) < 0)) {
                    intersection = 2;
                    triangle subTri;
                    double remainArea;

                    if (vad.cross(ved) * vbd.cross(ved) < 0) {
                        point ab = eq.intersect(setD, setAB);
                        if (eq.doubleEq(vcd.cross(ved), 0)) {//直线交AB边，同时过C点
                            subTri = new triangle(pa, pc, ab);
                        } else if (vad.cross(ved) * vcd.cross(ved) < 0) {//直线交AB边，CA边
                            point ca = eq.intersect(setD, setCA);
                            subTri = new triangle(pa, ca, ab);
                        } else {//直线交AB边，BC边
                            point bc = eq.intersect(setD, setBC);
                            subTri = new triangle(pa, bc, ab);
                        }
                    } else if (vad.cross(ved) * vcd.cross(ved) < 0) {
                        point ca = eq.intersect(setD, setCA);
                        if (eq.doubleEq(vbd.cross(ved), 0)) {//直线交CA边，同时过B点
                            subTri = new triangle(pb, pc, ca);
                        } else {//直线交CA边，BC边
                            point bc = eq.intersect(setD, setBC);
                            subTri = new triangle(pa, ca, bc);
                        }
                    } else {
                        point bc = eq.intersect(setD, setBC);//直线交BC边，同时过A点
                        subTri = new triangle(pa, pb, bc);
                    }

                    double sub_tri_area = subTri.getArea();
                    remainArea = tri.getArea() - sub_tri_area;
                    if (remainArea < sub_tri_area)
                        System.out.print(intersection + " " + Math.round(remainArea * 1000000) / 1000000.0 + " " + Math.round(sub_tri_area * 1000000) / 1000000.0);
                    else System.out.print(intersection + " " + Math.round(sub_tri_area * 1000000) / 1000000.0 + " " + Math.round(remainArea * 1000000) / 1000000.0);
                    return;
                } else if (eq.doubleEq(ved.cross(vad), 0) || eq.doubleEq(ved.cross(vbd), 0) || eq.doubleEq(ved.cross(vcd), 0)) {
                    intersection = 1;
                } else {
                    intersection = 0;
                }
                System.out.print(intersection);

                break;
            }
            case '5':{
                if (parselen != 4) {
                    System.out.print("wrong number of points");
                    return;
                }

                point[] points = eq.fillPoints(4, parses);
                triangle tri = new triangle(points[1], points[2], points[3]);
                int feature = eq.getFeature(tri);
                if (feature == 0 || feature == -1) {
                    System.out.print("data error");
                    return;
                }

                double x1 = points[0].x;
                double y1 = points[0].y;
                double x2 = points[1].x;
                double y2 = points[1].y;
                double x3 = points[2].x;
                double y3 = points[2].y;
                double x4 = points[3].x;
                double y4 = points[3].y;

                double distance1 = ((y2 - y3) * x1 + (x3 - x2) * y1 + x2 * y3 - y2 * x3) / Math.sqrt((y2 - y3) * (y2 - y3) + (x2 - x3) * (x2 - x3));
                double distance2 = ((y2 - y4) * x1 + (x4 - x2) * y1 + x2 * y4 - y2 * x4) / Math.sqrt((y2 - y4) * (y2 - y4) + (x2 - x4) * (x2 - x4));
                double distance3 = ((y3 - y4) * x1 + (x4 - x3) * y1 + x3 * y4 - y3 * x4) / Math.sqrt((y3 - y4) * (y3 - y4) + (x3 - x4) * (x3 - x4));

                if (eq.doubleEq(distance1, 0) || eq.doubleEq(distance2, 0) || eq.doubleEq(distance3, 0)) {
                    System.out.print("on the triangle");
                    return;
                }

                triangle tri_NAB = new triangle(points[0], points[1], points[2]);
                triangle tri_NBC = new triangle(points[0], points[2], points[3]);
                triangle tri_NAC = new triangle(points[0], points[1], points[3]);
                double area = tri.getArea();
                double area1 = tri_NAB.getArea();
                double area2 = tri_NBC.getArea();
                double area3 = tri_NAC.getArea();
                if (Math.abs(area - area1 - area2 - area3) < 0.001) {
                    System.out.print("in the triangle");
                }
                else System.out.print("outof the triangle");
                break;
            }
        }
    }

    final boolean doubleEq(double x, double y) {
        return (Math.abs(x - y) < 1e-15);
    }

    final point[] fillPoints(int n, String[] target){
        point[] points = new point[n];
        double getX, getY;
        for (int i = 0; i < target.length; i++){
            String[] pLetter = target[i].split(",");
            getX = Double.parseDouble(pLetter[0]);
            getY = Double.parseDouble(pLetter[1]);
            points[i] = new point(getX, getY);
        }
        return points;
    }

    //point类表示了平面中的一个点
    static class point{
        double x, y;
        point(double xin, double yin){
            x = xin;
            y = yin;
        }
    }

    static class line{
        point p1, p2;
        double len;//以形如my = kx + b 的格式表述直线。
        line(point p1in, point p2in){
            p1 = p1in;
            p2 = p2in;
            len = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
        }
    }

    final double[] getMKB(line l){//以形如my = kx + b 的格式表述直线。
        double[] lineSet = new double[3];//m，k，b分别存储在linSet的0、1、2位置
        double m, k, b;
        point p1 = l.p1;
        point p2 = l.p2;
        Practice_7_4 eq = new Practice_7_4();
        if(eq.doubleEq(p1.x, p2.x)){
            //description = "mode1, vertical line: x = " + p1.x;
            m = 0;
            k = 1;
            b = -p1.x;
        }
        else if(eq.doubleEq(p1.y, p2.y)){
            //description = "mode2,horizontal line: y = " + p1.y;
            m = 1;
            k = 0;
            b = p1.y;
        }
        else{
            m = 1;
            k = (p2.y - p1.y)/(p2.x - p1.x);
            b = p1.y - k * p1.x;
            //description = "mode3,normal line: y = " + k + "x" + " + " + b;
        }
        lineSet[0] = m;
        lineSet[1] = k;
        lineSet[2] = b;
        return lineSet;
    }

    final point intersect (double[] set1, double[] set2) {//直线形式为my = kx + b, 其实就是 -kx + my = b，可以将两条直线作为二元一次方程组求解。
        double x = (set2[2] * set1[0] - set2[0] * set1[2]) / (-set2[1] * set1[0] - set2[0] * -set1[1]);
        double y = (-set2[1] * set1[2] - set2[2] * -set1[1]) / (-set2[1] * set1[0] - set2[0] * -set1[1]);
        return new point(x, y);
    }

    static class vector{
        double x, y;//向量的坐标值
        vector(point a, point b){
            x = b.x - a.x;
            y = b.y - a.y;
        }
        final double cross (vector v){
            return x * v.y - v.x * y;
        }
    }

    static class triangle {
        point pa, pb, pc;
        line la, lb, lc;
        double circumference;
        triangle(point pain, point pbin, point pcin){
            pa = pain;
            pb = pbin;
            pc = pcin;
            la = new line(pb, pc);
            lb = new line(pa, pc);
            lc = new line(pa, pb);
            circumference = la.len + lb.len + lc.len;
        }
        final double getArea(){//返回面积
            return Math.sqrt((circumference / 2.0) * (circumference / 2.0 - la.len) * (circumference / 2.0 - lb.len) * (circumference / 2.0 - lc.len));
        }
    }

    final int getFeature(triangle tri){//-1为有点重合，0为不构成三角形（三点共线），1为正常三角形
        double a = tri.la.len;
        double b = tri.lb.len;
        double c = tri.lc.len;
        Practice_7_4 eq = new Practice_7_4();
        if(eq.doubleEq(a, 0) || eq.doubleEq(b, 0) || eq.doubleEq(c, 0) ){
            return -1;
        }
        else if(eq.doubleEq(a + b, c) || eq.doubleEq(b + c, a) || eq.doubleEq(a + c, b) ){
            return 0;
        }
        else return 1;
    }
}