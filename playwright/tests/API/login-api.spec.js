import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { NegativeLogin, InvalidLogin } from '../../test-data/api/login.data.js';

test.describe('Kiểm tra Login API', () => {
    let loginAPI;
    let databaseAPI;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();
    });

    test('LoginAPI-TC01: Đăng nhập thành công', async () => {
        const res = await loginAPI.login('john', 'demo');
        expect(res.status()).toBe(200);
    });

    for (let data of NegativeLogin()) {
        test(`LoginAPI-TC02: Đăng nhập với trường hợp ${data.case}`, async () => {
            const res = await loginAPI.login(data.username, data.password);
            expect(res.status()).toBe(400);
        });
    }

    for (let data of InvalidLogin()) {
        test(`LoginAPI-TC03: Đăng nhập với trường hợp ${data.case}`, async () => {
            const res = await loginAPI.login(data.username, data.password);
            expect(res.status()).toBe(404);
        });
    }
});