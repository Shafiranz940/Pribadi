[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/aRvIU2lf)
| Name           | NRP        | Kelas     |
| ---            | ---        | ----------|
| Shafira Nauraishma Zahida | 5025241235 | Jarkom B |



## Put your topology config image here!

<img width="1349" height="775" alt="image" src="https://github.com/user-attachments/assets/a42db051-bb20-4eb7-8404-b5995653b4a1" />


## Put your GNS3 Project file here!

https://drive.google.com/drive/folders/1FGoIonDDm2yeQZy2MKJZ8kL5BTl7iXFC?usp=sharing

<br>

## Soal 1

> Menggunakan metode VLSM, buatlah pembagian subnet untuk masing-masing gedung dengan cara yang seefisien mungkin!

> _Using the VLSM method, create subnets for each building as efficiently as possible!_

**Answer:**

- Screenshot

  <img width="1599" height="899" alt="image" src="https://github.com/user-attachments/assets/5811dd7e-dde5-47b3-abbd-1fb26f862114" />

  <img width="1391" height="480" alt="image" src="https://github.com/user-attachments/assets/e1f47819-a6e2-40c0-997c-cdf630b302da" />


- Explanation

  Melakukan subnetting dengan ketentuan VLSM serta disesuaikan dengan jumlah host/PC yang diketahui dalam soal. Berikut ini link perhitungan penentuan ip address seluruh node beserta subnettingnya:
  https://docs.google.com/spreadsheets/d/1ycHazMdKJx4GITilaJMKPRBlMUdmiNKowafO9nNCiS8/edit?gid=0#gid=0

<br>

## Soal 2

> Konfigurasi semua router agar bisa terhubung ke semua jaringan. Gunakan static routing dan uji dengan melakukan ping dari **Budapest** ke **Alekhine** dan dari **Ponziani** ke **Sicilian**!

> _Configure all routers to connect to all networks. Use static routing and perform testing by pinging from **Budapest** to **Alekhine** and from **Ponziani** to **Sicilian**!_

**Answer:**

- Screenshot

  <img width="1559" height="353" alt="image" src="https://github.com/user-attachments/assets/b7ab3c5d-52f0-4b47-adc6-1674d07d8f50" />

- Explanation

  Melakukan static routing antar router dan antar node yang tersambung dalam setiao router berdasarkan aturan berikut:
  
  **ROUTER**

  Melakukan static routing antara router Lucena, Zwsichenzug, Fianchetto, dan Smith-Morra dengan command berikut pada konfigurasinya:
    - Lucena
      ```
      
      # Static config for eth0
      auto eth0
      iface eth0 inet static
      	address 10.155.0.33
      	netmask 255.255.255.248
      
      # Static config for eth1
      auto eth1
      iface eth1 inet static
      	address 10.155.0.65
      	netmask 255.255.255.192
      
      post-up ip route add 10.155.0.40/29 via 10.155.0.34
      post-up ip route add 10.155.0.128/25 via 10.155.0.34
      post-up ip route add 10.155.0.48/28 via 10.155.0.34
      post-up ip route add 10.155.1.0/24 via 10.155.0.34
      post-up ip route add 10.155.0.28/30 via 10.155.0.35
      post-up ip route add 10.155.32.0/19 via 10.155.0.35
      post-up ip route add 10.155.8.0/21 via 10.155.0.35

      post-up sysctl -w net.ipv4.ip_forward=1
      ```
    - Zwichenzug
      ```
      # Static config for eth0
      auto eth0
      iface eth0 inet static
      	address 10.155.0.34
      	netmask 255.255.255.248
      
      # Static config for eth1
      auto eth1
      iface eth1 inet static
      	address 10.155.0.41
      	netmask 255.255.255.248
      
      post-up ip route add 10.155.0.64/26 via 10.155.0.33
      post-up ip route add 10.155.0.28/30 via 10.155.0.35
      post-up ip route add 10.155.0.48/28 via 10.155.0.43
      post-up ip route add 10.155.0.128/25 via 10.155.0.43
      post-up ip route add 10.155.1.0/24 via 10.155.0.43
      post-up ip route add 10.155.32.0/19 via 10.155.0.35
      post-up ip route add 10.155.8.0/21 via 10.155.0.35
      
      post-up sysctl -w net.ipv4.ip_forward=1
      ```
    - Fianchetto
      ```
      # Static config for eth0
      auto eth0
      iface eth0 inet static
      	address 10.155.0.29
      	netmask 255.255.255.252
      
      # Static config for eth1
      auto eth1
      iface eth1 inet static
      	address 10.155.0.35
      	netmask 255.255.255.248

      # Routing eth0
      post-up ip route replace default via 10.155.0.30 dev eth0
      
      # Routing eth1
      post-up ip route add 10.155.0.64/26 via 10.155.0.33 
      post-up ip route add 10.155.0.40/29 via 10.155.0.34 
      post-up ip route add 10.155.0.128/25 via 10.155.0.34
      post-up ip route add 10.155.0.48/28 via 10.155.0.34
      post-up ip route add 10.155.1.0/24 via 10.155.0.34
      
      post-up sysctl -w net.ipv4.ip_forward=1
      ```
    - Smith-Morra
      ```
      # DHCP config for eth0
      auto eth0
      iface eth0 inet dhcp
      
      # Static config for eth1
      auto eth1
      iface eth1 inet static
      	address 10.155.0.30
      	netmask 255.255.255.252
      	gateway 10.155.0.29
      
      # Static config for eth2
      auto eth2
      iface eth2 inet static
      	address 10.155.32.1
      	netmask 255.255.224.0
      
      # Static config for eth3
      auto eth3
      iface eth3 inet static
      	address 10.155.8.1
      	netmask 255.255.248.0
      
      post-up ip route add 10.155.0.32/29 via 10.155.0.29 
      post-up ip route add 10.155.32.0/19 via 10.155.32.1 
      post-up ip route add 10.155.8.0/21 via 10.155.8.1
      post-up ip route add 10.155.0.128/25 via 10.155.0.29
      post-up ip route add 10.155.0.48/28 via 10.155.0.29
      post-up ip route add 10.155.1.0/24 via 10.155.0.29
      
      post-up sysctl -w net.ipv4.ip_forward=1
      ```
    - Zugzwang
      ```
      # Static config for eth0
      auto eth0
      iface eth0 inet static
      	address 10.155.0.43
      	netmask 255.255.255.248
      	gateway 10.155.0.41
      
      # Static config for eth1
      auto eth1
      iface eth1 inet static
      	address 10.155.0.129
      	netmask 255.255.255.128
      
      # Static config for eth2
      auto eth2
      iface eth2 inet static
      	address 10.155.0.49
      	netmask 255.255.255.240
      
      # Static config for eth3
      auto eth3
      iface eth3 inet static
      	address 10.155.1.1
      	netmask 255.255.255.0
      
      post-up ip route add 10.155.0.64/26 via 10.155.0.41
      post-up ip route add 10.155.0.32/29 via 10.155.0.41
      post-up ip route add 10.155.0.40/29 via 10.155.0.41
      post-up ip route add 10.155.0.28/30 via 10.155.0.41
      post-up ip route add 10.155.32.0/19 via 10.155.0.41
      post-up ip route add 10.155.8.0/21 via 10.155.0.41
      
      post-up sysctl -w net.ipv4.ip_forward=1
      ```
      
      **NODE**
      
      Kemudian disetiap nodenya saya menambahkan `gateway <IP Router yang terhubung dengan node>` untuk membuat static routing default melalui router yang tersambung agar dapat terhubung dengan router lain. Setelah melakukan routing, saya melakukan pengecekan routing yang telah saya buat dengan melakukan ping ip node yang diinginkan dalam soal. Dimana saya melakukan ping di node Budapes menuju Alekhine dan node Ponziani menuju ke Sicilian.
