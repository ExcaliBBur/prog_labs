public class Main {
    public static void main(String[] args) {
        long [] c = new long[13];
        double[] x = new double[20];
        for (int i = 0; i < 13; i++) {
            c[i] = (15 - i);
        }
        for (int i = 0; i < 20; i ++){
            x[i] = Math.random() * 14.0 - 6.0;
        }
        double[][]s = new double[13][20];
        for (int i = 0; i < 13;i ++) {
            for (int j = 0; j < 20; j ++) {
                double temp;
                if (c[i] == 11) {
                    temp = 3 / (4 + Math.pow(Math.sin(x[j]),2));
                    temp = Math.log(temp);
                }
                else if (c[i] == 3 || c[i] == 5 || c[i] == 8 || c[i] == 10 || c[i] == 13 || c[i] == 15) {
                    double temp2;
                    temp = Math.pow(Math.E, Math.abs(x[j]) * -1);
                    temp = Math.PI - Math.asin(temp);
                    temp2 = Math.pow(x[j]/2,2);
                    temp2 = Math.cbrt(temp2);
                    temp = temp / temp2;
                    temp2 = Math.pow(Math.E,Math.sin(x[j]));
                    temp = Math.pow(temp,temp2);
                }
                else {
                    temp = 2 / Math.asin((x[j] + 1) / 14);
                    temp = Math.cos(temp);
                    temp = Math.asin(temp);
                    temp = Math.cos(temp);
                }
                s[i][j] = temp;
            }
        }
        for (int i = 0; i < 13; i ++) {
            for (int j = 0; j < 20; j++) {
                System.out.printf("%6.2f",s[i][j]);
            }
            System.out.println();
        }
    }
}