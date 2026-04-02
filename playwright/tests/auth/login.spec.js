import { test, expect } from '../../fixture/login-fixture.js';
import { InvalidLogin, NegativeLogin } from '../../test-data/auth/login.data.js';


test.describe('Kiểm tra luồng Login và Logout', () => {

    test('Login-TC01: User thực hiện login rồi logout', async ({ login }) => {
        await login.logout();
        await expect(login.loginButton).toBeVisible();
    });

    for (let data of InvalidLogin()) {
        test(`Login-TC02: Thực hiện login với case invalid: ${data.case}`, async ({ loginPage }) => {
            await loginPage.login(data.username, data.password);
            await expect(loginPage.errorMessage).toContainText('Please enter a username and password.');
        });
    }

    for (let data of NegativeLogin()) {
        test(`Login-TC03: Thực hiện login với case Negative: ${data.case}`, async ({ loginPage }) => {
            await loginPage.login(data.username, data.password);
            await expect(loginPage.errorMessage).toContainText('The username and password could not be verified.');
        });
    }
});