<br>

## Soal 3

> Berikan seluruh client (**Blackmar-Diemer, Budapest,** dan **Stafford**) IP secara dinamis dari DHCP. Range IP dibebaskan, namun tunjukkan bahwa mereka mendapatkan IP secara dinamis!

> _Assign all clients (**Blackmar-Diemer, Budapest,** and **Stafford**) dynamic IP addresses via DHCP. You may use any IP range you would like, but prove that they receive IP addresses dynamically!_

**Answer:**

- Screenshot

  <img width="1582" height="617" alt="image" src="https://github.com/user-attachments/assets/e79e15db-2d31-4779-a0a3-883c918f1e2c" />

- Explanation

  Pertama melakukan forwarding di router `Smith-Morra` dengan command berikut:
  ```
  iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE
  echo 1 > /proc/sys/net/ipv4/ip_forward
  ```

  Selanjutnya melakukan konfigurasi nameserver pada `etc/resolv.conf` disetiap router dan node yang ada. Pastikan juga untuk menambah `ip route add default via <IP address gateway>` disetiap router yang tidak tersambung secara langsung dengan NAT, seperti pada router `Lucena, Zwischenzug, dan Zugwang`. Selanjutnya melakukan `ping google.com` di setiap router dan node yang telah dikonfigurasi.

  **DHCP Server dan Relay**

  Setelah memastikan setiap router dan node dapat terhubung, maka kita bisa mulai melakukan konfigurasi dhcp server dan dhcp relay seperti di modul 2 sesuai dengan node yang ditugaskan. Berikut ini konfigurasi dhcp server yang telah saya buat:
  ```
  subnet 10.155.0.64 netmask 255.255.255.192 {
  }

  subnet 10.155.32.0 netmask 255.255.224.0 {
      range 10.155.51.158 10.155.51.170;
      option routers 10.155.32.1;
      option subnet-mask 255.255.224.0;
      option domain-name-servers 8.8.8.8, 1.1.1.1;
  }
  
  subnet 10.155.8.0 netmask 255.255.248.0 {
      range 10.155.8.20 10.155.8.25;
      option routers 10.155.8.1;
      option subnet-mask 255.255.248.0;
      option domain-name-servers 8.8.8.8, 1.1.1.1;
  }
  ```

  Berikutnya mengaktifkan semua dhcp server dan dhcp relay dengan command `service isc-dhcp-server restart` dan `service isc-dhcp-relay restart`. Selanjutnya melakukan `echo 1 > /proc/sys/net/ipv4/ip_forward` untuk melakukan forwarding dhcp server yang telah di konfigurasi ke dhcp relay yang telah disiapkan. Di Smoth-Morra jalankan kembali command `iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE`. Langkah terakhir matikan dan nyalakan kembali node yang ip nya ingin dibuat secara dinamis. Kemudian cek ip address yang telah dibuat dengan command `ip -br addr`.
  
