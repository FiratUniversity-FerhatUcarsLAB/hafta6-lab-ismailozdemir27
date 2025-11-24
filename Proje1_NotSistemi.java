Ismail OZDEMIR
250541109
24.11.2025
1.ODEV

  import java.util.Scanner;
  public class notSistemi{
     public static void main(String[] args) {
        //Ödev 1
        Scanner in = new Scanner(System.in);
        int vizeNot,finalNot,odevNot;
        System.out.print("Vize notunu Girin:");
        vizeNot=in.nextInt();
        System.out.print("Final notunu Girin:");
        finalNot=in.nextInt();
        System.out.print("Ödev notunu Girin:");
        odevNot=in.nextInt();
        System.out.println("=== Ogrenci Not Raporu ===");
        System.out.println("Vize Notu : " + vizeNot);
        System.out.println("Final Notu : " + finalNot);
        System.out.println("Ödev Notu : " + odevNot);
        double ort = calculateAverage(vizeNot, finalNot, odevNot);
        System.out.println("---------------------------");
        System.out.println("Ortalama : " + ort);
        System.out.println("Harf Notu : "+ getLetterGrade(ort));
        System.out.println("Durum : "+ ((isPassingGrade(ort) ? "GEÇTİ" : "KALDI")));
        System.out.println("Onur Listesi : "+((isHonorList(ort,vizeNot,finalNot,odevNot) ? "EVET" : "HAYIR")));
        System.out.println("Bütünleme : "+ ((hasRetakeRight(ort) ? "VAR" : "YOK")));

    }
    public static double calculateAverage(int vizeNot,int finalNot,int odevNot){
        return vizeNot * 0.3 + finalNot * 0.4 + odevNot * 0.3;
    }
    public static boolean isPassingGrade(double ort){
        if (ort >= 50){
            return true;
        }else {
            return false;
        }
    }
    public static String getLetterGrade(double ort){
        String harfNot="";
        if (ort >= 90 && ort <= 100){
            harfNot= "A";
        } else if (ort >=80 && ort <= 90) {
            harfNot= "B";
        }
        else if (ort >=70 && ort <= 80) {
            harfNot= "C";
        }
        else if (ort >=60 && ort <= 70) {
            harfNot= "D";
        }
        else  {
            harfNot= "F";
        }
        return harfNot;
    }
    public static boolean isHonorList(double ort,int vizeNot,int finalNot,int odevNot){
        if (ort >=85 && vizeNot >=70 && finalNot >= 70 && odevNot >= 70) {
            return true;
        }else {
            return false;
        }
    }
    public static boolean hasRetakeRight(double ort){

            return (ort >=40 && ort < 50) ? true : false;

    }
  }



(=========CIKTISI===========)
Vize notunu Girin: 80
Final notunu Girin: 75
Ödev notunu Girin: 100
=== Ogrenci Not Raporu ===
Vize Notu : 80
Final Notu : 75
Ödev Notu : 100
---------------------------
Ortalama : 84.0
Harf Notu : B
Durum : GEÇTİ
Onur Listesi : HAYIR
Bütünleme : YOK


  
