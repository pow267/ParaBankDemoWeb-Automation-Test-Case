import { test as base, expect } from '@playwright/test';
import { LoginPage } from '../pages/auth/LoginPage.js';

export const test = base.extend({

    loginPage: async ({ page }, use) => {
        const loginPage = new LoginPage(page);
        await loginPage.goto();
        await use(loginPage);
    },

    login: async ({ page }, use) => {
        const loginPage = new LoginPage(page);
        await loginPage.goto();
        await loginPage.login('john', 'demo');
        await expect(loginPage.successMessage).toBeVisible();
        await use(loginPage);
    }
});


export { expect };