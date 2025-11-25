[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/oYnIPZ_t)
| Name           | NRP        | Kelas     |
| ---            | ---        | ----------|
| Shafira Nauraishma Zahida | 5025241235 | Jarkom B |



## Put your topology config image here!

<img width="867" height="418" alt="image" src="https://github.com/user-attachments/assets/e1837ba6-896d-4e93-bf6c-e6299212df66" />


## Put your GNS3 Project file here!

https://drive.google.com/drive/folders/12xlAHhu73jsXdIJaDVMdjrgR-m8ufNmf?usp=sharing

<br>

## Soal 1

> Lakukan subnetting pada topologi diatas menggunakan metode VLSM: [Referensi](https://github.com/arsitektur-jaringan-komputer/Modul-Jarkom/tree/master/Modul-4/Subnetting#2-vlsm-variable-length-subnet-masking)  
*Cantumkan juga tabel dan diagram pembagian subnet pada laporan praktikum*.


> _Subnet the topology above using the VLSM method: [Reference](https://github.com/arsitektur-jaringan-komputer/Modul-Jarkom/tree/master/Modul-4/Subnetting#2-vlsm-variable-length-subnet-masking)_  
_Also include the subnet table and diagram in the lab report._

**Answer:**

- Screenshot

  <img width="1920" height="1080" alt="10 1550 023 (2)" src="https://github.com/user-attachments/assets/9cdf6428-93a0-4ed5-abca-08d81c3774f7" />

  <img width="940" height="443" alt="image" src="https://github.com/user-attachments/assets/6ad17e22-0560-4c7e-8eaf-0b112f6249cf" />


- Explanation

  Melakukan subnetting berdasarkan setiap berdasarkan metode classful. Selain itu kita juga harus memperhatikan untuk jumlah IP useable yang diketahui dalam soal. Sehingga pembagian IP nya akan menjadi seperti gambar diatas. Perhitungan lebih lanjut dapat diakses dalam link berikut
  `https://docs.google.com/spreadsheets/d/1HO8XVlxh9uaAOe-dsUY8ZkYZiQlYk5HUE8bBPvEpZpo/edit?usp=sharing`

<br>

## Soal 2

> Buatlah agar router-2 dapat melakukan koneksi ke internet. [Dapat menggunakan static routing].

> _Make sure router-2 can connect to the internet. [Can use static routing]._

**Answer:**

- Screenshot

  `<img width="661" height="362" alt="Screenshot 2025-11-21 164417" src="https://github.com/user-attachments/assets/6d635374-9655-49f2-a29a-240008a8aa23" />

- Explanation

  Menjalankan command `ping google.com` dalam Router2

<br>

## Soal 3

> Setelah mengimplementasi subnetting, buatlah agar seluruh topologi dapat terhubung. Lakukan Dynamic Routing pada topologi tersebut.
*Pastikan seluruh node yang ada dapat mengakses internet*.

> _After implementing subnetting, ensure the entire topology is connected. Perform dynamic routing on the topology._  
_Ensure all existing nodes can access the internet._

**Answer:**

- Screenshot

  <img width="1286" height="656" alt="Screenshot 2025-11-21 193750" src="https://github.com/user-attachments/assets/a1ecad5c-1a52-490c-b440-e4b27f044994" />
  
  <img width="1279" height="429" alt="Screenshot 2025-11-21 193855" src="https://github.com/user-attachments/assets/a0b683c2-c643-4b78-b483-44b190fe5ce6" />
  
  <img width="1341" height="437" alt="Screenshot 2025-11-21 193826" src="https://github.com/user-attachments/assets/66b7f7a3-ba98-46c7-9d89-6d8b851f8083" />
  
  <img width="707" height="591" alt="image" src="https://github.com/user-attachments/assets/af27348d-1d6d-4119-9bc0-b7a86400dfd0" />


- Explanation

  **Router2**
  ```
   apt-get update
   apt-get install iptables -y
  ```
  buat dynamic routing dulu sesuai dengan langkah-langkah dalam modul 4 sampai exit
  
  **Router1,3,4,5**
  
  dynamic routing semua router kemudian exitnya dilakukan bergantian
  `nano /etc/resolv.conf`
  ```
  nameserver 8.8.8.8 
  nameserver 1.1.1.1
  ```
  
  **Router2**
  ```
  iptables -t nat -A POSTROUTING -o eth3 -j MASQUERADE
  echo 1 > /proc/sys/net/ipv4/ip_forward
  ```
  
  **Router1,3,4,5**
  
  `ping google.com` (berhasil lanjut ke node)
  
  Semua Node
  set semua node menggunakan `ip add route default via <IP Router yang tersambung node>`
  `nano /etc/resolv.conf`
  ```
  nameserver 8.8.8.8
  nameserver 1.1.1.1
  ```
  `ping google.com`


<br>

## Soal 4

> Lakukan setup web server dengan file html di attachment berikut: [ Attachment ](https://drive.google.com/file/d/199qwfTNJCkxDV7mdO-MsaDdApkmKsnAG/view?usp=sharing)  menggunakan nginx pada “Web-Server-1” dan “Web-Server-2”.  
*Config dibebaskan kepada praktikkan dengan catatan menggunakan port 80*.

> _Set up a web server with the HTML file in the following attachment: [ Attachment ](https://drive.google.com/file/d/199qwfTNJCkxDV7mdO-MsaDdApkmKsnAG/view?usp=sharing) using nginx on “Web-Server-1” and “Web-Server-2”._
_Configuration is free to practice, but note that it uses port 80._

**Answer:**

- Screenshot

  <img width="1283" height="632" alt="Screenshot 2025-11-21 195910" src="https://github.com/user-attachments/assets/c59863ec-17a9-4bb5-8b70-e420a0a5d339" />


- Explanation

  **Web-Server 1 dan Web-Server-2**
  
  `apt-get install nginx -y`

  ```
  mkdir -p /var/www/html
  nano /var/www/html/index.html
  chown -R www-data:www-data /var/www/html
  chmod -R 755 /var/www/html
  ```
  
  `sudo nano /etc/nginx/sites-available/default`
  ```
  server {
      listen 80 default_server;
      listen [::]:80 default_server;
  
      root /var/www/html;
      index index.html;
  
      server_name _;
  
      location / {
          try_files $uri $uri/ =404;
      }
  }
  ```
  ```
  service nginx stop
  service nginx restart
  ```
  `curl http://localhost`


<br>

## Soal 5

> Kalian diminta untuk melakukan drop semua paket TCP yang masuk  ke subnet HR dengan port 1337 dan 4444. Lakukan testing dengan netcat.

> _You are asked to drop all incoming TCP packets to the HR subnet with ports 1337 and 4444. Test with netcat._

**Answer:**

- Screenshot

  <img width="553" height="376" alt="Screenshot 2025-11-21 203255" src="https://github.com/user-attachments/assets/40a63d86-1250-4eb5-8144-a2d37bd018bd" />


- Explanation
  
  **Router 5**
  ```
  apt-get update
  apt-get install iptables -y
  ```
  ```
  iptables -A FORWARD -p tcp -d 10.155.2.0/23 --dporroot
  iptables -A FORWARD -p tcp -d 10.155.2.0/23 --dporroot
  iptables -L FORWARD -n -v
  ```
  ```
  nc -vz 10.155.2.2 1337
  nc -vz 10.155.2.2 4444
  ```
  ```
  nc -vz 10.155.2.252 1337
  nc -vz 10.155.2.252 4444
  ```

<br>

## Soal 6

> Lakukan pembatasan sehingga koneksi SSH pada semua Web Server hanya dapat dilakukan oleh user yang berada pada node IT-PC-1, IT-PC-2, dan IT-PC-3. 

> _Implement restrictions so that SSH connections to all Web Servers can only be made by users on nodes IT-PC-1, IT-PC-2, and IT-PC-3._

**Answer:**

- Screenshot

  <img width="1214" height="421" alt="Screenshot 2025-11-21 212101" src="https://github.com/user-attachments/assets/be2d3b2c-2961-4dd4-ba39-c8d0dcda946c" />


- Explanation

  **Web-Server 1 dan Web-Server-2**
  ```
  apt-get update
  apt-get install iptables -y
  ```
  
  `apt-get install openssh-server -y`

  ```
  ls /usr/sbin/sshd
  /usr/sbin/sshd
  ```
  
  `ss -tlnp | grep 22`

  ```
  iptables -A INPUT -p tcp --dport 22 -j DROP
  iptables -I INPUT 1 -p tcp -s 10.155.0.130 --dport 22 -j ACCEPT
  iptables -I INPUT 2 -p tcp -s 10.155.0.180 --dport 22 -j ACCEPT
  iptables -I INPUT 3 -p tcp -s 10.155.0.205 --dport 22 -j ACCEPT
  ```
  
  **IT-PC-1, IT-PC-2, dan IT-PC-3**
  
  `nc -vz 10.155.0.66 22`
  
  **HR-PC-1 dan HR-PC-2**
  
  `nc -vz 10.155.0.66 22`

<br>

## Soal 7

> Semua subnet hanya dapat mengakses semua DB-Server pada port 80 dan 443 (DB-Server-1 dan DB-Server-2) pada hari Senin-Sabtu, pukul 07:00- 22:00.

> _All subnets can only access all DB-Servers on ports 80 and 443 (DB-Server-1 and DB-Server-2) on Monday-Saturday, 07:00-22:00._

**Answer:**

- Screenshot

  <img width="1337" height="666" alt="image" src="https://github.com/user-attachments/assets/f1caf5ad-75b2-414d-a031-d57d1db46363" />


- Explanation

  **Router 3**
  ```
  apt-get update
  apt-get install iptables -y
  ```
  
  ```
  iptables -A FORWARD -p tcp -d 10.155.0.18 --dport 80 \
      -m time --timestart 07:00 --timestop 22:00 \
      --weekdays Mon,Tue,Wed,Thu,Fri,Sat -j ACCEPT

  iptables -A FORWARD -p tcp -d 10.155.0.18 --dport 443 \
      -m time --timestart 07:00 --timestop 22:00 \
      --weekdays Mon,Tue,Wed,Thu,Fri,Sat -j ACCEPT
  ```
  
  ```
  iptables -A FORWARD -p tcp -d 10.155.0.98 --dport 80 \
      -m time --timestart 07:00 --timestop 22:00 \
      --weekdays Mon,Tue,Wed,Thu,Fri,Sat -j ACCEPT
  
  iptables -A FORWARD -p tcp -d 10.155.0.98 --dport 443 \
      -m time --timestart 07:00 --timestop 22:00 \
      --weekdays Mon,Tue,Wed,Thu,Fri,Sat -j ACCEPT
  ```
  
  ```
  iptables -A FORWARD -p tcp -d 10.155.0.18 --dport 80 -j REJECT
  iptables -A FORWARD -p tcp -d 10.155.0.18 --dport 443 -j REJECT
  ```
  
  ```
  iptables -A FORWARD -p tcp -d 10.155.0.98 --dport 80 -j REJECT
  iptables -A FORWARD -p tcp -d 10.155.0.98 --dport 443 -j REJECT
  ```

  `iptables -L FORWARD -n --line-numbers`
  
  **IT-PC-1, IT-PC-2, dan IT-PC3**
  ```
  curl http://10.155.0.18/
  curl http://10.155.0.98/
  ```

<br>

## Soal 8

> Kemudian, buat agar “Web-Server-1” dan “Web-Server-2” hanya memperbolehkan traffic bertipe HTTP.

> _Then, make sure that “Web-Server-1” and “Web-Server-2” only allow HTTP type traffic._

**Answer:**

- Screenshot

  <img width="1358" height="659" alt="image" src="https://github.com/user-attachments/assets/954aa0b4-b879-4681-804a-b6ecd3e931b1" />


- Explanation

  **Web-Server 1 dan Web-Server-2**
  ```
  iptables -F INPUT
  iptables -A INPUT -i lo -j ACCEPT
  iptables -A INPUT -p tcp --dport 80 -m state --state NEW,ESTABLISHED -j ACCEPT
  iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT
  iptables -A INPUT -p tcp -j DROP
  ```
  
  **IT-PC-1, IT-PC-2, dan IT-PC-3**
  ```
  nc -vz 10.155.0.66 80
  nc -vz 10.155.0.66 22 
  nc -vz 10.155.0.66 443
  ```
  ```
  nc -vz 10.155.0.34 80
  nc -vz 10.155.0.34 22 
  nc -vz 10.155.0.34 443
  ```

<br>

## Soal 9

> Pilih salah satu Subnet dan lakukan blokir terhadap semua request protokol ICMP (ping) dari luar subnet terhadap subnet tersebut.

> _Select one of the Subnets and block all ICMP protocol requests (ping) from outside the subnet to that subnet._

**Answer:**

- Screenshot

  <img width="1178" height="563" alt="image" src="https://github.com/user-attachments/assets/e11a8d9f-27a1-4a51-9560-426454a32413" />

  <img width="1133" height="335" alt="image" src="https://github.com/user-attachments/assets/4f3a54a7-ad41-445c-892b-4f3f0c240d59" />


- Explanation

  **Router 5**
  
  `iptables -A INPUT -p icmp ! -s 10.155.2.0/23 -d 10.155.2.0/23 -j DROP`
  
  **HR-PC-1**
  ```
  ping 10.155.2.252 
  ping 10.155.0.18
  ```

  **HR-PC-2**
  ```
  ping 10.155.2.2 
  ping 10.155.0.18
  ```

<br>

## Soal 10

> Konfigurasikan fitur logging untuk melakukan log terhadap seluruh paket yang di-DROP pada lalu lintas setiap node.

> _Configure the logging feature to log all dropped packets on each node's traffic._

**Answer:**

- Screenshot

  `Put your screenshot in here`

- Explanation

  `Put your explanation in here`

<br>
  
## Problems

## Revisions (if any)
