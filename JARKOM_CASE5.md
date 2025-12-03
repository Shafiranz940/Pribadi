  Dalam case 5 kali ini membahas seputar transport layer dengan tujuan dari melakukan case ini kita diharapkan dapat memahami prinsip kerja dari Reliable Data Transfer yang diimplementasikan di protoko transport (TCP), mengamati mekanisme RDT padaTCP saat melakukan pembentukan koneksi (SYN SYN-ACK, ACK), pengiriman data, dan penerimaan ACK, serta mengamati mekanisme RDT pada TCP saat terjadinya packet loss Mengamati kinerja pada TCP congestion control cubic dan rend.

  Berikut ini langkah-langkah yang saya lakukan dalam mencoba case 5 tersebut:

**Persiapan**

  - Melakukan konfigurasi ip address static yaitu `192.168.200.100/24` pada `netics-pc-desktop` dan `192.168.200.101` pada `netics-pc-2`
    
    <img width="407" height="142" alt="image" src="https://github.com/user-attachments/assets/22f0d232-ba11-4bb2-a5b8-879c34cf3df4" />

    <img width="398" height="139" alt="image" src="https://github.com/user-attachments/assets/6ad6f354-8346-4fd6-9596-30a75d9f31a8" />

  - Kemudian melakukan konfigurasi untuk membuat bridge pada node `netics-pc-1` untuk menghubungkan `netics-pc-2` dan `netics-pc-desktop` dengan membuat file `/myscripts/start.sh` sesuai dengan isi file dalam case 5 berikut:
    ```
    mkdir -p /myscripts/
    touch /myscripts/start.sh
    chmod +x /myscripts/start.sh
    echo '#!/bin/sh
    
    brctl addbr br0
    brctl addif br0 eth0
    brctl addif br0 eth1
    
    ip link set br0 up
    ' >> /myscripts/start.sh
    ```
    Node tersebut akan dijalankan dengan command berikut:
    ```
    echo '/bin/sh /myscripts/start.sh' >> ~/.bashrc
    sh ~/.bashrc
    ```
    <img width="734" height="503" alt="image" src="https://github.com/user-attachments/assets/be0d58f3-14e2-4762-bc36-f3980aa2578b" />

  - Selanjutnya melakukan konfigurasi http server/web server pada port 9999 melalui node `netics-pc-2` dengan membuat file `/myscripts/start_http.sh` sesuai dengan isi file dalam case 5 berikut:
    ```
    mkdir -p /myscripts
    touch /myscripts/start_http.sh 
    chmod +x /myscripts/start_http.sh
    echo '#!/bin/sh
    mkdir -p /myscripts/myweb
    cd /myscripts/myweb
    python3 -m http.server 9999 -d /myscripts/myweb' >> /myscripts/start_http.sh
    ```
    Node tersebut akan dijalankan dengan command berikut:
    ```
    echo '/bin/sh /myscripts/start_http.sh' >> ~/.bashrc
    sh ~/.bashrc	
    ```
  - Langkah persiapan selanjutnya yaitu melakukan pengecekan di `netics-pc-desktop-1` dengan membuka console vnc dan melakukan ping ip address `192.168.200.101` dan membuka browser chromium `http://192.168.200.101:9999`

    <img width="692" height="515" alt="image" src="https://github.com/user-attachments/assets/83716e42-3267-42c9-9053-df645010ca72" />

    <img width="1041" height="855" alt="image" src="https://github.com/user-attachments/assets/a1d86258-7ff4-402d-b505-d55549ab9c7d" />

  - Kemudian membuat file berukuran 1000, 10000, 50000, dan 5M bytes yang akan menghasilkan file `tes1000.bin, tes10000,bin, tes50000.bin, dan tes 5M.bin` dalam node `netics-pc-2` dengan command berikut:
    ```
    cd /myscripts/myweb

    dd if=/dev/random of=tes1000.bin bs=1 count=1000
    dd if=/dev/random of=tes10000.bin bs=10 count=1000
    dd if=/dev/random of=tes50000.bin bs=50 count=1000
    dd if=/dev/random of=tes5M.bin bs=5000 count=1000
    ```
    Setelah itu melakukan pengecekan di web servernya.
    
    <img width="731" height="497" alt="image" src="https://github.com/user-attachments/assets/457eb4b3-6265-4b69-97df-7c31942ab112" />

    <img width="1047" height="860" alt="image" src="https://github.com/user-attachments/assets/53c6130b-1902-4fd0-991d-d826e2de7578" />

**Scenario 1**
  - Mencoba mekanisme reliable data transfer tanpa adanya gangguan dengan melakukan download menggunakan curl alamat url `http://192.168.200.101:9999/tes10000.bin` pada console `netics-pc-desktop-1` menggunakan command `curl -o /dev/null -s -w "size=%{size_download}B speed=%{speed_download}B/s total=%{time_total}s\n" http://192.168.200.101:9999/tes1000.bin`. Kemudian melakukan pengecekan pada wireshark yang ada dalam web server `netics-pc-desktop-1`. Hasil observasi yang saya temukan adalah sebagai berikut:
    
**Scenario 2**

**Scenario 3**

**Tugas 1**
**Tugas 2**
