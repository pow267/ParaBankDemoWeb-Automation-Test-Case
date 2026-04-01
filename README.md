# 🏦 ParaBank-Automation-Test-Case

Dự án này chứa mã nguồn của hệ thống ngân hàng ParaBank và bộ kịch bản kiểm thử API tự động sử dụng Playwright.

## 📁 Cấu trúc dự án
- `/parabank`: Chứa mã nguồn/files liên quan đến ứng dụng ParaBank.
- `/playwright`: Chứa khung kiểm thử API (API Automation Test Framework).
  - `/pages/API`: API Controllers (Page Objects).
  - `/tests/API`: Các bộ kịch bản kiểm thử API.
  - `/allure-results`: Kết quả báo cáo sau khi chạy test.

## 🚀 Hướng dẫn nhanh (Playwright)
1. Di chuyển vào thư mục playwright: `cd playwright`
2. Cài đặt dependency: `npm install`
3. Chạy test: `npx playwright test`
4. Xem báo cáo: `npm run allure-report`

© 2026 ParaBank Automation Project
