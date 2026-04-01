import { test, expect } from '@playwright/test';
import { CustomersAPI } from '../../pages/API/customers-api.js';
import { LoginAPI } from '../../pages/API/login-api.js';
import { TransactionsAPI } from '../../pages/API/transactions-api.js';
import { DatabaseAPI } from '../../pages/API/database-api.js';
import { getAccountID } from '../../test-data/api/customer.data.js';

test.describe('Kiểm tra Transaction API', () => {

    let customersAPI;
    let loginAPI;
    let transactionsAPI;
    let databaseAPI;
    let accountID;

    test.beforeEach(async ({ request }) => {
        customersAPI = new CustomersAPI(request);
        loginAPI = new LoginAPI(request);
        transactionsAPI = new TransactionsAPI(request);

        databaseAPI = new DatabaseAPI(request);
        const res = await databaseAPI.initializeDB();
        expect(res.ok()).toBeTruthy();

        accountID = await getAccountID(loginAPI, customersAPI);
    });

    test('Transaction-TC01: Lấy lịch sử giao dịch từ tài khoản', async () => {

        const transactionsRes = await transactionsAPI.getTransactions(accountID);
        expect(transactionsRes.status()).toBe(200);
    });

    test('Transaction-TC02: Lấy lịch sử giao dịch từ tài khoản với ngày bắt đầu và kết thúc', async () => {

        const TransctionByDateRes = await transactionsAPI.getTransactionsByDate(accountID, '2026-03-01', '2026-04-01');
        expect(TransctionByDateRes.status()).toBe(200);
    });
});