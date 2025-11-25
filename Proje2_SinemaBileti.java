Ismail OZDEMIR
250541109
25.11.2025
  
  
  
  import java.util.Scanner;

public class SinemaBiletFiyatlandirma {

    // Temel sabitler
    private static final double INDIRIM_65_YAS = 0.30;
    private static final double INDIRIM_12_YAS_ALTI = 0.25;

    /**
     * 1. isWeekend(gun): Hafta sonu mu kontrol et
     * Gun: 1=Pzt, 7=Paz. Hafta sonu (Cumartesi=6, Pazar=7)
     */
    public static boolean isWeekend(int gun) {
        return gun == 6 || gun == 7;
    }

    /**
     * 2. isMatinee(saat): Matine mi kontrol et
     * Matine: 12:00 (12) öncesi, yani saat < 12 ise True.
     */
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    /**
     * 3. calculateBasePrice(gun, saat): Temel fiyatı hesapla
     */
    public static double calculateBasePrice(int gun, int saat) {
        boolean isWeekend = isWeekend(gun);
        boolean isMatinee = isMatinee(saat);
        double basePrice;

        if (isWeekend) {
            // Hafta Sonu Fiyatları
            basePrice = isMatinee ? 55.0 : 85.0; // Matine: 55 TL, Normal: 85 TL
        } else {
            // Hafta İçi Fiyatları
            basePrice = isMatinee ? 45.0 : 65.0; // Matine: 45 TL, Normal: 65 TL
        }
        return basePrice;
    }

    /**
     * 4. calculateDiscount(yas, meslek, gun): İndirim oranını hesapla (örn: 0.20)
     * Meslek: 1=Öğrenci, 2=Öğretmen, 3=Diğer
     * Gündüz: 1=Pzt, ..., 7=Paz (Switch-Case kullanımı için gün ve meslek)
     */
    public static double calculateDiscount(int yas, int meslek, int gun) {
        // Öncelikli Yaş İndirimleri
        if (yas >= 65) {
            return INDIRIM_65_YAS; // %30
        }
        if (yas < 12) {
            return INDIRIM_12_YAS_ALTI; // %25
        }

        // Meslek İndirimleri (Switch-Case ile Meslek ve Gün Kontrolü)
        switch (meslek) {
            case 1: // Öğrenci
                // Öğrenci: %20 (Pazartesi-Perşembe), %15 (Cuma-Pazar)
                return switch (gun) {
                    case 1, 2, 3, 4 -> 0.20; // Pzt-Per: %20
                    case 5, 6, 7 -> 0.15; // Cuma-Paz: %15
                    default -> 0.0;
                };
            case 2: // Öğretmen
                // Öğretmen: %35 (sadece Çarşamba)
                if (gun == 3) {
                    return 0.35; // Çarşamba: %35
                }
                break;
            case 3: // Diğer
            default:
                break;
        }

        return 0.0; // İndirim yok
    }

    /**
     * 5. getFormatExtra(filmTuru): Film formatı ekstra ücreti
     * Film Türü: 1=2D, 2=3D, 3=IMAX, 4=4DX (Switch-Case kullanımı)
     */
    public static double getFormatExtra(int filmTuru) {
        // Switch-Case kullanımı
        return switch (filmTuru) {
            case 2 -> 25.0; // 3D film: +25 TL
            case 3 -> 35.0; // IMAX: +35 TL
            case 4 -> 50.0; // 4DX: +50 TL
            case 1 -> 0.0; // 2D: +0 TL
            default -> 0.0;
        };
    }

    /**
     * 6. calculateFinalPrice(...): Toplam fiyatı hesaplar ve gerekli bilgileri döndürür.
     */
    public static double[] calculateFinalPrice(int gun, int saat, int yas, int meslek, int filmTuru) {
        double basePrice = calculateBasePrice(gun, saat);
        double discountRate = calculateDiscount(yas, meslek, gun);
        double extraFee = getFormatExtra(filmTuru);

        double discountAmount = basePrice * discountRate;
        double discountedPrice = basePrice - discountAmount;
        double finalPrice = discountedPrice + extraFee;

        // [Temel Fiyat, İndirim Oranı, İndirim Miktarı, Ekstra Ücret, Toplam Fiyat]
        return new double[]{basePrice, discountRate, discountAmount, extraFee, finalPrice};
    }

