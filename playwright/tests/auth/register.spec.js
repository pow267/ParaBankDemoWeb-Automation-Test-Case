import { test, expect } from '@playwright/test';
import { RegisterPage } from '../../pages/auth/RegisterPage.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { RegisterData, RegisterInvalidData } from '../../test-data/auth/register.data.js';

test.describe('Kiểm tra chức năng Register', () => {
    let registerPage;
    let databasePage;

    test.beforeEach(async ({ page, request }) => {
        databasePage = new DatabaseAPI(request);
        const cleanRes = await databasePage.cleanDB();
        expect(cleanRes.ok()).toBeTruthy();

        const seedRes = await databasePage.initializeDB();
        expect(seedRes.ok()).toBeTruthy();

        registerPage = new RegisterPage(page);
        await registerPage.goto();
        await registerPage.registerLinkClick();
        expect(registerPage.registerButton).toBeVisible();

    });

    test('Register-TC01: Đăng ký tài khoản thành công', async () => {
        await registerPage.register(RegisterData()[0]);
        await expect(registerPage.successMessage).toBeVisible();
    });

    for (let data of RegisterInvalidData()) {
        test(`Register-TC02: Đăng ký tài khoản với case Invalid: ${data.case}`, async () => {
            await registerPage.register(data);
            if (data.allErrors) {
                await expect(registerPage.errorMessage.first()).toContainText(data.allErrors);
            } else {
                await expect(registerPage.errorMessage.first()).toBeVisible();
            }
        });
    }
});