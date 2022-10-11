package assignment_4;

import java.util.Scanner;
import java.util.regex.*;

public class Practice_7_3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String all_coordinate = input.nextLine();
        input.close();
        String coordinate = "[+\\-]?(0(\\.[\\d]+)?|[1-9][0-9]*(\\.[\\d]+)?)";
        String parse = coordinate + "," + coordinate;
        String concat_parse = "\\s" + parse;
        boolean precise_match = all_coordinate.matches("^" + parse + concat_parse + "$");
        boolean rough_match = all_coordinate.matches("^" + parse + "(" + concat_parse + ")+" + "$");
        if(precise_match) {
            point[] points = new point[2];
            Pattern p = Pattern.compile(parse);
            Matcher m = p.matcher(all_coordinate);
            for (int i = 0; m.find(); i++){
                String point_parse = m.group();
                double getX, getY;
                getX = getCoordinate(point_parse, 0);
                getY = getCoordinate(point_parse, 1);
                points[i] = new point(getX, getY);
            }
            double length = distance(points[0], points[1]);
            System.out.print(length);
        }
        else if (rough_match){
            System.out.print("wrong number of points");
        }
        else System.out.print("Wrong Format");
    }

    public static double getCoordinate(String parse, int mode){
        //输入的parse格式形如3.3,2.2或者+3,-2。整数mode为模式，当mode为1，处理parse前半部分得到x坐标double值，当mode为0，处理parse后半部分得到y坐标double值
        int comma = parse.indexOf(',');
        String str;
        if (mode == 1) str = parse.substring(0,comma);
        else str = parse.substring(comma+1);
        return trimSign(str);
    }
    public static double trimSign(String signed_value){
        String value = signed_value.substring(1);
        if (Character.isDigit(signed_value.charAt(0))){
            return Double.parseDouble(signed_value);
        }
        else if (signed_value.charAt(0) == '+'){
            return Double.parseDouble(value);
        }
        else {
            return -Double.parseDouble(value);
        }
    }

    public static double distance(point p1, point p2){
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    static class point{
        double x, y;
        point(){
            x = 0;
            y = 0;
        }
        point(double xin, double yin){
            x = xin;
            y = yin;
        }
    }
}