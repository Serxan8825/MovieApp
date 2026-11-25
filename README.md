# MovieApp 🎬

<p align="center">
  <img src="https://github.com/user-attachments/assets/fbe67b64-5e3e-4852-83cd-73c1d73227b7" width="30%" />
  <img src="https://github.com/user-attachments/assets/7984e31f-7101-4c38-a192-b421c05b7986" width="30%" />
  <img src="https://github.com/user-attachments/assets/c54c6ec5-4faa-42b6-940f-804e963146a1" width="30%" />
</p>


**MovieApp** — müasir Android inkişaf standartları (Modern Android Development) ilə hazırlanmış, filmlərin kəşf edilməsi və idarə olunması üçün nəzərdə tutulmuş mobil tətbiqdir. 

## 🌟 Əsas Xüsusiyyət: Offline-First
Tətbiqin ən güclü tərəfi **Offline-First** prinsipi ilə işləməsidir. 
- **Keşləmə:** Məlumatlar API-dan çəkildikdən sonra yerli bazada (Room) saxlanılır.
- **Kəsintisiz Təcrübə:** İnternet bağlantısı olmasa belə, istifadəçi daha əvvəl yüklənmiş filmləri görə və tətbiqi istifadə edə bilər.
- **Sinxronizasiya:** Məlumat axını hər zaman yerli bazadan (Single Source of Truth) oxunur.

## 🛠 Texnologiyalar və Kitabxanalar
- **Dil:** Kotlin
- **UI:** Jetpack Compose (Deklarativ UI)
- **Arxitektura:** Clean Architecture + **MVI (Model-View-Intent)**
- **Dependency Injection:** Dagger Hilt
- **Yerli Baza:** Room Persistence Library
- **Şəbəkə:** Retrofit & OkHttp
- **Asinxronluq:** Kotlin Coroutines & Flow (StateFlow)
- **Şəkil Yükləmə:** Coil

## 📂 Layihə Strukturu
Layihə Clean Architecture prinsiplərinə uyğun olaraq aşağıdakı qatlara bölünmüşdür:
- **`data`**: API sorğuları (Remote), Room DB (Local), Repository implementasiyaları və Mapper-lər.
- **`domain`**: Biznes məntiqi (Use Cases), Repository interfeysləri və Domain Modellər.
- **`presentation`**: MVI patterni əsasında UI (Compose), ViewModel-lər və UI State-lərin idarə olunması.
- **`di`**: Hilt modulları vasitəsilə asılılıqların inyeksiyası.
- **`core`**: Ümumi utilitlər, rəng və mövzu (Theme) tənzimləmələri.

## ⚙️ Quraşdırma (Installation)
1. Layihəni klonlayın:
2.  [TheMovieDB](https://www.themoviedb.org/) saytından şəxsi **API Key** əldə edin.
3. Layihənin kök qovluğundakı `local.properties` faylını açın və aşağıdakı sətri əlavə edin:
4. MOVIE_API_KEY=sizin_api_keyiniz_bura

5.Android Studio-da layihəni açın, Gradle sinxronizasiyasını tamamlayın və tətbiqi işə salın.
   
   
   
