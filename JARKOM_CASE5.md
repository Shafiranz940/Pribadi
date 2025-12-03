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
  - Mencoba mekanisme reliable data transfer tanpa adanya gangguan dengan melakukan download menggunakan curl alamat url `http://192.168.200.101:9999/tes10000.bin` pada console `netics-pc-desktop-1` menggunakan command
    ```
    curl -o /dev/null -s -w "size=%{size_download}B speed=%{speed_download}B/s total=%{time_total}s\n" http://192.168.200.101:9999/tes1000.bin
    ```
    Kemudian melakukan pengecekan pada wireshark yang ada dalam web server `netics-pc-desktop-1`. Hasil observasi yang saya temukan adalah sebagai berikut:
    | No  | Parameter   | Hasil Observasi    |
    | --- | ----------- | ------------------ | 
    | 1   | IP sumber dan tujuan | <img width="869" height="600" alt="image" src="https://github.com/user-attachments/assets/fcc617d7-02f4-41b0-9777-184c4d0c95b6" /><img width="858" height="598" alt="image" src="https://github.com/user-attachments/assets/9a9dc8b5-fc55-42f8-8a79-0de9fac40a67" />|
    | 2   | Proses handshake (3 paket awal) | <img width="863" height="453" alt="image" src="https://github.com/user-attachments/assets/8cb5fe59-a1b3-4096-9d49-ba6fe70f9cbc" />|
    | 3   | Sequence Number awal klien | <img width="872" height="597" alt="image" src="https://github.com/user-attachments/assets/cfec321b-b1c4-4a72-936c-8239e3eaa4cf" />|
    | 4   | Sequence Number awal server | <img width="862" height="594" alt="image" src="https://github.com/user-attachments/assets/752742d1-71f6-44d8-bbdf-28b39904a01c" />|
    | 5   | ACK setelah handshake | <img width="866" height="590" alt="image" src="https://github.com/user-attachments/assets/7483ed41-9f19-4e26-8bbb-cc17b44cff86" />|
    | 6   | Kenaikan SEQ antar segmen data | <img width="1037" height="845" alt="image" src="https://github.com/user-attachments/assets/67cad9af-03a6-4d87-819e-e74cda6f7c29" />|
    | 7   | Pola ACK penerima | <img width="1015" height="598" alt="image" src="https://github.com/user-attachments/assets/6a2a71ac-10a6-49fa-872b-3bc3d234e193" /><img width="1014" height="594" alt="image" src="https://github.com/user-attachments/assets/e34396df-4f51-4628-932f-94caa9eb1d42" /><img width="1014" height="593" alt="image" src="https://github.com/user-attachments/assets/0fcc06ec-86ae-404f-9058-74590a87ad64" />|
    | 8   | RTT rata-rata | <img width="682" height="486" alt="image" src="https://github.com/user-attachments/assets/6f7db58f-6f57-49af-836b-983e319e1e09" /><img width="1020" height="590" alt="image" src="https://github.com/user-attachments/assets/d03c33d3-8e5e-4e41-8ae2-486fc0fe0d28" />|
    | 9   | Status retransmission | <img width="1022" height="256" alt="image" src="https://github.com/user-attachments/assets/467ab748-6996-470a-9976-bc818a733de2" />|
    | 10  | Pola penutup koneksi| <img width="1015" height="595" alt="image" src="https://github.com/user-attachments/assets/f5c469a7-97d7-4fbf-b074-93e472ba541c" />|

  - Apa fungsi utama Sequence Number dan Acknowledgment Number dalam TCP?
  - Mengapa ACK pada TCP bersifat kumulatif, bukan per-paket
  - Apa bukti dari hasil Wireshark bahwa TCP berhasil melakukan transfer data dengan reliable? Apa yang akan terjadi jika salah satu segmen data hilang?
       
