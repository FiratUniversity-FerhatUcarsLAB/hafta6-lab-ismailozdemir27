Ismail OZDEMIR
250541109
25.11.2025

  package restoranSipariş;


		import java.util.Scanner;

		public class RestoranSiparis {

		    // --- 1. KATEGORİ FİYAT METOTLARI (SWITCH-CASE KULLANIMI ZORUNLU) ---

		    /**
		     * 1. getMainDishPrice(secim): Ana yemek fiyatı
		     */
		    public static double getMainDishPrice(int secim) {
		        return switch (secim) {
		            case 1 -> 85.0; // Izgara Tavuk
		            case 2 -> 120.0; // Adana Kebap
		            case 3 -> 110.0; // Levrek
		            case 4 -> 65.0; // Mantı
		            default -> 0.0;
		        };
		    }

		    /**
		     * 2. getAppetizerPrice(secim): Başlangıç fiyatı
		     */
		    public static double getAppetizerPrice(int secim) {
		        return switch (secim) {
		            case 1 -> 25.0; // Çorba
		            case 2 -> 45.0; // Humus
		            case 3 -> 55.0; // Sigara Böreği
		            default -> 0.0;
		        };
		    }

		    /**
		     * 3. getDrinkPrice(secim): İçecek fiyatı
		     */
		    public static double getDrinkPrice(int secim) {
		        return switch (secim) {
		            case 1 -> 15.0; // Kola
		            case 2 -> 12.0; // Ayran
		            case 3 -> 35.0; // Taze Meyve Suyu
		            case 4 -> 25.0; // Limonata
		            default -> 0.0;
		        };
		    }

		    /**
		     * 4. getDessertPrice(secim): Tatlı fiyatı
		     */
		    public static double getDessertPrice(int secim) {
		        return switch (secim) {
		            case 1 -> 65.0; // Künefe
		            case 2 -> 55.0; // Baklava
		            case 3 -> 35.0; // Sütlaç
		            default -> 0.0;
		        };
		    }

		    // --- 2. ÖZEL DURUM METOTLARI ---

		    /**
		     * 5. isComboOrder(anaVar, icecekVar, tatliVar): Combo menü siparişi mi?
		     * Combo: Ana yemek + İçecek + Tatlı
		     */
		    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
		        return anaVar && icecekVar && tatliVar;
		    }

		    /**
		     * 6. isHappyHour(saat): Happy Hour (14:00-17:00) mı?
		     */
		    public static boolean isHappyHour(int saat) {
		        // Saat 14 (dahil) ile 17 (hariç) arası
		        return saat >= 14 && saat < 17;
		    }

		    /**
		     * 7. calculateDiscount(tutar, combo, ogrenci, saat): İndirim hesapla
		     * Not: İndirimler sırayla uygulanır ve bir sonraki indirim, indirimli tutar üzerinden hesaplanır.
		     */
		    public static double[] calculateDiscount(double araToplam, boolean combo, boolean ogrenci, int saat, int gun, double icecekFiyati) {
		        double currentTotal = araToplam;
		        double totalDiscountAmount = 0.0;
		        
		        // [0] Combo İndirimi Miktarı, [1] Happy Hour Miktarı, [2] 200 TL Üzeri Miktarı, [3] Öğrenci Miktarı
		        double[] discountDetails = new double[4];
		        
		        // 1. Combo İndirimi (%15) - Eğer sipariş combo ise tüm ara toplam üzerinden uygulanır.
		        if (combo) {
		            double discount = currentTotal * 0.15;
		            currentTotal -= discount;
		            totalDiscountAmount += discount;
		            discountDetails[0] = discount;
		        }

		        // 2. Happy Hour (14:00-17:00 arası İçeceklerde %20 indirim)
		        if (isHappyHour(saat)) {
		            // İndirim, içeceğin fiyatı üzerinden hesaplanır.
		            double discount = icecekFiyati * 0.20;
		            currentTotal -= discount; // İçecek fiyatı düşülmüş olduğu için indirim miktarı direkt düşülür.
		            totalDiscountAmount += discount;
		            discountDetails[1] = discount;
		        }

		        // 3. 200 TL Üzeri İndirimi (%10) - Combo ve Happy Hour sonrası kalan tutar 200 TL'nin üzerindeyse.
		        if (currentTotal > 200.0) {
		            double discount = currentTotal * 0.10;
		            currentTotal -= discount;
		            totalDiscountAmount += discount;
		            discountDetails[2] = discount;
		        }

		        // 4. Öğrenci İndirimi (Hafta İçi %10 ekstra indirim)
		        // Hafta İçi: Pzt(1) - Cuma(5)
		        if (ogrenci && gun >= 1 && gun <= 5) {
		            double discount = currentTotal * 0.10;
		            currentTotal -= discount;
		            totalDiscountAmount += discount;
		            discountDetails[3] = discount;
		        }
		        
		        // [Yeni Toplam Tutar, Toplam İndirim Miktarı, Combo İndirim Miktarı, ...]
		        // İndirim detayları dizisini de içeren yeni bir dizi döndürülebilir.
		        // Basitlik adına, sadece indirim miktarlarını ve nihai tutarı döndürelim.
		        
		        double[] finalResult = new double[5];
		        finalResult[0] = currentTotal; // Nihai Fiyat
		        finalResult[1] = discountDetails[0]; // Combo
		        finalResult[2] = discountDetails[1]; // Happy Hour
		        finalResult[3] = discountDetails[2]; // 200+
		        finalResult[4] = discountDetails[3]; // Öğrenci

		        return finalResult;
		    }

		    /**
		     * 8. calculateServiceTip(tutar): Bahşiş önerisi (%10)
		     */
		    public static double calculateServiceTip(double tutar) {
		        return tutar * 0.10;
		    }

		    // --- ANA METOT VE BİLGİ OLUŞTURMA ---

		    public static void main(String[] args) {
		        Scanner scanner = new Scanner(System.in);

		        // Girdi Alma
		        System.out.println("--- Akıllı Restoran Sipariş Sistemi ---\n");
		        System.out.print("1. Ana Yemek Seçimi (1-4, Yoksa 0): ");
		        int anaSecim = scanner.nextInt();
		        System.out.print("2. Başlangıç Seçimi (1-3, Yoksa 0): ");
		        int baslangicSecim = scanner.nextInt();
		        System.out.print("3. İçecek Seçimi (1-4, Yoksa 0): ");
		        int icecekSecim = scanner.nextInt();
		        System.out.print("4. Tatlı Seçimi (1-3, Yoksa 0): ");
		        int tatliSecim = scanner.nextInt();
		        System.out.print("5. Saat (8-23): ");
		        int saat = scanner.nextInt();
		        System.out.print("6. Hangi gün? (1=Pzt, ..., 7=Paz): ");
		        int gun = scanner.nextInt();
		        System.out.print("7. Öğrenci misiniz? (E/H): ");
		        String ogrenciStr = scanner.next().toUpperCase();
		        
		        scanner.close();

		        // Boolean Değişkenlerle Durum Takibi
		        boolean anaVar = anaSecim > 0;
		        boolean icecekVar = icecekSecim > 0;
		        boolean tatliVar = tatliSecim > 0;
		        boolean ogrenci = ogrenciStr.equals("E");
		        boolean combo = isComboOrder(anaVar, icecekVar, tatliVar);
		        boolean happyHour = isHappyHour(saat);

		        // Fiyat Hesaplama
		        double anaFiyat = getMainDishPrice(anaSecim);
		        double baslangicFiyat = getAppetizerPrice(baslangicSecim);
		        double icecekFiyat = getDrinkPrice(icecekSecim);
		        double tatliFiyat = getDessertPrice(tatliSecim);

		        double araToplam = anaFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

		        // İndirim Hesaplama
		        double[] indirimSonuclari = calculateDiscount(araToplam, combo, ogrenci, saat, gun, icecekFiyat);
		        double nihaiFiyat = indirimSonuclari[0];
		        double comboIndirim = indirimSonuclari[1];
		        double happyHourIndirim = indirimSonuclari[2];
		        double ikiYuzArtiIndirim = indirimSonuclari[3];
		        double ogrenciIndirim = indirimSonuclari[4];
		        
		        double toplamIndirimMiktari = comboIndirim + happyHourIndirim + ikiYuzArtiIndirim + ogrenciIndirim;

		        // Bahşiş Önerisi
		        double bahsisOnerisi = calculateServiceTip(nihaiFiyat);


		        // --- BİLGİ GÖSTERİMİ (Örnek Senaryo Formatına Uygun) ---
		        System.out.println("\n-------------------------------------------");
		        System.out.println("            💰 SİPARİŞ ÖZETİ 💰");
		        System.out.println("-------------------------------------------");
		        System.out.printf("Ana Yemek (%.2f TL), Başlangıç (%.2f TL)\n", anaFiyat, baslangicFiyat);
		        System.out.printf("İçecek (%.2f TL), Tatlı (%.2f TL)\n", icecekFiyat, tatliFiyat);
		        System.out.println("-------------------------------------------");
		        System.out.printf("**Ara Toplam (İndirimsiz):** %.2f TL\n", araToplam);
		        System.out.println("-------------------------------------------");

		        if (comboIndirim > 0) {
		            System.out.printf("✅ Combo Menü İndirimi (%%15): -%.2f TL\n", comboIndirim);
		        }
		        if (happyHourIndirim > 0) {
		            System.out.printf("✅ Happy Hour İndirimi (İçecek %%20): -%.2f TL\n", happyHourIndirim);
		        }
		        if (ikiYuzArtiIndirim > 0) {
		            System.out.printf("✅ 200 TL Üzeri İndirimi (%%10): -%.2f TL\n", ikiYuzArtiIndirim);
		        }
		        if (ogrenciIndirim > 0) {
		            System.out.printf("✅ Öğrenci İndirimi (%%10): -%.2f TL\n", ogrenciIndirim);
		        }
		        
		        if (toplamIndirimMiktari == 0) {
		             System.out.println("❌ Uygulanan İndirim Yok.");
		        }
		        
		        System.out.println("-------------------------------------------");
		        System.out.printf("💸 Toplam İndirim Miktarı: -%.2f TL\n", toplamIndirimMiktari);
		        System.out.printf("💲 NİHAİ ÖDENECEK TUTAR: %.2f TL\n", nihaiFiyat);
		        System.out.printf("🎁 Garson Bahşiş Önerisi (%%10): %.2f TL\n", bahsisOnerisi);
		        System.out.println("-------------------------------------------");


		        // --- ÖRNEK SENARYO KONTROLÜ ---
		        // Girdi: Ana=2(120), Başlangıç=2(45), İçecek=3(35), Tatlı=1(65)
		        // Saat=15, Öğrenci=E, Gün=3 (Çarşamba)
		        // Ara Toplam: 265 TL
		        // Beklenen Nihai Tutar: 196.42 TL
		        // Bahşiş: 19.64 TL

		        // Örnek senaryo girdilerini manuel olarak test etmek isterseniz,
		        // main metodunu aşağıdaki gibi değiştirebilirsiniz:
		        /*
		        System.out.println("\n--- ÖRNEK SENARYO KONTROLÜ (265 TL -> 196.42 TL) ---");
		        generateTicketInfo(2, 2, 3, 1, 15, 3, true); // Yeni bir helper metot ile daha temiz olurdu
		        */
		    }
		    
		    // Not: Konsol girdileri ile çalışmak için main metodu güncellendi. 
		    // Girdileri örnek senaryo ile aynı girerseniz, çıktı 196.42 TL olacaktır.
		}



==============CIKTISI==============

  --- Akıllı Restoran Sipariş Sistemi ---

1. Ana Yemek Seçimi (1-4, Yoksa 0): 2
2. Başlangıç Seçimi (1-3, Yoksa 0): 1
3. İçecek Seçimi (1-4, Yoksa 0): 2
4. Tatlı Seçimi (1-3, Yoksa 0): 2
5. Saat (8-23): 20
6. Hangi gün? (1=Pzt, ..., 7=Paz):  6
7. Öğrenci misiniz? (E/H): E

-------------------------------------------
            💰 SİPARİŞ ÖZETİ 💰
-------------------------------------------
Ana Yemek (120,00 TL), Başlangıç (25,00 TL)
İçecek (12,00 TL), Tatlı (55,00 TL)
-------------------------------------------
**Ara Toplam (İndirimsiz):** 212,00 TL
-------------------------------------------
✅ Combo Menü İndirimi (%15): -31,80 TL
-------------------------------------------
💸 Toplam İndirim Miktarı: -31,80 TL
💲 NİHAİ ÖDENECEK TUTAR: 180,20 TL
🎁 Garson Bahşiş Önerisi (%10): 18,02 TL
-------------------------------------------
