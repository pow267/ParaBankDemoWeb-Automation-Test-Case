import { test, expect } from '@playwright/test';
import { LoginAPI } from '../../pages/API/login-api.js';
import { BillpayAPI } from '../../pages/API/billpay-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { payeeData, payeeInvalidData } from '../../test-data/api/payee.data.js';
import { AccountsAPI } from '../../pages/API/accounts-api.js';
import { getAccountID } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra Billpay API', async () => {
    let loginAPI;
    let billpayAPI;
    let databaseAPI;
    let customerAPI;
    let accountsAPI;
    let fromAccounts;

    test.beforeEach(async ({ request }) => {
        loginAPI = new LoginAPI(request);
        billpayAPI = new BillpayAPI(request);
        customerAPI = new CustomersAPI(request);
        accountsAPI = new AccountsAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();

        fromAccounts = await getAccountID(loginAPI, customerAPI);
    });

    test('Billpay-TC1: User tạo billpay thành công', async () => {

        const beforeRes = await accountsAPI.getAccountDetails(fromAccounts);
        const beforeData = await beforeRes.json();
        const initialBalance = beforeData.balance;

        const billpayRes = await billpayAPI.pay(fromAccounts, 1000, payeeData());
        expect(billpayRes.status()).toBe(200);

        const afterRes = await accountsAPI.getAccountDetails(fromAccounts);
        const afterData = await afterRes.json();
        const afterBalance = afterData.balance;

        expect(afterBalance).toBe(initialBalance - 1000);
    });

    test('Billpay-TC2: User tạo billpay với số âm', async () => {

        const billpayRes = await billpayAPI.pay(fromAccounts, -1000, payeeData());
        expect(billpayRes.status()).toBe(400);
    });

    for (let data of payeeInvalidData()) {
        test(`Billpay-TC3: User tạo billpay với ${data.case}`, async () => {
            const fromAccounts = await getAccountID(loginAPI, customerAPI, 1);
            const billpayRes = await billpayAPI.pay(fromAccounts, -1000, data.data);
            expect(billpayRes.status()).toBe(400);
        });
    }
});