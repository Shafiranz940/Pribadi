Perkembangan jaringan komputer menuntut adanya sistem routing yang efisien, dinamis, dan mampu menangani pertukaran data antar jaringan dengan skala yang lebih luas. Pada jaringan dengan lebih dari satu router, penggunaan routing statis menjadi kurang efektif karena membutuhkan konfigurasi manual serta sulit beradaptasi terhadap perubahan topologi jaringan. Oleh karena itu, diperlukan sebuah protokol routing dinamis yang mampu melakukan pertukaran informasi rute secara otomatis.

Salah satu protokol routing dinamis yang banyak digunakan adalah Open Shortest Path First (OSPF). OSPF merupakan protokol routing berbasis link-state yang mampu menentukan jalur terbaik berdasarkan cost serta mendukung pembagian jaringan ke dalam beberapa area, dengan Area 0 sebagai backbone utama. Penggunaan OSPF memungkinkan jaringan untuk menjadi lebih terstruktur, skalabel, dan efisien dalam proses routing data.

Pada laporan ini dibahas implementasi routing OSPF Area 0 yang menghubungkan jaringan LAN dan beberapa link antar-router (backbone). Konfigurasi meliputi penentuan Router ID, pendefinisian subnet LAN, serta subnet backbone yang menghubungkan router satu dengan router lainnya. Dengan konfigurasi ini, setiap router dapat saling bertukar informasi routing secara otomatis sehingga komunikasi antar jaringan dapat berjalan dengan baik.

Melalui percobaan dan konfigurasi yang dilakukan, diharapkan dapat dipahami konsep dasar OSPF, fungsi backbone network, serta peran OSPF dalam membangun jaringan yang handal dan terintegrasi. Berikut ini implementasi yang telah saya lakukan pada case 6:

1. Pertama kita membuat 3 LAN(subnet), yaitu subnet untuk ruangan dosen, kelas IF-105, dan kelas IF-106. Dimana setiap subnet memiliki dhcp server masing-masing yang memiliki pool ip address masing-masing seperti dalam panduan case 6 dan dua image netics-pc. Kemudian dilakukan pengecekan routing antara dhcp server dengan netics-pc untuk memastikan dhcp server dan netics-pc telah terhubung.

     **SUBNET RUANG DOSEN**
    
    <img width="669" height="607" alt="Screenshot 2025-12-15 205314" src="https://github.com/user-attachments/assets/2aa73d6d-28b4-4ebc-b67b-22fc32602392" />

    **SUBNET KELAS IF-105**
   
    <img width="665" height="578" alt="Screenshot 2025-12-15 210104" src="https://github.com/user-attachments/assets/673b4be6-d23d-4f7c-bb2d-bc7939956cc8" />

    **SUBNET KELAS IF-106**
   
    <img width="669" height="596" alt="Screenshot 2025-12-15 210914" src="https://github.com/user-attachments/assets/329ad5b4-eb9d-4226-983a-31d5ec1a5d01" />

2. Kemudian menambahkan node R1 agar netics-pc-3 pada subnet kelas IF-105 dapat berkomunikasi dengan netics-pc-1 ruangan dosen. Disini kita hanya menambahkan ip addr berdasarkan ip network id. Hal ini dibuktikan dengan melakukan ping dan ip address netics-pc-1 pada netics-pc-5
 
  <img width="531" height="440" alt="Screenshot 2025-12-15 211241" src="https://github.com/user-attachments/assets/a89b76fd-c3db-41a1-9961-caf27b53e100" />

  <img width="725" height="184" alt="Screenshot 2025-12-15 211429" src="https://github.com/user-attachments/assets/139b54e3-aaab-4c4e-8bbc-c25a13211289" />

  <img width="523" height="227" alt="Screenshot 2025-12-15 211439" src="https://github.com/user-attachments/assets/5a045a22-6cf6-4751-b7ac-165420817384" />

