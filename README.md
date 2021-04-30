# WORKOUT

Aplikasi ini dibuat untuk memenuhi keperluan tugas besar IF3210 Pengembangan Aplikasi pada Platform Khusus

## Summary

  - [Cara Kerja](#cara-kerja)
  - [Library yang Digunakan](#library-yang-digunakan)
  - [Screenshots](#screenshots)
  - [Pembagian Kerja](#pembagian-kerja)

## Cara Kerja

### Sports News
  
##### Dari API berita, kami mengambil data yang dibutuhkan menggunakan retrofit untuk menampilkan daftar berita dan dimasukkan ke RecyclerView. Kemudian, berita yang terpilih dibuka menggunakan WebView

### Training Tracker
##### Pengguna dapat memilih mode Cycling/Running/Walking. Ketika pengguna menekan tombol start, aplikasi akan langsung menyimpan titik lokasi terkini dan meng-track route yang diambil oleh pengguna. Ketika distop, pengguna akan langsung dimunculkan tampilan detail workout yang telah dilakukan dengan rincian rute, mode workout, hingga posisi terakhir. Data tersebut disimpan dalam LocalDB (Room) agar dapat diakses di masa mendatang

### Training History
##### Pengguna memilih tanggal yang ingin diperiksa riwayat workoutnya. Dari tanggal itu akan diperiksa seluruh daftar workout yang sesuai tanggalnya. Kemudian, akan ditampilkan list dari semua workout yang dilakukan pada tanggal tersebut. Pengguna dapat membuka tampilan detail dari workout yang terpilih

### Training Scheduler
##### Aplikasi menyimpan jadwal rutinitas untuk diingatkan. Service akan terus berjalan di balik layar. Ketika waktu yang ditentukan tiba, service akan mengirimkan notifikasi ke layar perangkat

## Library yang Digunakan

- [AndroidX](https://www.googleadservices.com/pagead/aclk?sa=L&ai=DChcSEwjLyaDBjabwAhVTKXIKHei4DQ0YABAAGgJzZg&ae=2&ohost=www.google.com&cid=CAESQOD2tRTJ1Km4v37KNuk501SRn06L2cUEOrGE32zkvw2zZ0xMepKuvlSwALw1Gdfw9CMikc8aCNanbv_vMwNF3_Q&sig=AOD64_0yvd8A9ysNfl4GDCLXkPCKlJpnKA&q&adurl&ved=2ahUKEwic1prBjabwAhUBeisKHeb-BqEQ0Qx6BAgFEAE) - Modul turunan dari Jetpack. Digunakan untuk memberikan komponen yang masih disupport resmi oleh Google
- [Android Material](https://material.io/develop/android) - Digunakan untuk mempermudah development dengan menampilkan template-template yang bisa langsung digunakan
- [Retrofit](https://square.github.io/retrofit/) - Digunakan untuk mengambil data dari API
- [Picasso](https://square.github.io/picasso/) - Digunakan untuk merender gambar langsung dari url
- [Legacy Support](https://developer.android.com/jetpack/androidx/releases/legacy) - Digunakan untuk memastikan penggunaan modul-modul SDK lama dapat berjalan dengan baik
- [Google Maps Services](https://developers.google.com/maps/documentation/android-sdk/overview?hl=id) - Digunakan untuk keperluan Geolocation

## Screenshots

## Pembagian Kerja

- **Rehan Adi Satrya** - *Provided README Template* -
    [Rehanadi](https://gitlab.informatika.org/rehanadi)
- **Rakha Fadhilah** - *Provided README Template* -
    [RakhaFadhilah](https://gitlab.informatika.org/rakhafadhilah)
- **Tifany Angelia** - *Provided README Template* -
    [Tifangel](https://gitlab.informatika.org/tifangel)