<br>

## Soal 4

> Berikan web server **Slav** dan **Sicilian** IP address yang tetap/fixed dari DHCP. 

> _Assign **Slav** and **Sicilian** web servers fixed IP addresses via DHCP._

**Answer:**

- Screenshot

  <img width="1617" height="566" alt="image" src="https://github.com/user-attachments/assets/6d11a853-e635-44db-b7c9-e026a2b684a9" />

- Explanation

  Pertama melakukan forwarding di router `Smith-Morra` dengan command berikut:
  ```
  iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE
  echo 1 > /proc/sys/net/ipv4/ip_forward
  ```

  Selanjutnya melakukan konfigurasi nameserver pada `etc/resolv.conf` disetiap router dan node yang ada. Pastikan juga untuk menambah `ip route add default via <IP address gateway>` disetiap router yang tidak tersambung secara langsung dengan NAT, seperti pada router `Lucena, Zwischenzug, dan Zugwang`. Selanjutnya melakukan `ping google.com` di setiap router dan node yang telah dikonfigurasi.

  **DHCP Server dan Relay**

  Setelah memastikan setiap router dan node dapat terhubung, maka kita bisa mulai melakukan konfigurasi dhcp server dan dhcp relay seperti di modul 2 sesuai dengan node yang ditugaskan. Berikut ini konfigurasi dhcp server yang telah saya buat:
  ```
  subnet 10.155.0.64 netmask 255.255.255.192 {
  }

  subnet 10.155.32.0 netmask 255.255.224.0 {
      range 10.155.51.158 10.155.51.170;
      option routers 10.155.32.1;
      option subnet-mask 255.255.224.0;
      option domain-name-servers 8.8.8.8, 1.1.1.1;
  }
  
  subnet 10.155.8.0 netmask 255.255.248.0 {
      range 10.155.8.20 10.155.8.25;
      option routers 10.155.8.1;
      option subnet-mask 255.255.248.0;
      option domain-name-servers 8.8.8.8, 1.1.1.1;
  }
  
  subnet 10.155.1.0 netmask 255.255.255.0 {
    option routers 10.155.1.1;
    option subnet-mask 255.255.255.0;
    option domain-name-servers 8.8.8.8, 1.1.1.1;
  }

  subnet 10.155.0.128 netmask 255.255.255.128 {
      option routers 10.155.0.129;
      option subnet-mask 255.255.255.128;
      option domain-name-servers 8.8.8.8, 1.1.1.1;
  }
  
  host Slav {
      hardware ethernet 02:42:3c:1e:47:00;
      fixed-address 10.155.1.2;
  }
  
  host Sicilian {
      hardware ethernet 02:42:80:92:e2:00;
      fixed-address 10.155.0.231;
  }
  ```

  Berikutnya mengaktifkan semua dhcp server dan dhcp relay dengan command `service isc-dhcp-server restart` dan `service isc-dhcp-relay restart`. Selanjutnya melakukan `echo 1 > /proc/sys/net/ipv4/ip_forward` untuk melakukan forwarding dhcp server yang telah di konfigurasi ke dhcp relay yang telah disiapkan. Di Smoth-Morra jalankan kembali command `iptables -t nat -A POSTROUTING -o eth0 -j MASQUERADE`. Setelah itu menambahkan configure `hwaddress ether 'hwaddress_milik_Slav' dan `hwaddress ether 'hwaddress_milik_Sicilian'. hwaddress dapat dilihat dengan menjalankan command `ip link show` pada node `slav` dan `sicilian`. Langkah terakhir matikan dan nyalakan kembali node yang ip nya ingin dibuat secara dinamis. Kemudian cek ip address yang telah dibuat dengan command `ip -br addr`.

<br>

## Soal 5

> Buatlah konfigurasi untuk domain:  
**parkov.com** → IP Node **Slav**  
**paskarov.com** → IP Node **Sicilian** 
Pada **DNS Master Caro-Kann.** Tambahkan juga subdomain www untuk kedua domain tersebut.

> _Configure the domains:  
**parkov.com** → **Slav** Node IP  
**paskarov.com** → **Sicilian** Node IP  
On the **Caro-Kann DNS Master,** then add the www subdomain for both domains._

**Answer:**

- Screenshot

  <img width="629" height="619" alt="image" src="https://github.com/user-attachments/assets/2380f28c-c8c3-4eed-9d61-e601c67cadc6" />

