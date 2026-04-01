import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { TransferAPI } from '../../pages/API/transfer-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { AccountsAPI } from '../../pages/API/accounts-api.js';
import { getAccounts } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra API transfer', () => {
    let transferAPI;
    let databaseAPI;
    let loginAPI;
    let customersAPI;
    let accountsAPI;
    let accounts;
    let fromAccounts;
    let toAccounts;

    test.beforeEach(async ({ request }) => {
        transferAPI = new TransferAPI(request);
        loginAPI = new LoginAPI(request);
        customersAPI = new CustomersAPI(request);
        accountsAPI = new AccountsAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();

        accounts = await getAccounts(loginAPI, customersAPI);
        expect(accounts.length).toBeGreaterThanOrEqual(2);
        fromAccounts = accounts[0].id;
        toAccounts = accounts[1].id;
    });

    test('Transfer-TC01: Chuyển tiền giữa hai tài khoản thành công', async () => {

        const beforeResA = await accountsAPI.getAccountDetails(fromAccounts);
        const beforeDataA = await beforeResA.json();
        const initialBalanceA = beforeDataA.balance;

        const beforeResB = await accountsAPI.getAccountDetails(toAccounts);
        const beforeDataB = await beforeResB.json();
        const initialBalanceB = beforeDataB.balance;

        const tranferRes = await transferAPI.transfer(fromAccounts, toAccounts, 1000);
        expect(tranferRes.status()).toBe(200);

        const afterResA = await accountsAPI.getAccountDetails(fromAccounts);
        const afterBalanceA = (await afterResA.json()).balance;

        const afterResB = await accountsAPI.getAccountDetails(toAccounts);
        const afterBalanceB = (await afterResB.json()).balance;

        expect(afterBalanceA).toBe(initialBalanceA - 1000);
        expect(afterBalanceB).toBe(initialBalanceB + 1000);
    });

    test('Transfer-TC02: Chuyển tiền số âm giữa hai tài khoản ', async () => {

        const tranferRes = await transferAPI.transfer(fromAccounts, toAccounts, -1000);
        expect(tranferRes.status()).toBe(400);
    });

    test('Transfer-TC03: Chuyển tiền số cực lớn giữa hai tài khoản ', async () => {

        const tranferRes = await transferAPI.transfer(fromAccounts, toAccounts, 999999999999999);
        expect(tranferRes.status()).toBe(400);
    });
});