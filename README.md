**Komang Very Dharmasuta (2305551119)**

**I Made Gede Riyandhi Wiguna Putra (2305551170)**
#
# Projek_PBO-API
## SISTEM PEMBAYARAN SUBSCRIPTION SEDERHANA
Program ini dirancang untuk membuat backend API untuk Sistem Pembayaran Subscription yang sederhana, yang akan memberikan respons dalam format JSON. API ini mendukung berbagai metode permintaan yaitu :
**GET**  : untuk mendapatkan daftar atau detail data dari entitas.
**POST** : untuk membuat entitas baru.
**PUT** : untuk memperbarui data dari entitas yang ada.
**DELETE** : untuk menghapus data entitas.
Data yang digunakan dalam Sismtem Pembayaran Subscription Sederhana ini akan disimpan dalam database SQLite. Selain itu, API yang telah dibuat akan diuji menggunakan aplikasi Postman.

# Menggunakan Program
## Request Method GET
#### customer
GET /customers 

Untuk menampilkan daftar semua pelanggan menggunakan URL sebagai berikut : `http:/localhost:9119/customers`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.
 
![WhatsApp Image 2024-06-21 at 12 30 35_b4733f65](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/f0bf7c38-be21-4427-8c89-f8d26318bec2)
GET /customers/{id} 

Untuk menampilkan informasi pelanggan dan alamatnya menggunakan URL sebagai berikut : `http:localhost:9119/customers/1`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 14 33 16_6435c6d8](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/5adcc68d-9884-4234-a18f-af1314df49fe)
GET /customers/{id}/cards

Untuk menampilkan daftar kartu kredit/debit memiliki pelanggan menggunakan URL sebagai berikut : `http://localhost:9119/customers/1/cards`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 11 59 37_49e74359](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/c98cd669-7eab-4cc4-92b6-b412c8d79d67)

#### subscriptions
GET /subscriptions

Untuk menampilkan daftar semua subscriptions menggunakan URL sebagai berikut : `http://localhost:9119/subscriptions`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 12 30 02_ea2f73e8](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/4e98f279-ff60-496f-af1c-079b783c0c0a)
GET /subscriptions/{id} 

Untuk menampilkan informasi subscription, customer:id,first_name,last_name,  subscription_items:quantity,amount, item:id,name,price,type menggunakan URL sebagai berikut : `http://localhost:9119/items/5`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 14 49 28_7498a66c](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/344b0481-1be6-44e9-8762-14f40a3914a9)

#### items
GET /items

Untuk menampilkan daftar semua produk menggunakan URL sebagai berikut : `http://localhost:9119/items`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 12 28 53_b3bbdc42](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/a244e49b-759c-42d7-b8ac-1f56452dbf90)

GET /items/{id}

Untuk menampilkan informasi produk dapat menggunakan URL sebagai berikut : `http://localhost:9119/items/5`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 15 21 26_c141f095](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/fa3599d5-6e01-4b02-9707-d31ff642d44b) 
## Request Method POST
POST /customers 

Untuk menampilkan membuat pelanggan baru dapat menggunakan URL sebagai berikut : `http://localhost:9119/customer/8`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 40 30_08484494](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/ec7d3272-2857-49c7-b262-5af43c9c4d5d)
POST /items

Untuk menampilkan membuat item baru dapat menggunakan URL sebagai berikut : `http://localhost:9119/items/1`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 41 09_0acd4cf6](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/ec6fee7d-3166-4e38-a451-5a60888d83f4)

## Request Method PUT
PUT /customers/{id}

Untuk menampilkan memperbarui pelanggan dapat menggunakan URL sebagai berikut : `http://localhost:9119/customers/8`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 42 28_85f7daa1](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/772ecefe-a548-4513-816b-c4cf8b869d48)
PUT /items/{id}

Untuk menampilkan memperbarui item dapat menggunakan URL sebagai berikut : `http://localhost:9119/items/2`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 48 08_57747e1e](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/3a2ba6cf-a8e6-4794-a283-28c4408ffe3b)

## Request Method DELETE
DELETE /items/{id}

Untuk menampilkan mengubah status item is_active menjadi false menggunakan URL sebagai berikut : `http://localhost:9119/items/2`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 51 09_81991a93](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/a7fcae22-5ad9-44c2-9302-27304d70716e)
DELETE /customers/{id}/cards/{id}'

Untuk menampilkan menghapus informasi kartu kredit pelanggan jika is_primary bernilai false menggunakan URL sebagai berikut : `http://localhost:9119/customers/2/cards/1`

Hasil dari eksekusi URL di atas dapat dilihat sebagai berikut.

![WhatsApp Image 2024-06-21 at 00 53 53_da4e930e](https://github.com/riyandhiwiguna/Tugas_PBO2/assets/147158662/3b2b0a48-2b70-46db-a0a4-5aca6afc3b00)
 
# PENUTUP
SEKIAN PENJELASAN DARI KAMI SEMOGA MUDAH DI MENGERTI🙌🏻