- Explanation

  **#DNS MASTER#**

    Membuat file `named.conf` dalam direktori `/myscripts/dns` dengan isi file konfigurasi berikut:
    ```
    options {
    directory "/myscripts/dns";
    listen-on { any; };
    allow-query { any; };
    allow-transfer { 10.155.0.62; };
  };
  
    zone "localhost" {
      type master;
      file "db.localhost";
  };
  
    zone "parkov.com" {
      type master;
      file "db.parkov.com";
      also-notify { 10.155.0.62; };
      allow-transfer { 10.155.0.62; };
  };
  
    zone "paskarov.com" {
      type master;
      file "db.paskarov.com";
      also-notify { 10.155.0.62; };
      allow-transfer { 10.155.0.62; };
  };
    ```
    Selanjutnya membuat file `db.localhost`, `db.parkov.com`, dan `db.paskarov.com` dengan isi file konfigurasi seperti berikut:
    
    **db.localhost**
    ```
    $TTL 86400
    @   IN  SOA localhost. root.localhost. (
            1       ; serial
            3600    ; refresh
            1800    ; retry
            604800  ; expire
            86400 ) ; minimum
    
        IN  NS  localhost.
        IN  A   127.0.0.1
    ```
    **db.parkov.com**
    ```
    $TTL 86400
    @   IN  SOA ns1.parkov.com. admin.parkov.com. (
            1       ; Serial
            3600    ; Refresh
            1800    ; Retry
            604800  ; Expire
            86400 ) ; Minimum TTL
    
        IN  NS  ns1.parkov.com.
    ns1 IN  A   10.155.0.50
    @   IN  A   10.155.1.2
    www IN  A   10.155.1.2
    ```
    **db.paskarov.com**
    ```
    $TTL 86400
    @   IN  SOA ns1.paskarov.com. admin.paskarov.com. (
            1 ; serial
        3600 ; refresh
        1800 ; retry
        604800 ; expire
        86400 ) ; minimum
    
        IN  NS  ns1.paskarov.com.
    ns1 IN  A   10.155.0.50
    @   IN  A   10.155.0.231
    www IN  A   10.155.0.231
    ```
    Langkah selanjutnya adalah membuat file `start_dns.sh` dengan isi konfigurasi sebagai berikut:
    ```
    #!/bin/bash
    
    named-checkzone parkov.com /myscripts/dns/db.parkov.com
    named-checkzone paskarov.com /myscripts/dns/db.paskarov.com
   
    pkill named
    named -c /myscripts/dns/named.conf -g
    ```
    Setelah membuat file-file yang dibutuhkan maka langkah selanjutnya adalah menjalankan dns master dengan masuk ke tmux dan menjalankan dengan command `bash /myscripts/dns/start_dns.sh`. Lalu cek apakah dns master berjalan dengan menggunakan command `ps aux | grep named`.
  
<br>

## Soal 6

> Konfigurasikan juga **Alekhine** sebagai **DNS Slave** yang bekerja untuk membantu **Caro-Kann.** Lakukan pengujian dengan **mematikan Caro-Kann** lalu coba ping ke domain dan subdomain tersebut (pilih salah satu saja).

> _Configure **Alekhine** as a **DNS Slave** to assist **Caro-Kann**. Perform testing by **disabling Caro-Kann** and then pinging the domain and subdomain (choose only one)._

**Answer:**

- Screenshot

  <img width="582" height="577" alt="image" src="https://github.com/user-attachments/assets/f07481d2-b94b-4d31-a61b-e7ab1880400c" />

  <img width="1919" height="215" alt="image" src="https://github.com/user-attachments/assets/110648d2-9298-4ef3-89d5-82fd334b3b72" />

  <img width="1919" height="1023" alt="image" src="https://github.com/user-attachments/assets/f93d0edf-6f37-4dbc-9bad-d19cc11c8166" />

  <img width="850" height="733" alt="image" src="https://github.com/user-attachments/assets/efd403e3-3463-4483-8425-4ae33432ca4a" />

- Explanation

  Membuat file `named.conf` dalam direktori `/myscripts/dns` dengan isi file konfigurasi berikut:
  ```
  options {
    directory "/myscripts/dns";
    listen-on { 0.0.0.0; };
    listen-on-v6 { none; };
    listen-on port 53 { any; };

    allow-query { any; };
    recursion yes;
  };

    zone "localhost" {
        type master;
        file "db.localhost";
    };
    
    zone "parkov.com" {
        type slave;
        masters { 10.155.0.50; };
        file "slaves/db.parkov.com";
    };
    
    zone "paskarov.com" {
        type slave;
        masters { 10.155.0.50; };
        file "slaves/db.paskarov.com";
    };
    ```
    Selanjutnya membuat direktori baru dengan command `mkdir -p /myscripts/dns/slaves` yang akan diisi oleh file hasil transfer dari DNS Master. Karena DNS Slave pada node Verso dikonfigurasi untuk menerima transfer zona dari DNS Master Renoir menggunakan alamat IP `10.155.0.50` Sehingga direktori `/myscripts/dns/slaves` akan otomatis terisi file zona hasil replikasi dari master.
    
    Kemudian membuat file `start_dns.sh` dengan isi file konfigurasi seperti berikut:
    ```
    #!/bin/bash
    
    pkill named
    named -c /myscripts/dns/named.conf -g
    
    netstat -tulpn | grep 53
    
    nslookup parkov.com 127.0.0.1
    nslookup paskarov.com 127.0.0.1
    ```
    Baru kita jalankan DNS Slaves nya dalam tmux dengan command bash `/myscripts/dns/start_dns.sh` dan lakukan pengecekan dengan cara `ps aux | grep named`. Serta melakukan pengecekan file hasil transfer dengan menggunakan command `ls /myscripts/dns/slaves`.

    

<br>

## Soal 7

