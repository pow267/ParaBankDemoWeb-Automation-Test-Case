import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { DepositAPI } from '../../pages/API/deposit-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { AccountsAPI } from '../../pages/API/accounts-api.js';
import { getAccountID } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra Deposit API', async () => {
    let loginAPI;
    let depositAPI;
    let customersAPI;
    let databaseAPI;
    let accountsAPI;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);
        depositAPI = new DepositAPI(request);
        customersAPI = new CustomersAPI(request);
        accountsAPI = new AccountsAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();
    })

    test('Deposit-TC01: nạp tiền vào tài khoản thành công', async () => {

        const accountID = await getAccountID(loginAPI, customersAPI);

        const beforeRes = await accountsAPI.getAccountDetails(accountID);
        const beforeData = await beforeRes.json();
        const initialBalance = beforeData.balance;

        const depositRes = await depositAPI.deposit(accountID, 1000);
        expect(depositRes.status()).toBe(200);

        const afterRes = await accountsAPI.getAccountDetails(accountID);
        const afterData = await afterRes.json();
        const afterBalance = afterData.balance;

        expect(afterBalance).toBe(initialBalance + 1000);
    });
});