**Scenario 2**
  - Mencoba mekanisme reliable data transfer dengan adanya gangguan 1% packet loss dengan melakukan simulasi packet loss pada bridge `netics-pc-1` menggunakan command berikut:
    ```
    tc qdisc del dev eth0 root
    tc qdisc del dev eth1 root
    tc qdisc add dev eth0 root netem loss 10%
    tc qdisc add dev eth1 root netem loss 10%
    ```
    Selanjutnya melakukan download menggunakan curl alamat url `http://192.168.200.101:9999/tes5M.bin` pada console `netics-pc-desktop-1` menggunakan command
    ```
    curl -o /dev/null -s -w "size=%{size_download}B speed=%{speed_download}B/s total=%{time_total}s\n" http://192.168.200.101:9999/tes5M.bin
    ```
    <img width="748" height="427" alt="image" src="https://github.com/user-attachments/assets/dfca9ba2-a794-4cd0-8620-2a9a774d57af" />

    <img width="1038" height="617" alt="image" src="https://github.com/user-attachments/assets/3ebcdde9-8d8a-40be-82a4-558eb7ead690" />

    Kemudian melakukan pengecekan pada wireshark yang ada dalam web server `netics-pc-desktop-1`. Hasil observasi yang saya temukan adalah sebagai berikut:
    | No  | Parameter   | Hasil Observasi    |
    | --- | ----------- | ------------------ | 
    | 1   | Persentase loss yang disimulasikan | 1%|
    | 2   | Jumlah retransmission | <img width="1018" height="588" alt="image" src="https://github.com/user-attachments/assets/08c4f696-6624-4244-acee-d2c1121cbab9" />|
    | 3   | Jumlah dupACK | <img width="1020" height="597" alt="image" src="https://github.com/user-attachments/assets/75024f3f-0429-4115-b80a-b42aced0e458" />|
    | 4   | RTT sebelum dan sesudah loss | <img width="1018" height="595" alt="image" src="https://github.com/user-attachments/assets/32b03ab9-49df-4f7d-a9d6-472519b98e1d" /><img width="1016" height="593" alt="image" src="https://github.com/user-attachments/assets/c8fb3e43-0f9c-462f-8170-c6dccaeedd8d" /><img width="1015" height="590" alt="image" src="https://github.com/user-attachments/assets/8169176e-ff8f-4056-a0d5-40585c2389ba" />|
    | 5   | Fast retransmit terdeteksi | <img width="1013" height="595" alt="image" src="https://github.com/user-attachments/assets/03298973-882d-41a6-908b-369530264268" />|
    | 6   | Timeout retransmission | <img width="1017" height="603" alt="image" src="https://github.com/user-attachments/assets/749a044c-8208-41a7-8133-d3146eb26a72" /><img width="1015" height="590" alt="image" src="https://github.com/user-attachments/assets/acd654b8-7189-4000-83b6-bc933c47cceb" />|
    | 7   | Throughput rata-rata | <img width="669" height="222" alt="image" src="https://github.com/user-attachments/assets/1acdcbce-cb1f-469c-9c5d-830c566b4dcd" /><img width="982" height="687" alt="image" src="https://github.com/user-attachments/assets/d90e123d-0435-4f42-a4cb-a8bcd4f55d32" />|
    | 8   | Lama transfer total | <img width="1042" height="741" alt="image" src="https://github.com/user-attachments/assets/29af8467-9bc2-44ab-82b6-33d8cbf87546" /><img width="1017" height="603" alt="image" src="https://github.com/user-attachments/assets/8e3a33e6-f5a5-4a0e-ac97-1e7503d80067" /><img width="1019" height="596" alt="image" src="https://github.com/user-attachments/assets/289fd384-63a5-4aa7-b2a1-93f1cd31ba64" />|
    | 9   | Pola cwnd(perkiraan) | <img width="1029" height="424" alt="image" src="https://github.com/user-attachments/assets/9e0ea23a-a7dc-40b4-80fd-baedf84b003e" /><img width="1025" height="407" alt="image" src="https://github.com/user-attachments/assets/b2f4420b-780b-48e9-9ad6-5e2931dba636" />|
    
  - Bagaimana TCP mengetahui bahwa sebuah segmen hilang?
  - Jelaskan perbedaan antara timeout retransmission dan fast retransmit.
  - Mengapa throughput menurun saat terjadi loss?
  - Bagaimana menunjukkan dari wireshark bahwa TCP tetap reliable meskipun terjadi loss?
    
