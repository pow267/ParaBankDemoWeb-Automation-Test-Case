#ParaBank-Automation-Test-Case

Dự án này chứa mã nguồn của hệ thống ngân hàng ParaBank và bộ kịch bản kiểm thử API tự động sử dụng Playwright.

## Cấu trúc dự án
- `/parabank`: Chứa mã nguồn/files liên quan đến Web application ParaBank.
- `/playwright`: Chứa API Automation Test Framework.
  - `/pages/API`: API Controllers (Page Objects).
  - `/tests/API`: Các bộ test case API.
  - `/allure-results`: Kết quả báo cáo sau khi chạy test.
    
## Hướng dẫn chạy Web Demo
1. Tải về và cài đặt Docker Desktop: `https://www.docker.com/`
2. Mở Terminal và dùng lệnh git clone repo về: `git clone https://github.com/pow267/ParaBankDemoWeb-Automation-Test-Case`
3. Di chuyển vào thư mục parabank: `cd parabank`
4. Dùng lệnh docker compose để Build and Run Demo Web: `docker compose -d --build`

## Hướng dẫn dùng Playwright
1. Di chuyển vào thư mục playwright: `cd playwright`
2. Cài đặt dependency: `npm install`
3. Cài đặt playwright: `npx playwright install`
4. Chạy test ( sau khi chạy hết bộ test sẽ tự xuất Allure Report ): `npm run full-test`
5. Xem lại báo cáo: `npm run report`


© 2026 ParaBank Demo Web Project