> Konfigurasikan **Sicilian** agar berfungsi sebagai **web server nginx** yang akan menyajikan [halaman berikut](https://drive.google.com/file/d/1eX0ZjRKprx8T34XFAssrpc7ZE1j6Jv0j/view). Konfigurasikan juga agar **Sicilian** bisa menyimpan custom access log ke file **/tmp/access.log** dan error log ke file **/tmp/error.log.**

> _Configure **Sicilian** to function as an **nginx web server**that will serve [this page](https://drive.google.com/file/d/1eX0ZjRKprx8T34XFAssrpc7ZE1j6Jv0j/view). Also, configure **Sicilian** to save custom access logs to **/tmp/access.log** and error logs to **/tmp/error.log.**_

**Answer:**

- Screenshot

  <img width="1285" height="941" alt="image" src="https://github.com/user-attachments/assets/afc4d3c0-695e-4054-bc1f-bbd33f738da4" />

  <img width="854" height="1018" alt="image" src="https://github.com/user-attachments/assets/f72be5c1-11be-40bf-8af9-0e21c321f840" />

  <img width="1895" height="126" alt="image" src="https://github.com/user-attachments/assets/79a9e61b-46fe-4d66-a63e-23e40fae89d4" />

- Explanation

  Membuat direktori baru dengan command berikut  `mkdir -p /myscripts/myconfig`, `mkdir -p /myscripts/myweb`, dan `mkdir -p /myscripts/mylogs`. Selanjutnya membuat file `sicilian.html` dalam direktori `/myscripts/myweb` sesuai dengan isi file yang diminta oleh soal. Lalu membuat file `nginx.conf` dalam direktori `/myscripts/myconfig` dengan konfigurasi berikut:
  ```
  user www-data;
  worker_processes auto;
  worker_cpu_affinity auto;
  pid /tmp/nginx.pid;
  error_log /tmp/error.log;
    
  events {
      worker_connections 768;
  }
    
  http {
      server {
          listen 80;
          server_name _;
    
          root /myscripts/myweb;
          index sicilian.html;
    
          access_log /tmp/access.log;
          error_log /tmp/error.log;
    
          location / {
               try_files $uri $uri/ =404;
           }
       }
   }
   ```
   Kemudian membuat file start_http.sh dalam direktori /myscripts/myconfig dengan konfigurasi berikut:
   ``` 
   #!/bin/bash
    
   service nginx restart
    
   nginx -c /myscripts/myconfig/nginx.conf -g 'daemon off;'
    
   service nginx status
   ```
   Selanjutnya edit default nginx dengan command ini `nano /etc/nginx/sites-available/default` dengan konfigurasi berikut:
   ```
   server {
      listen 80;
      server_name paskarov.com;
    
      root /myscripts/myweb;
      index sicilian.html;
    
      access_log /tmp/access.log;
      error_log /tmp/error.log;
    
        location / {
            try_files $uri $uri/ =404;
        }
    }
    ```
    Baru melakukan start ngix dengan command `bash /myscripts/myconfig/start_http.sh`. Untuk melakukan pengecekan html bekerja atau tidak, bisa menambahkan node netics-pc-desktop. Selanjutnya membuat ip addr pada node tersebut dengan menggunakan command `ip addr add 10.155.0.233/25 dev eth0`. Kemudian masuk ke dalam chromium dan memasukkan `url http://10.155.0.231/`. Juga bisa melakukan pengecekan dengan command `curl http://10.155.0.231/` dalam console node sicilian. Langkah terakhir melakukan pengecekan access dengan command `cat /tmp/access.log`.

<br>

## Soal 8

> Buatlah custom access log ke file **/tmp/access.log.** Untuk keperluan logging, gunakan format log seperti di bawah:
> - Tanggal dan waktu akses dalam format standar log.
> - Nama node yang sedang diakses.
> - Alamat IP klien yang mengakses website.
> - Metode HTTP dan URI yang diakses oleh klien.
> - Status respons HTTP yang diberikan oleh server.
> - Jumlah byte yang dikirimkan dalam respons.
> - Waktu yang dihabiskan oleh server untuk menangani permintaan.> 
> - Contoh format log yang sesuai:  
[01/Oct/2024:11:30:45 +0000] Jarkom Node Sicilian Access from 192.168.1.15 using method "GET /resep/bayam HTTP/1.1" returned status 200 with 2567 bytes sent in 0.038 seconds

> _Webserver: Create a custom access log to the file **/tmp/access.log.** For logging purposes, use the log format shown below:_
> - _The date and time of access in standard log format._
> - _The name of the node being accessed._
> - _The IP address of the client accessing the website._
> - _The HTTP method and URI accessed by the client._
> - _The HTTP response status returned by the server._
> - _The number of bytes sent in the response._
> - _The time spent by the server processing the request._
> - _Example of appropriate log format:  
[01/Oct/2024:11:30:45 +0000] Jarkom Node Sicilian Access from 192.168.1.15 using method "GET /resep/bayam HTTP/1.1" returned status 200 with 2567 bytes sent in 0.038 seconds_

**Answer:**

- Screenshot

  <img width="1878" height="207" alt="image" src="https://github.com/user-attachments/assets/1f784185-a42d-4cac-92ce-f3e101456fe7" />

- Explanation

  Menambahkan konfigurasi baru dalam file `nginx.conf` sehingga konfigurasinya menjadi sebagai berikut:
  ```
  user www-data;
  worker_processes auto;
  pid /tmp/nginx.pid;
  error_log /tmp/error.log;
  
  events {
      worker_connections 768;
  }
  
  http {
      log_format custom_format '[$time_local] Jarkom Node Sicilian Access from $remote_addr '
                               'using method "$request" returned status $status '
                               'with $body_bytes_sent bytes sent in $request_time seconds';
  
      server {
          listen 80;
          server_name _;
  
          root /myscripts/myweb;
          index sicilian.html;
  
          access_log /tmp/access.log custom_format;
          error_log /tmp/error.log;
  
          location / {
              try_files $uri $uri/ =404;
          }
      }
  }
  ```
  Selanjutnya berhentikan nginx dengan command `pkill nginx`. Kemudian jalankan ulang nginx dengan command `nginx -c /myscripts/myconfig/nginx.conf`. Lalu tes akses ke web server dengan command `curl http://10.155.0.231/`. Dan terakhir cek status access.log nya telah berubah sesuai format atau belum dengan command `cat /tmp/access.log`.

<br>

## Soal 9

> Konfigurasikan juga **Slav** agar berfungsi sebagai **web server nginx** yang menyajikan [halaman berikut](https://drive.google.com/file/d/1h8ik1Zcubntp0dvHt9NHYqSZLSTG6FuZ/view) dan **hanya** bisa diakses melalui port **8000** dan **8888.**

> _Configure **Slav** to function as an **nginx web server** that serves [this page](https://drive.google.com/file/d/1h8ik1Zcubntp0dvHt9NHYqSZLSTG6FuZ/view?usp=drive_link) and is **only** accessible via ports **8000** and **8888.**_

**Answer:**

- Screenshot

  <img width="837" height="1016" alt="image" src="https://github.com/user-attachments/assets/98f05bf6-8b00-445b-a652-a504a7507008" />
  
  <img width="1272" height="916" alt="image" src="https://github.com/user-attachments/assets/e98a822a-2669-42cc-a10f-3c58a5b52f5d" />

  <img width="810" height="1015" alt="image" src="https://github.com/user-attachments/assets/96f06691-1e99-493a-9f93-7d7e3548852d" />

  <img width="1267" height="942" alt="image" src="https://github.com/user-attachments/assets/eaa3216d-8d10-4e2c-b9f6-5626b81af752" />

- Explanation

  Membuat direktori baru dengan command berikut `mkdir -p /myscripts/myconfig`, `mkdir -p /myscripts/myweb`, dan `mkdir -p /myscripts/mylogs`. Selanjutnya membuat file slav.html dalam direktori `/myscripts/myweb` sesuai dengan isi file yang diminta oleh soal. Lalu membuat file `nginx.conf` dalam direktori `/myscripts/myconfig` dengan konfigurasi berikut:
  ```
  user www-data;
  worker_processes auto;
  pid /tmp/nginx.pid;
  
  events {
      worker_connections 768;
  }
  
  http {
  
      log_format custom_format '[$time_local] Jarkom Node Slav Access from $remote_addr '
                               'using method "$request" returned status $status '
                               'with $body_bytes_sent bytes sent in $request_time seconds';
  
      server {
          listen 80;
          server_name _;
  
          return 403;
      }

      server {
          listen 8000;
          server_name parkov.com;
  
          root /myscripts/myweb;
          index slav.html;
  
          access_log /tmp/access.log custom_format;
          error_log /tmp/error.log;
  
          location / {
              try_files $uri $uri/ =404;
          }
      }
  
      server {
          listen 8888;
          server_name parkov.com;
  
          root /myscripts/myweb;
          index slav.html;
  
          access_log /tmp/access.log custom_format;
          error_log /tmp/error.log;
  
          location / {
              try_files $uri $uri/ =404;
          }
      }
  }
  ```
  Kemudian membuat file `start_http.sh` dalam direktori `/myscripts/myconfig` dengan konfigurasi berikut:
  ```
  #!/bin/bash

  service nginx restart
  
  nginx -c /myscripts/myconfig/nginx.conf -g 'daemon off;'

  service nginx status
  ```
  Selanjutnya edit default nginx dengan command ini `nano /etc/nginx/sites-available/default` dengan konfigurasi berikut:
  ```
  server {
      listen 80;
      server_name parkov.com;
  
      root /myscripts/myweb;
      index slav.html;
  
      access_log /tmp/access.log;
      error_log /tmp/error.log;
  
      location / {
  
          try_files $uri $uri/ =404;
      }
  }
  ```
  Baru melakukan start ngix dengan command `bash /myscripts/myconfig/start_http.sh`. Untuk melakukan pengecekan html bekerja atau tidak, bisa menambahkan node netics-pc-desktop dengan masuk ke dalam chromium dan memasukkan `url http://10.155.1.2:8000` dan `url http://10.155.1.2:8888`. Langkah terakhir melakukan pengecekan dengan command `curl -v http://10.155.1.2:8000` dan `curl -v http://10.155.1.2:8888`. Selanjutnya kita juga bisa melihat access dengan command `cat /tmp/access.log`.


<br>

## Soal 10

> Untuk memudahkan akses, buatlah satu domain lagi dengan nama **openings.com** yang mengarah ke **Petrov.** Lalu, konfigurasikan juga **Petrov** sebagai **Reverse Proxy** yang akan melakukan forward request ke server yang sesuai berdasarkan URL profile yang diminta oleh klien dengan ketentuan sebagai berikut:
> - Request untuk “openings.com/**sicilian**” harus dialihkan ke web server **Sicilian.**
> - Request untuk “openings.com/**slav**” harus dialihkan ke web server **Slav.**

> _To facilitate access, create another domain with the name **openings.com** that points to **Petrov.** Then, configure **Petrov** as a **Reverse Proxy** that will forward requests to the appropriate server based on the profile URL requested by the client with the following conditions:_
> - _Requests for “openings.com/**sicilian**” must be forwarded to web server **Sicilian.**_
> - _Request for “openings.com/**slav**” must be forwarded to web server **Slav.**_

**Answer:**

- Screenshot

  <img width="1919" height="1020" alt="image" src="https://github.com/user-attachments/assets/248a5b09-56d6-46a0-9cd6-beab5d17a131" />

  <img width="914" height="477" alt="image" src="https://github.com/user-attachments/assets/fa341c01-83ee-42a1-b474-bec750caee1d" />

  <img width="941" height="501" alt="image" src="https://github.com/user-attachments/assets/36075a0f-558d-456d-a42b-a997200d9697" />

  <img width="899" height="499" alt="image" src="https://github.com/user-attachments/assets/3ef12246-dbe1-4c20-aae1-9bb17a41c021" />

- Explanation

  **#DNS MASTER#**

  Menambah kan konfigurasi zone `openings.com` dalam file `named.conf` dengan konfigurasi berikut:
  ```
  zone "openings.com" {
      type master;
      file "db.openings.com";
  };
  ```
  Kemudian menambah file baru `db.openings.com` dalam direktori `/myscripts/dns` dengan konfigurasi berikut ini:
  ```
  $TTL 86400
  @   IN  SOA ns1.openings.com. admin.openings.com. (
          1       ; Serial
          3600    ; Refresh
          1800    ; Retry
          604800  ; Expire
          86400 ) ; Minimum TTL
  
      IN  NS  ns1.openings.com.
  ns1 IN  A   10.155.0.50        ;
  @   IN  A   10.155.0.42        ;

  ```
  Terakhir restart DNS Master dengan command `bash /myscripts/dns/start_dns.sh` dalam tmux.
  
  **#DNS SLAVES#**
  
  Menambah kan konfigurasi zone `openings.com` dalam file `named.conf` dengan konfigurasi berikut:
  ```
  zone "openings.com" {
      type slave;
      masters { 10.155.0.50; };
      file "slaves/db.openings.com";
  };
  ```
  Kemudian restart DNS Slave dengan command `bash /myscripts/dns/start_dns.sh` dalam tmux.
  
  **#PETROV#**
  
  Membuat direktori baru dengan command `mkdir -p /myscripts/myconfig`. Lalu membuat file baru `nginx.conf` dalam direktori tersebut dengan konfigurasi berikut:
  ```
  user www-data;
  worker_processes auto;
  pid /tmp/nginx.pid;
  
  events {
      worker_connections 768;
  }
  
  http {
  
      log_format custom_format '[$time_local] Reverse Proxy Access from $remote_addr '
                               'request "$request" returned status $status '
                               'with $body_bytes_sent bytes in $request_time seconds';
      access_log /tmp/access.log custom_format;
      error_log /tmp/error.log;
  
      server {
          listen 80;
          server_name openings.com;
  
          location /sicilian/ {
              proxy_pass http://10.155.0.231:80/;
          }
  
          location /slav8000/ {
              proxy_pass http://10.155.1.2:8000/;
          }
  
          location /slav8888/ {
              proxy_pass http://10.155.1.2:8888/;
          }
      }
  }
  ```
  Setelah itu mematikan nginx dengan command `pkill nginx`. Kemudian restart nginx dengan `nginx -c /myscripts/myconfig/nginx.conf`. Terakhir lakukan pengecekan `curl http://openings.com/sicilian`, `curl http://openings.com/slav8000`, dan `curl http://openings.com/slav8888`.

<br>

## Soal 11

> Tambahkan juga konfigurasi agar request untuk “openings.com/**random**” akan mengalihkan request ke webserver **Sicilian** dan **Slav** dengan algoritma _round-robin_.

> _Additionally, configure requests for "openings.com/**random**" to be redirected to the **Sicilian** and **Slav** web servers using a round-robin algorithm._

**Answer:**

- Screenshot

  `Put your screenshot in here`

- Explanation

  `Put your explanation in here`

<br>

## Soal 12

> Anatoly Parkov berencana untuk melakukan ekspansi secara besar-besaran. Maka dari itu, hapus seluruh konfigurasi Static Routing dan ubah agar seluruh router menggunakan Dynamic Routing. Gunakan protokol RIP!

> _Anatoly Parkov plans to perform a great expansion. Therefore, remove all Static Routing configurations and configure all routers to use Dynamic Routing. Use the RIP protocol!_

**Answer:**

- Screenshot

  <img width="1684" height="531" alt="image" src="https://github.com/user-attachments/assets/7fb4b079-ee37-4995-bee7-2ff885f09acd" />


- Explanation
  Langkah pertama yang harus dilakukan ialah melakukan penghapusan setiap static routing yang ada pada setiap router dengan command `ip route del <NID>/<Netmask> via <IP Address gateway>`. Kemudian melakukan dynamic routing seperti pada langkah-langkah berikut ini:

  **Router Smith-Morra**
  Membuat dynamic routing terlebih dahulu sesuai dengan langkah-langkah dalam modul 4 hingga exit
  
  **Router Lucena, Zwischenzug, Zugzwang, dan Fianchetto**
  
  Membuat dynamic routing terlebih dahulu sesuai dengan langkah-langkah dalam modul 4 di semua router kemudian exitnya dilakukan bergantian.

  **Router Smith-morra**

  Menjalankan command berikut ini untuk melakukan forwarding internet ke seluruh router:
  ```
  iptables -t nat -A POSTROUTING -o eth3 -j MASQUERADE
  echo 1 > /proc/sys/net/ipv4/ip_forward
  ```
  **Router Lucena, Zwischenzug, Zugzwang, dan Fianchetto**
  ```
  ping google.com
  ```

<br>

## Soal 13

> Untuk meningkatkan keamanan, konfigurasikan firewall **Smith-Morra** untuk melakukan pembatasan koneksi SSH ke server DNS. Drop semua packet SSH yang berasal dari seluruh client yang memiliki tujuan ke **Caro-Kann** atau **Alekhine.**

> _To increase security, configure the **Smith-Morra** firewall to restrict SSH connections to the **DNS server.** Drop all SSH packets from all clients destined for **Caro-Kann** or **Alekhine.**_

**Answer:**

- Screenshot

  <img width="1717" height="544" alt="image" src="https://github.com/user-attachments/assets/a53f7353-0a3a-49e5-bb1d-1515328792e9" />

- Explanation

  Menjalankan command rule berikut pada router `Smith-morra` :
  ```
  iptables -A FORWARD -p tcp -d 10.155.0.50 --dport 22 -j DROP
  iptables -A FORWARD -p tcp -d 10.155.0.62 --dport 22 -j DROP
  ```
  Kemudian melakukan pengecekan pada beberapa node lain, seperti node `Petrov` dan `Slav` dengan command berikut:
  ```
  ssh root@10.155.0.50
  ssh root@10.155.0.62
  ```
  Bisa juga dengan menggunakan command berikut:
  ```
  nc -vz 10.155.0.50 22
  nc -vz 10.155.0.62 22
  ```

<br>

## Soal 14

> Nampaknya, web server juga manusia sehingga hanya ingin bekerja di hari kerja. Maka dari itu, semua client hanya bisa mengakses **Sicilian** dan **Slav** pada hari Senin-Jumat pada pukul 09:00-17:00.

> _Apparently, web servers are humans too, so they only want to work on weekdays. Therefore, all clients can only access **Sicilian** and **Slav** on Monday through Friday, 9:00 AM to 5:00 PM._

**Answer:**

- Screenshot

  <img width="865" height="694" alt="image" src="https://github.com/user-attachments/assets/3f3424b8-2df7-4c05-b091-a80fea8aa9e5" />

  <img width="1685" height="634" alt="Cuplikan layar 2025-12-07 222107" src="https://github.com/user-attachments/assets/a1caeb28-8c27-4599-91ac-f1f115ff5603" />

- Explanation
  
  Melakukan firewall configure sesuai soal di setiap router yang ada dalam topologi dengan command berikut:
  ```
  apt-get update
  apt-get install iptables -y

  iptables -F FORWARD
  iptables -A FORWARD -m state --state ESTABLISHED,RELATED -j ACCEPT

  iptables -A FORWARD -p tcp -d 10.155.0.231 --dport 80 -m time --timestart 09:00 --timestop 17:00 --weekdays Mon,Tue,Wed,Thu,Fri --kerneltz -j ACCEPT
  iptables -A FORWARD -p tcp -d 10.155.1.2 --dport 8000 -m time --timestart 09:00 --timestop 17:00 --weekdays Mon,Tue,Wed,Thu,Fri --kerneltz -j ACCEPT
  iptables -A FORWARD -p tcp -d 10.155.1.2 --dport 8888 -m time --timestart 09:00 --timestop 17:00 --weekdays Mon,Tue,Wed,Thu,Fri --kerneltz -j ACCEPT

  iptables -A FORWARD -p tcp -d 10.155.0.231 --dport 80 -j DROP
  iptables -A FORWARD -p tcp -d 10.155.1.2 --dport 8000 -j DROP
  iptables -A FORWARD -p tcp -d 10.155.1.2 --dport 8888 -j DROP
  ```
  Kemudian melakukan pengecekan rule yang telah dibuat dengan command berikut `iptables -L FORWARD -n -v --line-numbers`. Langkah berikutnya melakukan pengecekan akses web server pada client lainnya dengan command `curl -I http://10.155.1.2:8000` dan `curl -I http://10.155.0.231`.

<br>

## Soal 15

> Terakhir, Gerry Paskarov berpesan untuk selalu melakukan logging, sehingga konfigurasikan fitur logging untuk melakukan log terhadap seluruh paket yang di-DROP pada firewall **Smith-Morra.**
> _Finally, Gerry Paskarov advises to always perform logging, so configure a logging feature to log all packets dropped on the **Smith-Morra** firewall._

**Answer:**

- Screenshot

  `Put your screenshot in here`

- Explanation

  `Put your explanation in here`

<br>
  
## Problems
   Cukup sulit nomor awal daripada nomor akhir
## Revisions (if any)
   Revisi nomor 11 dan 15