**Scenario 3**
  - Mencoba mekanisme TCP congestion control "cubic" dengan adanya gangguan 1% packet loss dan latency 100ms dengan melakukan simulasi packet loss pada bridge `netics-pc-1` menggunakan command berikut:
    ```
    tc qdisc del dev eth0 root
    tc qdisc del dev eth1 root
    tc qdisc add dev eth0 root handle 1: tbf rate 2mbit burst 32kbit latency 100ms 
    tc qdisc add dev eth0 parent 1:1 handle 10: netem delay 100ms loss 1%
    tc qdisc add dev eth1 root handle 1: tbf rate 2mbit burst 32kbit latency 100ms
    tc qdisc add dev eth1 parent 1:1 handle 10: netem delay 100ms loss 1%
    ```
    Selanjutnya melakukan setup mekanisme TCP congestion control "cubic" dengan melakukan download menggunakan curl alamat url `http://192.168.200.101:9999/tes5M.bin` pada console `netics-pc-desktop-1` menggunakan command
    ```
    sysctl -w net.ipv4.tcp_congestion_control=cubic
    curl -o /dev/null -s -w "size=%{size_download}B speed=%{speed_download}B/s total=%{time_total}s\n" http://192.168.200.101:9999/tes5M.bin
    ```
    <img width="741" height="229" alt="image" src="https://github.com/user-attachments/assets/a63ca973-9e5f-461c-99c9-38515ee7ded4" />

    <img width="1041" height="524" alt="image" src="https://github.com/user-attachments/assets/eee74021-bcf4-4d73-b510-9ad98113b8c2" />
    
    Kemudian melakukan pengecekan pada wireshark yang ada dalam web server `netics-pc-desktop-1`. Hasil observasi yang saya temukan adalah sebagai berikut:
    | No  | Parameter   | Hasil Observasi    |
    | --- | ----------- | ------------------ | 
    | 1   | RTT rata-rata | <img width="1042" height="605" alt="image" src="https://github.com/user-attachments/assets/4d6371d3-57ed-4307-b828-79dc38b3ef13" /><img width="1031" height="601" alt="image" src="https://github.com/user-attachments/assets/6acd9966-7750-4015-a9f7-45e980c9bb6a" /><img width="1069" height="782" alt="image" src="https://github.com/user-attachments/assets/b3715f9b-8c08-4b14-abd2-3bda0b19f89b" />|
    | 2   | RTT maksimum | <img width="1031" height="601" alt="image" src="https://github.com/user-attachments/assets/fae4acea-fa96-42ed-bb0e-f3ea149ad5b0" />|
    | 3   | Throughput(curl) | <img width="1041" height="642" alt="image" src="https://github.com/user-attachments/assets/6068dcae-a34f-43e4-9beb-af72c0bc2e11" /><img width="688" height="500" alt="image" src="https://github.com/user-attachments/assets/166cf176-cb69-4bf4-a42c-caaedc7c7a76" />|
    | 4   | Waktu transfer total | <img width="1041" height="642" alt="image" src="https://github.com/user-attachments/assets/cf29d090-ce73-4309-b1b1-81f9903df884" />|
    | 5   | Jumlah retransmission | <img width="1043" height="609" alt="image" src="https://github.com/user-attachments/assets/3c93af15-2b1d-4191-85f0-31b3a948fe3c" />|
    | 6   | Jumlah dupACK | <img width="1020" height="606" alt="image" src="https://github.com/user-attachments/assets/0407b83f-10ee-48b7-9c74-4655e0fd0faf" />|
    | 7   | Pola cwnd (awal-akhir) | <img width="998" height="702" alt="image" src="https://github.com/user-attachments/assets/8f15ee9a-d5cf-405d-b58e-7dec9a31f227" />|
    | 8   | Nilai rwnd rata-rata | <img width="982" height="622" alt="image" src="https://github.com/user-attachments/assets/133ec2cb-58af-44ab-ae3a-97aec946df88" /><img width="700" height="500" alt="image" src="https://github.com/user-attachments/assets/0582abc6-9e6e-47e5-8850-0297f8768839" />****<img width="957" height="575" alt="image" src="https://github.com/user-attachments/assets/fbe3d796-d38a-4783-8355-71eaba8abfdb" />|
    | 9   | Stabilitas throughput(grafik wireshark)| <img width="688" height="500" alt="image" src="https://github.com/user-attachments/assets/23649581-4e1a-429c-a714-befbd92c587e" /><img width="988" height="702" alt="image" src="https://github.com/user-attachments/assets/2e3de61d-c4a9-49c8-83a3-82b757d0b169" />|
    | 10   | Efisiensi(Throughput ÷ RTT) | <img width="981" height="615" alt="image" src="https://github.com/user-attachments/assets/a9da6ed8-4b02-4297-8551-b911dfc98dd7" /><img width="687" height="493" alt="image" src="https://github.com/user-attachments/assets/30a6a744-2e0b-4c5c-80ad-b529f3f81c98" /> <img width="1041" height="642" alt="image" src="https://github.com/user-attachments/assets/6068dcae-a34f-43e4-9beb-af72c0bc2e11" /><img width="688" height="500" alt="image" src="https://github.com/user-attachments/assets/166cf176-cb69-4bf4-a42c-caaedc7c7a76" />|

  - Algoritma mana yang memberikan throughput tertinggi? Mengapa demikian?
  - Algoritma mana yang paling sensitif terhadap RTT tinggi?
  - Apakah ada hubungan antara jumlah retransmission dan jenis algoritma?
  - Apa perbedaan bentuk kurva cwnd antara Reno dan Cubic?
  - Algoritma mana yang paling efisien di jaringan dengan delay tinggi? Jelaskan.
    
**Tugas 1**

**Tugas 2**
