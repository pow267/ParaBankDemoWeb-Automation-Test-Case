# ParaBank-Automation-Test-Case

Dự án này chứa mã nguồn của hệ thống ngân hàng ParaBank và bộ kịch bản kiểm thử API tự động sử dụng Playwright.

# Cấu trúc dự án

- `/parabank`: Chứa mã nguồn/files liên quan đến Web application ParaBank.
- `/playwright`: Chứa API Automation Test Framework.
  - `/pages/API`: API Controllers (Page Objects).
  - `/tests/API`: Các bộ test case API.
  - `/allure-results`: Kết quả báo cáo sau khi chạy test.
- /Bug-report: Vị trí tài liệu báo cáo lỗi.


# AUTOMATION TEST PLAN

## 1. Mục tiêu

Mục tiêu của kế hoạch kiểm thử là xây dựng một bộ API Automation Test nhằm xác nhận tính đúng đắn của các chức năng chính trong hệ thống ParaBank, đồng thời đảm bảo các API hoạt động ổn định, xử lý đúng logic nghiệp vụ và phản hồi chính xác với các trường hợp hợp lệ và không hợp lệ.


### **1.1 In Scope**

Phạm vi kiểm thử tập trung vào **API layer** của hệ thống ParaBank, bao gồm các chức năng nghiệp vụ chính:

* **Authentication** : Đăng nhập người dùng và xác thực token
* **Account Management** : Tạo tài khoản mới, truy xuất thông tin tài khoản
* **Transaction** : Thực hiện các giao dịch như nạp tiền, chuyển tiền, thanh toán hóa đơn
* **Loan** : Đăng ký khoản vay và xử lý yêu cầu vay
* **Customer Information** : Lấy thông tin người dùng
* **Database Control** : Reset dữ liệu hệ thống thông qua API trước mỗi test

Ngoài ra, phạm vi còn bao gồm:

* Kiểm tra response status code và response body
* Kiểm thử các trường hợp hợp lệ (positive) và không hợp lệ (negative)
* Kiểm tra cơ chế xác thực thông qua JWT token

---

### **1.2 Out of Scope**

Các nội dung không nằm trong phạm vi kiểm thử của dự án:

* **UI Testing** : Không kiểm thử giao diện người dùng
* **Performance Testing** : Không đánh giá hiệu năng, tải hoặc stress test
* **Security Testing chuyên sâu** : Không bao gồm penetration testing hoặc kiểm thử bảo mật nâng cao
* **Cross-browser testing** : Không kiểm thử trên nhiều trình duyệt
* **Mobile testing** : Không kiểm thử trên thiết bị di động
* **Third-party integrations** : Không kiểm thử các dịch vụ bên ngoài (nếu có)

## 2. Chiến lược kiểm thử

Framework được xây dựng bằng Playwright, sử dụng cho API testing với cách tiếp cận có cấu trúc rõ ràng và dễ mở rộng. Các API được đóng gói trong các lớp Controller để tái sử dụng và giảm trùng lặp code.

Test được thiết kế theo hướng:

- Có thể chạy độc lập (independent)

- Có thể chạy song song (parallel execution)

- Có khả năng tái sử dụng dữ liệu thông qua data-driven testing

Dữ liệu kiểm thử được tách riêng trong thư mục /test-data/api, giúp dễ dàng thay đổi và mở rộng mà không ảnh hưởng đến logic test.

Trước mỗi test, hệ thống sẽ gọi API reset database (DatabaseAPI.initializeDB) để đảm bảo môi trường luôn ở trạng thái ban đầu. Điều này giúp giảm flaky test và tăng độ tin cậy của kết quả.

## 3. Test Environment

Base URL: ParaBank Demo Web API

Tool: Playwright (API Testing)

Ngôn ngữ: JavaScript (Node.js)

Reporting: Allure Report

OS: Windows

## 4. Tiêu chí đánh giá

Test bắt đầu khi:

* API hoạt động ổn định
* Environment sẵn sàng
* Test data đã được chuẩn bị

Test kết thúc khi:

* Tất cả test case đã được thực thi
* Kết quả test được ghi nhận đầy đủ
* Các lỗi quan trọng đã được phát hiện và báo cáo

## 5. Báo cáo và kết quả

Kết quả kiểm thử được tổng hợp thông qua  **Allure Report** , cung cấp:

* Tổng số test case (pass/fail)
* Chi tiết từng test case
* Log request/response API
* Thông tin lỗi giúp phân tích nhanh

## 6. Rủi ro và hướng xử lý

Một số rủi ro có thể gặp:

* Flaky test do dữ liệu không ổn định
  → Giải pháp: reset database trước mỗi test
* API thay đổi nhưng test chưa cập nhật
  → Giải pháp: thiết kế controller layer để dễ bảo trì
* Phụ thuộc môi trường
  → Giải pháp: chuẩn hóa test data và cấu hình environment


# Hướng dẫn chạy Web Demo

1. Tải về và cài đặt Docker Desktop: `https://www.docker.com/`
2. Mở Terminal và dùng lệnh git clone repo về: `git clone https://github.com/pow267/ParaBankDemoWeb-Automation-Test-Case`
3. Di chuyển vào thư mục parabank: `cd parabank`
4. Dùng lệnh docker compose để Build and Run Demo Web: `docker compose -d --build`

# Hướng dẫn dùng Playwright

1. Di chuyển vào thư mục playwright: `cd playwright`
2. Cài đặt dependency: `npm install`
3. Cài đặt playwright: `npx playwright install`
4. Chạy test ( sau khi chạy hết bộ test sẽ tự xuất Allure Report ): `npm run full-test`
5. Xem lại báo cáo: `npm run report`

© 2026 ParaBank Demo Web Project