3. Selanjutnya merancang dan mengonfigurasi jaringan backbone dengan subnetting yang nantinya akan mengatur routing supaya semua subnet dapat saling terhubung. Caranya ialah menambahkan router tambahan, yaitu R1, R2, dan R3 yang terhubung dengan setipa switch subnet. Router-router tersebut kemudian dihubungkan dengan switch backbone yang memiliki subnet tersendiri seperti tertera pada panduan case 6. Setelah itu melakukan konfigurasi ip address pada setiap router dan melakukan static routing antar subnet di setiap routernya. Hal ini dibutuhkan agar setiap subnetnya dapat saling terhubung dan berkomunikasi. Terakhir melakukan pengecekan dengan menggunakan ping dan mtr antara netics-pc-5 dengan netics-pc1.

   <img width="1347" height="532" alt="Screenshot 2025-12-15 215958" src="https://github.com/user-attachments/assets/5c9a4833-5431-4fb8-8383-a3b179b53816" />
 
  <img width="1333" height="522" alt="Screenshot 2025-12-15 220056" src="https://github.com/user-attachments/assets/93253ca8-ddd1-4d15-beeb-2478ab8cc320" />

4. Dalam meningkatkan kehandalan konektifitas jaringan diperlukan adanya link redundasi. Jalur ini merupakan jalur kembar yang jika salah satu putus, maka jalur yang lain akan bisa menggantikan. Oleh karena itu, dalam konfigurasi ini menggunakan dynamic routing dengan OSPF dan RIP LAN. Pada proses dynamic routing dengan OSPF, hal pertama yang dilakukan ialah menghapus switch backbone dan menghubung setiap link ethernet antar router. Kemudian melakukan konfigurasi ip address sesuai dengan subnet yang telah diberikan pada panduan case 6. Selanjutnya melakukan start router FRR dengan membuat konfigurasi start_frr.sh, hal ini disebabkan proses yang kita lakukan tidak menggunakan jaringan. Oleh sebab itu, kita harus melakukan konfigurasi FRR secara manual. Kemudian kita masuk ke konfigurasi router untuk melakukan pengecekan apakah dynamic routing FRR yang telah kita buat sebelumnya sudah jalan atau belum dengan cara `show ip route` dan `show interface brief`.

   <img width="662" height="635" alt="Screenshot 2025-12-15 222251" src="https://github.com/user-attachments/assets/64f48297-f5d3-41f4-b6dd-1ac094c2ffed" />

   <img width="1313" height="658" alt="Screenshot 2025-12-15 222205" src="https://github.com/user-attachments/assets/41512936-a1c5-4612-abb3-11d05b71ac1d" />

   <img width="1304" height="494" alt="Screenshot 2025-12-15 221436" src="https://github.com/user-attachments/assets/9d02b0b9-c714-48ec-b51c-288dbc569c3b" />

5. Selanjutnya melakukan konfigurasi routing OSPF dalam `vtysh` setiap router sesuai dengan panduan dari case 6. Setelah konfigurasi routing OSPF selesai maka perlu dilakukan pengecekan dengan menggunakan show ip route. Kemudian melakukan cek konektifitas juga dengan melakukan ping dan mtr dari netics-pc-3 ke netics-pc-5 dan netics-pc-2 ke netics-pc-4.

   <img width="651" height="616" alt="Screenshot 2025-12-15 223634" src="https://github.com/user-attachments/assets/64f5e10d-a295-4003-8e36-32ee3948565d" />

   <img width="1328" height="655" alt="Screenshot 2025-12-15 223614" src="https://github.com/user-attachments/assets/d02f2eb0-3707-40c1-bcb7-d36f164a738e" />

   <img width="1326" height="367" alt="Screenshot 2025-12-15 224215" src="https://github.com/user-attachments/assets/30ab776d-12df-435a-a6b4-808e49a23b05" />

   <img width="1332" height="347" alt="Screenshot 2025-12-15 224055" src="https://github.com/user-attachments/assets/cad5a894-efb5-41d5-accb-58abedd095f8" />

7. Juga melakukan observasi perubahan pada mtr dari simulasi link putus dari R1 ke R3 dengan cara suspend link. Dimana terlihat di mtr bahwa terdapat waktu proses (latensi), tapi rute akan menyesuaikan. Selain itu juga melakukan pengecekan routingan, neighbour dari current router, dan statistik aktifitas OSPF pada masing-masing interface pada R1 dan R3

   <img width="1349" height="518" alt="Screenshot 2025-12-15 224331" src="https://github.com/user-attachments/assets/daa4b5ad-de97-4d6c-b6b6-84556d77a7ae" />

   <img width="1044" height="619" alt="Screenshot 2025-12-15 224750" src="https://github.com/user-attachments/assets/f12e8299-2f97-4c38-847f-405072e96336" />

   <img width="1078" height="634" alt="Screenshot 2025-12-15 224619" src="https://github.com/user-attachments/assets/d8e2a022-d846-42cd-b63e-8e62254389bd" />