    /**
     * 7. generateTicketInfo(...): Bilet bilgisini oluşturur ve ekrana yazdırır.
     */
    public static void generateTicketInfo(int gun, int saat, int yas, int meslek, int filmTuru) {
        double[] results = calculateFinalPrice(gun, saat, yas, meslek, filmTuru);
        double basePrice = results[0];
        double discountRate = results[1];
        double discountAmount = results[2];
        double extraFee = results[3];
        double finalPrice = results[4];

        String[] gunIsimleri = {"", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"};
        String[] meslekIsimleri = {"", "Öğrenci", "Öğretmen", "Diğer"};
        String[] filmTurleri = {"", "2D", "3D", "IMAX", "4DX"};

        System.out.println("\n--- 🎫 Bilet Bilgileri ---");
        System.out.printf("**Tarih/Saat:** %s, %02d:00\n", gunIsimleri[gun], saat);
        
        // Temel Fiyat Bilgisi
        String seansTuru = isMatinee(saat) ? "Matine" : "Normal";
        String gunTuru = isWeekend(gun) ? "Hafta Sonu" : "Hafta İçi";
        System.out.printf("**Temel Fiyat:** %.2f TL (%s - %s)\n", basePrice, gunTuru, seansTuru);

        // İndirim Bilgisi
        if (discountRate > 0) {
            String indirimTuru;
            if (yas >= 65) indirimTuru = "65+ Yaş";
            else if (yas < 12) indirimTuru = "12 Yaş Altı";
            else indirimTuru = meslekIsimleri[meslek];

            System.out.printf("**Uygulanan İndirim:** %s İndirimi\n", indirimTuru);
            System.out.printf("**İndirim Oranı/Miktarı:** %%.0f / -%.2f TL\n", discountRate * 100, discountAmount);
            System.out.printf("**İndirimli Fiyat:** %.2f TL\n", basePrice - discountAmount);
        } else {
            System.out.println("**Uygulanan İndirim:** Yok");
        }

        // Ekstra Ücret Bilgisi
        if (extraFee > 0) {
            System.out.printf("**Film Formatı Ekstra:** %s (+%.2f TL)\n", filmTurleri[filmTuru], extraFee);
        } else {
            System.out.println("**Film Formatı Ekstra:** Yok");
        }

        System.out.println("---------------------------");
        System.out.printf("**💰 TOPLAM ÖDENECEK TUTAR: %.2f TL**\n", finalPrice);
        System.out.println("---------------------------");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Sinema Bileti Hesaplama Sistemi ---\n");
        
        // Girdileri Al
        System.out.print("Gün seçimi (1=Pzt, ..., 7=Paz): ");
        int gun = scanner.nextInt();
        
        System.out.print("Saat (8-23): ");
        int saat = scanner.nextInt();
        
        System.out.print("Yaş: ");
        int yas = scanner.nextInt();
        
        System.out.print("Meslek (1=Öğrenci, 2=Öğretmen, 3=Diğer): ");
        int meslek = scanner.nextInt();
        
        System.out.print("Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX): ");
        int filmTuru = scanner.nextInt();
        
        scanner.close();

        // Sonucu Hesapla ve Yazdır
        generateTicketInfo(gun, saat, yas, meslek, filmTuru);

        // Örnek Senaryo Kontrolü için yorum satırını açabilirsiniz:
        /*
        System.out.println("\n--- Örnek Senaryo Kontrolü (Perşembe, 10:00, 22 yaş, Öğrenci, 3D) ---");
        generateTicketInfo(4, 10, 22, 1, 2); // Beklenen: 61.00 TL
        */
    }
}



============CIKTISI===============


  --- Sinema Bileti Hesaplama Sistemi ---

Gün seçimi (1=Pzt, ..., 7=Paz): 7
Saat (8-23):  20
Yaş:  20 
Meslek (1=Öğrenci, 2=Öğretmen, 3=Diğer):  1
Film Türü (1=2D, 2=3D, 3=IMAX, 4=4DX):  3

--- 🎫 Bilet Bilgileri ---
**Tarih/Saat:** Pazar, 20:00
**Temel Fiyat:** 85,00 TL (Hafta Sonu - Normal)
**Uygulanan İndirim:** Öğrenci İndirimi
**İndirim Oranı/Miktarı:** %.0f / -15,00 TL
**İndirimli Fiyat:** 72,25 TL
**Film Formatı Ekstra:** IMAX (+35,00 TL)
---------------------------
**💰 TOPLAM ÖDENECEK TUTAR: 107,25 TL**
---------------------------
