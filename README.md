# Music Streaming API 🎵

Backend API cho ứng dụng nghe nhạc trực tuyến, xây dựng bằng Spring Boot.

 🛠 Công nghệ sử dụng
- Java 17 / Spring Boot 3
- Database: MySQL
- Security:** Spring Security + JWT (Authentication/Authorization)
- Cloud Storage: Cloudinary (Lưu trữ bài hát, ảnh bìa)
- Tool:** Maven, Docker

 ✨ Tính năng chính
- Đăng ký/Đăng nhập (Phân quyền User/Artist/Admin)
- Upload nhạc, quản lý Album (Tích hợp Cloudinary)
- Nghe nhạc, tạo Playlist
- Soft Delete dữ liệu để đảm bảo an toàn

## 🚀 Cài đặt
1. Clone dự án
2. Cấu hình file `application.yaml` (Database & Cloudinary Key)
3. Chạy lệnh `mvn spring-boot:run`
